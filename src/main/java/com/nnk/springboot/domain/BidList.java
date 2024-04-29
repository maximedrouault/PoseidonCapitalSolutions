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
@Table(name = "bidlist")
@DynamicUpdate
@Data
public class BidList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer BidListId;

    @NotBlank(message = "Account is mandatory")
    @Length(max = 30)
    private String account;

    @NotBlank(message = "Type is mandatory")
    @Length(max = 30)
    private String type;

    @NotNull(message = "bidQuantity is mandatory")
    @Positive
    @DecimalMax(value = MAX_DOUBLE)
    private Double bidQuantity;

//    @NotNull(message = "askQuantity is mandatory")
//    @Positive
//    @DecimalMax(value = MAX_DOUBLE)
//    private Double askQuantity;
//
//    @NotNull(message = "Bid is mandatory")
//    @Positive
//    @DecimalMax(value = MAX_DOUBLE)
//    private Double bid;
//
//    @NotNull(message = "Ask is mandatory")
//    @Positive
//    @DecimalMax(value = MAX_DOUBLE)
//    private Double ask;
//
//    @NotBlank(message = "Benchmark is mandatory")
//    @Length(max = 125)
//    private String benchmark;
//
//    @NotNull(message = "bidListDate is mandatory")
//    @CreationTimestamp
//    private Timestamp bidListDate;
//
//    @NotBlank(message = "Commentary is mandatory")
//    @Length(max = 125)
//    private String commentary;
//
//    @NotBlank(message = "Security is mandatory")
//    @Length(max = 125)
//    private String security;
//
//    @NotBlank
//    @Length(max = 10)
//    private String status;
//
//    @NotBlank(message = "Trader is mandatory")
//    @Length(max = 125)
//    private String trader;
//
//    @NotBlank(message = "Book is mandatory")
//    @Length(max = 125)
//    private String book;
//
//    @NotBlank(message = "CreationName is mandatory")
//    @Length(max = 125)
//    private String creationName;
//
//    @NotNull(message = "creationDate is mandatory")
//    @PastOrPresent
//    private Timestamp creationDate;
//
//    @NotBlank(message = "revisionName is mandatory")
//    @Length(max = 125)
//    private String revisionName;
//
//    @NotNull(message = "revisionDate is mandatory")
//    @PastOrPresent
//    private Timestamp revisionDate;
//
//    @NotBlank(message = "dealName is mandatory")
//    @Length(max = 125)
//    private String dealName;
//
//    @NotBlank(message = "dealType is mandatory")
//    @Length(max = 125)
//    private String dealType;
//
//    @NotBlank(message = "sourceListId is mandatory")
//    @Length(max = 125)
//    private String sourceListId;
//
//    @NotBlank(message = "Side is mandatory")
//    @Length(max = 125)
//    private String side;
}
