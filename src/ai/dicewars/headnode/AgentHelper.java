package ai.dicewars.headnode;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import ai.dicewars.common.Answer;
import ai.dicewars.common.Vertex;

public class AgentHelper {

	private static AgentHelper instance;

	public static AgentHelper getInsetance() {
		if (instance == null)
			instance = new AgentHelper();

		return instance;
	}

	public Map<Integer, Vertex> getIdVertexMap(List<? extends Vertex> vertices) {
		Map<Integer, Vertex> map = new TreeMap<Integer, Vertex>();
		for (Vertex v : vertices) {
			map.put(v.getId(), v);
		}
		return map;
	}

	public int maximumConnectedVertices(List<? extends Vertex> vertices,
			int playerNumber) {
		int maximumConnectedVertices = 0;
		Map<Integer, Vertex> map = getIdVertexMap(vertices);
		for (Vertex v : vertices) {
			if (v.getPlayer() != playerNumber)
				continue;

			int traversedConnected = traverse(map, new HashSet<Integer>(), v,
					playerNumber);
			maximumConnectedVertices = traversedConnected > maximumConnectedVertices ? traversedConnected
					: maximumConnectedVertices;
		}

		return maximumConnectedVertices;
	}

	public int maximumConnectedVerticesAfterWinningField(
			List<? extends Vertex> vertices, Vertex startVertex,
			int playerNumber) {
		Map<Integer, Vertex> map = getIdVertexMap(vertices);
		int maximumConnectedVertices = maximumConnectedVertices(vertices,
				playerNumber);
		int traversedConnected = traverse(map, new HashSet<Integer>(),
				startVertex, playerNumber);
		maximumConnectedVertices = traversedConnected > maximumConnectedVertices ? traversedConnected
				: maximumConnectedVertices;

		return maximumConnectedVertices;
	}

	/*
	 * Counts the maximum of connected fields of player who is in possession of
	 * startVertex
	 */
	public int maximumConnectedVerticesAfterLoosingField(
			List<? extends Vertex> vertices, Vertex startVertex) {

		int previousPlayerNumber = startVertex.getPlayer();
		((ConcreteVertex) startVertex).setPlayer(-1);

		int maximumConnectedVertices = maximumConnectedVertices(vertices,
				previousPlayerNumber);

		((ConcreteVertex) startVertex).setPlayer(previousPlayerNumber);

		return maximumConnectedVertices;
	}

	private int traverse(Map<Integer, Vertex> verticesMap,
			Set<Integer> traversedVertices, Vertex startVertex, int playerNumber) {
		if (traversedVertices.contains(startVertex.getId()))
			return 0;

		int connectedVertices = 1;
		traversedVertices.add(startVertex.getId());
		for (Integer id : verticesMap.get(startVertex.getId()).getNeighbours()) {
			Vertex v = verticesMap.get(id);
			if (v.getPlayer() == playerNumber) {
				connectedVertices += traverse(verticesMap, traversedVertices,
						v, playerNumber);
			}
		}

		return connectedVertices;
	}

	public int oppositePlayerNumber(int playerNumber) {
		if (playerNumber == 0)
			return 1;
		else
			return 0;
	}

	// TODO move to tests
	public void testMe(List<ConcreteVertex> vertices, Answer answer) {
		int playerNumber = 0;
		System.out.println("Maximum connected vertices of ME : "
				+ maximumConnectedVertices(vertices, playerNumber));
		System.out.println("Maximum connected vertices of EN : "
				+ maximumConnectedVertices(vertices,
						oppositePlayerNumber(playerNumber)));
		if (!answer.isEmptyMove()) {
			System.out
					.println("Maximum connected vertices of ME after winning : "
							+ maximumConnectedVerticesAfterWinningField(
									vertices,
									getVertex(vertices, answer.getTo()),
									playerNumber)
							+ " "
							+ getVertex(vertices, answer.getTo()));
			// TODO this has no sense. Enemy should have same number of fields
			// if I loose
			System.out
					.println("Maximum connected vertices of EN after loosing: "
							+ maximumConnectedVerticesAfterLoosingField(
									vertices,
									getVertex(vertices, answer.getTo())) + " "
							+ getVertex(vertices, answer.getTo()));
		} else {
			System.out.println("Can't be tested for empty move");
		}
	}

	private Vertex getVertex(List<ConcreteVertex> vertices, int vertexId) {
		for (Vertex v : vertices)
			if (v.getId() == vertexId)
				return v;
		return null;
	}

	public Set<Answer> getAllPossibleMoves(List<Vertex> vertices, int playerNumber){
		Map<Integer, Vertex> map = getIdVertexMap(vertices);
		Set< Answer> possibleMoves = new HashSet<Answer>();
		
		for(Vertex v : vertices){
			ConcreteVertex from = (ConcreteVertex)v;
			for(int neighbourId : v.getNeighbours()){
				ConcreteVertex to = (ConcreteVertex)map.get(neighbourId);
				if(GameHelper.getInsetance().isMovePossible(from, to, playerNumber))
					possibleMoves.add(new ConcreteAnswer(false, from.getId(), to.getId()));
			}
		}
		
		possibleMoves.add(new ConcreteAnswer(true, 0, 0));
		
		return possibleMoves;
	}
}
