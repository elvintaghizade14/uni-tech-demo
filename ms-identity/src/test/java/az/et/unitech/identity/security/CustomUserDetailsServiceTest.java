package az.et.unitech.identity.security;

import az.et.unitech.common.model.enums.UserType;
import az.et.unitech.common.security.model.CustomUserPrincipal;
import az.et.unitech.identity.dao.entity.UserEntity;
import az.et.unitech.identity.dao.repository.UserRepository;
import az.et.unitech.identity.error.exception.UserNotEnabledException;
import az.et.unitech.identity.error.exception.UserNotFoundException;
import az.et.unitech.identity.service.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Optional;
import java.util.Set;

import static az.et.unitech.identity.common.TestConstants.USERNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;


    @Test
    void loadUserByUsername_Should_Throw_UserNotFoundException() {
        UserNotFoundException ex = assertThrows(UserNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("wrongUserName"));

        assertEquals(404, ex.getCode());
        assertEquals("User not found!", ex.getMessage());
    }

    @Test
    void loadUserByUsername_Should_Throw_UserNotEnabledException() {
        final UserEntity userEntity = UserEntity.builder().username(USERNAME).enabled(false).build();

        given(userRepository.findByUsername(USERNAME)).willReturn(Optional.of(userEntity));

        UserNotEnabledException ex = assertThrows(UserNotEnabledException.class,
                () -> userDetailsService.loadUserByUsername(USERNAME));
        assertEquals(401, ex.getCode());
        assertEquals("User not enabled!", ex.getMessage());

        then(userRepository).should(times(1)).findByUsername(USERNAME);
    }

    @Test
    void loadUserByUsername_Should_Return_Success() {
        final String username = "TEST123";
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password("test1234")
                .firstName("Elvin2")
                .lastName("Taghizade2")
                .type(UserType.USER)
                .phone("+994553969332")
                .enabled(true)
                .build();
        userEntity.setId(2L);

        given(userRepository.findByUsername(username)).willReturn(Optional.of(userEntity));

        final CustomUserPrincipal expectedUserPrincipal = new CustomUserPrincipal(username, "test1234", "Elvin2 Taghizade2", true,
                UserType.USER, Set.of(new SimpleGrantedAuthority("USER")));

        assertEquals(expectedUserPrincipal, userDetailsService.loadUserByUsername(username));
    }

}