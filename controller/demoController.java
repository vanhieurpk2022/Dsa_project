package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import view.ChangePassword;
import view.Demo;
import view.LoginForm;

public class demoController implements ActionListener {
	private Demo demo;

	public demoController(Demo demo) {
		this.demo = demo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		String getAction = e.getActionCommand();
		JButton jbutotn = (JButton) e.getSource();

		// Đổi màu cho nút được nhấn
		if (getAction == "Đặt vé" || getAction == "Vé của tôi" || getAction == "Thông tin" || getAction == "Trang chủ"
				|| getAction == "") {
			demo.resetButtonColors();
			jbutotn.setBackground(Color.white);
			jbutotn.setBackground(Color.lightGray);

		}

		System.out.println(e.getActionCommand());
		if (getAction.equals("Đăng xuất")) {
			int a = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn thoát?");
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
		} else if (getAction.equals("Trang chủ")) {
			demo.updateTab("Trang chủ", demo.home());
		}

//		xử lí action nhập thông tin profile
		if (getAction.equals("Lưu")) {
			demo.setEditFalse();
		} else if (getAction.equals("Sửa")) {
			demo.setEditTrue();
		}
		if (getAction.equals(" ")) {
			new ChangePassword();
		}
	}

}
