package ai.dicewars.headnode;

import java.util.List;

import ai.dicewars.common.Vertex;

public class ConcreteVertex implements Vertex {

	private int id;
	private List<Integer> neighbours;
	private int numberOfDices;
	private int player;
	
	public ConcreteVertex(int id, List<Integer> neighbours, int numberOfDices, int player) {
		this.id = id;
		this.neighbours = neighbours;
		this.numberOfDices = numberOfDices;
		this.player = player;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public List<Integer> getNeighbours() {
		return neighbours;
	}

	@Override
	public int getNumberOfDices() {
		return numberOfDices;
	}

	@Override
	public int getPlayer() {
		return player;
	}

	public void setNumberOfDices(int numberOfDices) {
		this.numberOfDices = numberOfDices;
	}
	
	public void setPlayer(int player) {
		this.player = player;
	}

	public void addNeighbour(int neighbour) {
		this.neighbours.add(neighbour);
	}

}
