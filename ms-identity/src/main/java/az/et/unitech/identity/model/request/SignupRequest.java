package az.et.unitech.identity.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @ApiModelProperty(value = "Username should be PIN", example = "ABC1234")
    @NotBlank
    @Size(min = 7, max = 7)
    private String username;

    @NotBlank
    @Size(min = 8)
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Size(min = 10, max = 14)
    private String phone;

    @Override
    public String toString() {
        return new StringBuilder("SignupRequest{")
                .append("username='").append(username).append('\'')
                .append(", password='").append("******").append('\'')
                .append(", firstName='").append(firstName).append('\'')
                .append(", lastName='").append(lastName).append('\'')
                .append(", phone='").append(phone).append('\'')
                .append('}')
                .toString();
    }

}
