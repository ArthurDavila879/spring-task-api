package projeto.spring.controller;

import jakarta.validation.Valid;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projeto.spring.dto.AuthenticationDto;
import projeto.spring.dto.RegisterDto;
import projeto.spring.model.user.User;
import projeto.spring.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDto data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDto data) {
      if (this.userRepository.findByLogin(data.login()) !=null){
      return ResponseEntity.badRequest().build();}
        String encryptedPassword =  new BCryptPasswordEncoder().encode(data.password());
      User newUser = new User(data.login(),encryptedPassword,data.role());
      this.userRepository.save(newUser);
      return ResponseEntity.ok().build();
    }

}
