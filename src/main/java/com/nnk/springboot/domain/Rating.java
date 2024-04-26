package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "rating")
@Data
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "MoodysRating is mandatory")
    @Length(max = 125)
    private String moodysRating;

    @NotBlank(message = "sandPRating is mandatory")
    @Length(max = 125)
    private String sandPRating;

    @NotBlank(message = "fitchRating is mandatory")
    @Length(max = 125)
    private String fitchRating;

    @NotNull(message = "orderNumber is mandatory")
    @Positive
    @Max(127)
    private Integer orderNumber;
}