package az.et.unitech.identity.service;

import az.et.unitech.common.security.model.CustomUserPrincipal;
import az.et.unitech.identity.dao.entity.UserEntity;
import az.et.unitech.identity.error.exception.UserNotEnabledException;
import az.et.unitech.identity.error.exception.UserNotFoundException;
import az.et.unitech.identity.dao.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public CustomUserPrincipal loadUserByUsername(String username) {
        final UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        if (!userEntity.getEnabled()) throw new UserNotEnabledException();

        return new CustomUserPrincipal(username,
                userEntity.getPassword(),
                getFullName(userEntity),
                userEntity.getEnabled(),
                userEntity.getType(),
                Collections.emptyList());
    }

    private String getFullName(UserEntity userEntity) {
        return String.join(" ", userEntity.getFirstName(), userEntity.getLastName());
    }

}
