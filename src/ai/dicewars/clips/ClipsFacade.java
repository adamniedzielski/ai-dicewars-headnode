package ai.dicewars.clips;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import CLIPSJNI.Environment;
import CLIPSJNI.PrimitiveValue;
import ai.dicewars.common.Answer;
import ai.dicewars.common.Vertex;
import ai.dicewars.headnode.ConcreteAnswer;
import ai.dicewars.headnode.exception.ClipsException;

public class ClipsFacade {

	private final static String BASE_RULES_FILE_NAME = "dicewars.clp";
	private final static String TEMP_FILE_NAME = "temp.clp";

	Environment clips;

	public ClipsFacade() {
		clips = new Environment();
		loadFile(BASE_RULES_FILE_NAME);

		// PrimitiveValue pv = clips.eval(" (options) ");
	}

	public void createMapTopology(List<? extends Vertex> vertices) {
		writeTempFile(CommandFactory.getVerticesDeffacts(vertices));
		clips.load(TEMP_FILE_NAME);
	}

	public void transferMapState(List<? extends Vertex> vertices) {
		for (Vertex v : vertices) {
			clips.eval(CommandFactory.getVertexFact(v.getId(),
					v.getNumberOfDices(), v.getPlayer()));
		}

	}

	public Answer loadNextMove() throws ClipsException {
		try {
			PrimitiveValue pv = clips.eval(CommandFactory.getNextMoveFact());
			if (pv.size() > 1)
				throw new ClipsException(
						"There is more then one next-move fact present");
			if (pv.size() == 0)
				throw new ClipsException("There is no next-move fact present");

			pv = pv.get(0);

			ConcreteAnswer answer = new ConcreteAnswer(Boolean.getBoolean(pv
					.getFactSlot("isEmptyMove").symbolValue()), pv.getFactSlot(
					"from").intValue(), pv.getFactSlot("to").intValue());

			return answer;

		} catch (Exception e) {
			throw new ClipsException(e);
		}
	}

	public void run() {
		clips.run();
	}

	public void loadFile(String filename) {
		clips.load(filename);
	}

	public void reset() {
		clips.reset();
	}

	public void destroy() {
		if (clips != null)
			clips.destroy();
	}

	/*
	 * I don't know why, but some commands (such as deffacts) are giving errors
	 * when run in Environment.eval(...). The hack for it is to save commands to
	 * the file and then use Environment.load(...)
	 */
	public void writeTempFile(String commands) {
		try {
			PrintWriter writer = new PrintWriter(TEMP_FILE_NAME, "UTF-8");
			writer.print(commands);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// TODO REMOVE
	public void assertShit() {
		clips.assertString("(next-move(from 1)(to 2)(isEmptyMove false))");
	}

	public void definePlayer(int playerNumber) {
		writeTempFile(CommandFactory.getMyPlayerDeffacts(playerNumber));
		clips.load(TEMP_FILE_NAME);
	}

}
