package com.nnk.springboot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

/**
 * RuleName is a JPA Entity representing a rule name in the application.
 * It includes various details about the rule name such as name, description, json, template, sqlStr, and sqlPart.
 */
@Entity
@DynamicUpdate
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleName {

    /**
     * The unique identifier of the rule name.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The name associated with the rule name. It is mandatory and its maximum length is 125.
     */
    @NotBlank(message = "Name is mandatory")
    @Length(max = 125)
    private String name;

    /**
     * The description associated with the rule name. It is mandatory and its maximum length is 125.
     */
    @NotBlank(message = "Description is mandatory")
    @Length(max = 125)
    private String description;

    /**
     * The json associated with the rule name. It is mandatory and its maximum length is 125.
     */
    @NotBlank(message = "Json is mandatory")
    @Length(max = 125)
    private String json;

    /**
     * The template associated with the rule name. It is mandatory and its maximum length is 512.
     */
    @NotBlank(message = "Template is mandatory")
    @Length(max = 512)
    private String template;

    /**
     * The sqlStr associated with the rule name. It is mandatory and its maximum length is 125.
     */
    @NotBlank(message = "sqlStr is mandatory")
    @Length(max = 125)
    private String sqlStr;

    /**
     * The sqlPart associated with the rule name. It is mandatory and its maximum length is 125.
     */
    @NotBlank(message = "sqlPart is mandatory")
    @Length(max = 125)
    private String sqlPart;
}
