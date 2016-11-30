package automationFramework.NextWave2RestApi;

public class WSCreateNewCustomerResponse extends RestResponse {
	private String customerId;
	private String contactId;
	private Integer oneAccountId;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public Integer getOneAccountId() {
		return oneAccountId;
	}

	public void setOneAccountId(Integer oneAccountId) {
		this.oneAccountId = oneAccountId;
	}
}