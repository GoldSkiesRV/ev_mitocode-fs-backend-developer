package com.mitocode.service;

import com.mitocode.model.VitalS;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IVitalSService extends ICRUD<VitalS,Integer>{
    Page<VitalS> listPage(Pageable pageable);

}
