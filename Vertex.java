public class Vertex
{
    private int vertexNumber;
    private int totalLength = 0;
    private String vertexName;
    private int preceedingVertex;
    private boolean visited = false;
    private boolean hasPreceeding = false;

    Vertex(int vertexNumber, String vertexName) //Construct a vertex with a name and corresponding number to the its position on the graph.
    {
    this.vertexNumber = vertexNumber;
    this.vertexName = vertexName;
    }

    
    public String getVertexName() //gets name of vertex
    {
        return vertexName;
    }

    public int getVertexNumber() //gets number of vertex
    {
        return vertexNumber;
    }
    
    public int getTotalLength() //gets length from start vertex to this vertex
    {
        return totalLength;
    }
    
    public int getPreceedingVertex() //gets the vertex which preceeds this vertex
    {
        return preceedingVertex;
    }
    
    public void setVertexNumber(int setValue) //sets the vertex's number
    {
        this.vertexNumber = setValue;
    }
    
    public void setTotalLength(int setValue) //sets the length from start vertex to this vertex
    {
        this.totalLength = setValue;
    }
    
    public void setPreceedingVertex(int setValue) //sets the preceeding vertex
    {
        this.preceedingVertex = setValue;
    }
    
    public boolean getVisited() //gets whether or not this vertex has been visited
    {
        return visited;
    }
    
    public void setVisited(boolean setValue) // sets whether or not this vertex has been visited
    {
        visited = setValue;
    }
    
    public boolean getHasPreceeding() //gets whether or not this vertex has a preceeding vertex
    {
        return hasPreceeding;
    }
    
    public void setHasPreceeding(boolean setValue) //sets whether or not this vertex has a preceeding vertex
    {
        hasPreceeding = setValue;
    }

}
