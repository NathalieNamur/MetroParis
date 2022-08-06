package it.polito.tdp.metroparis.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.metroparis.model.Connessione;
import it.polito.tdp.metroparis.model.CoppiaId;
import it.polito.tdp.metroparis.model.Fermata;
import it.polito.tdp.metroparis.model.Linea;

public class MetroDAO {

	public List<Fermata> getAllFermate() {

		final String sql = "SELECT id_fermata, nome, coordx, coordy "
						 + "FROM fermata "
						 + "ORDER BY nome ASC";
		
		List<Fermata> fermate = new ArrayList<Fermata>();

		try {
			
			Connection conn = DBConnect.getConnection();
			
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				
				Fermata f = new Fermata(rs.getInt("id_Fermata"), 
										rs.getString("nome"),
										new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				fermate.add(f);
			}

			st.close();
			conn.close();
			
			return fermate;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore nel metodo getAllFermate().");
		}
	
	}

	
	/**
	public List<Linea> getAllLinee() {
		
		String sql = "SELECT id_linea, nome, velocita, intervallo "
				   + "FROM linea ORDER BY nome ASC";

		List<Linea> linee = new ArrayList<Linea>();

		try {
			
			Connection conn = DBConnect.getConnection();
			
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				
				Linea l = new Linea(rs.getInt("id_linea"), rs.getString("nome"),
									rs.getDouble("velocita"), rs.getDouble("intervallo"));
				linee.add(l);
			}

			st.close();
			conn.close();
			
			return linee;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

	}
	*/
	
	//VED.METODO1:
	public boolean isFermateConnesse(Fermata partenza, Fermata arrivo) {
		
		String sql = "SELECT COUNT(*) AS cnt "
					+ "FROM connessione "
					+ "WHERE id_stazP=? "
					+ "AND id_stazA=?";
		
		try {
			
			Connection conn = DBConnect.getConnection();
			
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, partenza.getIdFermata());
			st.setInt(2, arrivo.getIdFermata());
			
			ResultSet res = st.executeQuery();
			res.first();						//NB: no ciclo while. 
			int count = res.getInt("cnt");		//Il risultato sta su un'unica riga 
			
			st.close();
			conn.close();
			
			if(count>0)
				return true;		//oppure
			else					//return count>0
				return false;
	
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore nel metodo isFermateConnesse().");
		}
		
	}
	
	
	//METODO 2A:
	public List<Integer> getIdFermateConnesse(Fermata partenza) {
		
		String sql = "SELECT id_stazA "
				   + "FROM connessione "
				   + "WHERE id_stazP=? "
				   + "GROUP BY id_stazA";
		
		List<Integer> result = new ArrayList<Integer>();
		
		try {
			
			Connection conn = DBConnect.getConnection();
			
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, partenza.getIdFermata());
			
			ResultSet res = st.executeQuery();
			while(res.next()) {
				result.add(res.getInt("id_stazA"));
			}
			
			st.close();
			conn.close();
			
			return result ;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore nel metodo getIdFermateConnesse().");
		}
		
	}
	
	
	//METODO 2B:
	public List<Fermata> getFermateConnesse(Fermata partenza) {
		
		String sql = "SELECT id_fermata, nome, coordx, coordy "
				   + "FROM fermata "
				   + "WHERE id_fermata IN ( "
				   + "SELECT id_stazA "
				   + "FROM connessione "
				   + "WHERE id_stazP=? "
				   + "GROUP BY id_stazA "
				   + ") "
				   + "ORDER BY nome ASC";
		
		List<Fermata> result = new ArrayList<Fermata>();

		try {
			
			Connection conn = DBConnect.getConnection();
			
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, partenza.getIdFermata());
			
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				
				Fermata f = new Fermata(rs.getInt("id_Fermata"), 
										rs.getString("nome"),
										new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				result.add(f);
			}

			st.close();
			conn.close();
			
			return result; 

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore nel metodo getFermateConnesse().");
		}

	}
	
	
	//METODO3:
	public List<CoppiaId> getAllFermateConnesse() {
		
		String sql = "SELECT DISTINCT id_stazP, id_stazA "
				   + "FROM connessione" ;
		
		List<CoppiaId> result = new ArrayList<CoppiaId>() ;
		
		try {
			
			Connection conn = DBConnect.getConnection();
			
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet res = st.executeQuery() ;
			while(res.next()) {
				
				CoppiaId coppia = new CoppiaId(res.getInt("id_stazP"), 
											   res.getInt("id_stazA"));
				result.add(coppia);
			}
			
			st.close();
			conn.close();
			
			return result ;
		
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore nel metodo getAllFermateConnesse().");
		}
		
	}
	
	
}
