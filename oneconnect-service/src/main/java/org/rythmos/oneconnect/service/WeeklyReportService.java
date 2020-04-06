package org.rythmos.oneconnect.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.rythmos.oneconnect.bean.ClientDetailBean;
import org.rythmos.oneconnect.bean.EmployeeProjectMappingBean;
import org.rythmos.oneconnect.bean.PortfolioBean;
import org.rythmos.oneconnect.bean.ProjectDetailBean;
import org.rythmos.oneconnect.bean.RagStatusBean;
import org.rythmos.oneconnect.bean.WRApprovalReportBean;
import org.rythmos.oneconnect.bean.WRBean;
import org.rythmos.oneconnect.bean.WRCommentBean;
import org.rythmos.oneconnect.bean.WRImageBean;
import org.rythmos.oneconnect.bean.WRMajorUpdateBean;
import org.rythmos.oneconnect.bean.WRRiskAndMitigationBean;
import org.rythmos.oneconnect.bean.WeeklyReportBean;
import org.rythmos.oneconnect.bean.WeeklyReportJSONResponseBean;
import org.rythmos.oneconnect.constant.OneConnectConstants;
import org.rythmos.oneconnect.controller.NotificationController;
import org.rythmos.oneconnect.dao.WeeklyReportDAO;
import org.rythmos.oneconnect.exception.OneConnectApplicationException;
import org.rythmos.oneconnect.exception.OneConnectDBException;
import org.rythmos.oneconnect.json.request.IdJSONRequest;
import org.rythmos.oneconnect.json.request.NotificationJSONRequest;
import org.rythmos.oneconnect.json.request.WRApprovalJSONRequest;
import org.rythmos.oneconnect.json.request.WRJSONRequest;
import org.rythmos.oneconnect.json.request.WeeklyReportJSONRequest;
import org.rythmos.oneconnect.json.response.BaseResponse;
import org.rythmos.oneconnect.json.response.ResourceJSONResponse;
import org.rythmos.oneconnect.json.response.WRApprovalReportJSONResponse;
import org.rythmos.oneconnect.json.response.WRCommentJSONResponse;
import org.rythmos.oneconnect.json.response.WRImageJSONResponse;
import org.rythmos.oneconnect.json.response.WRMajorUpdateJSONResponse;
import org.rythmos.oneconnect.json.response.WRRiskAndMitigationJSONResponse;
import org.rythmos.oneconnect.json.response.WeeklyReportJSONResponse;
import org.rythmos.oneconnect.response.WeeklyReportResponse;
import org.rythmos.oneconnect.util.DateUtil;
import org.rythmos.oneconnect.util.OneConnectServiceUtility;
import org.rythmos.oneconnect.util.OneConnectUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Service
public class WeeklyReportService {

	public static Logger logger = LoggerFactory.getLogger(AdminService.class);

	@Autowired
	private WeeklyReportDAO weeklyReportDAO;

	@Autowired
	private OneConnectServiceUtility oneConnectServiceUtility;

	@Autowired
	private BaseResponse baseResponse;

	@Autowired
	private NotificationController notificationController;

	@Autowired
	private NotificationJSONRequest notificationJSONRequest;

	public WeeklyReportResponse<?> saveWeeklyReport(@RequestBody WRJSONRequest wrJSONRequest)
			throws OneConnectApplicationException {
		try {
			logger.info("saveWeeklyReport, Request Json: {}", wrJSONRequest);
			WRBean wrBean = new WRBean();
			wrBean.setClientDetail(wrJSONRequest.getClient());
			wrBean.setPortfolio(wrJSONRequest.getPortfolio());
			wrBean.setProjectDetail(wrJSONRequest.getProject());
			Long clientId = weeklyReportDAO.getClientId(wrBean);
			Long portfolioId = weeklyReportDAO.getPortfolioId(wrBean);
			Long projectId = weeklyReportDAO.getProjectId(wrBean);
			DateUtil dateUtil = new DateUtil();
			Date wReportDate = dateUtil.getFormattedDate(OneConnectConstants.DEFAULT_DATE_FORMAT,
					wrJSONRequest.getStartDate());

			if (null != clientId && null != portfolioId && null != projectId) {
				wrBean.setwReportDate(wReportDate);
				wrBean.setRagStatus(wrJSONRequest.getRagStatus());

				Long weeklyReportId = weeklyReportDAO.getWeeklyReportId(wrBean);
				if (oneConnectServiceUtility.checkProjectScope(projectId, wReportDate)) {
					if (weeklyReportId == null) {
						// insert in eSummary table and get the eSummaryId
						weeklyReportDAO.saveWeeklyReport(wrBean);
						weeklyReportId = weeklyReportDAO.getWeeklyReportId(wrBean);
					}

					logger.info("weeklyReportId is::{}", weeklyReportId);

					if (wrJSONRequest.getTransactionType() != null) {

						if (wrJSONRequest.getTransactionType()
								.equalsIgnoreCase(OneConnectConstants.transactionType.MAJORUPDATE.toString())) {
							wrBean.setWrMajorUpdateBean(new WRMajorUpdateBean());
							wrBean.getWrMajorUpdateBean()
									.setDescription(wrJSONRequest.getValueObject().getDescription());
							wrBean.getWrMajorUpdateBean().setWeeklyReport(new WRBean());
							wrBean.getWrMajorUpdateBean().getWeeklyReport().setId(weeklyReportId);
							return weeklyReportDAO.saveWRMajorUpdates(wrBean.getWrMajorUpdateBean());
						}
						if (wrJSONRequest.getTransactionType()
								.equalsIgnoreCase(OneConnectConstants.transactionType.RISKANDMITIGATION.toString())) {
							wrBean.setWrRiskAndMitigationBean(new WRRiskAndMitigationBean());
							wrBean.getWrRiskAndMitigationBean().setRiskDesc(wrJSONRequest.getValueObject().getRisk());
							wrBean.getWrRiskAndMitigationBean()
									.setMitigationDesc(wrJSONRequest.getValueObject().getMitigation());
							wrBean.getWrRiskAndMitigationBean().setWeeklyReport(new WRBean());
							wrBean.getWrRiskAndMitigationBean().getWeeklyReport().setId(weeklyReportId);
							return weeklyReportDAO.saveWRRiskAndMitigation(wrBean.getWrRiskAndMitigationBean());
						}
						if (wrJSONRequest.getTransactionType()
								.equalsIgnoreCase(OneConnectConstants.transactionType.COMMENT.toString())) {
							wrBean.setWrCommentBean(new WRCommentBean());
							wrBean.getWrCommentBean().setDescription(wrJSONRequest.getValueObject().getDescription());
							wrBean.getWrCommentBean().setWeeklyReport(new WRBean());
							wrBean.getWrCommentBean().getWeeklyReport().setId(weeklyReportId);
							return weeklyReportDAO.saveWRComments(wrBean.getWrCommentBean());
						}
					}

				} else {
					logger.info("Weekly Report Date is out of Project Scope!");
					baseResponse.setMessage("Weekly Report Date is out of Project Scope!");
					return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
				}
			} else {
				logger.info("Unable to proceed, client/portfolio/Project might be empty! ");
				baseResponse.setMessage("Unable to proceed, client/portfolio/Project might be empty!");
				return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}
			logger.info("RagStatus added successfully ");
			baseResponse.setMessage("RagStatus added successfully");
			return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);

		} catch (OneConnectDBException ode) {
			logger.error(ode.getMessage());
			throw new OneConnectApplicationException(ode.getMessage());
		} catch (NullPointerException ne) {
			logger.info("Bad Request,Fields can't be empty");
			throw new OneConnectApplicationException("Bad Request,Fields can't be empty");
		} catch (Exception e) {
			logger.error("Unknown service Error");
			throw new OneConnectApplicationException("Unknown Error");
		}

	}

	public WeeklyReportResponse<?> updateWeeklyReport(@RequestBody WRJSONRequest wrJSONRequest)
			throws OneConnectApplicationException {

		logger.info("updateWeeklyReport, Request Json: {}", wrJSONRequest);
		try {
			WRBean wrBean = new WRBean();
			wrBean.setClientDetail(wrJSONRequest.getClient());
			wrBean.setPortfolio(wrJSONRequest.getPortfolio());
			wrBean.setProjectDetail(wrJSONRequest.getProject());
			DateUtil dateUtil = new DateUtil();
			Date wReportDate = dateUtil.getFormattedDate(OneConnectConstants.DEFAULT_DATE_FORMAT,
					wrJSONRequest.getStartDate());
			wrBean.setwReportDate(wReportDate);
			Long weeklyReportId = weeklyReportDAO.getWeeklyReportId(wrBean);

			if (weeklyReportId != null) {

				wrBean.setRagStatus(wrJSONRequest.getRagStatus());
				if (wrJSONRequest.getTransactionType() != null) {
					if (wrJSONRequest.getTransactionType()
							.equalsIgnoreCase(OneConnectConstants.transactionType.RAGSTATUS.toString())) {
						wrBean.setRagStatus(new RagStatusBean());
						wrBean.getRagStatus().setId(wrJSONRequest.getRagStatus().getId());
						return weeklyReportDAO.updateWeeklyReport(wrBean);
					}

					if (wrJSONRequest.getTransactionType()
							.equalsIgnoreCase(OneConnectConstants.transactionType.MAJORUPDATE.toString())) {
						wrBean.setWrMajorUpdateBean(new WRMajorUpdateBean());
						wrBean.getWrMajorUpdateBean().setId(wrJSONRequest.getValueObject().getId());
						wrBean.getWrMajorUpdateBean().setDescription(wrJSONRequest.getValueObject().getDescription());
						wrBean.getWrMajorUpdateBean().setWeeklyReport(new WRBean());
						wrBean.getWrMajorUpdateBean().getWeeklyReport().setId(weeklyReportId);
						return weeklyReportDAO.updateWRMajorUpdates(wrBean.getWrMajorUpdateBean());
					}
					if (wrJSONRequest.getTransactionType()
							.equalsIgnoreCase(OneConnectConstants.transactionType.COMMENT.toString())) {
						wrBean.setWrCommentBean(new WRCommentBean());
						wrBean.getWrCommentBean().setId(wrJSONRequest.getValueObject().getId());
						wrBean.getWrCommentBean().setDescription(wrJSONRequest.getValueObject().getDescription());
						wrBean.getWrCommentBean().setWeeklyReport(new WRBean());
						wrBean.getWrCommentBean().getWeeklyReport().setId(weeklyReportId);
						return weeklyReportDAO.updateWRComments(wrBean.getWrCommentBean());
					}
					if (wrJSONRequest.getTransactionType()
							.equalsIgnoreCase(OneConnectConstants.transactionType.RISKANDMITIGATION.toString())) {
						wrBean.setWrRiskAndMitigationBean(new WRRiskAndMitigationBean());
						wrBean.getWrRiskAndMitigationBean().setId(wrJSONRequest.getValueObject().getId());
						wrBean.getWrRiskAndMitigationBean().setRiskDesc(wrJSONRequest.getValueObject().getRisk());
						wrBean.getWrRiskAndMitigationBean()
								.setMitigationDesc(wrJSONRequest.getValueObject().getMitigation());
						wrBean.getWrRiskAndMitigationBean().setWeeklyReport(new WRBean());
						wrBean.getWrRiskAndMitigationBean().getWeeklyReport().setId(weeklyReportId);
						return weeklyReportDAO.updateWRRiskAndMitigation(wrBean.getWrRiskAndMitigationBean());
					}
				}
			}
			logger.info("Weekly Report not found to update");
			baseResponse.setMessage("Weekly Report not found to update");
			return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);

		} catch (OneConnectDBException ode) {
			logger.error(ode.getMessage());
			throw new OneConnectApplicationException(ode.getMessage());
		} catch (NullPointerException ne) {
			logger.info("Bad Request,Fields can't be empty");
			throw new OneConnectApplicationException("Bad Request,Fields can't be empty");
		} catch (Exception e) {
			logger.error("Unknown service Error");
			throw new OneConnectApplicationException("Unknown Error");
		}
	}

	public WeeklyReportResponse<?> deleteWeeklyReport(@RequestBody WRJSONRequest wrJSONRequest)
			throws OneConnectApplicationException {
		logger.info("deleteteESummary, Request Json: {}", wrJSONRequest);
		try {
			WRBean wrBean = new WRBean();
			wrBean.setClientDetail(wrJSONRequest.getClient());
			wrBean.setPortfolio(wrJSONRequest.getPortfolio());
			wrBean.setProjectDetail(wrJSONRequest.getProject());
			Long clientId = weeklyReportDAO.getClientId(wrBean);
			Long portfolioId = weeklyReportDAO.getPortfolioId(wrBean);
			Long projectId = weeklyReportDAO.getProjectId(wrBean);
			DateUtil dateUtil = new DateUtil();
			Date wReportDate = dateUtil.getFormattedDate(OneConnectConstants.DEFAULT_DATE_FORMAT,
					wrJSONRequest.getStartDate());
			if (clientId != null && portfolioId != null && projectId != null && wrJSONRequest.getValueObject() != null
					&& !OneConnectUtility.isNullOrEmpty(wrJSONRequest.getTransactionType())) {
				wrBean.setwReportDate(wReportDate);
				Long weeklyReportId = weeklyReportDAO.getWeeklyReportId(wrBean);
				logger.info("request date", wrJSONRequest.getStartDate());
				if (weeklyReportId == null) {
					// Not able to find weekly Report table record to update
					logger.info("Weekly Report not found to delete");
					baseResponse.setMessage("Weekly Report not found to delete");
					return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
				}

				if (wrJSONRequest.getTransactionType()
						.equalsIgnoreCase(OneConnectConstants.transactionType.MAJORUPDATE.toString())) {
					wrBean.setWrMajorUpdateBean(new WRMajorUpdateBean());
					wrBean.getWrMajorUpdateBean().setId(wrJSONRequest.getValueObject().getId());
					return weeklyReportDAO.deleteWRMajorUpdates(wrBean.getWrMajorUpdateBean());
				}
				if (wrJSONRequest.getTransactionType()
						.equalsIgnoreCase(OneConnectConstants.transactionType.COMMENT.toString())) {
					wrBean.setWrCommentBean(new WRCommentBean());
					wrBean.getWrCommentBean().setId(wrJSONRequest.getValueObject().getId());
					return weeklyReportDAO.deleteWRComments(wrBean.getWrCommentBean());
				}
				if (wrJSONRequest.getTransactionType()
						.equalsIgnoreCase(OneConnectConstants.transactionType.RISKANDMITIGATION.toString())) {
					wrBean.setWrRiskAndMitigationBean(new WRRiskAndMitigationBean());
					wrBean.getWrRiskAndMitigationBean().setId(wrJSONRequest.getValueObject().getId());
					return weeklyReportDAO.deleteWRRiskAndMitigation(wrBean.getWrRiskAndMitigationBean());
				}
			}
			logger.info("Weekly Report not found to delete!");
			baseResponse.setMessage("\"Weekly Report not found to delete!");
			return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);

		} catch (OneConnectDBException ode) {
			logger.error(ode.getMessage());
			throw new OneConnectApplicationException(ode.getMessage());
		} catch (NullPointerException ne) {
			logger.info("Bad Request,Fields can't be empty");
			throw new OneConnectApplicationException("Bad Request,Fields can't be empty");
		} catch (Exception e) {
			logger.error("Unknown service Error");
			throw new OneConnectApplicationException("Unknown Error");
		}
	}

	public WeeklyReportResponse<?> getWeeklyReport(@RequestBody WeeklyReportJSONRequest weeklyReportJSONRequest)
			throws OneConnectApplicationException {
		logger.info("Get Weekly Report, Request Json: {}", weeklyReportJSONRequest);
		try {
			WeeklyReportBean weeklyReportBean = new WeeklyReportBean();
			weeklyReportBean.setClientId(weeklyReportJSONRequest.getClientId());
			weeklyReportBean.setPortfolioId(weeklyReportJSONRequest.getPortfolioId());
			weeklyReportBean.setProjectDetailId(weeklyReportJSONRequest.getProjectId());
			DateUtil dateUtil = new DateUtil();
			Date wReportDate = dateUtil.getFormattedDate(OneConnectConstants.DEFAULT_DATE_FORMAT,
					weeklyReportJSONRequest.getStartDate());
			weeklyReportBean.setwReportDate(wReportDate);

			if (oneConnectServiceUtility.checkProjectScope(weeklyReportBean.getProjectDetailId(), wReportDate)) {
				logger.info("Entered into project scope");
				WeeklyReportJSONResponseBean wrJSONResponseBean = weeklyReportDAO.getWeeklyReport(weeklyReportBean);
				if (!OneConnectUtility.isNull(wrJSONResponseBean)) {
					WeeklyReportJSONResponse wrJSONResponse = new WeeklyReportJSONResponse();
					wrJSONResponse.setProjectId(wrJSONResponseBean.getProjectId());
					wrJSONResponse.setProjectName(wrJSONResponseBean.getProjectName());
					wrJSONResponse.setProjectStartDate(wrJSONResponseBean.getProjectStartDate());
					wrJSONResponse.setProjectEndDate(wrJSONResponseBean.getProjectEndDate());
					wrJSONResponse.setDirector(wrJSONResponseBean.getDirector());
					wrJSONResponse.setManagerLeader(wrJSONResponseBean.getManagerLeader());
					wrJSONResponse.setProductOwner(wrJSONResponseBean.getProductOwner());
					wrJSONResponse.setPurpose(wrJSONResponseBean.getPurpose());
					wrJSONResponse.setScrumMasterFromRythmos(wrJSONResponseBean.getScrumMasterFromRythmos());
					wrJSONResponse.setRagStatus(wrJSONResponseBean.getRagStatus());

					List<EmployeeProjectMappingBean> employeeList = wrJSONResponseBean.getResource();
					List<ResourceJSONResponse> resourceJSONResponseList = new ArrayList<ResourceJSONResponse>();

					if (employeeList != null && !employeeList.isEmpty()) {
						for (EmployeeProjectMappingBean employeeResponseBean : employeeList) {
							logger.info("Employee {} ", employeeResponseBean);
							if (oneConnectServiceUtility.checkResourceProjectScope(employeeResponseBean.getId(),
									wReportDate)) {
								resourceJSONResponseList.add(convertToEmployeeJSONResponse(employeeResponseBean));
							}
						}
					}

					wrJSONResponse.setResources(resourceJSONResponseList);
					wrJSONResponse.setNumberOfResources(resourceJSONResponseList.size());

					List<WRMajorUpdateBean> wrMajorUpdateList = wrJSONResponseBean.getMajorUpdates();
					List<WRMajorUpdateJSONResponse> wrMajorUpdateJSONResponseList = new ArrayList<WRMajorUpdateJSONResponse>();
					if (wrMajorUpdateList != null && !wrMajorUpdateList.isEmpty()) {
						for (WRMajorUpdateBean wrMajorUpdateBean : wrMajorUpdateList) {
							wrMajorUpdateJSONResponseList.add(convertToWRMajorUpdateJSONResponse(wrMajorUpdateBean));
						}
					}
					wrJSONResponse.setMajorUpdates(wrMajorUpdateJSONResponseList);

					List<WRCommentBean> wrCommentsList = wrJSONResponseBean.getComments();
					List<WRCommentJSONResponse> wrCommentJSONResponseList = new ArrayList<WRCommentJSONResponse>();
					if (wrCommentsList != null && wrCommentsList.size() > 0) {
						for (WRCommentBean wrCommentsBean : wrCommentsList) {
							wrCommentJSONResponseList.add(convertToWRCommentJSONResponse(wrCommentsBean));
						}
					}
					wrJSONResponse.setComments(wrCommentJSONResponseList);

					List<WRRiskAndMitigationBean> wrRiskAndMitigationList = wrJSONResponseBean.getRiskAndMitigations();
					List<WRRiskAndMitigationJSONResponse> wrriskAndMitigationJSONResponseList = new ArrayList<WRRiskAndMitigationJSONResponse>();
					if (wrRiskAndMitigationList != null && wrRiskAndMitigationList.size() > 0) {
						for (WRRiskAndMitigationBean wrriskAndMitigationBean : wrRiskAndMitigationList) {
							wrriskAndMitigationJSONResponseList
									.add(convertToWRRiskAndMitigationJSONResponse(wrriskAndMitigationBean));
						}
					}

					wrJSONResponse.setRisksAndMitigations(wrriskAndMitigationJSONResponseList);
					logger.info("Weekly Report found");
					baseResponse.setMessage("Weekly Report found");
					return new WeeklyReportResponse<>(wrJSONResponse, null, HttpStatus.OK);
				}
			} else {
				logger.info("Weekly Report Date is out of Project Scope!");
				baseResponse.setMessage("Weekly Report Date is out of Project Scope!");
				return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}
			logger.info("Weekly Report not found!");
			baseResponse.setMessage("Weekly Report not found!");
			return new WeeklyReportResponse<>(baseResponse, null, HttpStatus.NOT_FOUND);
		} catch (OneConnectDBException ode) {
			logger.error(ode.getMessage());
			throw new OneConnectApplicationException(ode.getMessage());
		} catch (NullPointerException ne) {
			logger.info("Bad Request,Fields can't be empty");
			throw new OneConnectApplicationException("Bad Request,Fields can't be empty");
		} catch (Exception e) {
			logger.error("Unknown service Error");
			throw new OneConnectApplicationException("Unknown Error");
		}
	}

	public WeeklyReportResponse<?> getWeeklyReportApprovalStatus(
			@RequestBody WeeklyReportJSONRequest weeklyReportJSONRequest) throws OneConnectApplicationException {
		logger.info("Get Weekly Report, Request Json: {}", weeklyReportJSONRequest);
		try {
			WeeklyReportBean weeklyReportBean = new WeeklyReportBean();
			weeklyReportBean.setClientId(weeklyReportJSONRequest.getClientId());
			weeklyReportBean.setPortfolioId(weeklyReportJSONRequest.getPortfolioId());
			weeklyReportBean.setProjectDetailId(weeklyReportJSONRequest.getProjectId());
			DateUtil dateUtil = new DateUtil();
			Date wReportDate = dateUtil.getFormattedDate(OneConnectConstants.DEFAULT_DATE_FORMAT,
					weeklyReportJSONRequest.getStartDate());
			weeklyReportBean.setwReportDate(wReportDate);

			if (oneConnectServiceUtility.checkProjectScope(weeklyReportBean.getProjectDetailId(), wReportDate)) {
				logger.info("Entered into project scope");
				WRApprovalReportJSONResponse wRApprovalReportJSONResponse = weeklyReportDAO
						.getWeeklyReportApprovalStatus(weeklyReportBean);
				if (!OneConnectUtility.isNull(wRApprovalReportJSONResponse)) {

					return new WeeklyReportResponse<>(wRApprovalReportJSONResponse, null, HttpStatus.OK);

				}
			} else {
				logger.info("Weekly Report Date is out of Project Scope!");
				baseResponse.setMessage("Weekly Report Date is out of Project Scope!");
				return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}
			logger.info("Weekly Report not found!");
			baseResponse.setMessage("Weekly Report not found!");
			return new WeeklyReportResponse<>(baseResponse, null, HttpStatus.NOT_FOUND);
		} catch (OneConnectDBException ode) {
			logger.error(ode.getMessage());
			throw new OneConnectApplicationException(ode.getMessage());
		} catch (NullPointerException ne) {
			logger.info("Bad Request,Fields can't be empty");
			throw new OneConnectApplicationException("Bad Request,Fields can't be empty");
		} catch (Exception e) {
			logger.error("Unknown service Error");
			throw new OneConnectApplicationException("Unknown Error");
		}
	}

	public WeeklyReportResponse<?> getWeeklyReportImages(@RequestBody WeeklyReportJSONRequest weeklyReportJSONRequest)
			throws OneConnectApplicationException {
		logger.info("Get Weekly Report, Request Json: {}", weeklyReportJSONRequest);
		try {
			WeeklyReportBean weeklyReportBean = new WeeklyReportBean();
			weeklyReportBean.setClientId(weeklyReportJSONRequest.getClientId());
			weeklyReportBean.setPortfolioId(weeklyReportJSONRequest.getPortfolioId());
			weeklyReportBean.setProjectDetailId(weeklyReportJSONRequest.getProjectId());
			DateUtil dateUtil = new DateUtil();
			Date wReportDate = dateUtil.getFormattedDate(OneConnectConstants.DEFAULT_DATE_FORMAT,
					weeklyReportJSONRequest.getStartDate());
			weeklyReportBean.setwReportDate(wReportDate);

			if (oneConnectServiceUtility.checkProjectScope(weeklyReportBean.getProjectDetailId(), wReportDate)) {

				List<WRImageBean> imageList = weeklyReportDAO.getWeeklyReportImages(weeklyReportBean);
				List<WRImageJSONResponse> WRImageJSONResponseList = new ArrayList<WRImageJSONResponse>();
				for (WRImageBean wRImageBean : imageList) {
					WRImageJSONResponse wrImageJSONResponse = new WRImageJSONResponse();
					wrImageJSONResponse.setId(wRImageBean.getId());
					wrImageJSONResponse.setFileType(wRImageBean.getFileType());
					wrImageJSONResponse.setFileName(wRImageBean.getFileName());
					wrImageJSONResponse.setData(Base64.encodeBase64String(wRImageBean.getData()));
					WRImageJSONResponseList.add(wrImageJSONResponse);
					logger.info("Image {}", wrImageJSONResponse);
				}

				return new WeeklyReportResponse<>(WRImageJSONResponseList, null, HttpStatus.OK);

			} else {
				logger.info("Weekly Report Date is out of Project Scope!");
				baseResponse.setMessage("Weekly Report Date is out of Project Scope!");
				return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}
		} catch (OneConnectDBException ode) {
			logger.error(ode.getMessage());
			throw new OneConnectApplicationException(ode.getMessage());
		} catch (NullPointerException ne) {
			logger.info("Bad Request,Fields can't be empty");
			throw new OneConnectApplicationException("Bad Request,Fields can't be empty");
		} catch (Exception e) {
			logger.error("Unknown service Error");
			throw new OneConnectApplicationException("Unknown Error");
		}

	}

	public ResourceJSONResponse convertToEmployeeJSONResponse(EmployeeProjectMappingBean employeeProjectMappingBean) {

		ResourceJSONResponse resourceJSONResponse = new ResourceJSONResponse();
		resourceJSONResponse.setEmpId(employeeProjectMappingBean.getEmployee().getEmployeeId());
		resourceJSONResponse.setName(employeeProjectMappingBean.getEmployee().getEmployeeName());
		resourceJSONResponse.setBillingStatus(employeeProjectMappingBean.getBillingStatus());
		resourceJSONResponse.setPercentageOfUtilization(employeeProjectMappingBean.getUtilization());

		return resourceJSONResponse;
	}

	public WRMajorUpdateJSONResponse convertToWRMajorUpdateJSONResponse(WRMajorUpdateBean wrMajorUpdatesBean) {

		WRMajorUpdateJSONResponse wrMajorUpdateJSONResponse = new WRMajorUpdateJSONResponse();
		wrMajorUpdateJSONResponse.setId(wrMajorUpdatesBean.getId());
		wrMajorUpdateJSONResponse.setDescription(wrMajorUpdatesBean.getDescription());
		return wrMajorUpdateJSONResponse;
	}

	public WRCommentJSONResponse convertToWRCommentJSONResponse(WRCommentBean wrCommentsBean) {

		WRCommentJSONResponse wrCommentJSONResponse = new WRCommentJSONResponse();
		wrCommentJSONResponse.setId(wrCommentsBean.getId());
		wrCommentJSONResponse.setDescription(wrCommentsBean.getDescription());
		return wrCommentJSONResponse;
	}

	public WRRiskAndMitigationJSONResponse convertToWRRiskAndMitigationJSONResponse(
			WRRiskAndMitigationBean wrRiskAndMitigationBean) {

		WRRiskAndMitigationJSONResponse wrRiskAndMitigationJSONResponse = new WRRiskAndMitigationJSONResponse();
		wrRiskAndMitigationJSONResponse.setId(wrRiskAndMitigationBean.getId());
		wrRiskAndMitigationJSONResponse.setRisk(wrRiskAndMitigationBean.getRiskDesc());
		wrRiskAndMitigationJSONResponse.setMitigation(wrRiskAndMitigationBean.getMitigationDesc());
		return wrRiskAndMitigationJSONResponse;
	}

	public WeeklyReportResponse<?> uploadWeeklyReportImage(@RequestParam("file") MultipartFile file,
			@RequestParam("clientId") String clientId, @RequestParam("portfolioId") String portfolioId,
			@RequestParam("projectId") String projectId, @RequestParam("startDate") String startDate)
			throws OneConnectApplicationException {
		try {
			WRImageBean wrImageBean = new WRImageBean();
			wrImageBean.setFileType(file.getContentType());
			wrImageBean.setData(file.getBytes());
			wrImageBean.setFileName(file.getOriginalFilename());
			wrImageBean.setWeeklyReport(new WRBean());
			DateUtil dateUtil = new DateUtil();
			Date wReportDate = dateUtil.getFormattedDate(OneConnectConstants.DEFAULT_DATE_FORMAT, startDate);
			WRBean wrBean = new WRBean();
			wrBean.setClientDetail(new ClientDetailBean());
			wrBean.setPortfolio(new PortfolioBean());
			wrBean.setProjectDetail(new ProjectDetailBean());
			wrBean.getClientDetail().setId(Long.parseLong(clientId));
			wrBean.getPortfolio().setId(Long.parseLong(portfolioId));
			wrBean.getProjectDetail().setId(Long.parseLong(projectId));
			wrBean.setwReportDate(wReportDate);
			Long weeklyReportId = weeklyReportDAO.getWeeklyReportId(wrBean);
			wrImageBean.getWeeklyReport().setId(weeklyReportId);
			if (weeklyReportId != null) {
				return weeklyReportDAO.uploadWeeklyReportImage(wrImageBean);
			} else {
				logger.info("Please add RagStatus/MajorUpdates/Comments/RiskAndMitigation!");
				baseResponse.setMessage("Please add RagStatus/MajorUpdates/Comments/RiskAndMitigation!");
				return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}
		} catch (OneConnectDBException ode) {
			logger.error(ode.getMessage());
			throw new OneConnectApplicationException(ode.getMessage());
		} catch (NullPointerException ne) {
			logger.info("Bad Request,Fields can't be empty");
			throw new OneConnectApplicationException("Bad Request,Fields can't be empty");
		} catch (Exception e) {
			logger.error("Unknown service Error");
			throw new OneConnectApplicationException("Unknown Error");
		}
	}

	public WeeklyReportResponse<?> deleteWeeklyReportImage(IdJSONRequest idJSONRequest)
			throws OneConnectApplicationException {
		try {
			if (null != idJSONRequest.getId()) {
				WRImageBean WRImageBean = new WRImageBean();
				WRImageBean.setId(Long.parseLong(idJSONRequest.getId()));
				if (weeklyReportDAO.deleteWeeklyReportImage(WRImageBean)) {

					logger.info("Image deleted successfully");
					baseResponse.setMessage("Image deleted successfully");
					return new WeeklyReportResponse<>(baseResponse, null, HttpStatus.OK);
				} else {
					logger.info("Image not found to delete");
					baseResponse.setMessage("Image not found to delete");
					return new WeeklyReportResponse<>(baseResponse, null, HttpStatus.NOT_FOUND);
				}
			} else {
				logger.info("Image not found to delete");
				baseResponse.setMessage("Image not found to delete");
				return new WeeklyReportResponse<>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}
		} catch (OneConnectDBException ode) {
			logger.error(ode.getMessage());
			throw new OneConnectApplicationException(ode.getMessage());
		} catch (NullPointerException ne) {
			logger.info("Bad Request,Fields can't be empty");
			throw new OneConnectApplicationException("Bad Request,Fields can't be empty");
		} catch (Exception e) {
			logger.error("Unknown service Error");
			throw new OneConnectApplicationException("Unknown Error");
		}
	}

	public WeeklyReportResponse<?> saveApprovalStatus(@RequestBody WRApprovalJSONRequest wrApprovalJSONRequest)
			throws OneConnectDBException {
		logger.info("Save ApprovalStatus Request :: {}", wrApprovalJSONRequest);
		WRBean wrBean = new WRBean();
		wrBean.setClientDetail(wrApprovalJSONRequest.getClient());
		wrBean.setPortfolio(wrApprovalJSONRequest.getPortfolio());
		wrBean.setProjectDetail(wrApprovalJSONRequest.getProject());
		Long clientId = weeklyReportDAO.getClientId(wrBean);
		Long portfolioId = weeklyReportDAO.getPortfolioId(wrBean);
		Long projectId = weeklyReportDAO.getProjectId(wrBean);
		DateUtil dateUtil = new DateUtil();
		Date wReportDate = dateUtil.getFormattedDate(OneConnectConstants.DEFAULT_DATE_FORMAT,
				wrApprovalJSONRequest.getStartDate());

		if (null != clientId && null != portfolioId && null != projectId) {
			wrBean.setwReportDate(wReportDate);
			logger.info("Save ApprovalStatus Request Bean :: {}", wrBean);
			Long weeklyReportId = weeklyReportDAO.getWeeklyReportId(wrBean);
			if (weeklyReportId != null) {
				logger.info("weeklyReportId not NULL TRUE");
				WRApprovalReportBean wrApprovalReportBean = new WRApprovalReportBean();
				wrApprovalReportBean.setApprovalStatus(true);
				wrApprovalReportBean.setCodeQuality(wrApprovalJSONRequest.getCodeQuality());
				wrApprovalReportBean.setProductQuality(wrApprovalJSONRequest.getProjectQuality());
				wrApprovalReportBean.setWeeklyReport(new WRBean());
				wrApprovalReportBean.getWeeklyReport().setId(weeklyReportId);
				WeeklyReportResponse<?> response = weeklyReportDAO.saveApprovalStatus(wrApprovalReportBean);

				notificationJSONRequest.setClientId(clientId);
				notificationJSONRequest.setPortfolioId(portfolioId);
				notificationJSONRequest.setProjectId(projectId);
				notificationJSONRequest.setReportType("Weekly Summary Report");
				notificationJSONRequest.setOperationType("approve");
				notificationJSONRequest.setStartDate(wrApprovalJSONRequest.getStartDate());
				notificationJSONRequest.setIsMessage(false);
				try {

					if (notificationController.notify(notificationJSONRequest).getStatusCode().equals(HttpStatus.OK)) {

						logger.info("Weekly report Approved,added and published successfully");

					} else {
						logger.info("Weekly report Approved,UNABLE TO ADD and Publich Notification");
					}
				} catch (Exception e) {

					logger.info(" Exception :: Notifying and publiching Request given to notification service {} ",
							notificationJSONRequest);
				}
				return response;
			}
		}
		return new WeeklyReportResponse<>("clientId /portfolioId/projectId are null", null, HttpStatus.NOT_FOUND);

	}
}
