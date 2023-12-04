package info.pionas.quiz.api.auth;

import info.pionas.quiz.api.security.JWTUtil;
import info.pionas.quiz.domain.user.UserService;
import info.pionas.quiz.domain.user.api.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping
@RequiredArgsConstructor
class AuthenticationRestController {

    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public Mono<AuthResponse> login(@Valid @RequestBody AuthRequest ar) {
        return Mono.just(userService.findByUsername(ar.getUsername())
                .filter(userDetails -> passwordEncoder.encode(ar.getPassword()).equals(userDetails.getPassword()))
                .map(userDetails -> new AuthResponse(jwtUtil.generateToken(userDetails)))
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not exist", ar.getUsername()))));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AuthResponse> register(@Valid @RequestBody NewUserRequest newUserRequest) {
        User user = userService.addUser(userMapper.map(newUserRequest));
        return Mono.just(new AuthResponse(jwtUtil.generateToken(user)));
    }
}
