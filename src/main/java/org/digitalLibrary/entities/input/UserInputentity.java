package org.digitalLibrary.entities.input;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.digitalLibrary.enums.USER_ROLE;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInputentity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userid;
    @NotBlank(message = "firstname required ")

    private String firstname;
    private String lastname;
    @NotBlank(message = "email required ")
    private String email;
    @NotNull(message = "dateofbirth required")
    private Instant dateofbirth;
    @NotNull(message =  "Phone number required : ")
    private long phonenumber;
    @Size(min = 5)
    private String password;
    @Builder.Default
    private USER_ROLE role = USER_ROLE.USER;

}
