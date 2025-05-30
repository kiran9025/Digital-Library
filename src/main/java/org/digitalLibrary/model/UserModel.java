package org.digitalLibrary.model;

import lombok.*;
import org.digitalLibrary.enums.USER_ROLE;

import java.time.Instant;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

    private long userid;
    private String firstname;
    private String lastname;
    private String email;
    private Instant dateofbirth;
    private long phonenumber;
    private String password;
    private USER_ROLE role;



}
