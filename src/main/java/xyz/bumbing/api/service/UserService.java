package xyz.bumbing.api.service;

import xyz.bumbing.domain.dto.UserDto;

public interface UserService {

    UserDto create(UserDto.CreateUserDto createUserDto);

    UserDto update(Long id, UserDto.UpdateUserDto updateUserDto);

    void withdraw(Long id);

    UserDto getUser(Long id);

    void addRole(Long id,String role);

    void removeRole(Long id, String role);
}
