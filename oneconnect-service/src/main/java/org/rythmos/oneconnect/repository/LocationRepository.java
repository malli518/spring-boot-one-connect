package org.rythmos.oneconnect.repository;

import org.rythmos.oneconnect.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LocationRepository extends JpaRepository<Location, Long> {

	@Query(" from Location where cityId=:cityId")
	Location findLocationByCityId(@Param("cityId") Long cityId);

	@Query(" from Location where city_id=:city_id and state_id=:state_id and country_id=:country_id")
	Location findLocation(@Param("city_id") Long city_id, @Param("state_id") Long state_id,
			@Param("country_id") Long country_id);
}
