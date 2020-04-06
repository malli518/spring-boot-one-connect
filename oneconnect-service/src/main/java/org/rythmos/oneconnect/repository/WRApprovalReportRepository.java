package org.rythmos.oneconnect.repository;

import org.rythmos.oneconnect.entity.WRApprovalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WRApprovalReportRepository extends JpaRepository<WRApprovalReport, Long> {

	@Query("from WRApprovalReport where weekly_report_id=:weekly_report_id")
	WRApprovalReport findByWRId(@Param("weekly_report_id") Long weekly_report_id);

}
