package account.data;

import account.exceptions.ValidPassword;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class NewPasswordDto {
    @JsonProperty("new_password")
    @ValidPassword
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
