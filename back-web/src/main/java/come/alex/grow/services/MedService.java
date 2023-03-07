package come.alex.grow.services;

import come.alex.grow.entity.Medicines;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface MedService {
    public Medicines saveMedicine(Medicines medicines);
    public Medicines findByMedId(int med_id);
    public String deleteMedicine(int med_id);
    public List<Medicines> getMedicines();

}
