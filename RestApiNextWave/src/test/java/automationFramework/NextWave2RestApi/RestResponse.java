package automationFramework.NextWave2RestApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.Test;
import static com.jayway.restassured.RestAssured.*;
import automationFramework.Utilities.Logging;
import org.apache.log4j.Logger;

public class RestResponse {
	
	@SerializedName("x-cub-hdr")
	private RestResponseHeader hdr;

	public RestResponseHeader getHdr() {
		return hdr;
	}

	public void setResponseHeader(RestResponseHeader hdr) {
		this.hdr = hdr;
	}

}
