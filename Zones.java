 from date import java.util.Date;

public abstract  class Zones{
    protected static int counter = 1 ;
    protected final  int code ;
    protected String name ;
    protected SensorsList Sensors[];
    protected String Charactestic ;
    protected Boolean StatusActive = true;

    //-----------------------------------------------------

    public void Desactivate(){
        this.StatusActive = false ;
    }

    public void Edit_Senssors( Sensors[] New_SEnsor_List ){
        this.SensorsList = New_SEnsor_List ;
    }

    public void Edit_Charactestic(String New_char  ){
        this.Charactestic = New_char ;
    }
    public void Edit_Status(  Boolean New_Status){
        this.StatusActive = New_Status ;
    }
    public void Display(){
        System.out.println("Zone : " + this.name + " - Code : " + this.code + " - Status : " + this.StatusActive + " - Charactestic : " + this.Charactestic + " - Sensors : " + this.Sensors.length);

    }
    public void AssignAttributes(String name )
    {
        this.name = name ;
    }
    public void AssignAttributes(Sensors[] Sensors )
    {
        this.Sensors = Sensors ;
    }
    public void AssignAttributes(String Charactestic )
    {
        this.Charactestic = Charactestic ;
    }
    public void AssignAttributes(Boolean StatusActive )
    {
        this.StatusActive = StatusActive ;
    }

    public void AddZone(String name , Sensors[] Sensors , String Charactestic , Boolean StatusActive )
    {
        this.name = name ;
        this.Sensors = Sensors ;
        this.Charactestic = Charactestic ;
        this.StatusActive = StatusActive ;
        this.code = counter++ ;
    }
}


public abstract class Crops extends Zones {
    protected Date PlantingDate ;
    protected Date HarvestDate ;
    protected Growth_satge GrowthStage ;
    protected Soil_requirements SoilRequirements ;

    public void DisplayGrowthStage(){
        System.out.println("Growth Stage : " + this.GrowthStage);
    }
    public void UpdateGrowthStage(){
        this.GrowthStage = this.GrowthStage.next();
    }
    public void RegisterCrops(String name , Sensors[] Sensors , String Charactestic , Boolean StatusActive , Date PlantingDate , Date HarvestDate , Growth_satge GrowthStage , Soil_requirements SoilRequirements ){
        super.AddZone(name , Sensors , Charactestic , StatusActive );
        this.PlantingDate = PlantingDate ;
        this.HarvestDate = HarvestDate ;
        this.GrowthStage = GrowthStage ;
        this.SoilRequirements = SoilRequirements ;
    }

    public void DisplayCrops(){
        super.Display();
        System.out.println("Planting Date : " + this.PlantingDate + " - Harvest Date : " + this.HarvestDate + " - Growth Stage : " + this.GrowthStage + " - Soil Requirements : " + this.SoilRequirements);
    }

    public void AssignAttributes(Date PlantingDate ){
        this.PlantingDate = PlantingDate ;
    }
    public void AssignAttributes(Date HarvestDate ){
        this.HarvestDate = HarvestDate ;
    }
    public void AssignAttributes(Growth_satge GrowthStage ){
        this.GrowthStage = GrowthStage ;
    }
    public void AssignAttributes(Soil_requirements SoilRequirements ){
        this.SoilRequirements = SoilRequirements ;
    }
}

public class Crops_List extends Crops, Records{
    private CropsType Crops_Type ; 
    private double Crops_Production ; 


    public void CropsTypeRegister(CropsType Crops_Type ){
        this.Crops_Type = Crops_Type ;
    }
    public void CropsProductionRegister(double Crops_Production ){
        this.Crops_Production = Crops_Production ;
    }
    public void DisplayCrops_List(){
        super.Display();
        System.out.println("Crops Type : " + this.Crops_Type + " - Crops Production : " + this.Crops_Production);
    }

    public void AddCrops_List(String name , Sensors[] Sensors , String Charactestic , Boolean StatusActive , Date PlantingDate , Date HarvestDate , Growth_satge GrowthStage , Soil_requirements SoilRequirements , CropsType Crops_Type , double Crops_Production ){
        super.RegisterCrops(name , Sensors , Charactestic , StatusActive , PlantingDate , HarvestDate , GrowthStage , SoilRequirements );
        this.Crops_Type = Crops_Type ;
        this.Crops_Production = Crops_Production ;
    }

    public void record(String recordDate , double recordValue ){
        super.record(recordDate , this.Crops_Production );
        super.recordWihda = "Kg" ;
        super.recordType = this.Crops_Type.toString() ;
    }
}


//---------------------------------------------------------------------------------------------- Usess Classes --------------------------------------------------------------
public class Soil_requirements {

    private double OptimalPH ; 
    private double OptimalMoisture ; 

    public void DisplaySoil_requirements(){
        System.out.println("Optimal PH : " + this.OptimalPH + " - Optimal Moisture : " + this.OptimalMoisture);
    }
    public void Assign_SOil_Req(double OptimalPH , double OptimalMoisture ){
        this.OptimalPH = OptimalPH ;
        this.OptimalMoisture = OptimalMoisture ;
    }
}

public class FeedingProgramme{
    private String FeedType ; 
    private double Qunatity ; 

    public void AddFedingProgramme(String FeedType , double Qunatity ){
        this.FeedType = FeedType ;
        this.Qunatity = Qunatity ;
    }
    public void DisplayFedingProgramme(){
        System.out.println("Feed Type : " + this.FeedType + " - Qunatity : " + this.Qunatity);
    }
  
}
public class Animals{
    protected static int counter = 1 ;
    private final intAnimalId  ; 
    private int Age ; 
    private float Weight ; 
    private Health_status HealthStatus ; 


    public void Assign_animals( String Species , int Age , float Weight , Health_status HealthStatus ){
        this.AnimalId = counter++ ;
        this.Age = Age ;
        this.Weight = Weight ;
        this.HealthStatus = HealthStatus ;
    }
    public void DisplayAnimal(){
        System.out.println("AnimalId : " + this.AnimalId + " - Age : " + this.Age + " - Weight : " + this.Weight + " - HealthStatus : " + this.HealthStatus);
    }
  
    public void Assign_animals(int Age ){
        this.Age = Age ;
    }
    public void Assign_animals(float Weight ){
        this.Weight = Weight ;
    }
    public void Assign_animals(Health_status HealthStatus ){
        this.HealthStatus = HealthStatus ;
    }
}
//--------------------------------------------------------------------------------------------------------------------------------------------------





public abstract class LiveStrock extends Zones {
    protected FeedingProgramme FeedingProgramme ;

    public void Assign_LiveStrock(FeedingProgramme FeedingProgramme ; name , Sensors[] Sensors , String Charactestic , Boolean StatusActive ){
        super.AddZone(name , Sensors , Charactestic , StatusActive );
        this.FeedingProgramme = FeedingProgramme ;
    }
    public void DisplayLiveStrock(){
        super.Display();
        System.out.println("Feeding Programme : " + this.FeedingProgramme);
    }

    public void Assign_FeedingAttributes(FeedingProgramme FeedingProgramme ){
        this.FeedingProgramme = FeedingProgramme ;
    }


}

public class Ruminant extends LiveStrock,Records {
    private String RuminantType ;
    private Double MilkYield;

    public void Assign_Ruminant(name , Sensors[] Sensors , String Charactestic , Boolean StatusActive , FeedingProgramme FeedingProgramme , String RuminantType , Double MilkYield ){
        super.Assign_LiveStrock(FeedingProgramme , name , Sensors , Charactestic , StatusActive );
        this.RuminantType = RuminantType ;
        this.MilkYield = MilkYield ;
    }
    public void DisplayRuminant(){
        super.DisplayLiveStrock();
        System.out.println("Ruminant Type : " + this.RuminantType + " - Milk Yield : " + this.MilkYield);
    }

    public void Assign_Ruminant_Attributes(String RuminantType ){
        this.RuminantType = RuminantType ;
    }
    public void Assign_Ruminant_Attributes(Double MilkYield ){
        this.MilkYield = MilkYield ;
    }
    public void record(String recordDate , double recordValue ){
        super.record(recordDate , this.MilkYield );
        super.recordWihda = "L" ;
        super.recordType = this.RuminantType.toString() ;
    }
   
   
}

public class Plitory extends LiveStrock,Records{
    private String PlitoryType ;
    private Double EggCount;

    public void Assign_Plitory(name , Sensors[] Sensors , String Charactestic , Boolean StatusActive , FeedingProgramme FeedingProgramme , String PlitoryType , Double EggCount ){
        super.Assign_LiveStrock(FeedingProgramme , name , Sensors , Charactestic , StatusActive );
        this.PlitoryType = PlitoryType ;
        this.EggCount = EggCount ;
    }
    public void DisplayPlitory(){
        super.DisplayLiveStrock();
        System.out.println("Plitory Type : " + this.PlitoryType + " - Egg Count : " + this.EggCount);
    }

    public void Assign_Plitory_Attributes(String PlitoryType ){
        this.PlitoryType = PlitoryType ;
    }
    public void Assign_Plitory_Attributes(Double EggCount ){
        this.EggCount = EggCount ;
    }
    public void record(String recordDate , double recordValue ){
        super.record(recordDate , this.EggCount );
        super.recordWihda = "Eggs" ;
        super.recordType = this.PlitoryType.toString() ;
    }
   
}

public abstract Aquaculture extends Zones,Records{
    protected  static int NB_ANIMALS = 0   ;
    protected Animals[] species ; 
    protected FeedingProgramme Fed_Programme  ;


   public void Assign_Aquaculture(name , Sensors[] Sensors , String Charactestic , Boolean StatusActive , FeedingProgramme FeedingProgramme ,Animals[] asnimals   ){
    super.AddZone(name,Sensors,Charactestic,StatusActive);
    this.Fed_Programme = FeedingProgramme ; 
    this.species = asnimals ; 
    this.NB_ANIMALS ++ ; 
   }

   public void DisplayAquaculture(){
    super.Display() ; 
    for(i=1 , i<= this.NB_ANIMALS , i++){
    species[i].displayAnimal() ; 
    }
    System.out.println("Nb Animals" + this.NB_ANIMALS) ; 
    
   }

   public void Assign_animals(Animals[] asnimals ){
    this.species[NB_ANIMALS] = asnimals ;
    this.NB_ANIMALS ++ ;
   }

   public assign_feeding_programme(FeedingProgramme FeedingProgramme ){
    this.Fed_Programme = FeedingProgramme ;
   }


   public double record_value(){
    double sumweight = 0 ;
    for(i=1 , i<= this.NB_ANIMALS , i++){
    sumweight += this.species[i].getWeight() ;
    }
    return sumweight ;

   }
   public void record(String recordDate , double recordValue ){
    super.record(recordDate , recordValue );
    super.recordWihda = "Kg" ;
    super.recordType = this.species[i].toString() ;
   }
}



//-------------------------------------//Enum parts-------------------------------------



public enum Health_status{ HEALTHY,SICK,QUARANTINE }
public enum GROWTH_STAGE{semis, germination, croissance, maturité, récolte}
public enum Sensorstatus{ACTIVE , SUSPENDED, FAULTY}
public enum Locations{CROP, LIVESTOCK, AQUACULTURE}
public enum CropsType{CERIAL,FRUITS,VEGETABLES}



//----------------------------------------------------------

public abstract class Records{
        protected static int counter = 1 ;
        protected final int recordId ;
        protected Date recordDate ; 
        protected double recordValue ; 
        protected String recordType ; 
        protected String recordWihda ; 

        public void record(String recordDate , double recordValue  ){
            this.recordId = counter++ ;
            this.recordDate = recordDate ;
            this.recordValue = recordValue ;
        }

        public void display_record(){
            System.out.println("RecordId : " + this.recordId + " - RecordDate : " + this.recordDate + " - RecordValue : " + this.recordValue + " - RecordType : " + this.recordType + " - RecordWihda : " + this.recordWihda);
        }






}