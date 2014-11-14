package ai.dicewars.headnode;

import java.util.ArrayList;
import java.util.List;

import ai.dicewars.clips.ClipsAgent;
import ai.dicewars.common.Agent;

public class Main {

	public static void main(String[] args) {
		/*
		 * TODO: write code for tournament
		 * tournament should:
		 * - take list of agents
		 * - divide them into random pairs
		 * - run a couple of games
		 * - divide them into other random pairs
		 * - ...
		 * - display stats which agent is the best
		 */
		
		List<Agent> agents = new ArrayList<>();
		agents.add(new ClipsAgent("rules-agent1.clp"));
		agents.add(new FuzzyAgent("fcl/example.fcl"));
		
		Game game = new Game();
		game.play(agents.get(0), agents.get(1));
	}
}
