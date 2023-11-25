package com.mitocode.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Consult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idConsult;

    @ManyToOne //FK
    @JoinColumn(name = "id_patient", nullable = false, foreignKey = @ForeignKey(name = "FK_CONSULT_PATIENT"))
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "id_medic", nullable = false, foreignKey = @ForeignKey(name = "FK_CONSULT_MEDIC"))
    private Medic medic;

    @ManyToOne
    @JoinColumn(name = "id_specialty", nullable = false, foreignKey = @ForeignKey(name = "FK_CONSULT_SPECIALTY"))
    private Specialty specialty;

    @Column(length = 3, nullable = false)
    private String numConsult;

    @Column(nullable = false)
    private LocalDateTime consultDate;

    @OneToMany(mappedBy = "consult", cascade = CascadeType.ALL, orphanRemoval = true) //fetch = FetchType.EAGER)
    private List<ConsultDetail> details;

    //@Column(columnDefinition = "decimal(6,2)")
    //private double total;
}
