package org.rythmos.oneconnect.repository;

import org.rythmos.oneconnect.entity.ProjectRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRoleRepository extends JpaRepository<ProjectRole, Long> {

	@Query("from ProjectRole where Id=:id")
	ProjectRole findByProjectRoleById(@Param("id") Long id);

	@Query("select name from ProjectRole where id in (select max(projectRole) from EmployeeProjectMapping where employee_id=:employee_id and status=:status)")
	String findMaxRoleByEmployeeId(@Param("status") String status, @Param("employee_id") Long employee_id);

}
