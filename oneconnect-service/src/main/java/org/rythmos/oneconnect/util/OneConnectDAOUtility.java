package org.rythmos.oneconnect.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class OneConnectDAOUtility {
	@Autowired
	public ModelMapper modelMapper;
	// = new ModelMapper();;

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
