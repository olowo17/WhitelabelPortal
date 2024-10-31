package com.isw.ussd.whitelable.portal.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.isw.ussd.whitelable.portal.dtos.AuthenticationResponse;
import com.isw.ussd.whitelable.portal.dtos.CustomUserDetails;
import com.isw.ussd.whitelable.portal.dtos.PortalUserDto;
import com.isw.ussd.whitelable.portal.entities.user.Institution;
import com.isw.ussd.whitelable.portal.entities.portal.PortalUser;
import com.isw.ussd.whitelable.portal.enums.AuditType;
import com.isw.ussd.whitelable.portal.enums.PendingRequestStatus;
import com.isw.ussd.whitelable.portal.exceptions.APIException;
import com.isw.ussd.whitelable.portal.exceptions.UserNotFoundException;
import com.isw.ussd.whitelable.portal.infrastructure.context.Context;
import com.isw.ussd.whitelable.portal.mappers.PortalUserMapper;
import com.isw.ussd.whitelable.portal.params.*;
import com.isw.ussd.whitelable.portal.repositories.portal.PortalUserRepository;
import com.isw.ussd.whitelable.portal.security.jwt.JwtUtil;
import com.isw.ussd.whitelable.portal.utils.DateHelper;
import com.isw.ussd.whitelable.portal.utils.LocaleHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;


import static com.isw.ussd.whitelable.portal.vo.ResponseMessages.*;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private InstitutionService institutionService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MessagingService messagingService;
    @Autowired
    private PortalUserRepository userRepository;


    @Value("${frontend.reset.completeURL}")
    private String completeResetURL;

    private static final Logger LOG = LoggerFactory.getLogger(AuthService.class.getName());
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public PortalUser getUserByUserNameOrEmail(Context ctx, String identifier) {
        Optional<PortalUser> savedPortalUser = userRepository.findByUsername(identifier);

        if (savedPortalUser.isEmpty()) {
            String errorMessage = LocaleHandler.getMessage(ctx.getLanguageCode(), PORTAL_USER_NOT_FOUND);
            throw APIException.contextualError(ctx, HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
        }

        return savedPortalUser.get();
    }

    public AuthenticationResponse authenticateUser(String email, String password, Context ctx)
            throws BadCredentialsException, JsonProcessingException {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (BadCredentialsException badCredentialsException) {
            LOG.error("Incorrect username or password");
            throw badCredentialsException;
        }

        final CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(email);
        final PortalUser user = userDetails.user();

        Institution institution = user.getInstitutionId() != null
                ? institutionService.getInstitutionById(user.getInstitutionId())
                : null;

        PortalUserDto portalUserDto = PortalUserMapper.mapEntityToDTO(user, institution);
        final String token = jwtUtil.generateToken(userDetails);

        // Retrieve the first role ID or how do you want it returned ?
        Long firstRoleId = roleService.getFirstRoleId(user);

        AuthenticationResponse.LoginData data = new AuthenticationResponse.LoginData();

        data.setToken(token);

        data.setUser(portalUserDto);

        data.setVerticalMenuItems(roleService.getMenus(ctx, firstRoleId));

        return new AuthenticationResponse(data);
    }

    public ResetPasswordResponse initiateResetPassword(Context ctx, ResetPasswordRequest resetPasswordRequest){
        PortalUser user = getUserByUserNameOrEmail(ctx,resetPasswordRequest.getEmail());
        Institution institution = institutionService.getInstitutionById (user.getInstitutionId());

        Date initiateTime = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(initiateTime);
        c.add(Calendar.DATE,2);
        Date expireTime = c.getTime();

        ResetTokenInfo resetTokenInfo = ResetTokenInfo.builder()
                .email(resetPasswordRequest.getEmail())
                .institutionCode(institution.getCode())
                .initiatedTimeStamp(DateHelper.formatDate(initiateTime))
                .expiredTimeStamp(DateHelper.formatDate(expireTime))
                .build();

        final String resetToken = jwtUtil.generateToken(user);

        String details = String.format("Initiate request to reset admin password. Username: %s", user.getUsername());
        sendResetEmail(ctx, resetTokenInfo, resetToken);

        CreateAudit audit = CreateAudit.builder()
                .actionOn(user.getUsername())
                .actionBy(user.getUsername())
                .auditorId(user.getId())
                .auditType(AuditType.RESET_PASSWORD)
                .details(details)
                .institutionId(user.getInstitutionId())
                .status(PendingRequestStatus.APPROVED)
                .userIp(ctx.getSourceIpAddress())
                .build();

        // later: Should be saved to the database
        //  auditRepo.addInitiateAudit(audit);

        return ResetPasswordResponse.builder()
                .actionMessage(ctx.getMessage(PASSWORD_RESET_EMAIL_ACTION))
                .initiatedDate(resetTokenInfo.getInitiatedTimeStamp())
                .build();

    }

    public ResetPasswordResponse changePassword(Context ctx, ChangePasswordRequest changeRequest){
        PortalUser user = getUserByUserNameOrEmail(ctx,changeRequest.getUserName());
        try {
            if (!passwordEncoder.matches(changeRequest.getCurrentPassword(),user.getPassword())){
                throw new Exception("password not a match");
            }
        } catch (Exception e) {
            throw APIException.contextualError(ctx, HttpStatus.FORBIDDEN, ctx.getMessage(INVALID_LOGIN));
        }
        if (!PortalUserService.isCompliantPassword(changeRequest.getProposedPassword())) {
            throw APIException.contextualError(ctx, HttpStatus.BAD_REQUEST, ctx.getMessage(PASSWORD_NOT_COMPLIANT));
        }
        user.setPassword(passwordEncoder.encode(changeRequest.getProposedPassword()));
        user.setFirstLogin(false);
        userRepository.save(user);

        String details = String.format("Initiate complete admin password. Username: %s", user.getUsername());
        CreateAudit audit = CreateAudit.builder()
                .actionOn(user.getUsername())
                .actionBy(user.getUsername())
                .auditorId(user.getId())
                .auditType(AuditType.CHANGE_PASSWORD)
                .details(details)
                .institutionId(user.getInstitutionId())
                .status(PendingRequestStatus.APPROVED)
                .userIp(ctx.getSourceIpAddress())
                .build();
        //auditRepo.addInitiateAudit(audit);

        return ResetPasswordResponse.builder()
                .actionMessage(ctx.getMessage(CHANGE_PASSWORD_COMPLETE))
                .initiatedDate(DateHelper.formatDate(new Date()))
                .build();
    }

    public ResetPasswordResponse completePasswordReset(Context ctx, CompleteResetPasswordRequest completeResetPasswordRequest){
         String email = jwtUtil.extractUsernameFromToken(completeResetPasswordRequest.getToken());
         PortalUser user = userRepository.findByUsername(email).orElseThrow(()->new UserNotFoundException("user not found"));

         if(!PortalUserService.isCompliantPassword(user.getPassword())){
             throw APIException.contextualError(ctx,HttpStatus.BAD_REQUEST, ctx.getMessage(PASSWORD_NOT_COMPLIANT));
         }
         String encodedPassword = passwordEncoder.encode(completeResetPasswordRequest.getPassword());
         user.setPassword(encodedPassword);
         // might not be necesary here
        // user.setFirstLogin(false);
         userRepository.save(user);


        String details = String.format("Complete admin password reset. Username: %s", user.getUsername());
        CreateAudit audit = CreateAudit.builder()
                .actionOn(user.getUsername())
                .actionBy(user.getUsername())
                .auditorId(user.getId())
                .auditType(AuditType.RESET_PASSWORD)
                .details(details)
                .institutionId(user.getInstitutionId())
                .status(PendingRequestStatus.APPROVED)
                .userIp(ctx.getSourceIpAddress())
                .build();
        // later: Should be saved to the database
        //  auditRepo.addInitiateAudit(audit);

        return ResetPasswordResponse.builder()
                .actionMessage(ctx.getMessage(PASSWORD_RESET_COMPLETE))
                .initiatedDate(DateHelper.formatDate(new Date()))
                .build();
    }


    private void sendResetEmail(Context ctx, ResetTokenInfo resetTokenInfo, String resetToken) {
        String content = String.format("%s %s?token=%s", ctx.getMessage(PASSWORD_RESET_EMAIL_MESSAGE), completeResetURL, resetToken);
        messagingService.sendEmail(
                content, resetTokenInfo.getEmail(),
                ctx.getMessage(PASSWORD_RESET_EMAIL_SUBJECT), resetTokenInfo.getInstitutionCode(),
                ctx.getLanguageCode());
    }

}
