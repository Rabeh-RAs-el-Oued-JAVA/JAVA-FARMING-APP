import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

enum AlertLevel {
    WARNING, CRITICAL
}

class Alert {
    private final AlertLevel    level;
    private final String        message;
    private final LocalDateTime triggeredAt;
    private       boolean       acknowledged;
    private final double sensorCode ;           

    public Alert(AlertLevel level, String message , double code) {
        this.level        = level;
        this.message      = message;
        this.triggeredAt  = LocalDateTime.now();
        this.acknowledged = false;
        this.sensorCode = code ; 
    }

    public void       acknowledge()       { this.acknowledged = true; }
    public boolean    isAcknowledged()    { return acknowledged; }
    public AlertLevel getLevel()          { return level; }
    public String     getMessage()        { return message; }
    public LocalDateTime getTriggeredAt() { return triggeredAt; }

    @Override
    public String toString() {
        return "[" + level + "] " + message + " (at " + triggeredAt + ")";
    }
}

abstract class Sensorrecord {
    protected Sensors sensor;
    protected String timestamp;
    protected static List<Alert> alertes = new ArrayList<>();
    protected Alert alert;

    public Sensorrecord(Sensors sensor, String timestamp, List<Alert> alertes, Alert alert) {
        this.sensor = sensor;
        this.timestamp = timestamp;
        this.alert = alert;
    }

    public abstract void displayRecord();
    public abstract void TestThreshold();
}

class NumericalRecord extends Sensorrecord {
    private double value;
    private String unit;

    public NumericalRecord(Sensors sensor) {
        super(sensor, java.time.LocalDateTime.now().toString(), alertes, null);
        if (sensor instanceof numuricalsensors) {
            numuricalsensors ns = (numuricalsensors) sensor;
            this.value = ns.getValue();
            this.unit = ns.getUnit() != null ? ns.getUnit().toString() : "";
        }
    }

    public NumericalRecord(Sensors sensor, String timestamp) {
        super(sensor, timestamp, alertes, null);
        if (sensor instanceof numuricalsensors) {
            numuricalsensors ns = (numuricalsensors) sensor;
            this.value = ns.getValue();
            this.unit = ns.getUnit() != null ? ns.getUnit().toString() : "";
        }
    }

    public double getValue() { return value; }
    public String getUnit() { return unit; }

    @Override
    public void displayRecord() {
        java.lang.System.out.println("Sensor: " + (sensor != null ? sensor.getName() : "null"));
        java.lang.System.out.println("Timestamp: " + timestamp);
        java.lang.System.out.println("Value: " + value + " " + unit);
    }

    @Override
    public void TestThreshold() {
        if (sensor != null) {
            if (value < sensor.getMinthreshold()) {
                if (value < sensor.getMinthreshold() - sensor.getMinthreshold() * 0.2) {
                    alert = new Alert(AlertLevel.CRITICAL, "Value is below threshold by more than 20%", sensor.code);
                    alertes.add(alert);
                    this.alert = alert;
                } else {
                    alert = new Alert(AlertLevel.WARNING, "Value is below threshold by less than 20%", sensor.code);
                    alertes.add(alert);
                    this.alert = alert;
                }
            } else if (value > sensor.getMaxthreshold()) {
                if (value > sensor.getMaxthreshold() + sensor.getMaxthreshold() * 0.2) {
                    alert = new Alert(AlertLevel.CRITICAL, "Value is above threshold by more than 20%", sensor.code);
                    alertes.add(alert);
                    this.alert = alert;
                } else {
                    alert = new Alert(AlertLevel.WARNING, "Value is above threshold by less than 20%", sensor.code);
                    alertes.add(alert);
                    this.alert = alert;
                }
            }
        }
    }
}

class GpsRecord extends Sensorrecord {
    private int latitude;
    private int longitude;

    public GpsRecord(gpssensor sensor) {
        super(sensor, java.time.LocalDateTime.now().toString(), alertes, null);
        this.latitude = sensor.getLatitude();
        this.longitude = sensor.getLongitude();
    }

    public GpsRecord(gpssensor sensor, String timestamp) {
        super(sensor, timestamp, alertes, null);
        this.latitude = sensor.getLatitude();
        this.longitude = sensor.getLongitude();
    }

    public int getLatitude() { return latitude; }
    public int getLongitude() { return longitude; }

    @Override
    public void displayRecord() {
        java.lang.System.out.println("Sensor: " + (sensor != null ? sensor.getName() : "null"));
        java.lang.System.out.println("Timestamp: " + timestamp);
        java.lang.System.out.println("Position: lat=" + latitude + ", lon=" + longitude);
    }

    @Override
    public void TestThreshold() {
        if (sensor != null) {
            if (latitude < sensor.getMinthreshold() || latitude > sensor.getMaxthreshold() ||
                longitude < sensor.getMinthreshold() || longitude > sensor.getMaxthreshold()) {
                alert = new Alert(AlertLevel.CRITICAL, "Position is out of threshold", sensor.code);
                alertes.add(alert);
                this.alert = alert;
            }
        }
    }
}