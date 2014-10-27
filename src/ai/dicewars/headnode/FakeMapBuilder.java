package ai.dicewars.headnode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeMapBuilder {
	public List<ConcreteVertex> build() {
		List<ConcreteVertex> vertices = new ArrayList<>();
		vertices.add(new ConcreteVertex(1, Arrays.asList(2), 4, 0));
		vertices.add(new ConcreteVertex(2, Arrays.asList(1, 3, 4), 1, 0));
		vertices.add(new ConcreteVertex(3, Arrays.asList(5, 2), 2, 1));
		vertices.add(new ConcreteVertex(4, Arrays.asList(2, 6, 5), 2, 1));
		vertices.add(new ConcreteVertex(5, Arrays.asList(3, 4, 6), 2, 1));
		vertices.add(new ConcreteVertex(6, Arrays.asList(4, 5), 3, 0));
		return vertices;
	}
}
