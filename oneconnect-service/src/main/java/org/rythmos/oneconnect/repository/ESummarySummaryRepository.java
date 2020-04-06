package org.rythmos.oneconnect.repository;

import java.util.List;

import org.rythmos.oneconnect.entity.ESummarySummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ESummarySummaryRepository extends JpaRepository<ESummarySummary, Long> {

	@Query("from ESummarySummary where e_summary_e_summary_id=:e_summary_e_summary_id and summaryId=:summaryId")
	ESummarySummary findESummaryBySummaryId(@Param("e_summary_e_summary_id") Long e_summary_e_summary_id,
			@Param("summaryId") Long summaryId);

//	@Query("from ESummarySummary where summaryId=:summaryId")
//	ESummarySummary findESummarySummaryId(@Param("summaryId") Long summaryId);
//	
	@Query("from ESummarySummary where e_summary_e_summary_id=:e_summary_e_summary_id")
	List<ESummarySummary> findSummaryListbyeSummaryId(@Param("e_summary_e_summary_id") Long e_summary_e_summary_id);

}
