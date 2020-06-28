package com.blacklivesmatter.cashbailbackend.model;

import lombok.*;

import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Embeddable
@ToString
public class Address {
    String buildingNumber;
    String streetName;
    String unit;
    String state;
    String city;
    String zipCode;

}
