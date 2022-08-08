package it.polito.tdp.metroparis.model;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.metroparis.db.MetroDAO;

public class Model {
	
	MetroDAO dao = new MetroDAO();
	
	private List<Fermata> fermate;
	private Map<Integer,Fermata> idMap;
	
	private Graph<Fermata,DefaultEdge> grafo;
	
	
	
	//METODO CHE RESTITUISCE LA LISTA DELLE FERMATE:
	public List<Fermata> getFermate(){
		
		if(fermate == null) {
			
			fermate = dao.getAllFermate();
			
			idMap = new HashMap<Integer,Fermata>();
			for(Fermata f: fermate)
				idMap.put(f.getIdFermata(), f);
		}
		
		return fermate; 
	}
	
	
	//METODO CHE RESTITUISCE IL PERCORSO MINIMO TRA LE FERMATE DATE:
	public List<Fermata> calcolaPercorso (Fermata partenza, Fermata arrivo){
			
		//Creazione e popolamento del grafo tramite il metodo:
		creaGrafo();
			
		
		//Creazione e popolamento dell'albero inverso tramite il metodo:
		Map<Fermata,Fermata> alberoInverso = visitaGrafo(partenza);
		
		
		//Ricostruzione del percorso dall'albero inverso:
		
		List<Fermata> percorso = new ArrayList<>();
		
		Fermata corrente = arrivo; 
		
		while (corrente!=null) {  //il nodo sorgente non ha predecessore
			
			percorso.add(0,corrente);
			
			corrente = alberoInverso.get(corrente);
			
			//NB:
			//IL LAVORO DI COSTRUZIONE DELL'ALBERO INVERSO
			//IN QUESTO CASO E' INUTILE XKE' L'ITERATORE IN AMPIEZZA
			//CONTIENE GIA' DI PER SE' UN METODO CORRISPONDENTE.
			
			//QUINDI SENZA BISOGNO DI CREARE NE LA CLASSE 
			//RegistraAlberoDiVisita NE IL METODO alberoInvero(),
			//E' SUFFICIENTE SOSTITUIRE QUI:
			//QUESTA RIGA: corrente = alberoInverso.get(corrente);
			//CON QUESTA: corrente = getParent(corrente);
			
			//??
		}
		
		
		return percorso; 
	}
	
	
	
	//METODO DI CREAZIONE E POPOLAMENTO DEL GRAFO:
	public void creaGrafo() {
		
		//Inizializzazione grafo:
		grafo = new SimpleDirectedGraph<Fermata,DefaultEdge>(DefaultEdge.class);
	
		
		//Popolamento vertici grafo:
		Graphs.addAllVertices(grafo, getFermate());
		//Graphs.addAllVertices(grafo, fermate);
		
		
		//Popolamento archi grafo:
		//utilizzare uno dei metodi proposti nella classe ElencoMetodi.

		//Metodo3:
		List<CoppiaId> fermateDaCollegare = dao.getAllFermateConnesse();
		
		for(CoppiaId coppia : fermateDaCollegare) {
				
			Fermata partenza = idMap.get(coppia.getIdPartenza());
			Fermata arrivo = idMap.get(coppia.getIdArrivo());
			
			grafo.addEdge(partenza, arrivo);
				
		}
	
	}
	
	
	//METODO DI COSTRUZIONE ALBERO INVERSO (VISITA GRAFO):
	public Map<Fermata,Fermata> visitaGrafo(Fermata partenza) {
		
		//Iteratore (in ampiezza):
		GraphIterator<Fermata,DefaultEdge> iteratore = 
				new BreadthFirstIterator<>(this.grafo, partenza);

	
		//Albero invero:
		//mappa contenente le coppie vertice-predecessore vertice
		//corrispondenti ai vertici del percorso (a ritroso).
		Map<Fermata,Fermata> alberoInverso = new HashMap<>();
		
		//aggiunta in primis del nodo nodo radice,
		//senza nessun predecessore
		alberoInverso.put(partenza, null);
		
		
		//Listener:
		//registratore degli eventi generati dall'algoritmo di visita:
		iteratore.addTraversalListener(new RegistraAlberoDiVisita(alberoInverso, grafo));

		
		//Finchè l'iteratore trova vertici successivi,
		//questi sono da considerare:
		while(iteratore.hasNext()) {
			Fermata f = iteratore.next();
		}
		
		
		return alberoInverso; 	
		
	}
}

