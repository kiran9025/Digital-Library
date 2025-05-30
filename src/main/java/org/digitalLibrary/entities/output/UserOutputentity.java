package org.digitalLibrary.entities.output;

import jakarta.persistence.*;
import lombok.*;
import org.digitalLibrary.enums.USER_ROLE;

import java.time.Instant;

@Entity
@Data
@Builder
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class UserOutputentity {

    @Id
    @Column(name = "userid")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private long userid;
    @Column(name = "firstname",nullable = false)
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "email",nullable = false)
    private String email;
    @Column(name = "date_of_birth")
    private Instant dateofbirth;
    @Column(name = "phone_number",nullable = false)
    private long phonenumber;
    @Column(name = "password")
    private String password;
    @Column(name = "USER_ROLE")
    @Enumerated(EnumType.STRING)
    private USER_ROLE role;


}
