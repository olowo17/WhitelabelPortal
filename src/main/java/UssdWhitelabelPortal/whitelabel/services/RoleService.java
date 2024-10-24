/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UssdWhitelabelPortal.whitelabel.services;

import UssdWhitelabelPortal.whitelabel.entities.Menu;
import UssdWhitelabelPortal.whitelabel.entities.PortalUser;
import UssdWhitelabelPortal.whitelabel.entities.Role;
import UssdWhitelabelPortal.whitelabel.entities.RoleFunction;
import UssdWhitelabelPortal.whitelabel.infrastructure.context.Context;
import UssdWhitelabelPortal.whitelabel.repositories.RoleFunctionRepository;
import UssdWhitelabelPortal.whitelabel.repositories.RoleRepository;
import UssdWhitelabelPortal.whitelabel.utils.LocaleHandler;
import UssdWhitelabelPortal.whitelabel.vo.MenuInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private static final Logger logger = Logger.getLogger(RoleService.class.getName());
    private final RoleRepository roleRepo;
    private final RoleFunctionRepository roleFunctionRepository;

    public Set<Long> getRoleIds(PortalUser user) {
        return user.getRoles().stream()
                .map(Role::getId)
                .collect(Collectors.toSet());
    }
    public Long getFirstRoleId(PortalUser user) {
        return user.getRoles().stream()
                .findFirst()
                .map(Role::getId)
                .orElse(null);  // returns null if no role exists
    }

    public void loadRoles(){
        List<Role> roles = new ArrayList<>();
        Role roleSuperAdmin = new Role();
        Role roleAdmin = new Role();
        Role roleUser = new Role();
        roleUser.setName("USER");
        roleSuperAdmin.setName("SUPER_ADMIN");
        roleAdmin.setName("ADMIN");

        roleRepo.saveAll(roles);
    }



    public List<MenuInfoDto> getMenus(Context ctx, long roleID) {

        List<RoleFunction> resultList = roleFunctionRepository.findByRole(roleID);

        List<MenuInfoDto> list = new ArrayList<>();

        for (RoleFunction roleFunction : resultList) {

            Menu menu = roleFunction.getMenu();

            MenuInfoDto menuInfoDto = MenuInfoDto.builder()
                    .href(menu.getHref())
                    .icon(menu.getIcon())
                    .id(menu.getId())
                    .hasSubMenu(menu.getParent() != null)
                    .parentID(menu.getParent() != null ? menu.getParent().getId() : null)
                    .roleID(roleID)
                    .routerLink(menu.getRouterLink())
                    .target(menu.getTarget())
                    .build();

            try {

                menuInfoDto.setTitle(LocaleHandler.getMessage(ctx.getLanguageCode(), menu.getTitle()));

            } catch (Exception e) {

                menuInfoDto.setTitle(menu.getTitle());

            }

            list.add(menuInfoDto);

        }

        return list;
    }

}
