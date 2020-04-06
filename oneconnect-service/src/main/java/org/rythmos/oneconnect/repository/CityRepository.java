package org.rythmos.oneconnect.repository;

import java.util.List;

import org.rythmos.oneconnect.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

	@Query("from City where Id=:cityId")
	City findCityByCityId(@Param("cityId") Long cityId);

	@Query("from City where id=:id")
	City findStateIdByCityId(@Param("id") Long id);

	@Query("from City where state_id=:state_id")
	List<City> findCityByStateId(@Param("state_id") Long state_id);
}
