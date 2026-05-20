import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Zones {
    protected static int counter = 1;
    protected final int code;
    protected String name;
    protected List<Sensors> sensorsList;
    protected String characteristic;
    protected Boolean statusActive;
    protected double O;
    protected double R;
    protected List<Alert> activeAlerts;

    protected Zones(String name, String characteristic) {
        this.code = counter++;
        this.name = name;
        this.characteristic = characteristic;
        this.statusActive = true;
        this.sensorsList = new ArrayList<>();
        this.activeAlerts = new ArrayList<>();
        this.O = 0 ; 
        this.R = 0 ;
    }

    public int getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String getCharacteristic() {
        return this.characteristic;
    }

    public Boolean getStatusActive() {
        return this.statusActive;
    }

    public double getO() {
        return this.O;
    }

    public double getR() {
        return this.R;
    }

    public List<Alert> getActiveAlerts() {
        return this.activeAlerts;
    }

    public List<Sensors> getSensors() {
        return this.sensorsList;
    }

    public void setLocation(double latitude, double longitude) {
        this.O = latitude;
        this.R = longitude;
    }

    public void addSensor(Sensors sensor) {
        if (sensor != null) {
            this.sensorsList.add(sensor);
        }
    }

    public void removeSensor(Sensors sensor) {
        this.sensorsList.remove(sensor);
    }

    public void editSensor(Sensors sensor, int index) {
        this.sensorsList.set(index, sensor);
    }

    public void addAlert(Alert alert) {
        if (alert != null) {
            this.activeAlerts.add(alert);
        }
    }

    public void removeAlert(Alert alert) {
        this.activeAlerts.remove(alert);
    }

    public void editAlert(Alert alert, int index) {
        this.activeAlerts.set(index, alert);
    }

    public void deactivate() {
        this.statusActive = false;
    }

    public void editSensors(List<Sensors> newSensorsList) {
        this.sensorsList = new ArrayList<>(newSensorsList);
    }

    public void editCharacteristic(String newCharacteristic) {
        this.characteristic = newCharacteristic;
    }

    public void editStatus(Boolean newStatus) {
        this.statusActive = newStatus;
    }

    public void display() {
        java.lang.System.out.println("Zone: " + this.name
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
        java.lang.System.out.println("Growth Stage: " + this.growthStage);
    }

    public void updateGrowthStage() {
        this.growthStage = this.growthStage.next();
    }

    public void displayCrops() {
        super.display();
        java.lang.System.out.println("Planting Date: " + this.plantingDate
                + " - Harvest Date: " + this.harvestDate
                + " - Growth Stage: " + this.growthStage
                + " - Soil Requirements: " + this.soilRequirements);
    }

    public void setPlantingDate(Date plantingDate) {
        this.plantingDate = plantingDate;
    }

    public void setHarvestDate(Date harvestDate) {
        this.harvestDate = harvestDate;
    }

    public void setGrowthStage(GrowthStage growthStage) {
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
        this.cropsType = cropsType;
    }

    public void setCropsProduction(double cropsProduction) {
        this.cropsProduction = cropsProduction;
    }

    public void displayCropsList() {
        super.displayCrops();
        java.lang.System.out.println("Crops Type: " + this.cropsType + " - Crops Production: " + this.cropsProduction);
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
        java.lang.System.out.println("Feeding Programme: " + this.feedingProgramme);
    }

    public void setFeedingProgramme(FeedingProgramme feedingProgramme) {
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
        this.ruminantType = ruminantType;
    }

    public double getMilkYield() {
        return this.milkYield;
    }

    public void setMilkYield(double milkYield) {
        this.milkYield = milkYield;
    }

    public void displayRuminant() {
        super.displayLiveStock();
        java.lang.System.out.println("Ruminant Type: " + this.ruminantType + " - Milk Yield: " + this.milkYield);
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
        this.poultryType = poultryType;
    }

    public void setEggCount(double eggCount) {
        this.eggCount = eggCount;
    }

    public double getEggCount() {
        return this.eggCount;
    }

    public void displayPoultry() {
        super.displayLiveStock();
        java.lang.System.out.println("Poultry Type: " + this.poultryType + " - Egg Count: " + this.eggCount);
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
        java.lang.System.out.println("Nb Animals: " + this.species.size());
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

class AquacultureList extends Aquaculture {
    public AquacultureList(String name, String characteristic, FeedingProgramme feedProgramme) {
        super(name, characteristic, feedProgramme);
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
        java.lang.System.out.println("Optimal PH: " + this.optimalPH + " - Optimal Moisture: " + this.optimalMoisture);
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
        java.lang.System.out.println("Feed Type: " + this.feedType + " - Quantity: " + this.quantity);
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
        java.lang.System.out.println("AnimalId: " + this.animalId
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
        this.age = age;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setHealthStatus(HealthStatus healthStatus) {
        this.healthStatus = healthStatus;
    }

    public void recordHealthEvent(HealthStatus status, String description, Date date) {
        HealthEvent event = new HealthEvent(date, status, description);
        this.healthEventsList.add(event);
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
        java.lang.System.out.println("RecordId: " + this.recordId
                + " - RecordDate: " + this.recordDate
                + " - RecordValue: " + this.recordValue
                + " - RecordType: " + this.recordType
                + " - RecordUnit: " + this.recordUnit);
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

class RecordsFilter {
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
        double sum = 0;
        for (ProductionRecord record : records) {
            sum += record.getRecordValue();
        }
        return sum;
    }
}


