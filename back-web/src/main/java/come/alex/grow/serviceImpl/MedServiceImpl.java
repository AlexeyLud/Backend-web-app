package come.alex.grow.serviceImpl;

import com.google.common.collect.Lists;
import come.alex.grow.entity.Medicines;
import come.alex.grow.repositories.MedicinesRepository;
import come.alex.grow.services.MedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service("medServiceImpl")
public class MedServiceImpl implements MedService {

    private static final Logger logger = LoggerFactory.getLogger(MedServiceImpl.class);

    @Autowired
    private MedicinesRepository medicinesRepository;
    private List<Medicines> medlist = new ArrayList<>();

    @Override
    public List<Medicines> getMedicines() {
        logger.info("-----[findAll Medicines in repository]-----");
        medlist = Lists.newArrayList(medicinesRepository.findAll());
        return medlist;
    }

    @Override
    public Medicines saveMedicine(Medicines medicines){
        logger.info("-----[save Medicine in repository]-----");
        medicines = medicinesRepository.save(medicines);
        return medicines;
    }

    @Override
    public Medicines findByMedId(int med_id) {
        logger.info("-----[findById get Medicine in repository]-----");
        return medicinesRepository.findById(med_id).get();
    }

    @Override
    public String deleteMedicine(int med_id){
        logger.info("-----[deleteById Medicine in repository]-----");
        medicinesRepository.deleteById(med_id);
        return "Ok! success deleted with id = " + med_id;
    }

}
