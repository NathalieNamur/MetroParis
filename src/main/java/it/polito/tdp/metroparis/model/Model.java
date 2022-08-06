package it.polito.tdp.metroparis.model;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import it.polito.tdp.metroparis.db.MetroDAO;

public class Model {
	
	//Definizione grafo:
	private Graph<Fermata,DefaultEdge> grafo;
	
	MetroDAO dao = new MetroDAO();
	
	
	public void creaGrafo() {
		
		//Inizializzazione grafo:
		grafo = new SimpleDirectedGraph<Fermata,DefaultEdge>(DefaultEdge.class);
	
		
		//Popolamento vertici grafo:
		List<Fermata> fermate = dao.getAllFermate();
	
		Graphs.addAllVertices(grafo, fermate);
		
		
		//Popolamento archi grafo:
		//utilizzare uno dei metodi proposti nella classe ElencoMetodi.

		//...
		
		for(Fermata partenza : fermate) { //'fermate' oppure 'grafo.vertexSet()'
			
			List<Integer> idFermateConnesse = dao.getIdFermateConnesse(partenza);
				
			for(Integer id : idFermateConnesse) {
					
				Fermata arrivo = null; 
					
				for(Fermata f : fermate) {
				
					if(f.getIdFermata() == id) {
						arrivo = f; 
						break;
					}
				}
					
				grafo.addEdge(partenza, arrivo);
						
			}
		}
		
		
		System.out.println(grafo); //??
		System.out.println("Numero vertici grafo: "+grafo.vertexSet().size());
		System.out.println("Numero archi grafo: "+grafo.edgeSet().size());
		

	}
}

