package org.rythmos.oneconnect.repository;

import java.sql.Date;
import java.util.List;

import org.rythmos.oneconnect.entity.ProjectDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectDetailRepository extends JpaRepository<ProjectDetail, Long> {
	@Query("from ProjectDetail where clientId=:clientId")
	List<ProjectDetail> findProjectByClientId(@Param("clientId") String clientId);

	@Query("from ProjectDetail where projectName=:projectName")
	ProjectDetail findByProjectName(@Param("projectName") String projectName);

	@Query("from ProjectDetail where id=:id")
	ProjectDetail findProjectDetailId(@Param("id") Long Id);

	@Query("select projectName from ProjectDetail where id=:id")
	String findProjectNameById(@Param("id") Long Id);

	@Query("from ProjectDetail where portfolio_id=:portfolioId")
	List<ProjectDetail> findProjectDetailByPortfolioId(@Param("portfolioId") Long Id);
	
	@Query("from ProjectDetail PD where PD.projectEndDate <= :modifiedDate and projectEndDate>=:date")
    List<ProjectDetail> findProjectEndDates(@Param("modifiedDate") Date modifiedDate ,@Param("date") Date date);
}
