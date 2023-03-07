package come.alex.grow.repositories;

import come.alex.grow.entity.Medicines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicinesRepository extends JpaRepository<Medicines, Integer> {

}