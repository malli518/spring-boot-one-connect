package org.rythmos.oneconnect.repository;

import java.sql.Date;
import java.util.List;

import org.rythmos.oneconnect.entity.ESummaryRiskAndMitigation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ESummaryRiskAndMitigationRepository extends JpaRepository<ESummaryRiskAndMitigation, Long> {

	@Query("from ESummaryRiskAndMitigation where riskAndMitigationId=:riskAndMitigationId")
	ESummaryRiskAndMitigation findESummaryByRiskAndMitigationId(@Param("riskAndMitigationId") Long riskAndMitigationId);

	@Query("from ESummaryRiskAndMitigation where e_summary_e_summary_id=:e_summary_e_summary_id")
	List<ESummaryRiskAndMitigation> findRiskAndMitigationbyeSummaryId(
			@Param("e_summary_e_summary_id") Long e_summary_e_summary_id);

	@Query("from ESummaryRiskAndMitigation where (status='OPEN'  or (status='CLOSED' and closed_date>=:eSummaryDate ))and e_summary_e_summary_id in (select eSummaryId from ESummary where project_detail_id=:project_detail_id and eSummaryDate<=:eSummaryDate)")
	List<ESummaryRiskAndMitigation> findRiskAndMitigationByRiskStatusAndProject(@Param("project_detail_id") Long project_detail_id, @Param("eSummaryDate") Date eSummaryDate);
	
	@Query("from ESummaryRiskAndMitigation where status=:status")
	List<ESummaryRiskAndMitigation> findRiskAndMitigationByRiskStatus(@Param("status") String status);
	
//	@Query(value = "select edate from (select e_summary_id, max(e_summary_date) as edate from esummary where project_detail_id=?1) as es INNER JOIN esummary_risk_and_mitigation erm ON erm.e_summary_e_summary_id=es.e_summary_id", nativeQuery = true)
//	java.util.Date findRiskByLatestDate(@Param("project_detail_id") Long project_detail_id);

	@Query("from ESummaryRiskAndMitigation where riskAndMitigationId=:riskAndMitigationId")
	ESummaryRiskAndMitigation findRiskAndMitigationById(@Param("riskAndMitigationId") Long riskAndMitigationId);

	@Query("from ESummaryRiskAndMitigation where status=:status and e_summary_e_summary_id in (select eSummaryId from ESummary where client_detail_id =:client_detail_id)")
	List<ESummaryRiskAndMitigation> findRiskAndMitigationByClient(@Param("status") String status,
			@Param("client_detail_id") Long client_detail_id);

	@Query("from ESummaryRiskAndMitigation where status=:status and e_summary_e_summary_id in (select eSummaryId from ESummary where portfolio_id =:portfolio_id)")
	List<ESummaryRiskAndMitigation> findRiskAndMitigationByPortfolio(@Param("status") String status,
			@Param("portfolio_id") Long portfolio_id);

	@Query("from ESummaryRiskAndMitigation where status=:status and e_summary_e_summary_id in (select eSummaryId from ESummary where project_detail_id =:project_detail_id)")
	List<ESummaryRiskAndMitigation> findRiskAndMitigationByProject(@Param("status") String status,
			@Param("project_detail_id") Long project_detail_id);

}
