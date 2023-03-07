package come.alex.grow.services;

import come.alex.grow.entity.Patients;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface PatService {
    public Patients savePatient(Patients patients);
    public Patients findByPatId(int pat_id);
    public String deletePatient(int pat_id);
    public List<Patients> getPatients();
}
