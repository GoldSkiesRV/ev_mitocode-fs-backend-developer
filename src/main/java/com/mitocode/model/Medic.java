package com.mitocode.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Medic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idMedic;

    @Column(nullable = false, length = 70)
    private String firstName;

    @Column(nullable = false, length = 70)
    private String lastName;

    @Column(nullable = false, length = 12)
    private String cmp;

    //@Column(nullable = false, length = 70)
    private String photoUrl;
}
