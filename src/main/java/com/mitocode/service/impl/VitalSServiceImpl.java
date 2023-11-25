package com.mitocode.service.impl;

import com.mitocode.model.VitalS;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IVitalSRepo;
import com.mitocode.service.IVitalSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VitalSServiceImpl extends CRUDImpl<VitalS,Integer> implements IVitalSService {

    @Autowired
    private IVitalSRepo repo;
    @Override
    public Page<VitalS> listPage(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    protected IGenericRepo<VitalS, Integer> getRepo() {
        return repo;
    }
}
