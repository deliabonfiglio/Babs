package it.polito.tdp.babs;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.ResourceBundle;
import it.polito.tdp.babs.model.Model;
import it.polito.tdp.babs.model.SimulationResult;
import it.polito.tdp.babs.model.Simulazione;
import it.polito.tdp.babs.model.Station;
import it.polito.tdp.babs.model.Trip;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;

public class BabsController {

	private Model model;

	public void setModel(Model model) {
		this.model = model;
	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private DatePicker pickData;

	@FXML
	private Slider sliderK;

	@FXML
	private TextArea txtResult;

	@FXML
	void doContaTrip(ActionEvent event) {
		LocalDate data = pickData.getValue();
		
		Set<Station>stazioni = model.getStations();
		for(Station s: stazioni){
			int num_pick= model.getNumPick(s, data);
			int num_drop = model.getNumDrop(s, data);
			
			txtResult.appendText(s.toString()+" npick: "+num_pick+" ndrop: "+num_drop+"\n");
		}
	}

	@FXML
	void doSimula(ActionEvent event) {
		LocalDate ld = pickData.getValue();
		if (ld == null || ld.getDayOfWeek() == DayOfWeek.SATURDAY || ld.getDayOfWeek() == DayOfWeek.SUNDAY) {
			txtResult.setText("Selezionare una giorno feriale!");
			return;
		}
		
		double kvalue = sliderK.getValue();
		
		Set<Trip> tripsPick = model.getTripsWithPickForDay(ld);
		Set<Trip> tripsDrop = model.getTripsWithDropForDay(ld);
		
		Simulazione simulazione = new Simulazione(model);
		simulazione.loadPick(tripsPick);
		
		// Potrebbe falsare il risultato della simulazione
		// simulazione.loadDrop(tripsDrop);
		
		simulazione.loadStations(kvalue, model.getStations());
		simulazione.run();
		
		//devo prendere i risultati della simulazione appena fatta, altrimenti ne creerei una nuova!
		txtResult.clear();
		
		SimulationResult simulationResult = simulazione.collectResults();
		txtResult.appendText("PICK MISS: " + simulationResult.getPickMissed()+"\n");
		txtResult.appendText("DROP MISS: " + simulationResult.getDropMissed());
		
	}

	@FXML
	void initialize() {
		assert pickData != null : "fx:id=\"pickData\" was not injected: check your FXML file 'Babs.fxml'.";
		assert sliderK != null : "fx:id=\"sliderK\" was not injected: check your FXML file 'Babs.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Babs.fxml'.";

		pickData.setValue(LocalDate.of(2013, 9, 1));
	}
}
