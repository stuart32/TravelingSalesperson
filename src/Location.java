
public class Location {
	
	int id;
	double Lat;
	double Long;
	
	public Location(int id, double Lat, double Long){
		this.id = id;
		this.Lat = Lat;
		this.Long = Long;	
	}
	
	public int getId(){
		
		return this.id;
	}
	
	public double getLat(){
		return this.Lat;
	}
	
	public double getLong(){
		return this.Long;
	}

}
