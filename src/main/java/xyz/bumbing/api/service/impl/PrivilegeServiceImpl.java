package xyz.bumbing.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.bumbing.api.service.PrivilegeService;
import xyz.bumbing.domain.dto.PrivilegeDto;
import xyz.bumbing.domain.entity.Privilege;
import xyz.bumbing.domain.repo.PrivilegeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PrivilegeServiceImpl implements PrivilegeService {

    private final PrivilegeRepository privilegeRepository;

    @Override
    @Transactional
    public PrivilegeDto create(String name) {

        Privilege privilege = privilegeRepository.findByName(name).orElseGet(() -> Privilege.builder().name(name).build());

        privilegeRepository.save(privilege);

        return PrivilegeDto.of(privilege);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        privilegeRepository.deleteById(id);

    }

    @Override
    public List<PrivilegeDto> getAll() {
        return privilegeRepository.findAll().stream().map(PrivilegeDto::of).collect(Collectors.toList());
    }
}
