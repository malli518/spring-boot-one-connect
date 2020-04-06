package org.rythmos.oneconnect.repository;

import java.util.List;

import org.rythmos.oneconnect.entity.NotificationGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationGroupRepository extends JpaRepository<NotificationGroup, Long> {

	@Query("from NotificationGroup where user_id=:user_id order by created_date desc")
	List<NotificationGroup> findNotificationByUserId(@Param("user_id") Long user_id);

	@Query("from NotificationGroup where is_read=FALSE and user_id=:user_id order by created_date desc")
	List<NotificationGroup> findUnReadNotificationByUserId(@Param("user_id") Long user_id);
}
