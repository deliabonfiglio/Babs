package it.polito.tdp.babs.model;

import java.time.LocalDateTime;

public class Event implements Comparable<Event>{
	public enum EventType {
		PICK, DROP;
	}
	
	private EventType tipo;
	private LocalDateTime ldt;
	private Trip trip;
	
	public Event(EventType tipo, LocalDateTime data, Trip trip) {
		this.tipo=tipo;
		this.ldt=data;
		this.trip=trip;
	}

	public EventType getTipo() {
		return tipo;
	}

	public void setTipo(EventType tipo) {
		this.tipo = tipo;
	}

	public LocalDateTime getData() {
		return ldt;
	}

	public void setData(LocalDateTime data) {
		this.ldt = data;
	}

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	@Override
	public int compareTo(Event arg0) {
		// TODO Auto-generated method stub
		return this.getData().compareTo(arg0.getData());
	}
	
	

}
