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
	
	@Override 
	public String toString(){
		return "Vetex [ Id : " + getId() + " dices : " + getNumberOfDices() + " playerNumber : " + getPlayer() + " ]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result
				+ ((neighbours == null) ? 0 : neighbours.hashCode());
		result = prime * result + numberOfDices;
		result = prime * result + player;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConcreteVertex other = (ConcreteVertex) obj;
		if (id != other.id)
			return false;
// For our purpose no need to compare neighbors
//		if (neighbours == null) {
//			if (other.neighbours != null)
//				return false;
//		} else if (!neighbours.equals(other.neighbours))
//			return false;
		if (numberOfDices != other.numberOfDices)
			return false;
		if (player != other.player)
			return false;
		return true;
	}

}
