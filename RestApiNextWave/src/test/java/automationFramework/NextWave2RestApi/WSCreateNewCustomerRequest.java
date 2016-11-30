package automationFramework.NextWave2RestApi;

public class WSCreateNewCustomerRequest {

	private String customerType;
	private WSCustomerContact contacts;

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public WSCustomerContact getContact() {
		return contacts;
	}

	public void setContact(WSCustomerContact contacts) {
		this.contacts = contacts;
	}

}
