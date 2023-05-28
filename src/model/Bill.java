package model;

import java.util.Calendar;

public class Bill implements Cloneable{

    private String idProduct;
    private Calendar transactionDate;
    private double moneyPaid;
    private TypeProduct typeProduct;
    private int leftPage;
    private Calendar publicationDate;

    public Bill(String id, double moneyPaid, int typeProduct, Calendar publicationDate){
        this.idProduct=id;
        this.transactionDate=Calendar.getInstance();
        this.moneyPaid=moneyPaid;
        switch (typeProduct) {
            case 1:
                this.typeProduct=TypeProduct.BOOK;
                break;
        
            case 2:
                this.typeProduct=TypeProduct.MAGAZINE;
                break;
        }
        this.leftPage = 0;
        this.publicationDate=publicationDate;
    }

    public Bill(){

    }

    public String toString(){
        String date = transactionDate.get(Calendar.DAY_OF_MONTH) + "/" + transactionDate.get(Calendar.MONTH) + "/" +transactionDate.get(Calendar.YEAR);
        return "id: " + idProduct + ", fecha: " + date + ", monto: " + moneyPaid;
    }

    public boolean isBook(){
        if(typeProduct==TypeProduct.BOOK){
            return true;
        }else{
            return false;
        }
    }

    public boolean isMagazine(){
        if(typeProduct==TypeProduct.MAGAZINE){
            return true;
        }else{
            return false;
        }
    }

    public String getId(){
        return idProduct;
    }

    public void setBill(double moneyPaid, int typeProduct, Calendar publicationDate){
        this.moneyPaid = moneyPaid;
        this.publicationDate = publicationDate;
        switch (typeProduct) {
            case 1:
                this.typeProduct=TypeProduct.BOOK;
                break;
        
            case 2:
                this.typeProduct=TypeProduct.MAGAZINE;
                break;
        }
    }

    public Calendar getPublicationDate(){
        return publicationDate;
    }

    public int getLeftPage(){
        return leftPage;
    }

    public void addReadedPages(int readedPages){
        this.leftPage += readedPages;
    }

}