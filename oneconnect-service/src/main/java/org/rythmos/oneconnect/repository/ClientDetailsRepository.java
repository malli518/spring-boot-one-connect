package org.rythmos.oneconnect.repository;

import org.rythmos.oneconnect.entity.ClientDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDetailsRepository extends JpaRepository<ClientDetail, Long> {

	@Query("from ClientDetail where clientDescription=:clientDescription")
	ClientDetail findClientDetailsByClientName(@Param("clientDescription") String clientDescription);

	@Query("from ClientDetail where id=:id")
	ClientDetail findClientId(@Param("id") Long id);

}
