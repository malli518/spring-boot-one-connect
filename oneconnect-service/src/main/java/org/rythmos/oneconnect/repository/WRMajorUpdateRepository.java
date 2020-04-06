package org.rythmos.oneconnect.repository;

import java.util.List;

import org.rythmos.oneconnect.entity.WRMajorUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WRMajorUpdateRepository extends JpaRepository<WRMajorUpdate, Long> {
	@Query("from WRMajorUpdate where id=:id")
	WRMajorUpdate findWRMajorUpdatesById(@Param("id") Long id);

	@Query("from WRMajorUpdate where weekly_report_id=:weekly_report_id")
	List<WRMajorUpdate> findMajorUpdatesListbyweeklyReportId(@Param("weekly_report_id") Long weekly_report_id);
}
