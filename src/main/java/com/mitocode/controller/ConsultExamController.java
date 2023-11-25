package com.mitocode.controller;

import com.mitocode.dto.ConsultExamDTO;
import com.mitocode.model.ConsultExam;
import com.mitocode.service.IConsultExamService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/consultexams")
@RequiredArgsConstructor
public class ConsultExamController {

    @Qualifier("defaultMapper")
    private final ModelMapper mapper;
    private final IConsultExamService service;

    @GetMapping("/{idConsult}")
    public ResponseEntity<List<ConsultExamDTO>> getConsultsById(@PathVariable("idConsult") Integer idConsult) {
        List<ConsultExam> lst = service.getExamsByConsultId(idConsult);
        List<ConsultExamDTO> lstDTO = mapper.map(lst, new TypeToken<List<ConsultExamDTO>>() {}.getType());

        return new ResponseEntity<>(lstDTO, HttpStatus.OK);
    }
}
