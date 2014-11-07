package ai.dicewars.headnode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.rule.Variable;
import ai.dicewars.common.Agent;
import ai.dicewars.common.Answer;
import ai.dicewars.common.Vertex;
import ai.dicewars.sunday.ConcreteAnswer;

public class FuzzyAgent implements Agent {

	private int playerNumber;
	private List<? extends Vertex> vertices;
	private FIS fis;
	
	public FuzzyAgent(String filename) {
		fis = FIS.load(filename, true);
	}

	@Override
	public Answer makeMove(List<? extends Vertex> vertices) {
		this.vertices = vertices;
		
		TreeMap<Double, ConcreteAnswer> possibleMoves = new TreeMap<>();
		possibleMoves.put(0.0, new ConcreteAnswer(true, 0, 0));
		
		int yourTotalDices = 0;
		int enemyTotalDices = 0;
		int yourTotalVertices = 0;
		int enemyTotalVertices = 0;
		
		for (Vertex vertex : vertices) {
			if (vertex.getPlayer() == playerNumber) {
				yourTotalDices += vertex.getNumberOfDices();
				yourTotalVertices++;
			}
			else {
				enemyTotalDices += vertex.getNumberOfDices();
				enemyTotalVertices++;
			}
		}

		for (Vertex vertex : vertices) {
			if (vertex.getPlayer() == this.playerNumber && vertex.getNumberOfDices() > 1) {
				for (Integer neighbourId : vertex.getNeighbours()) {
					Vertex neighbour = getVertex(neighbourId);
					if (neighbour.getPlayer() != this.playerNumber) {
				        fis.setVariable("your_dices", vertex.getNumberOfDices());
				        fis.setVariable("enemy_dices", neighbour.getNumberOfDices());
				        fis.setVariable("your_total_dices", yourTotalDices);
				        fis.setVariable("enemy_total_dices", enemyTotalDices);
				        fis.setVariable("your_total_vertices", yourTotalVertices);
				        fis.setVariable("enemy_total_vertices", enemyTotalVertices);
				        fis.setVariable("your_connections", vertex.getNeighbours().size());
				        fis.setVariable("enemy_connections", neighbour.getNeighbours().size());
				        fis.setVariable("maximum_enemy_support", neighbourEnemyWithMaximumDices(neighbour));
				        fis.setVariable("your_maximum_threat", neighbourEnemyWithMaximumDices(vertex));
				        fis.evaluate();
				        Variable success = fis.getVariable("success");						
						
						possibleMoves.put(success.getValue(), new ConcreteAnswer(false, vertex.getId(), neighbourId));
					}
				}
			}
		}
		
		return possibleMoves.lastEntry().getValue();
	}

	private double neighbourEnemyWithMaximumDices(Vertex vertex) {
		double max = 0;
		for (Integer neighbourId : vertex.getNeighbours()) {
			Vertex neighbour = getVertex(neighbourId);
			if (neighbour.getPlayer() != playerNumber) {
				max = Math.max(max, neighbour.getNumberOfDices());
			}
		}
		return max;
	}

	@Override
	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}
	
	private Vertex getVertex(int vertexId) {
		for (Vertex v : vertices)
			if (v.getId() == vertexId)
				return v;
		return null;
	}
}
