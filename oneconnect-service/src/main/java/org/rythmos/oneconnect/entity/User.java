package org.rythmos.oneconnect.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;
import org.rythmos.oneconnect.audit.DateAudit;

/**
 * @author Prasanth Kusumaraju
 *
 */
@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }),
		@UniqueConstraint(columnNames = { "email" }) })
public class User extends DateAudit {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 40)
	private String name;

	@NotBlank
	@Size(max = 15)
	private String username;

	@NaturalId
	@NotBlank
	@Size(max = 40)
	@Email
	private String email;

	@NotBlank
	@Size(max = 100)
	private String password;

	@Lob
	@Column
	private byte[] data;

	@Column
	private String fileName;
	@Column
	private String fileType;

	@OneToOne
	private Employee employee;

	private Boolean admin;

	private Boolean executive;

	private Boolean projectRole;

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public User() {

	}

	public User(String name, String username, String email, String password, Boolean admin, Boolean executive,
			Boolean projectRole, String fileName, String fileType) {
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.admin = admin;
		this.executive = executive;
		this.projectRole = projectRole;

	}

	public User(String name, String username, String email, String password, Boolean admin, Boolean executive,
			Boolean projectRole) {
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.admin = admin;
		this.executive = executive;
		this.projectRole = projectRole;

		this.data = null;
		this.fileName = null;
		this.fileType = null;

	}

	public User(@NotBlank @Size(max = 40) String name, @NotBlank @Size(max = 15) String username,
			@NotBlank @Size(max = 40) @Email String email, @NotBlank @Size(max = 100) String password, byte[] data,
			String fileName, String fileType, Employee employee, Boolean admin, Boolean executive, Boolean projectRole,
			Set<Role> roles) {
		super();
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.data = data;
		this.fileName = fileName;
		this.fileType = fileType;
		this.employee = employee;
		this.admin = admin;
		this.executive = executive;
		this.projectRole = projectRole;
		this.roles = roles;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public Boolean getExecutive() {
		return executive;
	}

	public void setExecutive(Boolean executive) {
		this.executive = executive;
	}

	public Boolean getProjectRole() {
		return projectRole;
	}

	public void setProjectRole(Boolean projectRole) {
		this.projectRole = projectRole;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> set) {
		this.roles = set;
	}
}