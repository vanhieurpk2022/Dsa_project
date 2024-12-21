package model;

import java.util.Objects;

import view.LoginForm;

public class saveInfomationUser {
	private String userName;
	private String id;
	private String name;
	private String phone;
	private String email;
	private String gender;
	private String password;
	private SavePass save = new SavePass(LoginForm.class);
	private TicketCheck tick = new TicketCheck();

	public saveInfomationUser(String userName, String password, String id, String name, String phone, String email,
			String gender) {
		this.userName = userName;
		this.password = password;
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.gender = gender;
	}

	public saveInfomationUser(String userName, String id, String name, String phone) {
		this.userName = userName;
		this.id = id;
		this.name = name;
		this.phone = phone;
	}

	public saveInfomationUser() {
	}

	public String toString() {
		return "\n" + userName + "\t" + this.password + "\t" + this.id + "\t" + this.name + "\t" + this.phone + "\t"
				+ this.email + "\t" + this.gender;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public SavePass getSave() {
		return save;
	}

	public void setSave(SavePass save) {
		this.save = save;
	}

	public TicketCheck getTick() {
		return tick;
	}

	public void setTick(TicketCheck tick) {
		this.tick = tick;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		saveInfomationUser other = (saveInfomationUser) obj;
		return Objects.equals(userName, other.userName);
	}

}
