package xyz.bumbing.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import xyz.bumbing.api.service.PrivilegeService;
import xyz.bumbing.api.service.RoleService;

import javax.annotation.PostConstruct;

//@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final RoleService roleService;
        private final PrivilegeService privilegeService;

        public void initPrivilege() {
            privilegeService.create("READ_BOARD");
            privilegeService.create("READ_USER");
            privilegeService.create("WRITE_BOARD");
            privilegeService.create("WRITE_USER");
            privilegeService.create("SUPER");
        }

        public void initRole() {

        }


    }


}