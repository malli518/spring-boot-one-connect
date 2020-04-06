package org.rythmos.oneconnect.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.rythmos.oneconnect.bean.AgileAssessmentBean;
import org.rythmos.oneconnect.bean.AgileAssessmentQuestionBean;
import org.rythmos.oneconnect.entity.AgileAssessment;
import org.rythmos.oneconnect.entity.AgileAssessmentQuestion;
import org.rythmos.oneconnect.json.response.AgileIndicatorJSONResponse;
import org.rythmos.oneconnect.json.response.AgileIndicatorPointsJSONResponse;
import org.rythmos.oneconnect.json.response.AgileMaturityJSONResponse;
import org.rythmos.oneconnect.json.response.AgileReportAverageJSONRepsonse;
import org.rythmos.oneconnect.json.response.AgileReportTotalJSONRepsonse;
import org.rythmos.oneconnect.json.response.AssessmentDataJSONRepsonse;
import org.rythmos.oneconnect.json.response.BaseResponse;
import org.rythmos.oneconnect.repository.AgileAssessmentQuestionsRepository;
import org.rythmos.oneconnect.repository.AgileAssessmentRepository;
import org.rythmos.oneconnect.response.AgileReportResponse;
import org.rythmos.oneconnect.util.OneConnectDAOUtility;
import org.rythmos.oneconnect.util.OneConnectUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AgileReportDAO {

	@Autowired
	private AgileAssessmentRepository agileAssessmentRepository;

	@Autowired
	private AgileAssessmentQuestionsRepository agileAssessmentQuestionsRepository;

	@Autowired
	private OneConnectUtility oneConnectUtility;

	@Autowired
	private BaseResponse baseResponse;

	@Autowired
	private OneConnectDAOUtility oneConnectDAOUtility;

	public static Logger logger = LoggerFactory.getLogger(CHReportDAO.class);

	public AgileReportResponse<?> saveAgileReport(AgileAssessmentBean agileAssessmentBean) {
		logger.info("AgileAssessmentBean is::{}", agileAssessmentBean);
		if (agileAssessmentBean != null) {
			logger.info(" Null check Pass :: ");
			AgileAssessment agileAssessmentReport = convertToEntity(agileAssessmentBean);
			AgileAssessment agileAssessment = agileAssessmentRepository.findAssessmentBydesc(
					agileAssessmentBean.getQuestion(), agileAssessmentBean.getProjectDetail().getId(),
					new java.sql.Date(agileAssessmentBean.getAssessmentDate().getTime()),
					agileAssessmentBean.getAssessmentType());
			try {
				if (agileAssessment == null) {
					if (null != agileAssessmentRepository.save(agileAssessmentReport)) {
						logger.info("Question for" + agileAssessmentBean.getAssessmentType() + " added successfully");
						baseResponse.setMessage(
								"Question for " + agileAssessmentBean.getAssessmentType() + " added successfully");
						return new AgileReportResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
					} else {
						logger.info("Unable to add " + agileAssessmentBean.getAssessmentType()
								+ " Question, Question might already exist!");
						baseResponse.setMessage("Unable to add " + agileAssessmentBean.getAssessmentType()
								+ " Question, Question might already exist!");
						return new AgileReportResponse<BaseResponse>(baseResponse, null,
								HttpStatus.INTERNAL_SERVER_ERROR);
					}
				} else {
					logger.info("Invalid Agile Assessment Request! {}", agileAssessmentBean);
					baseResponse.setMessage("Invalid Agile Assessment Request!");
					return new AgileReportResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
				}
			} catch (Exception ex) {
				logger.info("Unable to add " + agileAssessmentBean.getAssessmentType()
						+ " Question, Question might already exist!");
				baseResponse.setMessage("Unable to add " + agileAssessmentBean.getAssessmentType()
						+ " Question, Question might already exist!");
				return new AgileReportResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);

			}

		} else {
			logger.info("Invalid Agile Assessment Request {}", agileAssessmentBean);
			baseResponse.setMessage("Invalid Agile Assessment question  Request");
			return new AgileReportResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}
	}

	private AgileAssessment convertToEntity(AgileAssessmentBean agileAssessmentBean) {
		return oneConnectDAOUtility.modelMapper.map(agileAssessmentBean, AgileAssessment.class);
	}

	private AgileAssessmentQuestionBean convertToBean(AgileAssessmentQuestion agileAssessmentQuestion) {
		return oneConnectDAOUtility.modelMapper.map(agileAssessmentQuestion, AgileAssessmentQuestionBean.class);

	}

	public AgileReportResponse<?> updateAgileReport(AgileAssessmentBean agileAssessmentBean) {

		logger.info("AgileAssessmentBean is::{}", agileAssessmentBean);
		try {
			if (agileAssessmentBean != null) {
				logger.info(" Null check Pass :: ");

				AgileAssessment agileAssessment = agileAssessmentRepository
						.findAssessmentById(agileAssessmentBean.getId());
				agileAssessment.setQuestion(agileAssessmentBean.getQuestion());
				agileAssessment.setScore(agileAssessmentBean.getScore());
				agileAssessment.setLastModifiedDate(new Date());
				if (null != agileAssessmentRepository.save(agileAssessment)) {
					logger.info(" Question for" + agileAssessmentBean.getAssessmentType() + " updated successfully ");
					baseResponse.setMessage(
							"Question for " + agileAssessmentBean.getAssessmentType() + " updated successfully");
					return new AgileReportResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
				} else {
					logger.info("Unable to update " + agileAssessmentBean.getAssessmentType()
							+ " Question, Question might already exist!");
					baseResponse.setMessage("Unable to update " + agileAssessmentBean.getAssessmentType()
							+ " Question, Question might already exist!");
					return new AgileReportResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				logger.info("Invalid Agile Assessment Request! {}", agileAssessmentBean);
				baseResponse.setMessage("Invalid Agile Assessment Request!");
				return new AgileReportResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception ex) {
			logger.info("Unable to update " + agileAssessmentBean.getAssessmentType()
					+ " Question, Question might already exist!");
			baseResponse.setMessage("Unable to update " + agileAssessmentBean.getAssessmentType()
					+ " Question, Question might already exist!");
			return new AgileReportResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public AgileReportResponse<?> deleteAgileReport(AgileAssessmentBean agileAssessmentBean) {
		logger.info("AgileAssessmentBean is::{}", agileAssessmentBean);
		try {
			if (agileAssessmentBean != null) {
				logger.info(" Null check Pass :: ");
				AgileAssessment agileAssessment = agileAssessmentRepository
						.findAssessmentById(agileAssessmentBean.getId());
				if (agileAssessment != null) {
					agileAssessmentRepository.delete(agileAssessment);
					logger.info("Question for " + agileAssessmentBean.getAssessmentType() + " deleted successfully");
					baseResponse.setMessage(
							"Question for " + agileAssessmentBean.getAssessmentType() + " deleted successfully");
					return new AgileReportResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
				} else {
					logger.info("Unable to delete " + agileAssessmentBean.getAssessmentType() + " Question! {}",
							agileAssessmentBean);
					baseResponse
							.setMessage("Unable to delete " + agileAssessmentBean.getAssessmentType() + " Question!");
					return new AgileReportResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				logger.info("Invalid request! {}", agileAssessmentBean);
				baseResponse.setMessage("Invalid request!");
				return new AgileReportResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			logger.info("Unable to delete {}  Question! {}", agileAssessmentBean.getAssessmentType(),
					agileAssessmentBean);
			baseResponse.setMessage("Unable to delete " + agileAssessmentBean.getAssessmentType() + " Question!");
			return new AgileReportResponse<>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public AgileReportResponse<?> findAgileReport(AgileAssessmentBean agileAssessmentBean) {
		logger.info("AgileAssessmentBean is::{}", agileAssessmentBean);
		if (agileAssessmentBean != null) {
			logger.info(" Null check Pass :: ");
			AgileMaturityJSONResponse agileMaturityJSONResponse = new AgileMaturityJSONResponse();

			List<AgileAssessment> agileAssessmentlist = agileAssessmentRepository.findAssessmentByProjectAndDate(
					agileAssessmentBean.getProjectDetail().getId(),
					new java.sql.Date(agileAssessmentBean.getAssessmentDate().getTime()));

			if (agileAssessmentlist.isEmpty()) {
				logger.info("Agile Maturity Assessment not found! {}", agileAssessmentBean);
				baseResponse.setMessage("Agile Maturity Assessment not found!");
				return new AgileReportResponse<>(baseResponse, null, HttpStatus.NOT_FOUND);
			}
			agileMaturityJSONResponse.setAssessmentData(convertToAssessmentDataJSONRepsonseList(agileAssessmentlist));

			Calendar calender = Calendar.getInstance();
			calender.setTime(agileAssessmentBean.getAssessmentDate());
			List<AgileIndicatorJSONResponse> agileIndicatorJSONResponseList = new ArrayList<>();

			for (int i = 0; i < 3; i++) {
				AgileIndicatorJSONResponse agileIndicatorJSONResponse = new AgileIndicatorJSONResponse();
				String monthLabel = calender.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
				logger.info(" Month Name ::" + monthLabel);
				agileIndicatorJSONResponse.setMonth(monthLabel);
				agileIndicatorJSONResponse.setAgilePoints(findAgileIndicatorPointsJSONResponse(agileAssessmentlist));
				calender.add(Calendar.MONTH, -1);
				agileIndicatorJSONResponseList.add(agileIndicatorJSONResponse);
				agileAssessmentlist = agileAssessmentRepository.findAssessmentByProjectAndDate(
						agileAssessmentBean.getProjectDetail().getId(),
						new java.sql.Date(calender.getTime().getTime()));

			}

			agileMaturityJSONResponse.setAgileData(agileIndicatorJSONResponseList);
			return new AgileReportResponse<>(agileMaturityJSONResponse, null, HttpStatus.OK);

		} else {
			logger.info("Invalid Request! {}", agileAssessmentBean);
			baseResponse.setMessage("Invalid Request!");
			return new AgileReportResponse<>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}

	}

	private List<AssessmentDataJSONRepsonse> convertToAssessmentDataJSONRepsonseList(
			List<AgileAssessment> agileAssessmentlist) {
		List<AssessmentDataJSONRepsonse> assessmentDataJSONRepsonseList = new ArrayList<>();
		for (AgileAssessment assessmentData : agileAssessmentlist) {
			AssessmentDataJSONRepsonse assessmentDataJSONRepsonse = new AssessmentDataJSONRepsonse();
			assessmentDataJSONRepsonse.setId(assessmentData.getId());
			assessmentDataJSONRepsonse.setQuestion(assessmentData.getQuestion());
			assessmentDataJSONRepsonse.setScore(assessmentData.getScore());
			assessmentDataJSONRepsonse.setAssessmentType(assessmentData.getAssessmentType());
			assessmentDataJSONRepsonseList.add(assessmentDataJSONRepsonse);
		}
		return assessmentDataJSONRepsonseList;
	}

	private AgileIndicatorPointsJSONResponse findAgileIndicatorPointsJSONResponse(
			List<AgileAssessment> agileAssessmentlist) {

		AgileIndicatorPointsJSONResponse agileIndicatorPointsJSONResponse = new AgileIndicatorPointsJSONResponse();
		AgileReportTotalJSONRepsonse agileReportTotalJSONRepsonse = new AgileReportTotalJSONRepsonse();
		AgileReportAverageJSONRepsonse agileReportAverageJSONRepsonse = new AgileReportAverageJSONRepsonse();

		int[] datacount = new int[6];

		for (AgileAssessment assessmentData : agileAssessmentlist) {

			switch (assessmentData.getAssessmentType()) {
			case "Partnership":
				agileReportTotalJSONRepsonse
						.setPartnership(agileReportTotalJSONRepsonse.getPartnership() + assessmentData.getScore());
				datacount[0]++;
				agileReportAverageJSONRepsonse
						.setPartnership(agileReportTotalJSONRepsonse.getPartnership() / datacount[0]);
				agileIndicatorPointsJSONResponse
						.setPartnership(agileReportTotalJSONRepsonse.getPartnership() / (datacount[0] * 5) * 100);

				break;

			case "Teamwork":

				agileReportTotalJSONRepsonse
						.setTeamwork(agileReportTotalJSONRepsonse.getTeamwork() + assessmentData.getScore());
				datacount[1]++;
				agileReportAverageJSONRepsonse.setTeamwork(agileReportTotalJSONRepsonse.getTeamwork() / datacount[1]);
				agileIndicatorPointsJSONResponse
						.setTeamwork(agileReportTotalJSONRepsonse.getTeamwork() / (datacount[1] * 5) * 100);
				break;

			case "Enthusiasm":
				agileReportTotalJSONRepsonse
						.setEnthusiasm(agileReportTotalJSONRepsonse.getEnthusiasm() + assessmentData.getScore());
				datacount[2]++;
				agileReportAverageJSONRepsonse
						.setEnthusiasm(agileReportTotalJSONRepsonse.getEnthusiasm() / datacount[2]);
				agileIndicatorPointsJSONResponse
						.setEnthusiasm(agileReportTotalJSONRepsonse.getEnthusiasm() / (datacount[2] * 5) * 100);
				break;

			case "Candor":
				agileReportTotalJSONRepsonse
						.setCandor(agileReportTotalJSONRepsonse.getCandor() + assessmentData.getScore());
				datacount[3]++;
				agileReportAverageJSONRepsonse.setCandor(agileReportTotalJSONRepsonse.getCandor() / datacount[3]);
				agileIndicatorPointsJSONResponse
						.setCandor(agileReportTotalJSONRepsonse.getCandor() / (datacount[3] * 5) * 100);
				break;

			case "Innovation":
				agileReportTotalJSONRepsonse
						.setInnovation(agileReportTotalJSONRepsonse.getInnovation() + assessmentData.getScore());
				datacount[4]++;
				agileReportAverageJSONRepsonse
						.setInnovation(agileReportTotalJSONRepsonse.getInnovation() / datacount[4]);
				agileIndicatorPointsJSONResponse
						.setInnovation(agileReportTotalJSONRepsonse.getInnovation() / (datacount[4] * 5) * 100);
				break;

			case "Focus":
				agileReportTotalJSONRepsonse
						.setFocus(agileReportTotalJSONRepsonse.getFocus() + assessmentData.getScore());
				datacount[5]++;
				agileReportAverageJSONRepsonse.setFocus(agileReportTotalJSONRepsonse.getFocus() / datacount[5]);
				agileIndicatorPointsJSONResponse
						.setFocus(agileReportTotalJSONRepsonse.getFocus() / (datacount[5] * 5) * 100);
				break;

			default:
				break;

			}
		}

		agileIndicatorPointsJSONResponse
				.setPartnership(oneConnectUtility.round(agileIndicatorPointsJSONResponse.getPartnership(), 2));
		agileIndicatorPointsJSONResponse
				.setTeamwork(oneConnectUtility.round(agileIndicatorPointsJSONResponse.getTeamwork(), 2));
		agileIndicatorPointsJSONResponse
				.setEnthusiasm(oneConnectUtility.round(agileIndicatorPointsJSONResponse.getEnthusiasm(), 2));
		agileIndicatorPointsJSONResponse
				.setCandor(oneConnectUtility.round(agileIndicatorPointsJSONResponse.getCandor(), 2));
		agileIndicatorPointsJSONResponse
				.setInnovation(oneConnectUtility.round(agileIndicatorPointsJSONResponse.getInnovation(), 2));
		agileIndicatorPointsJSONResponse
				.setFocus(oneConnectUtility.round(agileIndicatorPointsJSONResponse.getFocus(), 2));

		return agileIndicatorPointsJSONResponse;

	}

	public List<AgileAssessmentQuestionBean> getAgileAssessmentQuestion() {
		List<AgileAssessmentQuestion> agileAssessmentQuestionList = agileAssessmentQuestionsRepository.findAll();
		List<AgileAssessmentQuestionBean> agileAssessmentQuestionBeanList = new ArrayList<>();
		logger.info("Database List {} ::", agileAssessmentQuestionBeanList);
		for (AgileAssessmentQuestion agileAssessmentQuestion : agileAssessmentQuestionList) {
			agileAssessmentQuestionBeanList.add(convertToBean(agileAssessmentQuestion));
		}
		return agileAssessmentQuestionBeanList;
	}
}
