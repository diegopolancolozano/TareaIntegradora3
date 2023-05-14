package model;

import java.util.Calendar;
public class Book extends Bibliographic{

    private String review;
    private Gender gender;
    private double price;
    private int sold;
    public Book(String name, String id, Calendar publicationDate, String urlImage, int pages, String review, int gender, double price, int sold){
        super(name, id, publicationDate, urlImage, pages);
        this.review=review;
        switch(gender){
            case 1 -> this.gender=Gender.SCIENCE_FICTION;
            case 2 -> this.gender=Gender.FANTASY;
            case 3 -> this.gender=Gender.NOVEL;
        }
        this.price=price;
        this.sold=sold;
    }

    @Override
    public String getAllInfo(){
        String msg=getCommon();
        msg+="\n";
        msg+="resenia: " + review + "\n";
        msg+="precio: " + price;
        msg+=", cantidad vendida " + sold;
        msg+=" y su genero es: ";
        if(gender==Gender.FANTASY) msg+="fantasia";
        if(gender==Gender.NOVEL) msg+="novela";
        if(gender==Gender.SCIENCE_FICTION) msg+="ciencia ficcion";
        return msg;
    }

    public void setEverything(String review, int gender, double price, int sold){
        this.review=review;
        switch(gender){
            case 1 -> this.gender=Gender.SCIENCE_FICTION;
            case 2 -> this.gender=Gender.FANTASY;
            case 3 -> this.gender=Gender.NOVEL;
        }
        this.price=price;
        this.sold=sold;
    }

    public double getPrice(){
        return price;
    }

    public void addSold(){
        sold+=1;
    }
}