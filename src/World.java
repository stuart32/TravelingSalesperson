import java.io.BufferedReader;
//import java.lang.Math.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class World {
	public int locations = 8245;
	
	public World(){
		
	}
	
	
	
	
	
	//Reads the id, latitude and longitude data from a file and stores the data into an ArrayList of type Location. 
	public static ArrayList<Location> getLocations() throws IOException{
		
		System.out.println("Importing locations from file...");
		
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
	public static ArrayList<Route> initPop(int size){
		
		System.out.println("Initialising population of size: " + size);
		ArrayList<Route> initialPop = new ArrayList<Route>(size);
		
		for(int i=0; i<=size-1; i++){
			ArrayList<Location> randomRoute = new ArrayList<Location>();
			try {
				randomRoute = shuffleList(getLocations());
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Route newRoute = new Route(i,randomRoute);
			initialPop.add(newRoute);
				
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
		  
		  
		 
		 
	
	
	
	public static void main(String [] args) throws IOException{
		ArrayList<Route> population = new ArrayList<Route>();
		population = initPop(10);
		Evolution e = new Evolution();
		
		ArrayList<Route> ranked = new ArrayList<Route>();
		ranked = e.rankPopulation(population);
		
		for(Route route : ranked){
			System.out.println(route.cost);
		}
	
		
		/*
		ArrayList<Route> ranked = new ArrayList<Route>();
		ranked = e.rankPopulation(population);
		
		ArrayList<Route> n = new ArrayList<Route>();
		n = e.rankSelection(population);
		
		ArrayList<Location> mutant = new ArrayList<Location>();
		mutant = e.mate(n.get(0), n.get(1));
				
		Route child = new Route(100, mutant);
		
		
		
		child.setRoute(e.mate(n.get(0), n.get(1)));
		for(int i=0; i<child.getRoute().size();i++){
			System.out.println(i + ": " + n.get(0).getRoute().get(i).id + " " + n.get(1).getRoute().get(i).id + " " + child.getRoute().get(i).id);
		
		}
		
		int size = n.get(0).getRoute().size();
		System.out.println(' ');
		
		System.out.println(n.get(0).cost + " " + n.get(1).cost);
		System.out.println(n.get(0).getRoute().size() + " " + n.get(1).getRoute().size() + " " +  child.getRoute().size());
		
		System.out.println(n.get(0).getRoute().get(0).id + " " +          n.get(1).getRoute().get(0).id + " " + child.getRoute().get(0).id);
		System.out.println(n.get(0).getRoute().get(size/2).id + " " + n.get(1).getRoute().get(size/2).id + " " + child.getRoute().get(size/2).id);
		System.out.println(n.get(0).getRoute().get(size-1).id + " " +   n.get(1).getRoute().get(size-1).id + " " + child.getRoute().get(size-1).id);
		
		System.out.println(child.id + " " + child.cost);
		
		System.out.println("pop");
		for(Route route : ranked){
			System.out.println(route.id + " " + route.cost);
		}
		System.out.println("sel");
		for(Route route : n){
			System.out.println(route.id + " " + route.cost);
		}
		
		System.out.println(child.getRouteCost(mutant));
		*/
		
		/*
		for(int i=0; i<child.getRoute().size();i++){
			System.out.println(n.get(0).getRoute().get(i).id + " " + n.get(1).getRoute().get(i).id + " " + child.getRoute().get(i).id);
		}*/
	
	
		
		
		//e.evolve(population);
		//e.tournamentSSMutation(population, 2);
		
		e.hillClimbing(population, 50);
		
		
		
		
		
		
		
		
		/*
		System.out.println("ID COST");
		for(int i=0;i<population.size()-1; i++){
			
			System.out.println(population.get(i).getId() + " "+ population.get(i).getRouteCost());
		}
		*/
		/*
		ArrayList<Route> ranked = e.rankPopulation(population);
		//int[] ranked = e.rankPopulation(population);
		for(int i=0; i<ranked.size(); i++){
			System.out.println(ranked.get(i).getId() + " " + ranked.get(i).cost);
		}
		
		*/
	
		
	
		
		
	}

}
