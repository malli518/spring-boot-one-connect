package org.rythmos.oneconnect.bean;

import java.util.Date;

public interface ProjectEsummaryProjection {
	 Long getId();
	    String getProjectName();
	    Long getProjectId();
	    Long getClientId();
	    Long getPortfolioId();
	    Date getESummaryDate();
}
