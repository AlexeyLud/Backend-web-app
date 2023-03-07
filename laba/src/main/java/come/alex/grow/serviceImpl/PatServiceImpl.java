package come.alex.grow.serviceImpl;

import com.google.common.collect.Lists;
import come.alex.grow.entity.Patients;
import come.alex.grow.repositories.PatientsRepository;
import come.alex.grow.services.PatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service("patServiceImpl")
public class PatServiceImpl implements PatService {

    private static final Logger logger = LoggerFactory.getLogger(PatServiceImpl.class);

    @Autowired
    private PatientsRepository patientsRepository;
    private List<Patients> patlist = new ArrayList<>();

    @Override
    public List<Patients> getPatients() {
        logger.info("-----[findAll Patients in repository]-----");
        patlist = Lists.newArrayList(patientsRepository.findAll());
        return patlist;
    }

    @Override
    public Patients savePatient(Patients patients){
        logger.info("-----[save Patient in repository]-----");
        patients = patientsRepository.save(patients);
        return patients;
    }

    @Override
    public Patients findByPatId(int pat_id) {
        logger.info("-----[findById get Patient in repository]-----");
        return patientsRepository.findById(pat_id).get();
    }

    @Override
    public String deletePatient(int pat_id){
        logger.info("-----[deleteById Patient in repository]-----");
        patientsRepository.deleteById(pat_id);
        return "Ok! success deleted with id = " + pat_id;
    }

}
