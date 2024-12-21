package model;

public class Ticket {
	private String flightCode;
	private String flightDate;
	private String flightTime;
	private String departureAirport;
	private String arrivalAirport;
	private double price;
	private double tax;
	private double total;

	private static double TAX_RATE = 0.08;

	public Ticket(String flightCode, String flightDate, String flightTime, String departureAirport,
			String arrivalAirport) {
		this.flightCode = flightCode;
		this.flightDate = flightDate;
		this.flightTime = flightTime;
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;

		calculatePrice();
	}

	public Ticket() {
	}

	// Phương thức tính giá vé dựa trên điểm đi và điểm đến
	private void calculatePrice() {
		if (departureAirport.equals("Hà Nội") && arrivalAirport.equals("Đà Nẵng")) {
			this.price = 1000000;
		} else if (departureAirport.equals("Hà Nội") && arrivalAirport.equals("Nha Trang")) {
			this.price = 1500000;
		} else if (departureAirport.equals("Hà Nội") && arrivalAirport.equals("TPHCM")) {
			this.price = 2000000;
		} else {
			this.price = 0; // Giá mặc định nếu không khớp
		}

		// Tính thuế và tổng tiền dựa trên giá vé
		this.tax = this.price * TAX_RATE;
		this.total = this.price + this.tax;
	}

	// Các getter cho giá vé, thuế và tổng tiền
	public double getPrice() {
		return price;
	}

	public double getTax() {
		return tax;
	}

	public double getTotal() {
		return total;
	}

	// Các getter khác
	public String getFlightCode() {
		return flightCode;
	}

	public String getFlightDate() {
		return flightDate;
	}

	public String getFlightTime() {
		return flightTime;
	}

	public String getDepartureAirport() {
		return departureAirport;
	}

	public String getArrivalAirport() {
		return arrivalAirport;
	}

	public static double getTaxRate() {
		return TAX_RATE;
	}

	public static void setTaxRate(double taxRate) {
		TAX_RATE = taxRate;
	}

	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}

	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	public void setFlightTime(String flightTime) {
		this.flightTime = flightTime;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	// Các setter khác (nếu cần)
	public void setDepartureAirport(String departureAirport) {
		this.departureAirport = departureAirport;
		calculatePrice();
	}

	public void setArrivalAirport(String arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
		calculatePrice();
	}

	@Override
	public String toString() {
		return "\n" + flightCode + "," + flightDate + "," + flightTime + "," + departureAirport + "," + arrivalAirport;
	}
}
