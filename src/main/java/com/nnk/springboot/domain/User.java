package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Entity
@Table(name = "`user`")
@Data
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Username is mandatory")
    @Length(max = 125)
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Length(min = 8, max = 125)
    @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter")
    @Pattern(regexp = ".*[0-9].*", message = "Password must contain at least one digit")
    @Pattern(regexp = ".*[!?@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/].*", message = "Password must contain at least one symbol")
    private String password;

    @NotBlank(message = "FullName is mandatory")
    @Length(max = 125)
    private String fullname;

    @NotBlank(message = "Role is mandatory")
    @Length(max = 125)
    private String role;
}