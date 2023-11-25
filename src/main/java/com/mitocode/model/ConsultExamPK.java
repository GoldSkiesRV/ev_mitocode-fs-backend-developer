package com.mitocode.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class ConsultExamPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id_consult", nullable = false)
    private Consult consult;

    @ManyToOne
    @JoinColumn(name = "id_exam", nullable = false)
    private Exam exam;

    //ConsultExam (C1, EX2)
    //ConsultExam (C2, EX3)
}
