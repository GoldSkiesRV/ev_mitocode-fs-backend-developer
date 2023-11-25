package com.mitocode.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultListExamDTO {

    @NotNull
    private ConsultDTO consult;

    //@JsonProperty(value = "lst_exam")
    @NotNull
    private List<ExamDTO> lstExam;
}
