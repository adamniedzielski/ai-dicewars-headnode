package ai.dicewars.headnode;

import java.util.List;

import ai.dicewars.common.Agent;
import ai.dicewars.common.Answer;
import ai.dicewars.common.Vertex;

public class Game {
	private List<Vertex> vertices;

	public void play() {
		vertices = new MapBuilder().build();
		Agent agents[] = new Agent[2];
		agents[0] = new InteractiveAgent();
		agents[0].setPlayerNumber(0);
		agents[1] = new InteractiveAgent();
		agents[1].setPlayerNumber(1);
		
		int currentAgent = 0;
		
		while(!isGameFinished()) {
			Answer answer = agents[currentAgent].makeMove(vertices);
			if (answer.isEmptyMove()) {
				currentAgent = (currentAgent + 1) % 2;
			}
			else {
				applyMove(answer);
			}
		}
	}
	
	private void applyMove(Answer answer) {
		/*
		 * TODO: this method should check if the move is valid
		 * then it should roll the dices, determine the winner and modify the game state
		 */
	}

	private boolean isGameFinished() {
		/*
		 * TODO: this should have real implementation
		 */
		int countPlayerOne = 0;
		for (int i=0; i< vertices.size(); i++){
			if(vertices.get(i).getPlayer() == 0)
				countPlayerOne ++;
		}
		
		if (countPlayerOne ==0 || countPlayerOne==14)
			return true;
		else return false;
	}
}
