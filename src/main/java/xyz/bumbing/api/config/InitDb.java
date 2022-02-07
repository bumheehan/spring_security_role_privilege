package xyz.bumbing.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import xyz.bumbing.api.service.PrivilegeService;
import xyz.bumbing.api.service.RoleService;
import xyz.bumbing.api.service.UserService;
import xyz.bumbing.domain.dto.AddressDto;
import xyz.bumbing.domain.dto.UserDto;
import xyz.bumbing.domain.type.GenderType;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.initPrivilege();
        initService.initRole();
        initService.initUser();
        initService.initAdmin();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final RoleService roleService;
        private final PrivilegeService privilegeService;
        private final UserService userService;
        private final PasswordEncoder passwordEncoder;

        private final List<Long> initPrivilegeIds = new ArrayList<>();

        public void initPrivilege() {
            initPrivilegeIds.add(privilegeService.create("READ_BOARD").getId());
            initPrivilegeIds.add(privilegeService.create("READ_USER").getId());
            initPrivilegeIds.add(privilegeService.create("WRITE_BOARD").getId());
            initPrivilegeIds.add(privilegeService.create("WRITE_USER").getId());
            initPrivilegeIds.add(privilegeService.create("SUPER").getId());
        }

        public void initRole() {
            roleService.create("ROLE_USER", List.of(initPrivilegeIds.get(0), initPrivilegeIds.get(2)));
            roleService.create("ROLE_ADMIN", initPrivilegeIds);
        }

        public void initUser() {
            UserDto.CreateUserDto createUserDto = new UserDto.CreateUserDto();
            createUserDto.setPassword(passwordEncoder.encode("user"));
            createUserDto.setBirthDay(LocalDate.of(1990, 01, 01));
            createUserDto.setEmail("user@google.com");
            createUserDto.setGender(GenderType.M);
            createUserDto.setPhone("010-000-0000");
            createUserDto.setName("Han");

            AddressDto addressDto = new AddressDto();
            addressDto.setAddress1("seoul");
            addressDto.setAddress2("seocho");
            addressDto.setZipCode("12345");

            createUserDto.setAddress(addressDto);
            UserDto userDto = userService.create(createUserDto);
        }

        public void initAdmin() {
            UserDto.CreateUserDto createUserDto = new UserDto.CreateUserDto();
            createUserDto.setPassword(passwordEncoder.encode("admin"));
            createUserDto.setBirthDay(LocalDate.of(1993, 01, 01));
            createUserDto.setEmail("admin@google.com");
            createUserDto.setGender(GenderType.F);
            createUserDto.setPhone("010-000-0000");
            createUserDto.setName("Bing");

            AddressDto addressDto = new AddressDto();
            addressDto.setAddress1("seoul");
            addressDto.setAddress2("seocho");
            addressDto.setZipCode("12345");

            createUserDto.setAddress(addressDto);
            UserDto userDto = userService.create(createUserDto);
//            userService.removeRole(userDto.getId(), "ROLE_USER");
            userService.addRole(userDto.getId(), "ROLE_ADMIN");
        }

    }


}