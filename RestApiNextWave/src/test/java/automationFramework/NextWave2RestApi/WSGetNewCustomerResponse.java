package automationFramework.NextWave2RestApi;

public class WSGetNewCustomerResponse extends RestResponse {
	private WSCustomerInfo customerInfo;

	public WSCustomerInfo getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(WSCustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}

	
}