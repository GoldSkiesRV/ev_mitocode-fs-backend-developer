package com.mitocode.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(ConsultExamPK.class)
public class ConsultExam {

    @Id
    private Consult consult;

    @Id
    private Exam exam;

    public ConsultExam(Exam exam) {
        this.exam = exam;
    }

    /*
    [
        {
            consult: ....,
            exam: {}
        },
        {
            consult: ....,
            exam: {}
        },
        {
            consult: ....,
            exam: {}
        },
        {
            consult: ....,
            exam: {}
        },
    ]


     */
}
