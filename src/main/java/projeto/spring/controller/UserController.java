package projeto.spring.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import projeto.spring.dto.UserRequestDto;
import projeto.spring.dto.UserResponseDto;
import projeto.spring.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public List<UserResponseDto> getUser(){
        return userService.getUsers();
    }
    @PostMapping
    public UserResponseDto postUser( @Valid @RequestBody UserRequestDto dto){
        return userService.saveUser(dto);
    }
    @PutMapping(value = "/{id}")
    public UserResponseDto postUser(@PathVariable  String id,  @RequestBody UserRequestDto dto){
        return userService.putUser(id,dto);
    }
}
