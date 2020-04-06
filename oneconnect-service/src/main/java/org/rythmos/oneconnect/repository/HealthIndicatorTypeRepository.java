package org.rythmos.oneconnect.repository;

import org.rythmos.oneconnect.entity.HealthIndicatorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthIndicatorTypeRepository extends JpaRepository<HealthIndicatorType, Long> {
	@Query("from HealthIndicatorType where type=:type")
	HealthIndicatorType findByHealthIndicatorType(@Param("type") Long type);
}
