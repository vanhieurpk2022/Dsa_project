package model;

public class Ticket {
    private String flightCode;
    private String flightDate;
    private String flightTime;
    private String departureAirport;
    private String arrivalAirport;
    private double price; // Đổi sang kiểu double để dễ tính toán
    private double tax;
    private double total;

    private static final double TAX_RATE = 0.08;

    public Ticket(String flightCode, String flightDate, String flightTime, String departureAirport,
                  String arrivalAirport) {
        this.flightCode = flightCode;
        this.flightDate = flightDate;
        this.flightTime = flightTime;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;

        // Tự động tính toán giá vé, thuế và tổng tiền khi tạo đối tượng
        calculatePrice();
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
            this.price = 0;  // Giá mặc định nếu không khớp
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

    // Các setter khác (nếu cần)
    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
        calculatePrice(); // Cập nhật giá vé khi thay đổi điểm đi
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
        calculatePrice(); // Cập nhật giá vé khi thay đổi điểm đến
    }
}
