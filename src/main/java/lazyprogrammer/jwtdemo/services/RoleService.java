/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lazyprogrammer.jwtdemo.services;

import lazyprogrammer.jwtdemo.entities.Role;
import lazyprogrammer.jwtdemo.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class RoleService {

    private static final Logger logger = Logger.getLogger(RoleService.class.getName());
    private final RoleRepository roleRepo;

    public Role getRoleById(Long roleId) {

        Optional<Role> savedRole = roleRepo.findById(roleId);

        if (savedRole.isEmpty()) {

            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Role not found");

        }

        return savedRole.get();

    }

}
