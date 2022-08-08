package it.polito.tdp.metroparis.model;

import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;

public class RegistraAlberoDiVisita implements TraversalListener<Fermata, DefaultEdge> {
	
	//Creare una classe che implementi TraversalListener<,> 
	//e contenga tutti i metodi corrispondenti.
	
	//Popolare solo i metodi di interesse di tale classe.
	
	
	//Collegare tale classe all'algoritmo di visita,
	//inserendo nel metodo corrispondente nel model un listener.
	
	
	
	//ATTRIBUTI:
	private Graph<Fermata,DefaultEdge> grafo;
	private Map<Fermata,Fermata> alberoInverso;
	
	

	//COSTRUTTORE:
	public RegistraAlberoDiVisita(Map<Fermata, Fermata> alberoInverso, Graph<Fermata, DefaultEdge> grafo) {
		this.alberoInverso = alberoInverso;
		this.grafo = grafo ;
	}

	
	
	//METODI DI DEFAULT:
	@Override
	public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	//METODO PER POPOLARE L'ALBERO INVERSO (vertice-predecessore vertice):
	@Override
	public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
		
		//Ogni arco attraversato corrisponde ad un 
		//vertice di partenza (source) e un vertice di arrivo (target)
		Fermata source = this.grafo.getEdgeSource(e.getEdge());
		Fermata target = this.grafo.getEdgeTarget(e.getEdge());
		
		//a seconda che tali vertici siano o no già presenti nell'albero,
		//devono esservi aggiunti:
		
		//- se source e target sono già presenti: 
		// non devono esservi aggiunti.
		
		//- se ne source ne target sono già presenti: 
		//  impossibile.
		
		//- se il target non è presente, ma lo è il source: 
		//  il target è appena stato scoperto dal source, 
		//  quindi deve essere fatta l'agggiunta corretta.
		if(!alberoInverso.containsKey(target)) {
			alberoInverso.put(target, source) ;
		}
		
		//- se il source non è presente, ma lo è il target: 
		//  il source è appena stato scoperto dal target,
		//  quindi deve essere fatta l'agggiunta corretta.
		else if(!alberoInverso.containsKey(source)) {
			alberoInverso.put(source, target) ;

		}
		
	}

	
	@Override
	public void vertexTraversed(VertexTraversalEvent<Fermata> e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void vertexFinished(VertexTraversalEvent<Fermata> e) {
		// TODO Auto-generated method stub
		
	}

}