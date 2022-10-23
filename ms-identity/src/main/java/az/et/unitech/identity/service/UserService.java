package az.et.unitech.identity.service;

import az.et.unitech.common.error.exception.CommonException;
import az.et.unitech.identity.dao.entity.UserEntity;
import az.et.unitech.identity.dao.repository.UserRepository;
import az.et.unitech.identity.mapper.UserMapper;
import az.et.unitech.identity.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto findOne(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new CommonException(HttpStatus.NOT_FOUND.value(), "User not found with id " + id));
    }

    public boolean isUsernameExist(final String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional
    public UserDto save(UserDto userDto) {
        UserEntity userEntity = userMapper.toEntity(userDto);
        return userMapper.toDto(userRepository.save(userEntity));
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
