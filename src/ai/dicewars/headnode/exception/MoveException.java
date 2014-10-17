package ai.dicewars.headnode.exception;

public class MoveException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MoveException(String string){
		super(string);
	}
	
	public MoveException(Throwable t){
		super(t);
	} 
}
