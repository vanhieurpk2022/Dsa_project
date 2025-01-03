package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import model.Ticket;
import model.TicketManager;
import view.LoginForm;
import view.adminSetting;

public class adminController implements ActionListener {
	private adminSetting admin;
	private TicketManager ticket = new TicketManager();

	public adminController(adminSetting admin) {
		super();
		this.admin = admin;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// TODO Auto-generated method stub
		JButton button = (JButton) e.getSource();
		if (button == admin.button1 || button == admin.button2 || button == admin.button3 || button == admin.button4) {
			admin.resetButtonColors();
			button.setBackground(Color.lightGray);
			button.setForeground(Color.WHITE);

		}

		// Gọi phương thức cập nhật tab
		if (button == admin.button1) {
			admin.updateTab("Trang chủ", admin.home());
		} else if (button == admin.button2) {

			admin.updateTab("Quản lý vé", admin.mtickets());

		} else if (button == admin.button3) {
			admin.updateTab("Quản lý chuyến bay", admin.mPlane());
		} else if (button == admin.button4) {
			admin.updateTab("Quản lý khách hàng", admin.mCustomer());

		} else if (button == admin.button5) {
			int confirmResult = JOptionPane.showConfirmDialog(null, "Bạn có có muốn đăng xuất?", "Xác nhận",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (confirmResult == JOptionPane.YES_OPTION) {
				new LoginForm();
				admin.setVisible(false);
			}
		} else if (button == admin.btnAdd) {
			if (!admin.validateInput(admin.dateChooser, admin.timeSpinner, admin.cboSanBayDi, admin.cboSanBayDen,
					admin.panelM)) {
				return;
			}

			// add with information input
			String maCB = admin.generateFlightCode(admin.generatedCodes);
			String ngayCC = new SimpleDateFormat("dd/MM/yyyy").format(admin.dateChooser.getDate());
			String thoiGian = new SimpleDateFormat("HH:mm").format(admin.timeSpinner.getValue());
			String sanBayDi = admin.cboSanBayDi.getSelectedItem().toString();
			String sanBayDen = admin.cboSanBayDen.getSelectedItem().toString();

			admin.tableModel.addRow(new Object[] { maCB, ngayCC, thoiGian, sanBayDi, sanBayDen });
			Ticket tick = new Ticket(maCB, ngayCC, thoiGian, sanBayDi, sanBayDen);
			ticket.addTicket(tick);
			try {
				ticket.WriteTick("src/data/tickets.txt");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			admin.showSuccessMessage(admin.panelM, "Thêm chuyến bay thành công!");
			admin.clearFields(admin.dateChooser, admin.timeSpinner, admin.cboSanBayDi, admin.cboSanBayDen);

		} else if (button == admin.btnDelete) {
			int selectedRow = admin.table.getSelectedRow();

			if (selectedRow >= 0) {
				String maCB = admin.tableModel.getValueAt(selectedRow, 0).toString();

				admin.generatedCodes.remove(maCB);
				admin.tableModel.removeRow(selectedRow);
				admin.showSuccessMessage(admin.panelM, "Xóa chuyến bay thành công!");

				ticket.removeTicket(maCB);
				try {
					ticket.WriteTick("src/data/tickets.txt");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				admin.showErrorMessage(admin.panelM, "Hãy chọn chuyến bay cần xóa.");
			}
		} else if (button == admin.btnUpdate) {
			int selectedRow = admin.table.getSelectedRow();
			if (selectedRow >= 0) {
				if (!admin.validateInput(admin.dateChooser, admin.timeSpinner, admin.cboSanBayDi, admin.cboSanBayDen,
						admin.panelM)) {
					return;
				}

				admin.updateTableRow(admin.tableModel, selectedRow, admin.dateChooser, admin.timeSpinner,
						admin.cboSanBayDi, admin.cboSanBayDen);
				admin.showSuccessMessage(admin.panelM, "Cập nhật chuyến bay thành công!");
				String maCB = admin.tableModel.getValueAt(selectedRow, 0).toString();
				String ngayCC = new SimpleDateFormat("dd/MM/yyyy").format(admin.dateChooser.getDate());
				String thoiGian = new SimpleDateFormat("HH:mm").format(admin.timeSpinner.getValue());
				String sanBayDi = admin.cboSanBayDi.getSelectedItem().toString();
				String sanBayDen = admin.cboSanBayDen.getSelectedItem().toString();
				ticket.findticket(maCB, ngayCC, thoiGian, sanBayDi, sanBayDen);
				try {
					ticket.WriteTick("src/data/tickets.txt");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				admin.showErrorMessage(admin.panelM, "Hãy chọn chuyến bay cần cập nhật.");
			}

		} else if (button == admin.btnClear) {
			admin.clearFields(admin.dateChooser, admin.timeSpinner, admin.cboSanBayDi, admin.cboSanBayDen);
			admin.table.clearSelection();
		}

	}

}
