package lazyprogrammer.jwtdemo.services;

import lazyprogrammer.jwtdemo.dtos.PortalUserDto;
import lazyprogrammer.jwtdemo.entities.Branch;
import lazyprogrammer.jwtdemo.entities.Institution;
import lazyprogrammer.jwtdemo.entities.PortalUser;
import lazyprogrammer.jwtdemo.entities.Role;
import lazyprogrammer.jwtdemo.exceptions.APIException;
import lazyprogrammer.jwtdemo.exceptions.RolesNotAvailableException;
import lazyprogrammer.jwtdemo.params.SignUpRequest;
import lazyprogrammer.jwtdemo.repositories.RoleRepository;
import lazyprogrammer.jwtdemo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PortalUserService {
    private final UserRepository userRepository;
    private final BranchService branchService;
    private final InstitutionService institutionService;
    private final RoleRepository roleRepository;
    private static final Logger logger = Logger.getLogger(PortalUserService.class.getName());

    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{5,}$";
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean isCompliantPassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        return pattern.matcher(password).matches();
    }

    public PortalUser registerPortalUser(SignUpRequest signUpRequest){

        Optional<PortalUser> existingAccount = userRepository.findByUsername(signUpRequest.getEmail());
        if (existingAccount.isPresent()) {
            String errorMessage = "Account already exist";
            throw APIException.apiExceptionError(HttpStatus.CONFLICT, errorMessage);
        }

    Institution institution = institutionService.getInstitutionByCode(signUpRequest.getInstitutionCode());
    Branch branch = branchService.getBranchByCode(signUpRequest.getBranchCode());

        if (!branch.getInstitutionId().equals(institution.getId())) {
        String errorMessage = "Branch does not belong to the institution";
        throw APIException.apiExceptionError(HttpStatus.BAD_REQUEST, errorMessage);
    }

    Optional<PortalUser> creatorProfile = userRepository.findById(signUpRequest.getAuditorId());
        if (creatorProfile.isEmpty()) {
        String errorMessage = "Initiator Account profile not found";
        throw APIException.apiExceptionError(HttpStatus.FORBIDDEN, errorMessage);
    }

    PortalUser createdBy = creatorProfile.get();
    //Role role = roleService.getRoleById(signUpRequest.getRoleId());

        if (!isCompliantPassword(signUpRequest.getPassword())) {
            String errorMessage = "Password not compliant";
            throw APIException.apiExceptionError(HttpStatus.BAD_REQUEST, errorMessage);
    }

        List<Role> roles =roleRepository.findByNameIn(signUpRequest.getRoles());
        if (signUpRequest.getRoles().size() != roles.size()) {
            throw new RolesNotAvailableException(signUpRequest.getRoles().toString());
        }

        PortalUser user = PortalUser.builder()
            .branch(branch)
            .checker(signUpRequest.getIsChecker())
            .username(signUpRequest.getEmail())
            .createDate(new Date())
            .institutionId(institution.getId())
            .email(signUpRequest.getEmail())
            .roles(Set.copyOf(roles))
            .deleted(false)
            .firstName(signUpRequest.getFirstName())
            .lastName(signUpRequest.getLastName())
            .password(passwordEncoder.encode(signUpRequest.getPassword()))
            .status(true)
            .username(signUpRequest.getEmail())
            .createBy(createdBy.getId())
            .mobileNumber(signUpRequest.getMobileNumber())
            .firstLogin(true)
            .build();
        try {
        user = userRepository.save(user);
    } catch (Exception ex) {
        String errorMessage = "Error occurred while creating admin profile, please contact service admin";
        logger.log(Level.SEVERE, errorMessage, ex);
        throw APIException.apiExceptionError(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
    }
        return user;
}

}
