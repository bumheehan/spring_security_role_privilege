package xyz.bumbing.api.service;

import xyz.bumbing.domain.dto.PrivilegeDto;
import xyz.bumbing.domain.dto.RoleDto;

import java.util.List;

public interface RoleService {

    RoleDto create(String name,List<Long> privileges);

    RoleDto update(Long id, List<Long> privileges);

    void delete(Long id);

    List<RoleDto> getAll();
}
