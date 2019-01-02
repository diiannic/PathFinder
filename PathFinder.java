/* PathFinder.java
@ Ozzy Houck and Cole DiIanni
4/21/18
*/

import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.io.UnsupportedEncodingException;
import java.util.Deque;
import java.util.Set;
import java.util.List;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

public class PathFinder
{

    CarlUnweightedGraph wiki = new CarlUnweightedGraph();
    Map<Integer, Vertex> vertexMap = new TreeMap<Integer, Vertex>(); 
    Map<String, Integer> vertexMapKey = new TreeMap<String, Integer>();
 
    
    
    /** Constructs a PathFinder that represents the graph with vertices
    specified as in vertexFile and edges specified as in edgeFile.
    @param vertexFile Name of the file with the vertex names.
    @param edgeFile Name of the file with the edge names.
*/
public PathFinder(String vertexFile, String edgeFile)
{
    
    File vertex = new File(vertexFile); //creates files
    File edge = new File(edgeFile);
    Scanner vertexScanner = null; //makes a scanner for each file
    Scanner edgeScanner = null;
    try
      {
          vertexScanner = new Scanner(vertex); // tries making a new scanner for the desired file
      }
      catch (FileNotFoundException e)
      {
         System.err.println("Vertex file not found: " + vertexFile);
         System.exit(1);
      }

    while (vertexScanner.hasNextLine()) //while there is more information in the file...
    {
        String line = vertexScanner.nextLine().trim(); // Stores next line as a string and trims white space
        if (!line.equals("") && line.charAt(0) != '#') //Ignore the line if it starts with # or nothing
        {
            try
            {
               line = java.net.URLDecoder.decode(line, "UTF-8"); // URL decoding
            }
            catch (UnsupportedEncodingException e) //if decoding goes wrong
            {
                System.err.println("Oh no. Something went wrong." + e);
                System.exit(1);
            }
            int entryNumber = wiki.addVertex(); //Add a new vertex to the graph
            Vertex vertexObject = new Vertex(entryNumber, line); //creates new vertex object
            vertexMap.put(entryNumber, vertexObject); //Add a new entry to the map which holds the vertex number as the key and the corresponding vertex as the value
            vertexMapKey.put(line, entryNumber); //Makes a key to the first map
            
        }
    }

    try
      {
          edgeScanner = new Scanner(edge); // tries making a scanner for the desired file
      }
      catch (FileNotFoundException e)
      {
         System.err.println(" Edge file not found: " + edgeFile);
         System.exit(1);
      }
    
    while (edgeScanner.hasNextLine()) 
    {
        String line = edgeScanner.nextLine().trim(); // Stores next line as a string and cuts off white space
        if (!line.equals("") && line.charAt(0) != '#') //Ignore the line if it starts with # or nothing
        {
            String[] splitLine = line.split("\t"); //split at tab space
            String firstVertex = splitLine[0];
            String secondVertex = splitLine[1];
            
            try
            {
                firstVertex = java.net.URLDecoder.decode(firstVertex, "UTF-8"); // URL decoding
                secondVertex = java.net.URLDecoder.decode(secondVertex, "UTF-8"); // URL decoding
            }
            catch (UnsupportedEncodingException e) //if decoding goes wrong
            {
                System.err.println("Oh no. Something went wrong." + e);
                System.exit(1);
            }
        try
        {
            wiki.addEdge(vertexMapKey.get(firstVertex), vertexMapKey.get(secondVertex)); // adds an edge between the cooresponding number of the first vertex name and second vertex name
        }
        catch(java.lang.NullPointerException e) //catches if there are no vertecies in the vertexMapKey to add edges between
        {
            System.err.println("You need more vertecies in your vertex file" + e);
            System.exit(1);
        }
        }
    }

} //End PathFile Constructor
  
    
/** Returns the length of a shortest path from vertex1 to vertex2.  If no
    path exists, returns -1.  If the two vertices are the same, the path
    length is 0.
    @param vertex1 Name of the starting article vertex.
    @param vertex2 Name of the ending article vertex.
    @return Length of shortest path.
*/
public int getShortestPathLength(String vertex1, String vertex2)
{
    int lastVertexNumber = vertexMapKey.get(vertex2);// converts the sting input to the cooresponding vertex
    Vertex solutionVertex = vertexMap.get(lastVertexNumber);
    if (solutionVertex.getTotalLength() == 0 && !vertex1.equals(vertex2)) //returns a length of -1 if there is not a path and the start and end vertecies are not the same
    {
        return -1;
    }
        
    else
    {
        return solutionVertex.getTotalLength(); // returns the length from end vertecy to start
    }
   
} //end getShortestPathLength
 
/** Returns a shortest path from vertex1 to vertex2, represented as list
    that has vertex1 at position 0, vertex2 in the final position, and the
    names of each vertex on the path (in order) in between.  If the two
    vertices are the same, then the "path" is just a single vertex.  If no
    path exists, returns an empty list.
    @param vertex1 Name of the starting article vertex.
    @param vertex2 Name of the ending article vertex.
    @return List of the names of vertices on a shortest path.
*/
public List<String> getShortestPath(String vertex1, String vertex2) //changed to list
    {
        int startNumber = vertexMapKey.get(vertex1);
        int endNumber = vertexMapKey.get(vertex2);

        boolean done = false;//done = false
        Queue<Integer> vertexQueue= new ArrayDeque<Integer>(); //vertexQueue = a new queue to hold vertices as they are visted
        vertexMap.get(startNumber).setVisited(true);//Mark originVertex as visted
        vertexQueue.add(startNumber);//vertexQueue.enqueue(originVertex)
    
        while (!done && !vertexQueue.isEmpty()) // while there are vertecies still in the queue
        {
            
            int frontVertex = vertexQueue.remove();// get vertex in front of queue
            int count = wiki.getDegree(frontVertex); // get number of neighbors
            
            while (!done && count != 0) //While not done and front vertix has neighbors
            {   
                Iterable<Integer> nextNeighbors = wiki.getNeighbors(frontVertex); // goes through each neighbor
                for (int nextNeighbor : nextNeighbors)
                {
                    if (vertexMap.get(nextNeighbor).getVisited() == false)// only runs if the neighbor has not been visited already
                        {
                            vertexMap.get(nextNeighbor).setVisited(true); //Set the vertex to be visited
                           vertexMap.get(nextNeighbor).setHasPreceeding(true); //SSay that it has a preceeding vertex
                           vertexMap.get(nextNeighbor).setTotalLength(vertexMap.get(frontVertex).getTotalLength() + 1); //Add 1 to the total length of the path
                            
                            vertexMap.get(nextNeighbor).setPreceedingVertex(frontVertex); //Addign to previous vertex as the preceeding vertex
                            vertexQueue.add(nextNeighbor); //Add the vertex to the vertex queue. 

                            if (vertexMap.get(nextNeighbor).getVertexNumber() == endNumber) //If the end vertex is found stop the loop by making done true
                            {
                                
                                done = true;
                            }
                        }
                }
                count--;
            }
        }
        if (done) // when done finding path
        {
            CarlStack<Vertex> path = new CarlStack<Vertex>();
            List<String> pathList = new ArrayList<String>();
            Vertex topVertex = vertexMap.get(endNumber); // puts the last vertex into the path stack
            path.push(topVertex);
            while (path.peek().getHasPreceeding()) // while there is a preceeding vertex to the vertex on top of stack
            {
                topVertex = vertexMap.get(topVertex.getPreceedingVertex()); //sets topVertex to the preceeding vertex of current top vertex 
                path.push(topVertex);//adds the preceeding vertex (now topVertex) to stack
            }
            while (!path.isEmpty()) // goes through stack and adds the vertecies in order to a list of the path
            {
                String vertexInPath = path.pop().getVertexName();
                pathList.add(vertexInPath);
            }
            return pathList; // returns list of path
        }
    
        if (vertex1.equals(vertex2)) // If the start and end points are the same
        {
            return null;
        }
        else //If no path can be found between the two points
        {
            System.out.println("No Path Exists between " + vertex1 + " and "+ vertex2);
            return null; //If there is no path between the vectors return nothing 
        }
        
        
    } // End getShortestPath
    
    public void output(List<String> solutionList)
    {
        if (solutionList == null) //if there is no solution, do not run this
        {
            System.exit(1);
        }
        for (String solution : solutionList) //prints all the solutions in order with --> after them but not after the final vertex
        {
            System.out.print(solution);
            if (!solution.equals(solutionList.get(solutionList.size() - 1)))
            {
                System.out.print(" --> ");
            }
        }
        System.out.println("");//goes to next line
    } //end output
    
    public String random() 
    {
        int indexNumber = (int) (Math.random() * vertexMap.size());// gets random number within size of map
        Vertex randomVertex = vertexMap.get(indexNumber); // gets cooresponding vertex object
        String randomArticle = randomVertex.getVertexName(); // gets the name of the vertex
        return randomArticle; //returns the name of the random vertex as a string
    }

    public static void main(String[] args)
{
    if (args.length <= 1) //If user does not add any arguments
       {
         System.out.println("Not enough inputs were given."); 
		 System.exit(1);
       }
  
    PathFinder graph = new PathFinder(args[0], args[1]);// runs PathFinder with the two arguments as input files
    String start = graph.random();// gets random start vertex name
    String end = graph.random();// gets random end vertex name
    List<String> solutionList = graph.getShortestPath(start, end);// gets solution path from start vertex to end vertex
    int solutionLength = graph.getShortestPathLength(start, end);// gets shortest path length
    System.out.println("The shortest path length between " + start + " and " + end + " is " + solutionLength); // prints the shortest path length
    graph.output(solutionList); //calls method that prints out the path

} //end main
    
    
} //End of PathFinder class
