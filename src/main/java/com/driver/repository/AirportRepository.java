package com.driver.repository;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;


@Repository
public class AirportRepository {

    public HashMap<String,Airport> airportHashMap = new HashMap<>();
    public HashMap<Integer, Flight> flightHashMap = new HashMap<>();
    public HashMap<Integer, List<Integer>> flightPassengerHashMap = new HashMap<>();
    public HashMap<Integer,Passenger> passengerHashMap = new HashMap<>();

    public String addAirport(Airport airport)
    {
        if(!airportHashMap.containsKey(airport.getAirportName()))
        {
            airportHashMap.put(airport.getAirportName(), airport);
        }
        return "SUCCESS";
    }

    public String getLargestAirportName()
    {

        String ans = "";
        int terminals = 0;
        for(Airport airport : airportHashMap.values())
        {
            if(airport.getNoOfTerminals()>terminals){
                ans = airport.getAirportName();
                terminals = airport.getNoOfTerminals();
            }
            else if(airport.getNoOfTerminals()==terminals)
            {
                if(airport.getAirportName().compareTo(ans)<0)
                {
                    ans = airport.getAirportName();
                }
            }
        }
        return ans;
    }
    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity)
    {
        double distance = 1000000000;
        for(Flight flight:flightHashMap.values())
        {
            if((flight.getFromCity().equals(fromCity))&&(flight.getToCity().equals(toCity)))
            {
                distance = Math.min(distance,flight.getDuration());
            }
        }
        if(distance==1000000000){
            return -1;
        }
        return distance;
    }

    public int getNumberOfPeopleOn(Date date, String airportName)
    {
        Airport airport = airportHashMap.get(airportName);
        if(Objects.isNull(airport)){
            return 0;
        }
        City city = airport.getCity();
        int count = 0;
        for(Flight flight:flightHashMap.values()){
            if(date.equals(flight.getFlightDate()))
                if(flight.getToCity().equals(city)||flight.getFromCity().equals(city)){
                    int flightId = flight.getFlightId();
                    count = count + flightPassengerHashMap.get(flightId).size();
                }
        }
        return count;
    }

    public int calculateFlightFare(Integer flightId)
    {
        int noOfPeopleBooked = flightPassengerHashMap.get(flightId).size();
        return noOfPeopleBooked*50 + 3000;
    }

    public String bookATicket(Integer flightId,Integer passengerId)
    {

        return "FAILURE";
    }

    public String cancelATicket(Integer flightId,Integer passengerId)
    {
        List<Integer> passengers = flightPassengerHashMap.get(flightId);
        if(passengers == null){
            return "FAILURE";
        }

        if(passengers.contains(passengerId)){
            passengers.remove(passengerId);
            return "SUCCESS";
        }
        return "FAILURE";
    }
    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId)
    {
        int count = 0;
        for(Map.Entry<Integer,List<Integer>> entry: flightPassengerHashMap.entrySet()){

            List<Integer> passengers  = entry.getValue();
            for(Integer passenger : passengers){
                if(passenger==passengerId){
                    count++;
                }
            }
        }
        return count;
    }

    public String addFlight(Flight flight)
    {
        if(!flightHashMap.containsKey(flight.getFlightId()))
        {
            flightHashMap.put(flight.getFlightId(), flight);
        }
        return "SUCCESS";
    }

    public String getAirportNameFromFlightId(Integer flightId)
    {
        if(flightHashMap.containsKey(flightId)){
            City city = flightHashMap.get(flightId).getFromCity();
            for(Airport airport:airportHashMap.values()){
                if(airport.getCity().equals(city)){
                    return airport.getAirportName();
                }
            }
        }
        return null;
    }

    public int calculateRevenueOfAFlight(Integer flightId)
    {

        return 0;
    }

    public String addPassenger(Passenger passenger)
    {
        if(!passengerHashMap.containsKey(passenger.getPassengerId()))
        {
            passengerHashMap.put(passenger.getPassengerId(), passenger);
        }
        return "SUCCESS";
    }
}
