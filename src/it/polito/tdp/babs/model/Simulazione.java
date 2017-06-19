package it.polito.tdp.babs.model;

import java.time.LocalDate;
import java.util.*;

import it.polito.tdp.babs.model.Event.EventType;

public class Simulazione {

	private SimulationResult simulationResult;
	private Map<Integer, Integer> mapStationOccupacy;
	private PriorityQueue<Event> pt;
	private Model model;	

	public Simulazione(Model model) {
		pt = new PriorityQueue<Event>();
		simulationResult = new SimulationResult();
		mapStationOccupacy = new HashMap<Integer, Integer>();
		this.model = model;
	}

	public void loadPick(Set<Trip> tripsPick) {
		for (Trip trip : tripsPick) {
			pt.add(new Event(EventType.PICK, trip.getStartDate(), trip));
		}
	}
	
	/*public void loadDrop(List<Trip> trips) {
		for (Trip trip : trips) {
			pt.add(new Event(EventType.DROP, trip.getEndDate(), trip));
		}
	}*/
	
	public void loadStations(double k, Set<Station> stazioni) {
		for (Station station : stazioni) {
			int occupacy = (int) (station.getDockCount() * k);
			mapStationOccupacy.put(station.getStationID(), occupacy);
		}
	}

	public void run() {
		while(!pt.isEmpty()){
			Event e = pt.poll();
			System.out.println(e.toString()+"\n");
			
			switch(e.getTipo()){
			case PICK:
				processPickEvent(e);
				break;
				
			case DROP:
				processDropEvent(e);
				break;
			default:
				break;
			}
		}		
	}

	private void processDropEvent(Event e) {
		int occupacy = mapStationOccupacy.get(e.getTrip().getStartStationID());
		
		int posti_totali = model.getMappaStazioni().get(e.getTrip().getEndStationID()).getDockCount();
		
		if(posti_totali>=occupacy){
			//ci sono posti x lasciare la bici==> tutto ok, no altri eventi
			occupacy++;
			mapStationOccupacy.put(e.getTrip().getEndStationID(), occupacy);
			
		} else {
			//no posti==> aumento numero di drop mancati
			simulationResult.addDropMissed();
		}
		
	}

	private void processPickEvent(Event e) {
		int occupacy = mapStationOccupacy.get(e.getTrip().getStartStationID());
		
		if(occupacy>0){
			occupacy--;
			
			this.mapStationOccupacy.put(e.getTrip().getStartStationID(), occupacy);
			
			pt.add(new Event(EventType.DROP, e.getTrip().getEndDate(), e.getTrip()));
						
		} else {
			simulationResult.addPickMissed();
		}
		
	}

	public SimulationResult collectResults() {
		return simulationResult;
	}

}
