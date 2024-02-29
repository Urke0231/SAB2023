package rs.etf.sab.student;
import java.util.*;
// Stores the input Graph
public class ShortestPathWithHalfEdgeWeight {
// Stores edges of input Graph
	static List<List<Pair>> graph = new ArrayList<>();
	static List<List<Integer>> edges = new ArrayList<>();

	static void addEdge(int u, int v, int w) {
		graph.get(u).add(new Pair(v, w));
		graph.get(v).add(new Pair(u, w));
		edges.add(Arrays.asList(u, v, w));
	// Function to find the shortest distance
// to each node from the src node using
// Dijkstra’s Algorithm
	}
// Stores the shortest distance of
	// each node form src node
	static class Pair implements Comparable<Pair> {
		int first, second;
// Stores the node and current
	// minimum distance in a heap
		Pair(int f, int s) {
			first = f;
			second = s;
		}
// If the distance obtained
			// from parent is less than
			// the current minimum
			// distance stored for child
		public int compareTo(Pair o) {
			return Integer.compare(first, o.first);
		}
	}
// Function to find shortest distance
// between two nodes by reducing any
// one weight of an edge by half

	static List<Integer> dijkstras(int src, int N) {
		List<Integer> dis = new ArrayList<>(Collections.nCopies(N, Integer.MAX_VALUE));
		List<Boolean> vis = new ArrayList<>(Collections.nCopies(N, false));
		PriorityQueue<Pair> pq = new PriorityQueue<>();

		pq.add(new Pair(0, src));
		dis.set(src, 0);
// Stores the shortest distance
	// of each node from B
		while (!pq.isEmpty()) {
			Pair cur = pq.poll();
			int node = cur.second, weight = cur.first;
			if (vis.get(node))
				continue;
			vis.set(node, true);

			for (Pair child : graph.get(node)) {
				if (dis.get(child.first) > child.second + weight) {
					dis.set(child.first, weight + child.second);
					pq.add(new Pair(dis.get(child.first), child.first));
				}
			}
		}

		return dis;
	}

	static int shortestDistance(int N, int M, int A, int B) {
		List<Integer> disA = dijkstras(A, N);
		List<Integer> disB = dijkstras(B, N);

		int ans = disA.get(B);
		for (List<Integer> edge : edges) {
			int u = edge.get(0), v = edge.get(1), weight = edge.get(2);
			int cur = Math.min(disA.get(u) + disB.get(v), disA.get(v) + disB.get(u)) + (weight / 2);
			ans = Math.min(ans, cur);
		}

		return ans;
	}
// Driver Code
	public static void main(String[] args) {
		int N = 9, M = 14, A = 0, B = 2;

		for (int i = 0; i < N; i++) {
			graph.add(new ArrayList<>());
		}
// Create a Graph
		addEdge(0, 1, 4);
		addEdge(1, 2, 8);
		addEdge(2, 3, 7);
		addEdge(3, 4, 9);
		addEdge(4, 5, 10);
		addEdge(5, 6, 2);
		addEdge(6, 7, 1);
		addEdge(7, 0, 8);
		addEdge(1, 7, 11);
		addEdge(7, 8, 7);
		addEdge(2, 8, 2);
		addEdge(6, 8, 6);
		addEdge(2, 5, 4);
		addEdge(3, 5, 14);
// Function Call
		System.out.println(shortestDistance(N, M, A, B));
	}
}
