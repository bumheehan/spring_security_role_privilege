package xyz.bumbing.api.service;

import xyz.bumbing.domain.dto.PrivilegeDto;
import xyz.bumbing.domain.entity.Privilege;

import java.util.List;

public interface PrivilegeService {

    PrivilegeDto create(String name);

    void delete(Long id);

    List<PrivilegeDto> getAll();
}
