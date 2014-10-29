package ai.dicewars.headnode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MaximumConnectedComponent {
	
	private List<ConcreteVertex> vertices;
	private List<Integer> visited;

	public MaximumConnectedComponent(List<ConcreteVertex> vertices) {
		this.vertices = vertices;
	}

	public int calculate() {
		visited = new ArrayList<>();
		int maximumComponentSize = 0;

		for (ConcreteVertex vertex : vertices) {
			if (!visited.contains(vertex.getId())) {
				Queue<ConcreteVertex> queue = new LinkedList<>();
				queue.add(vertex);
				visited.add(vertex.getId());
				int currentComponentSize = 1;
				
				while(!queue.isEmpty()) {
					ConcreteVertex currentVertex = queue.poll();
					visited.add(vertex.getId());
					for (Integer neighbourId : currentVertex.getNeighbours()) {
						ConcreteVertex neighbour = getVertex(neighbourId);
						if (neighbour != null && !visited.contains(neighbourId)) {
							currentComponentSize++;
							visited.add(neighbourId);
							queue.add(neighbour);
						}
					}
				}
				
				maximumComponentSize = Math.max(maximumComponentSize, currentComponentSize);
			}
		}
		
		return maximumComponentSize;
	}

	private ConcreteVertex getVertex(int vertexId){
		for(ConcreteVertex v : vertices)
			if(v.getId() == vertexId)
				return v;
		return null;
	}

}
