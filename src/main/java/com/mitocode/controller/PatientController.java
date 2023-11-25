package com.mitocode.controller;

import com.mitocode.dto.PatientDTO;
import com.mitocode.dto.PatientRecord;
import com.mitocode.model.Patient;
import com.mitocode.service.IPatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/patients") //endpoints | sustantivos plural
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {

    //@Autowired
    private final IPatientService service;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    /*public PatientController(IPatientService service) {
        this.service = service;
    }*/

    @GetMapping
    public ResponseEntity<List<PatientDTO>> findAll(){
        List<PatientDTO> lst = service.findAll().stream().map(this::convertToDto).toList();
        //List<PatientRecord> lst = service.findAll().stream().map(e -> new PatientRecord(e.getIdPatient(), e.getFirstName(), e.getLastName(), e.getDni(), e.getAddress(), e.getPhone(), e.getEmail())
            /*{
            PatientDTO dto = new PatientDTO();
            dto.setIdPatient(e.getIdPatient());
            dto.setPrimaryName(e.getFirstName());
            dto.setSurname(e.getLastName());
            dto.setDni(e.getDni());
            dto.setEmail(e.getEmail());
            dto.setAddress(e.getAddress());
            return dto;
        }*/
        //).toList();
        return new ResponseEntity<>(lst, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> findById(@PathVariable("id") Integer id){
        Patient obj = service.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    /*@PostMapping
    public ResponseEntity<Patient> save(@RequestBody Patient patient){
        Patient obj = service.save(patient);
        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }*/

    @PostMapping
    public ResponseEntity<PatientDTO> save(@Valid @RequestBody PatientDTO dto){
        Patient obj = service.save(convertToEntity(dto));
        //localhost:8080/patients/3
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdPatient()).toUri();
        return ResponseEntity.created(location).build(); //.body(obj);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody PatientDTO dto) throws Exception {
        dto.setIdPatient(id); //SE DEBE AGREGAR ESTO
        Patient obj = service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<PatientDTO> findByIdHateoas(@PathVariable("id") Integer id){
        EntityModel<PatientDTO> resource = EntityModel.of(convertToDto(service.findById(id)));
        //localhost:8080/patients/1
        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
        WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).findById(id));

        resource.add(link1.withRel("patient-info1"));
        resource.add(link1.withRel("patient-info2"));
        return resource;
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<PatientDTO>> listPage(Pageable pageable){
        Page<PatientDTO> page = service.listPage(pageable).map(p -> mapper.map(p, PatientDTO.class));
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    private PatientDTO convertToDto(Patient obj){
        return mapper.map(obj, PatientDTO.class);
    }

    private Patient convertToEntity(PatientDTO dto){
        return mapper.map(dto, Patient.class);
    }
}
