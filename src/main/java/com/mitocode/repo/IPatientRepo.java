package com.mitocode.repo;

import com.mitocode.model.Patient;
import org.springframework.data.jpa.repository.Query;

public interface IPatientRepo extends IGenericRepo<Patient, Integer>{

    //@Query("UPDATE Patient p SET p.status = 0 WHERE id = :id")
}
