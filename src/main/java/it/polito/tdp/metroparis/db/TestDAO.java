package it.polito.tdp.metroparis.db;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.metroparis.model.Fermata;

public class TestDAO {

	public static void main(String[] args) {
		
		MetroDAO dao = new MetroDAO();
		
		
		List<Fermata> fermate = new ArrayList<>();
		fermate = dao.getAllFermate();
		
		System.out.println("Numero di fermate: "+fermate.size());
	
	}
}
