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
 * Trade is a JPA Entity representing a trade in the application.
 * It includes various details about the trade such as account, type, buy quantity, sell quantity, buy price,
 * sell price, benchmark, trade date, security, status, trader, book, creation name, creation date, revision name,
 * revision date, deal name, deal type, source list id, and side.
 */
@Entity
@DynamicUpdate
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Trade {

    /**
     * The unique identifier of the trade.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The account associated with the trade. It is mandatory and its maximum length is 30.
     */
    @NotBlank(message = "Account is mandatory")
    @Length(max = 30)
    private String account;

    /**
     * The type of the trade. It is mandatory and its maximum length is 30.
     */
    @NotBlank(message = "Type is mandatory")
    @Length(max = 30)
    private String type;

    /**
     * The buy quantity of the trade. It is mandatory and must be positive.
     * It must not exceed the maximum double value.
     */
    @NotNull(message = "buyQuantity is mandatory")
    @Positive
    @DecimalMax(MAX_DOUBLE)
    private Double buyQuantity;

    /**
     * The sell quantity of the trade. It must not exceed the maximum double value.
     */
    @DecimalMax(MAX_DOUBLE)
    private Double sellQuantity;

    /**
     * The buy price of the trade. It must be positive and must not exceed the maximum double value.
     */
    @Positive
    @DecimalMax(MAX_DOUBLE)
    private Double buyPrice;

    /**
     * The sell price of the trade. It must be positive and must not exceed the maximum double value.
     */
    @Positive
    @DecimalMax(MAX_DOUBLE)
    private Double sellPrice;

    /**
     * The benchmark of the trade. Its maximum length is 125.
     */
    @Length(max = 125)
    private String benchmark;

    /**
     * The trade date of the trade. It must be in the past or present.
     */
    @PastOrPresent
    private Timestamp tradeDate;

    /**
     * The security of the trade. Its maximum length is 125.
     */
    @Length(max = 125)
    private String security;

    /**
     * The status of the trade. Its maximum length is 125.
     */
    @Length(max = 125)
    private String status;

    /**
     * The trader of the trade. Its maximum length is 125.
     */
    @Length(max = 125)
    private String trader;

    /**
     * The book of the trade. Its maximum length is 125.
     */
    @Length(max = 125)
    private String book;

    /**
     * The creation name of the trade. Its maximum length is 125.
     */
    @Length(max = 125)
    private String creationName;

    /**
     * The creation date of the trade. It must be in the past or present.
     */
    @PastOrPresent
    private Timestamp creationDate;

    /**
     * The revision name of the trade. Its maximum length is 125.
     */
    @Length(max = 125)
    private String revisionName;

    /**
     * The revision date of the trade. It must be in the past or present.
     */
    @PastOrPresent
    private Timestamp revisionDate;

    /**
     * The deal name of the trade. Its maximum length is 125.
     */
    @Length(max = 125)
    private String dealName;

    /**
     * The deal type of the trade. Its maximum length is 125.
     */
    @Length(max = 125)
    private String dealType;

    /**
     * The source list id of the trade. Its maximum length is 125.
     */
    @Length(max = 125)
    private String sourceListId;

    /**
     * The side of the trade. Its maximum length is 125.
     */
    @Length(max = 125)
    private String side;
}
