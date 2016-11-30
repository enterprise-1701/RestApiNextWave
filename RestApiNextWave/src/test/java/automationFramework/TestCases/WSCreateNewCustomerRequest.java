package automationFramework.TestCases;

public class WSCreateNewCustomerRequest {

	private String customerType;
	private WSCustomerContact contact;

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public WSCustomerContact getContact() {
		return contact;
	}

	public void setContact(WSCustomerContact contact) {
		this.contact = contact;
	}

}
