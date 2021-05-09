package com.repository;

import com.model.Case;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

@Repository
public interface CaseRepo extends JpaRepository<Case, Integer>, JpaSpecificationExecutor<Case> {
    @Query("SELECT c FROM Case c JOIN c.user WHERE c.user.apiKey = :apiKey")
    List<Case> findByApiKey (@Param("apiKey") String apiKey);
}
