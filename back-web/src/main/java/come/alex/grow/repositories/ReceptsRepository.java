package come.alex.grow.repositories;

import come.alex.grow.entity.Recepts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceptsRepository extends JpaRepository<Recepts, Integer> {

}