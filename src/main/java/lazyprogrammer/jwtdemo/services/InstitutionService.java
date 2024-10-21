package lazyprogrammer.jwtdemo.services;

import lazyprogrammer.jwtdemo.entities.Institution;
import lazyprogrammer.jwtdemo.exceptions.APIException;
import lazyprogrammer.jwtdemo.repositories.InstitutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.logging.Logger;


@Service
@RequiredArgsConstructor
public class InstitutionService {

    private static final Logger logger = Logger.getLogger(InstitutionService.class.getName());

    private final InstitutionRepository institutionRepo;

    public Institution getInstitutionByCode(String code) {

        Optional<Institution> existingInstitution = institutionRepo.findByCode(code);

        if (existingInstitution.isEmpty()) {

            String errorMessage = String.format("Institution with code: %s not found", code);

            throw APIException.apiExceptionError(HttpStatus.NOT_FOUND, errorMessage);

        }

        return existingInstitution.get();

    }

    public Institution getInstitutionById(Long id) {

        Optional<Institution> existingInstitution = institutionRepo.findById(id);

        if (existingInstitution.isEmpty()) {

            String errorMessage = String.format("Institution with ID: %s not found", id);

            throw APIException.apiExceptionError( HttpStatus.NOT_FOUND, errorMessage);

        }

        return existingInstitution.get();

    }




}
