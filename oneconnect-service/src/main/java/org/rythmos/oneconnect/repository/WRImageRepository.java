package org.rythmos.oneconnect.repository;

import java.util.List;

import org.rythmos.oneconnect.entity.WRImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WRImageRepository extends JpaRepository<WRImage, Long> {

	@Query("from WRImage where weekly_report_id=:weekly_report_id ")
	List<WRImage> findWRImagesByWeeklyReportId(@Param("weekly_report_id") Long weekly_report_id);

	@Query("from WRImage where id=:id ")
	WRImage findWRImagesById(@Param("id") Long id);
}
