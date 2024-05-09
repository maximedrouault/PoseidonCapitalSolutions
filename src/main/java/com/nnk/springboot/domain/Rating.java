package com.nnk.springboot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

/**
 * Rating is a JPA Entity representing a rating in the application.
 * It includes various details about the rating such as moodys rating, sandP rating, fitch rating, and order number.
 */
@Entity
@DynamicUpdate
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rating {

    /**
     * The unique identifier of the rating.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The moodys rating associated with the rating. It is mandatory and its maximum length is 125.
     */
    @NotBlank(message = "moodysRating is mandatory")
    @Length(max = 125)
    private String moodysRating;

    /**
     * The sandP rating associated with the rating. It is mandatory and its maximum length is 125.
     */
    @NotBlank(message = "sandPRating is mandatory")
    @Length(max = 125)
    private String sandPRating;

    /**
     * The fitch rating associated with the rating. It is mandatory and its maximum length is 125.
     */
    @NotBlank(message = "fitchRating is mandatory")
    @Length(max = 125)
    private String fitchRating;

    /**
     * The order number associated with the rating. It is mandatory and must be positive. It must not exceed the maximum integer value.
     */
    @NotNull(message = "orderNumber is mandatory")
    @Positive
    @Max(Integer.MAX_VALUE)
    private Integer orderNumber;
}