package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;

import model.SavePass;
import model.User;
import model.UserManager;
import view.Demo;
import view.LoginForm;
import view.RegistrationForm;
import view.adminSetting;

public class LoginController implements ActionListener {
	LoginForm loginform;
	UserManager userManager = new UserManager();
	boolean checkbox;
	private SavePass savepass = new SavePass(LoginForm.class);

	public LoginController(LoginForm loginform) {
		this.loginform = loginform;
		savepass.putAccountAdmin();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			userManager.LoadUser("src/data/user.txt");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String getAction = e.getActionCommand();
		String username = loginform.getUserName();
		String password = loginform.getPassword();
		User user = new User(username, password);

		if (getAction.equals("Đăng nhập")) {

			// kiểm tra ô chứa
			if (username.isEmpty() || password.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
			} else { // không empty
				try {

					if (savepass.getAdminUser().equals(username) && savepass.getAdminPass().equals(password)) {
						loginform.dispose();
						new adminSetting();

					} else if (userManager.isExits(username, password)) { // đăng nhập thành công
						loginform.dispose();
						savepass.save(username, password);
						new Demo();

					} else {
						JOptionPane.showMessageDialog(null, "Tên đăng nhập hoặc mật khẩu không đúng", "Lỗi",
								JOptionPane.ERROR_MESSAGE);
					}

				} catch (Exception a) {
					// Xử lý ngoại lệ nếu có
					JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi: " + a.getMessage(), "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					a.printStackTrace(); // In chi tiết lỗi ra console (nếu cần thiết)
				}

			}

		} else if (getAction.equals("Đăng ký")) {
			new RegistrationForm();

		} else if (getAction.equals("Hủy")) {
			System.exit(0);
		}

	}

}
