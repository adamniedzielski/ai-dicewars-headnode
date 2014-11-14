package ai.dicewars.clips;

import java.util.List;

import ai.dicewars.common.Agent;
import ai.dicewars.common.Answer;
import ai.dicewars.common.Vertex;
import ai.dicewars.headnode.InteractiveAgent;
import ai.dicewars.headnode.exception.ClipsException;

public class ClipsAgent implements Agent {
	private boolean initated = false;
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
	// TODO pass agent number here and then use it to create fact of me and
	// enemy
	public ClipsAgent(String filename) {
		this.rulesFileName = filename;
	}

	@Override
	public Answer makeMove(List<? extends Vertex> vertices) {
		this.vertices = vertices;
		try {
			if (!initated)
				init();

			clipsFacade.reset();

			clipsFacade.transferMapState(this.vertices);
			clipsFacade.run();
			return clipsFacade.loadNextMove();
		} catch (ClipsException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void init() throws ClipsException {
		clipsFacade = new ClipsFacade();
		clipsFacade.createMapTopology(this.vertices);
		clipsFacade.definePlayer(this.playerNumber);

		// This is a file that you provide
		clipsFacade.loadFile(rulesFileName);

		initated = true;
	}

	public void destroy() {
		clipsFacade.destroy();
	}

	@Override
	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

}
