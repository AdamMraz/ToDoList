package main.com.repository;

import main.com.model.Case;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface CaseRepo extends JpaRepository<Case, Integer>, JpaSpecificationExecutor<Case> {
}
