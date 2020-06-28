package com.blacklivesmatter.cashbailbackend.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Entity
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    @NotNull
    private BigDecimal amount;

    @NotNull
    @ManyToOne(targetEntity = AppUser.class)
    private AppUser user;

    @NotNull
    @ManyToOne(targetEntity = Cause.class)
    private Cause cause;



}
