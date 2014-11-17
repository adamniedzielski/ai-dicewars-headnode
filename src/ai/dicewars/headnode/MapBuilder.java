package ai.dicewars.headnode;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;

/*
 * This class should build random connected graph
 * it should also assign dices and players to vertices
 */

public class MapBuilder {

	public List<ConcreteVertex> build(int MapSize, int NrOfConn) {
		List<ConcreteVertex> vertices = new ArrayList<>();

		vertices.add(new ConcreteVertex(0, null, 0, 0));

		// initialize elements
		for (int i = 0; i < MapSize/2; i++) {
			vertices.add(new ConcreteVertex((i + 1), new ArrayList<Integer>(),
					1, 0));
		}
		for (int i = MapSize/2; i < MapSize; i++) {
			vertices.add(new ConcreteVertex((i + 1), new ArrayList<Integer>(),
					1, 1));
		}

		// set 1 dice for each field
		for (int i = 1; i < MapSize + 1; i++) {
			vertices.get(i).setNumberOfDices(1);
		}

		Random randomGenerator = new Random();

		// randomly set nr of dices on a field (player one)
		for (int i = 0; i < MapSize; i++) {
			int randomInt = randomGenerator.nextInt(MapSize/2) + 1;
			if (vertices.get(randomInt).getNumberOfDices() < 8) {
				vertices.get(randomInt).setNumberOfDices(
						vertices.get(randomInt).getNumberOfDices() + 1);
			} else {
				i--;
			}
		}

		// randomly set nr of dices on a field (player two)
		for (int i = 0; i < MapSize; i++) {
			int randomInt = randomGenerator.nextInt(MapSize/2) + MapSize/2 + 1;
			if (vertices.get(randomInt).getNumberOfDices() < 8) {
				vertices.get(randomInt).setNumberOfDices(
						vertices.get(randomInt).getNumberOfDices() + 1);
			} else {
				i--;
			}
		}

		// przechowuje indexy, kt�re usuwaj� si� gdy liczba zostanie wylosowana
		List<Integer> indexesToRemove = new ArrayList<>();
		// zapami�tuje kolejno�� w drzewie (do iterowania)
		List<Integer> treeBuilder = new ArrayList<>();

		for (int i = 0; i < MapSize; i++) {
			indexesToRemove.add(i + 1);
		}

		int randomParent = randomGenerator.nextInt(MapSize) + 1;

		treeBuilder.add(0, randomParent);
		indexesToRemove = elementRemover(randomParent - 1, indexesToRemove);

		for (int i = 0; i < treeBuilder.size(); i++) {
			randomParent = treeBuilder.get(i);
			int quantityOfChildren = randomGenerator.nextInt(2) + 1;
			for (int j = 0; j < quantityOfChildren; j++) {
				int randomChildren = randomGenerator.nextInt(indexesToRemove
						.size());
				treeBuilder.add(indexesToRemove.get(randomChildren));
				vertices.get(randomParent).addNeighbour(
						indexesToRemove.get(randomChildren));
				vertices.get(indexesToRemove.get(randomChildren)).addNeighbour(
						randomParent);
				indexesToRemove = elementRemover(randomChildren,
						indexesToRemove);
				if (indexesToRemove.isEmpty())
					break;
			}

			if (indexesToRemove.isEmpty())
				break;
		}

		//Creating additional random connections to create the map, not simple tree
		int randomConnectionsQuantity = randomGenerator.nextInt(NrOfConn);
		//int randomConnectionsQuantity = 9;
		for (int i = 0; i <= randomConnectionsQuantity; i++) {
			int randomVertex1 = randomGenerator.nextInt(MapSize) + 1;
			int randomVertex2 = randomGenerator.nextInt(MapSize) + 1;
			if (randomVertex1 == randomVertex2) {
				i--;
			} else {
				Boolean isConnected = false;
				for (int j = 0; j < vertices.get(randomVertex1).getNeighbours()
						.size(); j++) {
					if(vertices.get(randomVertex1).getNeighbours().get(j) == randomVertex2){
						isConnected = true;
						break;
					}
				}
				if(!isConnected){
					vertices.get(randomVertex1).addNeighbour(randomVertex2);
					vertices.get(randomVertex2).addNeighbour(randomVertex1);
				}
			}
		}
		vertices.remove(0);

		return vertices;
	}

	// removes element from IndexesToRemove and shifts the list
	public List<Integer> elementRemover(int id, List<Integer> IndexesToRemove) {
		for (int i = id; i < IndexesToRemove.size() - 1; i++) {
			IndexesToRemove.set(i, IndexesToRemove.get(i + 1));
		}
		IndexesToRemove.remove(IndexesToRemove.size() - 1);
		return IndexesToRemove;
	}

}
