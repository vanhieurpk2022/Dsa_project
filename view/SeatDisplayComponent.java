package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class SeatDisplayComponent extends JPanel {
	private static final int THRESHOLD_FOR_COMBOBOX = 2;

	private final JTextField selectedSeatsField;

	public SeatDisplayComponent() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(200, 40));

		selectedSeatsField = new JTextField("Chưa chọn ghế");
		selectedSeatsField.setEditable(false);
		selectedSeatsField.setHorizontalAlignment(JTextField.CENTER);
		selectedSeatsField.setFont(new Font("Arial", Font.PLAIN, 12));

		add(selectedSeatsField);
	}

	public void updateDisplay(List<Integer> selectedSeats) {
		removeAll();

		if (selectedSeats.isEmpty()) {
			selectedSeatsField.setText("Chưa chọn ghế");
		} else {
			// Lấy ghế đầu tiên trong danh sách và hiển thị
			int seat = selectedSeats.get(0);
			selectedSeatsField.setText(formatSingleSeat(seat));
		}

		add(selectedSeatsField);
		revalidate();
		repaint();
	}

	private String formatSingleSeat(int seat) {
		int seatNumber = seat + 1;
		return seatNumber <= 20 ? "V-" + seatNumber : "G-" + seatNumber;
	}

	public String getSelectedSeat() {
		return selectedSeatsField.getText();
	}

}
