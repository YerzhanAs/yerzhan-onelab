package kz.library.system.security.service;

import kz.library.system.domains.entities.Role;
import kz.library.system.domains.entities.User;
import kz.library.system.domains.repositories.UserRepository;
import kz.library.system.models.request.AuthenticationRequest;
import kz.library.system.models.request.RegisterRequest;
import kz.library.system.models.response.AuthenticationResponse;
import kz.library.system.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    void registerUserSuccessfully() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("yerzhanAs");
        request.setPassword("yerzhanBest");
        request.setFirstname("Yerzhan");
        request.setLastname("Ashimov");
        request.setEmail("yerzhan@gmail.com");

        String expectedToken = "randomToken";
        String encodedPassword = "encodedPassword";

        given(passwordEncoder.encode(request.getPassword())).willReturn(encodedPassword);
        given(jwtUtils.generateToken(any(UserDetails.class))).willReturn(expectedToken);

        AuthenticationResponse response = authService.register(request);

        assertEquals(expectedToken, response.getToken());

        verify(passwordEncoder).encode(request.getPassword());

        verify(userRepository).save(argThat(user ->
                user.getUsername().equals(request.getUsername()) &&
                        user.getPassword().equals(encodedPassword) &&
                        user.getFirstname().equals(request.getFirstname()) &&
                        user.getLastname().equals(request.getLastname()) &&
                        user.getEmail().equals(request.getEmail()) &&
                        user.getRole() == Role.USER
        ));

        verify(jwtUtils).generateToken(argThat(userDetails ->
                userDetails.getUsername().equals(request.getUsername())
        ));
    }

    @Test
    void authenticateUserSuccessfully() {
        String username = "yerzhan";
        String password = "yerzhan123";
        AuthenticationRequest request = new AuthenticationRequest(username, password);
        User user = User.builder()
                .username(username)
                .build();

        String expectedToken = "jwtToken";

        given(userRepository.findByUsername(username)).willReturn(Optional.of(user));
        given(jwtUtils.generateToken(user)).willReturn(expectedToken);

        AuthenticationResponse response = authService.authenticate(request);

        assertEquals(expectedToken, response.getToken(), "JWT токен не соответствует ожидаемому");

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByUsername(username);
        verify(jwtUtils).generateToken(user);
    }

    @Test
    void userNotFoundThrowsException() {
        AuthenticationRequest request = new AuthenticationRequest("user", "password");
        given(userRepository.findByUsername(request.getUsername())).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> authService.authenticate(request));
        verify(userRepository).findByUsername(request.getUsername());
    }
}
