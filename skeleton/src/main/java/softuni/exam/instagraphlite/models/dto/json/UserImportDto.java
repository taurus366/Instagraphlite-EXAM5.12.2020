package softuni.exam.instagraphlite.models.dto.json;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class UserImportDto {
    @Expose
    @NotNull
    @Length(min = 2,max = 18)
    private String username;

    @Expose
    @NotNull
    @Length(min = 4)
    private String password;

    @Expose
    @NotNull
    private String profilePicture;


    public UserImportDto() {
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
