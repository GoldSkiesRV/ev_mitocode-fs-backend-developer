package com.mitocode.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class VitalSDTO {
    @EqualsAndHashCode.Include
    private Integer idVitalS;

    @NotNull
    private PatientDTO patient;

    @NotNull
    private LocalDateTime date;

    @NotNull
    private String temperature;

    @NotNull
    private String pulse;

    @NotNull
    private String respirationRate;
}
