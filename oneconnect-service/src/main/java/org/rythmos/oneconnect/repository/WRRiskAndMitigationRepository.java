package org.rythmos.oneconnect.repository;

import java.util.List;

import org.rythmos.oneconnect.entity.WRRiskAndMitigation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WRRiskAndMitigationRepository extends JpaRepository<WRRiskAndMitigation, Long> {
	@Query("from WRRiskAndMitigation where id=:id")
	WRRiskAndMitigation findWRRiskAndMitigationById(@Param("id") Long id);

	@Query("from WRRiskAndMitigation where weekly_report_id=:weekly_report_id")
	List<WRRiskAndMitigation> findRiskAndMitigationListbyweeklyReportId(
			@Param("weekly_report_id") Long weekly_report_id);
}
