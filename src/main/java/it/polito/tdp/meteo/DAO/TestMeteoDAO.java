package it.polito.tdp.meteo.DAO;

import java.util.List;

import it.polito.tdp.meteo.model.Rilevamento;

public class TestMeteoDAO {

	public static void main(String[] args) {
		
		MeteoDAO dao = new MeteoDAO();

		List<Rilevamento> list = dao.getAllRilevamenti();

		// STAMPA: localita, giorno, mese, anno, umidita (%)
		for (Rilevamento r : list) {
			System.out.format("%-10s %2td/%2$2tm/%2$4tY %3d%%\n", r.getLocalita(), r.getData(), r.getUmidita());
		}
		
		List<Rilevamento> gen = dao.getAllRilevamentiLocalitaMese(1, "Genova");
		for(Rilevamento r : gen) {
			System.out.println(r.toString());
		}
		System.out.println(dao.getAvgRilevamentiLocalitaMese(1, "Genova"));
//		
		List<Rilevamento> mil = dao.getAllRilevamentiLocalitaMese(5, "Milano");
		for(Rilevamento r : mil) {
			System.out.println(r.toString());
		}
		System.out.println(dao.getAvgRilevamentiLocalitaMese(5, "Milano"));
//		
		List<Rilevamento> tor = dao.getAllRilevamentiLocalitaMese(11, "Torino");
		for(Rilevamento r : tor) {
			System.out.println(r.toString());
		}
		System.out.println(dao.getAvgRilevamentiLocalitaMese(11, "Torino"));
		

	}

}
