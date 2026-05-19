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
    protected  static List<History>  alertes ;
    protected Alert alert;

    public Sensorrecord(Sensors sensor, String timestamp, <History> alertes, Alert alert) {
        this.sensor = sensor;
        this.timestamp = timestamp;
        this.alertes = new ArrayList<History>();
        this.alert = alert;
    }




}

class NumericalRecord extends Sensorrecord {
    private double value;
    private String unit;

    public NumericalRecord(Sensors sensor) {
        super(sensor, timestamp, alertes, alert);
        this.value = sensor.getValue();
        this.unit = sensor.getUnit();
    }

    public double getValue() { return value; }
    public String getUnit() { return unit; }

    @Override
    public void displayRecord() {
        System.out.println("Sensor: " + sensor.getName());
        System.out.println("Timestamp: " + timestamp);
        System.out.println("Value: " + value + " " + unit);
    }

    public void TestThreshold() {
                if(value < sensor.getMinthreshold() ) {
                    if(value  < sensor.getMinthreshold() - sensor.getMinthreshold() * 0.2) {
                        alert = new Alert(AlertLevel.CRITICAL, "Value is below threshold by more than 20%");
                        alertes.add(alert);
                        this.alert = alert;
                    }else{
                        alert = new Alert(AlertLevel.WARNING, "Value is below threshold by less than 20%");
                        alertes.add(alert);
                        this.alert = alert;
                    }

                
                }
                else if(value > sensor.getMaxthreshold() ) {
                    if(value  > sensor.getMaxthreshold() + sensor.getMaxthreshold() * 0.2) {
                        alert = new Alert(AlertLevel.CRITICAL, "Value is above threshold by more than 20%");
                        alertes.add(alert);
                        this.alert = alert;
                    }else{
                        alert = new Alert(AlertLevel.WARNING, "Value is above threshold by less than 20%");
                        alertes.add(alert);
                        this.alert = alert;
                    }
                }





                }


    









}
 class GpsRecord extends Sensorrecord {
    private int latitude;
    private int longitude;

    public GpsRecord(gpssensor sensor) {
        super(sensor, timestamp, alertes, alert);
        this.latitude = sensor.getLatitude();
        this.longitude = sensor.getLongitude();
    }

    public int getLatitude() { return latitude; }
    public int getLongitude() { return longitude; }

    @Override
    public void displayRecord() {
        System.out.println("Sensor: " + sensor.getName());
        System.out.println("Timestamp: " + timestamp);
        System.out.println("Position: lat=" + latitude + ", lon=" + longitude);
    }


     public void TestThreshold() {
        if(latitude < minthreshold || latitude > maxthreshold || longitude < minthreshold || longitude > maxthreshold) {
            alert = new Alert(AlertLevel.CRITICAL, "Position is out of threshold");
            alertes.add(alert);
            this.alert = alert;
        }
    }










}