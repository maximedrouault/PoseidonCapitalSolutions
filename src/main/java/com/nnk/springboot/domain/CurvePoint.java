package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import static com.nnk.springboot.constants.Constants.MAX_DOUBLE;


@Entity
@Table(name = "curvepoint")
@DynamicUpdate
@Data
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "curveId is mandatory")
    @Positive
    @Max(127)
    private Integer curveId;

    //    private Timestamp asOfDate;

    @NotNull(message = "Term is mandatory")
    @Positive
    @DecimalMax(value = MAX_DOUBLE)
    private Double term;

    @NotNull(message = "Value is mandatory")
    @Positive
    @DecimalMax(value = MAX_DOUBLE)
    private Double value;

//    private Timestamp creationDate;
}
