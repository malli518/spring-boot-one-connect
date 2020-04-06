package org.rythmos.oneconnect.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.rythmos.oneconnect.bean.RagStatusBean;
import org.rythmos.oneconnect.constant.OneConnectConstants;
import org.rythmos.oneconnect.dao.ESummaryDAO;
import org.rythmos.oneconnect.entity.ProjectDetail;
import org.rythmos.oneconnect.entity.RoleName;
import org.rythmos.oneconnect.exception.OneConnectDBException;
import org.rythmos.oneconnect.json.request.ClientDetailJSONRequest;
import org.rythmos.oneconnect.json.request.ESJSONRequest;
import org.rythmos.oneconnect.json.request.ExecutiveSummaryJSONRequest;
import org.rythmos.oneconnect.json.request.PortfolioJSONRequest;
import org.rythmos.oneconnect.json.request.ProjectDetailJSONRequest;
import org.rythmos.oneconnect.json.request.RiskAndMitigationJSONRequest;
import org.rythmos.oneconnect.json.response.AccomplishmentJSONResponse;
import org.rythmos.oneconnect.json.response.BaseResponse;
import org.rythmos.oneconnect.json.response.ExecutiveSummaryJSONResponse;
import org.rythmos.oneconnect.json.response.ProjectEsummaryJSONResponse;
import org.rythmos.oneconnect.json.response.RAGStatusJSONResponse;
import org.rythmos.oneconnect.json.response.ReportDetailJSONResponse;
import org.rythmos.oneconnect.json.response.ResourceJSONResponse;
import org.rythmos.oneconnect.json.response.RiskAndMitigationJSONResponse;
import org.rythmos.oneconnect.json.response.SummaryJSONResponse;
import org.rythmos.oneconnect.repository.ClientDetailsRepository;
import org.rythmos.oneconnect.repository.ESummaryRepository;
import org.rythmos.oneconnect.repository.EmployeeProjectMappingRepository;
import org.rythmos.oneconnect.repository.PortfolioRepository;
import org.rythmos.oneconnect.repository.ProjectDetailRepository;
import org.rythmos.oneconnect.response.ESummaryResponse;
import org.rythmos.oneconnect.response.RiskResponse;
import org.rythmos.oneconnect.security.UserPrincipal;
import org.rythmos.oneconnect.util.DateUtil;
import org.rythmos.oneconnect.util.OneConnectServiceUtility;
import org.rythmos.oneconnect.util.OneConnectUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ESummaryService {

	private static Logger logger = LoggerFactory.getLogger(ESummaryService.class);

	@Autowired
	private ProjectDetailRepository projectDetailRepository;

	@Autowired
	private ESummaryRepository eSummaryRepository;

	@Autowired
	private ClientDetailsRepository clientDetailsRepository;

	@Autowired
	private PortfolioRepository portfolioRepository;

	@Autowired
	private ESummaryDAO eSummaryDAO;

	@Autowired
	private BaseResponse baseResponse;

	@Autowired
	private EmployeeProjectMappingRepository employeeProjectMappingRepository;

	@Autowired
	private OneConnectServiceUtility oneConnectServiceUtility;

	public Object getPrincipal() {
		return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	public ESummaryResponse<?> saveESummary(@RequestBody ESJSONRequest esJSONRequest) throws OneConnectDBException {

		logger.info("saveESummary, Request Json: {}", esJSONRequest);
		Long clientId = clientDetailsRepository.findClientId(esJSONRequest.getClient().getId()).getId();
		Long portfolioId = portfolioRepository.findPortfolioById(esJSONRequest.getPortfolio().getId()).getId();
		Long projectId = projectDetailRepository.findProjectDetailId(esJSONRequest.getProject().getId()).getId();
		DateUtil dateUtil = new DateUtil();
		Date startDate = dateUtil.getFormattedDate(OneConnectConstants.DEFAULT_DATE_FORMAT,
				esJSONRequest.getStartDate());

		if (clientId != null && portfolioId != null && projectId != null && startDate != null) {
			if (oneConnectServiceUtility.checkProjectScope(projectId, startDate)) {
				ESummaryBean eSummaryBean = new ESummaryBean();
				eSummaryBean.seteSummaryDate(startDate);
				eSummaryBean.setClientDetail(esJSONRequest.getClient());
				eSummaryBean.setPortfolio(esJSONRequest.getPortfolio());
				eSummaryBean.setProjectDetail(esJSONRequest.getProject());
				eSummaryBean.setRagStatus(esJSONRequest.getRagStatus());

				Long eSummaryId = eSummaryRepository.findESummaryId(clientId, portfolioId, projectId,
						new java.sql.Date(startDate.getTime()));
				if (eSummaryId == null) {
					// insert in eSummary table and get the eSummaryId
					eSummaryDAO.saveESummary(eSummaryBean);
					eSummaryId = eSummaryRepository.findESummaryId(clientId, portfolioId, projectId,
							new java.sql.Date(startDate.getTime()));
				}

				logger.info("eSummaryId is::{}", eSummaryId);

				if (esJSONRequest.getTransactionType() != null) {

					if (esJSONRequest.getTransactionType()
							.equalsIgnoreCase(OneConnectConstants.transactionType.SUMMARY.toString())) {
						eSummaryBean.seteSummarySummaryBean(new ESummarySummaryBean());
						eSummaryBean.geteSummarySummaryBean()
								.setDescription(esJSONRequest.getValueObject().getDescription());
						eSummaryBean.geteSummarySummaryBean().seteSummary(new ESummaryBean());
						eSummaryBean.geteSummarySummaryBean().geteSummary().seteSummaryId(eSummaryId);
						return eSummaryDAO.saveESummarySummary(eSummaryBean.geteSummarySummaryBean());
					}
					if (esJSONRequest.getTransactionType()
							.equalsIgnoreCase(OneConnectConstants.transactionType.ACCOMPLISHMENT.toString())) {
						eSummaryBean.seteSummaryAccomplishmentBean(new ESummaryAccomplishmentBean());
						eSummaryBean.geteSummaryAccomplishmentBean()
								.setDescription(esJSONRequest.getValueObject().getDescription());
						eSummaryBean.geteSummaryAccomplishmentBean().seteSummary(new ESummaryBean());
						eSummaryBean.geteSummaryAccomplishmentBean().geteSummary().seteSummaryId(eSummaryId);
						return eSummaryDAO.saveESummaryAccomplishment(eSummaryBean.geteSummaryAccomplishmentBean());
					}
					if (esJSONRequest.getTransactionType()
							.equalsIgnoreCase(OneConnectConstants.transactionType.RISKANDMITIGATION.toString())) {
						eSummaryBean.seteSummaryRiskAndMitigationBean(new ESummaryRiskAndMitigationBean());
						eSummaryBean.geteSummaryRiskAndMitigationBean()
								.setRiskDesc(esJSONRequest.getValueObject().getRisk());
						eSummaryBean.geteSummaryRiskAndMitigationBean()
								.setMitigationDesc(esJSONRequest.getValueObject().getMitigation());
						eSummaryBean.geteSummaryRiskAndMitigationBean()
								.setStatus(OneConnectConstants.status.OPEN.toString());
						eSummaryBean.geteSummaryRiskAndMitigationBean().setCreatedDate(new Date());
						eSummaryBean.geteSummaryRiskAndMitigationBean().setRiskAge(dateUtil.getDaysDifference(
								new Date(), eSummaryBean.geteSummaryRiskAndMitigationBean().getCreatedDate()));
						eSummaryBean.geteSummaryRiskAndMitigationBean().seteSummary(new ESummaryBean());
						eSummaryBean.geteSummaryRiskAndMitigationBean().geteSummary().seteSummaryId(eSummaryId);
						return eSummaryDAO
								.saveESummaryRiskAndMitigation(eSummaryBean.geteSummaryRiskAndMitigationBean());
					}
				}
			} else {
				logger.info("Executive Summary date is out of Project Scope!");
				baseResponse.setMessage("Executive Summary date is out of Project Scope!");
				return new ESummaryResponse<>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}
		} else {
			logger.info("Unable to proceed, client/portfolio/Project might be empty! ");
			baseResponse.setMessage("Unable to proceed, client/portfolio/Project might be empty!");
			return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}
		logger.info("RagStatus added successfully ");
		baseResponse.setMessage("RagStatus added successfully");
		return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);

	}

	public ESummaryResponse<?> updateESummary(@RequestBody ESJSONRequest esJSONRequest) throws OneConnectDBException {

		logger.info("updateESummary, Request Json: {}", esJSONRequest);
		Long clientId = clientDetailsRepository.findClientId(esJSONRequest.getClient().getId()).getId();
		Long portfolioId = portfolioRepository.findPortfolioById(esJSONRequest.getPortfolio().getId()).getId();
		Long projectId = projectDetailRepository.findProjectDetailId(esJSONRequest.getProject().getId()).getId();
		DateUtil dateUtil = new DateUtil();
		Date startDate = dateUtil.getFormattedDate(OneConnectConstants.DEFAULT_DATE_FORMAT,
				esJSONRequest.getStartDate());

		logger.info("ClientId " + clientId + " portfolioId " + portfolioId + "projectId " + projectId + " Date "
				+ new java.sql.Date(startDate.getTime()));

		Long eSummaryId = eSummaryRepository.findESummaryId(clientId, portfolioId, projectId,
				new java.sql.Date(startDate.getTime()));

		if (eSummaryId != null) {
			if (oneConnectServiceUtility.checkProjectScope(projectId, startDate)) {
				ESummaryBean eSummaryBean = new ESummaryBean();
				eSummaryBean.seteSummaryDate(startDate);
				eSummaryBean.setClientDetail(esJSONRequest.getClient());
				eSummaryBean.setPortfolio(esJSONRequest.getPortfolio());
				eSummaryBean.setProjectDetail(esJSONRequest.getProject());
				eSummaryBean.setRagStatus(esJSONRequest.getRagStatus());
				if (esJSONRequest.getTransactionType() != null) {
					if (esJSONRequest.getTransactionType()
							.equalsIgnoreCase(OneConnectConstants.transactionType.RAGSTATUS.toString())) {
						eSummaryBean.setRagStatus(new RagStatusBean());
						eSummaryBean.getRagStatus().setId(esJSONRequest.getRagStatus().getId());
						return eSummaryDAO.updateESummary(eSummaryBean);
					}

					if (esJSONRequest.getTransactionType()
							.equalsIgnoreCase(OneConnectConstants.transactionType.SUMMARY.toString())) {
						eSummaryBean.seteSummarySummaryBean(new ESummarySummaryBean());
						eSummaryBean.geteSummarySummaryBean().setSummaryId(esJSONRequest.getValueObject().getId());
						eSummaryBean.geteSummarySummaryBean()
								.setDescription(esJSONRequest.getValueObject().getDescription());
						eSummaryBean.geteSummarySummaryBean().seteSummary(new ESummaryBean());
						eSummaryBean.geteSummarySummaryBean().geteSummary().seteSummaryId(eSummaryId);
						return eSummaryDAO.updateESummarySummary(eSummaryBean.geteSummarySummaryBean());
					}
					if (esJSONRequest.getTransactionType()
							.equalsIgnoreCase(OneConnectConstants.transactionType.ACCOMPLISHMENT.toString())) {
						eSummaryBean.seteSummaryAccomplishmentBean(new ESummaryAccomplishmentBean());
						eSummaryBean.geteSummaryAccomplishmentBean()
								.setAccomplishmentId(esJSONRequest.getValueObject().getId());
						eSummaryBean.geteSummaryAccomplishmentBean()
								.setDescription(esJSONRequest.getValueObject().getDescription());
						eSummaryBean.geteSummaryAccomplishmentBean().seteSummary(new ESummaryBean());
						eSummaryBean.geteSummaryAccomplishmentBean().geteSummary().seteSummaryId(eSummaryId);
						return eSummaryDAO.updateESummaryAccomplishment(eSummaryBean.geteSummaryAccomplishmentBean());
					}
					if (esJSONRequest.getTransactionType()
							.equalsIgnoreCase(OneConnectConstants.transactionType.RISKANDMITIGATION.toString())) {
						eSummaryBean.seteSummaryRiskAndMitigationBean(new ESummaryRiskAndMitigationBean());
						eSummaryBean.geteSummaryRiskAndMitigationBean()
								.setRiskAndMitigationId(esJSONRequest.getValueObject().getId());
						eSummaryBean.geteSummaryRiskAndMitigationBean()
								.setRiskDesc(esJSONRequest.getValueObject().getRisk());
						eSummaryBean.geteSummaryRiskAndMitigationBean()
								.setMitigationDesc(esJSONRequest.getValueObject().getMitigation());
						eSummaryBean.geteSummaryRiskAndMitigationBean().seteSummary(new ESummaryBean());
						eSummaryBean.geteSummaryRiskAndMitigationBean().geteSummary().seteSummaryId(eSummaryId);
						return eSummaryDAO
								.updateESummaryRiskAndMitigation(eSummaryBean.geteSummaryRiskAndMitigationBean());
					}
				}
			} else {
				logger.info("Executive Summary date is out of Project Scope!");
				baseResponse.setMessage("Executive Summary date is out of Project Scope!");
				return new ESummaryResponse<>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}
		}
		logger.info("Executive Summary Record not found to update");
		baseResponse.setMessage("Executive Summary Record not found to update");
		return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);

	}

	public ESummaryResponse<?> deleteESummary(@RequestBody ESJSONRequest esJSONRequest) {
		logger.info("deleteteESummary, Request Json: {}", esJSONRequest);
		Long clientId = clientDetailsRepository.findClientId(esJSONRequest.getClient().getId()).getId();
		Long portfolioId = portfolioRepository.findPortfolioById(esJSONRequest.getPortfolio().getId()).getId();
		Long projectId = projectDetailRepository.findProjectDetailId(esJSONRequest.getProject().getId()).getId();
		DateUtil dateUtil = new DateUtil();
		Date startDate = dateUtil.getFormattedDate(OneConnectConstants.DEFAULT_DATE_FORMAT,
				esJSONRequest.getStartDate());
		if (clientId != null && portfolioId != null && projectId != null && esJSONRequest.getValueObject() != null
				&& !OneConnectUtility.isNullOrEmpty(esJSONRequest.getTransactionType())) {
			ESummaryBean eSummaryBean = new ESummaryBean();
			eSummaryBean.seteSummaryDate(startDate);
			eSummaryBean.setClientDetail(esJSONRequest.getClient());
			eSummaryBean.setPortfolio(esJSONRequest.getPortfolio());
			eSummaryBean.setProjectDetail(esJSONRequest.getProject());
			Long eSummaryId = eSummaryRepository.findESummaryId(clientId, portfolioId, projectId,
					new java.sql.Date(startDate.getTime()));
			logger.info("request date {}", esJSONRequest.getStartDate());
			if (eSummaryId == null) {
				// Not able to find eSummary table record to update
				logger.info("Executive Summary Record not found to delete");
				baseResponse.setMessage("Executive Summary Record not found to delete");
				return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
			}

			if (esJSONRequest.getTransactionType()
					.equalsIgnoreCase(OneConnectConstants.transactionType.SUMMARY.toString())) {
				eSummaryBean.seteSummarySummaryBean(new ESummarySummaryBean());
				eSummaryBean.geteSummarySummaryBean().setSummaryId(esJSONRequest.getValueObject().getId());
				eSummaryBean.geteSummarySummaryBean().seteSummary(new ESummaryBean());
				eSummaryBean.geteSummarySummaryBean().geteSummary().seteSummaryId(eSummaryId);
				return eSummaryDAO.deleteESummarySummary(eSummaryBean.geteSummarySummaryBean());
			}
			if (esJSONRequest.getTransactionType()
					.equalsIgnoreCase(OneConnectConstants.transactionType.ACCOMPLISHMENT.toString())) {
				eSummaryBean.seteSummaryAccomplishmentBean(new ESummaryAccomplishmentBean());
				eSummaryBean.geteSummaryAccomplishmentBean()
						.setAccomplishmentId(esJSONRequest.getValueObject().getId());
				eSummaryBean.geteSummaryAccomplishmentBean().seteSummary(new ESummaryBean());
				eSummaryBean.geteSummaryAccomplishmentBean().geteSummary().seteSummaryId(eSummaryId);
				return eSummaryDAO.deleteESummaryAccomplishment(eSummaryBean.geteSummaryAccomplishmentBean());
			}
			if (esJSONRequest.getTransactionType()
					.equalsIgnoreCase(OneConnectConstants.transactionType.RISKANDMITIGATION.toString())) {
				eSummaryBean.seteSummaryRiskAndMitigationBean(new ESummaryRiskAndMitigationBean());
				eSummaryBean.geteSummaryRiskAndMitigationBean()
						.setRiskAndMitigationId(esJSONRequest.getValueObject().getId());
				return eSummaryDAO.deleteESummaryRiskAndMitigation(eSummaryBean.geteSummaryRiskAndMitigationBean());
			}
		}
		logger.info("Executive Summary Record not found to delete");
		baseResponse.setMessage("Executive Summary Record not found to delete");
		return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);

	}

	// post operation for Esummary

	public ESummaryResponse<?> getESummary(@RequestBody ExecutiveSummaryJSONRequest executiveSummaryJSONRequest)
			throws OneConnectDBException {
		logger.info("getESummary, Request Json: {}", executiveSummaryJSONRequest);
		ExecutiveSummaryBean executiveSummaryBean = new ExecutiveSummaryBean();
		executiveSummaryBean.setClientId(executiveSummaryJSONRequest.getClientId());
		executiveSummaryBean.setPortfolioId(executiveSummaryJSONRequest.getPortfolioId());
		executiveSummaryBean.setProjectDetailId(executiveSummaryJSONRequest.getProjectId());
		DateUtil dateUtil = new DateUtil();
		Date startDate = dateUtil.getFormattedDate(OneConnectConstants.DEFAULT_DATE_FORMAT,
				executiveSummaryJSONRequest.getStartDate());
		executiveSummaryBean.seteSummaryDate(startDate);
		if (executiveSummaryBean.getClientId() != null && executiveSummaryBean.getPortfolioId() != null
				&& executiveSummaryBean.getProjectDetailId() != null
				&& executiveSummaryBean.geteSummaryDate() != null) {
			if (oneConnectServiceUtility.checkProjectScope(executiveSummaryBean.getProjectDetailId(),
					executiveSummaryBean.geteSummaryDate())) {

				ExecutiveSummaryJSONResponseBean esJSONResponseBean = eSummaryDAO.getESummary(executiveSummaryBean);
				if (!OneConnectUtility.isNull(esJSONResponseBean)) {
					ExecutiveSummaryJSONResponse esJSONResponse = new ExecutiveSummaryJSONResponse();
					esJSONResponse.setProjectId(esJSONResponseBean.getProjectId());
					esJSONResponse.setProjectName(esJSONResponseBean.getProjectName());
					esJSONResponse.setProjectStartDate(esJSONResponseBean.getProjectStartDate());
					esJSONResponse.setProjectEndDate(esJSONResponseBean.getProjectEndDate());
					esJSONResponse.setDirector(esJSONResponseBean.getDirector());
					esJSONResponse.setManagerLeader(esJSONResponseBean.getManagerLeader());
					esJSONResponse.setProductOwner(esJSONResponseBean.getProductOwner());
					esJSONResponse.setPurpose(esJSONResponseBean.getPurpose());
					esJSONResponse.setScrumMasterFromRythmos(esJSONResponseBean.getScrumMasterFromRythmos());
					esJSONResponse.setRagStatus(esJSONResponseBean.getRagStatus());

					List<EmployeeProjectMappingBean> employeeList = esJSONResponseBean.getResource();

					List<ResourceJSONResponse> resourceJSONResponseList = new ArrayList<ResourceJSONResponse>();
					if (employeeList != null && !employeeList.isEmpty()) {
						for (EmployeeProjectMappingBean employeeResponseBean : employeeList) {
							logger.info("Employee {} ", employeeResponseBean);
							if (oneConnectServiceUtility.checkResourceProjectScope(employeeResponseBean.getId(),
									startDate)) {
								resourceJSONResponseList.add(convertToEmployeeJSONResponse(employeeResponseBean));
							}
						}
					}
					esJSONResponse.setResources(resourceJSONResponseList);
					esJSONResponse.setNumberOfResources(resourceJSONResponseList.size());

					List<ESummarySummaryBean> esSummaryList = esJSONResponseBean.getSummary();
					List<SummaryJSONResponse> summaryJSONResponseList = new ArrayList<SummaryJSONResponse>();
					if (esSummaryList != null && !esSummaryList.isEmpty()) {
						for (ESummarySummaryBean summaryResponseBean : esSummaryList) {
							summaryJSONResponseList.add(convertToSummaryJSONResponse(summaryResponseBean));
						}
					}
					esJSONResponse.setSummary(summaryJSONResponseList);

					List<ESummaryAccomplishmentBean> esAccomplishmentList = esJSONResponseBean.getAccomplishments();
					List<AccomplishmentJSONResponse> accomplishmentJSONResponseList = new ArrayList<AccomplishmentJSONResponse>();
					if (esAccomplishmentList != null && !esAccomplishmentList.isEmpty()) {
						for (ESummaryAccomplishmentBean accomplishmentResponseBean : esAccomplishmentList) {
							accomplishmentJSONResponseList
									.add(convertToAccomplishmentJSONResponse(accomplishmentResponseBean));
						}
					}
					esJSONResponse.setAccomplishments(accomplishmentJSONResponseList);

					List<ESummaryRiskAndMitigationBean> esRiskAndMitigationList = esJSONResponseBean
							.getRiskAndMitigation();
					List<RiskAndMitigationJSONResponse> riskAndMitigationJSONResponseList = new ArrayList<RiskAndMitigationJSONResponse>();
					if (esRiskAndMitigationList != null && !esRiskAndMitigationList.isEmpty()) {
						for (ESummaryRiskAndMitigationBean riskAndMitigationResponseBean : esRiskAndMitigationList) {
							riskAndMitigationJSONResponseList
									.add(convertToRiskAndMitigationJSONResponse(riskAndMitigationResponseBean));
						}
					}

					esJSONResponse.setRisksAndMitigations(riskAndMitigationJSONResponseList);
					logger.info("Got a Executive Summary Report");
					baseResponse.setMessage("Got a Executive Summary Report");
					return new ESummaryResponse<>(esJSONResponse, null, HttpStatus.OK);
				} else {
					logger.info("Executive Summary not found!");
					baseResponse.setMessage("Executive Summary not found!");
					return new ESummaryResponse<>(baseResponse, null, HttpStatus.NOT_FOUND);
				}
			} else {

				logger.info("Executive Summary date is out of Project Scope!");
				baseResponse.setMessage("Executive Summary date is out of Project Scope!");
				return new ESummaryResponse<>(baseResponse, null, HttpStatus.BAD_REQUEST);

			}
		} else {
			logger.info("Unable to proceed, client/portfolio/Project might be empty! ");
			baseResponse.setMessage("Unable to proceed, client/portfolio/Project might be empty!");
			return new ESummaryResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);

		}

	}

	public SummaryJSONResponse convertToSummaryJSONResponse(ESummarySummaryBean eSummarySummaryBean) {

		SummaryJSONResponse summaryJSONResponse = new SummaryJSONResponse();
		summaryJSONResponse.setId(eSummarySummaryBean.getSummaryId());
		summaryJSONResponse.setDescription(eSummarySummaryBean.getDescription());
		return summaryJSONResponse;
	}

	public ResourceJSONResponse convertToEmployeeJSONResponse(EmployeeProjectMappingBean employeeProjectMappingBean) {

		ResourceJSONResponse resourceJSONResponse = new ResourceJSONResponse();
		resourceJSONResponse.setEmpId(employeeProjectMappingBean.getEmployee().getEmployeeId());
		resourceJSONResponse.setName(employeeProjectMappingBean.getEmployee().getEmployeeName());
		resourceJSONResponse.setBillingStatus(employeeProjectMappingBean.getBillingStatus());
		resourceJSONResponse.setPercentageOfUtilization(employeeProjectMappingBean.getUtilization());

		return resourceJSONResponse;
	}

	public AccomplishmentJSONResponse convertToAccomplishmentJSONResponse(
			ESummaryAccomplishmentBean eSummaryAccomplishmentBean) {

		AccomplishmentJSONResponse accomplishmentJSONResponse = new AccomplishmentJSONResponse();
		accomplishmentJSONResponse.setId(eSummaryAccomplishmentBean.getAccomplishmentId());
		accomplishmentJSONResponse.setDescription(eSummaryAccomplishmentBean.getDescription());
		return accomplishmentJSONResponse;
	}

	public RiskAndMitigationJSONResponse convertToRiskAndMitigationJSONResponse(
			ESummaryRiskAndMitigationBean eSummaryRiskAndMitigationBean) {

		RiskAndMitigationJSONResponse riskAndMitigationJSONResponse = new RiskAndMitigationJSONResponse();
		riskAndMitigationJSONResponse.setId(eSummaryRiskAndMitigationBean.getRiskAndMitigationId());
		riskAndMitigationJSONResponse.setRisk(eSummaryRiskAndMitigationBean.getRiskDesc());
		riskAndMitigationJSONResponse.setMitigation(eSummaryRiskAndMitigationBean.getMitigationDesc());
		if (OneConnectConstants.status.OPEN.toString().equalsIgnoreCase(eSummaryRiskAndMitigationBean.getStatus())) {
			riskAndMitigationJSONResponse.setStatus(Boolean.TRUE);
		} else {
			riskAndMitigationJSONResponse.setStatus(Boolean.FALSE);
		}
		DateUtil dateUtil = new DateUtil();
		String createdDate = dateUtil.getFormattedDateString(eSummaryRiskAndMitigationBean.getCreatedDate(),
				OneConnectConstants.DEFAULT_DATE_FORMAT);
		
		String closedDate = dateUtil.getFormattedDateString(eSummaryRiskAndMitigationBean.getClosedDate(),
				OneConnectConstants.DEFAULT_DATE_FORMAT);
		riskAndMitigationJSONResponse.setClosedDate(closedDate);
		riskAndMitigationJSONResponse
				.setRiskAge(dateUtil.getDaysDifference(new Date(), eSummaryRiskAndMitigationBean.getCreatedDate()));
		riskAndMitigationJSONResponse.setCreatedDate(createdDate);
		
		riskAndMitigationJSONResponse
				.setProjectName(eSummaryRiskAndMitigationBean.geteSummary().getProjectDetail().getProjectName());
		riskAndMitigationJSONResponse.setReportType(OneConnectConstants.EXECUTIVE_SUMMARY_REPORT);
		riskAndMitigationJSONResponse.setReportDetails(new ReportDetailJSONResponse());
		riskAndMitigationJSONResponse.getReportDetails()
				.setClientId(eSummaryRiskAndMitigationBean.geteSummary().getClientDetail().getId());
		riskAndMitigationJSONResponse.getReportDetails()
				.setPortfolioId(eSummaryRiskAndMitigationBean.geteSummary().getPortfolio().getId());
		riskAndMitigationJSONResponse.getReportDetails()
				.setProjectId(eSummaryRiskAndMitigationBean.geteSummary().getProjectDetail().getId());
		Date startDate = oneConnectServiceUtility
				.getLatestreportdate(riskAndMitigationJSONResponse.getReportDetails().getProjectId());
		riskAndMitigationJSONResponse.getReportDetails().setStartDate(startDate);

		return riskAndMitigationJSONResponse;
	}

	public List<RiskAndMitigationJSONResponse> getAllRisks() {

		List<ESummaryRiskAndMitigationBean> esRiskAndMitigationList = eSummaryDAO.getAllRisks();

		List<RiskAndMitigationJSONResponse> riskAndMitigationJSONResponseList = new ArrayList<RiskAndMitigationJSONResponse>();

		Object principal = getPrincipal();

		if (principal instanceof UserPrincipal && (((UserPrincipal) principal).getAuthorities().iterator().next()
				.toString().equals(RoleName.ROLE_ADMIN.toString())
				|| ((UserPrincipal) principal).getAuthorities().iterator().next().toString()
						.equals(RoleName.ROLE_EXECUTIVE.toString()))) {

			if (esRiskAndMitigationList != null && !esRiskAndMitigationList.isEmpty()) {
				for (ESummaryRiskAndMitigationBean riskAndMitigationResponseBean : esRiskAndMitigationList) {
					riskAndMitigationJSONResponseList
							.add(convertToRiskAndMitigationJSONResponse(riskAndMitigationResponseBean));
				}
			}
			return riskAndMitigationJSONResponseList;
		} else {
			Long employeeId = ((UserPrincipal) principal).getEmployee().getId();
			List<ProjectDetail> projectList = employeeProjectMappingRepository.findProjectsByEmpId(employeeId);
			List<Long> projectIdList = new ArrayList<>();
			for (ProjectDetail projectDetail : projectList) {
				Long id = projectDetail.getId();
				projectIdList.add(id);
			}

			List<RiskAndMitigationJSONResponse> riskAndMitigationJSONResponseList1 = new ArrayList<RiskAndMitigationJSONResponse>();
			for (ESummaryRiskAndMitigationBean riskAndMitigationResponseBean : esRiskAndMitigationList) {
				if (projectIdList.contains(riskAndMitigationResponseBean.geteSummary().getProjectDetail().getId())) {
					riskAndMitigationJSONResponseList1
							.add(convertToRiskAndMitigationJSONResponse(riskAndMitigationResponseBean));
				}
			}
			return riskAndMitigationJSONResponseList1;
		}
	}

	public RiskResponse<?> updateRiskStatus(@RequestBody RiskAndMitigationJSONRequest riskAndMitigationJSONRequest) {
		ESummaryRiskAndMitigationBean riskAndMitigationBean = new ESummaryRiskAndMitigationBean();
		riskAndMitigationBean.setRiskAndMitigationId(riskAndMitigationJSONRequest.getId());
		return eSummaryDAO.updateRiskStatus(riskAndMitigationBean);
	}

	public List<RiskAndMitigationJSONResponse> getRisksByClient(
			@RequestBody ClientDetailJSONRequest clientDetailJSONRequest) {
		ClientDetailBean clientDetailBean = new ClientDetailBean();
		clientDetailBean.setId(clientDetailJSONRequest.getId());
		List<ESummaryRiskAndMitigationBean> esRiskAndMitigationList = eSummaryDAO.getRisksByClient(clientDetailBean);

		List<RiskAndMitigationJSONResponse> riskAndMitigationJSONResponseList = new ArrayList<RiskAndMitigationJSONResponse>();
		Object principal = getPrincipal();

		if (principal instanceof UserPrincipal && (((UserPrincipal) principal).getAuthorities().iterator().next()
				.toString().equals(RoleName.ROLE_ADMIN.toString())
				|| ((UserPrincipal) principal).getAuthorities().iterator().next().toString()
						.equals(RoleName.ROLE_EXECUTIVE.toString()))) {
			if (esRiskAndMitigationList != null && !esRiskAndMitigationList.isEmpty()) {
				for (ESummaryRiskAndMitigationBean riskAndMitigationResponseBean : esRiskAndMitigationList) {
					riskAndMitigationJSONResponseList
							.add(convertToRiskAndMitigationJSONResponse(riskAndMitigationResponseBean));
				}
			}
			return riskAndMitigationJSONResponseList;
		} else {
			Long employeeId = ((UserPrincipal) principal).getEmployee().getId();
			List<ProjectDetail> projectList = employeeProjectMappingRepository.findProjectsByEmpId(employeeId);
			List<Long> projectIdList = new ArrayList<>();
			for (ProjectDetail projectDetail : projectList) {
				Long id = projectDetail.getId();
				projectIdList.add(id);
			}

			List<RiskAndMitigationJSONResponse> riskAndMitigationJSONResponseList1 = new ArrayList<RiskAndMitigationJSONResponse>();
			if (esRiskAndMitigationList != null && !esRiskAndMitigationList.isEmpty()) {
			for (ESummaryRiskAndMitigationBean riskAndMitigationResponseBean : esRiskAndMitigationList) {
				if (projectIdList.contains(riskAndMitigationResponseBean.geteSummary().getProjectDetail().getId())) {
					riskAndMitigationJSONResponseList1
							.add(convertToRiskAndMitigationJSONResponse(riskAndMitigationResponseBean));
				}
			}
			}
			return riskAndMitigationJSONResponseList1;
		}
	}

	public List<RiskAndMitigationJSONResponse> getRisksByPortfolio(
			@RequestBody PortfolioJSONRequest portfolioJSONRequest) {
		PortfolioBean portfolioBean = new PortfolioBean();
		portfolioBean.setId(portfolioJSONRequest.getId());
		List<ESummaryRiskAndMitigationBean> esRiskAndMitigationList = eSummaryDAO.getRisksByPortfolio(portfolioBean);
		Object principal = getPrincipal();

		if (principal instanceof UserPrincipal && (((UserPrincipal) principal).getAuthorities().iterator().next()
				.toString().equals(RoleName.ROLE_ADMIN.toString())
				|| ((UserPrincipal) principal).getAuthorities().iterator().next().toString()
						.equals(RoleName.ROLE_EXECUTIVE.toString()))) {
			List<RiskAndMitigationJSONResponse> riskAndMitigationJSONResponseList = new ArrayList<RiskAndMitigationJSONResponse>();
			if (esRiskAndMitigationList != null && !esRiskAndMitigationList.isEmpty()) {
				for (ESummaryRiskAndMitigationBean riskAndMitigationResponseBean : esRiskAndMitigationList) {
					riskAndMitigationJSONResponseList
							.add(convertToRiskAndMitigationJSONResponse(riskAndMitigationResponseBean));
				}
			}
			return riskAndMitigationJSONResponseList;
		} else {
			Long employeeId = ((UserPrincipal) principal).getEmployee().getId();
			List<ProjectDetail> projectList = employeeProjectMappingRepository.findProjectsByEmpId(employeeId);
			List<Long> projectIdList = new ArrayList<>();
			for (ProjectDetail projectDetail : projectList) {
				Long id = projectDetail.getId();
				projectIdList.add(id);
			}

			List<RiskAndMitigationJSONResponse> riskAndMitigationJSONResponseList1 = new ArrayList<RiskAndMitigationJSONResponse>();
			if (esRiskAndMitigationList != null && !esRiskAndMitigationList.isEmpty()) {
			for (ESummaryRiskAndMitigationBean riskAndMitigationResponseBean : esRiskAndMitigationList) {
				if (projectIdList.contains(riskAndMitigationResponseBean.geteSummary().getProjectDetail().getId())) {
					riskAndMitigationJSONResponseList1
							.add(convertToRiskAndMitigationJSONResponse(riskAndMitigationResponseBean));
				}
			}
			}
			return riskAndMitigationJSONResponseList1;
		}
	}

	public List<RiskAndMitigationJSONResponse> getRisksByProject(
			@RequestBody ProjectDetailJSONRequest projectDetailJSONRequest) {
		ProjectDetailBean projectDetailBean = new ProjectDetailBean();
		projectDetailBean.setId(projectDetailJSONRequest.getId());
		List<ESummaryRiskAndMitigationBean> esRiskAndMitigationList = eSummaryDAO.getRisksByProject(projectDetailBean);
		Object principal = getPrincipal();

		if (principal instanceof UserPrincipal && (((UserPrincipal) principal).getAuthorities().iterator().next()
				.toString().equals(RoleName.ROLE_ADMIN.toString())
				|| ((UserPrincipal) principal).getAuthorities().iterator().next().toString()
						.equals(RoleName.ROLE_EXECUTIVE.toString()))) {
			List<RiskAndMitigationJSONResponse> riskAndMitigationJSONResponseList = new ArrayList<RiskAndMitigationJSONResponse>();
			if (esRiskAndMitigationList != null && !esRiskAndMitigationList.isEmpty()) {
				for (ESummaryRiskAndMitigationBean riskAndMitigationResponseBean : esRiskAndMitigationList) {
					riskAndMitigationJSONResponseList
							.add(convertToRiskAndMitigationJSONResponse(riskAndMitigationResponseBean));
				}
			}
			return riskAndMitigationJSONResponseList;
		} else {
			Long employeeId = ((UserPrincipal) principal).getEmployee().getId();
			List<ProjectDetail> projectList = employeeProjectMappingRepository.findProjectsByEmpId(employeeId);
			List<Long> projectIdList = new ArrayList<>();
			for (ProjectDetail projectDetail : projectList) {
				Long id = projectDetail.getId();
				projectIdList.add(id);
			}

			List<RiskAndMitigationJSONResponse> riskAndMitigationJSONResponseList1 = new ArrayList<RiskAndMitigationJSONResponse>();
			if (esRiskAndMitigationList != null && !esRiskAndMitigationList.isEmpty()) {
			for (ESummaryRiskAndMitigationBean riskAndMitigationResponseBean : esRiskAndMitigationList) {
				if (projectIdList.contains(riskAndMitigationResponseBean.geteSummary().getProjectDetail().getId())) {
					riskAndMitigationJSONResponseList1
							.add(convertToRiskAndMitigationJSONResponse(riskAndMitigationResponseBean));
				}
			}
			}
			return riskAndMitigationJSONResponseList1;
		}
	}

	public RAGStatusJSONResponse getAllRAGStatus() {
		RAGStatusResponseBean ragStatusResponseBean = eSummaryDAO.getAllRAGStatus();
		List<ESummaryBean> projectEsummaryGreenlist = ragStatusResponseBean.getGreen();
		List<ESummaryBean> projectEsummaryAmberlist = ragStatusResponseBean.getAmber();
		List<ESummaryBean> projectEsummaryRedlist = ragStatusResponseBean.getRed();
		RAGStatusJSONResponse ragStatusJSONResponse = new RAGStatusJSONResponse();

		List<ProjectEsummaryJSONResponse> projectGreenList = new ArrayList<ProjectEsummaryJSONResponse>();
		List<ProjectEsummaryJSONResponse> projectAmberList = new ArrayList<ProjectEsummaryJSONResponse>();
		List<ProjectEsummaryJSONResponse> projectRedList = new ArrayList<ProjectEsummaryJSONResponse>();

		Object principal = getPrincipal();

		if (principal instanceof UserPrincipal && (((UserPrincipal) principal).getAuthorities().iterator().next()
				.toString().equals(RoleName.ROLE_ADMIN.toString())
				|| ((UserPrincipal) principal).getAuthorities().iterator().next().toString()
						.equals(RoleName.ROLE_EXECUTIVE.toString()))) {

			if (projectEsummaryGreenlist != null && !projectEsummaryGreenlist.isEmpty()) {
				for (ESummaryBean eSummaryBean : projectEsummaryGreenlist) {
					projectGreenList.add(convertToProjectJSONResponse(eSummaryBean));
				}
			}
			ragStatusJSONResponse.setGreen(projectGreenList);

			if (projectEsummaryAmberlist != null && !projectEsummaryAmberlist.isEmpty()) {
				for (ESummaryBean eSummaryBean : projectEsummaryAmberlist) {
					projectAmberList.add(convertToProjectJSONResponse(eSummaryBean));
				}
			}
			ragStatusJSONResponse.setAmber(projectAmberList);

			if (projectEsummaryRedlist != null && !projectEsummaryRedlist.isEmpty()) {
				for (ESummaryBean eSummaryBean : projectEsummaryRedlist) {
					projectRedList.add(convertToProjectJSONResponse(eSummaryBean));
				}
			}
			ragStatusJSONResponse.setRed(projectRedList);

			return ragStatusJSONResponse;
		} else {

			RAGStatusJSONResponse ragStatusJSONResponse1 = new RAGStatusJSONResponse();
			Long employeeId = ((UserPrincipal) principal).getEmployee().getId();
			List<ProjectDetail> projectList = employeeProjectMappingRepository.findProjectsByEmpId(employeeId);
			List<Long> projectIdList = new ArrayList<>();
			for (ProjectDetail projectDetail : projectList) {
				Long id = projectDetail.getId();
				projectIdList.add(id);
			}

			List<ProjectEsummaryJSONResponse> greenProjectsList = new ArrayList<ProjectEsummaryJSONResponse>();
			if (projectEsummaryGreenlist != null && !projectEsummaryGreenlist.isEmpty()) {
				for (ESummaryBean eSummaryBean : projectEsummaryGreenlist) {
					if (projectIdList.contains(eSummaryBean.getProjectDetail().getId())) {
						greenProjectsList.add(convertToProjectJSONResponse(eSummaryBean));
					}
				}
			}
			ragStatusJSONResponse1.setGreen(greenProjectsList);

			List<ProjectEsummaryJSONResponse> redProjectsList = new ArrayList<ProjectEsummaryJSONResponse>();
			if (projectEsummaryRedlist != null && !projectEsummaryRedlist.isEmpty()) {
				for (ESummaryBean eSummaryBean : projectEsummaryRedlist) {
					if (projectIdList.contains(eSummaryBean.getProjectDetail().getId())) {
						redProjectsList.add(convertToProjectJSONResponse(eSummaryBean));
					}
				}
			}
			ragStatusJSONResponse1.setRed(redProjectsList);
			List<ProjectEsummaryJSONResponse> amberProjectsList = new ArrayList<ProjectEsummaryJSONResponse>();
			if (projectEsummaryAmberlist != null && !projectEsummaryAmberlist.isEmpty()) {
				for (ESummaryBean eSummaryBean : projectEsummaryAmberlist) {
					if (projectIdList.contains(eSummaryBean.getProjectDetail().getId())) {
						amberProjectsList.add(convertToProjectJSONResponse(eSummaryBean));
					}
				}
			}
			ragStatusJSONResponse1.setAmber(amberProjectsList);

			return ragStatusJSONResponse1;

		}

	}

	public RAGStatusJSONResponse getRAGStatusByClient(@RequestBody ClientDetailJSONRequest clientDetailJSONRequest) {
		ClientDetailBean clientDetailBean = new ClientDetailBean();
		clientDetailBean.setId(clientDetailJSONRequest.getId());
		RAGStatusResponseBean ragStatusResponseBean = eSummaryDAO.getRAGStatusByClient(clientDetailBean);
		List<ESummaryBean> projectDetailGreenlist = ragStatusResponseBean.getGreen();
		List<ESummaryBean> projectDetailAmberlist = ragStatusResponseBean.getAmber();
		List<ESummaryBean> projectDetailRedlist = ragStatusResponseBean.getRed();
		RAGStatusJSONResponse ragStatusJSONResponse = new RAGStatusJSONResponse();

		List<ProjectEsummaryJSONResponse> projectGreenList = new ArrayList<ProjectEsummaryJSONResponse>();
		List<ProjectEsummaryJSONResponse> projectAmberList = new ArrayList<ProjectEsummaryJSONResponse>();
		List<ProjectEsummaryJSONResponse> projectRedList = new ArrayList<ProjectEsummaryJSONResponse>();
		Object principal = getPrincipal();

		if (principal instanceof UserPrincipal && (((UserPrincipal) principal).getAuthorities().iterator().next()
				.toString().equals(RoleName.ROLE_ADMIN.toString())
				|| ((UserPrincipal) principal).getAuthorities().iterator().next().toString()
						.equals(RoleName.ROLE_EXECUTIVE.toString()))) {

			if (projectDetailGreenlist != null && !projectDetailGreenlist.isEmpty()) {
				for (ESummaryBean eSummaryBean : projectDetailGreenlist) {
					projectGreenList.add(convertToProjectJSONResponse(eSummaryBean));
				}
			}
			ragStatusJSONResponse.setGreen(projectGreenList);

			if (projectDetailAmberlist != null && !projectDetailAmberlist.isEmpty()) {
				for (ESummaryBean eSummaryBean : projectDetailAmberlist) {
					projectAmberList.add(convertToProjectJSONResponse(eSummaryBean));
				}
			}
			ragStatusJSONResponse.setAmber(projectAmberList);

			if (projectDetailRedlist != null && !projectDetailRedlist.isEmpty()) {
				for (ESummaryBean eSummaryBean : projectDetailRedlist) {
					projectRedList.add(convertToProjectJSONResponse(eSummaryBean));
				}
			}
			ragStatusJSONResponse.setRed(projectRedList);

			return ragStatusJSONResponse;
		} else {
			RAGStatusJSONResponse ragStatusJSONResponse1 = new RAGStatusJSONResponse();
			Long employeeId = ((UserPrincipal) principal).getEmployee().getId();
			List<ProjectDetail> projectList = employeeProjectMappingRepository.findProjectsByEmpId(employeeId);
			List<Long> projectIdList = new ArrayList<>();
			for (ProjectDetail projectDetail : projectList) {
				Long id = projectDetail.getId();
				projectIdList.add(id);
			}

			List<ProjectEsummaryJSONResponse> greenProjectsList = new ArrayList<ProjectEsummaryJSONResponse>();
			if (projectDetailGreenlist != null && !projectDetailGreenlist.isEmpty()) {
				for (ESummaryBean eSummaryBean : projectDetailGreenlist) {
					if (projectIdList.contains(eSummaryBean.getProjectDetail().getId())) {
						greenProjectsList.add(convertToProjectJSONResponse(eSummaryBean));
					}
				}
			}
			ragStatusJSONResponse1.setGreen(greenProjectsList);

			List<ProjectEsummaryJSONResponse> redProjectsList = new ArrayList<ProjectEsummaryJSONResponse>();
			if (projectDetailRedlist != null && !projectDetailRedlist.isEmpty()) {
				for (ESummaryBean eSummaryBean : projectDetailRedlist) {
					if (projectIdList.contains(eSummaryBean.getProjectDetail().getId())) {
						redProjectsList.add(convertToProjectJSONResponse(eSummaryBean));
					}
				}
			}
			ragStatusJSONResponse1.setRed(redProjectsList);
			List<ProjectEsummaryJSONResponse> amberProjectsList = new ArrayList<ProjectEsummaryJSONResponse>();
			if (projectDetailAmberlist != null && !projectDetailAmberlist.isEmpty()) {
				for (ESummaryBean eSummaryBean : projectDetailAmberlist) {
					if (projectIdList.contains(eSummaryBean.getProjectDetail().getId())) {
						amberProjectsList.add(convertToProjectJSONResponse(eSummaryBean));
					}
				}
			}
			ragStatusJSONResponse1.setAmber(amberProjectsList);

			return ragStatusJSONResponse1;
		}
	}

	public RAGStatusJSONResponse getRAGStatusByPortfolio(@RequestBody PortfolioJSONRequest portfolioJSONRequest) {
		PortfolioBean portfolioBean = new PortfolioBean();
		portfolioBean.setId(portfolioJSONRequest.getId());
		RAGStatusResponseBean ragStatusResponseBean = eSummaryDAO.getRAGStatusByPortfolio(portfolioBean);
		List<ESummaryBean> projectDetailGreenlist = ragStatusResponseBean.getGreen();
		List<ESummaryBean> projectDetailAmberlist = ragStatusResponseBean.getAmber();
		List<ESummaryBean> projectDetailRedlist = ragStatusResponseBean.getRed();
		RAGStatusJSONResponse ragStatusJSONResponse = new RAGStatusJSONResponse();

		List<ProjectEsummaryJSONResponse> projectGreenList = new ArrayList<ProjectEsummaryJSONResponse>();
		List<ProjectEsummaryJSONResponse> projectRedList = new ArrayList<ProjectEsummaryJSONResponse>();
		List<ProjectEsummaryJSONResponse> projectAmberList = new ArrayList<ProjectEsummaryJSONResponse>();

		Object principal = getPrincipal();

		if (principal instanceof UserPrincipal && (((UserPrincipal) principal).getAuthorities().iterator().next()
				.toString().equals(RoleName.ROLE_ADMIN.toString())
				|| ((UserPrincipal) principal).getAuthorities().iterator().next().toString()
						.equals(RoleName.ROLE_EXECUTIVE.toString()))) {
			if (projectDetailGreenlist != null && !projectDetailGreenlist.isEmpty()) {
				for (ESummaryBean eSummaryBean : projectDetailGreenlist) {
					projectGreenList.add(convertToProjectJSONResponse(eSummaryBean));
				}
			}
			ragStatusJSONResponse.setGreen(projectGreenList);

			if (projectDetailAmberlist != null && !projectDetailAmberlist.isEmpty()) {
				for (ESummaryBean eSummaryBean : projectDetailAmberlist) {
					projectAmberList.add(convertToProjectJSONResponse(eSummaryBean));
				}
			}
			ragStatusJSONResponse.setAmber(projectAmberList);

			if (projectDetailRedlist != null && !projectDetailRedlist.isEmpty()) {
				for (ESummaryBean eSummaryBean : projectDetailRedlist) {
					projectRedList.add(convertToProjectJSONResponse(eSummaryBean));
				}
			}
			ragStatusJSONResponse.setRed(projectRedList);

			return ragStatusJSONResponse;
		} else {
			RAGStatusJSONResponse ragStatusJSONResponse1 = new RAGStatusJSONResponse();
			Long employeeId = ((UserPrincipal) principal).getEmployee().getId();
			List<ProjectDetail> projectList = employeeProjectMappingRepository.findProjectsByEmpId(employeeId);
			List<Long> projectIdList = new ArrayList<>();
			for (ProjectDetail projectDetail : projectList) {
				Long id = projectDetail.getId();
				projectIdList.add(id);
			}

			List<ProjectEsummaryJSONResponse> greenProjectsList = new ArrayList<ProjectEsummaryJSONResponse>();
			if (projectDetailGreenlist != null && !projectDetailGreenlist.isEmpty()) {
				for (ESummaryBean eSummaryBean : projectDetailGreenlist) {
					if (projectIdList.contains(eSummaryBean.getProjectDetail().getId())) {
						greenProjectsList.add(convertToProjectJSONResponse(eSummaryBean));
					}
				}
			}
			ragStatusJSONResponse1.setGreen(greenProjectsList);

			List<ProjectEsummaryJSONResponse> redProjectsList = new ArrayList<ProjectEsummaryJSONResponse>();
			if (projectDetailRedlist != null && !projectDetailRedlist.isEmpty()) {
				for (ESummaryBean eSummaryBean : projectDetailRedlist) {
					if (projectIdList.contains(eSummaryBean.getProjectDetail().getId())) {
						redProjectsList.add(convertToProjectJSONResponse(eSummaryBean));
					}
				}
			}
			ragStatusJSONResponse1.setRed(redProjectsList);
			List<ProjectEsummaryJSONResponse> amberProjectsList = new ArrayList<ProjectEsummaryJSONResponse>();
			if (projectDetailAmberlist != null && !projectDetailAmberlist.isEmpty()) {
				for (ESummaryBean eSummaryBean : projectDetailAmberlist) {
					if (projectIdList.contains(eSummaryBean.getProjectDetail().getId())) {
						amberProjectsList.add(convertToProjectJSONResponse(eSummaryBean));
					}
				}
			}
			ragStatusJSONResponse1.setAmber(amberProjectsList);

			return ragStatusJSONResponse1;
		}
	}

	public ProjectEsummaryJSONResponse convertToProjectJSONResponse(ESummaryBean eSummaryBean) {

		ProjectEsummaryJSONResponse projectEsummaryJSONResponse = new ProjectEsummaryJSONResponse();
		projectEsummaryJSONResponse.setProjectName(eSummaryBean.getProjectDetail().getProjectName());
		projectEsummaryJSONResponse.setReportType(OneConnectConstants.EXECUTIVE_SUMMARY_REPORT);
		projectEsummaryJSONResponse.setReportDetails(new ReportDetailJSONResponse());
		projectEsummaryJSONResponse.getReportDetails()
				.setClientId(eSummaryBean.getProjectDetail().getPortfolio().getClientDetail().getId());
		projectEsummaryJSONResponse.getReportDetails()
				.setPortfolioId(eSummaryBean.getProjectDetail().getPortfolio().getId());
		projectEsummaryJSONResponse.getReportDetails().setProjectId(eSummaryBean.getProjectDetail().getId());
		projectEsummaryJSONResponse.getReportDetails().setStartDate(eSummaryBean.geteSummaryDate());
		return projectEsummaryJSONResponse;
	}

}
