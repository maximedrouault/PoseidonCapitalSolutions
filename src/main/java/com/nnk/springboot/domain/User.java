package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * User is a JPA Entity representing a user in the application.
 * It includes various details about the user such as username, password, fullname, and role.
 */
@Entity
@Table(name = "`user`")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    /**
     * The unique identifier of the user.
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    /**
     * The username associated with the user. It is mandatory and its maximum length is 125.
     */
    @NotBlank(message = "Username is mandatory")
    @Length(max = 125)
    @Column(unique = true)
    private String username;

    /**
     * The password associated with the user. It is mandatory and its length must be between 8 and 125.
     * It must contain at least one uppercase letter, one digit, and one symbol.
     */
    @NotBlank(message = "Password is mandatory")
    @Length(min = 8, max = 125)
    @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter")
    @Pattern(regexp = ".*[0-9].*", message = "Password must contain at least one digit")
    @Pattern(regexp = ".*[!?@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/].*", message = "Password must contain at least one symbol")
    private String password;

    /**
     * The fullname associated with the user. It is mandatory and its maximum length is 125.
     */
    @NotBlank(message = "FullName is mandatory")
    @Length(max = 125)
    private String fullname;

    /**
     * The role associated with the user. It is mandatory and its maximum length is 125.
     */
    @NotBlank(message = "Role is mandatory")
    @Length(max = 125)
    private String role;
}