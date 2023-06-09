package baronvice.mvcstuff.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @NotEmpty(message = "Nickname should not be empty")
    @Size(min = 2, max = 50, message = "Nickname possible length is between 2 and 50")
    private String nickname;
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 50, message = "Nickname possible length is between 2 and 50")
    private String name;
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
    // Country, City, Index
    @Pattern(regexp = "[A-Z]\\w{3,55}, [A-Z]\\w{0,84}, \\d{6}", message = "Incorrect address format")
    private String address;
}
