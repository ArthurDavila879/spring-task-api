package projeto.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import projeto.spring.dto.UserRequestDto;
import projeto.spring.dto.UserResponseDto;
import projeto.spring.model.user.User;
import projeto.spring.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    public List<UserResponseDto> getUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponseDto(user.getId(), user.getLogin()))
                .toList();

    }

    public UserResponseDto saveUser(UserRequestDto dto) {
        User user = new User();
        user.setLogin(dto.login());
        user.setPassword(dto.password());

        User savedUser = userRepository.save(user);


        return new UserResponseDto(savedUser.getId(), savedUser.getLogin());
    }
    public UserResponseDto putUser(String id,UserRequestDto dto){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (dto.login() != null){
            user.setLogin(dto.login());
        }

        if (dto.password() != null){
            user.setPassword(dto.password());
        }
        User userSaved = userRepository.save(user);

        return new UserResponseDto(userSaved.getId(), userSaved.getLogin());    }

}
