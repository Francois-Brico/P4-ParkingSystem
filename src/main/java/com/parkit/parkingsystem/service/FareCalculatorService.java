package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        double inTime = ticket.getInTime().getTime();  // (FR) Fonction getHour modifier en fonction getTime + adaptation formule pour avoir le nombre d'heure (en prennant en compte l'apres virgule)
                
        double outTime = ticket.getOutTime().getTime(); //  (FR) Changement du format "int" en "double" 
        

        //TODO: Some tests are failing here. Need to check if this logic is correct
        double hDuration = (outTime - inTime)/(60*60*1000);
        
        if(hDuration<=0.5) { // (FR) Rajout de gratuité si inferieur a 30 minute
        	ticket.setPrice(0) ;
        	
        } else {
	        switch (ticket.getParkingSpot().getParkingType()){
	            case CAR: {
	                ticket.setPrice(hDuration * Fare.CAR_RATE_PER_HOUR);
	                break;
	            }
	            case BIKE: {
	                ticket.setPrice(hDuration * Fare.BIKE_RATE_PER_HOUR);
	                break;
	            }
	            default: throw new IllegalArgumentException("Unkown Parking Type");
	        }
        }
    }
}