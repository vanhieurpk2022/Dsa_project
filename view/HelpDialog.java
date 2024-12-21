package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

public class HelpDialog extends JDialog {
	private JTextArea helpTextArea;
	private JScrollPane scrollPane;
	private static final Color HEADER_COLOR = new Color(51, 51, 51);
	private static final Color BACKGROUND_COLOR = new Color(248, 249, 250);
	private static final Color BUTTON_COLOR = new Color(0, 123, 255);
	private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 20);
	private static final Font CONTENT_FONT = new Font("Arial", Font.PLAIN, 14);

	public HelpDialog() {
		super();
		this.setTitle("Hỗ Trợ");
		initComponents();
		setModal(true);
	}

	private void initComponents() {
		// Main panel with padding
		JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		mainPanel.setBackground(BACKGROUND_COLOR);

		// Header Panel
		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(HEADER_COLOR);
		headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

		JLabel titleLabel = new JLabel("HƯỚNG DẪN SỬ DỤNG", JLabel.CENTER);
		titleLabel.setFont(TITLE_FONT);
		titleLabel.setForeground(Color.WHITE);
		headerPanel.add(titleLabel, BorderLayout.CENTER);

		// Content Area
		helpTextArea = new JTextArea(15, 40);
		helpTextArea.setEditable(false);
		helpTextArea.setLineWrap(true);
		helpTextArea.setWrapStyleWord(true);
		helpTextArea.setFont(CONTENT_FONT);
		helpTextArea.setBackground(Color.WHITE);
		helpTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Custom ScrollPane
		scrollPane = new JScrollPane(helpTextArea);
		scrollPane.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230)));
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);

		// Button Panel
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.setBackground(BACKGROUND_COLOR);

		JButton closeButton = createStyledButton("Đóng");
		closeButton.addActionListener(e -> dispose());
		buttonPanel.add(closeButton);

		// Add components to main panel
		mainPanel.add(headerPanel, BorderLayout.NORTH);
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		// Dialog settings
		setContentPane(mainPanel);
		setDefaultHelpContent();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setMinimumSize(new Dimension(700, 500));
		setLocationRelativeTo(null);
	}

	private JButton createStyledButton(String text) {
		JButton button = new JButton(text);
		button.setFont(new Font("Arial", Font.BOLD, 14));
		button.setForeground(Color.WHITE);
		button.setBackground(BUTTON_COLOR);
		button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
		button.setFocusPainted(false);

		// Hover effect
		button.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				button.setBackground(BUTTON_COLOR.darker());
			}

			public void mouseExited(MouseEvent e) {
				button.setBackground(BUTTON_COLOR);
			}
		});

		return button;
	}

	private void setDefaultHelpContent() {
		StringBuilder help = new StringBuilder();

		// Section 1: User Account
		help.append("1. Tài khoản người dùng:\n\n");
		help.append("   • Tài khoản có thể vào hệ thống thông qua việc đăng nhập.\n");
		help.append("   • Tài khoản có thể đăng kí thông qua nút đăng ký tại nơi đăng nhập.\n");
		help.append("   • Mọi tài khoản có thể sử dụng để đặt vé.\n\n");

		// Section 2: Ticket Booking
		help.append("2. Các đặt vé:\n\n");
		help.append("   • Vé có thể đặt tại mục đặt vé.\n");
		help.append("   • Người dùng có thể đặt vé bằng cách chọn vé và nhấn xác nhận.\n");
		help.append("   • Để tìm vé thích hợp có thể dùng bộ lọc để tìm vé.\n");
		help.append("   • Sau khi xác nhận cần điền những thông tin vé cần thiết và đặt vé.\n");
		help.append("   • Sau khi người dùng đặt vé thì có thể xem lại vé mình đặt tại mục \"Vé Của Tôi.\"\n\n");

		// Section 3: Contact Support
		help.append("3. Liên hệ hỗ trợ:\n\n");
		help.append("   • Email: support@example.com\n");
		help.append("   • Điện thoại: (123) 456-7890\n");

		helpTextArea.setText(help.toString());
		helpTextArea.setCaretPosition(0);
	}

	public void setHelpContent(String content) {
		helpTextArea.setText(content);
		helpTextArea.setCaretPosition(0);
	}
}