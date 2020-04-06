package org.rythmos.oneconnect.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.MappingException;
import org.rythmos.oneconnect.bean.CityBean;
import org.rythmos.oneconnect.bean.ClientDetailBean;
import org.rythmos.oneconnect.bean.CountryBean;
import org.rythmos.oneconnect.bean.EmployeeBean;
import org.rythmos.oneconnect.bean.EmployeeProjectMappingBean;
import org.rythmos.oneconnect.bean.LocationBean;
import org.rythmos.oneconnect.bean.PortfolioBean;
import org.rythmos.oneconnect.bean.ProjectDetailBean;
import org.rythmos.oneconnect.bean.ProjectRoleBean;
import org.rythmos.oneconnect.bean.StateBean;
import org.rythmos.oneconnect.constant.OneConnectConstants;
import org.rythmos.oneconnect.controller.NotificationController;
import org.rythmos.oneconnect.entity.City;
import org.rythmos.oneconnect.entity.ClientDetail;
import org.rythmos.oneconnect.entity.Country;
import org.rythmos.oneconnect.entity.Employee;
import org.rythmos.oneconnect.entity.EmployeeProjectMapping;
import org.rythmos.oneconnect.entity.Location;
import org.rythmos.oneconnect.entity.Portfolio;
import org.rythmos.oneconnect.entity.ProjectDetail;
import org.rythmos.oneconnect.entity.ProjectRole;
import org.rythmos.oneconnect.entity.RoleName;
import org.rythmos.oneconnect.entity.State;
import org.rythmos.oneconnect.json.request.NotificationJSONRequest;
import org.rythmos.oneconnect.json.response.BaseResponse;
import org.rythmos.oneconnect.repository.CityRepository;
import org.rythmos.oneconnect.repository.ClientDetailsRepository;
import org.rythmos.oneconnect.repository.CountryRepository;
import org.rythmos.oneconnect.repository.EmployeeProjectMappingRepository;
import org.rythmos.oneconnect.repository.EmployeeRepository;
import org.rythmos.oneconnect.repository.LocationRepository;
import org.rythmos.oneconnect.repository.PortfolioRepository;
import org.rythmos.oneconnect.repository.ProjectDetailRepository;
import org.rythmos.oneconnect.repository.ProjectRoleRepository;
import org.rythmos.oneconnect.repository.StateRepository;
import org.rythmos.oneconnect.response.ClientDetailResponse;
import org.rythmos.oneconnect.response.PortfolioResponse;
import org.rythmos.oneconnect.response.ProjectDetailResponse;
import org.rythmos.oneconnect.response.ProjectEndDateResponse;
import org.rythmos.oneconnect.response.ResourceResponse;
import org.rythmos.oneconnect.security.UserPrincipal;
import org.rythmos.oneconnect.util.DateUtil;
import org.rythmos.oneconnect.util.OneConnectDAOUtility;
import org.rythmos.oneconnect.util.OneConnectUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author Prasanth Kusumaraju
 *
 */
@Component
public class AdminDAO {

	public static Logger logger = LoggerFactory.getLogger(AdminDAO.class);

	@Autowired
	EmployeeProjectMappingRepository employeeProjectMappingRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	ClientDetailsRepository clientDetailsRepository;

	@Autowired
	PortfolioRepository portfolioRepository;

	@Autowired
	ProjectDetailRepository projectDetailRepository;

	@Autowired
	CityRepository cityRepository;

	@Autowired
	CountryRepository countryRepository;

	@Autowired
	StateRepository stateRepository;

	@Autowired
	LocationRepository locationRepository;

	@Autowired
	ProjectRoleRepository projectRoleRepository;

	@Autowired
	private BaseResponse baseResponse;

	@Autowired
	OneConnectDAOUtility oneConnectDAOUtility;

	@Autowired
	private DateUtil dateUtil;

	@Autowired
	private NotificationController notificationController;

	public Object getPrincipal() {
		return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	public List<CountryBean> getCountries() {
		List<Country> countryList = countryRepository.findAll();
		return countryList.stream().map(country -> convertToBean(country)).collect(Collectors.toList());
	}

	public List<StateBean> getStates(CountryBean countryBean) {

		List<State> stateList = stateRepository.findStateByCountryId(countryBean.getId());

		return stateList.stream().map(state -> convertToBean(state)).collect(Collectors.toList());
	}

	public List<CityBean> getCities(StateBean stateBean) {
		List<City> cityList = cityRepository.findCityByStateId(stateBean.getId());
		logger.info("cityList::{}", cityList);
		return cityList.stream().map(city -> convertToBean(city)).collect(Collectors.toList());
	}

//	private ApplicationRoleBean convertToBean(ApplicationRole applicationRole) {
//
//		ApplicationRoleBean applicationRoleBean = oneConnectDAOUtility.modelMapper.map(applicationRole,
//				ApplicationRoleBean.class);
//		return applicationRoleBean;
//
//	}

	private ProjectRoleBean convertToBean(ProjectRole projectRole) {

		ProjectRoleBean ProjectRoleBean = oneConnectDAOUtility.modelMapper.map(projectRole, ProjectRoleBean.class);
		return ProjectRoleBean;

	}

	private StateBean convertToBean(State state) {
		return oneConnectDAOUtility.modelMapper.map(state, StateBean.class);

	}

	private ClientDetailBean convertToBean(ClientDetail clientDetail) {
		return oneConnectDAOUtility.modelMapper.map(clientDetail, ClientDetailBean.class);

	}

	private CountryBean convertToBean(Country country) {
		return oneConnectDAOUtility.modelMapper.map(country, CountryBean.class);

	}

	private CityBean convertToBean(City city) {
		return oneConnectDAOUtility.modelMapper.map(city, CityBean.class);

	}

	private PortfolioBean convertToBean(Portfolio portfolio) {
		return oneConnectDAOUtility.modelMapper.map(portfolio, PortfolioBean.class);

	}

	private Portfolio convertToEntity(PortfolioBean portfolioBean) {
		return oneConnectDAOUtility.modelMapper.map(portfolioBean, Portfolio.class);

	}

	private ClientDetail convertToEntity(ClientDetailBean clientDetailBean) throws MappingException {
		return oneConnectDAOUtility.modelMapper.map(clientDetailBean, ClientDetail.class);

	}

	private ProjectDetail convertToEntity(ProjectDetailBean projectDetailBean) throws MappingException {
		return oneConnectDAOUtility.modelMapper.map(projectDetailBean, ProjectDetail.class);

	}

	private Employee convertToEntity(EmployeeBean employeeBean) throws MappingException {
		return oneConnectDAOUtility.modelMapper.map(employeeBean, Employee.class);

	}

	public ClientDetailResponse<?> saveClient(ClientDetailBean clientDetailBean) {

		ClientDetail clientDetail = convertToEntity(clientDetailBean);

		Location location = locationRepository.findLocation(clientDetail.getLocation().getCity().getId(),
				clientDetail.getLocation().getState().getId(), clientDetail.getLocation().getCountry().getId());
		logger.info("location ::", location);
		if (OneConnectUtility.isNull(location)
				&& !OneConnectUtility.isNull(clientDetail.getLocation().getCity().getId())
				&& !OneConnectUtility.isNull(clientDetail.getLocation().getState().getId())
				&& !OneConnectUtility.isNull(clientDetail.getLocation().getCountry().getId())) {

			City city = cityRepository.findCityByCityId(clientDetail.getLocation().getCity().getId());

			Location newLocation = new Location();
			newLocation.setCity(city);
			newLocation.setState(city.getState());
			newLocation.setCountry(city.getState().getCountry());
			newLocation.setLatitude("0");
			newLocation.setLongitude("0");

			if (locationRepository.save(newLocation) != null) {
				location = locationRepository.findLocation(clientDetail.getLocation().getCity().getId(),
						clientDetail.getLocation().getState().getId(), clientDetail.getLocation().getCountry().getId());
			} else {
				location = null;
			}
		}
		try {
			if (!OneConnectUtility.isNull(location)) {
				clientDetail.setLocation(location);

				if (clientDetailsRepository.save(clientDetail) != null) {
					logger.info("Client added successfully");
					baseResponse.setMessage("Client added successfully");
					return new ClientDetailResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
				} else {
					logger.info("Unable to add Client, Client might already exist!");
					baseResponse.setMessage("Unable to add Client, Client might already exist!");
					return new ClientDetailResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				logger.info("Location not found to save Client {}", clientDetailBean);
				baseResponse.setMessage("Location not found to save Client");
				return new ClientDetailResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception ex) {
			logger.info("Unable to add Client, Client might already exist!");
			baseResponse.setMessage("Unable to add Client, Client might already exist!");
			return new ClientDetailResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public LocationBean getLocationByCityID(Long cityId, Long stateId, Long countryId) {
		return oneConnectDAOUtility.modelMapper.map(locationRepository.findLocation(cityId, stateId, countryId),
				LocationBean.class);
	}

	public List<ClientDetailBean> getClientDetails() {
		List<ClientDetail> clientDetailList = clientDetailsRepository.findAll();

		return clientDetailList.stream().map(clientDetail -> convertToBean(clientDetail)).collect(Collectors.toList());
	}

	public List<ClientDetailBean> getApplicableClientDetails(Long employeeId, String role) {

		List<EmployeeProjectMapping> employeeProjectMappingList = employeeProjectMappingRepository
				.findAllActiveProjectsForEmployee(OneConnectConstants.employProjectStatus.ACTIVE.toString(),
						employeeId);
		Set<ClientDetail> clientDetailList = new HashSet<ClientDetail>();
		for (EmployeeProjectMapping employeeProjectMapping : employeeProjectMappingList) {

			clientDetailList.add(employeeProjectMapping.getProjectDetail().getPortfolio().getClientDetail());
		}
		return clientDetailList.stream().map(clientDetail -> convertToBean(clientDetail)).collect(Collectors.toList());
	}

	public ClientDetailResponse<?> updateClient(ClientDetailBean clientDetailBean) {

		ClientDetail clientDetail = clientDetailsRepository.findClientId(clientDetailBean.getId());
		logger.info("client Details are::{}", clientDetail);
		if (clientDetail != null) {
			clientDetail.setClientDescription(clientDetailBean.getClientDescription());
			clientDetail.setStatus(clientDetailBean.getStatus());
			clientDetail.setClientName(clientDetailBean.getClientName());

			Location location = locationRepository.findLocation(clientDetailBean.getLocation().getCity().getId(),
					clientDetailBean.getLocation().getState().getId(),
					clientDetailBean.getLocation().getCountry().getId());

			logger.info("Location before Check {} :: ", location);
			if (OneConnectUtility.isNull(location)
					&& !OneConnectUtility.isNull(clientDetail.getLocation().getCity().getId())
					&& !OneConnectUtility.isNull(clientDetail.getLocation().getState().getId())
					&& !OneConnectUtility.isNull(clientDetail.getLocation().getCountry().getId())) {

				logger.info(" :: Failed update ::");

				City city = cityRepository.findCityByCityId(clientDetailBean.getLocation().getCity().getId());

				Location newLocation = new Location();

				newLocation.setCity(city);
				newLocation.setState(city.getState());
				newLocation.setCountry(city.getState().getCountry());
				newLocation.setLatitude("0");
				newLocation.setLongitude("0");
				if (locationRepository.save(newLocation) != null) {
					location = locationRepository.findLocation(newLocation.getCity().getId(),
							newLocation.getState().getId(), newLocation.getCountry().getId());
				} else {
					location = null;
				}

			}
			try {
				if (!OneConnectUtility.isNull(location)) {

					clientDetail.setLocation(location);
					if (clientDetailsRepository.save(clientDetail) != null) {
						logger.info("Client updated successfully");
						baseResponse.setMessage("Client updated successfully");
						return new ClientDetailResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
					} else {
						logger.info("Unable to update Client, Client might already exist!");
						baseResponse.setMessage("Unable to update Client, Client might already exist!");
						return new ClientDetailResponse<BaseResponse>(baseResponse, null,
								HttpStatus.INTERNAL_SERVER_ERROR);
					}
				} else {
					logger.info("Location does not exist!");
					baseResponse.setMessage("Location does not exist!");
					return new ClientDetailResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
				}
			} catch (Exception ex) {
				logger.info("Unable to update Client, Client might already exist!");
				baseResponse.setMessage("Unable to update Client, Client might already exist!");
				return new ClientDetailResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			logger.info("Client does not exist!");
			baseResponse.setMessage("Client does not exist!");
			return new ClientDetailResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}

	}

	public ClientDetailResponse<?> deleteClient(ClientDetailBean clientDetailBean) {
		ClientDetail clientDetail = clientDetailsRepository.findClientId(clientDetailBean.getId());
		logger.info("Client Details found with an clientID is::{}", clientDetail);
		try {
			if (clientDetail != null) {
				clientDetailsRepository.delete(clientDetail);
				baseResponse.setMessage("Client deleted successfully");
				return new ClientDetailResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
			} else {
				logger.info("Client does not exist to Delete!");
				baseResponse.setMessage("Client does not exist to Delete!");
				return new ClientDetailResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			logger.info("Unable to delete Client!");
			baseResponse.setMessage("Unable to delete Client!");
			return new ClientDetailResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
		}
	}

	public List<PortfolioBean> getPortfolios(ClientDetailBean cientDetailBean) {
		List<Portfolio> portfolioList = portfolioRepository.findPortfolioByClientId(cientDetailBean.getId());
		return portfolioList.stream().map(portfolio -> convertToBean(portfolio)).collect(Collectors.toList());
	}

	public List<PortfolioBean> getApplicablePortfolios(ClientDetailBean cientDetailBean, Long employeeId, String role) {

		List<EmployeeProjectMapping> employeeProjectMappingList = employeeProjectMappingRepository
				.findAllActiveProjectsForEmployee(OneConnectConstants.employProjectStatus.ACTIVE.toString(),
						employeeId);
		Set<Portfolio> portfolioList = new HashSet<>();

		for (EmployeeProjectMapping employeeProjectMapping : employeeProjectMappingList) {
			if (cientDetailBean.getId()
					.equals(employeeProjectMapping.getProjectDetail().getPortfolio().getClientDetail().getId())) {
				portfolioList.add(employeeProjectMapping.getProjectDetail().getPortfolio());

			}
		}
		return portfolioList.stream().map(portfolio -> convertToBean(portfolio)).collect(Collectors.toList());
	}

	public PortfolioResponse<?> savePortfolio(PortfolioBean portfolioBean) {
		logger.info(" PortfolioJSONRequest {} ", portfolioBean);
		ClientDetail clientDetail = clientDetailsRepository.findClientId(portfolioBean.getClientDetail().getId());
		try {
			if (null != clientDetail) {
				logger.info(" Null check Pass :: ");
				Portfolio portfolio = convertToEntity(portfolioBean);
				if (null != portfolioRepository.save(portfolio)) {
					logger.info("Portfolio added successfully");
					baseResponse.setMessage("Portfolio added successfully");
					return new PortfolioResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
				} else {
					logger.info("Unable to add Portfolio, Portfolio might already exist!");
					baseResponse.setMessage("Unable to add Portfolio, Portfolio might already exist!");
					return new PortfolioResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				logger.info("Client not found to add Portfolio!{}", portfolioBean);
				baseResponse.setMessage("Client not found to add Portfolio!");
				return new PortfolioResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception ex) {
			logger.info("Unable to add Portfolio, Portfolio might already exist!");
			baseResponse.setMessage("Unable to add Portfolio, Portfolio might already exist!");
			return new PortfolioResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public PortfolioResponse<?> updatePortfolio(PortfolioBean portfolioBean) {

		logger.info(" PortfolioJSONRequest {} ", portfolioBean);
		ClientDetail clientDetail = clientDetailsRepository.findClientId(portfolioBean.getClientDetail().getId());
		Portfolio portfolioEntity = portfolioRepository.findPortfolioById(portfolioBean.getId());
		try {
			if (!OneConnectUtility.isNull(clientDetail) && !OneConnectUtility.isNull(portfolioBean.getId())
					&& !OneConnectUtility.isNull(portfolioEntity)
					&& !OneConnectUtility.isNullOrEmpty(portfolioBean.getName())) {

				logger.info(" Null check Pass :: ");

				portfolioEntity.setName(portfolioBean.getName());
				portfolioEntity.setClientDetail(clientDetail);

				if (null != portfolioRepository.save(portfolioEntity)) {
					logger.info("Portfolio updated successfully");
					baseResponse.setMessage("Portfolio updated successfully");
					return new PortfolioResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
				} else {
					logger.info("Unable to update Portfolio, Portfolio might already exist! {}");
					baseResponse.setMessage("Unable to update Portfolio, Portfolio might already exist!");
					return new PortfolioResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				logger.info("Client not found to update Portfolio!{}", portfolioBean);
				baseResponse.setMessage("Client not found to update Portfolio!");
				return new PortfolioResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception ex) {
			logger.info("Unable to update Portfolio, Portfolio might already exist! {}");
			baseResponse.setMessage("Unable to update Portfolio, Portfolio might already exist!");
			return new PortfolioResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public List<PortfolioBean> getPortfolioDetails() {
		List<Portfolio> portfolioList = portfolioRepository.findAll();
		return portfolioList.stream().map(portfolio -> convertToBean(portfolio)).collect(Collectors.toList());
	}

	public PortfolioResponse<?> deletePortfolio(PortfolioBean portfolioBean) {
		Portfolio portfolio = portfolioRepository.findPortfolioById(portfolioBean.getId());
		logger.info("Portfolio found with an Portfolio_id is::{}", portfolio);
		try {
			if (!OneConnectUtility.isNull(portfolio)) {
				portfolioRepository.delete(portfolio);
				if (OneConnectUtility.isNull(portfolioRepository.findPortfolioById(portfolioBean.getId()))) {
					baseResponse.setMessage("Portfolio deleted successfully");
					return new PortfolioResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
				} else {
					logger.info("Unable to delete Portfolio! {}", portfolioBean);
					baseResponse.setMessage("Unable to delete Portfolio!");
					return new PortfolioResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				logger.info("Portfolio does not exist! {} ", portfolioBean);
				baseResponse.setMessage("Portfolio does not exist!");
				return new PortfolioResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			logger.info("Unable to delete Portfolio! {}", portfolioBean);
			baseResponse.setMessage("Unable to delete Portfolio!");
			return new PortfolioResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ProjectDetailResponse<?> saveProject(ProjectDetailBean projectDetailBean) {
		logger.info(" ProjectDetailBean {} ", projectDetailBean);
		Portfolio portfolio = portfolioRepository.findPortfolioById(projectDetailBean.getPortfolio().getId());
		try {
			if (portfolio != null) {
				ProjectDetail project = convertToEntity(projectDetailBean);
				if (projectDetailRepository.save(project) != null) {
					logger.info("Project added successfully");
					baseResponse.setMessage("Project added successfully");
					return new ProjectDetailResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
				} else {
					logger.info("Unable to add Project, Project might already exist! {}", projectDetailBean);
					baseResponse.setMessage("Unable to add Project, Project might already exist!");
					return new ProjectDetailResponse<BaseResponse>(baseResponse, null,
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				logger.info("Client not found to add Project! {}", projectDetailBean);
				baseResponse.setMessage("Client not found to add Project!");
				return new ProjectDetailResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			logger.info("Unable to add Project, Project might already exist! {}", projectDetailBean);
			baseResponse.setMessage("Unable to add Project, Project might already exist!");
			return new ProjectDetailResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private ProjectDetailBean convertToBean(ProjectDetail project) {
		ProjectDetailBean projectDetailBean = oneConnectDAOUtility.modelMapper.map(project, ProjectDetailBean.class);
		return projectDetailBean;
	}

	public List<ProjectDetailBean> getProjectDetails() {
		List<ProjectDetail> projectList = projectDetailRepository.findAll();
		return projectList.stream().map(project -> convertToBean(project)).collect(Collectors.toList());
	}

	public List<ProjectDetailBean> getProjectDetailsByPortfolio(PortfolioBean portfolio) {
		List<ProjectDetail> projectList = projectDetailRepository.findProjectDetailByPortfolioId(portfolio.getId());
		return projectList.stream().map(project -> convertToBean(project)).collect(Collectors.toList());
	}

	public List<ProjectDetailBean> getApplicableProjectDetailsByPortfolio(PortfolioBean portfolio, Long employeeId,
			String role) {

		Set<ProjectDetail> projectList = new HashSet<>();
		logger.info("EmployeeId is ::{}", employeeId);
		List<EmployeeProjectMapping> employeeProjectMappingList = employeeProjectMappingRepository
				.findAllActiveProjectsForEmployee(OneConnectConstants.employProjectStatus.ACTIVE.toString(),
						employeeId);

		for (EmployeeProjectMapping employeeProjectMapping : employeeProjectMappingList) {
			if (portfolio.getId() == employeeProjectMapping.getProjectDetail().getPortfolio().getId()) {
				projectList.add(employeeProjectMapping.getProjectDetail());

			}
		}

		return projectList.stream().map(project -> convertToBean(project)).collect(Collectors.toList());
	}

	public ProjectDetailResponse<?> updateProject(ProjectDetailBean projectDetailBean) {
		logger.info(" ProjectDetailBean {} ", projectDetailBean);
		Portfolio portfolio = portfolioRepository.findPortfolioById(projectDetailBean.getPortfolio().getId());
		ProjectDetail projectEntity = projectDetailRepository.findProjectDetailId(projectDetailBean.getId());
		try {
			if (!OneConnectUtility.isNull(portfolio) && !OneConnectUtility.isNull(projectDetailBean.getId())
					&& !OneConnectUtility.isNull(projectEntity)
					&& !OneConnectUtility.isNullOrEmpty(projectDetailBean.getDirector())
					&& !OneConnectUtility.isNullOrEmpty(projectDetailBean.getManager())
					&& !OneConnectUtility.isNullOrEmpty(projectDetailBean.getProductOwner())
					&& !OneConnectUtility.isNull(projectDetailBean.getProjectEndDate())
					&& !OneConnectUtility.isNull(projectDetailBean.getProjectStartDate())
					&& !OneConnectUtility.isNullOrEmpty(projectDetailBean.getProjectName())
					&& !OneConnectUtility.isNullOrEmpty(projectDetailBean.getPurpose())
					&& !OneConnectUtility.isNullOrEmpty(projectDetailBean.getRythmosSM())) {
				logger.info(" Null check Pass :: ");
				projectEntity.setProjectName(projectDetailBean.getProjectName());
				projectEntity.setDirector(projectDetailBean.getDirector());
				projectEntity.setManager(projectDetailBean.getManager());
				projectEntity.setProductOwner(projectDetailBean.getProductOwner());
				projectEntity.setProjectEndDate(projectDetailBean.getProjectEndDate());
				projectEntity.setProjectStartDate(projectDetailBean.getProjectStartDate());
				projectEntity.setPurpose(projectDetailBean.getPurpose());
				projectEntity.setRythmosSM(projectDetailBean.getRythmosSM());
				projectEntity.setPortfolio(portfolio);

				if (projectDetailRepository.save(projectEntity) != null) {
					logger.info(" Project updated successfully ");
					baseResponse.setMessage("Project updated successfully");
					return new ProjectDetailResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
				} else {
					logger.info("Unable to update Project, Project might already exist! {}", projectDetailBean);
					baseResponse.setMessage("Unable to update Project, Project might already exist!");
					return new ProjectDetailResponse<BaseResponse>(baseResponse, null,
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				logger.info("Project not found {}", projectDetailBean);
				baseResponse.setMessage("Project not found");
				return new ProjectDetailResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			logger.info("Unable to update Project, Project might already exist! {}", projectDetailBean);
			baseResponse.setMessage("Unable to update Project, Project might already exist!");
			return new ProjectDetailResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ProjectDetailResponse<?> deleteProject(ProjectDetailBean projectDetailBean) {
		ProjectDetail project = projectDetailRepository.findProjectDetailId(projectDetailBean.getId());
		logger.info("Project found with an project_id is::{}", project);
		try {
			if (!OneConnectUtility.isNull(project)) {
				projectDetailRepository.delete(project);
				if (OneConnectUtility.isNull(projectDetailRepository.findProjectDetailId(projectDetailBean.getId()))) {
					logger.info("Project deleted successfully {}", projectDetailBean);
					baseResponse.setMessage("Project deleted successfully");
					return new ProjectDetailResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
				} else {
					logger.info("Unable to delete Project! {}", projectDetailBean);
					baseResponse.setMessage("Unable to delete Project!");
					return new ProjectDetailResponse<BaseResponse>(baseResponse, null,
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				logger.info("Project does not exist {} ", projectDetailBean);
				baseResponse.setMessage("Project does not exist!");
				return new ProjectDetailResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			logger.info("Unable to delete Project! {}", projectDetailBean);
			baseResponse.setMessage("Unable to delete Project!");
			return new ProjectDetailResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private EmployeeProjectMappingBean convertToBean(EmployeeProjectMapping employeeProjectMapping) {
		EmployeeProjectMappingBean employeeProjectMappingBean = oneConnectDAOUtility.modelMapper
				.map(employeeProjectMapping, EmployeeProjectMappingBean.class);
		return employeeProjectMappingBean;
	}

	private EmployeeBean convertToBean(Employee employee) {
		EmployeeBean employeeBean = oneConnectDAOUtility.modelMapper.map(employee, EmployeeBean.class);
		return employeeBean;
	}

	public List<EmployeeProjectMappingBean> getAllProjectResources() {
		List<EmployeeProjectMapping> employeeProjectMappingList = employeeProjectMappingRepository
				.findAllActiveEmployees(OneConnectConstants.employProjectStatus.ACTIVE.toString());
		logger.info("employeeProjectMappingList Size :: {} ", employeeProjectMappingList.size());
		return employeeProjectMappingList.stream().map(employeeProjectMapping -> convertToBean(employeeProjectMapping))
				.collect(Collectors.toList());
	}

	public List<EmployeeProjectMappingBean> findAllResourcesByProject(Long id) {

		List<EmployeeProjectMapping> employeeProjectMappingList = employeeProjectMappingRepository
				.findAllActiveEmployeesByProjectId(id, OneConnectConstants.employProjectStatus.ACTIVE.toString());
		logger.info("employeeProjectMappingList Size  :: {} ", employeeProjectMappingList.size());
		return employeeProjectMappingList.stream().map(employeeProjectMapping -> convertToBean(employeeProjectMapping))
				.collect(Collectors.toList());
	}

	public List<ProjectRoleBean> getAllProjectRoles() {
		List<ProjectRole> projectRoleList = projectRoleRepository.findAll();
		return projectRoleList.stream().map(projectRole -> convertToBean(projectRole)).collect(Collectors.toList());
	}

	public ResourceResponse<?> addResource(EmployeeBean employeeBean,
			List<EmployeeProjectMappingBean> employeeProjectMappingBeanList) {
		try {
			if (saveResource(employeeBean)) {
				Employee employee = employeeRepository.findEmployeeByEmailId(employeeBean.getEmailId()).get(0);
				if (mapProjectResource(employeeProjectMappingBeanList, employee).getStatusCode()
						.equals(HttpStatus.OK)) {

					logger.info("Resource and Project/Projects added successfully");
					baseResponse.setMessage("Resource and Project/Projects added successfully");
					return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);

				} else {
					logger.info("Unable to map Project/Projects to the Resource!");
					baseResponse.setMessage("Unable to map Project to the Resource!");
					return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.PARTIAL_CONTENT);
				}
			} else {
				logger.info(" Unable to add Resource, Resource might already exist!");
				baseResponse.setMessage("Unable to add Resource, Resource might already exist!");
				return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.CONFLICT);
			}
		} catch (Exception ex) {
			logger.info("Unable to map Project/Projects to the Resource!");
			baseResponse.setMessage("Unable to map Project to the Resource!");
			return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.PARTIAL_CONTENT);
		}

	}

	private ResourceResponse<?> mapProjectResource(List<EmployeeProjectMappingBean> employeeProjectMappingBeanList,
			Employee employee) {
		logger.info("Mapping Resource to Project");
		try {
			if (!OneConnectUtility.isNull(employeeProjectMappingBeanList)
					&& employeeProjectMappingBeanList.size() > 0) {
				int count = employeeProjectMappingBeanList.size();
				for (EmployeeProjectMappingBean employeeProjectMappingBean : employeeProjectMappingBeanList) {

					EmployeeBean employeeBean = convertToBean(
							employeeRepository.findEmployeeById(employee.getId()).get(0));

					employeeProjectMappingBean.setEmployee(employeeBean);
					ProjectDetail project = projectDetailRepository
							.findProjectDetailId(employeeProjectMappingBean.getProjectDetail().getId());

					ProjectDetailBean projectDetail = convertToBean(project);

					employeeProjectMappingBean.setProjectDetail(projectDetail);
					employeeProjectMappingBean.setReleaseDate(projectDetail.getProjectEndDate());
					ProjectRoleBean projectRole = convertToBean(projectRoleRepository
							.findByProjectRoleById(employeeProjectMappingBean.getProjectRole().getId()));
					employeeProjectMappingBean.setProjectRole(projectRole);

					EmployeeProjectMapping employeeProjectMapping = convertToEntity(employeeProjectMappingBean);
					List<EmployeeProjectMapping> isResouceActiveByProject = employeeProjectMappingRepository
							.findProjectActiveForEmployee(employeeProjectMappingBean.getProjectDetail().getId(),
									OneConnectConstants.employProjectStatus.ACTIVE.toString(),
									employeeProjectMappingBean.getEmployee().getId());
					if (isResouceActiveByProject.isEmpty()) {
						try {
							logger.info("Adding Project {} ", employeeProjectMapping);
							employeeProjectMappingRepository.save(employeeProjectMapping);

							Long projectId = project.getId();

							Long portfolioId = project.getPortfolio().getId();
							Long clientId = project.getPortfolio().getClientDetail().getId();

							NotificationJSONRequest notificationJSONRequest = new NotificationJSONRequest();

							notificationJSONRequest.setClientId(clientId);
							notificationJSONRequest.setPortfolioId(portfolioId);
							notificationJSONRequest.setProjectId(projectId);

							notificationJSONRequest.setReportType("Resource");
							notificationJSONRequest.setOperationType("save");
							notificationJSONRequest.setStartDate(dateUtil.getFormattedDateString(
									employeeProjectMapping.getAssignedDate(), OneConnectConstants.DEFAULT_DATE_FORMAT));
							notificationJSONRequest
									.setNotificationBody(employeeProjectMapping.getEmployee().getEmployeeName());

							notificationJSONRequest.setIsMessage(false);
							notificationJSONRequest.setMessageId(null);
							notificationJSONRequest.setRoom(null);
							notificationController.notify(notificationJSONRequest);

							count--;

						} catch (Exception e) {

							logger.info("Project was not added {} ,\n as of now {} were mapped \n Exception {}",
									employeeProjectMapping, e);
						}
					} else {
						logger.info("Duplicate Project Mapping found For the Resource", employeeProjectMapping);
					}
				}
				if (count == 0) {
					baseResponse.setMessage("Project/Projects mapped successfully to the Resource");
					return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
				} else {
					baseResponse.setMessage("Unable to map Project/Projects to the Resource!");
					return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.PARTIAL_CONTENT);
				}
			} else {
				logger.info("Empty list in the EmployeeProjectMappingBean {}", employeeProjectMappingBeanList);
				baseResponse.setMessage("Empty list in the EmployeeProjectMappingBean");
				return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception ex) {
			baseResponse.setMessage("Unable to map Project/Projects to the Resource!");
			return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.PARTIAL_CONTENT);
		}
	}

	private ResourceResponse<?> updateMapProjectResource(
			List<EmployeeProjectMappingBean> employeeProjectMappingBeanList, Employee employee) {
		try {
			if (!OneConnectUtility.isNull(employeeProjectMappingBeanList)
					&& employeeProjectMappingBeanList.size() > 0) {
				int count = employeeProjectMappingBeanList.size();
				EmployeeBean employeeBean = convertToBean(employeeRepository.findEmployeeById(employee.getId()).get(0));

				for (EmployeeProjectMappingBean employeeProjectMappingBean : employeeProjectMappingBeanList) {

					employeeProjectMappingBean.setEmployee(employeeBean);

					ProjectDetailBean projectDetail = convertToBean(projectDetailRepository
							.findProjectDetailId(employeeProjectMappingBean.getProjectDetail().getId()));

					employeeProjectMappingBean.setProjectDetail(projectDetail);

					ProjectRoleBean projectRole = convertToBean(projectRoleRepository
							.findByProjectRoleById(employeeProjectMappingBean.getProjectRole().getId()));

					employeeProjectMappingBean.setProjectRole(projectRole);

					EmployeeProjectMapping employeeProjectMapping = convertToEntity(employeeProjectMappingBean);

					List<EmployeeProjectMapping> isResouceActiveByProject = employeeProjectMappingRepository
							.findProjectActiveForEmployee(employeeProjectMappingBean.getProjectDetail().getId(),
									OneConnectConstants.employProjectStatus.ACTIVE.toString(),
									employeeProjectMappingBean.getEmployee().getId());

					if (isResouceActiveByProject.size() == 1) {
						try {
							employeeProjectMappingRepository.save(employeeProjectMapping);
							Long projectId = projectDetail.getId();
							Long portfolioId = projectDetail.getPortfolio().getId();
							Long clientId = projectDetail.getPortfolio().getClientDetail().getId();

							NotificationJSONRequest notificationJSONRequest = new NotificationJSONRequest();

							notificationJSONRequest.setClientId(clientId);
							notificationJSONRequest.setPortfolioId(portfolioId);
							notificationJSONRequest.setProjectId(projectId);
							notificationJSONRequest.setReportType("Resource");
							notificationJSONRequest.setOperationType("update");
							notificationJSONRequest.setStartDate(dateUtil.getFormattedDateString(
									employeeProjectMapping.getAssignedDate(), OneConnectConstants.DEFAULT_DATE_FORMAT));
							notificationJSONRequest
									.setNotificationBody(employeeProjectMapping.getEmployee().getEmployeeName());

							notificationJSONRequest.setIsMessage(false);
							notificationJSONRequest.setMessageId(null);
							notificationJSONRequest.setRoom(null);
							notificationController.notify(notificationJSONRequest);
							count--;

						} catch (Exception e) {

							logger.info("Project was not added {} ,\n as of now {} were mapped \n Exception {}",
									employeeProjectMapping, e);

							baseResponse.setMessage("Project was not added");
							return new ResourceResponse<BaseResponse>(baseResponse, null,
									HttpStatus.EXPECTATION_FAILED);
						}
					} else {
						logger.info("No Project Mapping found For the Resource To Update", employeeProjectMapping);
					}
				}
				if (count == 0) {
					logger.info("Project/Projects updated successfully to the Resource");
					baseResponse.setMessage("Project updated successfully to the Resource");
					return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
				} else {
					logger.info("Unable to update Project to the Resource!");
					baseResponse.setMessage("Unable to update  Project to the Resource!");
					return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.PARTIAL_CONTENT);
				}
			} else {
				logger.info("No Project/Projects found to map ! {} ", employeeProjectMappingBeanList);
				baseResponse.setMessage("No Project/Projects found to map ! ");
				return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception ex) {
			logger.info("Unable to update Project to the Resource!");
			baseResponse.setMessage("Unable to update  Project to the Resource!");
			return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.PARTIAL_CONTENT);
		}
	}

	private EmployeeProjectMapping convertToEntity(EmployeeProjectMappingBean employeeProjectMappingBean) {
		EmployeeProjectMapping employeeProjectMapping = oneConnectDAOUtility.modelMapper.map(employeeProjectMappingBean,
				EmployeeProjectMapping.class);
		return employeeProjectMapping;
	}

	private Boolean saveResource(EmployeeBean employeeBean) {
		if (null != employeeBean && !OneConnectUtility.isNullOrEmpty(employeeBean.getEmailId())
				&& !OneConnectUtility.isNullOrEmpty(employeeBean.getEmployeeId())
				&& !OneConnectUtility.isNullOrEmpty(employeeBean.getEmployeeName())) {

			try {
				Employee employee = convertToEntity(employeeBean);
				if ((employeeRepository.findEmployeeById(employeeBean.getId()).size() == 0
						&& employeeRepository.findEmployeeByEmailId(employeeBean.getEmailId()).size() == 0)
						&& null != employeeRepository.save(employee)) {

					logger.info("EmployeeBean got created Successfully");
					return true;
				} else {
					logger.info("The Resource you are trying to save is already Exists");
					return false;
				}
			} catch (Exception e) {
				logger.info("Exception occurred  the EmployeeBean {},exception :: {}", employeeBean, e);
				return false;
			}
		} else {
			logger.info("NULL values in the EmployeeBean {}", employeeBean);
			return false;
		}

	}

	private boolean emailSafeCheck(EmployeeBean employeeBean) {

		List<Employee> employeeList = employeeRepository.findEmployeeByEmailId(employeeBean.getEmailId());

		if (employeeList.size() == 1 && employeeList.get(0).getId() == employeeBean.getId()) {
			return true;
		}
		if (employeeList.size() == 0) {
			return true;
		}
		return false;

	}

	public ResourceResponse<?> updateResource(EmployeeBean employeeBean) {
		if (null != employeeBean && !OneConnectUtility.isNullOrEmpty(employeeBean.getEmailId())
				&& !OneConnectUtility.isNullOrEmpty(employeeBean.getEmployeeId())
				&& !OneConnectUtility.isNullOrEmpty(employeeBean.getEmployeeName()) && employeeBean.getId() > 0) {

			try {
				Employee employee = employeeRepository.findEmployeeById(employeeBean.getId()).get(0);

				employee.setEmailId(employeeBean.getEmailId());
				employee.setEmployeeId(employeeBean.getEmployeeId());
				employee.setEmployeeName(employeeBean.getEmployeeName());
				employee.setLastModifiedDate(new Date());

				if (employeeRepository.findEmployeeById(employeeBean.getId()).size() == 1
						&& emailSafeCheck(employeeBean) && null != employeeRepository.save(employee)) {

					logger.info("Resource updated successfully");
					baseResponse.setMessage("Resource updated successfully");
					return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);

				} else {
					logger.info(
							"The Resource you are trying to Update does not Exist or Email Id might already Exist! {}",
							employee);
					baseResponse.setMessage("Unable to update Resource, Email might already exist!");
					return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);

				}
			} catch (Exception e) {
				logger.info("Exception occurred while updating the Employee! {},exception :: {}", employeeBean, e);
				baseResponse.setMessage("Unable to update Resource, Email might already exist!");
				return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);

			}
		} else {
			logger.info("EmailId,EmployeeId and EmployeeName cannot be null :: {}", employeeBean);
			baseResponse.setMessage("EmailId,EmployeeId and EmployeeName cannot be null");
			return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);

		}

	}

	public ResourceResponse<?> deleteResource(EmployeeBean employeeBean) {

		// find all active projects for that employee
		// update status to IN-Active and update relieving date once All Projects are
		// done then
		// update employee status to inactive

		List<Employee> employeeList = employeeRepository.findActiveEmployeeById(employeeBean.getId(),
				OneConnectConstants.employStatus.ACTIVE.toString());
		if (null != employeeList && employeeList.size() == 1) {
			Employee employee = employeeList.get(0);
			List<EmployeeProjectMapping> employeeProjectMappingList = employeeProjectMappingRepository
					.findAllActiveProjectsForEmployee(OneConnectConstants.employProjectStatus.ACTIVE.toString(),
							employee.getId());
			int activeProjectsCount = employeeProjectMappingList.size();
			int deletedProjectsCount = deleteEmployeeProject(employeeProjectMappingList);

			if (activeProjectsCount == deletedProjectsCount) {
				logger.info("Deleted All Projects Associated To The Employee");
				employee.setLastModifiedDate(new Date());
				employee.setStatus(OneConnectConstants.employStatus.INACTIVE.toString());
				if (null != employeeRepository.save(employee)) {

					logger.info("Resource deleted successfully", employee);
					baseResponse.setMessage("Resource deleted successfully");
					return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);

				} else {
					logger.info("Unable to delete Resource!", employee);
					baseResponse.setMessage("Unable to delete Resource!");
					return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);

				}

			} else {
				logger.info("Unable To delete all Projects associated to the Resource!");
				baseResponse.setMessage("Unable To delete all Projects associated to the Resource!");
				return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
			}

		} else {
			logger.info("Resource does not exist!", employeeBean);
			baseResponse.setMessage("Resource does not exist!");
			return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
		}

	}

	private int deleteEmployeeProject(List<EmployeeProjectMapping> employeeProjectMappingList) {

		int deletedProjectsCount = 0;

		for (EmployeeProjectMapping employeeProjectMapping : employeeProjectMappingList) {
			employeeProjectMapping.setStatus(OneConnectConstants.employProjectStatus.INACTIVE.toString());
			employeeProjectMapping.setReleaseDate(new Date());
			employeeProjectMapping.setLastModifiedDate(new Date());
			if (null != employeeProjectMappingRepository.save(employeeProjectMapping)) {

				ProjectDetailBean projectDetail = convertToBean(
						projectDetailRepository.findProjectDetailId(employeeProjectMapping.getProjectDetail().getId()));
				Long projectId = projectDetail.getId();
				Long portfolioId = projectDetail.getPortfolio().getId();
				Long clientId = projectDetail.getPortfolio().getClientDetail().getId();

				NotificationJSONRequest notificationJSONRequest = new NotificationJSONRequest();

				notificationJSONRequest.setClientId(clientId);
				notificationJSONRequest.setPortfolioId(portfolioId);
				notificationJSONRequest.setProjectId(projectId);

				notificationJSONRequest.setReportType("Resource");
				notificationJSONRequest.setOperationType("delete");
				notificationJSONRequest.setStartDate(dateUtil.getFormattedDateString(
						employeeProjectMapping.getAssignedDate(), OneConnectConstants.DEFAULT_DATE_FORMAT));
				notificationJSONRequest.setNotificationBody(employeeProjectMapping.getEmployee().getEmployeeName());

				notificationJSONRequest.setIsMessage(false);
				notificationJSONRequest.setMessageId(null);
				notificationJSONRequest.setRoom(null);
				notificationController.notify(notificationJSONRequest);
				deletedProjectsCount++;
			}
		}
		return deletedProjectsCount;

	}

	public ResourceResponse<?> deleteEmployesProject(EmployeeBean employeeBean,
			List<EmployeeProjectMappingBean> employeeProjectMappingBeanList) {

		if (null != employeeBean && employeeBean.getId() > 0 && null != employeeProjectMappingBeanList) {

			Employee employee = employeeRepository
					.findActiveEmployeeById(employeeBean.getId(), OneConnectConstants.employStatus.ACTIVE.toString())
					.get(0);
			int activeProjectsCount = employeeProjectMappingBeanList.size();
			int deletedProjectsCount = 0;

			for (EmployeeProjectMappingBean employeeProjectMappingBean : employeeProjectMappingBeanList) {

				List<EmployeeProjectMapping> employeeProjectMappingList = employeeProjectMappingRepository
						.findProjectActiveForEmployee(employeeProjectMappingBean.getProjectDetail().getId(),
								OneConnectConstants.employProjectStatus.ACTIVE.toString(), employee.getId());

				if (deleteEmployeeProject(employeeProjectMappingList) == 1) {
					deletedProjectsCount++;
				}
			}

			if (activeProjectsCount == deletedProjectsCount) {
				logger.info("Project/Projects deleted successfully");
				baseResponse.setMessage("Project deleted successfully");
				return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
			} else {
				logger.info("Unable to delete Project/Projects!");
				baseResponse.setMessage("Unable to delete Project!");
				return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
			}
		} else {
			logger.info("Resources/Projects are Empty!");
			baseResponse.setMessage("Resources/Projects are Empty!");
			return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
		}

	}

	public ResourceResponse<?> addProjestsToExistingResource(EmployeeBean employeeBean,
			List<EmployeeProjectMappingBean> employeeProjectMappingBeanList) {

		logger.info("addProjestsToExistingResource::{}", employeeProjectMappingBeanList);
		List<Employee> employeeList = employeeRepository.findActiveEmployeeById(employeeBean.getId(),
				OneConnectConstants.employStatus.ACTIVE.toString());
		if (null != employeeList && employeeList.size() > 0) {
			Employee employee = employeeList.get(0);

			List<EmployeeProjectMappingBean> dupEmployeeProjectMappingBeanList = new ArrayList<EmployeeProjectMappingBean>();
			dupEmployeeProjectMappingBeanList.addAll(employeeProjectMappingBeanList);

			List<EmployeeProjectMapping> employeeProjectMappingList = employeeProjectMappingRepository
					.findAllActiveProjectsForEmployee(OneConnectConstants.employProjectStatus.ACTIVE.toString(),
							employeeBean.getId());
			for (EmployeeProjectMapping employeeProjectMapping : employeeProjectMappingList) {
				dupEmployeeProjectMappingBeanList.add(convertToBean(employeeProjectMapping));
			}

			ResourceResponse<?> duplicateCheck = checkDuplicatesInProjectList(dupEmployeeProjectMappingBeanList);
			try {
				if (duplicateCheck.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
					ResourceResponse<?> utilizationCheck = checkProjectUtilization(dupEmployeeProjectMappingBeanList);
					if (utilizationCheck.getStatusCode().equals(HttpStatus.OK)) {
						if (mapProjectResource(employeeProjectMappingBeanList, employee).getStatusCode()
								.equals(HttpStatus.OK)) {

							logger.info("Project/Projects mapped successfully to the Resource");
							baseResponse.setMessage("Project/Projects mapped successfully to the Resource");

							return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);

						} else {
							logger.info("Unable to map Project/Projects to the Resource! ");
							baseResponse.setMessage("Unable to map Project/Projects/Projects to the Resource! ");
							return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.PARTIAL_CONTENT);

						}
					} else {
						return utilizationCheck;
					}
				} else {
					return duplicateCheck;

				}
			} catch (Exception ex) {
				logger.info(" Unable to map Project/Projects to the Resource! ");
				baseResponse.setMessage("Unable to map Project/Projects to the Resource! ");
				return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.PARTIAL_CONTENT);
			}

		} else {
			logger.info(" Unable to find Resource to assign Project/Projects! ");
			baseResponse.setMessage("Unable to find Resource to assign Project/Projects!");
			return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
		}

	}

	private ResourceResponse<?> checkDuplicatesInProjectList(
			List<EmployeeProjectMappingBean> employeeProjectMappingBeanList) {
		logger.info("Checking Duplicates in project List");

		// Returns true if Duplicate Project is found

		final Set<Long> nonDuplicateProjectId = new HashSet<>();
		for (EmployeeProjectMappingBean employeeProjectMappingBean : employeeProjectMappingBeanList) {

			if (!nonDuplicateProjectId.add(employeeProjectMappingBean.getProjectDetail().getId())) {
				logger.info("Duplicate entry found for the Project! {}", employeeProjectMappingBean.getProjectDetail());
				baseResponse.setMessage("Duplicate Project "
						+ employeeProjectMappingBean.getProjectDetail().getProjectName() + " found for the Resource!");
				return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.CONFLICT);
			}
		}
		logger.info("No Duplicate Project Found");
		baseResponse.setMessage("No Duplicate Project Found");
		return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
	}

	private ResourceResponse<?> checkProjectUtilization(
			List<EmployeeProjectMappingBean> employeeProjectMappingBeanList) {

		// Returns TRUE if Project utilization all Together is less than or equals to
		// 100
		// Returns FALSE if Project utilization is 0 (or) null (or) Project utilization
		// all Together is greater than or equals to 100

		float totalProjectUtilization = 0;
		logger.info("Project Utilization Check");
		for (EmployeeProjectMappingBean employeeProjectMappingBean : employeeProjectMappingBeanList) {
			if (null != employeeProjectMappingBean.getUtilization()
					&& Float.parseFloat(employeeProjectMappingBean.getUtilization()) > 0) {

				totalProjectUtilization += Float.parseFloat(employeeProjectMappingBean.getUtilization());
			} else {
				logger.info("Utilization of Project {} is  0!"
						+ employeeProjectMappingBean.getProjectDetail().getProjectName());
				baseResponse.setMessage("Utilization of Project is 0!"
						+ employeeProjectMappingBean.getProjectDetail().getProjectName() + " is Null / 0");
				return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
			}

		}

		if (totalProjectUtilization <= 100.00) {
			logger.info("Project utilization Check Pass");
			baseResponse.setMessage("Project utilization Check Pass ");
			return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);

		} else {
			logger.info("Project Utilization Check Failed Total Utilization is {} ,it should be Less Than 100!",
					totalProjectUtilization);
			baseResponse.setMessage("Utilization might be exceeding, it should be less than 100!");
			return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
		}

	}

	public ResourceResponse<?> updateProjectForExistingResource(EmployeeBean employeeBean,
			List<EmployeeProjectMappingBean> employeeProjectMappingBeanList) {

		List<Employee> employeeList = employeeRepository.findEmployeeById(employeeBean.getId());

		if (null != employeeList && employeeList.size() == 1) {
			Employee employee = employeeList.get(0);
			List<EmployeeProjectMappingBean> dupEmployeeProjectMappingBeanList = employeeProjectMappingBeanList;

			float totalCountBeforeUpdate = 0;
			float onlyPojectsUtilizationInDB = 0;
			float onlyPojectsUtilizationInReq = 0;
			float totalCountAfterUpdate = 0;

			List<EmployeeProjectMapping> employeeProjectMappingList = employeeProjectMappingRepository
					.findAllActiveProjectsForEmployee(OneConnectConstants.employProjectStatus.ACTIVE.toString(),
							employeeBean.getId());

			for (EmployeeProjectMapping employeeProjectMapping : employeeProjectMappingList) {
				totalCountBeforeUpdate += Float.parseFloat(employeeProjectMapping.getUtilization());
			}

			for (EmployeeProjectMappingBean employeeProjectMappingBean : employeeProjectMappingBeanList) {
				if (employeeProjectMappingBean.getId() > 0) {
					onlyPojectsUtilizationInDB += Float.parseFloat(employeeProjectMappingRepository
							.findEmployeeProjectMappingById(employeeProjectMappingBean.getId()).get(0)
							.getUtilization());
				} else {
					logger.info(" Project you are trying to update does not Exist!");
					baseResponse.setMessage(" Project you are trying to update does not Exist!");
					return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
				}
				onlyPojectsUtilizationInReq += Float.parseFloat(employeeProjectMappingBean.getUtilization());
			}

			totalCountAfterUpdate = totalCountBeforeUpdate - onlyPojectsUtilizationInDB + onlyPojectsUtilizationInReq;

			if (totalCountAfterUpdate <= 100) {
				ResourceResponse<?> duplicateCheck = checkDuplicatesInProjectList(employeeProjectMappingBeanList);
				try {
					if (duplicateCheck.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
						ResourceResponse<?> utilizationCheck = checkProjectUtilization(
								dupEmployeeProjectMappingBeanList);
						if (utilizationCheck.getStatusCode().equals(HttpStatus.OK)) {
							if (updateMapProjectResource(employeeProjectMappingBeanList, employee).getStatusCode()
									.equals(HttpStatus.OK)) {

								logger.info("Project updated successfully to the Resource");
								baseResponse.setMessage("Project updated successfully to the Resource");
								return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);

							} else {
								logger.info("Unable to update Project to the Resource!");
								baseResponse.setMessage("Unable to update Project to the Resource!");
								return new ResourceResponse<BaseResponse>(baseResponse, null,
										HttpStatus.PARTIAL_CONTENT);

							}
						} else {
							return utilizationCheck;
						}
					} else {
						return duplicateCheck;

					}
				} catch (Exception ex) {
					logger.info("Unable to update Project to the Resource!");
					baseResponse.setMessage("Unable to update Project to the Resource!");
					return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.PARTIAL_CONTENT);
				}
			} else {
				logger.info(" Project utilization exceded for the Resource");
				baseResponse.setMessage("Project utilization exceded for the Resource");
				return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);

			}
		} else {
			logger.info(" Unable to find Resource to assign/update to Project/Projects! ");
			baseResponse.setMessage(" Unable to find Resource to assign/update to Project/Projects!");
			return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
		}

	}

	public ProjectEndDateResponse<?> getProjectEndDateAlert() {

		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 30);
		Date modifiedDate = cal.getTime();

		List<ProjectDetail> projectdetailList = projectDetailRepository
				.findProjectEndDates(new java.sql.Date(modifiedDate.getTime()), new java.sql.Date(date.getTime()));
        List<String> projectEndAlertList1=new ArrayList<>();
		Object principal = getPrincipal();
		logger.info("Role is::{}",((UserPrincipal) principal).getAuthorities().iterator().next().toString());
		if (principal instanceof UserPrincipal && (((UserPrincipal) principal).getAuthorities().iterator().next()
				.toString().equals(RoleName.ROLE_ADMIN.toString())
				|| ((UserPrincipal) principal).getAuthorities().iterator().next().toString()
						.equals(RoleName.ROLE_EXECUTIVE.toString()))) {
			for(ProjectDetail projectdetail:projectdetailList) {
				String endDate=dateUtil.getFormattedDateString(projectdetail.getProjectEndDate(), OneConnectConstants.DEFAULT_DATE_FORMAT);
				long daysleft=dateUtil.getDaysDifference(projectdetail.getProjectEndDate(), new Date());
				baseResponse.setMessage("The project "+projectdetail.getProjectName()+" will end in "+daysleft+" days on "+endDate);
				projectEndAlertList1.add(baseResponse.getMessage());
				
			}
			return new ProjectEndDateResponse<>(projectEndAlertList1, null, HttpStatus.OK);
			
		} else {
			Long employeeId = ((UserPrincipal) principal).getEmployee().getId();
			List<ProjectDetail> projectList = employeeProjectMappingRepository.findProjectsByEmpId(employeeId);
			  List<String> projectEndAlertList2=new ArrayList<>();
			List<Long> projectIdList = new ArrayList<>();
			for (ProjectDetail projectDetail : projectList) {
				Long id = projectDetail.getId();
				projectIdList.add(id);
			}
			List<ProjectDetail> applicableProjectsList = new ArrayList<ProjectDetail>();
			for (ProjectDetail projectDetail : projectdetailList) {
				if (projectIdList.contains(projectDetail.getId())) {
					applicableProjectsList.add(projectDetail);
				}
			}
			for(ProjectDetail projectdetail:applicableProjectsList) {
				String endDate=dateUtil.getFormattedDateString(projectdetail.getProjectEndDate(), OneConnectConstants.DEFAULT_DATE_FORMAT);
				long daysleft=dateUtil.getDaysDifference(projectdetail.getProjectEndDate(), new Date());
				baseResponse.setMessage("The project "+projectdetail.getProjectName()+" will end in "+daysleft+" days on "+endDate);
				projectEndAlertList2.add(baseResponse.getMessage());
				
			}
			return new ProjectEndDateResponse<>(projectEndAlertList2, null, HttpStatus.OK);
		}
	}

}
