package org.rythmos.oneconnect.repository;

import java.sql.Date;
import java.util.List;

import org.rythmos.oneconnect.entity.AgileAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AgileAssessmentRepository extends JpaRepository<AgileAssessment, Long> {

	@Query("from AgileAssessment where projectId=:projectId and assessmentDate between :startDate and :endDate order by id")
	List<AgileAssessment> findByProjectIdAndDate(@Param("projectId") Long projectId, @Param("startDate") Date startDate,
			@Param("endDate") Date endDate);

	@Query("from AgileAssessment where id=:id ")
	AgileAssessment findAssessmentById(@Param("id") long id);

	@Query("from AgileAssessment where question=:question and project_detail_id=:project_detail_id and assessmentDate=:assessmentDate and assessmentType=:assessmentType")
	AgileAssessment findAssessmentBydesc(@Param("question") String question,
			@Param("project_detail_id") Long project_detail_id, @Param("assessmentDate") Date assessmentDate,
			@Param("assessmentType") String assessmentType);

	@Query("from AgileAssessment where project_detail_id=:project_detail_id and assessmentDate=:assessmentDate")
	List<AgileAssessment> findAssessmentByProjectAndDate(@Param("project_detail_id") Long project_detail_id,
			@Param("assessmentDate") Date assessmentDate);
	
	@Query("select distinct(assessmentDate) from AgileAssessment where client_detail_id=:client_detail_id and portfolio_id=:portfolio_id and project_detail_id=:project_detail_id ")
	List<java.util.Date> findReportDates(@Param("client_detail_id") Long client_detail_id,@Param("portfolio_id") Long portfolio_id,@Param("project_detail_id") Long project_detail_id);

}
