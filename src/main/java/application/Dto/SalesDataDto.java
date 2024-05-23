package application.Dto;

import java.security.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SalesDataDto {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phone;

    @NotBlank(message = "Company is required")
    private String company;

    @NotBlank(message = "Status is required")
    private String status;

    @Column(name = "timestamp")
    private Timestamp timestamp;

	public SalesDataDto() {
		super();
	}

	public SalesDataDto(Long id, @NotBlank(message = "Name is required") String name,
			@Email(message = "Invalid email format") String email,
			@Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits") String phone,
			@NotBlank(message = "Company is required") String company,
			@NotBlank(message = "Status is required") String status, Timestamp timestamp) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.company = company;
		this.status = status;
		this.timestamp = timestamp;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}