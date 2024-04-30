package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import static com.nnk.springboot.constants.Constants.MAX_DOUBLE;


@Entity
@Table(name = "trade")
@DynamicUpdate
@Data
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tradeId;

    @NotBlank(message = "Account is mandatory")
    @Length(max = 30)
    private String account;

    @NotBlank(message = "Type is mandatory")
    @Length(max = 30)
    private String type;

    @NotNull(message = "buyQuantity is mandatory")
    @Positive
    @DecimalMax(value = MAX_DOUBLE)
    private Double buyQuantity;

//    private Double sellQuantity;
//    private Double buyPrice;
//    private Double sellPrice;
//    private String benchmark;
//    private Timestamp tradeDate;
//    private String security;
//    private String status;
//    private String trader;
//    private String book;
//    private String creationName;
//    private Timestamp creationDate;
//    private String revisionName;
//    private Timestamp revisionDate;
//    private String dealName;
//    private String dealType;
//    private String sourceListId;
//    private String side;
}
