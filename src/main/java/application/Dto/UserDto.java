package application.Dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserDto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "User Name is required")
    private String username;

    @NotBlank(message = "First Name is required")
    private String firstname;
    
    @NotBlank(message = "Last Name is required")
    private String username1;;

    private String password;

    @NotBlank(message = "Confirm Password is required")
    private String confirmPassword;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Gender is required")
    private String gender;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phone;

    @NotBlank(message = "Date of Birth is required")
    private String dob;

    @NotBlank(message = "Degree is required")
    private String degree;
    
    private String otherDegree;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Marital Status is required")
    private String maritalStatus;

    @NotBlank(message = "Department is required")
    private String department;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles;

    public UserDto() {
		super();
	}

	public UserDto(Long id, @NotBlank(message = "User Name is required") String username,
			@NotBlank(message = "First Name is required") String firstname,
			@NotBlank(message = "Last Name is required") String username1, String password,
			@NotBlank(message = "Confirm Password is required") String confirmPassword,
			@NotBlank(message = "Email is required") String email,
			@NotBlank(message = "Gender is required") String gender,
			@Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits") String phone,
			@NotBlank(message = "Date of Birth is required") String dob,
			@NotBlank(message = "Degree is required") String degree, String otherDegree,
			@NotBlank(message = "Address is required") String address,
			@NotBlank(message = "Marital Status is required") String maritalStatus,
			@NotBlank(message = "Department is required") String department, Set<String> roles) {
		super();
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.username1 = username1;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.email = email;
		this.gender = gender;
		this.phone = phone;
		this.dob = dob;
		this.degree = degree;
		this.otherDegree = otherDegree;
		this.address = address;
		this.maritalStatus = maritalStatus;
		this.department = department;
		this.roles = roles;
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

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getUsername1() {
		return username1;
	}

	public void setUsername1(String username1) {
		this.username1 = username1;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}
	
	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getOtherDegree() {
		return otherDegree;
	}

	public void setOtherDegree(String otherDegree) {
		this.otherDegree = otherDegree;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

}
