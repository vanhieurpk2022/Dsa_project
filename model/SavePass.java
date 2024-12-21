package model;

import java.util.prefs.Preferences;

public class SavePass {
	private Preferences prefs;

	public SavePass(Class<?> clazz) {
		prefs = Preferences.userNodeForPackage(clazz);

	}

	public void putAccountAdmin() {
		this.prefs.put("adminUser", "admin");
		this.prefs.put("adminPass", "admin");
	}

	// Truy xuất thông tin username
	public String getAdminUser() {
		return prefs.get("adminUser", "");
	}

	// Truy xuất thông tin password
	public String getAdminPass() {
		return prefs.get("adminPass", "");
	}

	public void save(String username, String password) {
		prefs.put("username", username);
		prefs.put("password", password);
	}

	public void remove(String username, String pass) {
		prefs.remove(username);
		prefs.remove(pass);
	}

	public void removePassword(String pass) {
		prefs.remove(pass);
	}

	// Truy xuất thông tin username
	public String getUsername() {
		return prefs.get("username", "");
	}

	// Truy xuất thông tin password
	public String getPassword() {
		return prefs.get("password", "");
	}

	public void savePassword(String password) {
		prefs.get(password, "");
	}

	public boolean getCheckboxState(String key, boolean defaultValue) {
		return prefs.getBoolean(key, defaultValue); // Lấy giá trị boolean
	}

	public void saveCheckboxState(String key, boolean value) {
		prefs.putBoolean(key, value); // Lưu giá trị boolean vào Preferences
	}

	public void saveFlightCode(String a) {
		prefs.put("FlightCode", a);
	}

	public String getFlightCode(String a) {
		return prefs.get("FlightCode", "");
	}

	public void removeFlightCode(String a) {
		prefs.remove("FlightCode");
	}

	public boolean checkAccess(String user) {
		return (this.getUsername().equals(user));
	}

}
