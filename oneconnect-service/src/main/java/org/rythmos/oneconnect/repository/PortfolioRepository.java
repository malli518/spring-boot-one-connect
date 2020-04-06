package org.rythmos.oneconnect.repository;

import java.util.List;

import org.rythmos.oneconnect.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

	@Query("from Portfolio where id=:id")
	Portfolio findPortfolioById(@Param("id") Long id);

	@Query("from Portfolio where client_detail_id=:client_detail_id")
	List<Portfolio> findPortfolioByClientId(@Param("client_detail_id") Long client_detail_id);
}
