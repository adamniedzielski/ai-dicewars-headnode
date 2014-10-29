package ai.dicewars.headnode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ai.dicewars.common.Vertex;

import java.util.Random;

/*
 * This class should build random connected graph
 * it should also assign dices and players to vertices
 */

public class MapBuilder {

	public List<ConcreteVertex> build() {
		List<ConcreteVertex> vertices = new ArrayList<>();

		/*
		 * Vertex first = new ConcreteVertex(1, Arrays.asList(2), 4, 0); Vertex
		 * second = new ConcreteVertex(2, Arrays.asList(1), 6, 1);
		 * vertices.add(first); vertices.add(second);
		 */
		
		//initialize elements
		for (int i = 0; i < 7; i++) {
			vertices.add(new ConcreteVertex((i + 1), new ArrayList<Integer>(), 1, 0));
		}
		for (int i = 7; i < 14; i++) {
			vertices.add(new ConcreteVertex((i + 1), new ArrayList<Integer>(), 1, 1));
		}
		
		vertices.add(new ConcreteVertex(0, null, 0, 0));
		
		//set 1 dice for each field
				for(int i=1; i<15; i++){
					vertices.get(i).setNumberOfDices(1);
				}
				
		
	
		Random randomGenerator = new Random();

		//randomly set nr of dices on a field (player one)
		for (int i = 0; i < 14; i++) {
			int randomInt = randomGenerator.nextInt(7)+1;
			if (vertices.get(randomInt).getNumberOfDices() < 8) {
				vertices.get(randomInt).setNumberOfDices(vertices.get(randomInt).getNumberOfDices()+1);
			} else {
				i--;
			}
		}
		
		//randomly set nr of dices on a field (player two)
		for (int i = 0; i < 14; i++) {
			int randomInt = randomGenerator.nextInt(7)+7;
			if (vertices.get(randomInt).getNumberOfDices() < 8) {
				vertices.get(randomInt).setNumberOfDices(vertices.get(randomInt).getNumberOfDices()+1);
			} else {
				i--;
			}
		}

		
		//przechowuje indexy, które usuwaj¹ siê gdy liczba zostanie wylosowana
		List<Integer> indexesToRemove = new ArrayList();
		//zapamiêtuje kolejnoœæ w drzewie (do iterowania)
		List<Integer> treeBuilder = new ArrayList<>();
				
		
		for(int i=0; i<14; i++){
			indexesToRemove.add(i+1);
		}
		
		int randomParent = randomGenerator.nextInt(14)+1;
		
		treeBuilder.add(0, randomParent);
		indexesToRemove = elementRemover(randomParent, indexesToRemove);
		
		for(int i = 0; i < treeBuilder.size(); i++){
			randomParent = treeBuilder.get(i);
			int quantityOfChildren = randomGenerator.nextInt(5)+1;
			for(int j=0; j<quantityOfChildren; j++){
				int randomChildren = randomGenerator.nextInt(indexesToRemove.size());
				treeBuilder.add(indexesToRemove.get(randomChildren));
				vertices.get(randomParent).addNeighbour(indexesToRemove.get(randomChildren));
				indexesToRemove = elementRemover(randomChildren, indexesToRemove);
				if(indexesToRemove.isEmpty()) break;
			}
			
			if(indexesToRemove.isEmpty()) break;
		}
			
		
		return vertices;
	}
	
	
	//removes element from IndexesToRemove and shifts the list
	List<Integer> elementRemover(int id, List<Integer> IndexesToRemove){
		for (int i=id; i<IndexesToRemove.size()-1; i++){
			IndexesToRemove.set(id, IndexesToRemove.get(id+1));
		}
		IndexesToRemove.remove(IndexesToRemove.size()-1);
		return IndexesToRemove;
	}
	 
}
