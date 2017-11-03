
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Evolution {
	
	
	
	public Evolution(){
		
	}
	
	private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
	
	public ArrayList<Route> rankPopulation(ArrayList<Route> population){
		//System.out.println("Ranking population");
		int costs[] = new int[population.size()]; 
		ArrayList<Route> ranked = new ArrayList<Route>();
		
		for(int i=0; i<costs.length; i++){
			costs[i] = population.get(i).cost;
		}
		Arrays.sort(costs);
		
		for(int i=0; i<costs.length; i++){
			//System.out.println(costs[i]);
			//System.out.println("finding route");
			for(int j=0; j < population.size(); j++){
				if(costs[i] == population.get(j).cost){
					ranked.add(i,population.get(j));
										
				}
				//System.out.println(ranked.get(i).getId());

			}
			
		}
		
		
		return ranked;
	}
	
	public ArrayList<Location> mutateRoute(ArrayList<Location> route, int pressure){
		
		
		ArrayList<Location> mutant = new ArrayList<Location>();
		for(int i=0; i< route.size(); i++){
			mutant.add(route.get(i));
		}
		
		int aggustedPressure = pressure*40;
		
		for(int i=0; i<pressure-1; i++){
			
			int loc1 = getRandomNumberInRange(0,mutant.size()-1);
			int loc2 = getRandomNumberInRange(0,mutant.size()-1);
			
			//Collections.swap(mutant, loc1, loc2);
		
			Location tmp = mutant.get(loc1);
			mutant.set(loc1, mutant.get(loc2));
			mutant.set(loc2, tmp);
			
	
		}
		
		return mutant;
		
	}
	
	public void hillClimbing(ArrayList<Route> population, int pressure){
	
		boolean button = true;
		int genNo =1;
		while(button == true){
		
			
			for(int i=0; i<population.size(); i++){
				//Route current = route;
				ArrayList<Location> mutant = new ArrayList<Location>();
				mutant = mutateRoute(population.get(i).getRoute(),pressure);
				Route mutated = new Route(population.get(i).id, mutant );
				mutated.cost = mutated.getRouteCost(mutant);
				if(mutated.cost <100){
					System.out.println("boom");
					System.out.println(mutated.cost);
					button = false;
				}
				
				if(mutated.cost > 900){
					if(mutated.cost <= population.get(i).cost){
						population.set(i, mutated);
						
						//System.out.println(mutated.cost + " " + population.get(i).cost);
					}
				}
				
			
				
			}
		
		ArrayList<Route> ranking = rankPopulation(population);
		Route best = ranking.get(0);
		System.out.println("ID COST");
		System.out.println("Gen: "+ genNo + " " + best.getId() + " " + best.cost);
		
		//S
		//System.out.println(genSinceLastSwitch);
		genNo++;
			
		}	
	}
	
	public ArrayList<Location> mate(Route parent1, Route parent2){
		
		int size = parent1.getRoute().size();
		int cutoff = size/2;
		
		ArrayList<Location> childRoute = new ArrayList<Location>(size);
		for(int i=0; i<size;i++){
			if(i<cutoff){
				childRoute.add(parent1.getRoute().get(i));
			}
			else{
				childRoute.add(parent2.getRoute().get(i));
			}
			
		}
		
		
		
				
		return childRoute;
	}
	
	public void evolve(ArrayList<Route> population, int gen){
		boolean button = true;
		int genNo =0;
		
		while(button == true){
			
			if(genNo  == gen){
				button = true;
			}
			
			for(int i=0; i<population.size(); i++){
				//System.out.println("for loop");
				ArrayList<Route> selected = new ArrayList<Route>();
				selected = rankSelection(population);
			
					
			
				ArrayList<Location> mated = new ArrayList<Location>(selected.get(0).getRoute().size());
				mated = mate(selected.get(0), selected.get(1));
				
				
				
				Route child = new Route(population.get(i).id, mated); 
				
				System.out.println("Route: " + i + ", Sel1: " + selected.get(0).cost + ", Sel2: " + selected.get(1).cost + " CHILD: " + child.cost);
				if(child.cost < population.get(i).cost)
				{
					population.set(i,child);
					//System.out.println("add");
				}
				
				
			}
			
		
			ArrayList<Route> ranking = rankPopulation(population);
			Route best = ranking.get(0);
			System.out.println("ID COST");
			System.out.println(best.getId() + " " + best.cost);
			
			genNo++;
		}
	}
	
	
	
	
	public ArrayList<Route> rankSelection(ArrayList<Route> population){
		
		
		
		ArrayList<Route> ranking = new ArrayList<Route>();
		ranking = rankPopulation(population);
		int[] weights = new int[population.size()];
		int sum = 0;
		int rank = population.size();
		for(int i=0; i<population.size(); i++){
			weights[i] = rank;
			rank --;
			sum++;
		}
		
		ArrayList<Route> selected = new ArrayList<Route>(1);
		int count =0;
		while(count < 2){
		int randomNo = getRandomNumberInRange(0,sum);
		for(int i=0; i<ranking.size(); i++){
			randomNo = randomNo - weights[ranking.get(i).id];
			if(randomNo <= 0){
				selected.add(ranking.get(i));
				break;
			}
		}
		
		count ++;
	}
		
		return selected;	
		
	}
	
	public Route tournamentSelect(ArrayList<Route> population, int size){
		
		ArrayList<Route> tournament = new ArrayList<Route>();
		
		for(int i=0; i<size;i++){
			
			int randomNo = getRandomNumberInRange(0,population.size()-1);
			tournament.add(population.get(randomNo));
			
		}
		Route best = tournament.get(0);
		for(Route route : tournament){
			if(route.cost < best.cost){
				best = route;
			}
		}
		
		return best;
	}
	
	
	public void tournamentSSMutation(ArrayList<Route> population, int pressure, int gen) throws IOException{
		
		boolean termination = false;
		int genNo = 0;
		
		if(pressure > population.size()){
			throw new IOException("Pressure cant be larger than population size");
		}

		while(termination == false){
			
			
			if(genNo  == gen){
				termination = true;
			}
		
			
			Route selected = tournamentSelect(population, pressure);
			Route mutant = new Route(selected.id, mutateRoute(selected.getRoute(), 100));
			
			//.out.println(selected.cost + " " + mutant.cost);
			
			ArrayList<Route> ranking = rankPopulation(population);
			
			if(mutant.cost < ranking.get(ranking.size()-1).cost){
				System.out.println("Bam");
				for(int i=0; i<population.size(); i++){
					if(population.get(i).id == ranking.get(population.size()-1).id){
						population.set(i, mutant);
						System.out.println("Swap");
					}
				}
			}
			
			 ranking = rankPopulation(population);
			Route best = ranking.get(0);
			System.out.println("GEN ID COST");
			System.out.println(genNo + " " + best.getId() + " " + best.cost);
			
			
			
			genNo ++;
		}
		
		

	
	
	}
	
	
	

}
