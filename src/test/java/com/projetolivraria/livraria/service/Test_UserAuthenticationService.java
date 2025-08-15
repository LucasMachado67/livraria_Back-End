package com.projetolivraria.livraria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.projetolivraria.livraria.model.enums.UserRole;
import com.projetolivraria.livraria.model.user.AuthenticationDTO;
import com.projetolivraria.livraria.model.user.LoginResponse;
import com.projetolivraria.livraria.model.user.RegisterRequestDTO;
import com.projetolivraria.livraria.model.user.User;
import com.projetolivraria.livraria.repository.UserRepository;
import com.projetolivraria.livraria.security.TokenService;

@ExtendWith(MockitoExtension.class)
public class Test_UserAuthenticationService {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenService tokenService;
    
    @Mock
    private ApplicationContext context;

    @Mock
    private AuthenticationManager authManager;

    @InjectMocks
    private UserAuthenticationService userAuthenticationService;

    @BeforeEach
    void setup(){
        lenient().when(context.getBean(AuthenticationManager.class)).thenReturn(authManager);
    }

    @Test
    @DisplayName("Should create User when register method is called")
    void testRegisterUser(){
        RegisterRequestDTO dto = new RegisterRequestDTO(
            "lucas",
            "Lucas@gmail.com",
            "123321",
            "47 9 8578-4567",
            "Male");


        ResponseEntity<Object> response = userAuthenticationService.register(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw error when an email already exists")
    void testRegister_EmailAlreadyInUse() {
        RegisterRequestDTO dto = new RegisterRequestDTO("Lucas", "lucas@email.com", "senha123", "123456789", "M");

        when(userRepository.findByEmail(dto.email())).thenReturn(new User());

        ResponseEntity<Object> response = userAuthenticationService.register(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should get Authenticated User when method called")
    void testGetAuthenticatedUser(){
        
        User mockUser = new User();
        mockUser.setName(null);;
        mockUser.setEmail("lucas@gmail.com");
        mockUser.setGender("Male");
        mockUser.setPhone("47 9 8452-5891");
        mockUser.setPassword("1233123");
        mockUser.setRole(UserRole.ADMIN);

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(mockUser);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        User result = userAuthenticationService.getAuthenticatedUser();

        assertNotNull(result);
        assertEquals("lucas@gmail.com", result.getEmail());

    }

    @Test
    @DisplayName("Should throw error when getAuthenticatedUser fail")
    void testGetAuthenticatedUserError(){
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(null);

        UserAuthenticationService userAuthenticationService = new UserAuthenticationService();

        assertThrows(RuntimeException.class, () -> {
            userAuthenticationService.getAuthenticatedUser();
        });
    }

    @Test
    @DisplayName("Should log in User when method log in is called")
    void testLoginser(){

        ApplicationContext context = mock(ApplicationContext.class);
        AuthenticationManager authManager = mock(AuthenticationManager.class);
        TokenService tokenService = mock(TokenService.class);
        UserAuthenticationService service = new UserAuthenticationService();

        AuthenticationDTO dto = new AuthenticationDTO("lucas@gmail.com", "123321");

        User mockUser = mock(User.class);
        when(mockUser.getEmail()).thenReturn("lucas@gmail.com");
        when(mockUser.getName()).thenReturn("Lucas");
        when(mockUser.getPhone()).thenReturn("47 9 8729 9548");
        when(mockUser.getGender()).thenReturn("MASCULINO");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(mockUser);

        when(context.getBean(AuthenticationManager.class)).thenReturn(authManager);
        when(authManager.authenticate(any())).thenReturn(authentication);
        when(tokenService.generateToken(mockUser)).thenReturn("fake-jwt-token");

        ResponseEntity<Object> response = service.login(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        LoginResponse body = (LoginResponse) response.getBody();
        assertNotNull(body);
        assertEquals("fake-jwt-token", body.getToken());
        assertEquals("lucas@gmail.com", body.getEmail());
        assertEquals("Lucas", body.getName());
        
    }

}
