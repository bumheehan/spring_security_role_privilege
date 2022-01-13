package xyz.bumbing.api.service;

import xyz.bumbing.api.service.dto.CreateUserDto;
import xyz.bumbing.domain.dto.UserDto;

public interface UserService {

    public UserDto signIn(CreateUserDto createUserDto);

}
