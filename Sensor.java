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
enum  typee{
    biometric,enviromental,soil,water
}
enum units{
C,acivity,Per,PH,rain
}   
interface activation{
    void changestatus(status status);
}
 class numuricalsensors extends Sensors{
 protected typee type;
 
    protected int value;
protected units unit;
protected boolean outofrange=false;
public numuricalsensors(int code, location location, int minthreshold, int maxthreshold, int value, units unit, typee type,boolean  outofrange ) {
        super(code, location, minthreshold, maxthreshold);
        this.value = value;
        //test threshold
        if(value<minthreshold || value>maxthreshold){
this.outofrange=true;
        }
        this.unit = unit;
        this.type = type;
     
    }
    public void setValue(int value) {
        this.value = value;
     
    }   
    public void setUnit(String unit) {
        this.unit = units.valueOf(unit);
    }
        public int getValue() {
            return value;
        }
        public units getUnit() {
            return unit;
        }
         public typee getType() {
            return type;
        }
    @Override
    public void displayinfo() {
        super.displayinfo();    
        System.out.println("value: "+value);
        System.out.println("unit: "+unit);
}

    
    
}
 class temperature extends numuricalsensors{
    public temperature(int code, location location, int minthreshold, int maxthreshold, int value, String unit,typee type,boolean outofrange ) {
        super(code, location, minthreshold, maxthreshold, value, units.C, type,outofrange);
    }

    
}
  class activitylevel extends numuricalsensors{
    public activitylevel(int code, location location, int minthreshold, int maxthreshold, int value, String unit,typee type,boolean outofrange) {
        super(code, location, minthreshold, maxthreshold, value, units.acivity, typee.biometric,outofrange);
    }}

 class humidity extends numuricalsensors{
    public humidity(int code, location location, int minthreshold, int maxthreshold, int value, String unit,typee type,boolean outofrange) {
        super(code, location, minthreshold, maxthreshold, value, units.C, typee.enviromental,outofrange);
    }
}
 class rainfall extends numuricalsensors{
    public rainfall(int code, location location, int minthreshold, int maxthreshold, int value, String unit,typee type,boolean outofrange) {
        super(code, location, minthreshold, maxthreshold, value, units.rain, typee.enviromental,outofrange);
    }
}
class soilmoisture extends numuricalsensors{
    public soilmoisture(int code, location location, int minthreshold, int maxthreshold, int value, String unit,typee type,boolean outofrange) {
        super(code, location, minthreshold, maxthreshold, value, units.Per, typee.soil,outofrange);
    }
}
class ph extends numuricalsensors{
    public ph(int code, location location, int minthreshold, int maxthreshold, int value, String unit,typee type,boolean outofrange) {
        super(code, location, minthreshold, maxthreshold, value, units.PH, typee.soil,outofrange);
    }
}
 class nitrogenlevel extends numuricalsensors{
    public nitrogenlevel(int code, location location, int minthreshold, int maxthreshold, int value, String unit,typee type,boolean outofrange) {
        super(code, location, minthreshold, maxthreshold, value, units.Per, typee.soil,outofrange);
    }
}

class dissolvedoxygen extends numuricalsensors{
    public dissolvedoxygen(int code, location location, int minthreshold, int maxthreshold, int value, String unit,typee type,boolean outofrange) {
        super(code, location, minthreshold, maxthreshold, value, units.Per, typee.water,outofrange);
    }
}


