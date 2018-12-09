package zedcode.giagioi.testthi.model;

public class AddMap {
    private double Latitude;
    private double Longitude;

    public AddMap(int latitude, int longitude) {
        Latitude = latitude;
        Longitude = longitude;
    }
    public  AddMap() {

    }
    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }
}
