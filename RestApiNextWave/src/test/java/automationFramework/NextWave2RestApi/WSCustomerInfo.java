package automationFramework.NextWave2RestApi;

import java.util.Date;
import java.util.List;

public class WSCustomerInfo {

	private String customerId;
	private String customerType;
	private List<WSCustomerInfoContact> contacts;
	private List<WSAddressExt> addresses;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
	public List<WSCustomerInfoContact> getContacts() {
		return contacts;
	}

	public List<WSAddressExt> getAddresses() {
		return addresses;
	}

	
}
