package org.rythmos.oneconnect.repository;

import java.sql.Date;
import java.util.List;

import org.rythmos.oneconnect.entity.ClientHealthReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientHealthReportsRepository extends JpaRepository<ClientHealthReport, Long> {
	@Query("from ClientHealthReport where client_detail_id=:client_detail_id and portfolio_id=:portfolio_id and project_detail_id=:project_detail_id and date=:date")
	List<ClientHealthReport> findHealthReportByProject(@Param("client_detail_id") Long client_detail_id,
			@Param("portfolio_id") Long portfolio_id, @Param("project_detail_id") Long project_detail_id,
			@Param("date") Date date);

	@Query("from ClientHealthReport where id=:id")
	ClientHealthReport findHealthReportById(@Param("id") Long id);
	
	@Query("select distinct(date) from ClientHealthReport where client_detail_id=:client_detail_id and portfolio_id=:portfolio_id and project_detail_id=:project_detail_id ")
	List<java.util.Date> findReportDates(@Param("client_detail_id") Long client_detail_id,@Param("portfolio_id") Long portfolio_id,@Param("project_detail_id") Long project_detail_id);
}
