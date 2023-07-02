package calocheck.boundedContext.criteria.repository;

import calocheck.boundedContext.criteria.entity.Criteria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CriteriaRepository extends JpaRepository <Criteria, Long> {

}
