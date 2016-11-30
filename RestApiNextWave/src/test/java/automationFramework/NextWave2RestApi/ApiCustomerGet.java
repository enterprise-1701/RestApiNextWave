package automationFramework.NextWave2RestApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.internal.mapper.ObjectMapperType;
import com.jayway.restassured.mapper.ObjectMapper;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.openqa.selenium.os.WindowsUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import automationFramework.Utilities.Global;
import automationFramework.Utilities.Logging;
import org.apache.log4j.Logger;

import automationFramework.Utilities.Utils;

@JsonIgnoreProperties(ignoreUnknown = true)
// API Test customer GET - NIS NextWave v2.0
public class ApiCustomerGet {

	// this URI returns null
	private static String ENDPOINT = "http://172.23.4.196:8201/nis/nwapi/v2/customer/2177";
	//private static String ENDPOINT2 = "http://172.23.4.196:8201/nis/nwapi/v2/customer/2177?returnCustomerInfo=true";
	
	private static String ENDPOINT2 = "http://172.23.4.196:8201/nis/nwapi/v2/customer/";
	private static String customerId = "2177";
	private static String ENDPOINT3 = "?returnCustomerInfo=true";
	
	private static Logger Log = Logger.getLogger(Logger.class.getName());

	@BeforeMethod
	public void setUp() throws InterruptedException {
		RestAssured.defaultParser = Parser.JSON;
		Logging.setLogConsole();
		Logging.setLogFile();
		Log.info("Setup Started");
		Log.info("Current OS: " + WindowsUtils.readStringRegistryValue(Global.OS));
		Log.info("Starting Tests");

	}

	// API Get success
	@Test(enabled = false)
	public static void apiGetSuccess() throws JSONException {

		Response response = Utils.getRequestSpecifications().when().get(ENDPOINT).thenReturn();
		Assert.assertNotNull(response);
		System.out.println(response.asString());
		String header = response.getHeader("x-cub-hdr");
		Log.info(header);
		Assert.assertTrue(header.contains("Successful"), "API request failed!");

	}
	
	//------------------------------------------------------------------------------------------

	// API Get customer info to verify customer post success
	// Using WSAddressExt, WSCustomerInfo, WSCustomerInfoContact
	@Test(enabled = true)
	public static void apiGetCustomerInfo() throws JSONException {

		WSGetNewCustomerResponse response = Utils.getRequestSpecifications().get(ENDPOINT2+customerId+ENDPOINT3)
				.as(WSGetNewCustomerResponse.class);
		Assert.assertNotNull(response);	
		
		//get the request object
		WSCreateNewCustomerRequest request = ApiCustomerPost.createRequestBody();
		
		//comparison of request object to the response object 
		Assert.assertEquals(response.getCustomerInfo().getCustomerId(), "2177");
		Assert.assertEquals(response.getCustomerInfo().getCustomerType(), request.getCustomerType());
		Assert.assertEquals(response.getCustomerInfo().getContacts().get(0).getContactId(), "2177");
		Assert.assertEquals(response.getCustomerInfo().getContacts().get(0).getContactType(), request.getContact().getContactType());
		Assert.assertEquals(response.getCustomerInfo().getContacts().get(0).getName().getFirstName(), request.getContact().getName().getFirstName());
		Assert.assertEquals(response.getCustomerInfo().getContacts().get(0).getName().getLastName(), request.getContact().getName().getLastName());
		Assert.assertEquals(response.getCustomerInfo().getContacts().get(0).getName().getNameSuffixId(), request.getContact().getName().getNameSuffixId());
		
		//address
		Assert.assertEquals(response.getCustomerInfo().getContacts().get(0).getAddress().getAddress1(), request.getContact().getAddress().getAddress1());
		Assert.assertEquals(response.getCustomerInfo().getContacts().get(0).getAddress().getAddress2(), request.getContact().getAddress().getAddress2());
		Assert.assertEquals(response.getCustomerInfo().getContacts().get(0).getAddress().getCity(), request.getContact().getAddress().getCity());
		Assert.assertEquals(response.getCustomerInfo().getContacts().get(0).getAddress().getState(), request.getContact().getAddress().getState());
		Assert.assertEquals(response.getCustomerInfo().getContacts().get(0).getAddress().getCountry(), request.getContact().getAddress().getCountry());
		Assert.assertEquals(response.getCustomerInfo().getContacts().get(0).getAddress().getPostalCode(), request.getContact().getAddress().getPostalCode());
		Assert.assertTrue(response.getCustomerInfo().getContacts().get(0).getAddress().getAddressId()!=null);
		
		//phone
		Assert.assertEquals(response.getCustomerInfo().getContacts().get(0).getPhone().get(0).getNumber(), request.getContact().getPhone().get(0).getNumber());
		Assert.assertEquals(response.getCustomerInfo().getContacts().get(0).getPhone().get(0).getType(), request.getContact().getPhone().get(0).getType());
		Assert.assertEquals(response.getCustomerInfo().getContacts().get(0).getPhone().get(1).getNumber(), request.getContact().getPhone().get(1).getNumber());
		Assert.assertEquals(response.getCustomerInfo().getContacts().get(0).getPhone().get(1).getType(), request.getContact().getPhone().get(1).getType());
		Assert.assertEquals(response.getCustomerInfo().getContacts().get(0).getPhone().get(2).getNumber(), request.getContact().getPhone().get(2).getNumber());
		Assert.assertEquals(response.getCustomerInfo().getContacts().get(0).getPhone().get(2).getType(), request.getContact().getPhone().get(2).getType());
		
		//email, dob, personal id
		Assert.assertEquals(response.getCustomerInfo().getContacts().get(0).getEmail(), request.getContact().getEmail());
		Assert.assertEquals(response.getCustomerInfo().getContacts().get(0).getDateOfBirth(), request.getContact().getDateOfBirth());
		Assert.assertEquals(response.getCustomerInfo().getContacts().get(0).getPersonalIdentifierInfo().getPersonalIdentifierType(), request.getContact().getPersonalIdentifierInfo().getPersonalIdentifierType());
		Assert.assertEquals(response.getCustomerInfo().getContacts().get(0).getPersonalIdentifierInfo().getPersonalIdentifier(), request.getContact().getPersonalIdentifierInfo().getPersonalIdentifier());
		
		//username, pin, security question
		//Assert.assertEquals(response.getCustomerInfo().getContacts().get(0).getUsername(), request.getContact().getUsername());
		Assert.assertEquals(response.getCustomerInfo().getContacts().get(0).getPin(), request.getContact().getPin());
		Assert.assertEquals(response.getCustomerInfo().getContacts().get(0).getSecurityQAs().get(0).getSecurityQuestion(), request.getContact().getSecurityQAs().get(0).getSecurityQuestion());
		Assert.assertEquals(response.getCustomerInfo().getContacts().get(0).getSecurityQAs().get(0).getSecurityAnswer(), request.getContact().getSecurityQAs().get(0).getSecurityAnswer());
		
		//addresses
		Assert.assertEquals(response.getCustomerInfo().getAddresses().get(0).getAddress1(), request.getContact().getAddress().getAddress1());
		Assert.assertEquals(response.getCustomerInfo().getAddresses().get(0).getAddress2(), request.getContact().getAddress().getAddress2());
		Assert.assertEquals(response.getCustomerInfo().getAddresses().get(0).getCity(), request.getContact().getAddress().getCity());
		Assert.assertEquals(response.getCustomerInfo().getAddresses().get(0).getState(), request.getContact().getAddress().getState());
		Assert.assertEquals(response.getCustomerInfo().getAddresses().get(0).getCountry(), request.getContact().getAddress().getCountry());
		Assert.assertEquals(response.getCustomerInfo().getAddresses().get(0).getPostalCode(), request.getContact().getAddress().getPostalCode());
		Assert.assertTrue(response.getCustomerInfo().getAddresses().get(0).getAddressId()!=null);
		
	}

	   

	@AfterMethod
	public void tearDown() {
		Log.info("Ending Tests");
		Log.info("TearDown Complete");

	}

}
