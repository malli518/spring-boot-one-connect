package org.rythmos.oneconnect.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.rythmos.oneconnect.bean.ClientDetailBean;
import org.rythmos.oneconnect.bean.ESummaryAccomplishmentBean;
import org.rythmos.oneconnect.bean.ESummaryBean;
import org.rythmos.oneconnect.bean.ESummaryRiskAndMitigationBean;
import org.rythmos.oneconnect.bean.ESummarySummaryBean;
import org.rythmos.oneconnect.bean.EmployeeProjectMappingBean;
import org.rythmos.oneconnect.bean.ExecutiveSummaryBean;
import org.rythmos.oneconnect.bean.ExecutiveSummaryJSONResponseBean;
import org.rythmos.oneconnect.bean.PortfolioBean;
import org.rythmos.oneconnect.bean.ProjectDetailBean;
import org.rythmos.oneconnect.bean.RAGStatusResponseBean;
import org.rythmos.oneconnect.constant.OneConnectConstants;
import org.rythmos.oneconnect.entity.ESummary;
import org.rythmos.oneconnect.entity.ESummaryAccomplishment;
import org.rythmos.oneconnect.entity.ESummaryRiskAndMitigation;
import org.rythmos.oneconnect.entity.ESummarySummary;
import org.rythmos.oneconnect.entity.EmployeeProjectMapping;
import org.rythmos.oneconnect.entity.ProjectDetail;
import org.rythmos.oneconnect.entity.RagStatus;
import org.rythmos.oneconnect.json.response.BaseResponse;
import org.rythmos.oneconnect.repository.ESummaryAccomplishmentRepository;
import org.rythmos.oneconnect.repository.ESummaryRepository;
import org.rythmos.oneconnect.repository.ESummaryRiskAndMitigationRepository;
import org.rythmos.oneconnect.repository.ESummarySummaryRepository;
import org.rythmos.oneconnect.repository.EmployeeProjectMappingRepository;
import org.rythmos.oneconnect.repository.ProjectDetailRepository;
import org.rythmos.oneconnect.repository.RagStatusRepository;
import org.rythmos.oneconnect.repository.UserRepository;
import org.rythmos.oneconnect.response.ESummaryResponse;
import org.rythmos.oneconnect.response.RiskResponse;
import org.rythmos.oneconnect.security.UserPrincipal;
import org.rythmos.oneconnect.util.DateUtil;
import org.rythmos.oneconnect.util.OneConnectDAOUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ESummaryDAO {

	public static Logger logger = LoggerFactory.getLogger(AdminDAO.class);

	@Autowired
	private ESummaryRepository eSummaryRepository;

	@Autowired
	private ESummarySummaryRepository esSummaryRepository;

	@Autowired
	private ESummaryAccomplishmentRepository esAccomplishmentRepository;

	@Autowired
	private ESummaryRiskAndMitigationRepository esRiskAndMitigationRepository;

	@Autowired
	private RagStatusRepository ragStatusRepository;

	@Autowired
	private EmployeeProjectMappingRepository employeeProjectMappingRepository;

	@Autowired
	private ProjectDetailRepository projectDetailRepository;

	@Autowired
	private UserRepository userRespository;

	@Autowired
	private BaseResponse baseResponse;
	@Autowired
	private OneConnectDAOUtility oneConnectDAOUtility;
	// save Operation

	private ESummary convertToEntity(ESummaryBean eSummaryBean) {
		return oneConnectDAOUtility.modelMapper.map(eSummaryBean, ESummary.class);

	}

	private ESummarySummary convertToEntity(ESummarySummaryBean esSummaryBean) {
		return oneConnectDAOUtility.modelMapper.map(esSummaryBean, ESummarySummary.class);

	}

	private ESummaryAccomplishment convertToEntity(ESummaryAccomplishmentBean esAccomplishmentBean) {
		return oneConnectDAOUtility.modelMapper.map(esAccomplishmentBean, ESummaryAccomplishment.class);

	}

	private ESummaryRiskAndMitigation convertToEntity(ESummaryRiskAndMitigationBean esRiskAndMitigationBean) {
		return oneConnectDAOUtility.modelMapper.map(esRiskAndMitigationBean, ESummaryRiskAndMitigation.class);

	}

	public ESummaryResponse<?> saveESummary(ESummaryBean eSummaryBean) {
		logger.info("esummarybean is::{}", eSummaryBean);
		if (eSummaryBean != null) {
			logger.info(" Null check Pass :: ");
			ESummary eSummary = convertToEntity(eSummaryBean);
			if (eSummaryBean.getRagStatus() == null) {
				eSummary.setRagStatus(ragStatusRepository.findRagStatusById(1L));
			} else {
				eSummary.setRagStatus(ragStatusRepository.findRagStatusById(eSummaryBean.getRagStatus().getId()));
			}
			if (null != eSummaryRepository.save(eSummary)) {
				logger.info(" ES_Summary Saved successfully ");
				baseResponse.setMessage("Summary Saved Successfully!");
				return new ESummaryResponse<>(baseResponse, null, HttpStatus.OK);
			} else {
				logger.info("ES_Summary not {}", eSummaryBean);
				baseResponse.setMessage("Unable to Save ExecutiveSummary Summary!");
				return new ESummaryResponse<>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} else {
			logger.info("Esummary Not found {}", eSummaryBean);
			baseResponse.setMessage("Esummary Not Found ");
			return new ESummaryResponse<>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}
	}

	public ESummaryResponse<?> updateESummary(ESummaryBean eSummaryBean) {
		Long eSummaryID = eSummaryRepository.findESummaryId(eSummaryBean.getClientDetail().getId(),
				eSummaryBean.getPortfolio().getId(), eSummaryBean.getProjectDetail().getId(),
				new java.sql.Date(eSummaryBean.geteSummaryDate().getTime()));
		ESummary eSummary = eSummaryRepository.findESummaryById(eSummaryID);
		if (eSummary != null) {

			RagStatus ragStaus = ragStatusRepository.findRagStatusById(eSummaryBean.getRagStatus().getId());
			eSummary.setRagStatus(ragStaus);
			logger.info("RAG status changed");
			if (eSummaryRepository.save(eSummary) != null) {
				logger.info("RagStatus updated successfully");
				baseResponse.setMessage("RagStatus updated successfully");
				return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
			} else {
				logger.info("RagStatus Unable to Update");
				baseResponse.setMessage("Unable to Update RagStatus");
				return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else

		{
			logger.info("ESummary Not found {}", eSummaryBean);
			baseResponse.setMessage("Executive Summary Not Found ");
			return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}
	}

	public ESummaryResponse<?> saveESummarySummary(ESummarySummaryBean esSummaryBean) {
		try {
			if (esSummaryBean != null) {

				logger.info(" Null check Pass :: ");
				ESummarySummary esSummary = convertToEntity(esSummaryBean);
				ESummary eSummary = eSummaryRepository.findESummaryById(esSummaryBean.geteSummary().geteSummaryId());
				esSummary.seteSummary(eSummary);
				if (null != esSummaryRepository.save(esSummary)) {
					logger.info("Summary added successfully ::{} ", esSummary);
					baseResponse.setMessage("Summary added successfully");
					return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
				} else {
					logger.info("Unable to add Summary, Summary might already exist!", esSummaryBean);
					baseResponse.setMessage("Unable to add Summary, Summary might already exist!");
					return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				logger.info("Executive Summary date is out of Project Scope!");
				baseResponse.setMessage("Executive Summary date is out of Project Scope!");
				return new ESummaryResponse<>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception ex) {
			logger.info("Unable to add Summary, Summary might already exist!", esSummaryBean);
			baseResponse.setMessage("Unable to add Summary, Summary might already exist!");
			return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ESummaryResponse<?> updateESummarySummary(ESummarySummaryBean esSummaryBean) {

		ESummarySummary esSummary = esSummaryRepository
				.findESummaryBySummaryId(esSummaryBean.geteSummary().geteSummaryId(), esSummaryBean.getSummaryId());
		try {
			if (esSummary != null) {
				esSummary.setDescription(esSummaryBean.getDescription());
				if (esSummaryRepository.save(esSummary) != null) {
					logger.info("Summary updated successfully");
					baseResponse.setMessage("Summary updated successfully");
					return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
				} else {
					logger.info("Unable to update Summary, Summary might already exist!");
					baseResponse.setMessage("Unable to update Summary, Summary might already exist!");
					return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				logger.info("Summary not found! {}", esSummaryBean);
				baseResponse.setMessage("Summary not found!");
				return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			logger.info("Unable to update Summary, Summary might already exist!");
			baseResponse.setMessage("Unable to update Summary, Summary might already exist!");
			return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ESummaryResponse<?> deleteESummarySummary(ESummarySummaryBean esSummaryBean) {

		ESummarySummary esSummary = esSummaryRepository
				.findESummaryBySummaryId(esSummaryBean.geteSummary().geteSummaryId(), esSummaryBean.getSummaryId());
		if (esSummary != null) {
			esSummaryRepository.delete(esSummary);
			logger.info("Summary deleted successfully");
			baseResponse.setMessage("Summary deleted successfully");
			return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
		} else {
			logger.info("Bad Request!", esSummaryBean);
			baseResponse.setMessage("Unable to delete Summary!");
			return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}
	}

	public ESummaryResponse<?> saveESummaryAccomplishment(ESummaryAccomplishmentBean esAccomplishmentBean) {
		try {
			if (esAccomplishmentBean != null) {
				logger.info(" Null check Pass :: ");
				ESummaryAccomplishment esAccomplishment = convertToEntity(esAccomplishmentBean);
				ESummary eSummary = eSummaryRepository
						.findESummaryById(esAccomplishmentBean.geteSummary().geteSummaryId());
				esAccomplishment.seteSummary(eSummary);
				if (null != esAccomplishmentRepository.save(esAccomplishment)) {
					logger.info("Accomplishment added successfully");
					baseResponse.setMessage("Accomplishment added successfully");
					return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
				} else {
					logger.info("Unable to add an Accomplishment, Accomplishment might already exist! {}",
							esAccomplishmentBean);
					baseResponse.setMessage("Unable to add an Accomplishment, Accomplishment might already exist!");
					return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				logger.info("Bad Request! {}", esAccomplishmentBean);
				baseResponse.setMessage("Bad Request!");
				return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception ex) {
			logger.info("Unable to add an Accomplishment, Accomplishment might already exist! {}",
					esAccomplishmentBean);
			baseResponse.setMessage("Unable to add an Accomplishment, Accomplishment might already exist!");
			return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ESummaryResponse<?> updateESummaryAccomplishment(ESummaryAccomplishmentBean esAccomplishmentBean) {
		ESummaryAccomplishment esAccomplishment = esAccomplishmentRepository.findESummaryByAccomplishmentId(
				esAccomplishmentBean.geteSummary().geteSummaryId(), esAccomplishmentBean.getAccomplishmentId());
		try {
			if (esAccomplishment != null) {
				esAccomplishment.setDescription(esAccomplishmentBean.getDescription());
				if (esAccomplishmentRepository.save(esAccomplishment) != null) {
					logger.info("Accomplishment updated successfully");
					baseResponse.setMessage("Accomplishment updated successfully");
					return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
				} else {
					logger.info("Unable to update an Accomplishment, Accomplishment might already exist!");
					baseResponse.setMessage("Unable to update an Accomplishment, Accomplishment might already exist!");
					return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				logger.info("esAccomplishmentBean Not found! {}", esAccomplishmentBean);
				baseResponse.setMessage("Accomplishment Not Found! ");
				return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			logger.info("Unable to update an Accomplishment, Accomplishment might already exist!");
			baseResponse.setMessage("Unable to update an Accomplishment, Accomplishment might already exist!");
			return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public ESummaryResponse<?> deleteESummaryAccomplishment(ESummaryAccomplishmentBean esAccomplishmentBean) {
		ESummaryAccomplishment esAccomplishment = esAccomplishmentRepository.findESummaryByAccomplishmentId(
				esAccomplishmentBean.geteSummary().geteSummaryId(), esAccomplishmentBean.getAccomplishmentId());
		if (esAccomplishment != null) {
			esAccomplishmentRepository.delete(esAccomplishment);
			logger.info("Accomplishment deleted successfully");
			baseResponse.setMessage("Accomplishment deleted successfully");
			return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
		} else {
			logger.info("Accomplishment Not Found! {}", esAccomplishmentBean);
			baseResponse.setMessage("Unable to delete an Accomplishment!");
			return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
		}
	}

	public ESummaryResponse<?> saveESummaryRiskAndMitigation(ESummaryRiskAndMitigationBean esRiskAndMitigationBean) {
		try {
			if (esRiskAndMitigationBean != null) {
				logger.info(" Null check Pass :: ");
				ESummaryRiskAndMitigation esRiskAndMitigation = convertToEntity(esRiskAndMitigationBean);
				ESummary eSummary = eSummaryRepository
						.findESummaryById(esRiskAndMitigationBean.geteSummary().geteSummaryId());

				esRiskAndMitigation.seteSummary(eSummary);
				if (null != esRiskAndMitigationRepository.save(esRiskAndMitigation)) {
					logger.info("Risk and Mitigation added successfully");
					baseResponse.setMessage("Risk and Mitigation added successfully");
					return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
				} else {
					logger.info("Unable to add Risk, Risk might already exist!", esRiskAndMitigationBean);
					baseResponse.setMessage("Unable to add Risk, Risk might already exist!");
					return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				logger.info("esRiskAndMitigationBean Not found {}", esRiskAndMitigationBean);
				baseResponse.setMessage("RiskAndMitigation Not Found ");
				return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception ex) {
			logger.info("Unable to add Risk, Risk might already exist!", esRiskAndMitigationBean);
			baseResponse.setMessage("Unable to add Risk, Risk might already exist!");
			return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ESummaryResponse<?> updateESummaryRiskAndMitigation(ESummaryRiskAndMitigationBean esRiskAndMitigationBean) {
		ESummaryRiskAndMitigation esRiskAndMitigation = esRiskAndMitigationRepository
				.findESummaryByRiskAndMitigationId(esRiskAndMitigationBean.getRiskAndMitigationId());
		try {
			if (esRiskAndMitigation != null) {
				esRiskAndMitigation.setMitigationDesc(esRiskAndMitigationBean.getMitigationDesc());
				esRiskAndMitigation.setRiskAge(esRiskAndMitigationBean.getRiskAge());
				esRiskAndMitigation.setRiskDesc(esRiskAndMitigationBean.getRiskDesc());
				if (esRiskAndMitigationRepository.save(esRiskAndMitigation) != null) {
					logger.info("Risk and Mitigation updated successfully");
					baseResponse.setMessage("Risk and Mitigation updated successfully");
					return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
				} else {
					logger.info("Unable to update Risk and Mitigation, Risk might already exist!");
					baseResponse.setMessage("Unable to update Risk and Mitigation, Risk might already exist!");
					return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				logger.info("Bad Request! {}", esRiskAndMitigationBean);
				baseResponse.setMessage("Bad Request!");
				return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			logger.info("Unable to update Risk and Mitigation, Risk might already exist!");
			baseResponse.setMessage("Unable to update Risk and Mitigation, Risk might already exist!");
			return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ESummaryResponse<?> deleteESummaryRiskAndMitigation(ESummaryRiskAndMitigationBean esRiskAndMitigationBean) {

		ESummaryRiskAndMitigation esRiskAndMitigation = esRiskAndMitigationRepository
				.findRiskAndMitigationById(esRiskAndMitigationBean.getRiskAndMitigationId());
		if (esRiskAndMitigation != null) {
			esRiskAndMitigationRepository.delete(esRiskAndMitigation);
			baseResponse.setMessage("Risk and Mitigation deleted successfully");
			return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
		} else {
			logger.info("Unable to delete a Risk and Mitigation! {}", esRiskAndMitigationBean);
			baseResponse.setMessage("Unable to delete a Risk and Mitigation!");
			return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
		}
	}

	public ExecutiveSummaryJSONResponseBean getESummary(ExecutiveSummaryBean executiveSummaryBean) {
		Long clientId = executiveSummaryBean.getClientId();
		Long portfolioId = executiveSummaryBean.getPortfolioId();
		Long projectId = executiveSummaryBean.getProjectDetailId();
		Date startDate = executiveSummaryBean.geteSummaryDate();
		Long esummaryId = eSummaryRepository.findESummaryId(clientId, portfolioId, projectId,
				new java.sql.Date(startDate.getTime()));
		ProjectDetail projectDetailsDB = null;

		DateUtil dateUtil = new DateUtil();
		ExecutiveSummaryJSONResponseBean esResponseBean = new ExecutiveSummaryJSONResponseBean();
		RagStatus ragStatus = eSummaryRepository.findESummaryRagStatusById(esummaryId);
		esResponseBean.setRagStatus(ragStatus);
		if (executiveSummaryBean != null && projectId != null && startDate != null) {
			logger.info("Null Check passed");
			projectDetailsDB = projectDetailRepository.findProjectDetailId(projectId);
		}
		if (esummaryId != null) {
			if (projectDetailsDB != null) {
				ProjectDetail projectDetail = projectDetailsDB;
				projectId = projectDetail.getId();
				esResponseBean.setDirector(projectDetail.getDirector());
				esResponseBean.setProjectEndDate(
						dateUtil.getFormattedDateString(projectDetail.getProjectEndDate(), "MM/dd/yyyy"));
				esResponseBean.setScrumMasterFromRythmos(projectDetail.getRythmosSM());

				esResponseBean.setProjectId(projectId);

				esResponseBean.setManagerLeader(projectDetail.getManager());
				esResponseBean.setProjectStartDate(
						dateUtil.getFormattedDateString(projectDetail.getProjectStartDate(), "MM/dd/yyyy"));
				esResponseBean.setProjectName(projectDetail.getProjectName());
				esResponseBean.setProductOwner(projectDetail.getProductOwner());
				esResponseBean.setPurpose(projectDetail.getPurpose());

				List<EmployeeProjectMapping> empProjectList = employeeProjectMappingRepository
						.findAllActiveEmployeesByProjectId(esResponseBean.getProjectId(),
								OneConnectConstants.employProjectStatus.ACTIVE.toString());
				logger.info("esSummaryList is::{}", empProjectList);
				if (!empProjectList.isEmpty()) {
					List<EmployeeProjectMappingBean> empProjectMappingList = empProjectList.stream()
							.map(empProject -> convertToBean(empProject)).collect(Collectors.toList());
					esResponseBean.setResource(empProjectMappingList);
					esResponseBean.setNumberOfResourses(empProjectMappingList.size());
				}

				List<ESummarySummary> esSummaryList = esSummaryRepository.findSummaryListbyeSummaryId(esummaryId);
				logger.info("esSummaryList is::{}", esSummaryList);
				if (!esSummaryList.isEmpty()) {
					List<ESummarySummaryBean> eSummarySummaryList = esSummaryList.stream()
							.map(eSummarySummary -> convertToBean(eSummarySummary)).collect(Collectors.toList());
					esResponseBean.setSummary(eSummarySummaryList);
				}

				List<ESummaryAccomplishment> esAccomplishmentList = esAccomplishmentRepository
						.findAccomplishmentbyeSummaryId(esummaryId);
				logger.info("esSummaryList is::{}", esAccomplishmentList);
				if (!esAccomplishmentList.isEmpty()) {
					List<ESummaryAccomplishmentBean> eSummaryAccomplishmentList = esAccomplishmentList.stream()
							.map(eSummaryAccomplishment -> convertToBean(eSummaryAccomplishment))
							.collect(Collectors.toList());
					esResponseBean.setAccomplishments(eSummaryAccomplishmentList);

				}

//				List<ESummaryRiskAndMitigation> esRiskAndMitigationList = esRiskAndMitigationRepository
//						.findRiskAndMitigationByRiskStatusAndProject(OneConnectConstants.status.OPEN.toString(),
//								executiveSummaryBean.getProjectDetailId(),
//								new java.sql.Date(executiveSummaryBean.geteSummaryDate().getTime()));
				List<ESummaryRiskAndMitigation> esRiskAndMitigationList = esRiskAndMitigationRepository
						.findRiskAndMitigationByRiskStatusAndProject(
								executiveSummaryBean.getProjectDetailId(),
								new java.sql.Date(executiveSummaryBean.geteSummaryDate().getTime()));
				logger.info("esSummaryList is::{}", esRiskAndMitigationList);
				if (!esRiskAndMitigationList.isEmpty()) {
					List<ESummaryRiskAndMitigationBean> eSummaryRiskAndMitigationBeanList = esRiskAndMitigationList
							.stream().map(eSummaryRiskAndMitigation -> convertToBean(eSummaryRiskAndMitigation))
							.collect(Collectors.toList());
					esResponseBean.setRiskAndMitigation(eSummaryRiskAndMitigationBeanList);
				}

				return esResponseBean;

			}
		}
		return null;

	}

	private EmployeeProjectMappingBean convertToBean(EmployeeProjectMapping employeeProjectMapping) {
		EmployeeProjectMappingBean employeeProjectMappingBean = oneConnectDAOUtility.modelMapper
				.map(employeeProjectMapping, EmployeeProjectMappingBean.class);
		return employeeProjectMappingBean;
	}

	private ESummarySummaryBean convertToBean(ESummarySummary esSummary) {
		ESummarySummaryBean esSummaryBean = oneConnectDAOUtility.modelMapper.map(esSummary, ESummarySummaryBean.class);
		return esSummaryBean;
	}

	private ESummaryAccomplishmentBean convertToBean(ESummaryAccomplishment esAccomplishment) {
		ESummaryAccomplishmentBean esAccomplishmentBean = oneConnectDAOUtility.modelMapper.map(esAccomplishment,
				ESummaryAccomplishmentBean.class);
		return esAccomplishmentBean;
	}

	private ESummaryRiskAndMitigationBean convertToBean(ESummaryRiskAndMitigation esRiskAndMitigation) {
		ESummaryRiskAndMitigationBean esRiskAndMitigationBean = oneConnectDAOUtility.modelMapper
				.map(esRiskAndMitigation, ESummaryRiskAndMitigationBean.class);
		return esRiskAndMitigationBean;
	}

	public List<ESummaryRiskAndMitigationBean> getAllRisks() {
		List<ESummaryRiskAndMitigation> esRiskAndMitigationList = esRiskAndMitigationRepository
				.findRiskAndMitigationByRiskStatus(OneConnectConstants.status.OPEN.toString());
		logger.info("Risk and Mitigation list ::{}\n\n",esRiskAndMitigationList);
		if (!esRiskAndMitigationList.isEmpty()) {
			List<ESummaryRiskAndMitigationBean> eSummaryRiskAndMitigationBeanList = esRiskAndMitigationList.stream()
					.map(eSummaryRiskAndMitigation -> convertToBean(eSummaryRiskAndMitigation))
					.collect(Collectors.toList());
			return eSummaryRiskAndMitigationBeanList;
		}
		

		return null;
	}

	public RiskResponse<?> updateRiskStatus(ESummaryRiskAndMitigationBean riskAndMitigationBean) {
		ESummaryRiskAndMitigation esRiskAndMitigation = esRiskAndMitigationRepository
				.findRiskAndMitigationById(riskAndMitigationBean.getRiskAndMitigationId());
		Long projectId = esRiskAndMitigation.geteSummary().getProjectDetail().getId();
		
		try {
			List<String> userlist = userRespository.findUsernameByProjectId(projectId);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username= ((UserPrincipal) authentication.getPrincipal()).getUsername();
			logger.info("user list is::{}",userlist);
			logger.info("username is ::{}",username);
			if (userlist.contains((((UserPrincipal) authentication.getPrincipal()).getUsername()))||((UserPrincipal) authentication.getPrincipal()).getAdmin() ) {

				if (esRiskAndMitigation.getRiskAndMitigationId()>0) {
					esRiskAndMitigation.setStatus(OneConnectConstants.status.CLOSED.toString());
					esRiskAndMitigation.setClosedDate(new Date());
					if (esRiskAndMitigationRepository.save(esRiskAndMitigation) != null) {
						logger.info("Risk closed successfully");
						baseResponse.setMessage("Risk closed successfully");
						return new RiskResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
					} else {
						logger.info("Unable to close Risk!");
						baseResponse.setMessage("Unable to close Risk!");
						return new RiskResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
					}
				} else {
					logger.info("RiskAndMitigationBean Not found! {}", riskAndMitigationBean);
					baseResponse.setMessage("RiskAndMitigation Not Found!");
					return new RiskResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
				}
			} else {
				logger.info("Unauthorized to close this Risk! {}", riskAndMitigationBean);
				baseResponse.setMessage("Unauthorized to close this Risk!");
				return new RiskResponse<BaseResponse>(baseResponse, null, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception ex) {
			logger.info("Unable to close Risk!");
			baseResponse.setMessage("Unable to close Risk!");
			return new RiskResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public List<ESummaryRiskAndMitigationBean> getRisksByClient(ClientDetailBean clientDetailBean) {
		List<ESummaryRiskAndMitigation> riskAndMitigationList = esRiskAndMitigationRepository
				.findRiskAndMitigationByClient(OneConnectConstants.status.OPEN.toString(), clientDetailBean.getId());
		if (!riskAndMitigationList.isEmpty()) {
			List<ESummaryRiskAndMitigationBean> eSummaryRiskAndMitigationBeanList = riskAndMitigationList.stream()
					.map(eSummaryRiskAndMitigation -> convertToBean(eSummaryRiskAndMitigation))
					.collect(Collectors.toList());
			return eSummaryRiskAndMitigationBeanList;
		}
		return null;
	}

	public List<ESummaryRiskAndMitigationBean> getRisksByPortfolio(PortfolioBean portfolioBean) {
		List<ESummaryRiskAndMitigation> riskAndMitigationList = esRiskAndMitigationRepository
				.findRiskAndMitigationByPortfolio(OneConnectConstants.status.OPEN.toString(), portfolioBean.getId());
		if (!riskAndMitigationList.isEmpty()) {
			List<ESummaryRiskAndMitigationBean> eSummaryRiskAndMitigationBeanList = riskAndMitigationList.stream()
					.map(eSummaryRiskAndMitigation -> convertToBean(eSummaryRiskAndMitigation))
					.collect(Collectors.toList());
			return eSummaryRiskAndMitigationBeanList;
		}
		return null;

	}

	public List<ESummaryRiskAndMitigationBean> getRisksByProject(ProjectDetailBean projectDetailBean) {
		List<ESummaryRiskAndMitigation> riskAndMitigationList = esRiskAndMitigationRepository
				.findRiskAndMitigationByProject(OneConnectConstants.status.OPEN.toString(), projectDetailBean.getId());
		if (!riskAndMitigationList.isEmpty()) {
			List<ESummaryRiskAndMitigationBean> eSummaryRiskAndMitigationBeanList = riskAndMitigationList.stream()
					.map(eSummaryRiskAndMitigation -> convertToBean(eSummaryRiskAndMitigation))
					.collect(Collectors.toList());
			return eSummaryRiskAndMitigationBeanList;
		}
		return null;
	}

	public RAGStatusResponseBean getAllRAGStatus() {
		List<ESummary> projectEsummaryGreenList = eSummaryRepository.findProjectDetailByRagStatusId(1L);
		List<ESummary> projectEsummaryAmberList = eSummaryRepository.findProjectDetailByRagStatusId(2L);
		List<ESummary> projectEsummaryRedList = eSummaryRepository.findProjectDetailByRagStatusId(3L);
		RAGStatusResponseBean ragStatusResponseBean = new RAGStatusResponseBean();
		if (!projectEsummaryGreenList.isEmpty()) {
			List<ESummaryBean> eSummaryBean = new ArrayList<ESummaryBean>();
			eSummaryBean = projectEsummaryGreenList.stream().map(project -> convertToBean(project))
					.collect(Collectors.toList());
			ragStatusResponseBean.setGreen(eSummaryBean);
		}

		if (!projectEsummaryAmberList.isEmpty()) {
			List<ESummaryBean> eSummaryBean = new ArrayList<ESummaryBean>();
			eSummaryBean = projectEsummaryAmberList.stream().map(project -> convertToBean(project))
					.collect(Collectors.toList());
			ragStatusResponseBean.setAmber(eSummaryBean);
		}

		if (!projectEsummaryRedList.isEmpty()) {
			List<ESummaryBean> eSummaryBean = new ArrayList<ESummaryBean>();
			eSummaryBean = projectEsummaryRedList.stream().map(project -> convertToBean(project))
					.collect(Collectors.toList());
			ragStatusResponseBean.setRed(eSummaryBean);
		}

		return ragStatusResponseBean;

	}
	public RAGStatusResponseBean getRAGStatusByClient(ClientDetailBean clientDetailBean) {
		List<ESummary> projectDetailGreenList = eSummaryRepository.findProjectDetailByRagAndClientId(1L,
				clientDetailBean.getId());
		List<ESummary> projectDetailAmberList = eSummaryRepository.findProjectDetailByRagAndClientId(2L,
				clientDetailBean.getId());
		List<ESummary> projectDetailRedList = eSummaryRepository.findProjectDetailByRagAndClientId(3L,
				clientDetailBean.getId());
		RAGStatusResponseBean ragStatusResponseBean = new RAGStatusResponseBean();
		if (!projectDetailGreenList.isEmpty()) {
			List<ESummaryBean> eSummaryBean = new ArrayList<ESummaryBean>();
			eSummaryBean = projectDetailGreenList.stream().map(project -> convertToBean(project))
					.collect(Collectors.toList());
			ragStatusResponseBean.setGreen(eSummaryBean);
		}

		if (!projectDetailAmberList.isEmpty()) {
			List<ESummaryBean> eSummaryBean = new ArrayList<ESummaryBean>();
			eSummaryBean = projectDetailAmberList.stream().map(project -> convertToBean(project))
					.collect(Collectors.toList());
			ragStatusResponseBean.setAmber(eSummaryBean);
		}

		if (!projectDetailRedList.isEmpty()) {
			List<ESummaryBean> eSummaryBean = new ArrayList<ESummaryBean>();
			eSummaryBean = projectDetailRedList.stream().map(project -> convertToBean(project))
					.collect(Collectors.toList());
			ragStatusResponseBean.setRed(eSummaryBean);
		}

		return ragStatusResponseBean;

	}

	public RAGStatusResponseBean getRAGStatusByPortfolio(PortfolioBean portfolioBean) {
		List<ESummary> projectDetailGreenList = eSummaryRepository.findProjectDetailByRagAndPortfolioId(1L,
				portfolioBean.getId());
		List<ESummary> projectDetailAmberList = eSummaryRepository.findProjectDetailByRagAndPortfolioId(2L,
				portfolioBean.getId());
		List<ESummary> projectDetailRedList = eSummaryRepository.findProjectDetailByRagAndPortfolioId(3L,
				portfolioBean.getId());
		RAGStatusResponseBean ragStatusResponseBean = new RAGStatusResponseBean();
		if (!projectDetailGreenList.isEmpty()) {
			List<ESummaryBean> eSummaryBean = new ArrayList<ESummaryBean>();
			eSummaryBean = projectDetailGreenList.stream().map(project -> convertToBean(project))
					.collect(Collectors.toList());
			ragStatusResponseBean.setGreen(eSummaryBean);
		}

		if (!projectDetailAmberList.isEmpty()) {
			List<ESummaryBean> eSummaryBean = new ArrayList<ESummaryBean>();
			eSummaryBean = projectDetailAmberList.stream().map(project -> convertToBean(project))
					.collect(Collectors.toList());
			ragStatusResponseBean.setAmber(eSummaryBean);
		}

		if (!projectDetailRedList.isEmpty()) {
			List<ESummaryBean> eSummaryBean = new ArrayList<ESummaryBean>();
			eSummaryBean = projectDetailRedList.stream().map(project -> convertToBean(project))
					.collect(Collectors.toList());
			ragStatusResponseBean.setRed(eSummaryBean);
		}

		return ragStatusResponseBean;

	}

	private ESummaryBean convertToBean(ESummary eSummary) {
		ESummaryBean projectEsummaryBean = oneConnectDAOUtility.modelMapper.map(eSummary, ESummaryBean.class);
		return projectEsummaryBean;
	}

}
