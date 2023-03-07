package come.alex.grow.serviceImpl;

import com.google.common.collect.Lists;
import come.alex.grow.entity.Recepts;
import come.alex.grow.repositories.ReceptsRepository;
import come.alex.grow.services.RecService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service("recServiceImpl")
public class RecServiceImpl implements RecService {

    private static final Logger logger = LoggerFactory.getLogger(RecServiceImpl.class);

    @Autowired
    private ReceptsRepository receptsRepository;
    private List<Recepts> reclist = new ArrayList<>();

    @Override
    public List<Recepts> getRecepts() {
        logger.info("-----[findAll Recepts in repository]-----");
        reclist = Lists.newArrayList(receptsRepository.findAll());
        return reclist;
    }

    @Override
    public Recepts saveRecept(Recepts recepts){
        logger.info("-----[save Recept in repository]-----");
        recepts = receptsRepository.save(recepts);
        return recepts;
    }

    @Override
    public Recepts findByRecId(int id) {
        logger.info("-----[findById get Recept in repository]-----");
        return receptsRepository.findById(id).get();
    }

    @Override
    public String deleteRecept(int id){
        logger.info("-----[deleteById Recept in repository]-----");
        receptsRepository.deleteById(id);
        return "Ok! success deleted with id = " + id;
    }

}
