package com.nnk.springboot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import java.sql.Timestamp;

import static com.nnk.springboot.constants.Constants.MAX_DOUBLE;

/**
 * BidList is a JPA Entity representing a bid in the application.
 * It includes various details about the bid such as account, type, bid quantity, ask quantity, bid, ask, benchmark,
 * bid list date, commentary, security, status, trader, book, creation name, creation date, revision name, revision date,
 * deal name, deal type, source list id, and side.
 */
@Entity
@DynamicUpdate
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BidList {

    /**
     * The unique identifier of the bid.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The account associated with the bid. It is mandatory and its maximum length is 30.
     */
    @NotBlank(message = "Account is mandatory")
    @Length(max = 30)
    private String account;

    /**
     * The type of the bid. It is mandatory and its maximum length is 30.
     */
    @NotBlank(message = "Type is mandatory")
    @Length(max = 30)
    private String type;

    /**
     * The bid quantity. It is mandatory and must be positive or zero. It must not exceed the maximum double value.
     */
    @NotNull(message = "bidQuantity is mandatory")
    @PositiveOrZero
    @DecimalMax(MAX_DOUBLE)
    private Double bidQuantity;

    /**
     * The ask quantity. It must be positive or zero and must not exceed the maximum double value.
     */
    @PositiveOrZero
    @DecimalMax(MAX_DOUBLE)
    private Double askQuantity;

    /**
     * The bid. It must be positive or zero and must not exceed the maximum double value.
     */
    @PositiveOrZero
    @DecimalMax(MAX_DOUBLE)
    private Double bid;

    /**
     * The ask. It must be positive or zero and must not exceed the maximum double value.
     */
    @PositiveOrZero
    @DecimalMax(MAX_DOUBLE)
    private Double ask;

    /**
     * The benchmark. Its maximum length is 125.
     */
    @Length(max = 125)
    private String benchmark;

    /**
     * The bid list date. It must be in the future or present.
     */
    @FutureOrPresent
    private Timestamp bidListDate;

    /**
     * The commentary. Its maximum length is 125.
     */
    @Length(max = 125)
    private String commentary;

    /**
     * The security. Its maximum length is 125.
     */
    @Length(max = 125)
    private String security;

    /**
     * The status. Its maximum length is 10.
     */
    @Length(max = 10)
    private String status;

    /**
     * The trader. Its maximum length is 125.
     */
    @Length(max = 125)
    private String trader;

    /**
     * The book. Its maximum length is 125.
     */
    @Length(max = 125)
    private String book;

    /**
     * The creation name. Its maximum length is 125.
     */
    @Length(max = 125)
    private String creationName;

    /**
     * The creation date. It must be in the past or present.
     */
    @PastOrPresent
    private Timestamp creationDate;

    /**
     * The revision name. Its maximum length is 125.
     */
    @Length(max = 125)
    private String revisionName;

    /**
     * The revision date. It must be in the past or present.
     */
    @PastOrPresent
    private Timestamp revisionDate;

    /**
     * The deal name. Its maximum length is 125.
     */
    @Length(max = 125)
    private String dealName;

    /**
     * The deal type. Its maximum length is 125.
     */
    @Length(max = 125)
    private String dealType;

    /**
     * The source list id. Its maximum length is 125.
     */
    @Length(max = 125)
    private String sourceListId;

    /**
     * The side. Its maximum length is 125.
     */
    @Length(max = 125)
    private String side;
}
