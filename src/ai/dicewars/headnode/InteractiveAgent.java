package ai.dicewars.headnode;

import java.util.List;
import java.util.Scanner;

import ai.dicewars.common.Agent;
import ai.dicewars.common.Answer;
import ai.dicewars.common.Vertex;


/*
 * TODO: this agent should display game state in the console
 * and then read move from command line
 */

public class InteractiveAgent implements Agent {
	
	private int playerNumber;
	private List<Vertex> vertices;
	private ConcreteAnswer answer;

	@Override
	public Answer makeMove(List<Vertex> vertices) {
		printmap(vertices);
		System.out.println("Player " + playerNumber + " make move!");
		Scanner in = new Scanner(System.in);
		int notUsed = in.nextInt();
		return new ConcreteAnswer(true, 0, 0);
		
		
	}

	@Override
	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}
	
	public void printmap(List<Vertex> vertices)
	{
	for(int i=0; i<vertices.size(); i++)
System.out.println(""+vertices.get(i).getId()+", "+ vertices.get(i).getNeighbours()+", "+vertices.get(i).getNumberOfDices()+" , "+vertices.get(i).getPlayer()+" "); 
	
	}


		
	};

