package it.polito.tdp.metroparis.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElencoMetodi {

	/**
	//METODO1: 
	//Per ogni coppia di vertici (fermata partenza - fermata arrivo), 
	//il dao verifica se questi sono connessi.
	//In caso affermativo, il model crea quindi l'arco tra essi.
	
	for (Fermata partenza : fermate) {
		for (Fermata arrivo : fermate) {
			if(dao.isFermateConnesse(partenza, arrivo)) {
				grafo.addEdge(arrivo, partenza);
			}
		}
	}
	
	//METODO SEMPRE FUNZIONANTE.
	//MA DECISAMENTE TROPPO DISPENDIOSO NEI CASI, COME QUESTO, 
	//IN CUI IL NUMERO DI VERTICI E' ELEVATO!!
	*/
	
	
	/*
	//METODO2:
	//Per ogni vertice (fermata partenza), il dao individua i suoi vertici 
	//adiacenti (fermate arrivo). 
	//Il model crea quindi gli archi corrisondenti.
	
	
	//VARIANTE2A:
	//Il dao restituisce l'elenco degli id delle fermate adiacente. Quindi è 
	//necessario individuare nella lista fermate quella corrispondente
	//prima di poter creare l'arco.
		
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
	*/
	
	/**
	//VARIANTE2B:
	//Il dao restituisce direttamente l'elenco delle fermate adiacenti.
			
	for(Fermata partenza : fermate) {
			
		List<Fermata> fermateConnesse = dao.getFermateConnesse(partenza);
			
		for (Fermata arrivo : fermateConnesse) {
			grafo.addEdge(arrivo, partenza);
		}
		
	}
	*/
	
	/*
	//VARIANTE2C:
	//Il dao restituisce l'elenco degli id delle fermate adiacenti, da 
	//convertire in oggetti fermata tramite una Map<Integer,Fermata> 
	//(Identity Map) prima di poter creare l'arco.
	
	//NB:
	//1.Creare e popolare la identity Map, 
	//subito sotto la creazione della lista fermate:
	
	Map<Integer,Fermata> idMap = new HashMap<Integer,Fermata>();
		
	for(Fermata f: fermate)
		idMap.put(f.getIdFermata(), f);
		
	//2.Procedere col metodo di creazione degli archi:
	
	for(Fermata partenza : fermate) {
			
		List<Integer> idFermateConnesse = dao.getIdFermateConnesse(partenza);
			
		for(Integer id : idFermateConnesse) {
			Fermata arrivo = idMap.get(id);
			grafo.addEdge(partenza, arrivo);
		}
		
	}
	*/	
	
	
	/**
	//METODO3:
	//Il dao restuisce direttamente coppie di fermate connesse.
	//Il model crea quindi un arco tra esse. 
	
	//NB:
	//1.Creare e popolare la identity Map, 
	//subito sotto la creazione della lista fermate:
	
	Map<Integer,Fermata> idMap = new HashMap<Integer,Fermata>();
		
	for(Fermata f: fermate)
		idMap.put(f.getIdFermata(), f);
		
	//2.Procedere col metodo di creazione degli archi:
	
	List<CoppiaId> fermateDaCollegare = dao.getAllFermateConnesse();
		
	for(CoppiaId coppia : fermateDaCollegare) {
			
		Fermata partenza = idMap.get(coppia.getIdPartenza());
		Fermata arrivo = idMap.get(coppia.getIdArrivo());
		
		grafo.addEdge(partenza, arrivo);
			
	}
	
	*/
}
