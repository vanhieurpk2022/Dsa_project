package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FeedbackDialog extends JFrame {
	private static final Color HEADER_COLOR = new Color(51, 51, 51);
	private static final Color BACKGROUND_COLOR = new Color(248, 249, 250);
	private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 20);
	private JButton submitButton, cancelButton;
	private JTextField nameField, emailField;
	private JTextArea feedbackArea;

	public FeedbackDialog() {
		// TODO Auto-generated constructor stub
		setTitle("Phản hồi");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);

		JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		mainPanel.setBackground(BACKGROUND_COLOR);

		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(HEADER_COLOR);
		headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

		JLabel titleLabel = new JLabel("PHẢN HỒI", JLabel.CENTER);
		titleLabel.setFont(TITLE_FONT);
		titleLabel.setForeground(Color.WHITE);
		headerPanel.add(titleLabel, BorderLayout.CENTER);

		// Tạo JPanel nội dung
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridBagLayout());
		contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mainPanel.add(contentPanel, BorderLayout.CENTER);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		nameField = new JTextField(20);
		addField(contentPanel, gbc, 0, "Tên của bạn:", nameField, "Nhập tên của bạn...");

		emailField = new JTextField(20);
		addField(contentPanel, gbc, 1, "Email:", emailField, "Nhập email của bạn...");

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		JLabel feedbackLabel = new JLabel("Phản hồi của bạn:");
		contentPanel.add(feedbackLabel, gbc);

		gbc.gridx = 1;
		feedbackArea = new JTextArea(5, 20);
		feedbackArea.setLineWrap(true);
		feedbackArea.setWrapStyleWord(true);
		feedbackArea.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
						BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		JScrollPane scrollPane = new JScrollPane(feedbackArea);
		contentPanel.add(scrollPane, gbc);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

		submitButton = new JButton("Gửi");
		submitButton.setBackground(new Color(76, 175, 80));
		submitButton.setForeground(Color.WHITE);
		submitButton.setFocusPainted(false);
		submitButton.setFont(new Font("Arial", Font.BOLD, 14));
		buttonPanel.add(submitButton);

		cancelButton = new JButton("Hủy");
		cancelButton.setBackground(new Color(244, 67, 54));
		cancelButton.setForeground(Color.WHITE);
		cancelButton.setFocusPainted(false);
		cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
		buttonPanel.add(cancelButton);

		submitButton.addActionListener(new xulybutton());
		cancelButton.addActionListener(new xulybutton());

		mainPanel.add(headerPanel, BorderLayout.NORTH);
		mainPanel.add(contentPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		setContentPane(mainPanel);

		setVisible(true);
	}

	private class xulybutton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JButton jButton = (JButton) e.getSource();
			if (jButton == submitButton) {
				String name = nameField.getText();
				String email = emailField.getText();
				String feedback = feedbackArea.getText();

				if (name.isEmpty() || email.isEmpty() || feedback.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin.", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Cảm ơn phản hồi của bạn, " + name + "!", "Thành công",
							JOptionPane.INFORMATION_MESSAGE);
					nameField.setText("");
					emailField.setText("");
					feedbackArea.setText("");

				}
			} else if (jButton == cancelButton) {
				dispose();

			}

		}

	}

	private static void addField(JPanel panel, GridBagConstraints gbc, int row, String labelText, JTextField textField,
			String placeholder) {
		gbc.gridx = 0;
		gbc.gridy = row;
		JLabel label = new JLabel(labelText);
		panel.add(label, gbc);

		gbc.gridx = 1;

		textField.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
						BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		textField.setFont(new Font("Arial", Font.PLAIN, 14));
		textField.setForeground(Color.GRAY);
		textField.setText(placeholder);

		textField.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent e) {
				if (textField.getText().equals(placeholder)) {
					textField.setText("");
					textField.setForeground(Color.BLACK);
				}
			}

			public void focusLost(java.awt.event.FocusEvent e) {
				if (textField.getText().isEmpty()) {
					textField.setText(placeholder);
					textField.setForeground(Color.GRAY);
				}
			}
		});

		panel.add(textField, gbc);
	}

}
