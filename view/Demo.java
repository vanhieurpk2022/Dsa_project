package view;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import controller.demoController;
import model.SavePass;
import model.Ticket;
import model.TicketCheck;
import model.TicketManager;

public class Demo extends JFrame {
	private JTabbedPane tabbedPane;
	private JMenuBar menuBar;
	private JMenu managerMenu, helpMenu;
	private ImageIcon icon, iconhelp, iconReload, iconsearch1, iconlabel2, iconlabel1, iconlabel3;
	private JToolBar toolBar;
	private JButton button1, button2, button3, button4, button5, buttonContinue, seach1, search2;
	private JLabel label;
	private Timer timer;
	private int colorIndex = 0;
	private TicketManager manager = new TicketManager();
	private JPanel panelTabel;
	private JTable table;
	private JComboBox comboBox1, comboBox2, genderComboBox;
	private JDateChooser dateChooser;
	private JButton refreshButton;
	private JButton selectedButton = null;
	int stt;
	private DefaultTableModel model;
	private Color[] colors = { Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE,
			Color.MAGENTA };
	private JTextField cccdField, customerNameField, phoneField, emailField, usernameField;
	private JPanel buttonPanel;
	private JButton repairButton, saveButton, changePass;
	private JPasswordField passwordfield;
	private JButton startButton;
	private demoController handle;
	private LoginForm login;
	private SavePass savepass = new SavePass(LoginForm.class);

	public Demo() {
		setTitle("Hệ Thống đặt vé máy bay");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1300, 750);
		setResizable(false);
		setLocationRelativeTo(null);
		menuBar = new JMenuBar();

		handle = new demoController(this);
		managerMenu = new JMenu("Manager");
		icon = new ImageIcon(Demo.class.getResource("/img/Settings-icon.png"));
		managerMenu.setIcon(new ImageIcon(icon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH)));

		helpMenu = new JMenu("Help");
		iconhelp = new ImageIcon(Demo.class.getResource("/img/Question-icon.png"));
		helpMenu.setIcon(new ImageIcon(iconhelp.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));

		menuBar.add(managerMenu);
		menuBar.add(helpMenu);
		this.setJMenuBar(menuBar);

		// Tạo JToolBar chứa các JButton
		toolBar = new JToolBar();
		Dimension buttonSize = new Dimension(90, 60);
		button1 = createButton("Trang chủ", "/img/home-icon.png", buttonSize, true);

		button1.addActionListener(handle);
		button2 = createButton("Đặt vé", "/img/tickets-icon.png", buttonSize, false);
		button2.addActionListener(handle);
		button3 = createButton("Vé của tôi", "/img/Mytickets-icon.png", buttonSize, false);
		button3.addActionListener(handle);
		button4 = createButton("Thông tin", "/img/Profile-icon.png", buttonSize, false);
		button4.addActionListener(handle);
		button5 = createButton("Đăng xuất", "/img/exit-icon.png", buttonSize, false);
		button5.addActionListener(handle);

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

//	public class XuLyButton implements ActionListener {
//
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			// TODO Auto-generated method stub
//
//			// Đổi màu cho nút được nhấn
//			JButton clickedButton = (JButton) e.getSource();
//			if (clickedButton == button1 || clickedButton == button2 || clickedButton == button3
//					|| clickedButton == button4 || clickedButton == startButton) {
//				resetButtonColors();
//				clickedButton.setBackground(Color.lightGray);
//				clickedButton.setForeground(Color.WHITE);
//
//			}
//
//			// Gọi phương thức cập nhật tab
//			if (clickedButton == button1) {
//				updateTab("Trang chủ", home());
//			} else if (clickedButton == button2 || clickedButton == startButton) {
//				updateTab("Đặt vé", tickets());
//			} else if (clickedButton == button3) {
//				updateTab("Vé của tôi", myTickets());
//			} else if (clickedButton == button4) {
//				updateTab("Thông tin", proFile());
//			} else if (clickedButton == button5) {
//				int confirmResult = JOptionPane.showConfirmDialog(Demo.this, "Bạn có có muốn đăng xuất?", "Xác nhận",
//						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//
//				if (confirmResult == JOptionPane.YES_OPTION) {
//					new LoginForm();
//					setVisible(false);
//				}
//
//			} else if (clickedButton == buttonContinue) {
//				int selectedRow = table.getSelectedRow();
//
//				if (selectedRow != -1) {
//					String flightCode = (String) table.getValueAt(selectedRow, 0);
//					String flightDate = (String) table.getValueAt(selectedRow, 1);
//					String flightTime = (String) table.getValueAt(selectedRow, 2);
//					String departureAirport = (String) table.getValueAt(selectedRow, 3);
//					String arrivalAirport = (String) table.getValueAt(selectedRow, 4);
//					FlightConfirmationForm confirmationForm = new FlightConfirmationForm();
//					confirmationForm.setFlightDetails(flightCode, flightDate, flightTime, departureAirport,
//							arrivalAirport);
//
//				} else {
//					JOptionPane.showMessageDialog(null, "Vui lòng chọn một chuyến bay!");
//				}
//			} else if (clickedButton == seach1) {
//				String departure = (String) comboBox1.getSelectedItem();
//				String arrival = (String) comboBox2.getSelectedItem();
//				filterTickets(departure, arrival);
//			} else if (clickedButton == search2) {
//				String departure = (String) comboBox1.getSelectedItem();
//				String arrival = (String) comboBox2.getSelectedItem();
//				Date selectedDate = dateChooser.getDate();
//
//				if (selectedDate == null) {
//					JOptionPane.showMessageDialog(null, "Vui lòng chọn một ngày để tìm kiếm!");
//					return;
//				}
//
//				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//				String selectedDateString = dateFormat.format(selectedDate);
//				System.out.println(selectedDateString);
//
//				DefaultTableModel model = (DefaultTableModel) table.getModel();
//				model.setRowCount(0);
//
//				boolean hasResults = false;
//
//				boolean isAllDeparture = departure.equals("All");
//				boolean isAllArrival = arrival.equals("All");
//
//				for (Ticket ticket : manager.ticketList) {
//					String flightDate = ticket.getFlightDate();
//					String departureAirport = ticket.getDepartureAirport();
//					String arrivalAirport = ticket.getArrivalAirport();
//
//					// Kiểm tra điều kiện tìm kiếm
//					boolean dateMatch = selectedDateString.equals(flightDate);
//					boolean departureMatch = isAllDeparture || departure.equals(departureAirport);
//					boolean arrivalMatch = isAllArrival || arrival.equals(arrivalAirport);
//
//					// Trường hợp 1: Chỉ tìm theo ngày
//					boolean onlyDateSearch = !isAllDeparture && !isAllArrival && departure.isEmpty()
//							&& arrival.isEmpty();
//
//					// Trường hợp 2: Tìm theo ngày và địa điểm
//					boolean dateAndLocationSearch = !departure.isEmpty() || !arrival.isEmpty();
//
//					if (onlyDateSearch && dateMatch) {
//						addTicketToTable(model, ticket);
//						hasResults = true;
//					} else if (dateAndLocationSearch) {
//						if (dateMatch && departureMatch && arrivalMatch) {
//							addTicketToTable(model, ticket);
//							hasResults = true;
//						}
//					}
//				}
//
//				if (!hasResults) {
//					JOptionPane.showMessageDialog(null, "Không có chuyến bay nào phù hợp với tiêu chí tìm kiếm.");
//				}
//			} else if (clickedButton == refreshButton) {
//				comboBox1.setSelectedIndex(0);
//				comboBox2.setSelectedIndex(0);
//
//				dateChooser.setDate(null);
//
//				DefaultTableModel model = (DefaultTableModel) table.getModel();
//				model.setRowCount(0);
//
//				for (Ticket ticket : manager.ticketList) {
//					model.addRow(new Object[] { ticket.getFlightCode(), ticket.getFlightDate(), ticket.getFlightTime(),
//							ticket.getDepartureAirport(), ticket.getArrivalAirport() });
//				}
//			} else if (clickedButton == repairButton) {
//
//				cccdField.setEditable(true);
//				customerNameField.setEditable(true);
//				phoneField.setEditable(true);
//				emailField.setEditable(true);
//				usernameField.setEditable(true);
//				passwordfield.setEditable(true);
//				genderComboBox.setEnabled(true);
//			} else if (clickedButton == saveButton) {
//				int confirmResult = JOptionPane.showConfirmDialog(Demo.this, "Bạn có có muốn lưu lại các thông tin?",
//						"Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//
//				if (confirmResult == JOptionPane.YES_OPTION) {
//
//					cccdField.setEditable(false);
//					customerNameField.setEditable(false);
//					phoneField.setEditable(false);
//					emailField.setEditable(false);
//					usernameField.setEditable(false);
//					passwordfield.setEditable(false);
//					genderComboBox.setEnabled(false);
//				}
//			}
//
//		}
//
//	}

	public void resetButtonColors() {
		for (JButton button : new JButton[] { button1, button2, button3, button4 }) {
			if (button != selectedButton) {
				button.setBackground(null);
				button.setForeground(Color.BLACK);
			}
		}
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

	public JPanel home() {
		// Tạo panel chính với gradient
		JPanel mainPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				int w = getWidth();
				int h = getHeight();
				GradientPaint gp = new GradientPaint(0, 0, new Color(0, 153, 204), w, h, new Color(0, 204, 204));
				g2d.setPaint(gp);
				g2d.fillRect(0, 0, w, h);
			}
		};
		mainPanel.setLayout(new BorderLayout());

		// Panel trung tâm với hiệu ứng trong suốt
		JPanel centerPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setColor(new Color(240, 255, 255));
				g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
			}
		};
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.setOpaque(false);

		// Logo với animation
		JLabel logoLabel = new JLabel(loadIcon("/img/airplane.png", 120, 120), SwingConstants.CENTER) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private int range = 1000;
			private int startX = 20;
			private int endX = startX + range;
			private int currentX = startX;
			private int step = 5;

			{
				setLocation(currentX, getY());
				Timer timer = new Timer(20, e -> {
					currentX += step;
					if (currentX > endX) {
						currentX = startX;
					}
					setLocation(currentX, getY());
				});
				timer.start();
			}
		};

		logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 100));
		logoLabel.setForeground(new Color(25, 118, 210));
		logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Tiêu đề
		JLabel welcomeLabel = new JLabel("CHÀO MỪNG ĐẾN VỚI");
		welcomeLabel.setFont(new Font("Arial", Font.BOLD, 36));
		welcomeLabel.setForeground(new Color(51, 51, 51));
		welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Tên hệ thống với hiệu ứng đổ bóng
		JLabel systemLabel = new JLabel("HỆ THỐNG ĐẶT VÉ MÁY BAY") {
			private float alpha = 1.0f;
			private boolean increasing = false;
			private int colorIndex = 0;

			// Mảng màu sắc đẹp
			private Color[] colors = { new Color(25, 118, 210), new Color(220, 20, 60), new Color(255, 165, 0),
					new Color(106, 90, 205), new Color(50, 205, 50), new Color(255, 20, 147), new Color(255, 215, 0) };

			{
				// Timer cho hiệu ứng nhấp nháy và đổi màu
				Timer timer = new Timer(100, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// Xử lý alpha cho hiệu ứng fade
						if (increasing) {
							alpha += 0.1f;
							if (alpha >= 1.0f) {
								alpha = 1.0f;
								increasing = false;
								// Đổi màu khi đạt độ sáng tối đa
								colorIndex = (colorIndex + 1) % colors.length;
							}
						} else {
							alpha -= 0.1f;
							if (alpha <= 0.4f) {
								alpha = 0.4f;
								increasing = true;
							}
						}
						repaint();
					}
				});
				timer.start();
			}

			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				g2d.setFont(new Font("Arial", Font.BOLD, 42));

				// Lấy màu hiện tại và màu tiếp theo để tạo gradient
				Color currentColor = colors[colorIndex];
				Color nextColor = colors[(colorIndex + 1) % colors.length];

				// Vẽ đổ bóng
				g2d.setColor(new Color(0, 0, 0, (int) (50 * alpha)));
				g2d.drawString(getText(), 2, getHeight() - 2);

				// Tạo gradient paint cho text
				GradientPaint gradient = new GradientPaint(0, 0,
						new Color(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(),
								(int) (255 * alpha)),
						getWidth(), 0,
						new Color(nextColor.getRed(), nextColor.getGreen(), nextColor.getBlue(), (int) (255 * alpha)));
				g2d.setPaint(gradient);

				FontMetrics fm = g2d.getFontMetrics();
				g2d.drawString(getText(), 0, getHeight() - 4);

				if (alpha > 0.7f) {
					g2d.setColor(new Color(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(),
							(int) (100 * alpha)));
					g2d.setStroke(new BasicStroke(3));
					g2d.drawString(getText(), 0, getHeight() - 4);

					// Thêm hiệu ứng tỏa sáng
					AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha * 0.3f);
					g2d.setComposite(ac);
					g2d.setColor(currentColor);
					float glowSize = 4.0f;
					g2d.setStroke(new BasicStroke(glowSize));
					g2d.drawString(getText(), 0, getHeight() - 4);
				}
			}
		};
		systemLabel.setFont(new Font("Arial", Font.BOLD, 42));
		systemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		// Đảm bảo label đủ lớn để chứa text và hiệu ứng
		systemLabel.setPreferredSize(new Dimension(600, 60));

		// Slogan
		JLabel sloganLabel = new JLabel("Hành trình của bạn - Sự lựa chọn của chúng tôi");
		sloganLabel.setFont(new Font("Arial", Font.ITALIC, 28));
		sloganLabel.setForeground(new Color(70, 70, 70));
		sloganLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		startButton = new JButton("BẮT ĐẦU ĐẶT VÉ") {
			{
				setContentAreaFilled(false);
				setFocusPainted(false);
				setBorderPainted(false);
			}

			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				if (getModel().isPressed()) {
					g2d.setColor(new Color(0, 91, 150));
				} else if (getModel().isRollover()) {
					g2d.setColor(new Color(72, 209, 204));
				} else {
					g2d.setColor(new Color(72, 209, 204));
				}

				g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

				g2d.setColor(Color.WHITE);
				g2d.setFont(new Font("Arial", Font.BOLD, 16));
				FontMetrics fm = g2d.getFontMetrics();
				int x = (getWidth() - fm.stringWidth(getText())) / 2;
				int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
				g2d.drawString(getText(), x, y);
			}
		};
		startButton.addActionListener(handle);
		startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		startButton.setMaximumSize(new Dimension(250, 50));
		startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// Thêm các thành phần vào panel trung tâm với padding
		centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
		centerPanel.add(logoLabel);
		centerPanel.add(Box.createVerticalStrut(30));
		centerPanel.add(welcomeLabel);
		centerPanel.add(Box.createVerticalStrut(15));
		centerPanel.add(systemLabel);
		centerPanel.add(Box.createVerticalStrut(20));
		centerPanel.add(sloganLabel);
		centerPanel.add(Box.createVerticalStrut(40));
		centerPanel.add(startButton);

		// Thêm panel trung tâm vào panel chính với margin
		mainPanel.add(Box.createHorizontalStrut(100), BorderLayout.WEST);
		mainPanel.add(Box.createHorizontalStrut(100), BorderLayout.EAST);
		mainPanel.add(Box.createVerticalStrut(50), BorderLayout.NORTH);
		mainPanel.add(Box.createVerticalStrut(50), BorderLayout.SOUTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);

		return mainPanel;
	}

	public JPanel tickets() {
		// Main panel setup with modern colors
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout(15, 25));
		mainPanel.setBackground(new Color(245, 247, 250));

		// Control panel with gradient background
		JPanel controlPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				int w = getWidth();
				int h = getHeight();
				GradientPaint gp = new GradientPaint(0, 0, new Color(0, 153, 204), w, h, new Color(0, 204, 204));
				g2d.setPaint(gp);
				g2d.fillRect(0, 0, w, h);
			}
		};
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
		controlPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// Label panel with improved typography
		JPanel labelPanel = new JPanel(new GridLayout(1, 3, 20, 10));
		labelPanel.setOpaque(false);

		// Custom font for labels
		Font labelFont = new Font("Segoe UI", Font.BOLD, 22);
		Color labelColor = Color.WHITE;

		// Departure Label
		JLabel label1 = createStyledLabel("Điểm khởi hành", labelFont, labelColor, "/img/location-pin.png");
		label1.setBorder(BorderFactory.createEmptyBorder(10, 120, 0, 0));

		// Arrival Label
		JLabel label2 = createStyledLabel("Điểm đến", labelFont, labelColor, "/img/destination.png");
		label2.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 230));

		// Date Label
		JLabel label3 = createStyledLabel("Ngày Bay", labelFont, labelColor, "/img/timetable.png");
		label3.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 200));

		labelPanel.add(label1);
		labelPanel.add(label2);
		labelPanel.add(label3);

		// Button panel with modern styling
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 15));
		buttonPanel.setOpaque(false);

		// Refresh button
		refreshButton = createStyledButton("", "/img/reload-icon.png", new Dimension(45, 45));
		refreshButton.addActionListener(handle);

		// Styled ComboBoxes
		comboBox1 = createStyledComboBox(new String[] { "All", "Nha Trang", "Đà Nẵng", "Hà Nội", "TP.HCM" });
		comboBox2 = createStyledComboBox(new String[] { "All", "Nha Trang", "Đà Nẵng", "Hà Nội", "TP.HCM" });

		// Search buttons
		seach1 = createStyledButton("Tìm kiếm", "/img/search-icon.png", new Dimension(140, 40));
		seach1.addActionListener(handle);

		// Date Chooser
		dateChooser = new JDateChooser();
		dateChooser.setPreferredSize(new Dimension(180, 35));
		styleComponent(dateChooser);

		search2 = createStyledButton("Tìm kiếm", "/img/search-icon.png", new Dimension(140, 40));
		search2.addActionListener(handle);

		// Add components to button panel
		buttonPanel.add(refreshButton);
		buttonPanel.add(comboBox1);
		buttonPanel.add(comboBox2);
		buttonPanel.add(seach1);
		buttonPanel.add(dateChooser);
		buttonPanel.add(search2);

		// Assemble control panel
		controlPanel.add(labelPanel);
		controlPanel.add(Box.createVerticalStrut(15));
		controlPanel.add(buttonPanel);

		// Table panel with modern styling
		panelTabel = new JPanel();
		panelTabel.setBackground(new Color(245, 247, 250));
		panelTabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		String[] columnNames = { "Mã chuyến bay", "Ngày cất cánh", "Thời gian bay", "Sân bay đi", "Sân bay đến" };
		DefaultTableModel tableModel = new DefaultTableModel() {
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
//		for (Ticket ticket : manager.ticketList) {
//			tableModel.addRow(new Object[] { ticket.getFlightCode(), ticket.getFlightDate(), ticket.getFlightTime(),
//					ticket.getDepartureAirport(), ticket.getArrivalAirport() });
//		}

		// Style table
		table = new JTable(tableModel);
		table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		table.setRowHeight(35);
		table.setShowGrid(true);
		table.setGridColor(new Color(230, 230, 230));
		table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
		table.getTableHeader().setBackground(new Color(41, 128, 185));
		table.getTableHeader().setForeground(Color.WHITE);
		table.setSelectionBackground(new Color(186, 214, 237));
		table.setSelectionForeground(Color.BLACK);

		// Table scroll pane
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(1250, 300));
		scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
		panelTabel.add(scrollPane);

		// Footer panel
		JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		footerPanel.setBackground(new Color(245, 247, 250));
		footerPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

		buttonContinue = createStyledButton("Đặt vé", null, new Dimension(150, 45));
		buttonContinue.setFont(new Font("Segoe UI", Font.BOLD, 16));
		buttonContinue.addActionListener(handle);
		footerPanel.add(buttonContinue);

		// Add all panels to main panel
		mainPanel.add(controlPanel, BorderLayout.NORTH);
		mainPanel.add(panelTabel, BorderLayout.CENTER);
		mainPanel.add(footerPanel, BorderLayout.SOUTH);

		return mainPanel;
	}

	public JPanel myTickets() {

		JPanel mainMY = new JPanel();
		mainMY.setLayout(new BorderLayout());
		mainMY.setBackground(Color.LIGHT_GRAY);

		JPanel headerPanel = new JPanel();

		headerPanel.setPreferredSize(new Dimension(getWidth(), 80));
		JLabel headerLabel = new JLabel("HÓA ĐƠN", SwingConstants.CENTER);
		headerLabel.setFont(new Font("Arial", Font.BOLD, 34));
		headerLabel.setForeground(Color.WHITE);
		headerPanel.add(headerLabel);

		JPanel paddedTitlePanel = new JPanel(new BorderLayout()) {

			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				int w = getWidth();
				int h = getHeight();
				GradientPaint gp = new GradientPaint(0, 0, new Color(0, 153, 204), w, h, new Color(0, 204, 204));
				g2d.setPaint(gp);
				g2d.fillRect(0, 0, w, h);
			}
		};

		paddedTitlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		paddedTitlePanel.add(headerLabel, BorderLayout.CENTER);

		// Center Panel với Table
		JPanel panelCenter = new JPanel();
		model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Không cho phép edit cells
			}
		};

		// Tạo table với model
		table = new JTable(model);
		table.setRowHeight(25);
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		table.getTableHeader().setBackground(new Color(240, 240, 240));
		table.setSelectionBackground(new Color(232, 237, 242));

		// Thêm các cột
		String[] columnNames = { "STT", "Mã chuyến bay", "Họ và tên", "CCDD", "SĐT", "Ngày Bay", "Thời gian bay",
				"Sân bay đi", "Sân bay đến", "Số ghế", "Thuế", "giá vé", "Tổng tiền" };
		for (String columnName : columnNames) {
			model.addColumn(columnName);
		}
		List<TicketCheck> allTickets = TicketManager.getInstance().getAllTickets();
		model.setRowCount(0);
		int stt = 1;
//		for (TicketCheck ticket : allTickets) {
//			Object[] rowData = { stt++, ticket.generateFlightCode(), ticket.getPassengerName(), ticket.getId(),
//					ticket.getPhone(), ticket.getDate(), ticket.getTime(), ticket.getDeparture(), ticket.getArrival(),
//					ticket.getSelectedSeats(), ticket.getTax(), ticket.getPrice(), ticket.getTotal() };
//			model.addRow(rowData);
//		}

		// Thiết lập độ rộng cho các cột
		table.getColumnModel().getColumn(0).setPreferredWidth(40); // STT
		table.getColumnModel().getColumn(1).setPreferredWidth(100); // Mã chuyến bay
		table.getColumnModel().getColumn(2).setPreferredWidth(150); // Họ và tên
		table.getColumnModel().getColumn(3).setPreferredWidth(100); // CCCD
		table.getColumnModel().getColumn(4).setPreferredWidth(100); // SĐT
		table.getColumnModel().getColumn(5).setPreferredWidth(80); // Ngày bay
		table.getColumnModel().getColumn(6).setPreferredWidth(100); // Thời gian bay
		table.getColumnModel().getColumn(7).setPreferredWidth(80); // Sân bay đi
		table.getColumnModel().getColumn(8).setPreferredWidth(80); // Sân bay đến
		table.getColumnModel().getColumn(9).setPreferredWidth(50); // Số ghế
		table.getColumnModel().getColumn(10).setPreferredWidth(80); // Thuế
		table.getColumnModel().getColumn(10).setPreferredWidth(100); // giá vé
		table.getColumnModel().getColumn(10).setPreferredWidth(100); // tổng tiền

		// Tạo scroll pane với custom border
		JScrollPane sc = new JScrollPane(table);
		sc.setPreferredSize(new Dimension(1250, 400));
		sc.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20),
				BorderFactory.createLineBorder(new Color(200, 200, 200))));

		JPanel functionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		functionPanel.setBackground(Color.WHITE);

		JButton exportButton = new JButton("Xuất PDF");
		exportButton.setFocusPainted(false);

		JButton printButton = new JButton("In hóa đơn");
		printButton.setFocusPainted(false);

		functionPanel.add(exportButton);
		functionPanel.add(printButton);

		panelCenter.add(sc);
		mainMY.add(paddedTitlePanel, BorderLayout.NORTH);
		mainMY.add(panelCenter, BorderLayout.CENTER);
		mainMY.add(functionPanel, BorderLayout.SOUTH);

		return mainMY;
	}

	public JPanel proFile() {
		// Main container với BorderLayout
		JPanel mainMY = new JPanel(new BorderLayout());
		// Header Panel với gradient
		JPanel headerPanel = new JPanel();

		headerPanel.setPreferredSize(new Dimension(getWidth(), 80));
		JLabel headerLabel = new JLabel("HỒ SƠ", loadIcon("/img/Profile-icon.png", 40, 40), SwingConstants.CENTER);
		headerLabel.setFont(new Font("Arial", Font.BOLD, 34));
		headerLabel.setForeground(Color.WHITE);
		headerPanel.add(headerLabel);

		JPanel paddedTitlePanel = new JPanel(new BorderLayout()) {

			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				int w = getWidth();
				int h = getHeight();
				GradientPaint gp = new GradientPaint(0, 0, new Color(0, 153, 204), w, h, new Color(0, 204, 204));
				g2d.setPaint(gp);
				g2d.fillRect(0, 0, w, h);
			}
		};

		paddedTitlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		paddedTitlePanel.add(headerLabel, BorderLayout.CENTER);

		// Main content panel với padding
		JPanel mainPanel = new JPanel(new GridLayout(1, 2, 0, 0));
		mainPanel.setBackground(new Color(240, 240, 240));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JPanel formPanel = new JPanel(new GridBagLayout());
		formPanel.setOpaque(false);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(20, 20, 0, 50);

		// Thêm các components vào form
		JLabel labelcccd = new JLabel(" Căn cước/CMND:", loadIcon("/img/business-card.png", 30, 30), JLabel.LEFT);
		JLabel jLabelkh = new JLabel(" Tên khách hàng:", loadIcon("/img/id-card.png", 30, 30), JLabel.LEFT);
		JLabel jLabelstd = new JLabel(" Số điện thoại:", loadIcon("/img/call.png", 30, 30), JLabel.LEFT);
		JLabel jLabelemail = new JLabel(" Email:", loadIcon("/img/gmail.png", 30, 30), JLabel.LEFT);
		JLabel jLabelgt = new JLabel(" Giới Tính:", loadIcon("/img/gt.png", 30, 30), JLabel.LEFT);
		JLabel jLabelname = new JLabel(" Tên Đăng Nhập:", loadIcon("/img/account.png", 30, 30), JLabel.LEFT);
		JLabel jLabelmatkhau = new JLabel(" Mật khẩu:", loadIcon("/img/password.png", 30, 30), JLabel.LEFT);

		cccdField = createTextField("");
		addFormRow(formPanel, gbc, labelcccd, cccdField, 0);

		customerNameField = createTextField("");
		addFormRow(formPanel, gbc, jLabelkh, customerNameField, 1);

		phoneField = createTextField("");
		addFormRow(formPanel, gbc, jLabelstd, phoneField, 2);

		emailField = createTextField("");
		addFormRow(formPanel, gbc, jLabelemail, emailField, 3);

		String[] genders = { "Chọn giới tính", "Nam", "Nữ" };
		genderComboBox = new JComboBox<>(genders);
		genderComboBox.setBackground(Color.WHITE);
		addFormRow(formPanel, gbc, jLabelgt, genderComboBox, 4);
		usernameField = createTextField("");
		usernameField.setText(savepass.getUsername());
		usernameField.setEnabled(false);

		addFormRow(formPanel, gbc, jLabelname, usernameField, 5);

		passwordfield = createPasswordField(" ");
		passwordfield.setEchoChar('*');
		passwordfield.setText(savepass.getPassword());
		passwordfield.setEnabled(false);
		addFormRow(formPanel, gbc, jLabelmatkhau, passwordfield, 6);

		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
		buttonPanel.setOpaque(false);

		// Nút "Lưu"
		saveButton = new JButton("Lưu", loadIcon("/img/check.png", 24, 24));
		saveButton.addActionListener(handle);
		saveButton.setBackground(new Color(50, 205, 50));
		saveButton.setForeground(Color.WHITE);
		saveButton.setFocusPainted(false);
		getRootPane().setDefaultButton(saveButton);

		// Nút "Sửa"
		repairButton = new JButton("Sửa", loadIcon("/img/add.png", 24, 24));
		repairButton.addActionListener(handle);
		repairButton.setBackground(new Color(245, 245, 220));
		repairButton.setForeground(Color.BLACK);
		repairButton.setFocusPainted(false);

		// change pass
		changePass = new JButton(" ", loadIcon("/img/changePass.png", 24, 24));
		changePass.addActionListener(handle);
		changePass.setBackground(new Color(245, 245, 220));
		changePass.setForeground(Color.BLACK);
		changePass.setFocusPainted(false);

		// Thêm các nút vào buttonPanel
		buttonPanel.add(changePass);
		buttonPanel.add(repairButton);
		buttonPanel.add(saveButton);

		// Thêm buttonPanel vào formPanel
		addFormRow(formPanel, gbc, new JLabel(), buttonPanel, 7);

		JPanel imagePanel = new JPanel(new BorderLayout());
		imagePanel.setBackground(Color.WHITE);
		imagePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

		String absolutePath = "E:/ProjectCTDL/project/src/img/plane-profile.jpg";
		ImageIcon originalIcon = new ImageIcon(absolutePath);
		Image scaledImage = originalIcon.getImage().getScaledInstance(736, 500, Image.SCALE_SMOOTH);
		JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
		imageLabel.setHorizontalAlignment(JLabel.CENTER);
		imagePanel.add(imageLabel, BorderLayout.CENTER);

		mainPanel.add(formPanel);
		mainPanel.add(imagePanel);

		mainMY.add(paddedTitlePanel, BorderLayout.NORTH);
		mainMY.add(mainPanel, BorderLayout.CENTER);

		return mainMY;
	}

	public void addFormRow(JPanel panel, GridBagConstraints gbc, JLabel label, JComponent component, int row) {
		gbc.gridy = row;

		// Label with icon on the left
		gbc.gridx = 0;
		gbc.weightx = 0.8;
		label.setFont(new Font("Arial", Font.BOLD, 18));

		panel.add(label, gbc);

		// Component
		gbc.gridx = 1;
		gbc.weightx = 0.7;
		panel.add(component, gbc);
	}

	public JTextField createTextField(String text) {
		JTextField field = new JTextField(text);
		field.setBackground(Color.WHITE);
		field.setPreferredSize(new Dimension(200, 30));
		return field;
	}

	public void addTicketToTable(DefaultTableModel model, Ticket ticket) {
		model.addRow(new Object[] { ticket.getFlightCode(), ticket.getFlightDate(), ticket.getFlightTime(),
				ticket.getDepartureAirport(), ticket.getArrivalAirport() });
	}

	public static JButton createButton(String text, String iconPath, Dimension size, boolean isDefault) {
		JButton button = new JButton(text);

		ImageIcon icon = new ImageIcon(Demo.class.getResource(iconPath));
		Image img = icon.getImage();
		Image resizedImage = img.getScaledInstance(28, 28, Image.SCALE_SMOOTH);
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

	public void filterTickets(String departure, String arrival) {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);
//		for (Ticket ticket : manager.ticketList) {
//			boolean matches = true;
//
//			if (!departure.equals("All") && !ticket.getDepartureAirport().equals(departure)) {
//				matches = false;
//			}
//			if (!arrival.equals("All") && !ticket.getArrivalAirport().equals(arrival)) {
//				matches = false;
//			}
//			if (matches) {
//				tableModel.addRow(new Object[] { ticket.getFlightCode(), ticket.getFlightDate(), ticket.getFlightTime(),
//						ticket.getDepartureAirport(), ticket.getArrivalAirport() });
//			}
//		}
	}

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

	public void getImg(String name, int width, int height) {
	}

	public JPasswordField createPasswordField(String initialText) {
		JPasswordField field = new JPasswordField(initialText);
		field.setPreferredSize(new Dimension(200, 30));
		field.setEchoChar('*');
		return field;
	}

	// Helper methods for styling components
	public JLabel createStyledLabel(String text, Font font, Color color, String iconPath) {
		JLabel label = new JLabel(text, SwingConstants.CENTER);
		label.setFont(font);
		label.setForeground(color);
		ImageIcon icon = new ImageIcon(Demo.class.getResource(iconPath));
		icon = new ImageIcon(icon.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
		label.setIcon(icon);
		return label;
	}

	public JButton createStyledButton(String text, String iconPath, Dimension size) {
		JButton button = new JButton(text);
		button.setPreferredSize(size);
		if (iconPath != null) {
			ImageIcon icon = new ImageIcon(Demo.class.getResource(iconPath));
			icon = new ImageIcon(icon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH));
			button.setIcon(icon);
		}
		button.setFocusPainted(false);
		button.setBackground(new Color(41, 128, 185));
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Segoe UI", Font.BOLD, 14));
		button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// Add hover effect
		button.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				button.setBackground(new Color(52, 152, 219));
			}

			public void mouseExited(MouseEvent e) {
				button.setBackground(new Color(41, 128, 185));
			}
		});

		return button;
	}

	public JComboBox<String> createStyledComboBox(String[] items) {
		JComboBox<String> comboBox = new JComboBox<>(items);
		comboBox.setPreferredSize(new Dimension(200, 35));
		comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		comboBox.setBackground(Color.WHITE);
		comboBox.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
		return comboBox;
	}

	public void styleComponent(JComponent component) {
		component.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		component.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
	}

	public void setEditFalse() {
		cccdField.setEditable(false);
		phoneField.setEditable(false);
		customerNameField.setEditable(false);
		emailField.setEditable(false);
		genderComboBox.setEnabled(false);

	}

	public void setEditTrue() {
		cccdField.setEditable(true);
		phoneField.setEditable(true);
		customerNameField.setEditable(true);
		emailField.setEditable(true);
		genderComboBox.setEnabled(true);
	}

}
