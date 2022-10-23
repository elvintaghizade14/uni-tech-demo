package az.et.unitech.identity.repository;

import az.et.unitech.common.model.enums.UserType;
import az.et.unitech.identity.dao.entity.UserEntity;
import az.et.unitech.identity.dao.repository.UserRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    void loadRepository() {
        assertThat(userRepository).isNotNull();
    }

    @Test
    @Order(2)
    void crudOperation_Should_Return_Success() {

        // insert
        UserEntity userEntity = UserEntity.builder()
                .username("ABC1357")
                .password("abc13579")
                .firstName("Elvin")
                .lastName("Taghizade")
                .type(UserType.USER)
                .phone("+994553969332")
                .enabled(true)
                .build();
        userRepository.saveAndFlush(userEntity);

        // select
        UserEntity actualUserEntity = userRepository.findByUsernameEqualsIgnoreCase(userEntity.getUsername()).orElse(null);
        assertThat(actualUserEntity).isNotNull();
        assertEquals(userEntity, actualUserEntity);

        // update
        userEntity.setEnabled(false);
        userRepository.saveAndFlush(userEntity);
        UserEntity updatedUserEntity = userRepository.findByUsernameEqualsIgnoreCase(userEntity.getUsername()).orElse(null);
        assertThat(updatedUserEntity).isNotNull();
        assertEquals(false, updatedUserEntity.getEnabled());

        // delete
        userRepository.delete(userEntity);
        UserEntity deletedUserEntity = userRepository.findByUsername(userEntity.getUsername()).orElse(null);
        assertThat(deletedUserEntity).isNull();
    }

}