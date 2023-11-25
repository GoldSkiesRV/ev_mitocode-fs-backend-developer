package com.mitocode.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.mitocode.dto.*;
import com.mitocode.model.Consult;
import com.mitocode.model.Exam;
import com.mitocode.model.MediaFile;
import com.mitocode.service.IConsultService;
import com.mitocode.service.IMediaFileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.cloudinary.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/consults")
@RequiredArgsConstructor
public class ConsultController {
    
    private final IConsultService service;
    @Qualifier("consultMapper")
    private final ModelMapper mapper;

    private final IMediaFileService mfService;
    private final Cloudinary cloudinary;
    
    @GetMapping
    public ResponseEntity<List<ConsultDTO>> findAll(){
        List<ConsultDTO> lst = service.findAll().stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(lst, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultDTO> findById(@PathVariable("id") Integer id){
        Consult obj = service.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ConsultDTO> save(@Valid @RequestBody ConsultListExamDTO dto){
        Consult cons = this.convertToEntity(dto.getConsult());
        //List<Exam> exams = dto.getLstExam().stream().map( e -> mapper.map(e, Exam.class)).toList();
        List<Exam> exams = mapper.map(dto.getLstExam(), new TypeToken<List<Exam>>(){}.getType());

        Consult obj = service.saveTransactional(cons, exams);

        //localhost:8080/consults/3
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdConsult()).toUri();
        return ResponseEntity.created(location).build(); //.body(obj);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody ConsultDTO dto) throws Exception {
        Consult obj = service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<ConsultDTO> findByIdHateoas(@PathVariable("id") Integer id){
        EntityModel<ConsultDTO> resource = EntityModel.of(convertToDto(service.findById(id)));
        //localhost:8080/consults/1
        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
        WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).findById(id));

        resource.add(link1.withRel("consult-info1"));
        resource.add(link1.withRel("consult-info2"));
        return resource;
    }

    @PostMapping("/search/others")
    public ResponseEntity<List<ConsultDTO>> searchByOthers(@RequestBody FilterConsultDTO filterDTO){
        List<Consult> consults = service.search(filterDTO.getDni(), filterDTO.getFullname());
        //List<ConsultDTO> consultDTOS = consults.stream().map(e -> mapper.map(e, ConsultDTO.class)).toList();
        List<ConsultDTO> consultDTOS = mapper.map(consults, new TypeToken<List<ConsultDTO>>(){}.getType());

        return new ResponseEntity<>(consultDTOS, HttpStatus.OK);
    }

    @GetMapping("/search/date")
    public ResponseEntity<List<ConsultDTO>> searchByDates(
            @RequestParam(value = "date1", defaultValue = "2023-09-28", required = true) String date1,
            @RequestParam(value = "date2") String date2){

        List<Consult> consults = service.searchByDates(LocalDateTime.parse(date1), LocalDateTime.parse(date2));
        List<ConsultDTO> consultDTOS = mapper.map(consults, new TypeToken<List<ConsultDTO>>(){}.getType());

        return new ResponseEntity<>(consultDTOS, HttpStatus.OK);
    }

    @GetMapping("/callProcedureNative")
    public ResponseEntity<List<ConsultProcDTO>> callProcedureOrFunctionNative(){
        List<ConsultProcDTO> consults = service.callProcedureOrFunctionNative();
        return new ResponseEntity<>(consults, HttpStatus.OK);
    }

    @GetMapping("/callProcedureProjection")
    public ResponseEntity<List<IConsultProcDTO>> callProcedureOrFunctionProjection(){
        List<IConsultProcDTO> consults = service.callProcedureOrFunctionProjection();
        return new ResponseEntity<>(consults, HttpStatus.OK);
    }

    @GetMapping(value = "/generateReport", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> generateReport() throws Exception{
        byte[] data = service.generateReport();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping(value = "/saveFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveFile(@RequestParam("file") MultipartFile file) throws Exception{
        //BD
        MediaFile mf = new MediaFile();
        mf.setFileType(file.getContentType());
        mf.setFilename(file.getOriginalFilename());
        mf.setValue(file.getBytes());

        mfService.save(mf);
        //Repo Externo

        /*File f = this.convertToFile(file);
        Map response = cloudinary.uploader().upload(f, ObjectUtils.asMap("resource_type", "auto"));
        JSONObject json = new JSONObject(response);
        String url = json.getString("url");

        System.out.println(url);*/
        //mfService.update(url);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping(value = "/readFile/{idFile}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> readFile(@PathVariable("idFile") Integer idFile) throws IOException {

        byte[] arr = mfService.findById(idFile).getValue();

        return new ResponseEntity<>(arr, HttpStatus.OK);
    }

    public File convertToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(multipartFile.getBytes());
        outputStream.close();
        return file;
    }

    private ConsultDTO convertToDto(Consult obj){
        return mapper.map(obj, ConsultDTO.class);
    }

    private Consult convertToEntity(ConsultDTO dto){
        return mapper.map(dto, Consult.class);
    }
}
