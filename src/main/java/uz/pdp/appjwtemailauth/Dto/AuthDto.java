package uz.pdp.appjwtemailauth.Dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data

public class AuthDto {

    @Size(min = 3, max = 60)
    private String fistname;

    @Size(min = 3, max = 60)
    private String lastname;

    @Email
    private String email;

    private String password;
}
