package com.parkit.parkingsystem.service;
import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket, Boolean isRecurrent){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        long inHour = ticket.getInTime().getTime();
        long outHour = ticket.getOutTime().getTime();
        
        
        double duration = (outHour - inHour)/1000/60; //gets the difference between in and out dates in minutes
        double hours = (double)duration/60; //converts duration into hours

        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice(hours * Fare.CAR_RATE_PER_HOUR);
                break;
            }
            case BIKE: {
                ticket.setPrice(hours * Fare.BIKE_RATE_PER_HOUR);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
        
        if (hours<=0.5) {
        	ticket.setPrice(0); //free parking for those who don't stay more than half an hour!
        }
        
        if (isRecurrent) {
        	double discount = ticket.getPrice()*0.05;
        	ticket.setPrice(ticket.getPrice()-discount);
        	System.out.println("Welcome back! As a recurring user of our parking lot, you'll benefit from a 5% discount.");
        }
    }
    
}