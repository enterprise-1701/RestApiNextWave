package automationFramework.NextWave2RestApi;

import java.util.Date;
import java.util.List;

public class WSCustomerContact {

	private String contactType;
	private WSName name;
	private WSAddress address;
	private String addressId;
	private List<WSPhone> phone;
	private String email;
	private Date dateOfBirth;
	private WSPersonalIdentifier personalIdentifierInfo;
	private String username;
	private String password;
	private String pin;
	private List<WSSecurityQA> securityQAs;

	public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public WSName getName() {
		return name;
	}

	public void setName(WSName name) {
		this.name = name;
	}

	public WSAddress getAddress() {
		return address;
	}

	public void setAddress(WSAddress address) {
		this.address = address;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public List<WSPhone> getPhone() {
		return phone;
	}

	public void setPhone(List<WSPhone> phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public List<WSSecurityQA> getSecurityQAs() {
		return securityQAs;
	}

	public void setSecurityQAs(List<WSSecurityQA> securityQAs) {
		this.securityQAs = securityQAs;
	}

	public WSPersonalIdentifier getPersonalIdentifierInfo() {
		return personalIdentifierInfo;
	}

	public void setPersonalIdentifierInfo(WSPersonalIdentifier personalIdentifierInfo) {
		this.personalIdentifierInfo = personalIdentifierInfo;
	}

}
