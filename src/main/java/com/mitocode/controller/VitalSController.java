package com.mitocode.controller;

import com.mitocode.dto.VitalSDTO;
import com.mitocode.model.VitalS;
import com.mitocode.service.IVitalSService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/vitals")
@RequiredArgsConstructor
public class VitalSController {

    private final IVitalSService service;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<VitalSDTO>> findAll(){
        List<VitalSDTO> list = service.findAll().stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VitalSDTO> findById(@PathVariable("id") Integer id){
        VitalS obj = service.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<VitalSDTO> save(@Valid @RequestBody VitalSDTO dto){
        VitalS obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdVitalS()).toUri();
        return ResponseEntity.created(location).build(); //.body(obj);
    }
    @PutMapping("/{id}")
    public ResponseEntity<VitalSDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody VitalSDTO dto) throws Exception {
        VitalS obj = service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/pageable")
    public ResponseEntity<Page<VitalSDTO>> listPage(Pageable pageable){
        Page<VitalSDTO> page = service.listPage(pageable).map(p -> mapper.map(p, VitalSDTO.class));
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
    private VitalSDTO convertToDto(VitalS obj){
        return mapper.map(obj, VitalSDTO.class);
    }

    private VitalS convertToEntity(VitalSDTO dto){
        return mapper.map(dto, VitalS.class);
    }
}
