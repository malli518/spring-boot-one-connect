package org.rythmos.oneconnect.dao;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tomcat.util.codec.binary.Base64;
import org.rythmos.oneconnect.bean.EmployeeProjectMappingBean;
import org.rythmos.oneconnect.bean.WRApprovalReportBean;
import org.rythmos.oneconnect.bean.WRBean;
import org.rythmos.oneconnect.bean.WRCommentBean;
import org.rythmos.oneconnect.bean.WRImageBean;
import org.rythmos.oneconnect.bean.WRMajorUpdateBean;
import org.rythmos.oneconnect.bean.WRRiskAndMitigationBean;
import org.rythmos.oneconnect.bean.WeeklyReportBean;
import org.rythmos.oneconnect.bean.WeeklyReportJSONResponseBean;
import org.rythmos.oneconnect.constant.OneConnectConstants;
import org.rythmos.oneconnect.entity.EmployeeProjectMapping;
import org.rythmos.oneconnect.entity.ProjectDetail;
import org.rythmos.oneconnect.entity.RagStatus;
import org.rythmos.oneconnect.entity.WRApprovalReport;
import org.rythmos.oneconnect.entity.WRComment;
import org.rythmos.oneconnect.entity.WRImage;
import org.rythmos.oneconnect.entity.WRMajorUpdate;
import org.rythmos.oneconnect.entity.WRRiskAndMitigation;
import org.rythmos.oneconnect.entity.WeeklyReport;
import org.rythmos.oneconnect.exception.OneConnectDBException;
import org.rythmos.oneconnect.json.response.BaseResponse;
import org.rythmos.oneconnect.json.response.WRApprovalReportJSONResponse;
import org.rythmos.oneconnect.json.response.WRImageJSONResponse;
import org.rythmos.oneconnect.repository.ClientDetailsRepository;
import org.rythmos.oneconnect.repository.EmployeeProjectMappingRepository;
import org.rythmos.oneconnect.repository.PortfolioRepository;
import org.rythmos.oneconnect.repository.ProjectDetailRepository;
import org.rythmos.oneconnect.repository.RagStatusRepository;
import org.rythmos.oneconnect.repository.WRApprovalReportRepository;
import org.rythmos.oneconnect.repository.WRCommentRepository;
import org.rythmos.oneconnect.repository.WRImageRepository;
import org.rythmos.oneconnect.repository.WRMajorUpdateRepository;
import org.rythmos.oneconnect.repository.WRRiskAndMitigationRepository;
import org.rythmos.oneconnect.repository.WeeklyReportRepository;
import org.rythmos.oneconnect.response.WeeklyReportResponse;
import org.rythmos.oneconnect.util.DateUtil;
import org.rythmos.oneconnect.util.OneConnectDAOUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class WeeklyReportDAO {

	public static Logger logger = LoggerFactory.getLogger(AdminDAO.class);

	@Autowired
	private WeeklyReportRepository weeklyReportRepository;

	@Autowired
	private WRCommentRepository wrCommentRepository;

	@Autowired
	private WRImageRepository wrImageRepository;

	@Autowired
	private WRMajorUpdateRepository wrMajorUpdateRepository;

	@Autowired
	private WRRiskAndMitigationRepository wrRiskAndMitigationRepository;

	@Autowired
	private RagStatusRepository ragStatusRepository;

	@Autowired
	private ClientDetailsRepository clientDetailsRepository;

	@Autowired
	private PortfolioRepository portfolioRepository;

	@Autowired
	private ProjectDetailRepository projectDetailRepository;

	@Autowired
	private EmployeeProjectMappingRepository employeeProjectMappingRepository;

	@Autowired
	private WRApprovalReportRepository wrApprovalReportRepository;
	@Autowired
	private OneConnectDAOUtility oneConnectDAOUtility;

	@Autowired
	private BaseResponse baseResponse;

	private WeeklyReport convertToEntity(WRBean wrBean) {
		WeeklyReport weeklyReport = oneConnectDAOUtility.modelMapper.map(wrBean, WeeklyReport.class);
		return weeklyReport;
	}

	private WRMajorUpdate convertToEntity(WRMajorUpdateBean wrMajorUpdatesBean) {
		WRMajorUpdate wrMajorUpdates = oneConnectDAOUtility.modelMapper.map(wrMajorUpdatesBean, WRMajorUpdate.class);
		return wrMajorUpdates;
	}

	private WRComment convertToEntity(WRCommentBean wrCommentsBean) {
		WRComment wrComments = oneConnectDAOUtility.modelMapper.map(wrCommentsBean, WRComment.class);
		return wrComments;
	}

	private WRRiskAndMitigation convertToEntity(WRRiskAndMitigationBean wrRiskAndMitigationBean) {
		WRRiskAndMitigation wrRiskAndMitigation = oneConnectDAOUtility.modelMapper.map(wrRiskAndMitigationBean,
				WRRiskAndMitigation.class);
		return wrRiskAndMitigation;
	}

	public WeeklyReportResponse<?> saveWeeklyReport(WRBean wrBean) throws OneConnectDBException {
		logger.info("WRBean is::{}", wrBean);
		try {
			if (wrBean != null) {
				logger.info(" Null check Pass :: ");
				WeeklyReport weeklyReport = convertToEntity(wrBean);

				if (wrBean.getRagStatus() == null) {
					weeklyReport.setRagStatus(ragStatusRepository.findRagStatusById(1L));
				} else {
					weeklyReport.setRagStatus(ragStatusRepository.findRagStatusById(wrBean.getRagStatus().getId()));
				}

				if (null != weeklyReportRepository.save(weeklyReport)) {
					logger.info(" Weekly Report added successfully ");
					baseResponse.setMessage("Weekly Report added Successfully!");
					return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
				} else {
					logger.info("Unable to Save WeeklyReport! {}", wrBean);
					baseResponse.setMessage("Unable to Save WeeklyReport!");
					return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
				}

			} else {
				logger.info("Invalid request to save Weekly Report {}", wrBean);
				baseResponse.setMessage("Invalid request to save Weekly Report");
				return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}
		} catch (DataIntegrityViolationException die) {
			logger.info("Duplicate Entry Found! {}", wrBean);
			throw new OneConnectDBException("Duplicate Entry Found!");
		} catch (DataAccessException dae) {
			logger.info("Database Connection lost! {}", wrBean);
			throw new OneConnectDBException("Database Connection lost!");
		} catch (Exception e) {
			logger.info("Data not found! {}", wrBean);
			throw new OneConnectDBException("Data not found!");
		}
	}

	public Long getClientId(WRBean wrBean) throws OneConnectDBException {
		try {
			Long clientId = clientDetailsRepository.findClientId(wrBean.getClientDetail().getId()).getId();
			return clientId;
		} catch (DataAccessException dae) {
			logger.info("Database Connection lost! {}", wrBean);
			throw new OneConnectDBException("Database Connection lost!");
		} catch (NullPointerException ne) {
			logger.error("Client not found", wrBean);
			throw new OneConnectDBException("Client not found!");
		} catch (Exception e) {
			logger.error("Data Not found", wrBean);
			throw new OneConnectDBException("Data Not found!");
		}

	}

	public Long getPortfolioId(WRBean wrBean) throws OneConnectDBException {
		try {
			Long portfolioId = portfolioRepository.findPortfolioById(wrBean.getPortfolio().getId()).getId();
			return portfolioId;
		} catch (DataAccessException dae) {
			logger.info("Database Connection lost! {}", wrBean);
			throw new OneConnectDBException("Database Connection lost!");
		} catch (NullPointerException ne) {
			logger.error("Portfolio not found!", wrBean);
			throw new OneConnectDBException("Portfolio not found!");
		} catch (Exception e) {
			logger.error("Data Not found!", wrBean);
			throw new OneConnectDBException("Data Not found!");
		}

	}

	public Long getProjectId(WRBean wrBean) throws OneConnectDBException {
		try {
			Long projectId = projectDetailRepository.findProjectDetailId(wrBean.getProjectDetail().getId()).getId();
			return projectId;
		} catch (DataAccessException dae) {
			logger.error("Database Connection lost! {}", wrBean);
			throw new OneConnectDBException("Database Connection lost!");
		} catch (NullPointerException ne) {
			logger.error("Project not found!", wrBean);
			throw new OneConnectDBException("Project not found!");
		} catch (Exception e) {
			logger.error("Data Not found!", wrBean);
			throw new OneConnectDBException("Data Not found!");
		}

	}

	public Long getWeeklyReportId(WRBean wrBean) throws OneConnectDBException {
		try {
			logger.info("Date is::{}", new java.sql.Date(wrBean.getwReportDate().getTime()));
			Long weeklyReportId = weeklyReportRepository.findWeeklyReportId(wrBean.getClientDetail().getId(),
					wrBean.getPortfolio().getId(), wrBean.getProjectDetail().getId(),
					new java.sql.Date(wrBean.getwReportDate().getTime()));
			logger.info("Request Bean {} weeklyReportId {}", wrBean, weeklyReportId);
			return weeklyReportId;
		} catch (DataAccessException dae) {
			logger.error("Database Connection lost! {}", wrBean);
			throw new OneConnectDBException("Database Connection lost!");
		} catch (NullPointerException ne) {
			logger.error("Weekly report not found!", wrBean);
			throw new OneConnectDBException("Weekly report not found!");
		} catch (Exception e) {
			logger.error("Data Not found!", wrBean);
			throw new OneConnectDBException("Data Not found!");
		}

	}

	public WeeklyReportResponse<?> saveWRMajorUpdates(WRMajorUpdateBean wrMajorUpdatesBean)
			throws OneConnectDBException {
		try {
			if (wrMajorUpdatesBean != null) {
				logger.info(" Null check Pass :: ");
				WRMajorUpdate wrMajorUpdates = convertToEntity(wrMajorUpdatesBean);
				WeeklyReport weeklyReport = weeklyReportRepository
						.findWeeklyReportById(wrMajorUpdatesBean.getWeeklyReport().getId());
				wrMajorUpdates.setWeeklyReport(weeklyReport);
				if (null != wrMajorUpdateRepository.save(wrMajorUpdates)) {
					logger.info("Major Update added successfully ::{} ", wrMajorUpdates);
					baseResponse.setMessage("Major Update added successfully");
					return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
				} else {
					logger.info("Unable to add Major Update, Major Update might already exist!", wrMajorUpdatesBean);
					baseResponse.setMessage("Unable to add Major Update, Major Update might already exist!");
					return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				logger.info("Invalid Request to save Weekly Report Major Updates! {}", wrMajorUpdatesBean);
				baseResponse.setMessage("Invalid Request to save Weekly Report Major Updates!");
				return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}
		} catch (DataIntegrityViolationException die) {
			logger.error("Unable to add MajorUpdate, Major Update might already exist! ::{}", wrMajorUpdatesBean);
			throw new OneConnectDBException("Unable to add MajorUpdate, Major Update might already exist!");
		} catch (DataAccessException dae) {
			logger.error("Database Connection lost! {}", wrMajorUpdatesBean);
			throw new OneConnectDBException("Database Connection lost!");
		} catch (Exception e) {
			logger.error("Unable to save data! {}", wrMajorUpdatesBean);
			throw new OneConnectDBException("Unable to save data!");
		}
	}

	public WeeklyReportResponse<?> saveWRComments(WRCommentBean wrCommentsBean) throws OneConnectDBException {
		try {
			if (wrCommentsBean != null) {
				logger.info(" Null check Pass :: ");
				WRComment wrComments = convertToEntity(wrCommentsBean);
				WeeklyReport weeklyReport = weeklyReportRepository
						.findWeeklyReportById(wrCommentsBean.getWeeklyReport().getId());
				wrComments.setWeeklyReport(weeklyReport);
				if (null != wrCommentRepository.save(wrComments)) {
					logger.info("Comment added successfully");
					baseResponse.setMessage("Comment added successfully");
					return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
				} else {
					logger.info("Unable to add Comment, Comment might already exist! {}", wrCommentsBean);
					baseResponse.setMessage("Unable to add Comment, Comment might already exist!");
					return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				logger.info("Invalid request to save Weekly Report Comments!{}", wrCommentsBean);
				baseResponse.setMessage("Invalid request to save Weekly Report Comments!");
				return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}
		} catch (DataIntegrityViolationException die) {
			logger.error("Unable to add Comment, Comment might already exist! {}", wrCommentsBean);
			throw new OneConnectDBException("Unable to add Comment, Comment might already exist!");
		} catch (DataAccessException dae) {
			logger.error("Database Connection lost! {}", wrCommentsBean);
			throw new OneConnectDBException("Database Connection lost!");
		} catch (Exception e) {
			logger.error("Unable to save data!", wrCommentsBean);
			throw new OneConnectDBException("Unable to save data!");
		}

	}

	public WeeklyReportResponse<?> saveWRRiskAndMitigation(WRRiskAndMitigationBean wrRiskAndMitigationBean)
			throws OneConnectDBException {
		try {
			if (wrRiskAndMitigationBean != null) {
				logger.info(" Null check Pass :: ");
				WRRiskAndMitigation wrRiskAndMitigation = convertToEntity(wrRiskAndMitigationBean);
				WeeklyReport weeklyReport = weeklyReportRepository
						.findWeeklyReportById(wrRiskAndMitigationBean.getWeeklyReport().getId());

				wrRiskAndMitigation.setWeeklyReport(weeklyReport);
				if (null != wrRiskAndMitigationRepository.save(wrRiskAndMitigation)) {
					logger.info("Risk and Mitigation added successfully");
					baseResponse.setMessage("Risk and Mitigation added successfully");
					return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
				} else {
					logger.info("Unable to add Risk, Risk might already exist!", wrRiskAndMitigationBean);
					baseResponse.setMessage("Unable to add Risk, Risk might already exist!");
					return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				logger.info("Invalid Request to save Weekly Report Risks and Mitigation! {}", wrRiskAndMitigationBean);
				baseResponse.setMessage("Invalid Request to save Weekly Report Risks and Mitigation!");
				return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}
		} catch (DataIntegrityViolationException die) {
			logger.error("Unable to add Risk, Risk might already exist!::{}", wrRiskAndMitigationBean);
			throw new OneConnectDBException("Unable to add Risk, Risk might already exist!");
		} catch (DataAccessException dae) {
			logger.error("Database Connection lost! {}", wrRiskAndMitigationBean);
			throw new OneConnectDBException("Database Connection lost!");
		} catch (Exception e) {
			logger.error("Unable to save data!", wrRiskAndMitigationBean);
			throw new OneConnectDBException("Unable to save data!");
		}
	}

	public WeeklyReportResponse<?> updateWeeklyReport(WRBean wrBean) throws OneConnectDBException {
		try {
			Long weeklyReportId = weeklyReportRepository.findWeeklyReportId(wrBean.getClientDetail().getId(),
					wrBean.getPortfolio().getId(), wrBean.getProjectDetail().getId(),
					new java.sql.Date(wrBean.getwReportDate().getTime()));
			WeeklyReport weeklyReport = weeklyReportRepository.findWeeklyReportById(weeklyReportId);
			if (weeklyReport != null) {
				RagStatus ragStaus = ragStatusRepository.findRagStatusById(wrBean.getRagStatus().getId());
				weeklyReport.setRagStatus(ragStaus);
				weeklyReport.setLastModifiedDate(new Date());
				logger.info("RAG status changed");
				if (weeklyReportRepository.save(weeklyReport) != null) {
					logger.info("RagStatus updated successfully");
					baseResponse.setMessage("RagStatus updated successfully");
					return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
				} else {
					logger.info("RagStatus Unable to Update");
					baseResponse.setMessage("Unable to Update RagStatus");
					return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				logger.info("Weekly Report Not found {}", wrBean);
				baseResponse.setMessage("Weekly Report Not Found ");
				return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}
		} catch (DataIntegrityViolationException die) {
			logger.info("Duplicate Entry Found! {}", wrBean);
			throw new OneConnectDBException("Duplicate Entry Found!");
		} catch (DataAccessException dae) {
			logger.info("Database Connection lost! {}", wrBean);
			throw new OneConnectDBException("Database Connection lost!");
		} catch (Exception e) {
			logger.info("Data not found! {}", wrBean);
			throw new OneConnectDBException("Data not found!");
		}
	}

	public WeeklyReportResponse<?> updateWRMajorUpdates(WRMajorUpdateBean wrMajorUpdatesBean)
			throws OneConnectDBException {

		WRMajorUpdate wrMajorUpdates = wrMajorUpdateRepository.findWRMajorUpdatesById(wrMajorUpdatesBean.getId());
		try {
			if (wrMajorUpdates != null) {
				wrMajorUpdates.setDescription(wrMajorUpdatesBean.getDescription());
				wrMajorUpdates.setLastModifiedDate(new Date());
				if (wrMajorUpdateRepository.save(wrMajorUpdates) != null) {
					logger.info("Major Update updated successfully");
					baseResponse.setMessage("Major Update updated successfully");
					return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
				} else {
					logger.info("Unable to update Major Update, Major Update might already exist!");
					baseResponse.setMessage("Unable to update Major Update, Major Update might already exist!");
					return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				logger.info("Major Update not found! {}", wrMajorUpdatesBean);
				baseResponse.setMessage("Major Update not found!");
				return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
			}
		} catch (DataIntegrityViolationException die) {
			logger.error("Unable to update MajorUpdate, Major Update might already exist! ::{}", wrMajorUpdatesBean);
			throw new OneConnectDBException("Unable to add MajorUpdate, Major Update might already exist!");
		} catch (DataAccessException dae) {
			logger.error("Database Connection lost! {}", wrMajorUpdatesBean);
			throw new OneConnectDBException("Database Connection lost!");
		} catch (Exception e) {
			logger.error("Unable to update data! {}", wrMajorUpdatesBean);
			throw new OneConnectDBException("Unable to update data!");
		}
	}

	public WeeklyReportResponse<?> updateWRComments(WRCommentBean wrCommentsBean) throws OneConnectDBException {
		WRComment wrComments = wrCommentRepository.findWRCommentsById(wrCommentsBean.getId());
		try {
			if (wrComments != null) {
				wrComments.setDescription(wrCommentsBean.getDescription());
				wrComments.setLastModifiedDate(new Date());
				if (wrCommentRepository.save(wrComments) != null) {
					logger.info("Comment updated successfully");
					baseResponse.setMessage("Comment updated successfully");
					return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
				} else {
					logger.info("Unable to update an Comment, Comment might already exist!");
					baseResponse.setMessage("Unable to update an Comment, Comment might already exist!");
					return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				logger.info("Comment Not found! {}", wrCommentsBean);
				baseResponse.setMessage("Comment Not Found! ");
				return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
			}
		} catch (DataIntegrityViolationException die) {
			logger.error("Unable to update Comment, Comment might already exist! {}", wrCommentsBean);
			throw new OneConnectDBException("Unable to add Comment, Comment might already exist!");
		} catch (DataAccessException dae) {
			logger.error("Database Connection lost! {}", wrCommentsBean);
			throw new OneConnectDBException("Database Connection lost!");
		} catch (Exception e) {
			logger.error("Unable to update data!", wrCommentsBean);
			throw new OneConnectDBException("Unable to update data!");
		}

	}

	public WeeklyReportResponse<?> updateWRRiskAndMitigation(WRRiskAndMitigationBean wrRiskAndMitigationBean)
			throws OneConnectDBException {
		WRRiskAndMitigation wrRiskAndMitigation = wrRiskAndMitigationRepository
				.findWRRiskAndMitigationById(wrRiskAndMitigationBean.getId());
		try {
			if (wrRiskAndMitigation != null) {
				wrRiskAndMitigation.setMitigationDesc(wrRiskAndMitigationBean.getMitigationDesc());
				wrRiskAndMitigation.setRiskDesc(wrRiskAndMitigationBean.getRiskDesc());
				wrRiskAndMitigation.setLastModifiedDate(new Date());
				if (wrRiskAndMitigationRepository.save(wrRiskAndMitigation) != null) {
					logger.info("Risk and Mitigation updated successfully");
					baseResponse.setMessage("Risk and Mitigation updated successfully");
					return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
				} else {
					logger.info("Unable to update Risk and Mitigation, Risk might already exist!");
					baseResponse.setMessage("Unable to update Risk and Mitigation, Risk might already exist!");
					return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				logger.info("Risk and Mitigation not found to update! {}", wrRiskAndMitigationBean);
				baseResponse.setMessage("Risk and Mitigation not found to update!");
				return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
			}
		} catch (DataIntegrityViolationException die) {
			logger.error("Unable to update Risk, Risk might already exist!::{}", wrRiskAndMitigationBean);
			throw new OneConnectDBException("Unable to add Risk, Risk might already exist!");
		} catch (DataAccessException dae) {
			logger.error("Database Connection lost! {}", wrRiskAndMitigationBean);
			throw new OneConnectDBException("Database Connection lost!");
		} catch (Exception e) {
			logger.error("Unable to update data!", wrRiskAndMitigationBean);
			throw new OneConnectDBException("Unable to update data!");
		}
	}

	public WeeklyReportResponse<?> deleteWRMajorUpdates(WRMajorUpdateBean wrMajorUpdatesBean)
			throws OneConnectDBException {

		WRMajorUpdate wrMajorUpdates = wrMajorUpdateRepository.findWRMajorUpdatesById(wrMajorUpdatesBean.getId());
		try {
			if (wrMajorUpdates != null) {
				wrMajorUpdateRepository.delete(wrMajorUpdates);
				logger.info("Major Update deleted successfully");
				baseResponse.setMessage("Major Update deleted successfully");
				return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
			} else {
				logger.info("Major Update not found to delete!", wrMajorUpdatesBean);
				baseResponse.setMessage("Unable to delete Major Update!");
				return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}
		} catch (DataIntegrityViolationException die) {
			logger.error("Unable to delete Major Update!::{}", wrMajorUpdatesBean);
			throw new OneConnectDBException("Unable to delete Major Update!");
		} catch (DataAccessException dae) {
			logger.error("Database Connection lost! {}", wrMajorUpdatesBean);
			throw new OneConnectDBException("Database Connection lost!");
		} catch (Exception e) {
			logger.error("Unable to delete data!", wrMajorUpdatesBean);
			throw new OneConnectDBException("Unable to delete data!");
		}

	}

	public WeeklyReportResponse<?> deleteWRComments(WRCommentBean wrCommentsBean) throws OneConnectDBException {
		WRComment wrComments = wrCommentRepository.findWRCommentsById(wrCommentsBean.getId());
		try {
			if (wrComments != null) {
				wrCommentRepository.delete(wrComments);
				logger.info("Comment deleted successfully");
				baseResponse.setMessage("Comment deleted successfully");
				return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
			} else {
				logger.info("Comment Not Found to delete! {}", wrCommentsBean);
				baseResponse.setMessage("Unable to delete Comment!");
				return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
			}
		} catch (DataIntegrityViolationException die) {
			logger.error("Unable to delete Comment!::{}", wrCommentsBean);
			throw new OneConnectDBException("Unable to delete Comment!");
		} catch (DataAccessException dae) {
			logger.error("Database Connection lost! {}", wrCommentsBean);
			throw new OneConnectDBException("Database Connection lost!");
		} catch (Exception e) {
			logger.error("Unable to delete data!", wrCommentsBean);
			throw new OneConnectDBException("Unable to delete data!");
		}
	}

	public WeeklyReportResponse<?> deleteWRRiskAndMitigation(WRRiskAndMitigationBean wrRiskAndMitigationBean)
			throws OneConnectDBException {

		WRRiskAndMitigation wrRiskAndMitigation = wrRiskAndMitigationRepository
				.findWRRiskAndMitigationById(wrRiskAndMitigationBean.getId());
		try {
			if (wrRiskAndMitigation != null) {
				wrRiskAndMitigationRepository.delete(wrRiskAndMitigation);
				baseResponse.setMessage("Risk and Mitigation deleted successfully");
				return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
			} else {
				logger.info("Unable to delete a Risk and Mitigation! {}", wrRiskAndMitigationBean);
				baseResponse.setMessage("Unable to delete a Risk and Mitigation!");
				return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
			}
		} catch (DataIntegrityViolationException die) {
			logger.error("Unable to delete a Risk and Mitigation!::{}", wrRiskAndMitigationBean);
			throw new OneConnectDBException("Unable to delete a Risk and Mitigation!");
		} catch (DataAccessException dae) {
			logger.error("Database Connection lost! {}", wrRiskAndMitigationBean);
			throw new OneConnectDBException("Database Connection lost!");
		} catch (Exception e) {
			logger.error("Unable to delete data!", wrRiskAndMitigationBean);
			throw new OneConnectDBException("Unable to delete data!");
		}
	}

	public WeeklyReportJSONResponseBean getWeeklyReport(WeeklyReportBean weeklyReportBean)
			throws OneConnectDBException {
		try {
			Long clientId = weeklyReportBean.getClientId();
			Long portfolioId = weeklyReportBean.getPortfolioId();
			Long projectId = weeklyReportBean.getProjectDetailId();
			Date wReportDate = weeklyReportBean.getwReportDate();
			Long weeklyReportId = weeklyReportRepository.findWeeklyReportId(clientId, portfolioId, projectId,
					new java.sql.Date(wReportDate.getTime()));
			ProjectDetail projectDetailsDB = null;

			DateUtil dateUtil = new DateUtil();
			WeeklyReportJSONResponseBean wrJSONResponseBean = new WeeklyReportJSONResponseBean();
			RagStatus ragStatus = weeklyReportRepository.findWeeklyReportRagStatusById(weeklyReportId);
			wrJSONResponseBean.setRagStatus(ragStatus);
			if (weeklyReportBean != null && projectId != null && wReportDate != null) {
				logger.info("Null Check passed");
				projectDetailsDB = projectDetailRepository.findProjectDetailId(projectId);
			}
			if (weeklyReportId != null) {
				if (projectDetailsDB != null) {
					ProjectDetail projectDetail = projectDetailsDB;
					projectId = projectDetail.getId();
					wrJSONResponseBean.setDirector(projectDetail.getDirector());
					wrJSONResponseBean.setProjectEndDate(
							dateUtil.getFormattedDateString(projectDetail.getProjectEndDate(), "MM/dd/yyyy"));
					wrJSONResponseBean.setScrumMasterFromRythmos(projectDetail.getRythmosSM());

					wrJSONResponseBean.setProjectId(projectId);

					wrJSONResponseBean.setManagerLeader(projectDetail.getManager());
					wrJSONResponseBean.setProjectStartDate(
							dateUtil.getFormattedDateString(projectDetail.getProjectStartDate(), "MM/dd/yyyy"));
					wrJSONResponseBean.setProjectName(projectDetail.getProjectName());
					wrJSONResponseBean.setProductOwner(projectDetail.getProductOwner());
					wrJSONResponseBean.setPurpose(projectDetail.getPurpose());

					List<EmployeeProjectMapping> empProjectList = employeeProjectMappingRepository
							.findAllActiveEmployeesByProjectId(wrJSONResponseBean.getProjectId(),
									OneConnectConstants.employProjectStatus.ACTIVE.toString());
					logger.info("wrSummaryList is::{}", empProjectList);
					if (empProjectList != null) {
						List<EmployeeProjectMappingBean> empProjectMappingList = empProjectList.stream()
								.map(empProject -> convertToBean(empProject)).collect(Collectors.toList());
						wrJSONResponseBean.setResource(empProjectMappingList);
						wrJSONResponseBean.setNumberOfResourses(empProjectMappingList.size());
					}

					List<WRMajorUpdate> wrMajorUpdatesList = wrMajorUpdateRepository
							.findMajorUpdatesListbyweeklyReportId(weeklyReportId);
					logger.info("wrMajorUpdatesList is::{}", wrMajorUpdatesList);
					if (wrMajorUpdatesList != null) {
						List<WRMajorUpdateBean> wrMajorUpdatesBeanList = wrMajorUpdatesList.stream()
								.map(wrMajorUpdates -> convertToBean(wrMajorUpdates)).collect(Collectors.toList());
						wrJSONResponseBean.setMajorUpdates(wrMajorUpdatesBeanList);
					}

					List<WRComment> wrCommentsList = wrCommentRepository
							.findCommentsListbyweeklyReportId(weeklyReportId);
					logger.info("wrCommentsList is::{}", wrCommentsList);
					if (wrCommentsList != null) {
						List<WRCommentBean> wrCommentsBeanList = wrCommentsList.stream()
								.map(wrComment -> convertToBean(wrComment)).collect(Collectors.toList());
						wrJSONResponseBean.setComments(wrCommentsBeanList);

					}

					List<WRRiskAndMitigation> wrRiskAndMitigationList = wrRiskAndMitigationRepository
							.findRiskAndMitigationListbyweeklyReportId(weeklyReportId);
					logger.info("wrRiskAndMitigationList is::{}", wrRiskAndMitigationList);
					if (wrRiskAndMitigationList != null) {
						List<WRRiskAndMitigationBean> wrRiskAndMitigationBeanList = wrRiskAndMitigationList.stream()
								.map(wrRiskAndMitigation -> convertToBean(wrRiskAndMitigation))
								.collect(Collectors.toList());
						wrJSONResponseBean.setRiskAndMitigations(wrRiskAndMitigationBeanList);
					}

					return wrJSONResponseBean;

				}
			}

			return null;
		} catch (DataIntegrityViolationException die) {
			logger.error("Unable to get Weekly Report!::{}");
			throw new OneConnectDBException("Unable to get Weekly Report!");
		} catch (DataAccessException dae) {
			logger.error("Database Connection lost! {}");
			throw new OneConnectDBException("Database Connection lost!");
		} catch (Exception e) {
			logger.error("Unable to get data!");
			throw new OneConnectDBException("Unable to get data!");
		}
	}

	public WRApprovalReportJSONResponse getWeeklyReportApprovalStatus(WeeklyReportBean weeklyReportBean)
			throws OneConnectDBException {
		try {
			Long clientId = weeklyReportBean.getClientId();
			Long portfolioId = weeklyReportBean.getPortfolioId();
			Long projectId = weeklyReportBean.getProjectDetailId();
			Date wReportDate = weeklyReportBean.getwReportDate();
			Long weeklyReportId = weeklyReportRepository.findWeeklyReportId(clientId, portfolioId, projectId,
					new java.sql.Date(wReportDate.getTime()));

			WRApprovalReport wRApprovalReport = wrApprovalReportRepository.findByWRId(weeklyReportId);

			if (null != wRApprovalReport) {
				WRApprovalReportJSONResponse wRApprovalReportJSONResponse = new WRApprovalReportJSONResponse();
				wRApprovalReportJSONResponse.setApprovalStatus(wRApprovalReport.getApprovalStatus());
				wRApprovalReportJSONResponse.setCodeQuality(wRApprovalReport.getCodeQuality());
				wRApprovalReportJSONResponse.setProjectQuality(wRApprovalReport.getProjectQuality());
				return wRApprovalReportJSONResponse;
			}

			return null;
		} catch (DataIntegrityViolationException die) {
			logger.error("Unable to get Weekly Report!::{}");
			throw new OneConnectDBException("Unable to get Weekly Report!");
		} catch (DataAccessException dae) {
			logger.error("Database Connection lost! {}");
			throw new OneConnectDBException("Database Connection lost!");
		} catch (Exception e) {
			logger.error("Unable to get data!");
			throw new OneConnectDBException("Unable to get data!");
		}
	}

	private EmployeeProjectMappingBean convertToBean(EmployeeProjectMapping employeeProjectMapping) {
		EmployeeProjectMappingBean employeeProjectMappingBean = oneConnectDAOUtility.modelMapper
				.map(employeeProjectMapping, EmployeeProjectMappingBean.class);
		return employeeProjectMappingBean;
	}

	private WRMajorUpdateBean convertToBean(WRMajorUpdate wrMajorUpdates) {
		WRMajorUpdateBean wrMajorUpdatesBean = oneConnectDAOUtility.modelMapper.map(wrMajorUpdates,
				WRMajorUpdateBean.class);
		return wrMajorUpdatesBean;
	}

	private WRImageBean convertToBean(WRImage wRImage) {
		return oneConnectDAOUtility.modelMapper.map(wRImage, WRImageBean.class);

	}

	private WRCommentBean convertToBean(WRComment wrComment) {
		return oneConnectDAOUtility.modelMapper.map(wrComment, WRCommentBean.class);

	}

	private WRRiskAndMitigationBean convertToBean(WRRiskAndMitigation wrRiskAndMitigation) {
		WRRiskAndMitigationBean wrRiskAndMitigationBean = oneConnectDAOUtility.modelMapper.map(wrRiskAndMitigation,
				WRRiskAndMitigationBean.class);
		return wrRiskAndMitigationBean;
	}

	public WeeklyReportResponse<?> uploadWeeklyReportImage(WRImageBean wrImageBean) throws OneConnectDBException {
		try {
			if (wrImageBean != null) {
				logger.info(" Null check Pass :: ");
				WRImage wrImage = convertToEntity(wrImageBean);
				if (wrImage.getFileType().equals(MediaType.IMAGE_JPEG_VALUE)
						|| wrImage.getFileType().equals(MediaType.IMAGE_PNG_VALUE)) {
					if (null != wrImageRepository.save(wrImage)) {

						WRImageJSONResponse wrImageJSONResponse = new WRImageJSONResponse();
						wrImageJSONResponse.setId(wrImage.getId());
						wrImageJSONResponse.setFileType(wrImage.getFileType());
						wrImageJSONResponse.setFileName(wrImage.getFileName());
						wrImageJSONResponse.setData(Base64.encodeBase64String(wrImage.getData()));
						logger.info("wrImageJSONResponse is ::{}", wrImageJSONResponse);
						logger.info("Image uploaded successfully");
						baseResponse.setMessage("Image uploaded successfully");
						return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
						// return ResponseEntity.ok().contentType(MediaType.ALL).body(wrImage);
					} else {
						logger.info("Unable to upload image!");
						baseResponse.setMessage("Unable to upload image!");
						return new WeeklyReportResponse<BaseResponse>(baseResponse, null,
								HttpStatus.INTERNAL_SERVER_ERROR);
					}
				} else {
					logger.info("Unsupported file format! only upload JPEG/JPG/PNG formats");
					baseResponse.setMessage("Unsupported file format! only upload JPEG/JPG/PNG formats");
					return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				logger.info("Invalid Request to upload image!");
				baseResponse.setMessage("Invalid Request to upload image!");
				return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}
		} catch (DataIntegrityViolationException die) {
			logger.error("Unable to Upload  Weekly Report image!::{}");
			throw new OneConnectDBException("Unable to Upload  Weekly Report image!");
		} catch (DataAccessException dae) {
			logger.error("Database Connection lost! {}");
			throw new OneConnectDBException("Database Connection lost!");
		} catch (Exception e) {
			logger.error("Unable to save data!");
			throw new OneConnectDBException("Unable to save data!");
		}
	}

	public List<WRImageBean> getWeeklyReportImages(WeeklyReportBean weeklyReportBean) throws OneConnectDBException {
		try {
			Long weeklyReportId = weeklyReportRepository.findWeeklyReportId(weeklyReportBean.getClientId(),
					weeklyReportBean.getPortfolioId(), weeklyReportBean.getProjectDetailId(),
					new java.sql.Date(weeklyReportBean.getwReportDate().getTime()));
			if (weeklyReportId > 0) {

				List<WRImage> wRImageList = wrImageRepository.findWRImagesByWeeklyReportId(weeklyReportId);
				List<WRImageBean> wRImageBeanBeanList = wRImageList.stream().map(wRImage -> convertToBean(wRImage))
						.collect(Collectors.toList());
				return wRImageBeanBeanList;
			} else {
				return null;
			}
		} catch (DataIntegrityViolationException die) {
			logger.error("Unable to get Weekly Report images!::{}");
			throw new OneConnectDBException("Unable to get Weekly Report images!");
		} catch (DataAccessException dae) {
			logger.error("Database Connection lost! {}");
			throw new OneConnectDBException("Database Connection lost!");
		} catch (Exception e) {
			logger.error("Unable to get data!");
			throw new OneConnectDBException("Unable to get data!");
		}
	}

	private WRImage convertToEntity(WRImageBean wrImageBean) {
		WRImage wrImage = oneConnectDAOUtility.modelMapper.map(wrImageBean, WRImage.class);
		return wrImage;
	}

	public Boolean deleteWeeklyReportImage(WRImageBean wRImageBean) throws OneConnectDBException {

		WRImage wRImage = wrImageRepository.findWRImagesById(wRImageBean.getId());
		try {
			if (null != wRImage) {
				wrImageRepository.delete(wRImage);
				logger.info("WeeklyReportImage with id :: {} Deleted Successfully", wRImageBean.getId());
				return true;
			} else {
				logger.info("Unable to find the image to Delete with the Id :: {}", wRImageBean.getId());
				return false;
			}
		} catch (DataIntegrityViolationException die) {
			logger.error("Unable to delete Weekly Report image!::{}");
			throw new OneConnectDBException("Unable to delete Weekly Report image!");
		} catch (DataAccessException dae) {
			logger.error("Database Connection lost! {}");
			throw new OneConnectDBException("Database Connection lost!");
		} catch (Exception e) {
			logger.error("Unable to delete data!");
			throw new OneConnectDBException("Unable to delet data!");
		}

	}

	public WeeklyReportResponse<?> saveApprovalStatus(WRApprovalReportBean wrApprovalReportBean) {
		WRApprovalReport wrApprovalReport = new WRApprovalReport();
		wrApprovalReport.setApprovalStatus(true);
		wrApprovalReport.setCodeQuality(wrApprovalReportBean.getCodeQuality());
		wrApprovalReport.setProjectQuality(wrApprovalReportBean.getProductQuality());
		wrApprovalReport.setWeeklyReport(new WeeklyReport());
		wrApprovalReport.setWeeklyReport(convertToEntity(wrApprovalReportBean.getWeeklyReport()));
		if (wrApprovalReportRepository.save(wrApprovalReport) != null) {
			logger.info("Successfully approved the report::  {}", wrApprovalReport);
			logger.info("Successfully approved the report::  {}", wrApprovalReportBean);
			baseResponse.setMessage("Successfully approved the report ");

			return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
		} else {
			logger.info("Unable to approve report!");
			baseResponse.setMessage("Unable to approve report!");
			return new WeeklyReportResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}

	}

}
