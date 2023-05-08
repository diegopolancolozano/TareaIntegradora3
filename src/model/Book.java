package model;

public class Book extends Bibliographic{

    private String author;

    public Book(String text, int pages, String author){
        super(text, pages);
        this.author=author;
    }

    public String getAuthor(){
        return author;
    }
}