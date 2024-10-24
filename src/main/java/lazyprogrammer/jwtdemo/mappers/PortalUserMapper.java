package lazyprogrammer.jwtdemo.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lazyprogrammer.jwtdemo.dtos.PortalUserDto;
import lazyprogrammer.jwtdemo.entities.Institution;
import lazyprogrammer.jwtdemo.entities.PortalUser;
import lazyprogrammer.jwtdemo.entities.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PortalUserMapper {

    public static PortalUserDto mapEntityToDTO(PortalUser user) throws JsonProcessingException {

        Optional<PortalUserDto> dto = JSONHelper.copyFields(user, PortalUserDto.class);

        return dto.get();

    }

    public static PortalUserDto mapEntityToDTO(PortalUser user, Institution institution) throws JsonProcessingException {

//        PortalUserDto dto = JSONHelper.copyFields(user, PortalUserDto.class).get();
//
//        dto.setInstitution(institution);
//
//        return dto;


            PortalUserDto dto = new PortalUserDto();
            dto.setId(user.getId());
            dto.setUserName(user.getUsername());  // Ensure this is mapped correctly
            dto.setEmailAddress(user.getEmail());  // Ensure this is mapped correctly
            dto.setMobileNumber(user.getMobileNumber());
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
            dto.setStatus(user.isStatus());
            dto.setBranch(user.getBranch());
            dto.setInstitution(institution);
            dto.setRoles(new ArrayList<>(user.getRoles()));
            dto.setPassword(user.getPassword());
            dto.setDateCreated(String.valueOf(user.getCreateDate()));
            return dto;
        }




}
