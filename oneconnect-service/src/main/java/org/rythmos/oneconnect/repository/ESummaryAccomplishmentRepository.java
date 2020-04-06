package org.rythmos.oneconnect.repository;

import java.util.List;

import org.rythmos.oneconnect.entity.ESummaryAccomplishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ESummaryAccomplishmentRepository extends JpaRepository<ESummaryAccomplishment, Long> {

	@Query("from ESummaryAccomplishment where e_summary_e_summary_id=:e_summary_e_summary_id and accomplishmentId=:accomplishmentId")
	ESummaryAccomplishment findESummaryByAccomplishmentId(@Param("e_summary_e_summary_id") Long e_summary_e_summary_id,
			@Param("accomplishmentId") Long accomplishmentId);

	@Query("from ESummaryAccomplishment where e_summary_e_summary_id=:e_summary_e_summary_id")
	List<ESummaryAccomplishment> findAccomplishmentbyeSummaryId(
			@Param("e_summary_e_summary_id") Long e_summary_e_summary_id);
}
