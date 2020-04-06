package org.rythmos.oneconnect.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.rythmos.oneconnect.bean.ClientDetailBean;
import org.rythmos.oneconnect.bean.PortfolioBean;
import org.rythmos.oneconnect.bean.ProjectDetailBean;
import org.rythmos.oneconnect.constant.OneConnectConstants;
import org.rythmos.oneconnect.entity.ClientDetail;
import org.rythmos.oneconnect.entity.EmployeeProjectMapping;
import org.rythmos.oneconnect.entity.Portfolio;
import org.rythmos.oneconnect.entity.ProjectDetail;
import org.rythmos.oneconnect.entity.Role;
import org.rythmos.oneconnect.entity.RoleName;
import org.rythmos.oneconnect.entity.User;
import org.rythmos.oneconnect.exception.OneConnectDBException;
import org.rythmos.oneconnect.repository.ClientDetailsRepository;
import org.rythmos.oneconnect.repository.ESummaryRepository;
import org.rythmos.oneconnect.repository.EmployeeProjectMappingRepository;
import org.rythmos.oneconnect.repository.PortfolioRepository;
import org.rythmos.oneconnect.repository.ProjectDetailRepository;
import org.rythmos.oneconnect.repository.ProjectRoleRepository;
import org.rythmos.oneconnect.repository.RoleRepository;
import org.rythmos.oneconnect.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

/**
 * @author Prasanth Kusumaraju
 *
 */
@Component
public class OneConnectServiceUtility {
	private static Logger logger = LoggerFactory.getLogger(OneConnectServiceUtility.class);

	@Autowired
	private ProjectDetailRepository projectDetailRepository;
	@Autowired
	private EmployeeProjectMappingRepository employeeProjectMappingRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProjectRoleRepository projectRoleRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ClientDetailsRepository clientDetailsRepository;
	@Autowired
	private PortfolioRepository portfolioRepository;
	
	@Autowired
	private ESummaryRepository eSummaryRepository;
	
	@Autowired
	private OneConnectDAOUtility oneConnectDAOUtility;

	public String generateProjectId(String clientId) {

		int project_count = projectDetailRepository.findProjectByClientId(clientId).size();
		String projectID = clientId + "_" + ++project_count;
		logger.info("Generated Project_ID :: {}", projectID);
		return projectID;
	}

	public boolean checkProjectScope(Long projectId, Date date) throws OneConnectDBException {
		try {
			ProjectDetail project = projectDetailRepository.findProjectDetailId(projectId);
			logger.info("Project Scope :: {} :: {}", project.getProjectStartDate(), project.getProjectEndDate());
			Date projectStartDate = project.getProjectStartDate();
			Date projectEndDate = project.getProjectEndDate();
			return OneConnectUtility.compareDate(date, projectStartDate)
					&& OneConnectUtility.compareDate(projectEndDate, date);
		} catch (DataIntegrityViolationException die) {
			logger.error("Unable to define Project Scope::{}");
			throw new OneConnectDBException("Unable to define Project Scope");
		} catch (DataAccessException dae) {
			logger.error("Database Connection lost! {}");
			throw new OneConnectDBException("Database Connection lost!");
		} catch (Exception e) {
			logger.error("Unable to get data!");
			throw new OneConnectDBException("Unable to get data!");
		}
	}

	public boolean checkResourceProjectScope(Long resourceProjectId, Date date) throws OneConnectDBException {
		try {
			List<EmployeeProjectMapping> projects = employeeProjectMappingRepository
					.findEmployeeProjectMappingById(resourceProjectId);
			logger.info("Resource Project Scope ::Resource Assined Date {} :: Request Date{}",
					projects.get(0).getAssignedDate(), date);
			if (OneConnectUtility.compareDate(date, projects.get(0).getAssignedDate())) {
				if (null != projects.get(0).getReleaseDate()) {
					logger.info("Resource Project Scope :: {} ",
							OneConnectUtility.compareDate(projects.get(0).getReleaseDate(), date));
					return OneConnectUtility.compareDate(projects.get(0).getReleaseDate(), date);

				} else {
					logger.info("Project Scope :: TRUE ");
					return true;
				}
			} else {
				logger.info("Project Scope :: FALSE ");
				return false;
			}
		} catch (DataIntegrityViolationException die) {
			logger.error("Unable to define Resource Scope::{}");
			throw new OneConnectDBException("Unable to define Project Scope");
		} catch (DataAccessException dae) {
			logger.error("Database Connection lost! {}");
			throw new OneConnectDBException("Database Connection lost!");
		} catch (Exception e) {
			logger.error("Unable to get data!");
			throw new OneConnectDBException("Unable to get data!");
		}

	}

	public boolean userRoleCheck(String emailId, Long empId) {

		Optional<User> optionalUser = userRepository.findByEmail(emailId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			try {

				String maxRole = projectRoleRepository
						.findMaxRoleByEmployeeId(OneConnectConstants.employProjectStatus.ACTIVE.toString(), empId);
				if (null != maxRole && !maxRole.equals("")) {
					logger.info("Employee max Role {}", maxRole);

					RoleName roleName = RoleName.valueOf(maxRole);
					Optional<Role> role = roleRepository.findByName(roleName);

					Set<Role> roles = new HashSet<>();
					roles.add(role.get());
					user.setRoles(roles);
					try {
						userRepository.save(user);
						logger.info("User Role Updated Successfully");
						return true;
					} catch (Exception e) {
						logger.info("unable to Update User Role!");
						return false;
					}

				}
				throw new IllegalArgumentException("No Max Role Found for Employee");
			} catch (IllegalArgumentException ex) {
				try {
					userRepository.delete(user);
					logger.info("User Deleted from User Table");
					return true;
				} catch (Exception e) {
					logger.error("unable to delete User from User Table");
					return false;

				}
			}
		} else {
			return true;
		}

	}

	public List<Long> getUsersByProject(Long projectId) {
		List<Long> usersList = userRepository.findUsersByProjectId(projectId);
		if (!usersList.isEmpty()) {
			logger.info("List of users mapped to the project are::{}", usersList);
			return usersList;
		} else {
			logger.info("No User mapped to the respective Project");
			return new ArrayList<>();
		}

	}

	public List<Long> getAdminList() {
		List<Long> usersList = userRepository.findAllAdmins();
		if (!usersList.isEmpty()) {
			logger.info("List of Admin's are::{}", usersList);
			return usersList;
		} else {
			logger.info("No Admin's found");

			return new ArrayList<>();
		}

	}

	public List<Long> getExecetiveList() {
		List<Long> usersList = userRepository.findAllExecutives();
		if (!usersList.isEmpty()) {
			logger.info("List of Execetive's are::{}", usersList);
			return usersList;
		} else {
			logger.info("No Execetive's found");
			return new ArrayList<>();
		}
	}
	
	
	
	public Date getLatestreportdate(Long project_id) {
		Date reportDate=eSummaryRepository.findRiskByLatestDate(project_id);
	
		return reportDate;
	}

	public ClientDetailBean getClientById(Long id) {
		return convertToBean(clientDetailsRepository.findClientId(id));

	}

	public ClientDetailBean convertToBean(ClientDetail clientDetail) {
		return oneConnectDAOUtility.modelMapper.map(clientDetail, ClientDetailBean.class);
	}

	public PortfolioBean getPortfolioById(Long id) {
		return convertToBean(portfolioRepository.findPortfolioById(id));

	}

	public PortfolioBean convertToBean(Portfolio portfolio) {
		return oneConnectDAOUtility.modelMapper.map(portfolio, PortfolioBean.class);
	}

	public ProjectDetailBean getProjectDetailById(Long id) {
		return convertToBean(projectDetailRepository.findProjectDetailId(id));

	}

	public ProjectDetailBean convertToBean(ProjectDetail projectDetail) {
		return oneConnectDAOUtility.modelMapper.map(projectDetail, ProjectDetailBean.class);
	}

}
