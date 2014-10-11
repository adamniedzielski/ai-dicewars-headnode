package ai.dicewars.headnode;

import ai.dicewars.common.Answer;

public class ConcreteAnswer implements Answer {
	
	private boolean empty;
	private int from;
	private int to;

	public ConcreteAnswer(boolean empty, int from, int to) {
		this.empty = empty;
		this.from = from;
		this.to = to;
	}

	@Override
	public int getFrom() {
		return from;
	}

	@Override
	public int getTo() {
		return to;
	}

	@Override
	public boolean isEmptyMove() {
		return empty;
	}

}
