import java.util.*;

public class ArtAcquisition
{
	// Store the queries for each archaeologist
	public static boolean query[];
	
	public static class Event implements Comparable<Event>
	{
		// 0 a query and 1 a path connecting pedestals
		int type;
		// Start and end pedestal
		int start, end;
		// Max weight the path supports
		long w;
		// Stores which query this corresponds to
		int queryIndex; 
		
		@Override
		// For sorting greatest to least weight
		public int compareTo(Event e)
		{
			return Long.compare(e.w, w);
		}
	}
	
	// The disjoint set class
	public static class DS
	{
		int[] parent, rank;
		
		// Disjoint set constructor
		DS(int n)
		{
			parent = new int[n];
			// Default ranks with 0
			rank = new int[n]; 
	            
			// Every node is the root of it's tree (parent[i] = i);
			for (int i = 0; i < n; i++)
			{
				parent[i] = i;
			}
		}

		// A path compress find method (note that rank's are not updated)
	    int find(int x)
		{
			// Find the root!
			int root = parent[x];
			while (root != parent[root])
			{
				root = parent[root];
			}
			
			// Start at the bottom of the path
			int bottom = x;
			
			// Change all parent pointers along path to the root to point towards it
			while (bottom != root)
			{
				int oldParent = parent[bottom];
				parent[bottom] = root;
				bottom = oldParent;
			}

	        // Return the root found
			return root;
		}
	        
		// The union function returns true if the sets were merged
		// and false otherwise
		boolean union(int i, int j)
		{
			// Try unioning from the roots
			i = find(i);
			j = find(j);
	            
			// Check if the roots were the same (NO MERGE)
			if (i == j)
			{
				return false;
			}
	            
			// Check if the rank of j was bigger
			if (rank[i] < rank[j])
			{
				// Make the parent of the small root (i)
				// the root of the big tree (j)
				parent[i] = j;
				return true;
			}
	            
			// Smaller root is most likely j.
			// Make the parent of the small root (j)
			// the root of the big tree (i)
			parent[j] = i;
			// Update i's rank if j was the same size
			if (rank[i] == rank[j])
			{
				rank[i]++;
			}
	            
			// Return that the merge happened
			return true;
		}
	}
	
	public static void main(String[] args)
	{
		// Pedestals, paths, and queries
		int n, m, q;
		Scanner sc = new Scanner(System.in);
		ArrayList<Event> pathArc = new ArrayList<Event>();
	
		// Read in pedestals and paths
		n = sc.nextInt();
		m = sc.nextInt();
		
		// Add paths to the Arraylist
		for(int i = 0; i < m; i++)
		{
			Event path = new Event();
			path.type = 1;
			path.start = sc.nextInt() - 1;
			path.end = sc.nextInt() - 1;
			path.w = (2 * sc.nextInt()) + 1;
			pathArc.add(path);
		}
		
		// Read in queries
		q = sc.nextInt();
		
		query = new boolean[q];
		
		// Add archaeologists to ArrayList
		for(int i = 0; i < q ; i++)
		{
			Event arc = new Event();
			arc.type = 0;
			arc.start = sc.nextInt() - 1;
			arc.end = sc.nextInt() - 1;
			arc.w = 2 * sc.nextInt();
			arc.queryIndex = i;
			pathArc.add(arc);
		}
		
		// Close scanner
		sc.close();
		
		// Sort the Arraylist
		Collections.sort(pathArc);
		
		// Initialize disjoint set with size number of pedestals
		DS set = new DS(n);
		
		// Loop through each Event
		for(Event e : pathArc)
		{
			//If path then merge
			if(e.type == 1)
			{
				if(set.union(e.start, e.end));
				continue;
			}
			//If archaeologist then compare
			else if(e.type == 0)
			{
				if(set.find(e.start) == set.find(e.end))
				{
					query[e.queryIndex] = true;
				}
			}
		}
		
		// Print out offline query
		for(int i = 0; i < q; i++)
		{
			if(query[i])
			{
				System.out.println("Yes");
			}
			else
			{
				System.out.println("No");
			}
		}
	}
}