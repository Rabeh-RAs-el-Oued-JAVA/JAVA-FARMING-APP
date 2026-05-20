import java.util.Date;

public class MainTest {
    public static void main(String[] args) {
        java.lang.System.out.println("=== STARTING SMART FARMING SYSTEM TEST ===");

        // 1. Initialize the system
        System farmingSystem = new System();

        // 2. Create Zones (Crops, Ruminant, Aquaculture)
        java.lang.System.out.println("\n--- Creating Zones ---");
        Zones wheatZone = farmingSystem.createZone("Crops", "Wheat Field A", "Organic wheat crops", 34.0, 5.0);
        Zones cowZone = farmingSystem.createZone("Ruminant", "Pasture B", "Dairy cows pasture", 35.0, 6.0);
        Zones fishZone = farmingSystem.createZone("Aquaculture", "Fish Pond C", "Tilapia aquaculture pond", 36.0, 7.0);

        if (wheatZone != null) {
            wheatZone.display();
            java.lang.System.out.println("Sensors registered for Wheat Field A: " + wheatZone.getSensors().size());
            for (Sensors s : wheatZone.getSensors()) {
                java.lang.System.out.println("  - " + s.getName() + " [Code: " + s.code + ", Status: " + s.getStatus() + "]");
            }
        }

        if (cowZone != null) {
            cowZone.display();
        }

        if (fishZone != null) {
            fishZone.display();
        }

        // 3. Test sensor readings and diagnostic simulation
        java.lang.System.out.println("\n--- Running Diagnostic (Sensor Readings & Alert Checks) ---");
        farmingSystem.runSystemDiagnostic();

        // 4. Check alerts
        java.lang.System.out.println("\n--- Listing Active Alerts ---");
        farmingSystem.displayActiveAlerts();

        // 5. Simulate out-of-range sensor readings to trigger warning and critical alerts
        java.lang.System.out.println("\n--- Simulating Out-of-Range Sensor Readings ---");
        if (wheatZone != null && !wheatZone.getSensors().isEmpty()) {
            for (Sensors s : wheatZone.getSensors()) {
                if (s instanceof soilmoisture) {
                    soilmoisture sm = (soilmoisture) s;
                    java.lang.System.out.println("Modifying Soil Moisture Sensor (Code " + sm.code + ") value to 95 (Max Threshold is 80)...");
                    sm.setValue(95); // Above maxthreshold of 80
                    farmingSystem.createsensorRecord(sm, java.time.LocalDateTime.now().toString());
                }
            }
        }

        if (fishZone != null && !fishZone.getSensors().isEmpty()) {
            for (Sensors s : fishZone.getSensors()) {
                if (s instanceof gpssensor) {
                    gpssensor gps = (gpssensor) s;
                    java.lang.System.out.println("Modifying GPS Sensor (Code " + gps.code + ") coordinates to lat=40, lon=40 (Threshold is lat=36, lon=7)...");
                    gps.setLatitude(40);
                    gps.setLongitude(40);
                    farmingSystem.createsensorRecord(gps, java.time.LocalDateTime.now().toString());
                }
            }
        }

        // 6. Check alerts again
        java.lang.System.out.println("\n--- Listing Active Alerts After Violations ---");
        farmingSystem.displayActiveAlerts();

        java.lang.System.out.println("\n=== SMART FARMING SYSTEM TEST ENDED ===");
    }
}
