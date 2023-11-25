package com.mitocode.controller;

import com.mitocode.dto.MedicDTO;
import com.mitocode.model.Medic;
import com.mitocode.service.IMedicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/medics")
@RequiredArgsConstructor
public class MedicController {
    
    private final IMedicService service;
    @Qualifier("medicMapper")
    private final ModelMapper mapper;

    @PreAuthorize("@authorizeLogic.hasAccess('findAll')")
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping
    public ResponseEntity<List<MedicDTO>> findAll(){
        List<MedicDTO> lst = service.findAll().stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(lst, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicDTO> findById(@PathVariable("id") Integer id){
        Medic obj = service.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MedicDTO> save(@Valid @RequestBody MedicDTO dto){
        Medic obj = service.save(convertToEntity(dto));
        //localhost:8080/medics/3
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdMedic()).toUri();
        return ResponseEntity.created(location).build(); //.body(obj);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody MedicDTO dto) throws Exception {
        Medic obj = service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<MedicDTO> findByIdHateoas(@PathVariable("id") Integer id){
        EntityModel<MedicDTO> resource = EntityModel.of(convertToDto(service.findById(id)));
        //localhost:8080/medics/1
        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
        WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).findById(id));

        resource.add(link1.withRel("medic-info1"));
        resource.add(link1.withRel("medic-info2"));
        return resource;
    }

    private MedicDTO convertToDto(Medic obj){
        return mapper.map(obj, MedicDTO.class);
    }

    private Medic convertToEntity(MedicDTO dto){
        return mapper.map(dto, Medic.class);
    }
}
