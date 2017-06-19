package it.polito.tdp.babs.model;

import java.time.LocalDate;
import java.util.*;

import it.polito.tdp.babs.db.BabsDAO;

public class Model {
	private Set<Station> stazioni;
	private Simulazione sim;
	private Map<Integer, Station> map;

	public Set<Station> getStations() {
		if(stazioni == null){
			map = new HashMap<Integer, Station>();
			BabsDAO dao = new BabsDAO();
			stazioni= dao.getAllStations();
			
			for(Station s: stazioni){
				map.put(s.getStationID(), s);
			}
		}
		return stazioni;
	}
	
	public Map<Integer, Station> getMappaStazioni(){
		return map;
	}

	public int getNumPick(Station s, LocalDate data) {
		BabsDAO dao = new BabsDAO();
		return dao.getNumPick(s,data);
	}

	public int getNumDrop(Station s, LocalDate data) {
		BabsDAO dao = new BabsDAO();
		return dao.getNumDrop(s,data);
	}

	public Set<Trip> getTripsWithPickForDay(LocalDate data) {
		BabsDAO dao = new BabsDAO();
		return dao.getTripWithPick(data);
	}

	public Set<Trip> getTripsWithDropForDay(LocalDate data) {
		BabsDAO dao = new BabsDAO();
		return dao.getTripWithDrop(data);
	}

}
