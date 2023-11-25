package com.mitocode.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MedicDTO {

    @EqualsAndHashCode.Include
    private Integer idMedic;

    @NotNull
    @NotEmpty
    @Size(min = 3)
    private String primaryName;

    @NotNull
    @NotEmpty
    @Size(min = 3)
    private String surname;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 12)
    private String cmpMedic;

    @NotNull
    @NotEmpty
    private String photo;

}
