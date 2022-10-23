package az.et.unitech.identity.dao.entity;

import az.et.unitech.common.model.enums.UserType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @NotBlank
    @Column(name = "username", nullable = false)
    private String username;

    @NotBlank
    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private UserType type;

    @NotBlank
    @Column(name = "phone", nullable = false)
    private String phone;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity userEntity = (UserEntity) o;
        return getId().equals(userEntity.getId()) && username.equals(userEntity.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), username);
    }

    @Override
    public String toString() {
        return new StringBuilder("User{")
                .append("id=").append(getId())
                .append(", username='").append(username).append('\'')
                .append(", firstName='").append(firstName).append('\'')
                .append(", lastName='").append(lastName).append('\'')
                .append(", type=").append(type)
                .append(", phone='").append(phone).append('\'')
                .append(", enabled=").append(enabled)
                .append('}')
                .toString();
    }

}
