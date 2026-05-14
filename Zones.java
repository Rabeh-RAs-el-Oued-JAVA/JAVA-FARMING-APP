package Zones ; 

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class TypeException extends RuntimeException {
    public TypeException(String message) {
        super(message);
    }
}

public abstract class Zones {
    protected static int counter = 1;
    protected final int code;
    protected String name;
    protected List<Sensor> sensorsList;
    protected String characteristic;
    protected Boolean statusActive;
    protected double latitude;
    protected double longitude;
    protected List<Alert> activeAlerts;

    protected Zones(String name, String characteristic) {
        this.code = counter++;
        this.name = name;
        this.characteristic = characteristic;
        this.statusActive = true;
        this.sensorsList = new ArrayList<>();
        this.activeAlerts = new ArrayList<>();
    }

    private void checkZoneActive() {
        if (!Boolean.TRUE.equals(this.statusActive)) {
            throw new IllegalStateException("Zone is inactive. Change the zone status before using this functionality.");
        }
    }

    public int getCode() {
        checkZoneActive();
        return this.code;
    }

    public String getName() {
        checkZoneActive();
        return this.name;
    }

    public String getCharacteristic() {
        checkZoneActive();
        return this.characteristic;
    }

    public Boolean getStatusActive() {
        return this.statusActive;
    }

    public double getLatitude() {
        checkZoneActive();
        return this.latitude;
    }

    public double getLongitude() {
        checkZoneActive();
        return this.longitude;
    }

    public List<Alert> getActiveAlerts() {
        checkZoneActive();
        return this.activeAlerts;
    }

    public List<Sensor> getSensors() {
        checkZoneActive();
        return this.sensorsList;
    }

    public void setLocation(double latitude, double longitude) {
        checkZoneActive();
        if (Double.isNaN(latitude) || Double.isInfinite(latitude) || latitude < -90 || latitude > 90) {
            throw new TypeException("Latitude must be a valid number between -90 and 90.");
        }
        if (Double.isNaN(longitude) || Double.isInfinite(longitude) || longitude < -180 || longitude > 180) {
            throw new TypeException("Longitude must be a valid number between -180 and 180.");
        }
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void addSensor(Sensor sensor) {
        checkZoneActive();
        if (sensor != null) {
            this.sensorsList.add(sensor);
        }
    }

    public void removeSensor(Sensor sensor) {
        checkZoneActive();
        this.sensorsList.remove(sensor);
    }

    public void editSensor(Sensor sensor, int index) {
        checkZoneActive();
        this.sensorsList.set(index, sensor);
    }

    public void addAlert(Alert alert) {
        checkZoneActive();
        if (alert != null) {
            this.activeAlerts.add(alert);
        }
    }

    public void removeAlert(Alert alert) {
        checkZoneActive();
        this.activeAlerts.remove(alert);
    }

    public void editAlert(Alert alert, int index) {
        checkZoneActive();
        this.activeAlerts.set(index, alert);
    }

    public void deactivate() {
        this.statusActive = false;
    }

    public void editSensors(List<Sensor> newSensorsList) {
        checkZoneActive();
        this.sensorsList = new ArrayList<>(newSensorsList);
    }

    public void editCharacteristic(String newCharacteristic) {
        checkZoneActive();
        this.characteristic = newCharacteristic;
    }

    public void editStatus(Boolean newStatus) {
        this.statusActive = newStatus;
    }

    public void display() {
        checkZoneActive();
        System.out.println("Zone: " + this.name
                + " - Code: " + this.code
                + " - Status: " + this.statusActive
                + " - Characteristic: " + this.characteristic
                + " - Sensors: " + this.sensorsList.size());
    }
}

abstract class Crops extends Zones {
    protected Date plantingDate;
    protected Date harvestDate;
    protected GrowthStage growthStage;
    protected SoilRequirements soilRequirements;

    protected Crops(String name, String characteristic, Date plantingDate, Date harvestDate,
                    GrowthStage growthStage, SoilRequirements soilRequirements) {
        super(name, characteristic);
        this.plantingDate = plantingDate;
        this.harvestDate = harvestDate;
        this.growthStage = growthStage;
        this.soilRequirements = soilRequirements;
    }

    public void displayGrowthStage() {
        System.out.println("Growth Stage: " + this.growthStage);
    }

    public void updateGrowthStage() {
        this.growthStage = this.growthStage.next();
    }

    public void displayCrops() {
        super.display();
        System.out.println("Planting Date: " + this.plantingDate
                + " - Harvest Date: " + this.harvestDate
                + " - Growth Stage: " + this.growthStage
                + " - Soil Requirements: " + this.soilRequirements);
    }

    public void setPlantingDate(Date plantingDate) {
        if (plantingDate == null) {
            throw new TypeException("Planting date must be a valid Date.");
        }
        this.plantingDate = plantingDate;
    }

    public void setHarvestDate(Date harvestDate) {
        if (harvestDate == null) {
            throw new TypeException("Harvest date must be a valid Date.");
        }
        this.harvestDate = harvestDate;
    }

    public void setGrowthStage(GrowthStage growthStage) {
        if (growthStage == null) {
            throw new TypeException("Growth stage must be a valid GrowthStage.");
        }
        this.growthStage = growthStage;
    }

    public Date getPlantingDate() {
        return this.plantingDate;
    }

    public Date getHarvestDate() {
        return this.harvestDate;
    }

    public GrowthStage getGrowthStage() {
        return this.growthStage;
    }
}

class CropsList extends Crops implements IRecordable {
    private CropsType cropsType;
    private double cropsProduction;
    private final List<ProductionRecord> records;

    public CropsList(String name, String characteristic, Date plantingDate, Date harvestDate,
                     GrowthStage growthStage, SoilRequirements soilRequirements,
                     CropsType cropsType, double cropsProduction) {
        super(name, characteristic, plantingDate, harvestDate, growthStage, soilRequirements);
        this.cropsType = cropsType;
        this.cropsProduction = cropsProduction;
        this.records = new ArrayList<>();
    }

    public void setCropsType(CropsType cropsType) {
        if (cropsType == null) {
            throw new TypeException("Crops type must be a valid CropsType.");
        }
        this.cropsType = cropsType;
    }

    public void setCropsProduction(double cropsProduction) {
        if (Double.isNaN(cropsProduction) || Double.isInfinite(cropsProduction) || cropsProduction < 0) {
            throw new TypeException("Crops production must be a positive number.");
        }
        this.cropsProduction = cropsProduction;
    }

    public void displayCropsList() {
        super.displayCrops();
        System.out.println("Crops Type: " + this.cropsType + " - Crops Production: " + this.cropsProduction);
    }

    @Override
    public void record(Date recordDate, double recordValue) {
        this.records.add(new ProductionRecord(recordDate, recordValue, this.cropsType.toString(), "Kg"));
    }

    @Override
    public void displayRecord() {
        for (ProductionRecord record : this.records) {
            record.display();
        }
    }

    @Override
    public List<ProductionRecord> getRecordsInDateRange(Date startDate, Date endDate) {
        List<ProductionRecord> result = new ArrayList<>();
        for (ProductionRecord record : this.records) {
            if (!record.getRecordDate().before(startDate) && !record.getRecordDate().after(endDate)) {
                result.add(record);
            }
        }
        return result;
    }

    @Override
    public double getTotalProduction() {
        double total = 0;
        for (ProductionRecord record : this.records) {
            total += record.getRecordValue();
        }
        return total;
    }

    @Override
    public double getAverageProduction() {
        return this.records.isEmpty() ? 0 : getTotalProduction() / this.records.size();
    }

    @Override
    public String generateProductionReport() {
        return "Crops production report - Type: " + this.cropsType
                + ", Total: " + getTotalProduction()
                + ", Average: " + getAverageProduction();
    }
}

abstract class LiveStock extends Zones {
    protected FeedingProgramme feedingProgramme;
    protected List<Animal> animalsList;
    protected String feedType;
    protected double feedQuantity;

    protected LiveStock(String name, String characteristic, FeedingProgramme feedingProgramme) {
        super(name, characteristic);
        this.feedingProgramme = feedingProgramme;
        this.animalsList = new ArrayList<>();
    }

    public void displayLiveStock() {
        super.display();
        System.out.println("Feeding Programme: " + this.feedingProgramme);
    }

    public void setFeedingProgramme(FeedingProgramme feedingProgramme) {
        if (feedingProgramme == null) {
            throw new TypeException("Feeding programme must be a valid FeedingProgramme.");
        }
        this.feedingProgramme = feedingProgramme;
    }

    public FeedingProgramme getFeedingProgramme() {
        return this.feedingProgramme;
    }

    public void addAnimal(Animal animal) {
        if (animal != null) {
            this.animalsList.add(animal);
        }
    }

    public void removeAnimal(Animal animal) {
        this.animalsList.remove(animal);
    }

    public List<Animal> getAnimals() {
        return this.animalsList;
    }

    public int getTotalAnimalCount() {
        return this.animalsList.size();
    }
}

class Ruminant extends LiveStock implements IRecordable {
    private String ruminantType;
    private double milkYield;
    private final List<ProductionRecord> records;

    public Ruminant(String name, String characteristic, FeedingProgramme feedingProgramme,
                    String ruminantType, double milkYield) {
        super(name, characteristic, feedingProgramme);
        this.ruminantType = ruminantType;
        this.milkYield = milkYield;
        this.records = new ArrayList<>();
    }

    public String getRuminantType() {
        return this.ruminantType;
    }

    public void setRuminantType(String ruminantType) {
        if (ruminantType == null || ruminantType.trim().isEmpty()) {
            throw new TypeException("Ruminant type must be a non-empty String.");
        }
        this.ruminantType = ruminantType;
    }

    public double getMilkYield() {
        return this.milkYield;
    }

    public void setMilkYield(double milkYield) {
        if (Double.isNaN(milkYield) || Double.isInfinite(milkYield) || milkYield < 0) {
            throw new TypeException("Milk yield must be a positive number.");
        }
        this.milkYield = milkYield;
    }

    public void displayRuminant() {
        super.displayLiveStock();
        System.out.println("Ruminant Type: " + this.ruminantType + " - Milk Yield: " + this.milkYield);
    }

    @Override
    public void record(Date recordDate, double recordValue) {
        this.records.add(new ProductionRecord(recordDate, recordValue, this.ruminantType, "L"));
    }

    @Override
    public void displayRecord() {
        for (ProductionRecord record : this.records) {
            record.display();
        }
    }

    @Override
    public List<ProductionRecord> getRecordsInDateRange(Date startDate, Date endDate) {
        return RecordsFilter.inDateRange(this.records, startDate, endDate);
    }

    @Override
    public double getTotalProduction() {
        return RecordsFilter.total(this.records);
    }

    @Override
    public double getAverageProduction() {
        return this.records.isEmpty() ? 0 : getTotalProduction() / this.records.size();
    }

    @Override
    public String generateProductionReport() {
        return "Ruminant production report - Type: " + this.ruminantType
                + ", Total: " + getTotalProduction()
                + ", Average: " + getAverageProduction();
    }
}

class Poultry extends LiveStock implements IRecordable {
    private String poultryType;
    private double eggCount;
    private final List<ProductionRecord> records;

    public Poultry(String name, String characteristic, FeedingProgramme feedingProgramme,
                   String poultryType, double eggCount) {
        super(name, characteristic, feedingProgramme);
        this.poultryType = poultryType;
        this.eggCount = eggCount;
        this.records = new ArrayList<>();
    }

    public String getPoultryType() {
        return this.poultryType;
    }

    public void setPoultryType(String poultryType) {
        if (poultryType == null || poultryType.trim().isEmpty()) {
            throw new TypeException("Poultry type must be a non-empty String.");
        }
        this.poultryType = poultryType;
    }

    public void setEggCount(double eggCount) {
        if (Double.isNaN(eggCount) || Double.isInfinite(eggCount) || eggCount < 0) {
            throw new TypeException("Egg count must be a positive number.");
        }
        this.eggCount = eggCount;
    }

    public double getEggCount() {
        return this.eggCount;
    }

    public void displayPoultry() {
        super.displayLiveStock();
        System.out.println("Poultry Type: " + this.poultryType + " - Egg Count: " + this.eggCount);
    }

    @Override
    public void record(Date recordDate, double recordValue) {
        this.records.add(new ProductionRecord(recordDate, recordValue, this.poultryType, "Eggs"));
    }

    @Override
    public void displayRecord() {
        for (ProductionRecord record : this.records) {
            record.display();
        }
    }

    @Override
    public List<ProductionRecord> getRecordsInDateRange(Date startDate, Date endDate) {
        return RecordsFilter.inDateRange(this.records, startDate, endDate);
    }

    @Override
    public double getTotalProduction() {
        return RecordsFilter.total(this.records);
    }

    @Override
    public double getAverageProduction() {
        return this.records.isEmpty() ? 0 : getTotalProduction() / this.records.size();
    }

    @Override
    public String generateProductionReport() {
        return "Poultry production report - Type: " + this.poultryType
                + ", Total: " + getTotalProduction()
                + ", Average: " + getAverageProduction();
    }
}

abstract class Aquaculture extends Zones implements IRecordable {
    protected List<Animal> species;
    protected FeedingProgramme feedProgramme;
    protected double waterTemperature;
    protected double dissolvedOxygen;
    protected double waterPH;
    private final List<ProductionRecord> records;

    protected Aquaculture(String name, String characteristic, FeedingProgramme feedProgramme) {
        super(name, characteristic);
        this.feedProgramme = feedProgramme;
        this.species = new ArrayList<>();
        this.records = new ArrayList<>();
    }

    public void setWaterQuality(double temperature, double oxygen, double pH) {
        if (Double.isNaN(temperature) || Double.isInfinite(temperature)) {
            throw new TypeException("Water temperature must be a valid number.");
        }
        if (Double.isNaN(oxygen) || Double.isInfinite(oxygen) || oxygen < 0) {
            throw new TypeException("Dissolved oxygen must be a positive number.");
        }
        if (Double.isNaN(pH) || Double.isInfinite(pH) || pH < 0 || pH > 14) {
            throw new TypeException("Water pH must be a valid number between 0 and 14.");
        }
        this.waterTemperature = temperature;
        this.dissolvedOxygen = oxygen;
        this.waterPH = pH;
    }

    public double getWaterTemperature() {
        return this.waterTemperature;
    }

    public double getDissolvedOxygen() {
        return this.dissolvedOxygen;
    }

    public double getWaterPH() {
        return this.waterPH;
    }

    public void addSpecies(Animal animal) {
        if (animal != null) {
            this.species.add(animal);
        }
    }

    public void deleteSpecies(Animal animal) {
        this.species.remove(animal);
    }

    public int getSpeciesCount() {
        return this.species.size();
    }

    public void setFeedingProgramme(FeedingProgramme feedingProgramme) {
        if (feedingProgramme == null) {
            throw new TypeException("Feeding programme must be a valid FeedingProgramme.");
        }
        this.feedProgramme = feedingProgramme;
    }

    public FeedingProgramme getFeedingProgramme() {
        return this.feedProgramme;
    }

    public void displayAquaculture() {
        super.display();
        for (Animal animal : this.species) {
            animal.display();
        }
        System.out.println("Nb Animals: " + this.species.size());
    }

    public double recordValue() {
        double sumWeight = 0;
        for (Animal animal : this.species) {
            sumWeight += animal.getWeight();
        }
        return sumWeight;
    }

    @Override
    public void record(Date recordDate, double recordValue) {
        this.records.add(new ProductionRecord(recordDate, recordValue, "Aquaculture", "Kg"));
    }

    @Override
    public void displayRecord() {
        for (ProductionRecord record : this.records) {
            record.display();
        }
    }

    @Override
    public List<ProductionRecord> getRecordsInDateRange(Date startDate, Date endDate) {
        return RecordsFilter.inDateRange(this.records, startDate, endDate);
    }

    @Override
    public double getTotalProduction() {
        return RecordsFilter.total(this.records);
    }

    @Override
    public double getAverageProduction() {
        return this.records.isEmpty() ? 0 : getTotalProduction() / this.records.size();
    }

    @Override
    public String generateProductionReport() {
        return "Aquaculture production report - Total: " + getTotalProduction()
                + ", Average: " + getAverageProduction();
    }
}

class SoilRequirements {
    private double optimalPH;
    private double optimalMoisture;

    public SoilRequirements(double optimalPH, double optimalMoisture) {
        this.optimalPH = optimalPH;
        this.optimalMoisture = optimalMoisture;
    }

    public void display() {
        System.out.println("Optimal PH: " + this.optimalPH + " - Optimal Moisture: " + this.optimalMoisture);
    }

    @Override
    public String toString() {
        return "PH=" + this.optimalPH + ", moisture=" + this.optimalMoisture;
    }
}

class FeedingProgramme {
    private String feedType;
    private double quantity;

    public FeedingProgramme(String feedType, double quantity) {
        this.feedType = feedType;
        this.quantity = quantity;
    }

    public void display() {
        System.out.println("Feed Type: " + this.feedType + " - Quantity: " + this.quantity);
    }

    @Override
    public String toString() {
        return this.feedType + " (" + this.quantity + ")";
    }
}

class HealthEvent {
    private Date eventDate;
    private HealthStatus healthStatus;
    private String description;

    public HealthEvent(Date eventDate, HealthStatus healthStatus, String description) {
        this.eventDate = eventDate;
        this.healthStatus = healthStatus;
        this.description = description;
    }

    public Date getEventDate() {
        return this.eventDate;
    }

    public HealthStatus getHealthStatus() {
        return this.healthStatus;
    }

    public String getDescription() {
        return this.description;
    }
}

class Animal {
    protected static int counter = 1;
    private final int animalId;
    private String species;
    private int age;
    private float weight;
    private HealthStatus healthStatus;
    private List<HealthEvent> healthEventsList;

    public Animal(String species, int age, float weight, HealthStatus healthStatus) {
        this.animalId = counter++;
        this.species = species;
        this.age = age;
        this.weight = weight;
        this.healthStatus = healthStatus;
        this.healthEventsList = new ArrayList<>();
    }

    public void display() {
        System.out.println("AnimalId: " + this.animalId
                + " - Species: " + this.species
                + " - Age: " + this.age
                + " - Weight: " + this.weight
                + " - HealthStatus: " + this.healthStatus);
    }

    public int getAnimalId() {
        return this.animalId;
    }

    public float getWeight() {
        return this.weight;
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new TypeException("Age must be a positive integer.");
        }
        this.age = age;
    }

    public void setWeight(float weight) {
        if (Float.isNaN(weight) || Float.isInfinite(weight) || weight < 0) {
            throw new TypeException("Weight must be a positive number.");
        }
        this.weight = weight;
    }

    public void setHealthStatus(HealthStatus healthStatus) {
        if (healthStatus == null) {
            throw new TypeException("Health status must be a valid HealthStatus.");
        }
        this.healthStatus = healthStatus;
    }

    public void recordHealthEvent(HealthStatus status, String description, Date date) {
        HealthEvent event = new HealthEvent(date, status, description);
        this.healthEventsList.add(event);
    }
}





class Alert {
    private int alertId;
    private String message;
    private Date alertDate;

    public Alert(int alertId, String message) {
        this.alertId = alertId;
        this.message = message;
        this.alertDate = new Date();
    }

    public int getAlertId() {
        return this.alertId;
    }

    public String getMessage() {
        return this.message;
    }

    public Date getAlertDate() {
        return this.alertDate;
    }
}

class Sensor {
    private String name;
    private SensorStatus status;

    public Sensor(String name, SensorStatus status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return this.name;
    }

    public SensorStatus getStatus() {
        return this.status;
    }

    public void setStatus(SensorStatus status) {
        if (status == null) {
            throw new TypeException("Sensor status must be a valid SensorStatus.");
        }
        this.status = status;
    }
}

interface IRecordable {
    void record(Date recordDate, double recordValue);

    void displayRecord();

    List<ProductionRecord> getRecordsInDateRange(Date startDate, Date endDate);

    double getTotalProduction();

    double getAverageProduction();

    String generateProductionReport();
}

class ProductionRecord {
    private static int counter = 1;
    private final int recordId;
    private Date recordDate;
    private double recordValue;
    private String recordType;
    private String recordUnit;

    public ProductionRecord(Date recordDate, double recordValue, String recordType, String recordUnit) {
        this.recordId = counter++;
        this.recordDate = recordDate;
        this.recordValue = recordValue;
        this.recordType = recordType;
        this.recordUnit = recordUnit;
    }

    public Date getRecordDate() {
        return this.recordDate;
    }

    public double getRecordValue() {
        return this.recordValue;
    }

    public void display() {
        System.out.println("RecordId: " + this.recordId
                + " - RecordDate: " + this.recordDate
                + " - RecordValue: " + this.recordValue
                + " - RecordType: " + this.recordType
                + " - RecordUnit: " + this.recordUnit);
    }
}



class RecordsFilter {
    private RecordsFilter() {
    }

    public static List<ProductionRecord> inDateRange(List<ProductionRecord> records, Date startDate, Date endDate) {
        List<ProductionRecord> result = new ArrayList<>();
        for (ProductionRecord record : records) {
            if (!record.getRecordDate().before(startDate) && !record.getRecordDate().after(endDate)) {
                result.add(record);
            }
        }
        return result;
    }

    public static double total(List<ProductionRecord> records) {
        double total = 0;
        for (ProductionRecord record : records) {
            total += record.getRecordValue();
        }
        return total;
    }
}

enum HealthStatus {
    HEALTHY, SICK, QUARANTINE
}

enum GrowthStage {
    SEMIS, GERMINATION, CROISSANCE, MATURITE, RECOLTE;

    public GrowthStage next() {
        GrowthStage[] values = values();
        int nextIndex = ordinal() + 1;
        return nextIndex < values.length ? values[nextIndex] : this;
    }
}

enum SensorStatus {
    ACTIVE, SUSPENDED, FAULTY
}

enum LocationType {
    CROP, LIVESTOCK, AQUACULTURE
}

enum CropsType {
    CEREAL, FRUITS, VEGETABLES
}
