package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import com.toedter.calendar.JDateChooser;

import controller.adminController;
import model.Ticket;
import model.TicketCheck;
import model.TicketManager;
import model.UserManager;
import model.saveInfomationUser;

public class adminSetting extends JFrame {
	private JMenuBar menuBar;
	private JMenu managerMenu;
	private ImageIcon icon;
	private JMenu helpMenu;
	private ImageIcon iconhelp;
	private JToolBar toolBar;
	public JButton button1, button2, button3, button4, button5;
	private JTabbedPane tabbedPane;
	private JButton selectedButton = null;
	private TicketManager manager = new TicketManager();
	private List<Object[]> originalData;
	public JButton btnUpdate, btnAdd, btnDelete, btnClear;
	public JSpinner timeSpinner;
	public JDateChooser dateChooser;
	public JComboBox<String> cboSanBayDi, cboSanBayDen;
	public JPanel panelM;
	private adminController admin;
	public Set<String> generatedCodes;
	public DefaultTableModel tableModel;
	public JTable table;
	private UserManager usermanager = new UserManager();

	public adminSetting() {
		// TODO Auto-generated constructor stub

		setTitle("Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1300, 750);
		setResizable(false);
		setLocationRelativeTo(null);

		admin = new adminController(this);

		// Tạo JMenuBar và thêm các JMenu
		menuBar = new JMenuBar();

		managerMenu = new JMenu("Manager");
		icon = new ImageIcon(adminSetting.class.getResource("/img/Settings-icon.png"));
		managerMenu.setIcon(new ImageIcon(icon.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));

		helpMenu = new JMenu("Help");
		iconhelp = new ImageIcon(adminSetting.class.getResource("/img/Question-icon.png"));
		helpMenu.setIcon(new ImageIcon(iconhelp.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));

		menuBar.add(managerMenu);
		menuBar.add(helpMenu);
		this.setJMenuBar(menuBar);

		// Tạo JToolBar chứa các JButton
		toolBar = new JToolBar();
		Dimension buttonSize = new Dimension(130, 60);
		button1 = createButton("Trang chủ", "/img/home-icon.png", buttonSize, true);
		button2 = createButton("Quản lý vé", "/img/ticketmanger.png", buttonSize, false);
		button3 = createButton("Quản lý chuyến bay", "/img/flight-management.png", buttonSize, false);
		button4 = createButton("Quản ký khách hàng", "/img/Profile-icon.png", buttonSize, false);
		button5 = createButton("Đăng xuất", "/img/exit-icon.png", buttonSize, false);

		button1.addActionListener(admin);
		button2.addActionListener(admin);
		button3.addActionListener(admin);
		button4.addActionListener(admin);
		button5.addActionListener(admin);

		toolBar.add(button1);
		toolBar.add(button2);
		toolBar.add(button3);
		toolBar.add(button4);
		toolBar.add(button5);

		// Tạo JTabbedPane và thêm các tab
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Trang chủ", home());

		// Sắp xếp các thành phần vào JFrame
		this.getContentPane().add(toolBar, "North");
		this.getContentPane().add(tabbedPane, "Center");

		this.setVisible(true);
	}

	public JPanel home() {
		// TODO Auto-generated method stub
		JPanel panel = new JPanel();
		return panel;
	}

	public JPanel mtickets() {
		// Tạo panel chính với BorderLayout
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		// Tiêu đề
		JLabel titleLabel = new JLabel("Quản lý vé máy bay", JLabel.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
		titleLabel.setForeground(new Color(33, 37, 41));
		mainPanel.add(titleLabel, BorderLayout.NORTH);

		// Thay đổi tiêu đề cột theo thuộc tính của TicketCheck
		String[] columnNames = { "STT", "Mã chuyến bay", "Tên khách hàng", "CCCD", "SĐT", "Ngày bay", "Giờ bay",
				"Điểm đi", "Điểm đến", "Số ghế", "Thuế", "Giá vé", "Tổng tiền" };

		// Tạo model cho table
		DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		try {
			manager.LoadMyTicket();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Lấy danh sách vé từ TicketManager
		Set<TicketCheck> allTickets = manager.getMyTicket();
		int stt = 1;

		// Thêm dữ liệu từ TicketCheck vào model
		for (TicketCheck ticket : allTickets) {
			Object[] rowData = { stt++, ticket.getFlightcode(), ticket.getPassengerName(), ticket.getId(),
					ticket.getPhone(), ticket.getDate(), ticket.getTime(), ticket.getDeparture(), ticket.getArrival(),
					ticket.getSelectedSeats(), ticket.getTax(), ticket.getPrice(), ticket.getTotal() };
			model.addRow(rowData);
		}

		// Thiết lập độ rộng từng cột một cách rõ ràng và hợp lý

		JTable ticketTable = new JTable(model);

		ticketTable.getColumnModel().getColumn(0).setPreferredWidth(40); // STT
		ticketTable.getColumnModel().getColumn(1).setPreferredWidth(130); // Flight Code
		ticketTable.getColumnModel().getColumn(2).setPreferredWidth(130); // Passenger Name
		ticketTable.getColumnModel().getColumn(3).setPreferredWidth(90); // Ticket ID
		ticketTable.getColumnModel().getColumn(4).setPreferredWidth(90); // Phone
		ticketTable.getColumnModel().getColumn(5).setPreferredWidth(80); // Date
		ticketTable.getColumnModel().getColumn(6).setPreferredWidth(100); // Time
		ticketTable.getColumnModel().getColumn(7).setPreferredWidth(80); // Departure
		ticketTable.getColumnModel().getColumn(8).setPreferredWidth(80); // Arrival
		ticketTable.getColumnModel().getColumn(9).setPreferredWidth(50); // Selected Seats
		ticketTable.getColumnModel().getColumn(10).setPreferredWidth(80); // Tax
		ticketTable.getColumnModel().getColumn(11).setPreferredWidth(100); // Price
		ticketTable.getColumnModel().getColumn(12).setPreferredWidth(100); // Total
		ticketTable.setFont(new Font("Arial", Font.PLAIN, 14));
		JTableHeader header = ticketTable.getTableHeader();
		header.setFont(new Font("Arial", Font.BOLD, 14));
		header.setBackground(new Color(63, 81, 181));
		header.setForeground(Color.WHITE);

		JScrollPane tableScrollPane = new JScrollPane(ticketTable);
		tableScrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		mainPanel.add(tableScrollPane, BorderLayout.CENTER);

		// Nút tìm kiếm
		JButton searchButton = new JButton("Tìm khách hàng");
		searchButton.setFont(new Font("Arial", Font.BOLD, 16));
		searchButton.setBackground(new Color(76, 175, 80));
		searchButton.setForeground(Color.WHITE);
		searchButton.setFocusPainted(false);
		searchButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		// Nút chỉnh sửa
//		JButton editButton = new JButton("Chỉnh sửa");
//		editButton.setFont(new Font("Arial", Font.BOLD, 16));
//		editButton.setBackground(new Color(255, 165, 0));
//		editButton.setForeground(Color.WHITE);
//		editButton.setFocusPainted(false);
//		editButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(searchButton);
//		buttonPanel.add(editButton);
		buttonPanel.setBackground(Color.WHITE);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		// Sự kiện tìm kiếm khách hàng
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Hiển thị hộp thoại để nhập từ khóa tìm kiếm
				String searchTerm = JOptionPane.showInputDialog(mainPanel, "Nhập tên hoặc SĐT khách hàng:",
						"Tìm kiếm khách hàng", JOptionPane.PLAIN_MESSAGE);

				// Kiểm tra nếu người dùng không nhập hoặc hủy
				if (searchTerm == null || searchTerm.trim().isEmpty()) {
					JOptionPane.showMessageDialog(mainPanel, "Vui lòng nhập thông tin để tìm kiếm!", "Thông báo",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				// Lọc dữ liệu theo từ khóa
				model.setRowCount(0); // Xóa tất cả các hàng hiện tại trong bảng
				boolean found = false; // Trạng thái tìm kiếm
				int stt = 1;
				// Duyệt qua danh sách vé để tìm kiếm
				for (TicketCheck t : manager.getMyTicket()) {
					String name = t.getPassengerName() != null ? t.getPassengerName() : ""; // Xử lý null
					String phone = t.getPhone() != null ? t.getPhone() : ""; // Xử lý null

					// Kiểm tra nếu tên hoặc số điện thoại chứa từ khóa tìm kiếm
					if (name.toLowerCase().contains(searchTerm.toLowerCase()) || phone.contains(searchTerm)) {
						// Thêm hàng vào bảng nếu tìm thấy
						model.addRow(new Object[] { stt++, t.getFlightcode(), // Mã chuyến bay
								t.getPassengerName(), // Tên khách hàng
								t.getId(), // Số CMND/CCCD
								t.getPhone(), // Số điện thoại
								t.getDate(), // Ngày bay
								t.getTime(), // Giờ bay
								t.getDeparture(), // Điểm đi
								t.getArrival(), // Điểm đến
								t.getSelectedSeats(), // Ghế ngồi
								t.getTax(), // Thuế
								t.getPrice(), // Giá vé
								t.getTotal() // Tổng tiền
						});
						found = true; // Cập nhật trạng thái tìm thấy
					}
				}

				// Hiển thị thông báo nếu không tìm thấy kết quả
				if (!found) {
					JOptionPane.showMessageDialog(mainPanel, "Không tìm thấy khách hàng phù hợp!", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		// Sự kiện chỉnh sửa thông tin vé
//		editButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				int selectedRow = ticketTable.getSelectedRow();
//				if (selectedRow != -1) {
//					// Lấy thông tin dòng đã chọn
//					Object[] rowData = new Object[tableModel.getColumnCount()];
//					for (int i = 0; i < rowData.length; i++) {
//						rowData[i] = tableModel.getValueAt(selectedRow, i);
//					}
//
//					// Mở hộp thoại để chỉnh sửa tất cả thông tin
//					String newFlightCode = JOptionPane.showInputDialog(mainPanel, "Nhập mã chuyến bay:", rowData[0]);
//					if (newFlightCode != null && !newFlightCode.trim().isEmpty()) {
//						tableModel.setValueAt(newFlightCode, selectedRow, 0); // Cập nhật mã chuyến bay
//					}
//
//					String newName = JOptionPane.showInputDialog(mainPanel, "Nhập tên khách hàng:", rowData[1]);
//					if (newName != null && !newName.trim().isEmpty()) {
//						tableModel.setValueAt(newName, selectedRow, 1); // Cập nhật tên khách hàng
//					}
//
//					String newCCCD = JOptionPane.showInputDialog(mainPanel, "Nhập số CCCD:", rowData[2]);
//					if (newCCCD != null && !newCCCD.trim().isEmpty()) {
//						tableModel.setValueAt(newCCCD, selectedRow, 2); // Cập nhật số CCCD
//					}
//
//					String newGender = JOptionPane.showInputDialog(mainPanel, "Nhập giới tính:", rowData[3]);
//					if (newGender != null && !newGender.trim().isEmpty()) {
//						tableModel.setValueAt(newGender, selectedRow, 3); // Cập nhật giới tính
//					}
//
//					String newDeparture = JOptionPane.showInputDialog(mainPanel, "Nhập điểm đi:", rowData[4]);
//					if (newDeparture != null && !newDeparture.trim().isEmpty()) {
//						tableModel.setValueAt(newDeparture, selectedRow, 4); // Cập nhật điểm đi
//					}
//
//					String newArrival = JOptionPane.showInputDialog(mainPanel, "Nhập điểm đến:", rowData[5]);
//					if (newArrival != null && !newArrival.trim().isEmpty()) {
//						tableModel.setValueAt(newArrival, selectedRow, 5); // Cập nhật điểm đến
//					}
//
//					String newDate = JOptionPane.showInputDialog(mainPanel, "Nhập ngày bay:", rowData[6]);
//					if (newDate != null && !newDate.trim().isEmpty()) {
//						tableModel.setValueAt(newDate, selectedRow, 6); // Cập nhật ngày bay
//					}
//
//					String newPhone = JOptionPane.showInputDialog(mainPanel, "Nhập số điện thoại:", rowData[7]);
//					if (newPhone != null && !newPhone.trim().isEmpty()) {
//						tableModel.setValueAt(newPhone, selectedRow, 7); // Cập nhật số điện thoại
//					}
//
//					String newEmail = JOptionPane.showInputDialog(mainPanel, "Nhập email:", rowData[8]);
//					if (newEmail != null && !newEmail.trim().isEmpty()) {
//						tableModel.setValueAt(newEmail, selectedRow, 8); // Cập nhật email
//					}
//
//					// Cập nhật lại dữ liệu gốc
//					originalData.set(selectedRow, rowData);
//				} else {
//					JOptionPane.showMessageDialog(mainPanel, "Vui lòng chọn một vé để chỉnh sửa!", "Thông báo",
//							JOptionPane.WARNING_MESSAGE);
//				}
//			}
//		});

		return mainPanel;
	}

	public JPanel mPlane() {
		generatedCodes = new HashSet<>();

		panelM = new JPanel(new BorderLayout(15, 15));
		panelM.setBorder(new EmptyBorder(15, 15, 15, 15));
		panelM.setBackground(new Color(240, 240, 240));

		// Input form panel with gradient background
		JPanel inputPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				int w = getWidth(), h = getHeight();
				GradientPaint gp = new GradientPaint(0, 0, new Color(255, 255, 255), 0, h, new Color(240, 240, 245));
				g2d.setPaint(gp);
				g2d.fillRect(0, 0, w, h);
			}
		};
		inputPanel.setLayout(new GridBagLayout());
		inputPanel.setPreferredSize(new Dimension(350, 0));

		inputPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(8, 8, 8, 8),
				BorderFactory.createTitledBorder(new LineBorder(new Color(100, 100, 100), 1, true),
						"Thông tin chuyến bay", TitledBorder.LEFT, TitledBorder.TOP,
						new Font("Segoe UI", Font.BOLD, 16), new Color(50, 50, 50))));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 10, 8, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.weightx = 1.0;

		Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
		Font inputFont = new Font("Segoe UI", Font.PLAIN, 13);

		JLabel[] labels = { createStyledLabel("Ngày cất cánh:", labelFont),
				createStyledLabel("Thời gian bay:", labelFont), createStyledLabel("Sân bay đi:", labelFont),
				createStyledLabel("Sân bay đến:", labelFont) };

		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("dd-MM-yyyy");
		dateChooser.setFont(inputFont);
		styleComponent(dateChooser);
		dateChooser.setPreferredSize(new Dimension(0, 30));

		timeSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
		timeSpinner.setEditor(timeEditor);
		timeSpinner.setFont(inputFont);
		styleComponent(timeSpinner);
		timeSpinner.setPreferredSize(new Dimension(0, 30));

		// Create airport combo boxes with sample data
		String[] airports = { "Chọn sân bay", "Đà Nẵng", "TP.HCM", "Nha Trang", "Hà Nội" };

		cboSanBayDi = new JComboBox<>(airports);
		cboSanBayDen = new JComboBox<>(airports);

		// Style the combo boxes
		cboSanBayDi.setFont(inputFont);
		cboSanBayDen.setFont(inputFont);
		styleComponent(cboSanBayDi);
		styleComponent(cboSanBayDen);

		Dimension comboBoxSize = new Dimension(0, 30);
		cboSanBayDi.setPreferredSize(comboBoxSize);
		cboSanBayDen.setPreferredSize(comboBoxSize);

		// Add components to form
		int row = 0;
		addFormRow(inputPanel, gbc, row++, labels[0], dateChooser);
		addFormRow(inputPanel, gbc, row++, labels[1], timeSpinner);
		addFormRow(inputPanel, gbc, row++, labels[2], cboSanBayDi);
		addFormRow(inputPanel, gbc, row++, labels[3], cboSanBayDen);

		// Table with modern styling
		String[] columnNames = { "Mã chuyến bay", "Ngày cất cánh", "Thời gian bay", "Sân bay đi", "Sân bay đến" };
		tableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// Set up table model
		for (String columnName : columnNames) {
			tableModel.addColumn(columnName);
		}
		tableModel.setRowCount(0);
		for (Ticket ticket : manager.ticketList) {
			tableModel.addRow(new Object[] { ticket.getFlightCode(), ticket.getFlightDate(), ticket.getFlightTime(),
					ticket.getDepartureAirport(), ticket.getArrivalAirport() });
		}
		table = new JTable(tableModel);
		table.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					try {
						// Set date
						Date date = new SimpleDateFormat("dd/MM/yyyy")
								.parse(tableModel.getValueAt(selectedRow, 1).toString());
						dateChooser.setDate(date);

						// Set time
						Date time = new SimpleDateFormat("HH:mm")
								.parse(tableModel.getValueAt(selectedRow, 2).toString());
						timeSpinner.setValue(time);

						// Set airports
						String sanBayDi = tableModel.getValueAt(selectedRow, 3).toString();
						String sanBayDen = tableModel.getValueAt(selectedRow, 4).toString();

						cboSanBayDi.setSelectedItem(sanBayDi);
						cboSanBayDen.setSelectedItem(sanBayDen);
					} catch (ParseException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		table.setRowHeight(28);
		table.setShowGrid(true);
		table.setGridColor(new Color(230, 230, 230));
		table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
		table.getTableHeader().setBackground(new Color(240, 240, 240));
		table.setSelectionBackground(new Color(232, 240, 254));

		// Adjust column widths
		TableColumnModel columnModel = table.getColumnModel();
		int[] columnWidths = { 100, 100, 100, 150, 150 };
		for (int i = 0; i < columnWidths.length; i++) {
			columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
		}

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(0, 8, 0, 0),
				BorderFactory.createTitledBorder(new LineBorder(new Color(100, 100, 100), 1, true),
						"Danh sách chuyến bay", TitledBorder.LEFT, TitledBorder.TOP,
						new Font("Segoe UI", Font.BOLD, 16), new Color(50, 50, 50))));

		// Button panel with modern styling
		JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 20));
		buttonPanel.setOpaque(false);

		Dimension buttonSize = new Dimension(80, 32);
		btnAdd = createStyledButton("Thêm", new Color(0, 120, 212));
		btnUpdate = createStyledButton("Cập nhật", new Color(0, 153, 76));
		btnDelete = createStyledButton("Xóa", new Color(212, 0, 0));
		btnClear = createStyledButton("Tạo mới", new Color(102, 102, 102));

		btnAdd.addActionListener(admin);
		btnUpdate.addActionListener(admin);
		btnDelete.addActionListener(admin);
		btnClear.addActionListener(admin);

		btnAdd.setPreferredSize(buttonSize);
		btnUpdate.setPreferredSize(buttonSize);
		btnDelete.setPreferredSize(buttonSize);
		btnClear.setPreferredSize(buttonSize);

		buttonPanel.add(btnAdd);
		buttonPanel.add(btnUpdate);
		buttonPanel.add(btnDelete);
		buttonPanel.add(btnClear);

		gbc.gridx = 0;
		gbc.gridy = row;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(20, 10, 8, 10);
		inputPanel.add(buttonPanel, gbc);

		panelM.add(inputPanel, BorderLayout.WEST);
		panelM.add(scrollPane, BorderLayout.CENTER);

		// Update button actions to use combo boxes instead of text fields

		return panelM;
	}

	public boolean isCellEditable(int row, int column) {
		return false; // Makes all cells non-editable
	}

	public JPanel mCustomer() {

		// Tạo panel chính
		JPanel panel = new JPanel(new BorderLayout());

		// Tiêu đề
		JLabel title = new JLabel("Quản lý tài khoản người dùng", JLabel.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 20));
		panel.add(title, BorderLayout.NORTH);

		// Bảng và mô hình dữ liệu
		String[] columnNames = { "Số CCCD", "Họ và Tên", "SĐT", "Email", "Giới tính", "Tên Đăng Nhập", "Mật Khẩu" };
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		try {
			// Đọc dữ liệu từ file
			FileReader file = new FileReader("src/data/InfomationUser.txt");
			BufferedReader reader = new BufferedReader(file);
			String str;
			while ((str = reader.readLine()) != null) {
				StringTokenizer token = new StringTokenizer(str, "\t"); // Giả sử file được phân tách bằng tab

				// Kiểm tra số lượng token khớp với các cột
				if (token.countTokens() >= 7) {
					String username = token.nextToken();
					String password = token.nextToken();
					String id = token.nextToken();
					String name = token.nextToken();
					String phone = token.nextToken();
					String email = token.nextToken();
					String gender = token.nextToken();

					// Thêm dòng dữ liệu vào mô hình bảng
					Object[] rowData = { id, name, phone, email, gender, username, password };
					tableModel.addRow(rowData);
				}
			}
			reader.close(); // Đóng file sau khi đọc
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Lỗi khi đọc file: " + e.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
		}

		// Tạo bảng JTable với DefaultTableModel
		JTable table = new JTable(tableModel);

		// Tạo JTable với model
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		table.getTableHeader().setBackground(new Color(0, 51, 153));
		table.getTableHeader().setForeground(Color.WHITE);
		table.setRowHeight(25);
		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane, BorderLayout.CENTER);

		// Panel chứa ô tìm kiếm và các nút chức năng
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

		// Ô tìm kiếm
		JTextField searchField = new JTextField(20);
		JButton btnSearch = new JButton("Tìm Kiếm");
		btnSearch.setBackground(new Color(0, 123, 255));
		btnSearch.setForeground(Color.WHITE);
		btnSearch.setFont(new Font("Arial", Font.BOLD, 14));
		btnSearch.setPreferredSize(new Dimension(150, 35));

		// Chức năng tìm kiếm động
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String searchTerm = searchField.getText().trim();
				if (searchTerm.isEmpty()) {
					JOptionPane.showMessageDialog(panel, "Vui lòng nhập thông tin để tìm kiếm!", "Thông báo",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				try {
					manager.loadTicketCheck();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// Lọc dữ liệu theo từ khóa
				tableModel.setRowCount(0); // Xóa tất cả các hàng hiện tại
				boolean found = false;

				for (saveInfomationUser row : manager.getSaveInfo()) {
					String name = row.getName();
					String phone = row.getPhone();

					// Kiểm tra nếu tên hoặc số điện thoại chứa từ khóa tìm kiếm
					if (name.toLowerCase().contains(searchTerm.toLowerCase()) || phone.contains(searchTerm)) {
						tableModel.addRow(new Object[] { row.getId(), // ID người dùng
								row.getName(), // Tên người dùng
								row.getPhone(), // Số điện thoại
								row.getEmail(), // Email (nếu có)
								row.getGender(), // Giới tính (nếu có)
								row.getUserName() // Tên tài khoản
						});
						found = true;
					}
				}

				if (!found) {
					JOptionPane.showMessageDialog(panel, "Không tìm thấy tài khoản phù hợp!", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		JButton btnResetPassword = new JButton("Đặt Lại Mật Khẩu");
		btnResetPassword.setBackground(new Color(34, 177, 76));
		btnResetPassword.setForeground(Color.WHITE);
		btnResetPassword.setFont(new Font("Arial", Font.BOLD, 14));
		btnResetPassword.setPreferredSize(new Dimension(150, 35));
		btnResetPassword.addActionListener(e -> {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1) {
				String newPassword = JOptionPane.showInputDialog(panel, "Nhập mật khẩu mới:");
				if (newPassword != null && !newPassword.trim().isEmpty()) {

					try {
						usermanager.LoadUser();
						manager.loadTicketCheck();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					tableModel.setValueAt(newPassword, selectedRow, 6);

					manager.changePass((String) tableModel.getValueAt(selectedRow, 5), newPassword);
					usermanager.removePass((String) tableModel.getValueAt(selectedRow, 5), newPassword);
					System.out.println((String) tableModel.getValueAt(selectedRow, 5));

					try {
						usermanager.WriteUser();
						manager.writeInfomationUser();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					// Đảm bảo bảng được làm mới
					tableModel.fireTableDataChanged(); // Đảm bảo bảng được làm mới
					JOptionPane.showMessageDialog(panel, "Mật khẩu đã được đặt lại.");
				} else {
					JOptionPane.showMessageDialog(panel, "Mật khẩu không hợp lệ.");
				}
			} else {
				JOptionPane.showMessageDialog(panel, "Vui lòng chọn tài khoản cần đặt lại mật khẩu.");
			}
		});

		// Thêm các thành phần vào bottom panel
		bottomPanel.add(new JLabel("Tìm kiếm (Tên/SĐT):"));
		bottomPanel.add(searchField);
		bottomPanel.add(btnSearch);
		bottomPanel.add(btnResetPassword);

		panel.add(bottomPanel, BorderLayout.SOUTH);
		return panel;
	}

	public void updateTab(String title, JPanel newView) {
		int tabIndex = tabbedPane.indexOfTab(title);
		if (tabIndex == -1) {
			tabbedPane.removeAll();
			tabbedPane.addTab(title, newView);
			tabbedPane.setSelectedIndex(0);
		} else {
			tabbedPane.setComponentAt(tabIndex, newView);
			tabbedPane.setSelectedIndex(tabIndex);
		}

	}

	public void resetButtonColors() {
		for (JButton button : new JButton[] { button1, button2, button3, button4 }) {
			if (button != selectedButton) {
				button.setBackground(null);
				button.setForeground(Color.BLACK);
			}
		}
	}

	public static JButton createButton(String text, String iconPath, Dimension size, boolean isDefault) {
		JButton button = new JButton(text);

		ImageIcon icon = new ImageIcon(adminSetting.class.getResource(iconPath));
		Image img = icon.getImage();
		Image resizedImage = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		button.setIcon(new ImageIcon(resizedImage));

		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setHorizontalTextPosition(SwingConstants.CENTER);

		button.setPreferredSize(size);
		button.setMinimumSize(size);
		button.setMaximumSize(size);
		button.setFocusPainted(false);
		if (isDefault) {
			button.setBackground(Color.lightGray);
			button.setForeground(Color.WHITE);
		} else {
			button.setBackground(null);
		}
		return button;

	}

	private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, JLabel label, JComponent field) {
		gbc.gridx = 0;
		gbc.gridy = row;
		gbc.gridwidth = 1;
		gbc.weightx = 0.3;
		panel.add(label, gbc);

		gbc.gridx = 1;
		gbc.weightx = 0.7;
		panel.add(field, gbc);
	}

	private JLabel createStyledLabel(String text, Font font) {
		JLabel label = new JLabel(text);
		label.setFont(font);
		label.setForeground(new Color(50, 50, 50));
		return label;
	}

	private JButton createStyledButton(String text, Color color) {
		JButton button = new JButton(text) {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				if (getModel().isPressed()) {
					g2.setColor(color.darker());
				} else if (getModel().isRollover()) {
					g2.setColor(color.brighter());
				} else {
					g2.setColor(color);
				}
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
				g2.dispose();
				super.paintComponent(g);
			}
		};
		button.setFont(new Font("Segoe UI", Font.BOLD, 14));
		button.setForeground(Color.WHITE);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return button;
	}

	public boolean validateInput(JDateChooser dateChooser, JSpinner timeSpinner, JComboBox<String> cboSanBayDi,
			JComboBox<String> cboSanBayDen, JPanel panel) {
		if (dateChooser.getDate() == null || timeSpinner.getValue() == null || cboSanBayDi.getSelectedItem() == null
				|| cboSanBayDen.getSelectedItem() == null || cboSanBayDi.getSelectedItem().toString().trim().isEmpty()
				|| cboSanBayDen.getSelectedItem().toString().trim().isEmpty()) {
			showErrorMessage(panel, "Vui lòng điền đầy đủ thông tin!");
			return false;
		}

		// Check if departure and arrival airports are the same
		String sanBayDi = cboSanBayDi.getSelectedItem().toString();
		String sanBayDen = cboSanBayDen.getSelectedItem().toString();
		if (sanBayDi.equals(sanBayDen)) {
			showErrorMessage(panel, "Sân bay đi và đến không được trùng nhau!");
			return false;
		}
		if (sanBayDi.equals("Chọn sân bay") || sanBayDen.equals("Chọn sân bay")) {
			showErrorMessage(panel, "Vui lòng chọn sân bay ");
			return false;
		}

		return true;
	}

	public void showSuccessMessage(JPanel panel, String message) {
		JOptionPane.showMessageDialog(panel, message, "Thành công", JOptionPane.INFORMATION_MESSAGE);
	}

	public void showErrorMessage(JPanel panel, String message) {
		JOptionPane.showMessageDialog(panel, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
	}

	public void clearFields(JDateChooser dateChooser, JSpinner timeSpinner, JComboBox<String> cboSanBayDi,
			JComboBox<String> cboSanBayDen) {
		dateChooser.setCalendar(null);
		timeSpinner.setValue(new Date());
		cboSanBayDi.setSelectedIndex(0);
		cboSanBayDen.setSelectedIndex(0);
	}

	public void updateTableRow(DefaultTableModel tableModel, int selectedRow, JDateChooser dateChooser,
			JSpinner timeSpinner, JComboBox<String> cboSanBayDi, JComboBox<String> cboSanBayDen) {
		tableModel.setValueAt(new SimpleDateFormat("dd/MM/yyyy").format(dateChooser.getDate()), selectedRow, 1);
		tableModel.setValueAt(new SimpleDateFormat("HH:mm").format(timeSpinner.getValue()), selectedRow, 2);
		tableModel.setValueAt(cboSanBayDi.getSelectedItem(), selectedRow, 3);
		tableModel.setValueAt(cboSanBayDen.getSelectedItem(), selectedRow, 4);
	}

	private void styleComponent(JComponent component) {
		component.setBackground(Color.WHITE);

	}

	public String generateFlightCode(Set<String> generatedCodes) {
		Random random = new Random();
		String code;
		do {
			code = "VN-" + (1000 + random.nextInt(9999));
		} while (!generatedCodes.add(code));
		return code;
	}

	public static void main(String[] args) {
		new adminSetting();
	}
}