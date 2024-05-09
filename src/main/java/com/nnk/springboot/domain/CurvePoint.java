package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

import static com.nnk.springboot.constants.Constants.MAX_DOUBLE;

/**
 * CurvePoint is a JPA Entity representing a curve point in the application.
 * It includes various details about the curve point such as curve id, term, value, as of date, and creation date.
 */
@Entity
@DynamicUpdate
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurvePoint {

    /**
     * The unique identifier of the curve point.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The curve id associated with the curve point. It is mandatory and must be positive.
     * It must not exceed the maximum integer value.
     */
    @NotNull(message = "curveId is mandatory")
    @Positive
    @Max(Integer.MAX_VALUE)
    private Integer curveId;

    /**
     * The term of the curve point. It is mandatory and must be positive or zero.
     * It must not exceed the maximum double value.
     */
    @NotNull(message = "Term is mandatory")
    @PositiveOrZero
    @DecimalMax(MAX_DOUBLE)
    private Double term;

    /**
     * The value of the curve point. It is mandatory and must be positive or zero.
     * It must not exceed the maximum double value.
     */
    @Column(name = "`value`")
    @NotNull(message = "Value is mandatory")
    @PositiveOrZero
    @DecimalMax(MAX_DOUBLE)
    private Double value;

    /**
     * The as of date of the curve point. It must be in the future or present.
     */
    @FutureOrPresent
    private Timestamp asOfDate;

    /**
     * The creation date of the curve point. It must be in the past or present.
     */
    @PastOrPresent
    private Timestamp creationDate;
}
