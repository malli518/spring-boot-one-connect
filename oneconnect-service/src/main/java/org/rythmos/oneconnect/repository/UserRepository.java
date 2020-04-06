package org.rythmos.oneconnect.repository;

import java.util.List;
import java.util.Optional;

import org.rythmos.oneconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Prasanth Kusumaraju
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	Optional<User> findByUsernameOrEmail(String username, String email);

	List<User> findByIdIn(List<Long> userIds);

	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	@Query("from User where id=:id")
	User findUserById(@Param("id") Long id);

	@Query("select id from User where username=:username")
	Optional<Long> findIDByUserName(@Param("username") String username);

	@Query("select email from User where username=:username")
	Optional<String> findEmailByUserName(@Param("username") String username);

	@Query("select id from User where employee_id in (select  employee from EmployeeProjectMapping where project_detail_id=:project_detail_id )")
	List<Long> findUsersByProjectId(@Param("project_detail_id") Long project_detail_id);
	
	@Query("select username from User where employee_id in (select  employee from EmployeeProjectMapping where project_detail_id=:project_detail_id )")
	List<String> findUsernameByProjectId(@Param("project_detail_id") Long project_detail_id);

	@Query("select id from User where admin=TRUE")
	List<Long> findAllAdmins();

	@Query("select id from User where executive=TRUE")
	List<Long> findAllExecutives();
}
