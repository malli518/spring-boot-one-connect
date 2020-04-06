package org.rythmos.oneconnect.repository;

import java.util.List;

import org.rythmos.oneconnect.entity.AgileAssessmentQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AgileAssessmentQuestionsRepository extends JpaRepository<AgileAssessmentQuestion, Long> {

	@Query("from AgileAssessmentQuestion")
	List<AgileAssessmentQuestion> findAll();
}
