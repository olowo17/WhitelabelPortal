package lazyprogrammer.jwtdemo.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lazyprogrammer.jwtdemo.dtos.PortalUserDto;
import lazyprogrammer.jwtdemo.entities.Institution;
import lazyprogrammer.jwtdemo.entities.PortalUser;

import java.util.Optional;

public class PortalUserMapper {

    public static PortalUserDto mapEntityToDTO(PortalUser user) throws JsonProcessingException {

        Optional<PortalUserDto> dto = JSONHelper.copyFields(user, PortalUserDto.class);

        return dto.get();

    }

    public static PortalUserDto mapEntityToDTO(PortalUser user, Institution institution) throws JsonProcessingException {

        PortalUserDto dto = JSONHelper.copyFields(user, PortalUserDto.class).get();

        dto.setInstitution(institution);

        return dto;

    }

}
