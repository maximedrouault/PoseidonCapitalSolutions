package com.nnk.springboot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import java.sql.Timestamp;

import static com.nnk.springboot.constants.Constants.MAX_DOUBLE;


@Entity
@DynamicUpdate
@Data
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Account is mandatory")
    @Length(max = 30)
    private String account;

    @NotBlank(message = "Type is mandatory")
    @Length(max = 30)
    private String type;

    @NotNull(message = "buyQuantity is mandatory")
    @Positive
    @DecimalMax(MAX_DOUBLE)
    private Double buyQuantity;


    @DecimalMax(MAX_DOUBLE)
    private Double sellQuantity;

    @Positive
    @DecimalMax(MAX_DOUBLE)
    private Double buyPrice;

    @Positive
    @DecimalMax(MAX_DOUBLE)
    private Double sellPrice;

    @Length(max = 125)
    private String benchmark;

    @PastOrPresent
    private Timestamp tradeDate;

    @Length(max = 125)
    private String security;

    @Length(max = 125)
    private String status;

    @Length(max = 125)
    private String trader;

    @Length(max = 125)
    private String book;

    @Length(max = 125)
    private String creationName;

    @PastOrPresent
    private Timestamp creationDate;

    @Length(max = 125)
    private String revisionName;

    @PastOrPresent
    private Timestamp revisionDate;

    @Length(max = 125)
    private String dealName;

    @Length(max = 125)
    private String dealType;

    @Length(max = 125)
    private String sourceListId;

    @Length(max = 125)
    private String side;
}
