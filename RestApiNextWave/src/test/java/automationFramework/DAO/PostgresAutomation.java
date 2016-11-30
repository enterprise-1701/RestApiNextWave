package automationFramework.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import automationFramework.Utilities.Global;
import automationFramework.Utilities.Logging;
import automationFramework.Utilities.Utils;

@Test
public class PostgresAutomation {

	static String browser;
	private static Logger Log = Logger.getLogger(Logger.class.getName());
	static Connection connection;
	WebDriver driver = new FirefoxDriver();

	@BeforeMethod
	public void setUp() throws InterruptedException {

		Logging.setLogConsole();
		Logging.setLogFile();

	}

	public void dbConnect() {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(Global.POSTGRESDB, Global.POSTGRESUSER, Global.POSTGRESPASS);
			Log.info("PostgresDB connection success!");

		} catch (Exception e) {
			Log.error("Not able to connect to postgresDB" + e);
		}
	}

	// update all fields to default values
	public void dbSetDefaultSettings() throws SQLException {

		try {
			Statement statement = connection.createStatement();
			MultiValueMap map = new MultiValueMap();
			map.put("false", "security.pin.numeric.checkrepeats");
			map.put("9", "security.pin.string.maxlength");
			map.put("4", "security.pin.string.minlength");
			map.put("true", "security.pwd.string.checkrepeats");
			map.put("false", "security.pwd.string.forcemixedcase");
			map.put("64", "security.pwd.string.maxlength");
			map.put("0", "security.pwd.string.mindigits");
			map.put("6", "security.pwd.string.minlength");
			map.put("1", "security.pwd.string.minletters");
			map.put("0", "security.pwd.string.minspecials");
			map.put("false", "security.pwd.string.usedictionary");
			map.put("AC7EC151AFB21006", "security.username.string.requireemail");

			List list = null;
			Set set = map.entrySet();
			Iterator i = set.iterator();

			while (i.hasNext()) {
				Map.Entry<String, List<String>> me = (Map.Entry) i.next();
				for (int j = 0; j < me.getValue().size(); j++) {
					//System.out.println(me.getKey() + " : " + me.getValue().get(j));
					statement.executeUpdate("UPDATE cms_main.settings SET param_value = '" + me.getKey() + "'"
							+ "WHERE param_name = '" + me.getValue().get(j) + "'");
				}
			}
			
		} catch (Exception e) {
			Log.error("Not able to update records " + e);
		}
	}

	
	//update settings
	public void dbUpdateSettings(String key, String value) throws InterruptedException {

		try {

			Statement statement = connection.createStatement();
			int i = statement.executeUpdate(
					"UPDATE cms_main.settings set param_value ='" + value + "'" + "WHERE param_name ='" + key + "'");
			Log.info("Update status " + i);

		} catch (Exception e) {
			Log.error("Not able to update records " + e);
		}

		// Do a refresh of CMS so data is not cached
		driver.navigate().to("http://172.23.4.196:8701/cms/updateTestSetting?clearCacheTestData=true");
		driver.close();

	}

	public void dbDisconnect() {
		try {
			connection.close();
			Log.info("PostgresDB disconnected");
		} catch (SQLException e) {
			Log.error("Not able to disconnect from postgresDB");
		}

	}

}
