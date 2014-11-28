package ai.dicewars.headnode;

import ai.dicewars.headnode.exception.MapException;
import ai.dicewars.headnode.exception.MoveException;

public class GameHelper {

	private static GameHelper instance;
	public static GameHelper getInsetance(){
		if(instance == null)
			instance = new GameHelper();
		
		return instance;
	}
	
	public boolean isMovePossible(ConcreteVertex from, ConcreteVertex to, int playerNumber){
		try{
			checkMoveLogic(from, to, playerNumber);
		} catch (MoveException e){
			return false;
		}
		return true;
	}
	
	public void checkMoveLogic(ConcreteVertex from, ConcreteVertex to, int playerNumber)
			throws MoveException {
		if (from.getNumberOfDices() == 1)
			throw new MoveException(
					"Can not shift dice from field with one dice");

		if (from.getPlayer() != playerNumber) {
			throw new MoveException(
					"You wanted to attack from field of your opponent");
		}

		if (to.getPlayer() == playerNumber) {
			throw new MoveException("You wanted to attack your own field");
		}

		try {
			checkMoveToplogy(from, to);
		} catch (MapException e) {
			throw new MoveException(e);
		}
	}

	private void checkMoveToplogy(ConcreteVertex from, ConcreteVertex to)
			throws MapException {
		if (!(from.getNeighbours().contains(to.getId()) && to.getNeighbours()
				.contains(from.getId())))
			throw new MapException("Vertices " + from.getId() + " and "
					+ to.getId() + " are not connected");
	}
}
