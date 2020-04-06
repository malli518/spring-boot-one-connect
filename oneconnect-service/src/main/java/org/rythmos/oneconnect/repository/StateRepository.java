package org.rythmos.oneconnect.repository;

import java.util.List;

import org.rythmos.oneconnect.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StateRepository extends JpaRepository<State, Long> {
	@Query("from State where country_id=:country_id")
	List<State> findStateByCountryId(@Param("country_id") Long country_id);
}