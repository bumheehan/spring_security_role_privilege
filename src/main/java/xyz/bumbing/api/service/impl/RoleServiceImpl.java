package xyz.bumbing.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.bumbing.api.exception.ErrorCode;
import xyz.bumbing.api.exception.UserException;
import xyz.bumbing.api.service.RoleService;
import xyz.bumbing.domain.dto.RoleDto;
import xyz.bumbing.domain.entity.Privilege;
import xyz.bumbing.domain.entity.Role;
import xyz.bumbing.domain.repo.PrivilegeRepository;
import xyz.bumbing.domain.repo.RoleRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;

    @Override
    @Transactional
    public RoleDto create(String name, List<Long> privilegeIds) {

        if(roleRepository.findByName(name).isPresent()){
            throw new UserException(ErrorCode.DUPLICATION);
        }

        List<Privilege> privileges = privilegeRepository.findAllById(privilegeIds);

        Role role = Role.builder().name(name).build();
        roleRepository.save(role);

        privileges.stream().forEach(role::addPrivilege);

        return RoleDto.of(role);
    }

    @Override
    @Transactional
    public RoleDto update(Long id, List<Long> privilegeIds) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new UserException(ErrorCode.ENTITY_NOT_FOUND));
        List<Privilege> privileges = privilegeRepository.findAllById(privilegeIds);

        role.removePrivileges();
        privileges.stream().forEach(role::addPrivilege);
        return RoleDto.of(role);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public List<RoleDto> getAll() {
        return roleRepository.findAll().stream().map(RoleDto::of).collect(Collectors.toList());
    }
}
