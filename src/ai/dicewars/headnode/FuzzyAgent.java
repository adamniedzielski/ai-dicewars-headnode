package ai.dicewars.headnode;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.rule.Variable;
import ai.dicewars.common.Agent;
import ai.dicewars.common.Answer;
import ai.dicewars.common.Vertex;
import ai.dicewars.sunday.ConcreteAnswer;

public class FuzzyAgent implements Agent {

	private int playerNumber;
	private List<? extends Vertex> vertices;

	@Override
	public Answer makeMove(List<? extends Vertex> vertices) {
		this.vertices = vertices;
		List<Vertex> move = firstPossibleMove();
		
		if (move == null) {
			return new ConcreteAnswer(true, 0, 0);
		}
		
		String fileName = "fcl/example.fcl";
		
		FIS fis = FIS.load(fileName, true);
        fis.setVariable("you", move.get(0).getNumberOfDices());
        fis.setVariable("enemy", move.get(1).getNumberOfDices());
        fis.evaluate();

        Variable success = fis.getVariable("success");

        if (success.getValue() > 5) {
        	return new ConcreteAnswer(false, move.get(0).getId(), move.get(1).getId());
        }
        else {
        	return new ConcreteAnswer(true, 0, 0);
        }
	}

	@Override
	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	private List<Vertex> firstPossibleMove() {
		for (Vertex vertex : vertices) {
			if (vertex.getPlayer() == this.playerNumber && vertex.getNumberOfDices() > 1) {
				for (Integer neighbourId : vertex.getNeighbours()) {
					Vertex neighbour = getVertex(neighbourId);
					if (neighbour.getPlayer() != this.playerNumber) {
						List<Vertex> result = new ArrayList<>();
						result.add(vertex);
						result.add(neighbour);
						return result;
					}
				}
			}
		}
		return null;
	}
	
	private Vertex getVertex(int vertexId) {
		for (Vertex v : vertices)
			if (v.getId() == vertexId)
				return v;
		return null;
	}
}
