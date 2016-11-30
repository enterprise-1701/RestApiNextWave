package automationFramework.TestCases;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.Test;
import static com.jayway.restassured.RestAssured.*;
import automationFramework.Utilities.Logging;
import org.apache.log4j.Logger;

public class RestResponse {

	private RestResponseHeader hdr;

	public RestResponseHeader getHdr() {
		return hdr;
	}

	public void setResponseHeader(RestResponseHeader hdr) {
		this.hdr = hdr;
	}

}
