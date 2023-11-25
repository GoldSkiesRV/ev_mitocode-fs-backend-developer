package com.mitocode.controller;

import com.mitocode.dto.ExamDTO;
import com.mitocode.model.Exam;
import com.mitocode.service.IExamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/exams")
@RequiredArgsConstructor
public class ExamController {
    
    private final IExamService service;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;
    
    @GetMapping
    public ResponseEntity<List<ExamDTO>> findAll(){
        List<ExamDTO> lst = service.findAll().stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(lst, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamDTO> findById(@PathVariable("id") Integer id){
        Exam obj = service.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ExamDTO> save(@Valid @RequestBody ExamDTO dto){
        Exam obj = service.save(convertToEntity(dto));
        //localhost:8080/exams/3
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdExam()).toUri();
        return ResponseEntity.created(location).build(); //.body(obj);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody ExamDTO dto) throws Exception {
        Exam obj = service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<ExamDTO> findByIdHateoas(@PathVariable("id") Integer id){
        EntityModel<ExamDTO> resource = EntityModel.of(convertToDto(service.findById(id)));
        //localhost:8080/exams/1
        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
        WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).findById(id));

        resource.add(link1.withRel("exam-info1"));
        resource.add(link1.withRel("exam-info2"));
        return resource;
    }

    private ExamDTO convertToDto(Exam obj){
        return mapper.map(obj, ExamDTO.class);
    }

    private Exam convertToEntity(ExamDTO dto){
        return mapper.map(dto, Exam.class);
    }
}
