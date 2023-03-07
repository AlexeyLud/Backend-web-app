package come.alex.grow.services;

import come.alex.grow.entity.Recepts;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface RecService {
    public Recepts saveRecept(Recepts recepts);
    public Recepts findByRecId(int id);
    public String deleteRecept(int id);
    public List<Recepts> getRecepts();
}
