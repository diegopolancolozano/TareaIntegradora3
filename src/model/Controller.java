package model;

public class Controller{
    private Bibliographic[] library;

    public Controller(){
        library = new Bibliographic[10];
    }

    public void initBooks(){
        for(int i=0; i<10; i++){
            library[i] = new Book("ey", 2, "oi");
        }
    }

    public void getBooks(){
        for(int i=0; i<10; i++){
            System.out.println(((Book)(library[i])).getAuthor());
        }
    }
}