package it.polito.tdp.meteo.model;

import java.util.*;

public class TestModel {

	public static void main(String[] args) {
		
		Model m = new Model();
		
//		System.out.println(m.getUmiditaMedia(12));
		
		List<Rilevamento> ril = m.trovaSequenza(5);
		System.out.println(ril.size());
		for(Rilevamento r : ril) {
			System.out.println(r);
		}

	}

}
