package org.rythmos.oneconnect.service;

import org.rythmos.oneconnect.bean.ReportDateJSONBean;
import org.rythmos.oneconnect.dao.ReportDateDAO;
import org.rythmos.oneconnect.json.request.ReportDateJSONRequest;
import org.rythmos.oneconnect.response.ReportDateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ReportDateService {

	@Autowired
	private ReportDateDAO dateDao;
	
	public ReportDateResponse<?> getReportDates(@RequestBody ReportDateJSONRequest reportDateJSONRequest){
		
		ReportDateJSONBean reportDateJSONBean=new ReportDateJSONBean();
		reportDateJSONBean.setClient(reportDateJSONRequest.getClient());
		reportDateJSONBean.setPortfolio(reportDateJSONRequest.getPortfolio());
		reportDateJSONBean.setProject(reportDateJSONRequest.getProject());
		reportDateJSONBean.setReportType(reportDateJSONRequest.getReportType());
		return dateDao.getReportDates(reportDateJSONBean);
		
	}
}
