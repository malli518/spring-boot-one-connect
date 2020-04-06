package org.rythmos.oneconnect.repository;

import org.rythmos.oneconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeAuthenticationRespository extends JpaRepository<User, Long> {
	@Query("from User where id=:id")
	User findByEmpId(@Param("id") Long id);
}
