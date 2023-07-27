package account.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class NewPasswordDto {

    @NotEmpty
    @JsonProperty("new_password")
 //   @Min(value = 12, message = "The password length must be at least 12 chars!")
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "NewPasswordDto{" +
                "newPassword='" + newPassword + '\'' +
                '}';
    }
}
