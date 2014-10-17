package ai.dicewars.headnode.exception;

public class MapException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MapException(String string){
		super(string);
	}
	
	public MapException(Throwable t){
		super(t);
	} 
}
