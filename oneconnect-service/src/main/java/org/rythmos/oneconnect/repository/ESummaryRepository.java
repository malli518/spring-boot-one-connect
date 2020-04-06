package org.rythmos.oneconnect.repository;

import java.sql.Date;
import java.util.List;

import org.rythmos.oneconnect.entity.ESummary;
import org.rythmos.oneconnect.entity.ProjectDetail;
import org.rythmos.oneconnect.entity.RagStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ESummaryRepository extends JpaRepository<ESummary, Long> {

	@Query("from ESummary where eSummaryId=:eSummaryId")
	ESummary findESummaryById(@Param("eSummaryId") Long eSummaryId);

	@Query("select eSummaryId from ESummary where client_detail_id=:client_detail_id and portfolio_id=:portfolio_id and project_detail_id=:project_detail_id and eSummaryDate=:eSummaryDate")
	Long findESummaryId(@Param("client_detail_id") Long client_detail_id, @Param("portfolio_id") Long portfolio_id,
			@Param("project_detail_id") Long project_detail_id, @Param("eSummaryDate") Date eSummaryDate);

	@Query("select ragStatus from ESummary where e_summary_id=:e_summary_id")
	RagStatus findESummaryRagStatusById(@Param("e_summary_id") Long e_summary_id);

	@Query("select eSummaryId from ESummary where client_detail_id=:client_detail_id")
	List<Long> findESummaryByClientId(@Param("client_detail_id") Long client_detail_id);

	@Query("select projectDetail  from ESummary where rag_status_id=:rag_status_id")
	List<ProjectDetail> findProjectIdByRagStatusId(@Param("rag_status_id") Long rag_status_id);
	
	@Query(value="from   ESummary where (projectDetail,eSummaryDate) in(select projectDetail,max(eSummaryDate) from ESummary group by projectDetail) and rag_status_id=:rag_status_id")
	List<ESummary> findProjectDetailByRagStatusId(@Param("rag_status_id") Long rag_status_id);
	
	@Query(value="from   ESummary where (projectDetail,eSummaryDate) in(select projectDetail,max(eSummaryDate) from ESummary group by projectDetail) and rag_status_id=:rag_status_id and client_detail_id=:client_detail_id")
	List<ESummary> findProjectDetailByRagAndClientId(@Param("rag_status_id") Long rag_status_id,
			@Param("client_detail_id") Long client_detail_id);
	
	@Query(value="from   ESummary where (projectDetail,eSummaryDate) in(select projectDetail,max(eSummaryDate) from ESummary group by projectDetail) and rag_status_id=:rag_status_id and portfolio_id=:portfolio_id")
	List<ESummary> findProjectDetailByRagAndPortfolioId(@Param("rag_status_id") Long rag_status_id,
			@Param("portfolio_id") Long portfolio_id);
	
	@Query("select max(eSummaryDate) from ESummary where project_detail_id=:project_detail_id")
	java.util.Date findRiskByLatestDate(@Param("project_detail_id") Long project_detail_id);
	
	@Query("select distinct(eSummaryDate) from ESummary where client_detail_id=:client_detail_id and portfolio_id=:portfolio_id and project_detail_id=:project_detail_id ")
	List<java.util.Date> findReportDates(@Param("client_detail_id") Long client_detail_id,@Param("portfolio_id") Long portfolio_id,@Param("project_detail_id") Long project_detail_id);
}
