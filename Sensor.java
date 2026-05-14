abstract class Sensors implements activation{
   protected  final int code;
 protected  location location;
protected int minthreshold;
protected int maxthreshold;
protected String name;
protected status status;
    public Sensors(int code, location location, int minthreshold, int maxthreshold) {
        this.code = code;
        this.location = location;
        this.minthreshold = minthreshold;
        this.maxthreshold = maxthreshold;
    }
  
    @Override
 public void changestatus(status status) {
      
    this.status=status;
    }

    public void setLocation(location location) {
        this.location = location;
    }
    
     public void setMinthreshold(int minthreshold) {
        this.minthreshold = minthreshold;
    }   
    public void setMaxthreshold(int maxthreshold) {
        this.maxthreshold = maxthreshold;
    }
     public void setName(String name) {
        this.name = name;
    }

    public int getMaxthreshold() {
        return maxthreshold;
    }
    public int getMinthreshold() {
        return minthreshold;
    }       
        public location getLocation() {
            return location;
        }
        public String getName() {
            return name;
        }
         public status getStatus() {
            return status;
        }
    public  void displayinfo(){
            System.out.println("code: "+code);
            System.out.println("location: "+location);
            System.out.println("minthreshold: "+minthreshold);
            System.out.println("maxthreshold: "+maxthreshold);
            System.out.println("name: "+name);
            System.out.println("status: "+status);
        }
    
}
 enum location{
grap,aquaculture,livestock
}
enum status{
    active,faulty,suspended
}
    
interface activation{
    void changestatus(status status);
}
 /*class numuricalsensors extends Sensors{
 protected int value;
protected String unit;
public numuricalsensors(int code, location location, int minthreshold, int maxthreshold, int value, String unit) {
        super(code, location, minthreshold, maxthreshold);
        this.value = value;
        this.unit = unit;
    }
    public void setValue(int value) {
        this.value = value;
    }   
    public void setUnit(String unit) {
        this.unit = unit;
    }
        public int getValue() {
            return value;
        }
        public String getUnit() {
            return unit;
        }
    @Override
    public void displayinfo() {
        super.displayinfo();    
        System.out.println("value: "+value);
        System.out.println("unit: "+unit);
}

    
    
}*/
class gpssensors extends Sensors{
int longitude;
    int latitude;
    public gpssensors(int code, location location, int minthreshold, int maxthreshold, int longitude, int latitude) {
        super(code, location, minthreshold, maxthreshold);
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }
        public int getLatitude() {
            return latitude;
        }
        public int getLongitude() {
            return longitude;
        }   

    @Override
    public void displayinfo() {
        super.displayinfo();
        System.out.println("longitude: "+longitude);
        System.out.println("latitude: "+latitude);  
    }
        

}

 class biometricsensors extends Sensors{
private int bodytemp;
private String bodytempunit;
    private int activitylevel;
    private String activityunit;

    public biometricsensors(int code, location location, int minthreshold, int maxthreshold, int value, String unit,int bodytemp,int activitylevel,String activityunit) {
        super(code, location, minthreshold, maxthreshold);
        this.activitylevel=activitylevel;
        this.activityunit=activityunit;
        this.bodytemp=bodytemp;
    }
public void setbodytemp(int bodytemp){
 this.bodytemp=bodytemp;
    }
public void setactivitylevel(int activitylevel){
    this.activitylevel=activitylevel;
}
public void setactivtyunit(String unity){
    activityunit=unity;
}
public int getbodytemp(){
    return this.bodytemp;
}
public int getactivitylevel(){
    return this.activitylevel;
}
public String getactivityunit(){
    return this.activityunit;
}

}
//lmra lifatt hbst hna
 class enviromental extends Sensors {
private int humidity;
private int temperature;
private  boolean rainfall;
public enviromental(int code, location location, int minthreshold, int maxthreshold, int value, String unit,int humidity,int temperature,boolean rainfall) {
        super(code, location, minthreshold, maxthreshold);
        this.humidity=humidity;
        this.temperature=temperature;
        this.rainfall=rainfall;
    }
public void setHumidity(int humidity){
    this.humidity=humidity; }
public void setTemperature(int temperature){
    this.temperature=temperature;
}
public void setRainfall(boolean rainfall){
    this.rainfall=rainfall;
}
public int getHumidity(){
    return this.humidity;
}
public int getTemperature(){
    return this.temperature;
}
public boolean isRainfall(){
    return this.rainfall;
}

}
 class soilsensors extends Sensors{
private int moisture;
private int ph;
private String nitrogenlevel;
public soilsensors(int code, location location, int minthreshold, int maxthreshold, int value, String unit, int moisture, int ph, String nitrogenlevel) {
    super(code, location, minthreshold, maxthreshold);
    this.moisture = moisture;
    this.ph = ph;
    this.nitrogenlevel = nitrogenlevel;
}
public void setMoisture(int moisture){
    this.moisture=moisture;
}
public void setPh(int ph){
    this.ph=ph;
}
public void setNitrogenlevel(String nitrogenlevel){
    this.nitrogenlevel=nitrogenlevel;
}
public int getMoisture(){
    return this.moisture;
}
public int getPh(){
    return this.ph;
}
public String getNitrogenlevel(){
    return this.nitrogenlevel;
}
}
 class watersensors extends Sensors{
private int temp;
private int dissolvedoxygen;
public watersensors(int code, location location, int minthreshold, int maxthreshold, int value, String unit, int temp, int dissolvedoxygen) {
    super(code, location, minthreshold, maxthreshold);
    this.temp = temp;
    this.dissolvedoxygen = dissolvedoxygen; }
public void setTemp(int temp){
    this.temp=temp;
}
public void setDissolvedoxygen(int dissolvedoxygen){
    this.dissolvedoxygen=dissolvedoxygen;
}   
public int getTemp(){
    return this.temp;
}
public int getDissolvedoxygen(){
    return this.dissolvedoxygen;
}
}   