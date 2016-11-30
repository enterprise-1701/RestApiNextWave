package automationFramework.TestCases;

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

//API Test
public class ApiCustomerPost {

	private static String ENDPOINT = "http://172.23.4.196:8201/nis/nwapi/v2/customer";
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

	// API Post success
	@Test(enabled = true)
	public static void apiPostSuccess() throws JSONException {

		WSCustomerContact contact = getContact();
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		assertResponse(response);
	}

	// API post error blank customer type
	@Test(enabled = true)
	public static void apiErrorBlankCustomerType() throws JSONException {
		WSCustomerContact contact = getContact();
		WSCreateNewCustomerRequest requestBody = createRequestBodyBlankCustomerType(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.required", "Field is blank.");
	}

	// API post error invalid customer type
	@Test(enabled = true)
	public static void apiErrorInvalidCustomerType() throws JSONException {
		WSCustomerContact contact = getContact();
		WSCreateNewCustomerRequest requestBody = createRequestBodyInvalidCustomerType(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.unexpected", "Invalid customer type.");
	}

	// API post error blank contact
	@Test(enabled = true)
	public static void apiErrorBlankContact() throws JSONException {

		WSCustomerContact contact = null;
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.required", "Field is blank.");
	}

	// API post error invalid contact type
	@Test(enabled = true)
	public static void apiErrorInvalidContactType() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.setContactType("invalid");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.unexpected", "Invalid contact type.");

	}

	// API post error blank contact type
	@Test(enabled = true)
	public static void apiErrorBlankContactType() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.setContactType(null);
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.required", "Field is blank.");
	}

	// API post error blank contact name
	@Test(enabled = true)
	public static void apiErrorBlankContactName() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getName().setFirstName(null);
		contact.getName().setLastName(null);
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.required", "Field is blank.");
	}

	// API post error blank contact first name
	@Test(enabled = true)
	public static void apiErrorBlankContactFirstName() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getName().setFirstName(null);
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.required", "Field is blank.");
	}

	// API post error max length first name
	@Test(enabled = true)
	public static void apiErrorMaxLengthContactFirstName() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getName().setFirstName("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.toolong", "Maximum length exceeded.");
	}

	// API post error max length middle name
	@Test(enabled = true)
	public static void apiErrorMaxLengthContactMiddleName() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getName().setMiddleInitial("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.toolong", "Maximum length exceeded.");
	}

	// API post error blank contact last name
	@Test(enabled = true)
	public static void apiErrorBlankContactLastName() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getName().setLastName(null);
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.required", "Field is blank.");
	}

	// API post error maximum length contact last name
	@Test(enabled = true)
	public static void apiErrorMaxLengthContactLastName() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getName().setLastName("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.toolong", "Maximum length exceeded.");
	}

	// API post error invalid suffix id
	@Test(enabled = true)
	public static void apiErrorInvalidSuffixId() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getName().setNameSuffixId(3);
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.unexpected", "Invalid name suffix id.");
	}

	// API post error invalid address id
	@Test(enabled = true)
	public static void apiErrorAddressId() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.setAddressId("invalid");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.unexpected", "Field should be blank.");
	}

	// API post error blank address
	@Test(enabled = true)
	public static void apiErrorBlankAddress() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getAddress().setAddress1(null);
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.required", "If address is provided, address1 is required.");
	}

	// API post error max length address 1
	@Test(enabled = true)
	public static void apiErrorMaxLengthAddress() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getAddress().setAddress1(
				"zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.toolong", "Maximum length exceeded.");
	}

	// API post error invalid address 2
	@Test(enabled = true)
	public static void apiErrorMaxLengthAddress2() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getAddress().setAddress2(
				"zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.toolong", "Maximum length exceeded.");
	}

	// API post error blank city
	@Test(enabled = true)
	public static void apiErrorBlankCity() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getAddress().setCity(null);
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.required", "If address is provided, city is required.");
	}

	// API post error maximum length city
	@Test(enabled = true)
	public static void apiErrorMaxLengthCity() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getAddress().setCity("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.toolong", "Maximum length exceeded.");
	}

	// API post error blank country
	@Test(enabled = true)
	public static void apiErrorBlankCountry() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getAddress().setCountry(null);
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.required", "If address is provided, country is required.");
	}

	// API post error invalid country
	@Test(enabled = true)
	public static void apiErrorInvalidCountry() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getAddress().setCountry("invalid");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.unexpected", "Invalid country.");
	}

	// API post error blank state
	@Test(enabled = true)
	public static void apiErrorBlankState() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getAddress().setState(null);
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.required", "If address is provided, state is required.");
	}

	// API post error invalid state
	@Test(enabled = true)
	public static void apiErrorInvalidState() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getAddress().setState("invalid");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.unexpected", "Invalid state.");
	}

	// API post error blank postal code
	@Test(enabled = true)
	public static void apiErrorBlankPostalCode() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getAddress().setPostalCode(null);
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.required", "If address is provided, postal code is required.");
	}

	// API post error invalid postal code
	@Test(enabled = true)
	public static void apiErrorInvalidPostalCode() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getAddress().setPostalCode("invalid");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.invalid.postal.code", "Invalid format dictated by country.");
	}

	// API post error blank phone number 1
	@Test(enabled = true)
	public static void apiErrorBlankPhoneNumber() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getPhone().get(0).setNumber(null);
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.required", "If phone is provided, phone number is required.");
	}

	// API post error invalid phone number 1
	@Test(enabled = true)
	public static void apiErrorInvalidPhoneNumber() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getPhone().get(0).setNumber("invalid");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.invalid.phone.number", "Invalid phone number.");
	}

	// API post error blank phone type 1
	@Test(enabled = true)
	public static void apiErrorBlankPhoneType() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getPhone().get(0).setType(null);
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.required", "If phone1 is provided, phone type is required.");
	}

	// API post error invalid phone type 1
	@Test(enabled = true)
	public static void apiErrorInvalidPhoneType() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getPhone().get(0).setType("invalid");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.unexpected", "Invalid phone type.");
	}

	// API post error blank phone number 2
	@Test(enabled = true)
	public static void apiErrorBlankPhoneNumber2() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getPhone().get(1).setNumber(null);
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.required", "If phone2 is provided, phone number is required.");
	}

	// API post error invalid phone number 2
	@Test(enabled = true)
	public static void apiErrorInvalidPhoneNumber2() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getPhone().get(1).setNumber("invalid");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.invalid.phone.number", "Invalid phone number.");
	}

	// API post error blank phone type 2
	@Test(enabled = true)
	public static void apiErrorBlankPhoneType2() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getPhone().get(1).setType(null);
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.required", "If phone2 is provided, phone type is required.");
	}

	// API post error invalid phone type 2
	@Test(enabled = true)
	public static void apiErrorInvalidPhoneType2() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getPhone().get(1).setType("invalid");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.unexpected", "Invalid phone type.");
	}

	// API post error blank phone number 3
	@Test(enabled = true)
	public static void apiErrorBlankPhoneNumber3() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getPhone().get(2).setNumber(null);
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.required", "If phone3 is provided, phone number is required.");
	}

	// API post error invalid phone number 3
	@Test(enabled = true)
	public static void apiErrorInvalidPhoneNumber3() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getPhone().get(2).setNumber("invalid");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.invalid.phone.number", "Invalid phone number.");
	}

	// API post error blank phone type 3
	@Test(enabled = true)
	public static void apiErrorBlankPhoneType3() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getPhone().get(2).setType(null);
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.required", "If phone3 is provided, phone type is required.");
	}

	// API post error invalid phone type 3
	@Test(enabled = true)
	public static void apiErrorInvalidPhoneType3() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getPhone().get(2).setType("invalid");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.unexpected", "Invalid phone type.");
	}

	// API post error blank email
	@Test(enabled = true)
	public static void apiErrorBlankEmail() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.setEmail(null);
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.required", "Field is blank.");
	}

	// API post error maximum length email
	@Test(enabled = true)
	public static void apiErrorMaximumLengthEmail() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.setEmail(
				"zzadfkajddfkadfkjdfdfsffsadfassafasfasfsdadfadfassdfaadfadsfafadfadfafdafddasfdfffdesse@yahoo.com");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.toolong", "Maximum length exceeded.");
	}

	// API post error special characters email
	@Test(enabled = true)
	public static void apiErrorSpecialCharactersEmail() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.setEmail("@yahoo");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.email.cannot.start.with.special.characters",
				"Cannot start with special characters: \".\" ,\"@\" or \"www.\"");
	}

	// API post error invalid email format
	@Test(enabled = true)
	public static void apiErrorInvalidFormatEmail() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.setEmail("test");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.email.invalid.format", "Must be in a standard valid email format.");
	}

	// API post error personal identifier blank
	@Test(enabled = true)
	public static void apiErrorBlankPersonalId() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getPersonalIdentifierInfo().setPersonalIdentifier(null);
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.required",
				"If personal identifier info is provided, personal identifier is required.");
	}

	// API post error personal identitifer maximum length
	@Test(enabled = true)
	public static void apiErrorMaxLengthPersonalId() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getPersonalIdentifierInfo().setPersonalIdentifier("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.toolong", "Maximum length exceeded.");
	}

	// API post error blank personal identitifer type
	@Test(enabled = true)
	public static void apiErrorBlankPersonalIdType() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getPersonalIdentifierInfo().setPersonalIdentifierType(null);
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.required",
				"If personal identifier info is provided, personal identifier type is required.");
	}

	// API post error invalid personal identitifer type
	@Test(enabled = true)
	public static void apiErrorInvalidPersonalIdType() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getPersonalIdentifierInfo().setPersonalIdentifierType("invalid");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.unexpected", "Invalid personal identifier type.");
	}

	// API post error blank username
	@Test(enabled = true)
	public static void apiErrorBlankUserName() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.setUsername(null);
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.required", "Field is blank.");
	}

	// API post error maximum length username
	@Test(enabled = true)
	public static void apiErrorMaxLengthUserName() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.setUsername("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.toolong", "Maximum length exceeded.");
	}

	// API post error special characters username
	@Test(enabled = true)
	public static void apiErrorSpecialCharactersUserName() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.setUsername("@yahoo");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.email.cannot.start.with.special.characters",
				"Cannot start with special characters: \".\" ,\"@\" or \"www.\"");
	}

	// API post error invalid username
	@Test(enabled = true)
	public static void apiErrorInvalidUserName() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.setUsername("invalid");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.email.invalid.format.", "Must be in a standard valid email format");
	}

	// API post error invalid username
	@Test(enabled = true)
	public static void apiErrorDuplicateUserName() throws JSONException {

		WSCustomerContact contact = getContact();
		String userName = Utils.randomEmailString();
		contact.setUsername(userName);
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		assertResponse(response);

		// create a second user with same username
		contact.setUsername(userName);
		WSCreateNewCustomerRequest requestBody2 = createRequestBody(contact);
		WSCreateNewCustomerResponse response2 = sendAPIRequest(requestBody2);
		checkError(response2, "errors.general.value.duplicate", "Value already exists");
	}

	// API post error blank password
	@Test(enabled = true)
	public static void apiErrorBlankPassword() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.setPassword(null);
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.required", "Field is blank.");
	}

	// API post error contain username in password
	@Test(enabled = true)
	public static void apiErrorContainUsernamePassword() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.setUsername("test@yahoo.com");
		contact.setPassword("test@yahoo.com");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.password.cannot.contain.username", "Password can not contain username in it.");
	}

	// API post error contain consecutively characters
	// There are two different errors on this page 27 NIS_NextWave_v2.0_REST_API
	@Test(enabled = true)
	public static void apiErrorContainSameCharacterPassword() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.setPassword("testt1234");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.password.cannot.contain.same.char.consecutively",
				"Password can not contain the same character consecutively.");
	}

	// API post error minimum lenght not met
	@Test(enabled = true)
	public static void apiErrorMinLengthPassword() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.setPassword("91245");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.toosmall", "Minimum length not met.");
	}

	// API post error maximum length password
	@Test(enabled = true)
	public static void apiErrorMaxLengthPassword() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.setPassword("91245912459124591245912459124591245");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.toolong", "Minimum length exceeded.");
	}

	// API post error contain digits password
	@Test(enabled = true)
	public static void apiErrorContainDigitsPassword() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.setPassword("invalidpassword");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.password.must.contain.atleast.digits", "Password must contain digits(s).");
	}

	// API post error mixed case letters password
	@Test(enabled = true)
	public static void apiErrorMixCasesPassword() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.setPassword("testerperfs");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.password.must.contain.mixed.case.chars",
				"Password must contain mixed case letters.");
	}

	// API post error contain letters password
	@Test(enabled = true)
	public static void apiErrorContainLettersPassword() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.setPassword("123456789");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.password.must.contain.atleast.chars", "Password must contain letter(s).");
	}

	// API post error contain special characters
	@Test(enabled = true)
	public static void apiErrorContainSpecialCharactersPassword() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.setPassword("Tester34780");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.password.must.contain.atleast.specials.chars",
				"Password must contain special character(s).");
	}

	// API post error contain dictionary words password
	@Test(enabled = true)
	public static void apiErrorContainDictionaryPassword() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.setPassword("britain");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.password.must.contain.dictionary.word",
				"Password can not contain dictionary word.");
	}

	// API post error pin contain username
	@Test(enabled = true)
	public static void apiErrorContainUsernamePin() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.setUsername("test1358@yahoo.com");
		contact.setPin("test1358@yahoo.com");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.pin.cannot.contain.username", "Pin cannot contain username in it.");
	}

	// API post error pin contain consecutively characters
	@Test(enabled = true)
	public static void apiErrorContainConsecutiveCharacterPin() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.setPin("1112456");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.pin.cannot.contain.same.char.consecutively",
				"Pin cannot contain same character consecutively.");
	}

	// API post error pin contain contiguous characters
	@Test(enabled = true)
	public static void apiErrorContainContigousCharacterPin() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.setPin("1234");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.pin.cannot.contain.contiguous.chars", "Pin cannot contain contiguous characters.");
	}

	// API post error pin too small
	@Test(enabled = true)
	public static void apiErrorTooSmallPin() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.setPin("12");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.toosmall", "Minimum length not met.");
	}

	// API post error pin too long
	@Test(enabled = true)
	public static void apiErrorTooLongPin() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.setPin("1935692432498193432343");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.toolong", "Maximum length exceeded.");
	}

	// API post error pin contain non-numeric characters
	@Test(enabled = true)
	public static void apiErrorNonNumericPin() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.setPin("invalid");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.numerics.only", "Field contains Non-Numeric characters.");
	}

	// API post error security question blank
	@Test(enabled = true)
	public static void apiErrorSecurityQuestionBlank() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getSecurityQAs().get(0).setSecurityQuestion(null);
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.required",
				"If security QAs is provided, security question is required.");
	}

	// API post error security question invalid
	@Test(enabled = true)
	public static void apiErrorSecurityQuestionInvalid() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getSecurityQAs().get(0).setSecurityQuestion("invalid");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.unexpected", "Invalid security question.");
	}

	// API post error security answer blank
	@Test(enabled = true)
	public static void apiErrorSecurityAnswerBlank() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getSecurityQAs().get(0).setSecurityAnswer(null);
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.required",
				"If security QAs is provided, security answer is required.");
	}

	// API post error security answer maximum length
	@Test(enabled = true)
	public static void apiErrorSecurityAnswerMaxLength() throws JSONException {

		WSCustomerContact contact = getContact();
		contact.getSecurityQAs().get(0).setSecurityAnswer(
				"zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
		WSCreateNewCustomerRequest requestBody = createRequestBody(contact);
		WSCreateNewCustomerResponse response = sendAPIRequest(requestBody);
		checkError(response, "errors.general.value.toolong", "Maximum length exceeded.");
	}

	// ****************************************************************************************************
	// start of private methods
	// ****************************************************************************************************

	// create contact object
	private static WSCustomerContact getContact() {

		WSCustomerContact contact = new WSCustomerContact();

		// set name and contact type
		WSName name = new WSName();
		name.setFirstName("indy");
		name.setLastName("jones");
		name.setNameSuffixId(1);
		contact.setName(name);
		contact.setContactType("Primary");

		// set address
		WSAddress address = new WSAddress();
		address.setAddress1("100 broadway");
		address.setCity("san diego");
		address.setCountry("US");
		address.setState("CA");
		address.setPostalCode("92122");
		contact.setAddress(address);

		// set phone
		WSPhone phone = new WSPhone();
		List<WSPhone> phoneList = new ArrayList<WSPhone>();
		phone.setType("M");
		phone.setNumber("8581223332");
		phoneList.add(phone);
		contact.setPhone(phoneList);

		// set email
		contact.setEmail("test1234@yahoo.com");

		// set dob
		String string = "January 2, 1980";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
		LocalDate lDate = LocalDate.parse(string, formatter);
		Date date = java.sql.Date.valueOf(lDate);
		contact.setDateOfBirth(date);

		// set username, password and pin
		contact.setUsername(Utils.randomEmailString());
		contact.setPassword("testSample1x@");
		contact.setPin("123456");

		// set personalIdentifier
		WSPersonalIdentifier identifier = new WSPersonalIdentifier();
		identifier.setPersonalIdentifier("1");
		identifier.setPersonalIdentifierType("DriversLicense");
		contact.setPersonalIdentifierInfo(identifier);

		// Set securityQA
		WSSecurityQA security = new WSSecurityQA();
		List<WSSecurityQA> securityList = new ArrayList<WSSecurityQA>();
		security.setSecurityQuestion("What is your mother's maiden name?");
		security.setSecurityAnswer("name");
		securityList.add(security);
		return contact;
	}

	// create request body valid
	private static WSCreateNewCustomerRequest createRequestBody(WSCustomerContact contact) {
		WSCreateNewCustomerRequest requestBody = new WSCreateNewCustomerRequest();
		requestBody.setCustomerType("Individual");
		requestBody.setContact(contact);
		return requestBody;
	}

	// create request body blank
	private static WSCreateNewCustomerRequest createRequestBodyBlankCustomerType(WSCustomerContact contact) {
		WSCreateNewCustomerRequest requestBody = new WSCreateNewCustomerRequest();
		requestBody.setCustomerType(null);
		requestBody.setContact(contact);
		return requestBody;
	}

	// create request body invalid
	private static WSCreateNewCustomerRequest createRequestBodyInvalidCustomerType(WSCustomerContact contact) {
		WSCreateNewCustomerRequest requestBody = new WSCreateNewCustomerRequest();
		requestBody.setCustomerType("invalid");
		requestBody.setContact(contact);
		return requestBody;
	}

	// Send API request
	private static WSCreateNewCustomerResponse sendAPIRequest(WSCreateNewCustomerRequest requestBody) {
		WSCreateNewCustomerResponse response = Utils.getRequestSpecifications().body(requestBody).when().post(ENDPOINT)
				.as(WSCreateNewCustomerResponse.class);

		return response;
	}

	// Assertions on post success
	private static void assertResponse(WSCreateNewCustomerResponse response) {

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getCustomerId());
		Log.info("New Customer Id: " + response.getCustomerId());
		Assert.assertNotNull(response.getContactId());
		Log.info("New Contact Id: " + response.getContactId());
		Assert.assertNotNull(response.getOneAccountId());
		Log.info("New OneAccount Id: " + response.getOneAccountId());
	}

	// Assertions on post errors
	private static void checkError(WSCreateNewCustomerResponse response, String errorKey, String errorMessage) {

		Assert.assertNotNull(response);
		Assert.assertEquals(response.getHdr().getErrorKey(), errorKey);
		Log.info("Actual error key " + response.getHdr().getErrorKey() + " matches expected errorkey " + errorKey);
		Assert.assertEquals(response.getHdr().getErrorMessage(), errorMessage);
		Log.info("Actual error message " + response.getHdr().getErrorMessage() + " matches expected errorMessage "
				+ errorMessage);
	}

	@AfterMethod
	public void tearDown() {
		Log.info("Ending Tests");
		Log.info("TearDown Complete");

	}

}
