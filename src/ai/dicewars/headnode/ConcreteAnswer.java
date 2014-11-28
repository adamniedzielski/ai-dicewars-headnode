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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (empty ? 1231 : 1237);
		result = prime * result + from;
		result = prime * result + to;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConcreteAnswer other = (ConcreteAnswer) obj;
		if (empty != other.empty)
			return false;
		if (from != other.from)
			return false;
		if (to != other.to)
			return false;
		return true;
	}

}
