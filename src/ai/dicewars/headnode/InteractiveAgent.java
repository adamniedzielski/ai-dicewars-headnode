package ai.dicewars.headnode;

import java.util.List;
import java.util.Scanner;

import ai.dicewars.common.Agent;
import ai.dicewars.common.Answer;
import ai.dicewars.common.Vertex;

public class InteractiveAgent implements Agent {

	protected int playerNumber;
	protected List<? extends Vertex> vertices;

	@Override
	public Answer makeMove(List<? extends Vertex> vertices) {
		this.vertices = vertices;
		printMap();
		System.out.println("Player " + playerNumber + " make move!");
		Scanner in = new Scanner(System.in);
		int from = in.nextInt();

		if (from == 0) {
			return new ConcreteAnswer(true, 0, 0);
		} else {
			int to = in.nextInt();
			return new ConcreteAnswer(false, from, to);
		}
	}

	@Override
	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	private void printMap() {
		for (Vertex vertex : vertices) {
			System.out.println("ID: " + vertex.getId());
			System.out.println("Player: " + vertex.getPlayer());
			System.out.println("Number of dices: " + vertex.getNumberOfDices());
			System.out.println("Connections: " + vertex.getNeighbours());
			System.out.println("###########");
		}
	}
}
