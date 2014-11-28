package ai.dicewars.headnode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import ai.dicewars.clips.ClipsAgent;
import ai.dicewars.common.Agent;
import ai.dicewars.common.Answer;
import ai.dicewars.common.Vertex;
import ai.dicewars.headnode.exception.MoveException;

public class Game {
	private static final int MAP_SIZE = 14;
	private static final int MAXIMUM_SUBSEQUENT_EMPTY_MOVES = 100;
	private List<ConcreteVertex> vertices;
	private List<ConcreteVertex> clonedVertices;
	private Random rand = new Random();
	private int currentAgent;
	private Graph graphOfMap;
	public String statistics;

	public void play(Agent firstAgent, Agent secondAgent) {

		vertices = new MapBuilder().build(MAP_SIZE, 2);
		clonedVertices = cloneVertices(vertices);

		createGraph();
		
		Agent agents[] = new Agent[]{firstAgent, secondAgent};
		agents[0].setPlayerNumber(0);
		agents[1].setPlayerNumber(1);

		gameLoop(agents);

		// 2nd game - the same map - opposite situation
		vertices = clonedVertices;
		
		agents = new Agent[]{secondAgent, firstAgent};
		agents[0].setPlayerNumber(0);
		agents[1].setPlayerNumber(1);
		
		
		gameLoop(agents);
		redrawGraph();

		destroyAgents(agents);

	}

	private List<ConcreteVertex> cloneVertices(List<ConcreteVertex> vertices) {
		List<ConcreteVertex> vertices2ndGame = new ArrayList<ConcreteVertex>();
		for (Vertex v : vertices) {
			vertices2ndGame.add(new ConcreteVertex(v.getId(),
					v.getNeighbours(), v.getNumberOfDices(), v.getPlayer()));
		}

		return vertices2ndGame;
	}

	private void gameLoop(Agent[] agents) {
		int subsequent_empty_moves = 0;
		boolean draw = false;
		currentAgent = 0;

		while (!isGameFinished()) {
			try {
				redrawGraph();
				Answer answer = agents[currentAgent].makeMove(vertices);

				if (answer.isEmptyMove()) {
					subsequent_empty_moves++;
					addRandomDices();
					currentAgent = (currentAgent + 1) % 2;
				} else {

					applyMove(answer);
					subsequent_empty_moves = 0;
				}

				if (subsequent_empty_moves == MAXIMUM_SUBSEQUENT_EMPTY_MOVES) {
					draw = true;
					break;
				}
			} catch (MoveException e) {
				e.printStackTrace();
				return;
			}
		}
		redrawGraph();
		displayWinner(draw, agents);

		if (getPlayerZeroCount() == MAP_SIZE) {
			addStatsResult(false);
		} else {
			addStatsResult(true);
		}
	}

	private void displayWinner(boolean draw, Agent[] agents) {
		if (draw) {
			System.out.println("Draw after " + MAXIMUM_SUBSEQUENT_EMPTY_MOVES
					+ " empty movements");
			return;
		}
		int winnerPlayerNumber = getWinnerPlayerNumber();
		if (agents[winnerPlayerNumber] instanceof INamedAgent)
			System.out.println("Won :" + ((INamedAgent) agents[0]).getAgentUniqueName());
		else
			System.out.println("Player " + winnerPlayerNumber + " won");
	}

	private int getWinnerPlayerNumber(){
		if (getPlayerZeroCount() == MAP_SIZE) 
			return 0;
		else if(getPlayerZeroCount() == 0)
			return 1;
		else 
			return -1;
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

			if (selectedVertex.getNumberOfDices() < 8) {
				selectedVertex.setNumberOfDices(selectedVertex
						.getNumberOfDices() + 1);
			}
		}
	}

	private void applyMove(Answer answer) throws MoveException {
		if (answer.isEmptyMove())
			return;

		try {
			ConcreteVertex from = getVertex(answer.getFrom());
			ConcreteVertex to = getVertex(answer.getTo());

			GameHelper.getInsetance().checkMoveLogic(from, to, currentAgent);

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

		if (countPlayerZero == 0 || countPlayerZero == MAP_SIZE) {
			return true; 
		} else {
			return false;
		}

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



	private ConcreteVertex getVertex(int vertexId) {
		for (ConcreteVertex v : vertices)
			if (v.getId() == vertexId)
				return v;
		return null;
	}

	private List<List<Integer>> createConnections() {

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

	private void createGraph() {
		List<List<Integer>> connections = createConnections();
		graphOfMap = new SingleGraph("Dice Wars");

		for (int i = 1; i <= MAP_SIZE; i++) {
			graphOfMap.addNode(Integer.toString(i));
		}

		updateLabels();

		graphOfMap
				.addAttribute(
						"ui.stylesheet",
						"node { shape: box; size: 30px, 30px; text-size : 20; fill-mode: dyn-plain; fill-color: green, red;}");

		for (int i = 0; i < connections.size(); i++) {
			graphOfMap.addEdge(connections.get(i).get(0).toString()
					+ connections.get(i).get(1).toString(), connections.get(i)
					.get(0).toString(), connections.get(i).get(1).toString());
		}

		graphOfMap.display();
	}

	private void updateLabels() {
		for (int i = 0; i < MAP_SIZE; i++) {
			Node node = graphOfMap.getNode(i);
			node.setAttribute("ui.label", "Id: " + (i + 1) + " Dices: "
					+ vertices.get(i).getNumberOfDices());
			node.setAttribute("ui.color", vertices.get(i).getPlayer());
		}
	}

	public void redrawGraph() {
		updateLabels();
	}
	
	public void addStatsPlayers(int player0, int player1){
		statistics = statistics + "\r\n" + player0 + "," + player1;
	}
	public void addStatsResult(boolean variant){
		if(variant == true){
			statistics = statistics + ",1,0";
		}else{
			statistics = statistics + ",0,1";
		}
	}

	/*
	 * Clips environment must be destroyed to prevent memory leak through JNI
	 * allocations
	 */
	private void destroyAgents(Agent[] agents) {
		for (Agent a : agents) {
			if (a instanceof ClipsAgent)
				((ClipsAgent) a).destroy();
		}
	}
}
