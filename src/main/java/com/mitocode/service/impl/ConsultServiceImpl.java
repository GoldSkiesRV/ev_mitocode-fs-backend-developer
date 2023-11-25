package com.mitocode.service.impl;

import com.mitocode.dto.ConsultProcDTO;
import com.mitocode.dto.IConsultProcDTO;
import com.mitocode.model.Consult;
import com.mitocode.model.Exam;
import com.mitocode.repo.IConsultExamRepo;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IConsultRepo;
import com.mitocode.service.IConsultService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ConsultServiceImpl extends CRUDImpl<Consult, Integer> implements IConsultService {

    //@Autowired
    private final IConsultRepo consultRepo;// = new ConsultRepo();
    private final IConsultExamRepo ceRepo;
    //private final DataSource dataSource;

    @Override
    protected IGenericRepo<Consult, Integer> getRepo() {
        return consultRepo;
    }

    @Transactional
    @Override
    public Consult saveTransactional(Consult consult, List<Exam> exams) {
        consultRepo.save(consult); //GUARDADO MAESTRO DETALLE
        exams.forEach( ex -> ceRepo.saveExam(consult.getIdConsult(), ex.getIdExam()));

        return consult;
    }

    @Override
    public List<Consult> search(String dni, String fullname) {
        return consultRepo.search(dni, fullname);
    }

    @Override
    public List<Consult> searchByDates(LocalDateTime date1, LocalDateTime date2) {
        final int OFFSET_DAYS = 1;
        return consultRepo.searchByDates(date1, date2.plusDays(OFFSET_DAYS));
    }

    @Override
    public List<ConsultProcDTO> callProcedureOrFunctionNative() {
        List<ConsultProcDTO> lst = new ArrayList<>();

        /*
        [3,	"02/09/2023"]
        [2,	"23/09/2023"]
         */

        consultRepo.callProcedureOrFunctionNative().forEach(e -> {
            ConsultProcDTO dto = new ConsultProcDTO();
            dto.setQuantity((Integer) e[0]);
            dto.setConsultdate((String) e[1]);

            lst.add(dto);
        });

        return lst;
    }

    @Override
    public List<IConsultProcDTO> callProcedureOrFunctionProjection() {
        return consultRepo.callProcedureOrFunctionProjection();
    }

    @Override
    public byte[] generateReport() throws Exception {
        byte[] data = null;

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("txt_title", "Report Title");

        File file = new ClassPathResource("/reports/consultas.jasper").getFile();
        JasperPrint print = JasperFillManager.fillReport(file.getPath(), parameters, new JRBeanCollectionDataSource(callProcedureOrFunctionNative()));
        data = JasperExportManager.exportReportToPdf(print);

        return data;
    }
}

