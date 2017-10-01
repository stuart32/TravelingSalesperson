import java.io.BufferedReader;
//import java.lang.Math.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class World {
	
	public World() {
		
	}
	
	
	
	//Reads the id, latitude and longitude data from a file and stores the data into an ArrayList of type Location. 
	public static ArrayList<Location> getLocations() throws IOException{
		
		File file = new File("src/data.txt");
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		ArrayList<Location> locations = new ArrayList<Location>();
		String line;
		boolean pause = true;
		
		try {
			
			
			while ((line = bufferedReader.readLine()) != null) {
				
				if(line.equals("NODE_COORD_SECTION")){
					line = bufferedReader.readLine();
					pause = false;
				}
				if(line.equals("EOF")){
					break;
				}
				if(pause == false){
					String[] words = new String[3];
					words = line.split(" ");
					Location location = new Location(Integer.parseInt(words[0]), Double.parseDouble(words[1]), Double.parseDouble(words[2]));
					locations.add(location);
					//System.out.println(words[0]);	
				}
			}
			fileReader.close();
			
			//System.out.println(locations.get(1).getId() + " " + locations.get(1).getLat() + " " + locations.get(1).getLong());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return locations;
	}
	
	
	//Initialises a new population of a certain size.
	public static ArrayList<ArrayList<Location>> initPop(int size){
		
		
		ArrayList<ArrayList<Location>> initialPop = new ArrayList<ArrayList<Location>>(size);
		
		for(int i=0; i<size-1; i++){
			ArrayList<Location> randomRoute = new ArrayList<Location>();
			try {
				randomRoute = shuffleList(getLocations());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			initialPop.add(randomRoute);
		}
		
		return initialPop;
		
	}
	
	
	
	//Shuffles an ArrayList of locations to an new random order. 
	 public static  ArrayList<Location> shuffleList(ArrayList<Location> a) {
		    int n = a.size();
		    Random random = new Random();
		    random.nextInt();
		    for (int i = 0; i < n; i++) {
		      int change = i + random.nextInt(n - i);
		      swap(a, i, change);
		    }
		    
		    return a;
		  }

	 
	 	//swaps two locations in an Array of Locations
		  private static void swap(ArrayList<Location> a, int i, int change) {
		    Location helper = a.get(i);
		    a.set(i, a.get(change));
		    a.set(change, helper);
		  }
		  
		  
		 
		  //Calculates the cost of travelling between location i and location j
		 public static int getTripCost(int i, int j, ArrayList<Location> dataset){
			
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
		 
		 
		 
		 public static int getRouteCost(ArrayList<Location> route){
			 
			 int location = 0;
			 int destination = location ++;
			 int cost = 0;
			 
			 for(int i=0; i<route.size(); i++){
				 cost =+ getTripCost(location, destination, route);
				 location ++;
				 destination ++;
			 }
			 return cost;
		 }
	
	
	
	public static void main(String [] args) throws IOException{
		ArrayList<ArrayList<Location>> population = new ArrayList<ArrayList<Location>>();
		population = initPop(20);
		
		//System.out.println(getTripCost(0,500, population.get(0)));
		//System.out.println(getTripCost(0,5, population.get(0)));
		
		for(int i=0; i< population.size()-1; i++)
		{
			getRouteCost(population.get(i));
		}
		
		
		/*
		for(int i=0; i<population.get(7).size(); i++){
			System.out.println(population.get(7).get(i).getId() + " " + population.get(7).get(i).getLat() + " " + population.get(7).get(i).getLong());
		}
		*/
		//System.out.println(new File(".").getAbsoluteFile());
		
	}

}
