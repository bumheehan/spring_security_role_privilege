package xyz.bumbing.api.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import xyz.bumbing.api.controller.dto.Response;
import xyz.bumbing.api.service.UserService;
import xyz.bumbing.domain.dto.AddressDto;
import xyz.bumbing.domain.dto.UserDto;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public Response<UserDto> create(CreateUserRequest createUserRequest) {
        UserDto.CreateUserDto createUserDto = new UserDto.CreateUserDto();
        createUserDto.setPassword(createUserRequest.getPassword());
        createUserDto.setBirthDay(createUserRequest.getBirthDay());
        createUserDto.setEmail(createUserRequest.getEmail());
        createUserDto.setGender(createUserRequest.getGender());
        createUserDto.setPhone(createUserRequest.getPhone());
        createUserDto.setName(createUserRequest.getName());

        AddressDto addressDto = new AddressDto();
        addressDto.setAddress1(createUserRequest.getAddress1());
        addressDto.setAddress2(createUserRequest.getAddress2());
        addressDto.setZipCode(createUserRequest.getZipCode());

        createUserDto.setAddress(addressDto);

        UserDto userDto = userService.create(createUserDto);

        return Response.ok(userDto);
    }

    @Override
    public Response<UserDto> update(Long id, UpdateUserRequest updateUserRequest) {

        UserDto.UpdateUserDto updateUserDto = new UserDto.UpdateUserDto();
        updateUserDto.setPassword(updateUserRequest.getPassword());

        AddressDto addressDto = new AddressDto();
        addressDto.setAddress1(updateUserRequest.getAddress1());
        addressDto.setAddress2(updateUserRequest.getAddress2());
        addressDto.setZipCode(updateUserRequest.getZipCode());

        updateUserDto.setAddress(addressDto);

        UserDto userDto = userService.update(id, updateUserDto);

        return Response.ok(userDto);
    }

    @Override
    public Response<Void> withdraw(Long id) {
        userService.withdraw(id);
        return Response.ok();
    }

    @Override
    public Response<UserDto> getUser(Long id) {
        return Response.ok(userService.getUser(id));
    }

}
