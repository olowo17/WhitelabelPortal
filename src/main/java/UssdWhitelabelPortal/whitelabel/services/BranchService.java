package UssdWhitelabelPortal.whitelabel.services;

import UssdWhitelabelPortal.whitelabel.entities.Branch;
import UssdWhitelabelPortal.whitelabel.exceptions.APIException;
import UssdWhitelabelPortal.whitelabel.repositories.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BranchService {
    private final BranchRepository branchRepo;

    public Branch getBranchById(Long id) {

        Optional<Branch> existingBranch = branchRepo.findById(id);

        if (existingBranch.isEmpty()) {

            String errorMessage = String.format("Branch with ID: %s not found", id);

            throw APIException.apiExceptionError(HttpStatus.NOT_FOUND, errorMessage);

        }

        return existingBranch.get();

    }

    public Branch getBranchByCode(String code) {

        Optional<Branch> existingBranch = branchRepo.findByCode(code);

        if (existingBranch.isEmpty()) {

            String errorMessage = String.format("Branch with code: %s not found", code);

            throw APIException.apiExceptionError(HttpStatus.NOT_FOUND, errorMessage);

        }

        return existingBranch.get();

    }

}
