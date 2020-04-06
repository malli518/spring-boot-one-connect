package org.rythmos.oneconnect.repository;

import org.rythmos.oneconnect.entity.RagStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RagStatusRepository extends JpaRepository<RagStatus, Long> {
	@Query("from RagStatus where id=:id")
	RagStatus findRagStatusById(@Param("id") Long id);
}
