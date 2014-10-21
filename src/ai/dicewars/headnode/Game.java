package ai.dicewars.headnode;

import java.util.List;
import java.util.Random;

import ai.dicewars.common.Agent;
import ai.dicewars.common.Answer;
import ai.dicewars.common.Vertex;
import ai.dicewars.headnode.exception.MapException;
import ai.dicewars.headnode.exception.MoveException;

public class Game {
	private List<ConcreteVertex> vertices;
	private Random rand = new Random();

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
				try{
					applyMove(answer);
				} catch(MoveException e){
					e.printStackTrace();
				}
			}
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
			
			if(currentAgentScore > oponentAgentScore){
				to.setNumberOfDices(from.getNumberOfDices() - 1);
				from.setNumberOfDices(1); 
				to.setPlayer(from.getPlayer());
			} else{
				from.setNumberOfDices(1); 
			}
				
		} catch (Exception e) {
			throw new MoveException(e);
		}
		/*
		 * TODO: this method should check if the move is valid then it should
		 * roll the dices, determine the winner and modify the game state
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
	
	private int getDiceRandom(){	
	    int randomNum = rand.nextInt((6 - 1) + 1) + 1;
	    return randomNum;
	}
	
	private int getRandomSum(int dices){
		int sum = 0;
		for(int i = 0; i<dices; i++)
			sum += getDiceRandom();
		return sum;
	}
	
	private void checkMoveLogic(ConcreteVertex from, ConcreteVertex to) throws MoveException{
		if(from.getNumberOfDices() == 1)
			throw new MoveException("Can not shift dice from field with one dice");
		
		try {
			checkMoveToplogy(from, to);
		} catch (MapException e) {
			throw new MoveException(e);
		}
	}
	
	private void checkMoveToplogy(ConcreteVertex from, ConcreteVertex to) throws MapException{
		//TODO no needed to check both ways
		if(!(from.getNeighbours().contains(to.getId()) && to.getNeighbours().contains(from.getId())))
			throw new MapException("Vertices " + from.getId() + " and " + to.getId() + " are not connected");
	}
	
	public ConcreteVertex getVertex(int vertexId){
		for(ConcreteVertex v : vertices)
			if(v.getId() == vertexId)
				return v;
		return null;
	}
}
