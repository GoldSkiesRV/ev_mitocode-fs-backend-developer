package com.mitocode.repo;

import com.mitocode.dto.ConsultProcDTO;
import com.mitocode.dto.IConsultProcDTO;
import com.mitocode.model.Consult;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface IConsultRepo extends IGenericRepo<Consult, Integer>{

    //FR: Jaime Medina -> jaime medina
    //BK BD: jaime medina
    @Query("FROM Consult c WHERE c.patient.dni = :dni OR LOWER(c.patient.firstName) LIKE %:fullname% OR LOWER(c.patient.lastName) LIKE %:fullname%")
    List<Consult> search(@Param("dni") String dni, @Param("fullname") String fullname);

    // >=       |   <
    //15-09-23 al 30-09-23
    @Query("FROM Consult c WHERE c.consultDate BETWEEN :date1 AND :date2")
    List<Consult> searchByDates(@Param("date1")LocalDateTime date1, @Param("date2") LocalDateTime date2);

    @Query(value = "select * from fn_list()", nativeQuery = true)
    List<Object[]> callProcedureOrFunctionNative();
    @Query(value = "select * from fn_list()", nativeQuery = true)
    List<IConsultProcDTO> callProcedureOrFunctionProjection();

}
