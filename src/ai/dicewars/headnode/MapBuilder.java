package ai.dicewars.headnode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ai.dicewars.common.Vertex;

/*
 * This class should build random connected graph
 * it should also assign dices and players to vertices
 */

public class MapBuilder {

	public List<ConcreteVertex> build() {
		List<ConcreteVertex> vertices = new ArrayList<>();
		ConcreteVertex first = new ConcreteVertex(1, Arrays.asList(2), 4, 0);
		ConcreteVertex second = new ConcreteVertex(2, Arrays.asList(1), 6, 1);
		vertices.add(first);
		vertices.add(second);
		
		return vertices;
	}
}
