package org.rythmos.oneconnect.security;

import java.util.Date;

import org.rythmos.oneconnect.constant.OneConnectConstants;
import org.rythmos.oneconnect.entity.Employee;
import org.rythmos.oneconnect.repository.EmployeeRepository;
import org.rythmos.oneconnect.repository.ProjectRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * Created by rajeevkumarsingh on 19/08/17.
 */
@Component
public class JwtTokenProvider {

	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

	@Value("${app.jwtSecret}")
	private String jwtSecret;

	@Value("${app.jwtExpirationInMs}")
	private int jwtExpirationInMs;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ProjectRoleRepository projectRoleRepository;

	public String generateToken(Authentication authentication) {

		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		Employee employee = employeeRepository.findEmployeeBymailId(userPrincipal.getEmail());
		userPrincipal.setEmployee(employee);
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

		if (userPrincipal.getAdmin() || userPrincipal.getExecutive()) {
			return Jwts.builder().setSubject(Long.toString(userPrincipal.getId())).setIssuedAt(new Date())
					.setExpiration(expiryDate).claim("userId", userPrincipal.getId())
					.claim("Email", userPrincipal.getEmail()).claim("name", userPrincipal.getName())
					.claim("eId", userPrincipal.getEmployee().getId())
					.claim("empId", userPrincipal.getEmployee().getEmployeeId())
					.claim("role", userPrincipal.getAdmin() ? "ROLE_ADMIN" : "ROLE_EXECUTIVE")
					.claim("admin", userPrincipal.getAdmin()).claim("executive", userPrincipal.getExecutive())
					.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
		}

		else {
			String projectRole = projectRoleRepository.findMaxRoleByEmployeeId(
					OneConnectConstants.employProjectStatus.ACTIVE.toString(), userPrincipal.getEmployee().getId());

			return Jwts.builder().setSubject(Long.toString(userPrincipal.getId())).setIssuedAt(new Date())
					.setExpiration(expiryDate).claim("userId", userPrincipal.getId())
					.claim("Email", userPrincipal.getEmail()).claim("name", userPrincipal.getName())
					.claim("eId", userPrincipal.getEmployee().getId())
					.claim("empId", userPrincipal.getEmployee().getEmployeeId()).claim("role", projectRole)
					.claim("admin", userPrincipal.getAdmin()).claim("executive", userPrincipal.getExecutive())
					.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
		}
	}

	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

		return Long.parseLong(claims.getSubject());
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			logger.error("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			logger.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			logger.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty.");
		}
		return false;
	}
}
