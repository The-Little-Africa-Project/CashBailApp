package com.blacklivesmatter.cashbailbackend.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Entity
public class Cause {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String description;
    private BigDecimal amountRequired;
    private BigDecimal amountReceived;
    private Instant dateOpened;


    @OneToMany(targetEntity = Donation.class)
    List<Donation> donations;

}
