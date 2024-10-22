package lazyprogrammer.jwtdemo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import lazyprogrammer.jwtdemo.dtos.AuthenticationResponse;
import lazyprogrammer.jwtdemo.dtos.CustomUserDetails;
import lazyprogrammer.jwtdemo.dtos.PortalUserDto;
import lazyprogrammer.jwtdemo.entities.Institution;
import lazyprogrammer.jwtdemo.entities.PortalUser;
import lazyprogrammer.jwtdemo.infrastructure.context.Context;
import lazyprogrammer.jwtdemo.mappers.PortalUserMapper;
import lazyprogrammer.jwtdemo.security.jwt.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

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

    private static final Logger LOG = LoggerFactory.getLogger(AuthService.class.getName());

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
}
