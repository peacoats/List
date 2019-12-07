package edu.example.list;

public class Listing {
    private int id;
    private String listing;
    private double number;
    private double cost;

    public Listing (int newID, String newListing, double newNumber, double newCost){
        id = newID;
        listing = newListing;
        number = newNumber;
        cost = newCost;
    }

    public String getListing(){return listing;}
    public int getId(){return id;}
    public double getNumber(){return number;}
    public double getCost(){return cost;}

    public double fromList(double current){return current/number;}

    public String toString(){return id + " " + listing + " " + number + " " + cost;}
}
