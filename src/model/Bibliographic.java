package model;

public abstract class Bibliographic{
    private String text;
    private int pages; 

    public Bibliographic(String text, int pages){
        this.text=text;
        this.pages=pages;
    }
}