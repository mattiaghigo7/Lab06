package it.polito.tdp.meteo.model;

import java.util.*;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	private MeteoDAO meteoDAO;
	private List<Citta> cittaPresenti;
	private List<Rilevamento> sequenza;
	private int costoMinore;
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;

	public int getCostoMinore() {
		return costoMinore;
	}

	public Model() {
		this.meteoDAO = new MeteoDAO();
		this.cittaPresenti = new LinkedList<Citta>();
		for (Rilevamento r : this.meteoDAO.getAllRilevamenti()) {
			Citta c = new Citta(r.getLocalita());
			if(!cittaPresenti.contains(c)) {
				cittaPresenti.add(c);
			}
		}
		cittaPresenti.sort(null);
	}

	public List<String> getUmiditaMedia(int mese) {
		List<String> umiditaMediaCitta = new LinkedList<String>();
		for(Citta c : cittaPresenti) {
			umiditaMediaCitta.add("Località: "+c.getNome()+" "+"\tUmidità media: "+this.meteoDAO.getAvgRilevamentiLocalitaMese(mese, c.getNome()));
		}
		return umiditaMediaCitta;
	}
	
	public List<Rilevamento> trovaSequenza(int mese) {
		sequenza = new LinkedList<Rilevamento>();
		costoMinore=Integer.MAX_VALUE;
		for(Citta c : cittaPresenti) {
			c.setCounter(0);
			c.setRilevamenti(meteoDAO.getAllRilevamentiLocalitaMese(mese, c.getNome()));
		}
		List<Rilevamento> parziale = new LinkedList<Rilevamento>();
		cerca(parziale,0);
		return sequenza;
	}
	
	//ricorsione
	private void cerca(List<Rilevamento> parziale, int livello) {
		if(livello==Model.NUMERO_GIORNI_TOTALI) {
			if(this.calcolaCosto(parziale)<costoMinore) {
				costoMinore = this.calcolaCosto(parziale);
				sequenza = new LinkedList<Rilevamento>(parziale);
			}
			return;
		}
		for(Citta c : cittaPresenti) {
			parziale.add(c.getRilevamenti().get(livello));
			c.increaseCounter();
			if(controllaParziale(parziale)) {
				cerca(parziale,livello+1);
			}
			parziale.remove(livello);
			c.decreaseCounter();
		}
	}
	
	private int calcolaCosto(List<Rilevamento> parziale) {
		String precedente = parziale.get(0).getLocalita();
		int costo = 0;
		for (Rilevamento r : parziale) {
			if(precedente.compareTo(r.getLocalita())!=0) {
				costo+=Model.COST;
			}
			costo+=r.getUmidita();
			precedente=r.getLocalita();
		}
		return costo;
	}
	
	private boolean controllaParziale(List<Rilevamento> parziale) {
		if(parziale.size()==0) {
			return true;
		}
		//controllo che non si stia più di 6 giorni in una città
		for(Citta c : cittaPresenti) {
			if(c.getCounter()>Model.NUMERO_GIORNI_CITTA_MAX) {
				return false;
			}
		}
		//controllo che si stia almeno per 3 giorni consecutivi nella stessa città
		String precedente = parziale.get(0).getLocalita();
		int cont = 0;
		for(Rilevamento r : parziale) {
			if(precedente.compareTo(r.getLocalita())!=0) {
				if(cont<Model.NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN) {
					return false;
				}
				cont=1;
				precedente=r.getLocalita();
			}
			else {
				cont++;
			}
		}
		return true;
	}
}
