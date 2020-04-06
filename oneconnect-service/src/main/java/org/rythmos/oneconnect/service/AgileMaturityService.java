package org.rythmos.oneconnect.service;

import java.util.Date;
import java.util.List;

import org.rythmos.oneconnect.bean.AgileAssessmentBean;
import org.rythmos.oneconnect.bean.AgileAssessmentQuestionBean;
import org.rythmos.oneconnect.bean.ClientDetailBean;
import org.rythmos.oneconnect.bean.PortfolioBean;
import org.rythmos.oneconnect.bean.ProjectDetailBean;
import org.rythmos.oneconnect.constant.OneConnectConstants;
import org.rythmos.oneconnect.dao.AgileReportDAO;
import org.rythmos.oneconnect.exception.OneConnectDBException;
import org.rythmos.oneconnect.json.request.AgileMaturityJSONRequest;
import org.rythmos.oneconnect.json.response.BaseResponse;
import org.rythmos.oneconnect.repository.ClientDetailsRepository;
import org.rythmos.oneconnect.repository.PortfolioRepository;
import org.rythmos.oneconnect.repository.ProjectDetailRepository;
import org.rythmos.oneconnect.response.AgileReportResponse;
import org.rythmos.oneconnect.util.DateUtil;
import org.rythmos.oneconnect.util.OneConnectServiceUtility;
import org.rythmos.oneconnect.util.OneConnectUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AgileMaturityService {

	@Autowired
	private ClientDetailsRepository clientDetailsRepository;

	@Autowired
	private PortfolioRepository portfolioRepository;

	@Autowired
	private ProjectDetailRepository projectDetailRepository;
	@Autowired
	private OneConnectServiceUtility oneConnectServiceUtility;

	@Autowired
	private AgileReportDAO agileReportDAO;

	@Autowired
	private BaseResponse baseResponse;

	public static Logger logger = LoggerFactory.getLogger(AgileMaturityService.class);

	public AgileReportResponse<?> findAgileReport(AgileMaturityJSONRequest agileMaturityJSONRequest)
			throws OneConnectDBException {
		logger.info(":: saveAgileReport ::{}", agileMaturityJSONRequest);
		Long clientId = clientDetailsRepository.findClientId(agileMaturityJSONRequest.getClient().getId()).getId();
		Long portfolioId = portfolioRepository.findPortfolioById(agileMaturityJSONRequest.getPortfolio().getId())
				.getId();
		Long projectId = projectDetailRepository.findProjectDetailId(agileMaturityJSONRequest.getProject().getId())
				.getId();
		DateUtil dateUtil = new DateUtil();
		Date assessmentDate = dateUtil.getFormattedDate(OneConnectConstants.DEFAULT_DATE_FORMAT,
				agileMaturityJSONRequest.getAssessmentDate());
		logger.info("Date :: " + assessmentDate);

		if (clientId != null && portfolioId != null && projectId != null && assessmentDate != null) {
			if (oneConnectServiceUtility.checkProjectScope(projectId, assessmentDate)) {

				AgileAssessmentBean agileAssessmentBean = new AgileAssessmentBean();
				agileAssessmentBean.setClientDetail(agileMaturityJSONRequest.getClient());
				agileAssessmentBean.setPortfolio(agileMaturityJSONRequest.getPortfolio());
				agileAssessmentBean.setProjectDetail(agileMaturityJSONRequest.getProject());
				agileAssessmentBean.setAssessmentDate(assessmentDate);
				return agileReportDAO.findAgileReport(agileAssessmentBean);

			} else {
				logger.info("Agile Assessment Report date is out of Project Scope!");
				baseResponse.setMessage("Agile Assessment Report date is out of Project Scope!");
				return new AgileReportResponse<>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}

		} else {
			logger.info("Invalid AgileMaturityRequest :: {}", agileMaturityJSONRequest);
			baseResponse.setMessage("Invalid AgileMaturityRequest");
			return new AgileReportResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}

	}

	public AgileReportResponse<?> saveAgileReport(AgileMaturityJSONRequest agileMaturityJSONRequest)
			throws NumberFormatException, OneConnectDBException {
		logger.info(":: saveAgileReport ::{}", agileMaturityJSONRequest);
		Long clientId = clientDetailsRepository.findClientId(agileMaturityJSONRequest.getClient().getId()).getId();
		Long portfolioId = portfolioRepository.findPortfolioById(agileMaturityJSONRequest.getPortfolio().getId())
				.getId();
		Long projectId = projectDetailRepository.findProjectDetailId(agileMaturityJSONRequest.getProject().getId())
				.getId();
		DateUtil dateUtil = new DateUtil();
		Date assessmentDate = dateUtil.getFormattedDate(OneConnectConstants.DEFAULT_DATE_FORMAT,
				agileMaturityJSONRequest.getAssessmentDate());
		logger.info("Date :: " + assessmentDate);

		if (clientId != null && portfolioId != null && projectId != null && assessmentDate != null) {
			if (oneConnectServiceUtility.checkProjectScope(projectId, assessmentDate)) {
				if (!OneConnectUtility.isNull(agileMaturityJSONRequest.getAssessmentType())
						&& (!OneConnectUtility.isNull(agileMaturityJSONRequest.getScore())
								&& Float.parseFloat(agileMaturityJSONRequest.getScore()) >= 0
								&& Float.parseFloat(agileMaturityJSONRequest.getScore()) <= 5)
						&& !OneConnectUtility.isNull(agileMaturityJSONRequest.getQuestion())
						&& (agileMaturityJSONRequest.getAssessmentType()
								.equalsIgnoreCase(OneConnectConstants.assessmentType.CANDOR.toString())
								|| agileMaturityJSONRequest.getAssessmentType()
										.equalsIgnoreCase(OneConnectConstants.assessmentType.ENTHUSIASM.toString())
								|| agileMaturityJSONRequest.getAssessmentType()
										.equalsIgnoreCase(OneConnectConstants.assessmentType.FOCUS.toString())
								|| agileMaturityJSONRequest.getAssessmentType()
										.equalsIgnoreCase(OneConnectConstants.assessmentType.INNOVATION.toString())
								|| agileMaturityJSONRequest.getAssessmentType()
										.equalsIgnoreCase(OneConnectConstants.assessmentType.PARTNERSHIP.toString())
								|| agileMaturityJSONRequest.getAssessmentType()
										.equalsIgnoreCase(OneConnectConstants.assessmentType.TEAMWORK.toString()))) {

					logger.info("Valid AgileMaturityRequest::{} ", agileMaturityJSONRequest);

					AgileAssessmentBean agileAssessmentBean = new AgileAssessmentBean();

					agileAssessmentBean.setClientDetail(agileMaturityJSONRequest.getClient());
					agileAssessmentBean.setPortfolio(agileMaturityJSONRequest.getPortfolio());
					agileAssessmentBean.setProjectDetail(agileMaturityJSONRequest.getProject());
					agileAssessmentBean.setQuestion(agileMaturityJSONRequest.getQuestion());
					agileAssessmentBean.setScore(Float.parseFloat(agileMaturityJSONRequest.getScore()));
					agileAssessmentBean.setAssessmentType(agileMaturityJSONRequest.getAssessmentType());
					agileAssessmentBean.setAssessmentDate(assessmentDate);

					return agileReportDAO.saveAgileReport(agileAssessmentBean);

				} else {
					logger.info("Score might be exceeded,it should be in between 0 - 5 ! :: {}",
							agileMaturityJSONRequest);
					baseResponse.setMessage("Score might be exceeded,it should be in between 0 - 5 !");
					return new AgileReportResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
				}
			} else {
				logger.info("Agile Assessment Report date is out of Project Scope!");
				baseResponse.setMessage("Agile Assessment Report date is out of Project Scope!");
				return new AgileReportResponse<>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}

		} else {
			logger.info("Invalid AgileMaturityRequest! :: {}", agileMaturityJSONRequest);
			baseResponse.setMessage("Score might be exceeded,it should be in between 0 - 5 !");
			return new AgileReportResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);

		}

	}

	public AgileReportResponse<?> updateAgileReport(AgileMaturityJSONRequest agileMaturityJSONRequest)
			throws NumberFormatException, OneConnectDBException {
		Long id = agileMaturityJSONRequest.getId();
		Long clientId = clientDetailsRepository.findClientId(agileMaturityJSONRequest.getClient().getId()).getId();
		Long portfolioId = portfolioRepository.findPortfolioById(agileMaturityJSONRequest.getPortfolio().getId())
				.getId();
		Long projectId = projectDetailRepository.findProjectDetailId(agileMaturityJSONRequest.getProject().getId())
				.getId();
		DateUtil dateUtil = new DateUtil();
		Date assessmentDate = dateUtil.getFormattedDate(OneConnectConstants.DEFAULT_DATE_FORMAT,
				agileMaturityJSONRequest.getAssessmentDate());

		if (id != null && clientId != null && portfolioId != null && projectId != null && assessmentDate != null) {
			if (oneConnectServiceUtility.checkProjectScope(projectId, assessmentDate)) {
				if (!OneConnectUtility.isNull(agileMaturityJSONRequest.getAssessmentType())
						&& (!OneConnectUtility.isNull(agileMaturityJSONRequest.getScore())
								&& Float.parseFloat(agileMaturityJSONRequest.getScore()) >= 0
								&& Float.parseFloat(agileMaturityJSONRequest.getScore()) <= 5)
						&& !OneConnectUtility.isNull(agileMaturityJSONRequest.getQuestion())
						&& (agileMaturityJSONRequest.getAssessmentType()
								.equalsIgnoreCase(OneConnectConstants.assessmentType.CANDOR.toString())
								|| agileMaturityJSONRequest.getAssessmentType()
										.equalsIgnoreCase(OneConnectConstants.assessmentType.ENTHUSIASM.toString())
								|| agileMaturityJSONRequest.getAssessmentType()
										.equalsIgnoreCase(OneConnectConstants.assessmentType.FOCUS.toString())
								|| agileMaturityJSONRequest.getAssessmentType()
										.equalsIgnoreCase(OneConnectConstants.assessmentType.INNOVATION.toString())
								|| agileMaturityJSONRequest.getAssessmentType()
										.equalsIgnoreCase(OneConnectConstants.assessmentType.PARTNERSHIP.toString())
								|| agileMaturityJSONRequest.getAssessmentType()
										.equalsIgnoreCase(OneConnectConstants.assessmentType.TEAMWORK.toString()))) {

					logger.info("Valid AgileMaturityRequest::{} ", agileMaturityJSONRequest);

					AgileAssessmentBean agileAssessmentBean = new AgileAssessmentBean();
					agileAssessmentBean.setId(agileMaturityJSONRequest.getId());
					agileAssessmentBean.setQuestion(agileMaturityJSONRequest.getQuestion());
					agileAssessmentBean.setScore(Float.parseFloat(agileMaturityJSONRequest.getScore()));
					agileAssessmentBean.setAssessmentType(agileMaturityJSONRequest.getAssessmentType());
					return agileReportDAO.updateAgileReport(agileAssessmentBean);
				} else {
					logger.info("*Score might be exceeded,it should be in between 0 - 5 !!::{} ",
							agileMaturityJSONRequest);
					baseResponse.setMessage(
							"Invalid AgileMaturityRequest \n *Score might be exceeded,it should be in between 0 - 5 !");
					return new AgileReportResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
				}
			} else {
				logger.info("Agile Assessment Report date is out of Project Scope!");
				baseResponse.setMessage("Agile Assessment Report date is out of Project Scope!");
				return new AgileReportResponse<>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}
		} else {

			logger.info("Invalid AgileMaturityRequest!::{} ", agileMaturityJSONRequest);
			baseResponse.setMessage("Invalid AgileMaturityRequest !");
			return new AgileReportResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}

	}

	public AgileReportResponse<?> deleteAgileReport(AgileMaturityJSONRequest agileMaturityJSONRequest)
			throws OneConnectDBException {
		Long id = agileMaturityJSONRequest.getId();
		Long clientId = clientDetailsRepository.findClientId(agileMaturityJSONRequest.getClient().getId()).getId();
		Long portfolioId = portfolioRepository.findPortfolioById(agileMaturityJSONRequest.getPortfolio().getId())
				.getId();
		Long projectId = projectDetailRepository.findProjectDetailId(agileMaturityJSONRequest.getProject().getId())
				.getId();
		DateUtil dateUtil = new DateUtil();
		Date assessmentDate = dateUtil.getFormattedDate(OneConnectConstants.DEFAULT_DATE_FORMAT,
				agileMaturityJSONRequest.getAssessmentDate());

		if (id != null && clientId != null && portfolioId != null && projectId != null && assessmentDate != null) {
			if (oneConnectServiceUtility.checkProjectScope(projectId, assessmentDate)) {
				if (!OneConnectUtility.isNull(agileMaturityJSONRequest.getAssessmentType())
						&& !OneConnectUtility.isNull(agileMaturityJSONRequest.getScore())
						&& !OneConnectUtility.isNull(agileMaturityJSONRequest.getQuestion())
						&& (agileMaturityJSONRequest.getAssessmentType()
								.equalsIgnoreCase(OneConnectConstants.assessmentType.CANDOR.toString())
								|| agileMaturityJSONRequest.getAssessmentType()
										.equalsIgnoreCase(OneConnectConstants.assessmentType.ENTHUSIASM.toString())
								|| agileMaturityJSONRequest.getAssessmentType()
										.equalsIgnoreCase(OneConnectConstants.assessmentType.FOCUS.toString())
								|| agileMaturityJSONRequest.getAssessmentType()
										.equalsIgnoreCase(OneConnectConstants.assessmentType.INNOVATION.toString())
								|| agileMaturityJSONRequest.getAssessmentType()
										.equalsIgnoreCase(OneConnectConstants.assessmentType.PARTNERSHIP.toString())
								|| agileMaturityJSONRequest.getAssessmentType()
										.equalsIgnoreCase(OneConnectConstants.assessmentType.TEAMWORK.toString()))) {
					logger.info("Valid AgileMaturityRequest::{} ", agileMaturityJSONRequest);

					AgileAssessmentBean agileAssessmentBean = new AgileAssessmentBean();
					agileAssessmentBean.setId(agileMaturityJSONRequest.getId());
					agileAssessmentBean.setAssessmentType(agileMaturityJSONRequest.getAssessmentType());
					return agileReportDAO.deleteAgileReport(agileAssessmentBean);
				} else {
					logger.info("Invalid AgileMaturityRequest! :: {}", agileMaturityJSONRequest);
					baseResponse.setMessage("Invalid AgileMaturityRequest!");
					return new AgileReportResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
				}
			} else {
				logger.info("Agile Assessment Report date is out of Project Scope!");
				baseResponse.setMessage("Agile Assessment Report date is out of Project Scope!");
				return new AgileReportResponse<>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}
		} else {

			logger.info("Invalid AgileMaturityRequest!::{} ", agileMaturityJSONRequest);
			baseResponse.setMessage("Invalid AgileMaturityRequest !");
			return new AgileReportResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}
	}

	public AgileReportResponse<?> getAgileReport(AgileMaturityJSONRequest agileMaturityJSONRequest)
			throws OneConnectDBException {
		DateUtil dateUtil = new DateUtil();
		Date assessmentDate = dateUtil.getFormattedDate(OneConnectConstants.DEFAULT_DATE_FORMAT,
				agileMaturityJSONRequest.getAssessmentDate());
		if (oneConnectServiceUtility.checkProjectScope(agileMaturityJSONRequest.getProject().getId(), assessmentDate)) {
			AgileReportResponse<?> response = findAgileReport(agileMaturityJSONRequest);
			if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				Boolean copyingStatus = copyAgileTemplateToTable(agileMaturityJSONRequest);
				if (copyingStatus) {
					return findAgileReport(agileMaturityJSONRequest);
				} else {
					logger.info("Unable to load AgileMaturityReport", agileMaturityJSONRequest);
					baseResponse.setMessage("Unable to load AgileMaturityReport");
					return new AgileReportResponse<>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
				}

			} else {
				return response;
			}

		} else {
			logger.info("Agile Assessment Report date is out of Project Scope!");
			baseResponse.setMessage("Agile Assessment Report date is out of Project Scope!");
			return new AgileReportResponse<>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}
	}

	public Boolean copyAgileTemplateToTable(AgileMaturityJSONRequest agileMaturityJSONRequest)
			throws NumberFormatException, OneConnectDBException {

		Long clientId = clientDetailsRepository.findClientId(agileMaturityJSONRequest.getClient().getId()).getId();
		Long portfolioId = portfolioRepository.findPortfolioById(agileMaturityJSONRequest.getPortfolio().getId())
				.getId();
		Long projectId = projectDetailRepository.findProjectDetailId(agileMaturityJSONRequest.getProject().getId())
				.getId();
		DateUtil dateUtil = new DateUtil();
		Date assessmentDate = dateUtil.getFormattedDate(OneConnectConstants.DEFAULT_DATE_FORMAT,
				agileMaturityJSONRequest.getAssessmentDate());

		List<AgileAssessmentQuestionBean> agileAssessmentQuestionBeanList = agileReportDAO.getAgileAssessmentQuestion();
		if (clientId != null && portfolioId != null && projectId != null && assessmentDate != null) {

			for (AgileAssessmentQuestionBean agileAssessmentQuestionBean : agileAssessmentQuestionBeanList) {
				AgileMaturityJSONRequest saveAgileMaturityJSONRequest = new AgileMaturityJSONRequest();
				saveAgileMaturityJSONRequest.setAssessmentDate(
						dateUtil.getFormattedDateString(assessmentDate, OneConnectConstants.DEFAULT_DATE_FORMAT));

				saveAgileMaturityJSONRequest.setClient(new ClientDetailBean());
				saveAgileMaturityJSONRequest.getClient().setId(clientId);

				saveAgileMaturityJSONRequest.setPortfolio(new PortfolioBean());
				saveAgileMaturityJSONRequest.getPortfolio().setId(portfolioId);

				saveAgileMaturityJSONRequest.setProject(new ProjectDetailBean());
				saveAgileMaturityJSONRequest.getProject().setId(projectId);

				saveAgileMaturityJSONRequest.setAssessmentType(agileAssessmentQuestionBean.getAssessmentType());
				saveAgileMaturityJSONRequest.setQuestion(agileAssessmentQuestionBean.getQuestion());
				saveAgileMaturityJSONRequest.setScore("0");
				if (null == saveAgileReport(saveAgileMaturityJSONRequest)) {
					return false;
				}

			}
		}
		return true;
	}
}
