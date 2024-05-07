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

@Entity
@DynamicUpdate
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name is mandatory")
    @Length(max = 125)
    private String name;

    @NotBlank(message = "Description is mandatory")
    @Length(max = 125)
    private String description;

    @NotBlank(message = "Json is mandatory")
    @Length(max = 125)
    private String json;

    @NotBlank(message = "Template is mandatory")
    @Length(max = 512)
    private String template;

    @NotBlank(message = "sqlStr is mandatory")
    @Length(max = 125)
    private String sqlStr;

    @NotBlank(message = "sqlPart is mandatory")
    @Length(max = 125)
    private String sqlPart;
}
