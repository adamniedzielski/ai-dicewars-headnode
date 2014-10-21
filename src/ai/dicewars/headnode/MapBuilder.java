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

		List<Integer> numberOfDices = new ArrayList<>();
		List<Integer> indexesToRemove = new ArrayList<>();
		List<List<Integer>> listsOfNeighbours = new ArrayList<>();
		List<List<Integer>> verticesFamily = new ArrayList<>();
		
		for(int i=1; i<15; i++){
			numberOfDices.set(i,1);
		}
		
		int sumOfDicesPlayer1 = 7;
		int sumOfDicesPlayer2 = 7;
		Random randomGenerator = new Random();

		while (sumOfDicesPlayer1 < 21) {
			for (int i = 0; i < 14; i++) {
				int randomInt = randomGenerator.nextInt(8)+1;
				if (numberOfDices.get(randomInt) < 8) {
					numberOfDices.set(randomInt,
							numberOfDices.get(randomInt) + 1);
					sumOfDicesPlayer1++;
				} else {
					i--;
				}
			}

		}
		
		while (sumOfDicesPlayer2 < 21) {
			for (int i = 0; i < 14; i++) {
				int randomInt = randomGenerator.nextInt(8) + 7;
				if (numberOfDices.get(randomInt) < 8) {
					numberOfDices.set(randomInt,
							numberOfDices.get(randomInt) + 1);
					sumOfDicesPlayer2++;
				} else {
					i--;
				}
			}

		}
		
		for(int i=1; i<15; i++){
			indexesToRemove.set(i, i);
		}
		int randomInt = randomGenerator.nextInt(14)+1;
		List<Integer> temp = new ArrayList<>();
		temp.add(1, randomInt);
		verticesFamily.add(temp);
		indexesToRemove.remove(randomInt);
		while(!indexesToRemove.isEmpty()){
			int numberOfChildren = randomGenerator.nextInt(5)+1;
			for(int i=0; i<numberOfChildren; i++){
				int randomChildren = randomGenerator.nextInt(indexesToRemove.size()-1) + 1;
				
			}
		}
		
		for (int i = 0; i < 7; i++) {
			vertices.add(new ConcreteVertex((i + 1), null, 1, 0));
		}
		for (int i = 7; i < 14; i++) {
			vertices.add(new ConcreteVertex((i + 1), null, 1, 1));
		}
		return vertices;
	}
}