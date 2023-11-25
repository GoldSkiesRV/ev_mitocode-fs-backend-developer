package com.mitocode.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class VitalS {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idVitalS;

    @ManyToOne
    @JoinColumn(name = "id_patient", nullable = false, foreignKey = @ForeignKey(name= "FK_VITALS_PATIENT"))
    private Patient patient;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false, length = 100)
    private String temperature;

    @Column(nullable = false, length = 100)
    private String pulse;

    @Column(nullable = false, length = 100)
    private String respirationRate;
}
