package ai.dicewars.headnode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import ai.dicewars.common.Agent;
import ai.dicewars.common.Answer;
import ai.dicewars.headnode.exception.MapException;
import ai.dicewars.headnode.exception.MoveException;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class Game {
	private static final int MAP_SIZE = 6;
	private List<ConcreteVertex> vertices;
	private Random rand = new Random();
	private int currentAgent;

	public void play() {
		/*
		 * TODO: change this to MapBuilder when it starts working again
		 */

		vertices = new MapBuilder().build();
		List<List<Integer>> connectionsOfVertices = createConnections(vertices);
		Graph graphOfMap = new SingleGraph("Dice Wars");
		createGraph(connectionsOfVertices, vertices, graphOfMap);
		// drawGraph(connectionsOfVertices, vertices);
		Agent agents[] = new Agent[2];
		agents[0] = new InteractiveAgent();
		agents[0].setPlayerNumber(0);
		agents[1] = new InteractiveAgent();
		agents[1].setPlayerNumber(1);

		currentAgent = 0;

		while (!isGameFinished()) {
			redrawGraph(connectionsOfVertices, vertices, graphOfMap);
			Answer answer = agents[currentAgent].makeMove(vertices);
			if (answer.isEmptyMove()) {
				addRandomDices();
				currentAgent = (currentAgent + 1) % 2;
			} else {
				try {
					applyMove(answer);
				} catch (MoveException e) {
					e.printStackTrace();
					return;
				}
			}
		}

		displayWinner();
	}

	private void displayWinner() {
		if (getPlayerZeroCount() == MAP_SIZE) {
			System.out.println("Player 0 won");
		} else {
			System.out.println("Player 1 won");
		}

	}

	private void addRandomDices() {
		List<ConcreteVertex> verticesOfCurrentAgent = new ArrayList<>();
		for (ConcreteVertex vertex : vertices) {
			if (vertex.getPlayer() == currentAgent) {
				verticesOfCurrentAgent.add(vertex);
			}
		}

		int numberOfExtraDices = new MaximumConnectedComponent(
				verticesOfCurrentAgent).calculate();

		for (int i = 0; i < numberOfExtraDices; i++) {
			ConcreteVertex selectedVertex = verticesOfCurrentAgent.get(rand
					.nextInt(verticesOfCurrentAgent.size()));
			selectedVertex
					.setNumberOfDices(selectedVertex.getNumberOfDices() + 1);
		}
	}

	private void applyMove(Answer answer) throws MoveException {
		if (answer.isEmptyMove())
			return;

		try {
			ConcreteVertex from = getVertex(answer.getFrom());
			ConcreteVertex to = getVertex(answer.getTo());

			checkMoveLogic(from, to);

			int currentAgentScore = getRandomSum(from.getNumberOfDices());
			int oponentAgentScore = getRandomSum(to.getNumberOfDices());

			if (currentAgentScore > oponentAgentScore) {
				to.setNumberOfDices(from.getNumberOfDices() - 1);
				from.setNumberOfDices(1);
				to.setPlayer(from.getPlayer());
			} else {
				from.setNumberOfDices(1);
			}

		} catch (Exception e) {
			throw new MoveException(e);
		}
	}

	private boolean isGameFinished() {
		int countPlayerZero = getPlayerZeroCount();

		if (countPlayerZero == 0 || countPlayerZero == MAP_SIZE)
			return true;
		else
			return false;
	}

	private int getPlayerZeroCount() {
		int countPlayerZero = 0;
		for (int i = 0; i < vertices.size(); i++) {
			if (vertices.get(i).getPlayer() == 0)
				countPlayerZero++;
		}
		return countPlayerZero;
	}

	private int getDiceRandom() {
		int randomNum = rand.nextInt((6 - 1) + 1) + 1;
		return randomNum;
	}

	private int getRandomSum(int dices) {
		int sum = 0;
		for (int i = 0; i < dices; i++)
			sum += getDiceRandom();
		return sum;
	}

	private void checkMoveLogic(ConcreteVertex from, ConcreteVertex to)
			throws MoveException {
		if (from.getNumberOfDices() == 1)
			throw new MoveException(
					"Can not shift dice from field with one dice");

		if (from.getPlayer() != currentAgent) {
			throw new MoveException(
					"You wanted to attack from field of your opponent");
		}

		if (to.getPlayer() == currentAgent) {
			throw new MoveException("You wanted to attack your own field");
		}

		try {
			checkMoveToplogy(from, to);
		} catch (MapException e) {
			throw new MoveException(e);
		}
	}

	private void checkMoveToplogy(ConcreteVertex from, ConcreteVertex to)
			throws MapException {
		if (!(from.getNeighbours().contains(to.getId()) && to.getNeighbours()
				.contains(from.getId())))
			throw new MapException("Vertices " + from.getId() + " and "
					+ to.getId() + " are not connected");
	}

	public ConcreteVertex getVertex(int vertexId) {
		for (ConcreteVertex v : vertices)
			if (v.getId() == vertexId)
				return v;
		return null;
	}

	public List<List<Integer>> createConnections(List<ConcreteVertex> vertices) {

		List<List<Integer>> connections = new ArrayList<>();

		for (ConcreteVertex vertex : vertices) {
			for (Integer neighbour : vertex.getNeighbours()) {
				if (connections.isEmpty()) {
					connections.add(new ArrayList<Integer>(Arrays.asList(
							vertex.getId(), neighbour)));
				} else {
					Boolean alreadyExist = false;
					for (int i = 0; i < connections.size(); i++) {
						if ((connections.get(i).get(0) == vertex.getId() && connections
								.get(i).get(1) == neighbour)
								|| ((connections.get(i).get(1) == vertex
										.getId() && connections.get(i).get(0) == neighbour))) {
							alreadyExist = true;
						}
					}
					if (!alreadyExist) {
						connections.add(new ArrayList<Integer>(Arrays.asList(
								vertex.getId(), neighbour)));
					}
				}
			}
		}

		return connections;
	}

	public void createGraph(List<List<Integer>> connections,
			List<ConcreteVertex> vertices, Graph graph) {

		
		Node n1 = graph.addNode("1");
		Node n2 = graph.addNode("2");
		Node n3 = graph.addNode("3");
		Node n4 = graph.addNode("4");
		Node n5 = graph.addNode("5");
		Node n6 = graph.addNode("6");
		Node n7 = graph.addNode("7");
		Node n8 = graph.addNode("8");
		Node n9 = graph.addNode("9");
		Node n10 = graph.addNode("10");
		Node n11 = graph.addNode("11");
		Node n12 = graph.addNode("12");
		Node n13 = graph.addNode("13");
		Node n14 = graph.addNode("14");

		n1.setAttribute("ui.label", "Id: 1 Dieces: "+vertices.get(1).getNumberOfDices());
		n2.setAttribute("ui.label", "Id: 2 Dieces: "+vertices.get(1).getNumberOfDices());
		n3.setAttribute("ui.label", "Id: 3 Dieces: "+vertices.get(1).getNumberOfDices());
		n4.setAttribute("ui.label", "Id: 4 Dieces: "+vertices.get(1).getNumberOfDices());
		n5.setAttribute("ui.label", "Id: 5 Dieces: "+vertices.get(1).getNumberOfDices());
		n6.setAttribute("ui.label", "Id: 6 Dieces: "+vertices.get(1).getNumberOfDices());
		n7.setAttribute("ui.label", "Id: 7 Dieces: "+vertices.get(1).getNumberOfDices());
		n8.setAttribute("ui.label", "Id: 8 Dieces: "+vertices.get(1).getNumberOfDices());
		n9.setAttribute("ui.label", "Id: 9 Dieces: "+vertices.get(1).getNumberOfDices());
		n10.setAttribute("ui.label", "Id: 10 Dieces: "+vertices.get(1).getNumberOfDices());
		n11.setAttribute("ui.label", "Id: 11 Dieces: "+vertices.get(1).getNumberOfDices());
		n12.setAttribute("ui.label", "Id: 12 Dieces: "+vertices.get(1).getNumberOfDices());
		n13.setAttribute("ui.label", "Id: 13 Dieces: "+vertices.get(1).getNumberOfDices());
		n14.setAttribute("ui.label", "Id: 14 Dieces: "+vertices.get(1).getNumberOfDices());
		
		graph.addAttribute(
				"ui.stylesheet",
				"node#'1' { shape: box; size: 30px, 30px; fill-color: red; text-size : 20;} node#'2' { shape: box; size: 30px, 30px; fill-color: red; text-size : 20;} node#'3' { shape: box; size: 30px, 30px; fill-color: red; text-size : 20;} node#'4' { shape: box; size: 30px, 30px; fill-color: red; text-size : 20;} node#'5' { shape: box; size: 30px, 30px; fill-color: red; text-size : 20;} node#'6' { shape: box; size: 30px, 30px; fill-color: red; text-size : 20;} node#'7' { shape: box; size: 30px, 30px; fill-color: red; text-size : 20;} node#'8' { shape: box; size: 30px, 30px; text-size : 20;} node#'9' { shape: box; size: 30px, 30px; text-size : 20;} node#'10' { shape: box; size: 30px, 30px; text-size : 20;} node#'11' { shape: box; size: 30px, 30px; text-size : 20;} node#'12' { shape: box; size: 30px, 30px; text-size : 20;} node#'13' { shape: box; size: 30px, 30px; text-size : 20;} node#'14' { shape: box; size: 30px, 30px; text-size : 20;}");

		for (int i = 0; i < connections.size(); i++) {
			graph.addEdge(connections.get(i).get(0).toString()
					+ connections.get(i).get(1).toString(), connections.get(i)
					.get(0).toString(), connections.get(i).get(1).toString());
		}

		graph.display();
	}

	public void redrawGraph(List<List<Integer>> connections,
			List<ConcreteVertex> vertices, Graph graph) {
		graph.display();
	}
}
