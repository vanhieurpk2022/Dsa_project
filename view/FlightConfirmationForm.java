package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.SavePass;
import model.SeatSelector;
import model.Ticket;
import model.TicketCheck;
import model.TicketManager;
import model.saveInfomationUser;

public class FlightConfirmationForm extends JFrame {
	private final List<Integer> selectedSeatsList = new ArrayList<>();
	private final SeatDisplayComponent displayComponent;
	private SeatSelector seatSelector;
	private JTextField departureField, idField, arrivalField, nameField, dateField, phoneField, timeField, priceField;
	private Ticket ticket;
	private JButton confirmButton, closeButton;
	private JTextField taxField;
	private JTextField totalField;
	private double thue = 0.08;
	private double priceT = 1000000;
	private TicketManager ticketManager = new TicketManager();

	public FlightConfirmationForm() {
		// Set up the frame
		setTitle("Xác Nhận");
		setSize(800, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);

		// Main panel
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(Color.LIGHT_GRAY);

		// Header panel
		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(new Color(0, 204, 204));
		JLabel headerLabel = new JLabel("XÁC NHẬN", loadIcon("/img/travel.png", 64, 64), JLabel.CENTER);
		headerLabel.setFont(new Font("Arial", Font.BOLD, 34));
		headerLabel.setForeground(Color.WHITE);
		headerPanel.add(headerLabel);

		// Form panel
		JPanel formPanel = new JPanel();
		formPanel.setLayout(new GridLayout(6, 4, 20, 20));
		formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

		// Left column
		// Create labels
		JLabel departureLabel = new JLabel(" Sân Bay Đi:", loadIcon("/img/location-pin.png", 25, 25), JLabel.LEFT);
		departureLabel.setFont(departureLabel.getFont().deriveFont(17f));

		JLabel idLabel = new JLabel("CMND:", loadIcon("/img/business-card.png", 25, 25), JLabel.LEFT);
		idLabel.setFont(departureLabel.getFont().deriveFont(17f));

		JLabel arrivalLabel = new JLabel("Sân Bay Đến:", loadIcon("/img/destination.png", 25, 25), JLabel.LEFT);
		arrivalLabel.setFont(departureLabel.getFont().deriveFont(17f));

		JLabel nameLabel = new JLabel("Họ Và Tên:", loadIcon("/img/id-card.png", 25, 25), JLabel.LEFT);
		nameLabel.setFont(departureLabel.getFont().deriveFont(17f));

		JLabel dateLabel = new JLabel("Ngày Bay:", loadIcon("/img/timetable.png", 25, 25), JLabel.LEFT);
		dateLabel.setFont(departureLabel.getFont().deriveFont(17f));

		JLabel phoneLabel = new JLabel("Số Điện Thoại:", loadIcon("/img/call.png", 25, 25), JLabel.LEFT);
		phoneLabel.setFont(departureLabel.getFont().deriveFont(17f));

		JLabel timeLabel = new JLabel("Thời Gian Bay:", loadIcon("/img/clock_ (1).png", 25, 25), JLabel.LEFT);
		timeLabel.setFont(departureLabel.getFont().deriveFont(17f));

		JLabel priceLabel = new JLabel("Giá Vé:", loadIcon("/img/tag.png", 25, 25), JLabel.LEFT);
		priceLabel.setFont(departureLabel.getFont().deriveFont(17f));

		JLabel seatLabel = new JLabel(" Đặt Ghế:", loadIcon("/img/seating.png", 25, 25), JLabel.LEFT);
		seatLabel.setFont(departureLabel.getFont().deriveFont(17f));

		JLabel taxLabel = new JLabel("Thuế:", loadIcon("/img/taxes.png", 25, 25), JLabel.LEFT);
		taxLabel.setFont(departureLabel.getFont().deriveFont(17f));

		JLabel classLabel = new JLabel("Thông tin Ghế:", loadIcon("/img/tickets_vip.png", 25, 25), JLabel.LEFT);
		classLabel.setFont(departureLabel.getFont().deriveFont(17f));

		JLabel totalLabel = new JLabel("Tổng Tiền:", loadIcon("/img/wealth.png", 25, 25), JLabel.LEFT);
		totalLabel.setFont(departureLabel.getFont().deriveFont(17f));

		// Create text fields and buttons
		departureField = new JTextField();
		idField = new JTextField();
		arrivalField = new JTextField();
		nameField = new JTextField();
		dateField = new JTextField();
		phoneField = new JTextField();
		timeField = new JTextField();
		priceField = new JTextField();

		JButton seatButton = new JButton("Chọn");
		seatButton.addActionListener(e -> {
			seatSelector.show();
			// Update price after seat selection dialog is closed
			updatePriceAfterSeatSelection();
		});

		taxField = new JTextField();
		totalField = new JTextField();

		// <==Khởi tạo field hiển thị ghế ==>
		displayComponent = new SeatDisplayComponent();
		seatSelector = new SeatSelector(this, selectedSeatsList, displayComponent);

		// <====>
		// Add labels and fields to formPanel
		formPanel.add(departureLabel);
		formPanel.add(departureField);

		formPanel.add(idLabel);
		formPanel.add(idField);

		formPanel.add(arrivalLabel);
		formPanel.add(arrivalField);

		formPanel.add(nameLabel);
		formPanel.add(nameField);

		formPanel.add(dateLabel);
		formPanel.add(dateField);

		formPanel.add(phoneLabel);
		formPanel.add(phoneField);

		formPanel.add(timeLabel);
		formPanel.add(timeField);

		formPanel.add(priceLabel);
		formPanel.add(priceField);

		formPanel.add(seatLabel);
		formPanel.add(seatButton);

		formPanel.add(taxLabel);
		formPanel.add(taxField);

		formPanel.add(classLabel);
		formPanel.add(displayComponent);

		formPanel.add(totalLabel);
		formPanel.add(totalField);

		// Action panel
		JPanel actionPanel = new JPanel();
		actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		confirmButton = new JButton("Xác Nhận", loadIcon("/img/check.png", 20, 20));
		confirmButton.addActionListener(new XuLyButton());
		closeButton = new JButton("Đóng", loadIcon("/img/exit-icon.png", 20, 20));
		closeButton.addActionListener(new XuLyButton());
		actionPanel.add(confirmButton);
		actionPanel.add(closeButton);

		// Add panels to the main panel
		mainPanel.add(headerPanel, BorderLayout.NORTH);
		mainPanel.add(formPanel, BorderLayout.CENTER);
		mainPanel.add(actionPanel, BorderLayout.SOUTH);

		// Add main panel to the frame
		add(mainPanel);
		setVisible(true);

	}

	public class XuLyButton implements ActionListener {
		SavePass savepass = new SavePass(LoginForm.class);

		@Override
		public void actionPerformed(ActionEvent e) {

			// TODO Auto-generated method stub
			try {
				ticketManager.loadTicketCheck();
				ticketManager.LoadMyTicket();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// Đổi màu cho nút được nhấn
			JButton clickedButton = (JButton) e.getSource();
			if (clickedButton == closeButton) {
				setVisible(false);
			} else if (clickedButton == confirmButton) {

				String departure = departureField.getText();
				String arrival = arrivalField.getText();
				String name = nameField.getText();
				String date = dateField.getText();
				String time = timeField.getText();
				String price = priceField.getText();
				String tax = taxField.getText();
				String total = totalField.getText();
				String id = idField.getText();
				String phone = phoneField.getText();
				String selectedSeats = displayComponent.getSelectedSeat();

				// Tạo đối tượng Ticket mới
				if (departure.isEmpty() || arrival.isEmpty() || name.isEmpty() || date.isEmpty() || time.isEmpty()
						|| price.isEmpty() || tax.isEmpty() || total.isEmpty() || id.isEmpty() || phone.isEmpty()
						|| selectedSeats.equals("Chưa chọn ghế")) {
					JOptionPane.showMessageDialog(FlightConfirmationForm.this, "Vui lòng nhập đủ thông tin.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				int confirmResult = JOptionPane.showConfirmDialog(FlightConfirmationForm.this,
						"Bạn có chắc chắn muốn xác nhận thông tin này không?", "Xác nhận lần nữa",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (confirmResult == JOptionPane.YES_OPTION) {
					TicketCheck newTicket = new TicketCheck(savepass.getUsername(),
							savepass.getFlightCode("FlightCode"), departure, arrival, name, date, time, price, tax,
							total, id, phone, selectedSeats);
					if (ticketManager.getsaveInfomation(savepass.getUsername()) == null) {
						saveInfomationUser save = new saveInfomationUser(savepass.getUsername(), savepass.getPassword(),
								id, name, phone, " ", " ");
						ticketManager.addUserInf(save);
						try {
							ticketManager.writeInfomationUser();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

					ticketManager.addTicketCheck(newTicket);
					try {
						ticketManager.SaveMyTicket();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(FlightConfirmationForm.this, "Đặt vé thành công!", "Confirmation",
							JOptionPane.INFORMATION_MESSAGE);
					FlightConfirmationForm.this.dispose();
				}
				// Close the form

			}
		}
	}

	private void updatePriceAfterSeatSelection() {
		String departure = departureField.getText().trim();
		String arrival = arrivalField.getText().trim();
		String selectedSeats = displayComponent.getSelectedSeat();

		double basePrice = calculateBasePrice(departure, arrival);

		// Adjust price based on seat type
		if (!selectedSeats.equals("Chưa chọn ghế")) {
			basePrice = adjustPriceForSeat(basePrice, selectedSeats);
		}

		if (basePrice > 0) {
			// Update price field with formatted value
			priceField.setText(String.format("%,.0f VND", basePrice));

			// Calculate and update tax
			double tax = basePrice * thue;
			taxField.setText(String.format("%,.0f VND (%.0f%%)", tax, thue * 100));

			// Calculate and update total
			double total = basePrice + tax;
			totalField.setText(String.format("%,.0f VND", total));
		}
	}

	// New method to adjust price based on seat type
	private double adjustPriceForSeat(double basePrice, String seatInfo) {
		String[] seats = seatInfo.split(", ");
		double highestPrice = basePrice;

		for (String seat : seats) {
			if (seat.startsWith("V-")) {
				// VIP seat - double the price
				highestPrice = Math.max(highestPrice, basePrice * 2);
			} else if (seat.startsWith("G-")) {
				// Regular seat - keep base price
				highestPrice = Math.max(highestPrice, basePrice);
			}
		}

		return highestPrice;
	}

	// Calculate base price method
	private static final Map<String, Map<String, Double>> ROUTE_PRICES = new HashMap<>();
	static {
		ROUTE_PRICES.put("HÀ NỘI", new HashMap<>() {
			{
				put("ĐÀ NẴNG", 1000000.0);
				put("NHA TRANG", 1200000.0);
				put("TP.HCM", 1500000.0);
			}
		});
		ROUTE_PRICES.put("ĐÀ NẴNG", new HashMap<>() {
			{
				put("HÀ NỘI", 1000000.0);
				put("NHA TRANG", 1000000.0);
				put("TP.HCM", 1200000.0);
			}
		});
		ROUTE_PRICES.put("NHA TRANG", new HashMap<>() {
			{
				put("TP.HCM", 1000000.0);
				put("ĐÀ NẴNG", 1000000.0);
				put("HÀ NỘI", 1200000.0);
			}
		});
		ROUTE_PRICES.put("TP.HCM", new HashMap<>() {
			{
				put("NHA TRANG", 1000000.0);
				put("ĐÀ NẴNG", 1200000.0);
				put("HÀ NỘI", 1500000.0);
			}
		});
	}

	private double calculateBasePrice(String departure, String arrival) {
		Map<String, Double> priceMap = ROUTE_PRICES.get(departure.toUpperCase());
		if (priceMap != null) {
			Double price = priceMap.get(arrival.toUpperCase());
			if (price != null) {
				return price;
			}
		}
		return 0;
	}

	public void setInfomationUser(String id, String name, String phone) {
		idField.setText(id);
		nameField.setText(name);
		phoneField.setText(phone);
		idField.setEditable(false);
		nameField.setEditable(false);
		phoneField.setEditable(false);
	}

	public String getIdCccd() {
		return idField.getText();
	}

	public String getNameGuest() {
		return nameField.getText();
	}

	public String getPhoneGuest() {
		return phoneField.getText();
	}

	// Modify setFlightDetails to update price
	public void setFlightDetails(String flightCode, String flightDate, String flightTime, String departureAirport,
			String arrivalAirport) {
		departureField.setText(departureAirport);
		arrivalField.setText(arrivalAirport);
		dateField.setText(flightDate);
		timeField.setText(flightTime);
		updatePriceAfterSeatSelection();
		departureField.setEditable(false);
		arrivalField.setEditable(false);
		dateField.setEditable(false);
		timeField.setEditable(false);
		priceField.setEditable(false);
		taxField.setEditable(false);
		totalField.setEditable(false);
	}

	// <===>
	public ImageIcon loadIcon(String path, int width, int height) {
		try {
			ImageIcon originalIcon = new ImageIcon(getClass().getResource(path));
			Image image = originalIcon.getImage();
			Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			return new ImageIcon(resizedImage);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
