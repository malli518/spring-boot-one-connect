package org.rythmos.oneconnect.repository;

import java.util.List;

import org.rythmos.oneconnect.entity.WRComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WRCommentRepository extends JpaRepository<WRComment, Long> {

	@Query("from WRComment where id=:id")
	WRComment findWRCommentsById(@Param("id") Long id);

	@Query("from WRComment where weekly_report_id=:weekly_report_id")
	List<WRComment> findCommentsListbyweeklyReportId(@Param("weekly_report_id") Long weekly_report_id);
}
