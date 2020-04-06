package org.rythmos.oneconnect.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

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
import org.rythmos.oneconnect.response.ProjectEndDateResponse;
import org.rythmos.oneconnect.dao.AdminDAO;
import org.rythmos.oneconnect.entity.RoleName;
import org.rythmos.oneconnect.json.request.ClientDetailJSONRequest;
import org.rythmos.oneconnect.json.request.CountryJSONRequest;
import org.rythmos.oneconnect.json.request.EmailJSONRequest;
import org.rythmos.oneconnect.json.request.EmployeeJSONRequest;
import org.rythmos.oneconnect.json.request.EmployeeProjectJSONRequest;
import org.rythmos.oneconnect.json.request.IdJSONRequest;
import org.rythmos.oneconnect.json.request.PortfolioJSONRequest;
import org.rythmos.oneconnect.json.request.ProjectDetailJSONRequest;
import org.rythmos.oneconnect.json.request.StateJSONRequest;
import org.rythmos.oneconnect.json.response.BaseResponse;
import org.rythmos.oneconnect.json.response.CityJSONResponse;
import org.rythmos.oneconnect.json.response.ClientBasicInfoJSONResponse;
import org.rythmos.oneconnect.json.response.ClientDetailsJSONResponse;
import org.rythmos.oneconnect.json.response.CountryJSONResponse;
import org.rythmos.oneconnect.json.response.EmployeeJSONResponse;
import org.rythmos.oneconnect.json.response.EmployeeProjectJSONResponse;
import org.rythmos.oneconnect.json.response.LocationJSONResponse;
import org.rythmos.oneconnect.json.response.PortfolioBasicInfoJSONResponse;
import org.rythmos.oneconnect.json.response.PortfolioJSONResponse;
import org.rythmos.oneconnect.json.response.ProjectDetailJSONResponse;
import org.rythmos.oneconnect.json.response.ProjectRoleJSONResponse;
import org.rythmos.oneconnect.json.response.StateJSONResponse;
import org.rythmos.oneconnect.response.CitiesResponse;
import org.rythmos.oneconnect.response.ClientDetailResponse;
import org.rythmos.oneconnect.response.CountriesResponse;
import org.rythmos.oneconnect.response.PortfolioResponse;
import org.rythmos.oneconnect.response.ProjectDetailResponse;
import org.rythmos.oneconnect.response.ProjectRoleResponse;
import org.rythmos.oneconnect.response.ResourceResponse;
import org.rythmos.oneconnect.response.StatesResponse;
import org.rythmos.oneconnect.security.UserPrincipal;
import org.rythmos.oneconnect.util.DateUtil;
import org.rythmos.oneconnect.util.EmailUtility;
import org.rythmos.oneconnect.util.OneConnectServiceUtility;
import org.rythmos.oneconnect.util.OneConnectUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Prasanth Kusumaraju
 *
 */
@Service
public class AdminService {

	public static Logger logger = LoggerFactory.getLogger(AdminService.class);

	@Autowired
	private EmailUtility emailUtility;
	@Autowired
	private AdminDAO adminDAO;

	@Autowired
	private DateUtil dateUtil;

	@Autowired
	private OneConnectServiceUtility oneConnectServiceUtility;

	@Autowired
	private BaseResponse baseResponse;

	public Object getPrincipal() {
		return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	public CountriesResponse<?> getCountries() {

		List<CountryBean> countryList = adminDAO.getCountries();
		List<CountryJSONResponse> countryJSONResponseList = new ArrayList<CountryJSONResponse>();
		if (countryList != null && !countryList.isEmpty()) {
			for (CountryBean countryBean : countryList) {
				countryJSONResponseList.add(convertToCountryJSONResponse(countryBean));
			}
			return new CountriesResponse<List<CountryJSONResponse>>(countryJSONResponseList, null, HttpStatus.OK);
		} else {

			logger.info("country list not found");
			baseResponse.setMessage("country list not found");
			return new CountriesResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
		}

	}

	public StatesResponse<?> getStates(CountryJSONRequest countryJSONRequest) {
		if (!OneConnectUtility.isNull(countryJSONRequest.getId())) {
			CountryBean countryBean = new CountryBean();
			countryBean.setId(Long.parseLong(countryJSONRequest.getId()));
			List<StateBean> stateList = adminDAO.getStates(countryBean);
			logger.info("States :: {}", stateList);
			List<StateJSONResponse> stateJSONResponseList = new ArrayList<StateJSONResponse>();

			if (stateList != null && stateList.size() > 0) {
				for (StateBean stateBean : stateList) {
					stateJSONResponseList.add(convertToStateJSONResponse(stateBean));
				}

				return new StatesResponse<List<StateJSONResponse>>(stateJSONResponseList, null, HttpStatus.OK);
			} else {
				logger.info("state list not found");
				baseResponse.setMessage("state list not found");
				return new StatesResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);

			}
		} else {
			logger.info("Bad Request {}", countryJSONRequest);
			baseResponse.setMessage("Bad Request");
			return new StatesResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);

		}
	}

	public CitiesResponse<?> getCities(StateJSONRequest stateJSONRequest) {

		if (!OneConnectUtility.isNull(stateJSONRequest.getId())) {
			StateBean stateBean = new StateBean();
			stateBean.setId(Long.parseLong(stateJSONRequest.getId()));
			List<CityBean> cityList = adminDAO.getCities(stateBean);
			List<CityJSONResponse> cityJSONResponseList = new ArrayList<CityJSONResponse>();

			if (cityList != null && cityList.size() > 0) {
				for (CityBean cityResponseBean : cityList) {
					cityJSONResponseList.add(convertToCityJSONResponse(cityResponseBean));
				}

				return new CitiesResponse<List<CityJSONResponse>>(cityJSONResponseList, null, HttpStatus.OK);

			} else {
				logger.info("City list not found");
				baseResponse.setMessage("city list not found");
				return new CitiesResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
			}
		} else {
			logger.info("Bad Request {}", stateJSONRequest);
			baseResponse.setMessage("Bad Request ");
			return new CitiesResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}
	}

	public CountryJSONResponse convertToCountryJSONResponse(CountryBean countryBean) {

		CountryJSONResponse countryJSONResponse = new CountryJSONResponse();
		countryJSONResponse.setId(countryBean.getId());
		countryJSONResponse.setName(countryBean.getName());
		countryJSONResponse.setPhoneCode(countryBean.getPhoneCode());
		countryJSONResponse.setSortName(countryBean.getSortName());
		return countryJSONResponse;
	}

	public StateJSONResponse convertToStateJSONResponse(StateBean stateBean) {

		StateJSONResponse stateJSONResponse = new StateJSONResponse();
		stateJSONResponse.setId(stateBean.getId());
		stateJSONResponse.setName(stateBean.getName());
		stateJSONResponse.setCountryId(stateBean.getCountry().getId());
		return stateJSONResponse;

	}

	public CityJSONResponse convertToCityJSONResponse(CityBean cityBean) {

		CityJSONResponse cityJSONResponse = new CityJSONResponse();
		cityJSONResponse.setId(cityBean.getId());
		cityJSONResponse.setName(cityBean.getName());
		cityJSONResponse.setStateId(cityBean.getState().getId());
		return cityJSONResponse;

	}

	public LocationJSONResponse convertToLocationJSONResponse(LocationBean locationBean) {

		LocationJSONResponse locationJSONResponse = new LocationJSONResponse();

		locationJSONResponse.setCountry(convertToCountryJSONResponse(locationBean.getCountry()));
		locationJSONResponse.setState(convertToStateJSONResponse(locationBean.getState()));
		locationJSONResponse.setCity(convertToCityJSONResponse(locationBean.getCity()));

		return locationJSONResponse;
	}

	public ClientDetailsJSONResponse convertToClientDetailsJSONResponse(ClientDetailBean clientDetailBean) {
		logger.info("clientDetailBean::{}", clientDetailBean);
		ClientDetailsJSONResponse clientDetailsJsonResponse = new ClientDetailsJSONResponse();

		clientDetailsJsonResponse.setClientDescription(clientDetailBean.getClientDescription());
		clientDetailsJsonResponse.setClientId(clientDetailBean.getClientId());
		clientDetailsJsonResponse.setClientName(clientDetailBean.getClientName());
		clientDetailsJsonResponse.setId(clientDetailBean.getId());
		clientDetailsJsonResponse.setLocation(convertToLocationJSONResponse(clientDetailBean.getLocation()));
		clientDetailsJsonResponse.setStatus(clientDetailBean.getStatus());
		return clientDetailsJsonResponse;

	}

	public PortfolioJSONResponse convertToPortfolioJSONResponse(PortfolioBean portfolioBean) {
		PortfolioJSONResponse portfolioJSONResponse = new PortfolioJSONResponse();

		portfolioJSONResponse.setId(portfolioBean.getId());
		portfolioJSONResponse.setName(portfolioBean.getName());
		portfolioJSONResponse.setClient(convertToClientDetailsJSONResponse(portfolioBean.getClientDetail()));
		return portfolioJSONResponse;
	}

	public ProjectDetailJSONResponse convertToProjectDetailJSONResponse(ProjectDetailBean projectDetailBean) {
		ProjectDetailJSONResponse projectDetailJSONResponse = new ProjectDetailJSONResponse();

		projectDetailJSONResponse.setDirector(projectDetailBean.getDirector());
		String endDate = dateUtil.getFormattedDateString(projectDetailBean.getProjectEndDate(),
				OneConnectConstants.DEFAULT_DATE_FORMAT);
		projectDetailJSONResponse.setEndDate(endDate);
		projectDetailJSONResponse.setId(projectDetailBean.getId());
		projectDetailJSONResponse.setManagerLeader(projectDetailBean.getManager());
		projectDetailJSONResponse.setPortfolioId(projectDetailBean.getPortfolio().getId());
		projectDetailJSONResponse.setProductOwner(projectDetailBean.getProductOwner());
		projectDetailJSONResponse.setProjectName(projectDetailBean.getProjectName());
		projectDetailJSONResponse.setPurpose(projectDetailBean.getPurpose());
		projectDetailJSONResponse.setScrumMaster(projectDetailBean.getRythmosSM());
		String startDate = dateUtil.getFormattedDateString(projectDetailBean.getProjectStartDate(),
				OneConnectConstants.DEFAULT_DATE_FORMAT);
		projectDetailJSONResponse.setStartDate(startDate);
		projectDetailJSONResponse.setPortfolioName(projectDetailBean.getPortfolio().getName());
		projectDetailJSONResponse.setClientId(projectDetailBean.getPortfolio().getClientDetail().getId());
		projectDetailJSONResponse.setClientName(projectDetailBean.getPortfolio().getClientDetail().getClientName());

		return projectDetailJSONResponse;
	}

	public ClientDetailResponse<?> saveClient(ClientDetailJSONRequest clientDetailJSONRequest) {

		logger.info("Save Client Request :: " + clientDetailJSONRequest);
		if (!OneConnectUtility.isNullOrEmpty(clientDetailJSONRequest.getClientName())
				&& !OneConnectUtility.isNullOrEmpty(clientDetailJSONRequest.getClientDescription())
				&& !OneConnectUtility.isNull(clientDetailJSONRequest.getLocation())) {

			LocationBean location = new LocationBean();
			CityBean city = new CityBean();
			StateBean state = new StateBean();
			CountryBean country = new CountryBean();

			location.setCity(city);
			location.setState(state);
			location.setCountry(country);

			location.getCountry().setId(Long.parseLong(clientDetailJSONRequest.getLocation().getCountry().getId()));
			location.getCountry().setName(clientDetailJSONRequest.getLocation().getCountry().getName());
			location.getCountry().setPhoneCode(clientDetailJSONRequest.getLocation().getCountry().getPhoneCode());
			location.getCountry().setSortName(clientDetailJSONRequest.getLocation().getCountry().getSortName());

			location.getState().setCountry(location.getCountry());
			location.getState().setId(Long.parseLong(clientDetailJSONRequest.getLocation().getState().getId()));
			location.getState().setName(clientDetailJSONRequest.getLocation().getState().getName());

			location.getCity().setId(Long.parseLong(clientDetailJSONRequest.getLocation().getCity().getId()));
			location.getCity().setName(clientDetailJSONRequest.getLocation().getCity().getName());
			location.getCity().setState(location.getState());

			ClientDetailBean clientDetailBean = new ClientDetailBean();
			clientDetailBean.setClientId(clientDetailJSONRequest.getClientName());
			clientDetailBean.setClientName(clientDetailJSONRequest.getClientName());
			clientDetailBean.setClientDescription(clientDetailJSONRequest.getClientDescription());
			clientDetailBean.setLocation(location);
			clientDetailBean.setStatus(OneConnectConstants.clientStatus.ACTIVE.toString());

			return adminDAO.saveClient(clientDetailBean);

		} else {

			baseResponse.setMessage("ClientName is empty or null");
			return new ClientDetailResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
		}
	}

	public ClientDetailResponse<?> getClientDetails() {

		Object principal = getPrincipal();
		List<ClientDetailsJSONResponse> clientDetailsJSONResponseList = new ArrayList<ClientDetailsJSONResponse>();
		List<ClientDetailBean> clientDetailList = null;
		if (principal instanceof UserPrincipal && (!((UserPrincipal) principal).getAuthorities().iterator().next()
				.toString().equals(RoleName.ROLE_ADMIN.toString())
				&& !((UserPrincipal) principal).getAuthorities().iterator().next().toString()
						.equals(RoleName.ROLE_EXECUTIVE.toString()))) {

			Long employeeId = ((UserPrincipal) principal).getEmployee().getId();
			String role = ((UserPrincipal) principal).getAuthorities().iterator().next().toString();
			logger.info(" role :: {}", role);
			clientDetailList = adminDAO.getApplicableClientDetails(employeeId, role);

		} else {
			logger.info(" Admin Else ::{}", ((UserPrincipal) principal).getAuthorities().iterator().next().toString());
			clientDetailList = adminDAO.getClientDetails();
		}
		if (clientDetailList != null && !clientDetailList.isEmpty()) {
			for (ClientDetailBean clientDetailBean : clientDetailList) {
				clientDetailsJSONResponseList.add(convertToClientDetailsJSONResponse(clientDetailBean));
			}

			return new ClientDetailResponse<List<ClientDetailsJSONResponse>>(clientDetailsJSONResponseList, null,
					HttpStatus.OK);
		} else {
			logger.info("No Clients found!");
			baseResponse.setMessage("No Clients found!");
			return new ClientDetailResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
		}

	}

	public ClientDetailResponse<?> getAllClientDetails() {

		List<ClientDetailsJSONResponse> clientDetailsJSONResponseList = new ArrayList<ClientDetailsJSONResponse>();
		List<ClientDetailBean> clientDetailList = null;
		clientDetailList = adminDAO.getClientDetails();
		if (clientDetailList != null && !clientDetailList.isEmpty()) {
			for (ClientDetailBean clientDetailBean : clientDetailList) {
				clientDetailsJSONResponseList.add(convertToClientDetailsJSONResponse(clientDetailBean));
			}
			return new ClientDetailResponse<List<ClientDetailsJSONResponse>>(clientDetailsJSONResponseList, null,
					HttpStatus.OK);
		} else {
			logger.info("No Clients found!");
			baseResponse.setMessage("No Clients found!");
			return new ClientDetailResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
		}

	}

	public ClientDetailResponse<?> updateClient(@RequestBody ClientDetailJSONRequest clientDetailJSONRequest) {

		if (!OneConnectUtility.isNull(clientDetailJSONRequest.getId())
				&& !OneConnectUtility.isNullOrEmpty(clientDetailJSONRequest.getClientName())
				&& !OneConnectUtility.isNullOrEmpty(clientDetailJSONRequest.getClientDescription())
				&& !OneConnectUtility.isNull(clientDetailJSONRequest.getLocation())
				&& !OneConnectUtility.isNullOrEmpty(clientDetailJSONRequest.getStatus())) {

			logger.info("Valid ClientDetailPojoRequest::{} ", clientDetailJSONRequest);
			ClientDetailBean clientDetailBean = new ClientDetailBean();
			clientDetailBean.setId(clientDetailJSONRequest.getId());
			clientDetailBean.setClientId(clientDetailJSONRequest.getClientId());
			clientDetailBean.setClientName(clientDetailJSONRequest.getClientName());
			clientDetailBean.setClientDescription(clientDetailJSONRequest.getClientDescription());
			clientDetailBean.setStatus(clientDetailJSONRequest.getStatus());

			clientDetailBean.setLocation(new LocationBean());
			clientDetailBean.getLocation().setCity(new CityBean());
			clientDetailBean.getLocation().setState(new StateBean());
			clientDetailBean.getLocation().setCountry(new CountryBean());

			clientDetailBean.getLocation().getCountry()
					.setId(Long.parseLong(clientDetailJSONRequest.getLocation().getCountry().getId()));
			clientDetailBean.getLocation().getCountry()
					.setName(clientDetailJSONRequest.getLocation().getCountry().getName());
			clientDetailBean.getLocation().getCountry()
					.setPhoneCode(clientDetailJSONRequest.getLocation().getCountry().getPhoneCode());
			clientDetailBean.getLocation().getCountry()
					.setSortName(clientDetailJSONRequest.getLocation().getCountry().getSortName());

			clientDetailBean.getLocation().getState()
					.setId(Long.parseLong(clientDetailJSONRequest.getLocation().getState().getId()));
			clientDetailBean.getLocation().getState()
					.setName(clientDetailJSONRequest.getLocation().getState().getName());
			clientDetailBean.getLocation().getState().setCountry(clientDetailBean.getLocation().getCountry());

			clientDetailBean.getLocation().getCity()
					.setId(Long.parseLong(clientDetailJSONRequest.getLocation().getCity().getId()));
			clientDetailBean.getLocation().getCity().setName(clientDetailJSONRequest.getLocation().getCity().getName());
			clientDetailBean.getLocation().getCity().setState(clientDetailBean.getLocation().getState());

			return adminDAO.updateClient(clientDetailBean);
		} else {
			logger.info("Invalid request or Client Id does not exist!::{} ", clientDetailJSONRequest);
			baseResponse.setMessage("Invalid request or Client Id does not exist!");
			return new ClientDetailResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}

	}

	public ClientDetailResponse<?> deleteClient(@RequestBody ClientDetailJSONRequest clientDetailJSONRequest) {
		if (!OneConnectUtility.isNull(clientDetailJSONRequest.getId())) {
			ClientDetailBean clientDetailBean = new ClientDetailBean();
			clientDetailBean.setId(clientDetailJSONRequest.getId());
			return adminDAO.deleteClient(clientDetailBean);
		} else {
			logger.info("Invalid delete request! ::{}", clientDetailJSONRequest);
			baseResponse.setMessage("Invalid delete request!");
			return new ClientDetailResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}
	}

	public PortfolioResponse<?> getPortfolios(ClientDetailJSONRequest clientDetailJSONRequest) {
		List<PortfolioBean> portfolioList = null;

		if (!OneConnectUtility.isNull(clientDetailJSONRequest.getId())) {
			ClientDetailBean cientDetailBean = new ClientDetailBean();
			cientDetailBean.setId(clientDetailJSONRequest.getId());
			Object principal = getPrincipal();
			if (principal instanceof UserPrincipal && (!((UserPrincipal) principal).getAuthorities().iterator().next()
					.toString().equals(RoleName.ROLE_ADMIN.toString())
					&& !((UserPrincipal) principal).getAuthorities().iterator().next().toString()
							.equals(RoleName.ROLE_EXECUTIVE.toString()))) {

				Long employeeId = ((UserPrincipal) principal).getEmployee().getId();
				String role = ((UserPrincipal) principal).getAuthorities().iterator().next().toString();
				portfolioList = adminDAO.getApplicablePortfolios(cientDetailBean, employeeId, role);

			} else {
				portfolioList = adminDAO.getPortfolios(cientDetailBean);
			}

			List<PortfolioJSONResponse> portfolioJSONResponseList = new ArrayList<PortfolioJSONResponse>();
			if (portfolioList != null) {

				for (PortfolioBean portfolioBean : portfolioList) {
					portfolioJSONResponseList.add(convertToPortfolioJSONResponse(portfolioBean));
				}
				return new PortfolioResponse<List<PortfolioJSONResponse>>(portfolioJSONResponseList, null,
						HttpStatus.OK);

			} else {
				logger.info("portfolio list not found");
				baseResponse.setMessage("state list not found");
				return new PortfolioResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
			}
		} else {
			logger.info("Bad Request {}", clientDetailJSONRequest);
			baseResponse.setMessage("Bad Request ");
			return new PortfolioResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}
	}

	public PortfolioResponse<?> savePortfolio(PortfolioJSONRequest portfolioJSONRequest) {
		logger.info("PortfolioJSONRequest::{}", portfolioJSONRequest);
		if (null != portfolioJSONRequest && !OneConnectUtility.isNull(portfolioJSONRequest.getClient())
				&& !OneConnectUtility.isNullOrEmpty(portfolioJSONRequest.getName())
				&& !OneConnectUtility.isNull(portfolioJSONRequest.getClient())) {

			PortfolioBean portfolioBean = new PortfolioBean();
			portfolioBean.setName(portfolioJSONRequest.getName());
			portfolioBean.setClientDetail(portfolioJSONRequest.getClient());

			return adminDAO.savePortfolio(portfolioBean);
		}

		baseResponse.setMessage("::Bad Request:: ");
		return new PortfolioResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
	}

	public PortfolioResponse<?> getPortfolioDetails() {
		List<PortfolioBean> portfolioList = adminDAO.getPortfolioDetails();
		List<PortfolioJSONResponse> portfolioJSONResponseList = new ArrayList<PortfolioJSONResponse>();
		if (portfolioList != null && portfolioList.size() > 0) {

			for (PortfolioBean portfolioBean : portfolioList) {
				portfolioJSONResponseList.add(convertToPortfolioJSONResponse(portfolioBean));
			}
			return new PortfolioResponse<List<PortfolioJSONResponse>>(portfolioJSONResponseList, null, HttpStatus.OK);
		} else {
			logger.info("No Portfolio Found for this Project");
			baseResponse.setMessage("No Portfolio Found for this Project");
			return new PortfolioResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
		}

	}

	public PortfolioResponse<?> updatePortfolio(PortfolioJSONRequest portfolioJSONRequest) {

		if (!OneConnectUtility.isNull(portfolioJSONRequest) && !OneConnectUtility.isNull(portfolioJSONRequest.getId())
				&& !OneConnectUtility.isNullOrEmpty(portfolioJSONRequest.getName())
				&& !OneConnectUtility.isNull(portfolioJSONRequest.getClient())) {

			PortfolioBean portfolioBean = new PortfolioBean();
			portfolioBean.setId(portfolioJSONRequest.getId());
			portfolioBean.setName(portfolioJSONRequest.getName());
			portfolioBean.setClientDetail(portfolioJSONRequest.getClient());
			return adminDAO.updatePortfolio(portfolioBean);

		} else {
			logger.info("Invalid request or Portfolio does not exist!::{} ", portfolioJSONRequest);
			baseResponse.setMessage("Invalid request or Portfolio does not exist!");
			return new PortfolioResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}

	}

	public PortfolioResponse<?> deletePortfolio(PortfolioJSONRequest portfolioJSONRequest) {
		if (!OneConnectUtility.isNull(portfolioJSONRequest.getId())) {
			PortfolioBean portfolioBean = new PortfolioBean();
			portfolioBean.setId(portfolioJSONRequest.getId());
			return adminDAO.deletePortfolio(portfolioBean);
		} else {
			logger.info("Invalid request or Portfolio does not exist!::{}", portfolioJSONRequest);
			baseResponse.setMessage("Invalid request or Portfolio does not exist!");
			return new PortfolioResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}
	}

	public ProjectDetailResponse<?> saveProject(ProjectDetailJSONRequest projectDetailJSONRequest) {
		logger.info("ProjectDetailJSONRequest::{}", projectDetailJSONRequest);
		if (!OneConnectUtility.isNull(projectDetailJSONRequest.getPortfolio())
				&& !OneConnectUtility.isNullOrEmpty(projectDetailJSONRequest.getManagerLeader())
				&& !OneConnectUtility.isNullOrEmpty(projectDetailJSONRequest.getDirector())
				&& !OneConnectUtility.isNullOrEmpty(projectDetailJSONRequest.getProductOwner())
				&& !OneConnectUtility.isNullOrEmpty(projectDetailJSONRequest.getProjectName())
				&& !OneConnectUtility.isNullOrEmpty(projectDetailJSONRequest.getPurpose())
				&& !OneConnectUtility.isNullOrEmpty(projectDetailJSONRequest.getScrumMaster())
				&& !OneConnectUtility.isNullOrEmpty(projectDetailJSONRequest.getStartDate())
				&& !OneConnectUtility.isNullOrEmpty(projectDetailJSONRequest.getEndDate())) {

			ProjectDetailBean projectDetailBean = new ProjectDetailBean();
			projectDetailBean.setProjectName(projectDetailJSONRequest.getProjectName());
			projectDetailBean.setPortfolio(projectDetailJSONRequest.getPortfolio());
			projectDetailBean.setManager(projectDetailJSONRequest.getManagerLeader());
			projectDetailBean.setDirector(projectDetailJSONRequest.getDirector());
			projectDetailBean.setProjectStartDate(dateUtil.getFormattedDate(OneConnectConstants.DEFAULT_DATE_FORMAT,
					projectDetailJSONRequest.getStartDate()));
			projectDetailBean.setProjectEndDate(dateUtil.getFormattedDate(OneConnectConstants.DEFAULT_DATE_FORMAT,
					projectDetailJSONRequest.getEndDate()));
			projectDetailBean.setRythmosSM(projectDetailJSONRequest.getScrumMaster());
			projectDetailBean.setProductOwner(projectDetailJSONRequest.getProductOwner());
			projectDetailBean.setPurpose(projectDetailJSONRequest.getPurpose());
			return adminDAO.saveProject(projectDetailBean);
		}
		logger.info("Invalid request or Portfolio does not exist!::{}", projectDetailJSONRequest);
		baseResponse.setMessage("Invalid requrest");
		return new ProjectDetailResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
	}

	public ProjectDetailResponse<?> getProjectDetails() {
		List<ProjectDetailBean> projectList = adminDAO.getProjectDetails();
		List<ProjectDetailJSONResponse> projectDetailJSONResponseList = new ArrayList<ProjectDetailJSONResponse>();
		Calendar dateWithTZ = null;
		logger.info("projectList::{}", projectList);
		if (projectList != null && projectList.size() > 0) {
			for (ProjectDetailBean projectDetailResponseBean : projectList) {
				dateWithTZ = Calendar.getInstance(TimeZone.getTimeZone(OneConnectConstants.DEFAULT_TIME_ZONE));

				dateWithTZ.setTime(projectDetailResponseBean.getProjectStartDate());
				projectDetailResponseBean.setProjectStartDate(dateWithTZ.getTime());
				dateWithTZ.setTime(projectDetailResponseBean.getProjectEndDate());
				projectDetailResponseBean.setProjectEndDate(dateWithTZ.getTime());
				projectDetailJSONResponseList.add(convertToProjectDetailJSONResponse(projectDetailResponseBean));

			}

			return new ProjectDetailResponse<List<ProjectDetailJSONResponse>>(projectDetailJSONResponseList, null,
					HttpStatus.OK);
		} else {
			logger.info("No project found");
			baseResponse.setMessage("No project found");
			return new ProjectDetailResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
		}
	}

	public ProjectDetailResponse<?> updateProject(ProjectDetailJSONRequest projectDetailJSONRequest) {
		if (!OneConnectUtility.isNull(projectDetailJSONRequest.getPortfolio())
				&& !OneConnectUtility.isNull(projectDetailJSONRequest.getId())
				&& !OneConnectUtility.isNullOrEmpty(projectDetailJSONRequest.getManagerLeader())
				&& !OneConnectUtility.isNullOrEmpty(projectDetailJSONRequest.getDirector())
				&& !OneConnectUtility.isNullOrEmpty(projectDetailJSONRequest.getProductOwner())
				&& !OneConnectUtility.isNullOrEmpty(projectDetailJSONRequest.getProjectName())
				&& !OneConnectUtility.isNullOrEmpty(projectDetailJSONRequest.getPurpose())
				&& !OneConnectUtility.isNullOrEmpty(projectDetailJSONRequest.getScrumMaster())
				&& !OneConnectUtility.isNullOrEmpty(projectDetailJSONRequest.getStartDate())
				&& !OneConnectUtility.isNullOrEmpty(projectDetailJSONRequest.getEndDate())) {
			DateUtil dateUtil = new DateUtil();
			Date startDate = dateUtil.getFormattedDate(OneConnectConstants.DEFAULT_DATE_FORMAT,
					projectDetailJSONRequest.getStartDate());
			Date endDate = dateUtil.getFormattedDate(OneConnectConstants.DEFAULT_DATE_FORMAT,
					projectDetailJSONRequest.getEndDate());
			ProjectDetailBean projectDetailBean = new ProjectDetailBean();
			projectDetailBean.setId(projectDetailJSONRequest.getId());
			projectDetailBean.setProjectName(projectDetailJSONRequest.getProjectName());
			projectDetailBean.setPortfolio(projectDetailJSONRequest.getPortfolio());
			projectDetailBean.setManager(projectDetailJSONRequest.getManagerLeader());
			projectDetailBean.setDirector(projectDetailJSONRequest.getDirector());
			projectDetailBean.setProjectStartDate(startDate);
			projectDetailBean.setProjectEndDate(endDate);
			projectDetailBean.setRythmosSM(projectDetailJSONRequest.getScrumMaster());
			projectDetailBean.setProductOwner(projectDetailJSONRequest.getProductOwner());
			projectDetailBean.setPurpose(projectDetailJSONRequest.getPurpose());
			return adminDAO.updateProject(projectDetailBean);
		} else {
			logger.info("Invalid Request or Project does not exist!::{} ", projectDetailJSONRequest);
			baseResponse.setMessage("Invalid Request or Portfolio does not exist!");
			return new ProjectDetailResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}
	}

	public ProjectDetailResponse<?> deleteProject(ProjectDetailJSONRequest projectDetailJSONRequest) {
		if (!OneConnectUtility.isNull(projectDetailJSONRequest.getId())) {
			ProjectDetailBean projectDetailBean = new ProjectDetailBean();
			projectDetailBean.setId(projectDetailJSONRequest.getId());
			return adminDAO.deleteProject(projectDetailBean);
		} else {
			logger.info("Invalid Request::{}", projectDetailJSONRequest);
			baseResponse.setMessage("Invalid Request");
			return new ProjectDetailResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}
	}

	public ProjectDetailResponse<?> getProjectDetailsByPortfolio(PortfolioJSONRequest portfolioJSONRequest) {
		List<ProjectDetailBean> projectList = null;
		PortfolioBean portfolioBean = new PortfolioBean();
		portfolioBean.setId(portfolioJSONRequest.getId());
		Object principal = getPrincipal();
		logger.info("Portfolio request::{}",portfolioJSONRequest);
		if (principal instanceof UserPrincipal && (!((UserPrincipal) principal).getAuthorities().iterator().next()
				.toString().equals(RoleName.ROLE_ADMIN.toString())
				&& !((UserPrincipal) principal).getAuthorities().iterator().next().toString()
						.equals(RoleName.ROLE_EXECUTIVE.toString()))) {
           
			Long employeeId = ((UserPrincipal) principal).getEmployee().getId();
	
			String role = ((UserPrincipal) principal).getAuthorities().iterator().next().toString();
			logger.info("employeeId::{}\n role::{}\n",employeeId,role);
			projectList = adminDAO.getApplicableProjectDetailsByPortfolio(portfolioBean, employeeId, role);

		} else {
			logger.info("user principle is::{}",((UserPrincipal) principal).getAuthorities().iterator().next().toString());
			projectList = adminDAO.getProjectDetailsByPortfolio(portfolioBean);
		}
		List<ProjectDetailJSONResponse> projectDetailJSONResponseList = new ArrayList<ProjectDetailJSONResponse>();

		logger.info("projectList::{}", projectList);
		if (projectList != null && projectList.size() > 0) {
			for (ProjectDetailBean projectDetailResponseBean : projectList) {
				projectDetailJSONResponseList.add(convertToProjectDetailJSONResponse(projectDetailResponseBean));

			}

			return new ProjectDetailResponse<List<ProjectDetailJSONResponse>>(projectDetailJSONResponseList, null,
					HttpStatus.OK);
		} else {
			logger.info("No project found!");
			baseResponse.setMessage("No project found!");
			return new ProjectDetailResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
		}
	}

	public ResourceResponse<?> getAllProjectResources() {

		List<EmployeeProjectMappingBean> employeeProjectMappingBeanList = adminDAO.getAllProjectResources();
		List<EmployeeJSONResponse> employeeJSONResponseList = new ArrayList<EmployeeJSONResponse>();
		if (employeeProjectMappingBeanList != null && employeeProjectMappingBeanList.size() > 0) {

			for (EmployeeProjectMappingBean employeeProjectMappingBean : employeeProjectMappingBeanList) {
				employeeJSONResponseList.add(convertToEmployeeProjectMappingJSONResponse(employeeProjectMappingBean));
			}
			logger.info("EmployeeProjectMappingBean Response for getALLProjectResource Operation {}",
					employeeJSONResponseList);
			employeeJSONResponseList = sortEmployeeJSONResponse(employeeJSONResponseList);
			return new ResourceResponse<List<EmployeeJSONResponse>>(employeeJSONResponseList, null, HttpStatus.OK);
		} else {
			logger.info("No Resources found!");
			baseResponse.setMessage("No Resources found");
			return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
		}
	}

	public List<EmployeeJSONResponse> sortEmployeeJSONResponse(List<EmployeeJSONResponse> employeeJSONResponseList) {
		// joining all projects related to employee
		List<EmployeeJSONResponse> sortedemployeeJSONResponseList = new ArrayList<EmployeeJSONResponse>();
		for (EmployeeJSONResponse employeeJSONResponse : employeeJSONResponseList) {
			EmployeeJSONResponse employeeDup = new EmployeeJSONResponse();
			if (sortedemployeeJSONResponseList.size() > 0) {
				employeeDup = sortedemployeeJSONResponseList.stream()
						.filter(employeeS -> employeeJSONResponse.getEmpId().equals(employeeS.getEmpId())).findAny()
						.orElse(null);
				if (null != employeeDup) {
					employeeDup.getProjects().addAll(employeeJSONResponse.getProjects());
				} else {
					sortedemployeeJSONResponseList.add(employeeJSONResponse);
				}
			} else {
				sortedemployeeJSONResponseList.add(employeeJSONResponse);
			}
		}
		logger.info("Sorted Employees {}" + sortedemployeeJSONResponseList);
		return sortedemployeeJSONResponseList;
	}

	public EmployeeJSONResponse convertToEmployeeProjectMappingJSONResponse(
			EmployeeProjectMappingBean employeeProjectMappingBean) {

		EmployeeJSONResponse employeeJSONResponse = new EmployeeJSONResponse();
		List<EmployeeProjectJSONResponse> employeeProjectJSONResponseList = new ArrayList<EmployeeProjectJSONResponse>();
		EmployeeProjectJSONResponse employeeProjectJSONResponse = new EmployeeProjectJSONResponse();
		ClientBasicInfoJSONResponse clientBasicInfoJSONResponse = new ClientBasicInfoJSONResponse();
		PortfolioBasicInfoJSONResponse portfolioBasicInfoJSONResponse = new PortfolioBasicInfoJSONResponse();
		logger.info("employeeProjectMappingBean :: {}", employeeProjectMappingBean);
		employeeJSONResponse.setId(employeeProjectMappingBean.getEmployee().getId());
		employeeJSONResponse.setEmpId(employeeProjectMappingBean.getEmployee().getEmployeeId());
		employeeJSONResponse.setName(employeeProjectMappingBean.getEmployee().getEmployeeName());
		employeeJSONResponse.setEmailId(employeeProjectMappingBean.getEmployee().getEmailId());

		employeeProjectJSONResponse.setId(employeeProjectMappingBean.getId());
		employeeProjectJSONResponse.setProjectId(employeeProjectMappingBean.getProjectDetail().getId());
		employeeProjectJSONResponse.setProjectName(employeeProjectMappingBean.getProjectDetail().getProjectName());

		ProjectRoleJSONResponse projectRoleJSONResponse = new ProjectRoleJSONResponse();
		projectRoleJSONResponse.setId(employeeProjectMappingBean.getProjectRole().getId());
		projectRoleJSONResponse.setName(employeeProjectMappingBean.getProjectRole().getName());

		employeeProjectJSONResponse.setProjectRole(projectRoleJSONResponse);
		employeeProjectJSONResponse.setUtilization(employeeProjectMappingBean.getUtilization());
		employeeProjectJSONResponse.setBillingStatus(employeeProjectMappingBean.getBillingStatus());

		clientBasicInfoJSONResponse
				.setId(employeeProjectMappingBean.getProjectDetail().getPortfolio().getClientDetail().getId());

		clientBasicInfoJSONResponse.setName(
				employeeProjectMappingBean.getProjectDetail().getPortfolio().getClientDetail().getClientName());

		portfolioBasicInfoJSONResponse.setId(employeeProjectMappingBean.getProjectDetail().getPortfolio().getId());

		portfolioBasicInfoJSONResponse.setName(employeeProjectMappingBean.getProjectDetail().getPortfolio().getName());

		employeeProjectJSONResponse.setClient(clientBasicInfoJSONResponse);
		employeeProjectJSONResponse.setPortfolio(portfolioBasicInfoJSONResponse);
		employeeProjectJSONResponseList.add(employeeProjectJSONResponse);

		employeeJSONResponse.setProjects(employeeProjectJSONResponseList);
		return employeeJSONResponse;
	}

	public ResourceResponse<?> findAllResourcesByProject(IdJSONRequest idJSONRequest) {
		logger.info("findAllResourcesByProject JSON Request {}", idJSONRequest);
		if (null != idJSONRequest && null != idJSONRequest.getId()) {
			try {
				Long id = Long.parseLong(idJSONRequest.getId());
				if (id > 0) {
					List<EmployeeProjectMappingBean> employeeProjectMappingBeanList = adminDAO
							.findAllResourcesByProject(id);
					List<EmployeeJSONResponse> employeeJSONResponseList = new ArrayList<EmployeeJSONResponse>();
					if (employeeProjectMappingBeanList != null && employeeProjectMappingBeanList.size() > 0) {

						for (EmployeeProjectMappingBean employeeProjectMappingBean : employeeProjectMappingBeanList) {
							employeeJSONResponseList
									.add(convertToEmployeeProjectMappingJSONResponse(employeeProjectMappingBean));
						}
						logger.info("EmployeeProjectMappingBean Response for findAllResourcesByProject Operation {}",
								employeeJSONResponseList);
						return new ResourceResponse<List<EmployeeJSONResponse>>(employeeJSONResponseList, null,
								HttpStatus.OK);
					} else {
						logger.info("No Resources found for the Project!");
						baseResponse.setMessage("No Resources found for the Project!");
						return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
					}
				} else {
					logger.info("Invalid ID in Request ::{}", idJSONRequest);
					baseResponse.setMessage("Invalid ID in Request");
					return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
				}
			}

			catch (Exception e) {
				logger.info("Exception! Invalid Project id");
				baseResponse.setMessage("Exception! Invalid Project id");
				return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}
		} else {
			logger.info("Invalid Request");
			baseResponse.setMessage("Invalid Request");
			return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}
	}

	public boolean checkDuplicatesInProjectList(List<EmployeeProjectJSONRequest> employeeProjectJSONRequestList) {
		logger.info("Checking Duplicates in project List");

		// Returns true if Duplicate Project is found

		final Set<Long> nonDuplicateProjectId = new HashSet<>();
		for (EmployeeProjectJSONRequest employeeProjectJSONRequest : employeeProjectJSONRequestList) {

			if (!nonDuplicateProjectId.add(employeeProjectJSONRequest.getProjectId())) {
				logger.info("Duplicate Entry Found for the project {}", employeeProjectJSONRequest.getName());
				return true;
			}
		}
		logger.info("No Duplicate Project Found");
		return false;
	}

	public boolean checkProjectUtilization(List<EmployeeProjectJSONRequest> employeeProjectJSONRequestList) {

		// Returns TRUE if Project utilization all Together is less than or equals to
		// 100
		// Returns FALSE if Project utilization is 0 (or) null (or) Project utilization
		// all Together is greater than or equals to 100

		float totalProjectUtilization = 0;
		logger.info("Project Utilization Check");
		for (EmployeeProjectJSONRequest employeeProjectJSONRequest : employeeProjectJSONRequestList) {
			if (null != employeeProjectJSONRequest.getUtilization()
					&& Float.parseFloat(employeeProjectJSONRequest.getUtilization()) > 0) {

				totalProjectUtilization += Float.parseFloat(employeeProjectJSONRequest.getUtilization());
			} else {
				logger.info("Project Utilization of Project {} is Null / 0" + employeeProjectJSONRequest.getName());
				return false;
			}

		}

		if (totalProjectUtilization <= 100.00) {
			logger.info("Project Utilization Check Pass");
			return true;
		} else {
			logger.info("Project Utilization Check Failed Total Utilization is {} ,it should be Less Than 100",
					totalProjectUtilization);
			return false;
		}

	}

	public ResourceResponse<?> addResource(EmployeeJSONRequest employeeJSONRequest) {
		logger.info("Add Resource Request {}", employeeJSONRequest);

		if (!OneConnectUtility.isNullOrEmpty(employeeJSONRequest.getEmailId())
				&& !OneConnectUtility.isNullOrEmpty(employeeJSONRequest.getEmpId())
				&& !OneConnectUtility.isNullOrEmpty(employeeJSONRequest.getName())
				&& !OneConnectUtility.isNull(employeeJSONRequest.getProjects())
				&& !employeeJSONRequest.getProjects().isEmpty()
				&& !checkDuplicatesInProjectList(employeeJSONRequest.getProjects())
				&& checkProjectUtilization(employeeJSONRequest.getProjects())) {

			EmployeeBean employeeBean = new EmployeeBean();
			List<EmployeeProjectMappingBean> employeeProjectMappingBeanList = new ArrayList<EmployeeProjectMappingBean>();
			employeeBean.setEmployeeId(employeeJSONRequest.getEmpId());
			employeeBean.setEmployeeName(employeeJSONRequest.getName());
			employeeBean.setEmailId(employeeJSONRequest.getEmailId());
			employeeBean.setJoiningDate(new Date());
			employeeBean.setStatus(OneConnectConstants.employStatus.ACTIVE.toString());

			for (EmployeeProjectJSONRequest employeeProject : employeeJSONRequest.getProjects()) {

				employeeProjectMappingBeanList.add(convertToBean(employeeProject, employeeBean));
			}

			return adminDAO.addResource(employeeBean, employeeProjectMappingBeanList);

		} else {
			logger.info("Utilization might be exceeding or  Duplicate Project/Projects may exist!");
			baseResponse.setMessage("Utilization might be exceeding or Duplicate Project/Projects may exist!");
			return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
		}

	}

	public ResourceResponse<?> addProjestsToExistingResource(EmployeeJSONRequest employeeJSONRequest) {
		logger.info("Update Resource Request {}", employeeJSONRequest);

		if (!OneConnectUtility.isNullOrEmpty(employeeJSONRequest.getEmailId())
				&& !OneConnectUtility.isNullOrEmpty(employeeJSONRequest.getEmpId())
				&& !OneConnectUtility.isNullOrEmpty(employeeJSONRequest.getName())
				&& !OneConnectUtility.isNull(employeeJSONRequest.getProjects())
				&& !OneConnectUtility.isNull(employeeJSONRequest.getId())
				&& !employeeJSONRequest.getProjects().isEmpty()
				&& !checkDuplicatesInProjectList(employeeJSONRequest.getProjects())
				&& checkProjectUtilization(employeeJSONRequest.getProjects())) {

			EmployeeBean employeeBean = new EmployeeBean();
			List<EmployeeProjectMappingBean> employeeProjectMappingBeanList = new ArrayList<EmployeeProjectMappingBean>();
			
			employeeBean.setId(Long.parseLong(employeeJSONRequest.getId()));
			employeeBean.setEmployeeId(employeeJSONRequest.getEmpId());
			employeeBean.setEmployeeName(employeeJSONRequest.getName());
			employeeBean.setEmailId(employeeJSONRequest.getEmailId());
			employeeBean.setJoiningDate(new Date());
			employeeBean.setStatus(OneConnectConstants.employStatus.ACTIVE.toString());

			for (EmployeeProjectJSONRequest employeeProject : employeeJSONRequest.getProjects()) {

				employeeProjectMappingBeanList.add(convertToBean(employeeProject, employeeBean));
			}

			return adminDAO.addProjestsToExistingResource(employeeBean, employeeProjectMappingBeanList);

		} else {
			logger.info("Projects/Resource details are  empty or null");
			baseResponse.setMessage("Project/Resource details are empty! or\r\n" + "\r\n"
					+ "Utilization might be exceeded! or\r\n" + "\r\n" + "Duplicate project Found!");
			return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
		}

	}

	public EmployeeProjectMappingBean convertToBean(EmployeeProjectJSONRequest employeeProject,
			EmployeeBean employeeBean) {
		EmployeeProjectMappingBean employeeProjectMappingBean = new EmployeeProjectMappingBean();
		ProjectDetailBean projectDetail = new ProjectDetailBean();
		ProjectRoleBean projectRole = new ProjectRoleBean();

		if (null != employeeProject.getId()) {
			employeeProjectMappingBean.setId(employeeProject.getId());
		}
		employeeProjectMappingBean.setEmployee(employeeBean);
		employeeProjectMappingBean.setAssignedDate(new Date());
		employeeProjectMappingBean.setBillingStatus(employeeProject.getBillingStatus());
		employeeProjectMappingBean.setUtilization(employeeProject.getUtilization());
		employeeProjectMappingBean.setStatus(OneConnectConstants.employProjectStatus.ACTIVE.toString());
		projectDetail.setId(employeeProject.getProjectId());
		projectRole.setId(Long.parseLong(employeeProject.getProjectRole().getId()));
		projectRole.setName(employeeProject.getProjectRole().getName());
		employeeProjectMappingBean.setProjectDetail(projectDetail);
		employeeProjectMappingBean.setProjectRole(projectRole);
		return employeeProjectMappingBean;
	}

	public ProjectRoleResponse<?> getAllProjectRoles() {
		// TODOdoesuto-generated method stub

		List<ProjectRoleBean> projectRolesList = adminDAO.getAllProjectRoles();

		List<ProjectRoleJSONResponse> projectRoleJSONResponseList = new ArrayList<ProjectRoleJSONResponse>();

		if (projectRolesList != null && !projectRolesList.isEmpty()) {

			for (ProjectRoleBean projectRoleBean : projectRolesList) {
				projectRoleJSONResponseList.add(convertToProjectRoleJSONResponse(projectRoleBean));
			}
			logger.info("ProjectRoleJSONResponse Response for getAllProjectRoles Operation {}",
					projectRoleJSONResponseList);
			return new ProjectRoleResponse<List<ProjectRoleJSONResponse>>(projectRoleJSONResponseList, null,
					HttpStatus.OK);
		} else {
			logger.info("No Project Roles found!");
			baseResponse.setMessage("No Project Roles found!");
			return new ProjectRoleResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
		}

	}

	private ProjectRoleJSONResponse convertToProjectRoleJSONResponse(ProjectRoleBean projectRoleBean) {
		ProjectRoleJSONResponse projectRoleJSONResponse = new ProjectRoleJSONResponse();
		projectRoleJSONResponse.setId(projectRoleBean.getId());
		projectRoleJSONResponse.setName(projectRoleBean.getName());
		return projectRoleJSONResponse;
	}

	public ResourceResponse<?> deleteResource(EmployeeJSONRequest employeeJSONRequest) {

		logger.info("DeleteResource Request {}", employeeJSONRequest);
		if (!OneConnectUtility.isNullOrEmpty(employeeJSONRequest.getId())) {
			EmployeeBean employeeBean = new EmployeeBean();
			employeeBean.setId(Long.parseLong(employeeJSONRequest.getId()));
			if (adminDAO.deleteResource(employeeBean).getStatusCode().equals(HttpStatus.OK)) {
				logger.info("Resource deleted successfully");
				baseResponse.setMessage("Resource deleted successfully");
				if (oneConnectServiceUtility.userRoleCheck(employeeJSONRequest.getEmailId(),
						Long.parseLong(employeeJSONRequest.getId()))) {
					return new ResourceResponse<>(baseResponse, null, HttpStatus.OK);
				} else {
					logger.info("Employee deleted successfully but unable to Delete User!");
					baseResponse.setMessage("Employee deleted successfully but unable to Delete User!");
					return new ResourceResponse<>(baseResponse, null, HttpStatus.OK);
				}

			} else {
				logger.info("Unable to delete the Resource!");
				baseResponse.setMessage("Unable to delete the Resource!");
				return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
			}

		} else {
			logger.info("Unable to delete Resource , ID might be empty!", employeeJSONRequest);
			baseResponse.setMessage("Unable to delete Resource , ID might be empty!");
			return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
		}

	}

	public ResourceResponse<?> deleteEmployesProject(EmployeeJSONRequest employeeJSONRequest) {
		logger.info("EmployesProject Request {}", employeeJSONRequest);
		if (!OneConnectUtility.isNullOrEmpty(employeeJSONRequest.getId())
				&& null != employeeJSONRequest.getProjects()) {

			EmployeeBean employeeBean = new EmployeeBean();
			employeeBean.setId(Long.parseLong(employeeJSONRequest.getId()));

			List<EmployeeProjectMappingBean> employeeProjectMappingBeanList = new ArrayList<EmployeeProjectMappingBean>();

			for (EmployeeProjectJSONRequest employeeProject : employeeJSONRequest.getProjects()) {

				employeeProjectMappingBeanList.add(convertToBean(employeeProject, employeeBean));
			}

			if (adminDAO.deleteEmployesProject(employeeBean, employeeProjectMappingBeanList).getStatusCode()
					.equals(HttpStatus.OK)) {
				logger.info("Project deleted successfully to the Resource");
				baseResponse.setMessage("Project deleted successfully to the Resource");
				if (oneConnectServiceUtility.userRoleCheck(employeeJSONRequest.getEmailId(),
						Long.parseLong(employeeJSONRequest.getId()))) {
					return new ResourceResponse<>(baseResponse, null, HttpStatus.OK);
				} else {
					logger.info("Project deleted successfully but unable to update User Role");
					baseResponse.setMessage("Project deleted successfully but unable to update User Role");
					return new ResourceResponse<>(baseResponse, null, HttpStatus.OK);
				}

			} else {
				logger.info("Unable to delete Project to the Resource!");
				baseResponse.setMessage("Unable to delete Project to the Resource!");
				return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
			}

		} else {
			logger.info("Not able to delete a Project, Resource ID might be empty! ", employeeJSONRequest);
			baseResponse.setMessage("Not able to delete a Project, Resource ID might be empty! ");
			return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
		}

	}

	public ResourceResponse<?> updateResource(EmployeeJSONRequest employeeJSONRequest) {

		/*
		 * private long id; * private String employeeName; * private String employeeId;
		 * * private String emailId; * private Date joiningDate; * private Date
		 * relievingDate; * private String status; * private ApplicationRoleBean
		 * applicationRole;
		 */

		/*
		 * private String id; private String name; private String empId; private String
		 * emailId; private String joiningDate; private String relievingDate;
		 * 
		 * private ApplicationRoleJSONRequest applicationRole;
		 */
		EmployeeBean employeeBean = convertToBean(employeeJSONRequest);
		return adminDAO.updateResource(employeeBean);
	}

	private EmployeeBean convertToBean(EmployeeJSONRequest employeeJSONRequest) {
		EmployeeBean employeeBean = new EmployeeBean();

		employeeBean.setId(Long.parseLong(employeeJSONRequest.getId()));
		employeeBean.setEmployeeName(employeeJSONRequest.getName());
		employeeBean.setEmployeeId(employeeJSONRequest.getEmpId());
		employeeBean.setEmailId(employeeJSONRequest.getEmailId());
		return employeeBean;
	}

	public ResourceResponse<?> updateProjectForExistingResource(EmployeeJSONRequest employeeJSONRequest) {

		logger.info("Update Resource Request {}", employeeJSONRequest);

		if (!OneConnectUtility.isNullOrEmpty(employeeJSONRequest.getEmailId())
				&& !OneConnectUtility.isNullOrEmpty(employeeJSONRequest.getEmpId())
				&& !OneConnectUtility.isNullOrEmpty(employeeJSONRequest.getName())
				&& !OneConnectUtility.isNull(employeeJSONRequest.getProjects())
				&& !OneConnectUtility.isNull(employeeJSONRequest.getId())
				&& !employeeJSONRequest.getProjects().isEmpty()
				&& !checkDuplicatesInProjectList(employeeJSONRequest.getProjects())
				&& checkProjectUtilization(employeeJSONRequest.getProjects())) {

			EmployeeBean employeeBean = new EmployeeBean();
			List<EmployeeProjectMappingBean> employeeProjectMappingBeanList = new ArrayList<EmployeeProjectMappingBean>();

			employeeBean.setId(Long.parseLong(employeeJSONRequest.getId()));
			employeeBean.setEmailId(employeeJSONRequest.getEmailId());

			for (EmployeeProjectJSONRequest employeeProject : employeeJSONRequest.getProjects()) {

				employeeProjectMappingBeanList.add(convertToBean(employeeProject, employeeBean));
			}
			ResourceResponse<?> response = adminDAO.updateProjectForExistingResource(employeeBean,
					employeeProjectMappingBeanList);

			if (oneConnectServiceUtility.userRoleCheck(employeeJSONRequest.getEmailId(),
					Long.parseLong(employeeJSONRequest.getId()))) {
				logger.info("Project & User Role got Updated successfully");
				return response;
			} else {
				logger.info("Project Updated successfully but unable to update User Role");
				return response;
			}

		} else {
			logger.info("Projects/Resource details are empty or null!");
			baseResponse.setMessage("Projects/Resource details are empty or null!");
			return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
		}

	}

	public ResourceResponse<?> sendEmail(EmailJSONRequest emailJSONRequest) {
		try {
			if (emailUtility.sendEmail(emailJSONRequest.getEmailId(), emailJSONRequest.getSubject(),
					emailJSONRequest.getBody())) {
				logger.info("Email Sent Successfully ");
				baseResponse.setMessage("Email Sent Successfully ");
				return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
			} else {
				logger.info("Unable to send Email!");
				baseResponse.setMessage("Unable to send Email!");
				return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("INTERNAL_SERVER_ERROR Unable to send Email!");
			baseResponse.setMessage("INTERNAL_SERVER_ERROR Unable to send Email!");
			return new ResourceResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	public ProjectEndDateResponse<?> getProjectEndDateAlert(){
		return adminDAO.getProjectEndDateAlert();
	}


}
