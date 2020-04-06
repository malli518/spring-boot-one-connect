package org.rythmos.oneconnect.repository;

import java.util.List;

import org.rythmos.oneconnect.entity.EmployeeProjectMapping;
import org.rythmos.oneconnect.entity.ProjectDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeProjectMappingRepository extends JpaRepository<EmployeeProjectMapping, Long> {

	@Query("from EmployeeProjectMapping where project_detail_id=:projectDetailId and status=:status")
	List<EmployeeProjectMapping> findAllActiveEmployeesByProjectId(@Param("projectDetailId") long projectDetailId,
			@Param("status") String status);

	@Query("from EmployeeProjectMapping where status=:status order by employee_id")
	List<EmployeeProjectMapping> findAllActiveEmployees(@Param("status") String status);

	@Query("from EmployeeProjectMapping where project_detail_id=:projectDetailId and status=:status and employee_id=:emp_id")
	List<EmployeeProjectMapping> findProjectActiveForEmployee(@Param("projectDetailId") long projectDetailId,
			@Param("status") String status, @Param("emp_id") long emp_id);

	@Query("from EmployeeProjectMapping where status=:status and employee_id=:emp_id")
	List<EmployeeProjectMapping> findAllActiveProjectsForEmployee(@Param("status") String status,
			@Param("emp_id") long emp_id);

	@Query("from EmployeeProjectMapping where id=:id")
	List<EmployeeProjectMapping> findEmployeeProjectMappingById(@Param("id") Long id);

	@Query("from EmployeeProjectMapping where status=:status and employee_id=:employee_id")
	List<EmployeeProjectMapping> findActiveEmployeeProjectByEmployeeId(@Param("status") String status,
			@Param("employee_id") Long employee_id);
	
	@Query("select projectDetail from EmployeeProjectMapping where employee_id=:employee_id")
	List<ProjectDetail> findProjectsByEmpId(@Param("employee_id") Long employee_id);

}
