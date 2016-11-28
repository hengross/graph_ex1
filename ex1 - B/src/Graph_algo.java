
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

class Vertex implements Comparable<Vertex>{

    public int name;
    public List<Edge> edges = new ArrayList<Edge>();
    public double dist;
    public int previous;
    public boolean visited;

    public Vertex(int name, double dist) {
        this.name = name;
        this.dist = dist;
        previous = -1;
        visited = false;
    }

    public Vertex(Vertex v) {
        this.name = v.name;
        this.dist = v.dist;
        previous = v.previous;
        visited = v.visited;

    }

    public String toString() {
        return "" + name;
    }
    public int compareTo(Vertex other)
	{
		return Double.compare(dist, other.dist);
	}
}

class Edge {

    public int vert;
    public double weight;
    public int sourceV;

    public Edge(int v, double w) {
        vert = v;
        weight = w;
    }

    public Edge(int v, double w, int source) {
        vert = v;
        weight = w;
        sourceV = source;
    }
}

public class Graph_algo {

    Vertex[] vertices;
    int source;
    public static double infinity = Double.POSITIVE_INFINITY;
    double diameter = 0; ///
    double radius = 0; /// 

    public Graph_algo(Vertex[] vs) {
        vertices = new Vertex[vs.length];
        for (int i = 0; i < vs.length; i++) {
            vertices[i] = vs[i];
        }
    }
    
    public void computePaths(int Vertexsource) {
                this.vertices[Vertexsource].dist= 0.;
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
		vertexQueue.add(vertices[Vertexsource]);
		while (!vertexQueue.isEmpty()) 
		{
			Vertex u = vertexQueue.poll();
			// Visit each edge exiting u
			for (Edge e : u.edges)
			{
				Vertex v = vertices[e.vert];
				double weight = e.weight;
				double distanceThroughU = u.dist + weight;
				if (distanceThroughU < v.dist) {//relaxation
					vertexQueue.remove(v);
					v.dist = distanceThroughU ;
					v.previous = vertices[u.name].name;
					vertexQueue.add((Vertex)(v));
				}
			}
		}
    }

    public boolean isInArr(int index,int [] arr){
        for (int i = 0; i < arr.length; i++) {
            if(index == arr[i]){
                return true;
            }
        }
        return false;
    } 
    
    public void computePathsBlackList(int Vertexsource,int [] arr) {
                this.vertices[Vertexsource].dist= 0.;
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
		vertexQueue.add(vertices[Vertexsource]);
		while (!vertexQueue.isEmpty()) 
		{
                        
			Vertex u = vertexQueue.poll();
                        
			// Visit each edge exiting u
			for (Edge e : u.edges)
			{
                            if(isInArr(e.vert,arr)==false){
				Vertex v = vertices[e.vert];
				double weight = e.weight;
				double distanceThroughU = u.dist + weight;
				if (distanceThroughU < v.dist) {//relaxation
					vertexQueue.remove(v);
					v.dist = distanceThroughU ;
					v.previous = vertices[u.name].name;
					vertexQueue.add((v));
				}
                            }
			}
                        
		}
    }

    //return the path between u to v
    public String getShortestPath(int u, int v) {
        int t = v;
        String ans = t + "";
        while (t != u) {
            t = vertices[t].previous;
            ans = t + "->" + ans;
        }
        return ans;
    }

    //return the shortest distance between u to v
    public double getSortestWeight(int u, int v) {
        return vertices[v].dist;
    }
    
    public void printWeights(){
		System.out.print("weights: ");
		for (Vertex v : vertices) {
			System.out.printf(v.dist + ", ");
		}
		System.out.println();
	}
    
    public double radius(){ /////////all
        double [] maxEdges = new double [vertices.length];
        double diaMin = 0;
        double dia = 0;
        int index = 0;
        for (int i = 0; i <vertices.length ; i++) {
            computePaths(vertices[i].name);
            for (int j = 0; j < vertices.length; j++) {
                if(vertices[j].dist > dia)
                    dia = vertices[j].dist;
            }
            maxEdges[index++] = dia;
            dia = 0;
            for (int k = 0; k < vertices.length; k++) {
                      vertices[k].visited = false;
                      vertices[k].previous = -1;
                      vertices[k].dist = infinity;
                  }
        }
        diaMin = maxEdges[0];
        double diameter = maxEdges[0]; ////////
        for (int i = 0; i < maxEdges.length; i++) {
            if(maxEdges[i] < diaMin)
                diaMin = maxEdges[i];
            if(maxEdges[i] > diameter)
                diameter = maxEdges[i]; 
        }
        this.diameter = diameter;
        return diaMin;
    }
    
    public double diameter(){ //////////alll
        return this.diameter;
    }
        
    //return true if the shortest path is in length of 1, otherwise returns false
    public boolean directPath(int u, int v) { ///////
        int t = v;
        String ans = t + "";
        int len = 0;
        while (t != u) {
            if(len > 1)
                return false;
            t = vertices[t].previous;
            ans = t + "->" + ans;
            len ++;
        }
        if(len > 1){
            return false;
        }
        return true;
    }
    //׳ ׳—׳–׳™׳¨ ׳ ׳ ׳× ׳ ׳  ׳”׳’׳¨׳£ ׳ ׳§׳™׳™׳  ׳ ׳× ׳ ׳©׳₪׳˜ ׳ ׳™ ׳©׳•׳•׳™׳•׳  ׳”׳ ׳©׳•׳ ׳©, ׳ ׳—׳¨׳× ׳”׳•׳  ׳ ׳—׳–׳™׳¨ ׳©׳§׳¨
    public boolean TriangleInequality(){
        for (int i = 0; i <vertices.length ; i++) {
            computePaths(vertices[i].name);
            for (int j = 0; j < vertices.length; j++) {
                if(vertices[i].name!=vertices[j].name){
                    if(directPath(vertices[i].name,vertices[j].name) == false)
                        return false;
                }
            }
            for (int k = 0; k < vertices.length; k++) { //////////
                      vertices[k].visited = false;
                      vertices[k].previous = -1;
                      vertices[k].dist = infinity;
                  }
        }
        return true;
    }
    
}