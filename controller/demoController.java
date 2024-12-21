package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.SavePass;
import model.Ticket;
import model.TicketCheck;
import model.TicketManager;
import model.UserManager;
import model.saveInfomationUser;
import view.ChangePassword;
import view.Demo;
import view.FlightConfirmationForm;
import view.LoginForm;

public class demoController implements ActionListener {
	private Demo demo;
	private TicketManager manager = new TicketManager();
	private TicketCheck check = new TicketCheck();
	private UserManager usermager = new UserManager();
	private SavePass savepass = new SavePass(LoginForm.class);
	private boolean isEditing = false; // Trạng thái chỉnh sửa ban đầu là false
	private boolean intinial = false;

	public demoController(Demo demo) {
		this.demo = demo;

	}

	public demoController() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// xử lí cập nhật profile

		// TODO Auto-generated method stub
		String getAction = e.getActionCommand();
		JButton jbutotn = (JButton) e.getSource();

		// Đổi màu cho nút được nhấn
		if (getAction.equals("Đặt vé") || getAction.equals("BẮT ĐẦU ĐẶT VÉ")) {
			demo.resetButtonColors();
			demo.button2.setBackground(Color.lightGray);
			demo.button2.setForeground(Color.WHITE);
		} else if (getAction == "Đặt vé" || getAction == "Vé của tôi" || getAction == "Thông tin"
				|| getAction == "Trang chủ") {
			demo.resetButtonColors();
			jbutotn.setBackground(Color.white);
			jbutotn.setBackground(Color.lightGray);

		}

		if (getAction.equals("Đăng xuất")) {
			int a = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn thoát?", "Xác nhận",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (a == JOptionPane.YES_OPTION) {
				demo.dispose();
				new LoginForm();
			}

		} else if (getAction.equals("Đặt vé") || getAction.equals("BẮT ĐẦU ĐẶT VÉ")) {
			demo.updateTab("Đặt vé", demo.tickets());
		} else if (getAction.equals("Vé của tôi")) {
			demo.updateTab("Vé của tôi", demo.myTickets());

		} else if (getAction.equals("Thông tin")) {
			demo.updateTab("Thông tin", demo.proFile());
			if (!isEditing) {
				try { // load
					manager.loadTicketCheck();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Lỗi khi lưu dữ liệu: " + e1.getMessage(), "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				}
				isEditing = true;
			}
			if (manager.getsaveInfomation(savepass.getUsername()) != null) { // Thêm điều kiện initialLoad
				demo.putInfo(manager.getsaveInfomation(savepass.getUsername()));
				demo.setEditFalse();
			} else {
				isEditing = false;
			}
			System.out.println(manager.getsaveInfomation(savepass.getUsername()) != null);
		} else if (getAction.equals("Trang chủ")) {
			demo.updateTab("Trang chủ", demo.home());
		} else if (jbutotn == demo.buttonContinue) {
			int selectedRow = demo.table.getSelectedRow();

			if (selectedRow != -1) {
				FlightConfirmationForm confirmationForm = new FlightConfirmationForm();
				try {
					manager.loadTicketCheck();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (manager.getsaveInfomation(savepass.getUsername()) != null) { // danh sach khashc đã được tạo
					saveInfomationUser get = manager.getsaveInfomation(savepass.getUsername());
					confirmationForm.setInfomationUser(get.getId(), get.getName(), get.getPhone());

				}

				String flightCode = (String) demo.table.getValueAt(selectedRow, 0);
				String flightDate = (String) demo.table.getValueAt(selectedRow, 1);
				String flightTime = (String) demo.table.getValueAt(selectedRow, 2);
				String departureAirport = (String) demo.table.getValueAt(selectedRow, 3);
				String arrivalAirport = (String) demo.table.getValueAt(selectedRow, 4);

				confirmationForm.setFlightDetails(flightCode, flightDate, flightTime, departureAirport, arrivalAirport);
				savepass.saveFlightCode(flightCode);
			} else {
				JOptionPane.showMessageDialog(null, "Vui lòng chọn một chuyến bay!");
			}
		} else if (jbutotn == demo.seach1) {
			String departure = (String) demo.comboBox1.getSelectedItem();
			String arrival = (String) demo.comboBox2.getSelectedItem();
			demo.filterTickets(departure, arrival);
		} else if (jbutotn == demo.search2) {
			String departure = (String) demo.comboBox1.getSelectedItem();
			String arrival = (String) demo.comboBox2.getSelectedItem();
			java.util.Date selectedDate = demo.dateChooser.getDate();

			if (selectedDate == null) {
				JOptionPane.showMessageDialog(null, "Vui lòng chọn một ngày để tìm kiếm!");
				return;
			}

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String selectedDateString = dateFormat.format(selectedDate);
//			System.out.println(selectedDateString);

			DefaultTableModel model = (DefaultTableModel) demo.table.getModel();
			model.setRowCount(0);

			boolean hasResults = false;

			boolean isAllDeparture = departure.equals("All");
			boolean isAllArrival = arrival.equals("All");

			for (Ticket ticket : manager.ticketList) {
				String flightDate = ticket.getFlightDate();
				String departureAirport = ticket.getDepartureAirport();
				String arrivalAirport = ticket.getArrivalAirport();

				boolean dateMatch = selectedDateString.equals(flightDate);
				boolean departureMatch = isAllDeparture || departure.equals(departureAirport);
				boolean arrivalMatch = isAllArrival || arrival.equals(arrivalAirport);

				// Trường hợp 1: Chỉ tìm theo ngày
				boolean onlyDateSearch = !isAllDeparture && !isAllArrival && departure.isEmpty() && arrival.isEmpty();

				// Trường hợp 2: Tìm theo ngày và địa điểm
				boolean dateAndLocationSearch = !departure.isEmpty() || !arrival.isEmpty();

				if (onlyDateSearch && dateMatch) {
					demo.addTicketToTable(model, ticket);
					hasResults = true;
				} else if (dateAndLocationSearch) {
					if (dateMatch && departureMatch && arrivalMatch) {
						demo.addTicketToTable(model, ticket);
						hasResults = true;
					}
				}
			}

			if (!hasResults) {
				JOptionPane.showMessageDialog(null, "Không có chuyến bay nào phù hợp với tiêu chí tìm kiếm.");
			}
		} else if (jbutotn == demo.refreshButton) {
			demo.comboBox1.setSelectedIndex(0);
			demo.comboBox2.setSelectedIndex(0);

			demo.dateChooser.setDate(null);

			DefaultTableModel model1 = (DefaultTableModel) demo.table.getModel();
			model1.setRowCount(0);

			for (Ticket ticket : manager.ticketList) {
				model1.addRow(new Object[] { ticket.getFlightCode(), ticket.getFlightDate(), ticket.getFlightTime(),
						ticket.getDepartureAirport(), ticket.getArrivalAirport() });
			}
		}

//		xử lí action nhập thông tin profile
		if (getAction.equals("Lưu"))

		{
			demo.setEditFalse();
			if (manager.isInfomationExits(savepass.getUsername())) {
				// Nếu đã tồn tại thì chỉ cập nhật
				manager.setSaveInfo(savepass.getUsername(), demo.getId(), demo.getName(), demo.getPhone(),
						demo.getEmail(), demo.getGender());
			} else {
				// Nếu chưa tồn tại thì thêm mới
				saveInfomationUser savm = new saveInfomationUser(savepass.getUsername(), savepass.getPassword(),
						demo.getId(), demo.getName(), demo.getPhone(), demo.getEmail(), demo.getGender());
				manager.addUserInf(savm);
			}
			try {
				manager.writeTicketCheck();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			intinial = false;
		} else if (getAction.equals("Sửa")) {
			demo.setEditTrue();
			intinial = true;
		}
		if (getAction.equals(" ")) {
			new ChangePassword();
		}

	}

	public void setIntinial(boolean a) {
		this.intinial = a;
	}

}
