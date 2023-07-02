package calocheck.boundedContext.criteria.repository;

import calocheck.boundedContext.criteria.entity.Criteria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CriteriaRepository extends JpaRepository <Criteria, Long> {

    Optional<Criteria> findByGenderAndAge(String gender, int age);

}
