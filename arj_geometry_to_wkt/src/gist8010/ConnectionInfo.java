package gist8010;
//==============================================================================
// File         : some_file.java
//
// Current Author: First_Name Last_Name
//
// Previous Author: None
//
// Contact Info: somebody@somewhere.com
//
// Purpose : 
//
// Dependencies: None
//
// Modification Log :
//    --> Created MMM-DD-YYYY (fl)
//    --> Updated MMM-DD-YYYY (fl)
//
// =============================================================================
public class ConnectionInfo {
	private String connectivityType;
	private String dbmsVendor;
	private String serverNameOrIP;
	private String serverPort;
	private String defaultDatabase;
	private String userName;
	private String userPassword;
	
	public ConnectionInfo() {
		this.connectivityType = "jdbc";
		this.dbmsVendor = "postgresql";
		this.serverNameOrIP = "gamma.athena.bcit.ca";
		this.serverPort = "5432";
		this.defaultDatabase = "a12345678";
		this.userName = "a12345678";
		this.userPassword = "xxxxx";
	}

	public String getConnectivityType() {
		return connectivityType;
	}

	public void setConnectivityType(String connectivityType) {
		this.connectivityType = connectivityType;
	}

	public String getDbmsVendor() {
		return dbmsVendor;
	}

	public void setDbmsVendor(String dbmsVendor) {
		this.dbmsVendor = dbmsVendor;
	}

	public String getServerNameOrIP() {
		return serverNameOrIP;
	}

	public void setServerNameOrIP(String serverNameOrIP) {
		this.serverNameOrIP = serverNameOrIP;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public String getDefaultDatabase() {
		return defaultDatabase;
	}

	public void setDefaultDatabase(String defaultDatabase) {
		this.defaultDatabase = defaultDatabase;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	public String getDatabaseURL()
	{
		return(String.format("%s:%s://%s:%s/%s", 
				this.connectivityType, this.dbmsVendor,
				this.serverNameOrIP, this.serverPort,
				this.defaultDatabase));		
	}
	

}
