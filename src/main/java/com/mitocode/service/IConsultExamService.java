package com.mitocode.service;

import com.mitocode.model.ConsultExam;

import java.util.List;

public interface IConsultExamService {

    List<ConsultExam> getExamsByConsultId(Integer idConsult);

}
