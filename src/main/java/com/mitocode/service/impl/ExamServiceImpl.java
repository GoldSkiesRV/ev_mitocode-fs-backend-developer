package com.mitocode.service.impl;

import com.mitocode.model.Exam;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IExamRepo;
import com.mitocode.service.IExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl extends CRUDImpl<Exam, Integer> implements IExamService {

    //@Autowired
    private final IExamRepo repo;// = new ExamRepo();

    @Override
    protected IGenericRepo<Exam, Integer> getRepo() {
        return repo;
    }
}
