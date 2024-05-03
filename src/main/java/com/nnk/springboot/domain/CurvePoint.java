package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

import static com.nnk.springboot.constants.Constants.MAX_DOUBLE;


@Entity
@DynamicUpdate
@Data
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "curveId is mandatory")
    @Positive
    @Max(Integer.MAX_VALUE)
    private Integer curveId;

    @NotNull(message = "Term is mandatory")
    @PositiveOrZero
    @DecimalMax(MAX_DOUBLE)
    private Double term;

    @Column(name = "`value`")
    @NotNull(message = "Value is mandatory")
    @PositiveOrZero
    @DecimalMax(MAX_DOUBLE)
    private Double value;


    @FutureOrPresent
    private Timestamp asOfDate;

    @PastOrPresent
    private Timestamp creationDate;
}
