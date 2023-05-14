package model;

import java.util.Calendar;
public class Magazine extends Bibliographic{

    private Category category;
    private String periodicity;
    private double priceSuscription;
    private int numberOfSuscriptions;

    public Magazine(String name, String id, Calendar publicationDate, String urlImage, int pages, int category, String periodicity, double priceSuscription, int numberOfSuscriptions){
        super(name, id, publicationDate, urlImage, pages);
        switch(category){
            case 1 -> this.category=Category.VARIETIES;
            case 2 -> this.category=Category.DESIGN;
            case 3 -> this.category=Category.SCIENTIFIC;
        }
        this.periodicity=periodicity;
        this.priceSuscription=priceSuscription;
        this.numberOfSuscriptions=numberOfSuscriptions;
    }

    @Override
    public String getAllInfo(){
        String msg=getCommon();
        msg+="\n";
        msg+="Periodicidad: " + periodicity;
        msg+=", precio de suscripción: " + priceSuscription;
        msg+=", Suscripciones: " + numberOfSuscriptions;
        msg+=" y su categoría es:";
        if(category==Category.VARIETIES) msg+="variedades";
        if(category==Category.DESIGN) msg+="diseño";
        if(category==Category.SCIENTIFIC) msg+="cientifica";
        return msg;
    }

    public void setDetails(int category, String periodicity, double priceSuscription, int numberOfSuscriptions){
        switch(category){
            case 1 -> this.category=Category.VARIETIES;
            case 2 -> this.category=Category.DESIGN;
            case 3 -> this.category=Category.SCIENTIFIC;
        }
        this.periodicity=periodicity;
        this.priceSuscription=priceSuscription;
        this.numberOfSuscriptions=numberOfSuscriptions;
    }

    public double getPriceSuscriptions(){
        return priceSuscription;
    }

    public void addSuscription(){
        numberOfSuscriptions+=1;
    }
}