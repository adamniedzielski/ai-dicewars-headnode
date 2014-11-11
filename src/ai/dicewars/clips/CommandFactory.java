package ai.dicewars.clips;

import java.util.List;

import ai.dicewars.common.Vertex;

public class CommandFactory {

	private static String getNeighbourFact(Integer vertex, Integer neighbour) {
		return "(vertex-neighbour (vertex " + vertex + ") (neighbour "
				+ neighbour + "))";
	}

	public static String getVerticesDeffacts(List<? extends Vertex> vertices) {
		String result = "(deffacts vertices\n";
		for (Vertex v : vertices) {
			for (Integer i : v.getNeighbours())
				result += getNeighbourFact(v.getId(), i) + "\n";
		}
		result += ")\n";

		return result;
	}

	public static String getVertexFact(int id, int numberOfDices, int player) {
		// TODO substite player with fact of me or enemy
		return "assert(vertex(id " + id + ") (dices " + numberOfDices
				+ ") (playerNumber " + player + "))";
	}

	public static String getNextMoveFact() {
		return "(find-all-facts ((?v next-move))(eq 1 1))";
	}

	public static String getMyPlayerDefglobal(int playerNumber) {
		return "(defglobal ?*myPlayerNumber* = " + playerNumber + ")";
	}
}
