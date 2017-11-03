import java.util.ArrayList;

public class Route {
	

	int id;
	int cost;
	
	ArrayList<Location> route;
	public Route(int id,ArrayList<Location> route){
		this.id = id;
		this.route = route;
		this.cost = getRouteCost(route);
		
	}
	
	public int getId(){
		
		return this.id;
	}
	
	public ArrayList<Location> getRoute(){
		return this.route;
	}
	
	
	//Calculates the cost of travelling between location i and location j
	 public  int getTripCost(int i, int j, ArrayList<Location> dataset){
		
		 double lati, longi, latj, longj;
		 double q1,q2,q3,q4,q5;
		 
		 lati = Math.PI * (dataset.get(i).getLat())/180;
		 longi = Math.PI * (dataset.get(i).getLong())/180;
		 latj = Math.PI * (dataset.get(j).getLat())/180;
		 longj = Math.PI * (dataset.get(j).getLong())/180;
		 
		 q1 = Math.cos(latj) * Math.sin(longi - longj);
		 q3 = Math.sin((longi - longj)/2);
		 q4 = Math.cos((longi - longj)/2);
		 q2 = (Math.sin(lati + latj) * q3 * q3) - (Math.sin(lati - latj) * q4 *q4);
		 q5 = (Math.cos(lati - latj) * q4 *q4) - (Math.cos(lati + latj) * q3 * q3);
		
		return (int)(6378388.0 * Math.atan2(Math.sqrt((q1*q1)+(q2*q2)), q5) + 1);
		 
	 }
	 
	 public static Route findRouteFromId(ArrayList<Route> population,int id){
			Route found = null;
			for(int i=0; i<population.size();i++){
				if(population.get(i).getId() == id){
					found = population.get(i);
				}
			}
			return found;
		}
	 
	 
	 
	 public  int getRouteCost(ArrayList<Location> route){
		 
		 //ArrayList<Location> route = this.route;
		 int location = 0;
		 int destination = location + 1;
		 int cost = 0;
		 
		 for(int i=0; i<route.size()-1; i++){
			 //System.out.println(location + " " + destination);
			 cost =+ getTripCost(location, destination, route);
			 location ++;
			 destination ++;
			 
		 }
		 
		 
		 return cost;
	 }
	 
	 public void setRoute(ArrayList<Location> newRoute){
		 this.route = newRoute;
		 this.cost = getRouteCost(newRoute);
	 }

	 
	 


}
