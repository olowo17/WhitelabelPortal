package UssdWhitelabelPortal.whitelabel.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import UssdWhitelabelPortal.whitelabel.dtos.PortalUserDto;
import UssdWhitelabelPortal.whitelabel.dtos.TokenUser;
import UssdWhitelabelPortal.whitelabel.entities.Institution;
import UssdWhitelabelPortal.whitelabel.entities.PortalUser;
import UssdWhitelabelPortal.whitelabel.repositories.InstitutionRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Optional;

@RequiredArgsConstructor
@Data
public class PortalUserMapper {
    private static final InstitutionRepository institutionRepository = null;

    public static PortalUserDto mapEntityToDTO(PortalUser user) throws JsonProcessingException {

        Optional<PortalUserDto> dto = JSONHelper.copyFields(user, PortalUserDto.class);

        return dto.get();

    }

    public static TokenUser mapEntityToTokenUser(PortalUser portalUser) throws JsonProcessingException {
        TokenUser tokenUser = new TokenUser();
        tokenUser.setBranch(portalUser.getBranch());
        tokenUser.setId(portalUser.getId());
        tokenUser.setEmailAddress(portalUser.getEmail());
        tokenUser.setUsername(portalUser.getUsername());
        return tokenUser;
    }

    public static PortalUserDto mapEntityToDTO(PortalUser user, Institution institution) {

            PortalUserDto dto = new PortalUserDto();
            dto.setId(user.getId());
            dto.setUserName(user.getUsername());
            dto.setEmailAddress(user.getEmail());
            dto.setMobileNumber(user.getMobileNumber());
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
            dto.setStatus(user.isStatus());
            dto.setBranch(user.getBranch());
            dto.setInstitution(institution);
            dto.setFirstLogin(user.isFirstLogin());
            dto.setRoles(new ArrayList<>(user.getRoles()));
            dto.setPassword(user.getPassword());
            dto.setDateCreated(String.valueOf(user.getCreateDate()));
            return dto;
        }




}
