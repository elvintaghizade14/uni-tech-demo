package az.et.unitech.identity.service;

import az.et.unitech.common.model.enums.UserType;
import az.et.unitech.common.security.TokenProvider;
import az.et.unitech.identity.model.dto.UserDto;
import az.et.unitech.identity.error.exception.InvalidAccessTokenException;
import az.et.unitech.identity.error.exception.InvalidRefreshTokenException;
import az.et.unitech.identity.error.exception.UsernameAlreadyExistsException;
import az.et.unitech.identity.model.request.SigninRequest;
import az.et.unitech.identity.model.request.SignupRequest;
import az.et.unitech.identity.model.TokenPair;
import az.et.unitech.identity.dao.repository.RedisRepository;
import az.et.unitech.identity.security.TokenUtil;
import az.et.unitech.identity.security.jwt.TokenCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final TokenCreator tokenCreator;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisRepository redisRepository;
    private final AuthenticationManager authenticationManager;

    public void signup(SignupRequest signupRequest) {
        if (userService.isUsernameExist(signupRequest.getUsername())) throw new UsernameAlreadyExistsException();
        UserDto userDto = UserDto.builder()
                .username(signupRequest.getUsername())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .firstName(signupRequest.getFirstName())
                .lastName(signupRequest.getLastName())
                .type(UserType.USER)
                .phone(signupRequest.getPhone())
                .enabled(true)
                .build();
        userService.save(userDto);
    }

    public TokenPair signin(SigninRequest signinRequest) {
        var authToken = new UsernamePasswordAuthenticationToken(
                signinRequest.getUsername(),
                signinRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);
        return createAndSaveToken(authentication);
    }

    public void signout(String authHeader) {
        final String accessToken = TokenUtil.extractToken(authHeader);
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final TokenPair currentTokenPair = redisRepository.read(authentication.getName());

        if (currentTokenPair == null || !Objects.equals(accessToken, currentTokenPair.getAccessToken()))
            throw new InvalidAccessTokenException();

        redisRepository.delete(authentication.getName());
    }

    public void verify(String authHeader) {
        final String accessToken = TokenUtil.extractToken(authHeader);
        final Authentication authentication = tokenProvider.parseAuthentication(accessToken);
        final TokenPair tokenPair = redisRepository.read(authentication.getName());

        Optional.ofNullable(tokenPair)
                .map(TokenPair::getAccessToken)
                .filter(accessToken::equals)
                .orElseThrow(InvalidAccessTokenException::new);
    }

    public TokenPair refreshToken(String refreshToken) {
        tokenProvider.validateToken(refreshToken, InvalidRefreshTokenException::getInstance);
        final Authentication authentication = tokenProvider.parseAuthentication(refreshToken);
        final TokenPair currentTokenPair = redisRepository.read(authentication.getName());

        if (currentTokenPair == null || !Objects.equals(refreshToken, currentTokenPair.getRefreshToken()))
            throw InvalidRefreshTokenException.getInstance();

        final TokenPair newTokenPair = tokenCreator.createTokenPair(authentication);
        redisRepository.update(authentication.getName(), newTokenPair);
        return newTokenPair;
    }

    private TokenPair createAndSaveToken(Authentication authentication) {
        final TokenPair tokenPair = tokenCreator.createTokenPair(authentication);
        redisRepository.save(authentication.getName(), tokenPair);
        return tokenPair;
    }

}
