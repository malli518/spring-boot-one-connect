package org.rythmos.oneconnect.util;

import java.util.ArrayList;
import java.util.List;

import org.rythmos.oneconnect.entity.ClientDetail;
import org.rythmos.oneconnect.entity.ProjectDetail;
import org.rythmos.oneconnect.json.response.ClientInfoJSONResponse;
import org.rythmos.oneconnect.repository.ClientDetailsRepository;
import org.rythmos.oneconnect.repository.ProjectDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

public class OneConnectControllerUtility {

	@Autowired
	private ClientDetailsRepository clientDetailsRepository;

	@Autowired
	private ProjectDetailRepository projectDetailRepository;

	@RequestMapping("/getAllClientProjects")
	public ResponseEntity<?> getClientProjectInfo() {

		// ClientInfoJSONResponse clientProjectInfo = new ClientInfoJSONResponse();
		List<ClientInfoJSONResponse> clientAddressList = new ArrayList<ClientInfoJSONResponse>();
		ClientInfoJSONResponse clientInfoJSONResponse = new ClientInfoJSONResponse();

		List<ClientDetail> clientDtlsDB = clientDetailsRepository.findAll();
		List<String> projectList = new ArrayList<String>();
		for (ClientDetail clientDt : clientDtlsDB) {
			clientInfoJSONResponse = new ClientInfoJSONResponse();
			String clientId = clientDt.getClientId();
			clientInfoJSONResponse.setClientId(clientId);
			clientInfoJSONResponse.setClientName(clientDt.getClientDescription());
			List<ProjectDetail> projectDtlDB = projectDetailRepository.findProjectByClientId(clientId);
			projectList = new ArrayList<String>();
			// ProjectDetailJSONRequest projectDetail=null;
			for (ProjectDetail projectDetailEntity : projectDtlDB) {
				projectList.add(projectDetailEntity.getProjectName());
			}
			clientInfoJSONResponse.setProjects(projectList);
			clientAddressList.add(clientInfoJSONResponse);
		}
		// clientProjectInfo.setClientsInfo(clientAddressList);
		return new ResponseEntity<List<ClientInfoJSONResponse>>(clientAddressList, HttpStatus.OK);
	}

}
