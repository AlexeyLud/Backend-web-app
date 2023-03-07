package come.alex.grow.repositories;

import come.alex.grow.entity.Patients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientsRepository extends JpaRepository<Patients, Integer> {

}