package org.rythmos.oneconnect.repository;

import java.sql.Timestamp;
import java.util.List;

import org.rythmos.oneconnect.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

	@Query("from Notification where id = (select max(id) from Notification where last_modified_by=:last_modified_by)")
	Notification findByUserAndTime(@Param("last_modified_by") String last_modified_by);
	
	@Query("from Notification where client_detail_id=:client_detail_id and portfolio_id=:portfolio_id and project_detail_id=:project_detail_id and start_date=:start_date and report_type=:report_type and is_message=:is_message")
	List<Notification> findExistNotification(@Param("client_detail_id") Long client_detail_id,@Param("portfolio_id") Long portfolio_id,@Param("project_detail_id") Long project_detail_id,@Param("start_date") Timestamp start_date,@Param("report_type") String report_type,@Param("is_message") Boolean is_message);
}
