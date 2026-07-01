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

}
