package org.rythmos.oneconnect.repository;

import java.util.Date;
import java.util.List;

import org.rythmos.oneconnect.entity.ClientHealthReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthReportsRepository extends JpaRepository<ClientHealthReport, Long> {
	@Query("from ClientHealthReport where projectId=:projectId and monthYear=:monthYear order by  ratingOnAScaleOfFive,monthYear")
	List<ClientHealthReport> findByProjectId(@Param("projectId") Long projectId, @Param("monthYear") Date monthYear);
}