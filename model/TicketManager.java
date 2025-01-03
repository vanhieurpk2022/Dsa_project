package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import view.LoginForm;

public class TicketManager {
	public List<Ticket> ticketList;
	private Set<saveInfomationUser> saveInfo = new LinkedHashSet<>();
	private Set<TicketCheck> getMyTicket;
	private Set<TicketCheck> store;
	private SavePass savepass = new SavePass(LoginForm.class);

	public TicketManager() {
		ticketList = new ArrayList<>();
		this.getMyTicket = new TreeSet<TicketCheck>(Comparator.comparing(TicketCheck::getDate).reversed()
				.thenComparing(TicketCheck::getSelectedSeats).thenComparing(TicketCheck::getFlightcode));
		loadTickets();

	}

	private void loadTickets() {
		File file = new File("src/data/tickets.txt");
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] data = line.split(",");
				if (data.length == 5) {
					ticketList.add(
							new Ticket(data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim(), data[4].trim()));
				}
			}
		} catch (Exception e) {
			System.out.println("Không thể đọc file, sử dụng dữ liệu mặc định");
		}
	}

	// Getter for ticketList
	public List<Ticket> getTicketList() {
		return ticketList;
	}

	public void addTicket(Ticket e) {
		ticketList.add(e);
	}

	public void removeTicket(Ticket e) {
		ticketList.remove(e);
	}

	public void addTicketCheck(TicketCheck check) {
		this.getMyTicket.add(check);
	}

	public Ticket findticket(String maCB, String flightDate, String flightTime, String departureAirport,
			String arrival) {
		for (Ticket ticket : ticketList) {
			if (ticket.getFlightCode().equals(maCB)) {
				ticket.setFlightDate(flightDate);
				ticket.setFlightTime(flightTime);
				ticket.setDepartureAirport(departureAirport);
				ticket.setArrivalAirport(arrival);
			}
		}
		return null;
	}

	public void WriteTick(String name) throws IOException {
		FileWriter file = new FileWriter(name);
		BufferedWriter buffer = new BufferedWriter(file);
		for (Ticket ticket : ticketList) {
			buffer.write(ticket.toString());
		}
		buffer.close();
		file.close();
	}

	public void removeTicket(String maChuyenBay) {
		Iterator<Ticket> iterator = ticketList.iterator();
		while (iterator.hasNext()) {
			Ticket ticket = iterator.next();
			if (ticket.getFlightCode().equals(maChuyenBay)) {
				iterator.remove(); // Sử dụng iterator để xóa
			}
		}
	}

	public void writeInfomationUser() throws IOException {
		FileWriter file = new FileWriter("src/data/InfomationUser.txt");
		BufferedWriter write = new BufferedWriter(file);
		for (saveInfomationUser saves : saveInfo) {
			write.write(saves.toString());
		}
		write.close();
		file.close();
	}

	public void loadTicketCheck() throws IOException {
		File file = new File("src/data/InfomationUser.txt");

		// Kiểm tra nếu file tồn tại và có nội dung
		if (!file.exists() || file.length() == 0) {
			return; // Nếu file không tồn tại hoặc trống, không làm gì và thoát phương thức
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String str;
			while ((str = reader.readLine()) != null) {
				StringTokenizer token = new StringTokenizer(str, "\t");

				// Xử lý dữ liệu từng dòng
				if (token.countTokens() >= 7) { // Đảm bảo dòng đủ 6 cột dữ liệu
					String getUser = token.nextToken();
					String password = token.nextToken();
					String getId = token.nextToken();
					String getName = token.nextToken();
					String getPhone = token.nextToken();
					String getEmail = token.nextToken();
					String getGender = token.nextToken();

					this.saveInfo.add(
							new saveInfomationUser(getUser, password, getId, getName, getPhone, getEmail, getGender));
				}
			}
		}
	}

	public void setSaveInfo(String username, String id, String name, String phone, String email, String gender) {
		for (saveInfomationUser save : saveInfo) {
			if (save.getUserName().equals(username)) {
				save.setId(id);
				save.setName(name);
				save.setPhone(phone);
				save.setEmail(email);
				save.setGender(gender);
				return;
			}
		}

	}

	public saveInfomationUser getsaveInfomation(String username) {

		for (saveInfomationUser saveInf : saveInfo) {
			if (saveInf.getUserName().equals(username)) {
				return saveInf;
			}
		}
		if (saveInfo == null || saveInfo.isEmpty()) {
			System.err.println("Danh sách người dùng trống hoặc chưa được khởi tạo.");
			return null;
		}
		return null;
	}

	public boolean isInfomationExits(String username) {
		for (saveInfomationUser save : saveInfo) {
			if (save.getUserName().equals(username)) {
				return true;
			}
		}
		return false;
	}

	public Set<saveInfomationUser> getSave() {
		return saveInfo;
	}

	public void setSave(Set<saveInfomationUser> save) {
		this.saveInfo = save;
	}

	public void addUserInf(saveInfomationUser e) {
		this.saveInfo.add(e);
	}

	public void SaveMyTicket() throws IOException {
		FileWriter fileName = new FileWriter("src/data/MyTicket.txt");
		BufferedWriter buff = new BufferedWriter(fileName);
		for (TicketCheck ticketCheck : getMyTicket) {
			buff.write(ticketCheck.toString());
		}
		buff.close();
		fileName.close();
	}

	public void LoadMyTicket() throws IOException {
		FileReader fileName = new FileReader("src/data/MyTicket.txt");
		BufferedReader buff = new BufferedReader(fileName);
		String str;
		while ((str = buff.readLine()) != null) {

			StringTokenizer tokenString = new StringTokenizer(str, "\t");

			// Kiểm tra nếu không đủ số lượng token
			if (tokenString.countTokens() < 13) {
				continue;
			}
			String Username = tokenString.nextToken();
			String flightCode = tokenString.nextToken();
			String fullName = tokenString.nextToken();
			String id = tokenString.nextToken();
			String phone = tokenString.nextToken();
			String dateFlight = tokenString.nextToken();
			String timeFlight = tokenString.nextToken();
			String departure = tokenString.nextToken();
			String arrival = tokenString.nextToken();
			String seat = tokenString.nextToken();
			String tax = tokenString.nextToken();
			String price = tokenString.nextToken();
			String total = tokenString.nextToken();

			getMyTicket.add(new TicketCheck(Username, flightCode, departure, arrival, fullName, dateFlight, timeFlight,
					price, tax, total, id, phone, seat));
		}
		buff.close();
		fileName.close();
	}

	public Set<TicketCheck> getGetMyTicket() {
		Set<TicketCheck> list = new TreeSet<TicketCheck>(Comparator.comparing(TicketCheck::getDate).reversed());
		for (TicketCheck l : getMyTicket) {
			if (l.getUserName().equals(savepass.getUsername())) {
				list.add(l);
			}
		}

		return list;
	}

	public Set<TicketCheck> getMyTicket() {
		return getMyTicket;
	}

	public void changePass(String username, String password) {
		for (saveInfomationUser inf : saveInfo) {
			if (inf.getUserName().equals(username)) {
				inf.setPassword(password);
			}
		}
	}

	public Set<saveInfomationUser> getSaveInfo() {
		return saveInfo;
	}

	public void setSaveInfo(Set<saveInfomationUser> saveInfo) {
		this.saveInfo = saveInfo;
	}

}
