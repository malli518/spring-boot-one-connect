package org.rythmos.oneconnect.repository;

import java.util.List;

import org.rythmos.oneconnect.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Query("from Employee where id=:id")
	List<Employee> findEmployeeById(@Param("id") Long id);

	@Query("from Employee where emailId=:emailId and status=:status")
	List<Employee> findActiveEmployeeByEmailId(@Param("emailId") String emailId, @Param("status") String status);

	@Query("from Employee where emailId=:emailId")
	List<Employee> findEmployeeByEmailId(@Param("emailId") String emailId);

	@Query("from Employee where Id=:id and status=:status")
	List<Employee> findActiveEmployeeById(@Param("id") Long id, @Param("status") String status);

	@Query("from Employee where emailId=:emailId")
	Employee findEmployeeBymailId(@Param("emailId") String emailId);
}
