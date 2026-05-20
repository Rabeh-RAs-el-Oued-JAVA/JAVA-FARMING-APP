import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class System {
    private List<Zones> zonesList;
    private List<Sensorrecord> sensorRecordsList;
    private int sensorCodeCounter;

    public System() {
        this.zonesList = new ArrayList<>();
        this.sensorRecordsList = new ArrayList<>();
        this.sensorCodeCounter = 1;
    }

    public List<Zones> getZonesList() {
        return zonesList;
    }

    public List<Sensorrecord> getSensorRecordsList() {
        return sensorRecordsList;
    }

    /**
     * Creates a new zone based on its type (Crops, Ruminant, Poultry, Aquaculture), sets its location coordinates, 
     * registers default sensors suitable for that type, configures a GPS sensor with the zone's coordinates 
     * as its threshold values, and appends the zone to the global system list.
     *
     * @param zoneType The classification of the zone (Crops/CropsList, Ruminant, Poultry, Aquaculture).
     * @param name The display name of the zone.
     * @param characteristic The main characteristics of the zone.
     * @param latitude The latitude location coordinate.
     * @param longitude The longitude location coordinate.
     * @return The newly instantiated and configured Zones object.
     */
    public Zones createZone(String zoneType, String name, String characteristic, double latitude, double longitude) {
        Zones zone = null;
        location sensorLoc = location.grap;

        if (zoneType.equalsIgnoreCase("Crops") || zoneType.equalsIgnoreCase("CropsList")) {
            zone = new CropsList(name, characteristic, new Date(), new Date(), GrowthStage.SEMIS, new SoilRequirements(6.5, 40.0), CropsType.CEREAL, 0.0);
            sensorLoc = location.grap;
        } else if (zoneType.equalsIgnoreCase("Ruminant")) {
            zone = new Ruminant(name, characteristic, new FeedingProgramme("Grass", 15.0), "Cow", 0.0);
            sensorLoc = location.livestock;
        } else if (zoneType.equalsIgnoreCase("Poultry")) {
            zone = new Poultry(name, characteristic, new FeedingProgramme("Grain", 0.2), "Chicken", 0.0);
            sensorLoc = location.livestock;
        } else if (zoneType.equalsIgnoreCase("Aquaculture") || zoneType.equalsIgnoreCase("AquacultureList")) {
            zone = new AquacultureList(name, characteristic, new FeedingProgramme("Fish Feed", 2.0));
            sensorLoc = location.aquaculture;
        }

        if (zone != null) {
            zone.setLocation(latitude, longitude);
            
            // The latitude and longitude are passed as the GPS threshold values
            int minGpsThreshold = (int) latitude;
            int maxGpsThreshold = (int) longitude;

            // GPS Sensor creation
            gpssensor gps = new gpssensor(sensorCodeCounter++, sensorLoc, minGpsThreshold, maxGpsThreshold, (int) latitude, (int) longitude);
            zone.addSensor(gps);

            // Populate the zone with default sensor arrays corresponding to its type
            if (zone instanceof CropsList) {
                zone.addSensor(new soilmoisture(sensorCodeCounter++, sensorLoc, 30, 80, 50, "%", typee.soil, false));
                zone.addSensor(new ph(sensorCodeCounter++, sensorLoc, 5, 8, 6, "pH", typee.soil, false));
                zone.addSensor(new nitrogenlevel(sensorCodeCounter++, sensorLoc, 10, 50, 25, "%", typee.soil, false));
                zone.addSensor(new temperature(sensorCodeCounter++, sensorLoc, 10, 40, 22, "C", typee.enviromental, false));
                zone.addSensor(new humidity(sensorCodeCounter++, sensorLoc, 20, 90, 60, "%", typee.enviromental, false));
                zone.addSensor(new rainfall(sensorCodeCounter++, sensorLoc, 0, 100, 15, "mm", typee.enviromental, false));
            } else if (zone instanceof Ruminant) {
                zone.addSensor(new temperature(sensorCodeCounter++, sensorLoc, 35, 41, 38, "C", typee.biometric, false));
                zone.addSensor(new activitylevel(sensorCodeCounter++, sensorLoc, 0, 100, 50, "steps", typee.biometric, false));
            } else if (zone instanceof Poultry) {
                zone.addSensor(new temperature(sensorCodeCounter++, sensorLoc, 15, 30, 22, "C", typee.enviromental, false));
                zone.addSensor(new humidity(sensorCodeCounter++, sensorLoc, 40, 80, 60, "%", typee.enviromental, false));
            } else if (zone instanceof Aquaculture) {
                zone.addSensor(new temperature(sensorCodeCounter++, sensorLoc, 15, 30, 20, "C", typee.water, false));
                zone.addSensor(new dissolvedoxygen(sensorCodeCounter++, sensorLoc, 4, 12, 8, "mg/L", typee.water, false));
                zone.addSensor(new ph(sensorCodeCounter++, sensorLoc, 6, 9, 7, "pH", typee.water, false));
            }

            zonesList.add(zone);
        }

        return zone;
    }

    /**
     * Instantiates a new Sensorrecord associated with the provided sensor and timestamp,
     * triggers its threshold checks, appends the record to the system logs, and propagates
     * any generated alerts to the relevant zone.
     *
     * @param sensor The source sensor providing the reading.
     * @param timestamp The timestamp of the reading.
     * @return The created Sensorrecord object.
     */
    public Sensorrecord createsensorRecord(Sensors sensor, String timestamp) {
        Sensorrecord record = null;
        if (sensor instanceof gpssensor) {
            record = new GpsRecord((gpssensor) sensor, timestamp);
        } else if (sensor instanceof numuricalsensors) {
            record = new NumericalRecord((numuricalsensors) sensor, timestamp);
        }

        if (record != null) {
            record.TestThreshold();
            sensorRecordsList.add(record);
            
            // Check if this record triggered an Alert, and add it to the corresponding zone
            if (record.alert != null) {
                for (Zones zone : zonesList) {
                    if (zone.getSensors().contains(sensor)) {
                        zone.addAlert(record.alert);
                    }
                }
            }
        }
        return record;
    }

    /**
     * Additional helper function: Iterates over all zones and displays a structured summary 
     * of all active, unacknowledged warning or critical alerts.
     */
    public void displayActiveAlerts() {
        java.lang.System.out.println("=== SYSTEM ACTIVE ALERTS ===");
        boolean hasAlerts = false;
        for (Zones zone : zonesList) {
            if (!zone.getActiveAlerts().isEmpty()) {
                hasAlerts = true;
                java.lang.System.out.println("Zone: " + zone.getName() + " has " + zone.getActiveAlerts().size() + " active alerts:");
                for (Alert alert : zone.getActiveAlerts()) {
                    java.lang.System.out.println("  - " + alert.toString());
                }
            }
        }
        if (!hasAlerts) {
            java.lang.System.out.println("No active alerts detected in the farming system.");
        }
    }

    /**
     * Additional helper function: Scans the entire system, collects live sensor readings from all 
     * registered zones, generates history records, and triggers threshold monitoring checks.
     */
    public void runSystemDiagnostic() {
        java.lang.System.out.println("=== INITIATING SYSTEM DIAGNOSTIC ===");
        String timestamp = java.time.LocalDateTime.now().toString();
        for (Zones zone : zonesList) {
            java.lang.System.out.println("Reading sensors for Zone: " + zone.getName() + " (Code: " + zone.getCode() + ")");
            for (Sensors sensor : zone.getSensors()) {
                createsensorRecord(sensor, timestamp);
            }
        }
        java.lang.System.out.println("Diagnostic complete. Total sensor records created: " + sensorRecordsList.size());
    }
}
