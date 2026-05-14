abstract class Sensorrecord {
    protected Sensors sensor;
    protected String timestamp;

    public Sensorrecord(Sensors sensor, String timestamp) {
        this.sensor = sensor;
        this.timestamp = timestamp;
    }

    public Sensors getSensor() { return sensor; }
    public String getTimestamp() { return timestamp; }

    public abstract void displayRecord();
}
class NumericalRecord extends Sensorrecord {
    private double value;
    private String unit;

    public NumericalRecord(Sensors sensor, String timestamp, double value, String unit) {
        super(sensor, timestamp);
        this.value = value;
        this.unit = unit;
    }

     public double getValue() { return value; }
    public String getUnit() { return unit; }

    @Override
    public void displayRecord() {
        System.out.println("Sensor: " + sensor.getName());
        System.out.println("Timestamp: " + timestamp);
        System.out.println("Value: " + value + " " + unit);
    }
}
 class GpsRecord extends Sensorrecord {
    private int latitude;
    private int longitude;

    public GpsRecord(Sensors sensor, String timestamp, int latitude, int longitude) {
        super(sensor, timestamp);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getLatitude() { return latitude; }
    public int getLongitude() { return longitude; }

    @Override
    public void displayRecord() {
        System.out.println("Sensor: " + sensor.getName());
        System.out.println("Timestamp: " + timestamp);
        System.out.println("Position: lat=" + latitude + ", lon=" + longitude);
    }
}