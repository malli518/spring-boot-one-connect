package org.rythmos.oneconnect.repository;

import java.sql.Date;
import java.util.List;

import org.rythmos.oneconnect.entity.RagStatus;
import org.rythmos.oneconnect.entity.WeeklyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WeeklyReportRepository extends JpaRepository<WeeklyReport, Long> {

	@Query("from WeeklyReport where id=:id")
	WeeklyReport findWeeklyReportById(@Param("id") Long id);

	@Query("select id from WeeklyReport where client_detail_id=:client_detail_id and portfolio_id=:portfolio_id and project_detail_id=:project_detail_id and wReportDate=:wReportDate")
	Long findWeeklyReportId(@Param("client_detail_id") Long client_detail_id, @Param("portfolio_id") Long portfolio_id,
			@Param("project_detail_id") Long project_detail_id, @Param("wReportDate") Date wReportDate);

	@Query("select ragStatus from WeeklyReport as wr where wr.id=:id")
	RagStatus findWeeklyReportRagStatusById(@Param("id") Long id);
	
	@Query("select distinct(wReportDate) from WeeklyReport where client_detail_id=:client_detail_id and portfolio_id=:portfolio_id and project_detail_id=:project_detail_id ")
	List<java.util.Date> findReportDates(@Param("client_detail_id") Long client_detail_id,@Param("portfolio_id") Long portfolio_id,@Param("project_detail_id") Long project_detail_id);

}
