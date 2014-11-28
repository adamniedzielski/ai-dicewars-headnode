package ai.dicewars.clips;

import java.util.List;

import ai.dicewars.common.Agent;
import ai.dicewars.common.Answer;
import ai.dicewars.common.Vertex;
import ai.dicewars.headnode.INamedAgent;
import ai.dicewars.headnode.exception.ClipsException;

public class ClipsAgent implements Agent, INamedAgent {
	private ClipsFacade clipsFacade;
	private String rulesFileName;
	private int playerNumber;
	private List<? extends Vertex> vertices;

	/**
	 * @param filename
	 *            The file with clips rules provided by user. As the outcome of
	 *            (run) there must be inserted a next-move fact, which indicates
	 *            the next move to be taken by this agent.
	 */

	public ClipsAgent(String filename) {
		this.rulesFileName = filename;
	}

	@Override
	public Answer makeMove(List<? extends Vertex> vertices) {
		this.vertices = vertices;
		try {
			clipsFacade = new ClipsFacade();
			clipsFacade.createMapTopology(this.vertices);
			clipsFacade.definePlayer(this.playerNumber);
			clipsFacade.loadFile(rulesFileName);
			
			clipsFacade.reset();
			clipsFacade.transferMapState(this.vertices);
			clipsFacade.run();
			Answer answer = clipsFacade.loadNextMove();
//			System.out.println(answer.isEmptyMove());
			clipsFacade.destroy();

			return answer;
		} catch (ClipsException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
    }

	@Override
	public String getAgentUniqueName() {
		return "ClipsAgent - " + rulesFileName;
	}

}
