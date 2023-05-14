package model;

import java.util.Calendar;
public abstract class Bibliographic{

    private String name;
    private String id;
    private Calendar publicationDate;
    private String urlImage;
    private int pages;
    private int readedPages;

    public Bibliographic(String name, String id, Calendar publicationDate, String urlImage, int pages){
        this.name=name;
        this.id=id;
        this.publicationDate=publicationDate;
        this.urlImage=urlImage;
        this.pages=pages;
        this.readedPages=0;
    }

    public void setCommon(String name, String id, Calendar publicationDate, String urlImage, int pages){
        this.name=name;
        this.id=id;
        this.publicationDate=publicationDate;
        this.urlImage=urlImage;
        this.pages=pages;
    }

    protected String getCommon(){
        String msg="El producto: " + name+ ":\n";
        msg+="id: "+id;
        msg+=", fecha de publicación: " + publicationDate.get(Calendar.DAY_OF_MONTH) + "/" + publicationDate.get(Calendar.MONTH) + "/" +publicationDate.get(Calendar.YEAR) + "\n";
        msg+="url: "+urlImage;
        msg+=", paginas: "+pages;
        msg+=" y paginas leidas: "+readedPages;
        return msg;
    }

    public String getId(){
        return id;
    }

    public abstract String getAllInfo();

    public String getProductPage(int actualPageProduct){
        if(actualPageProduct>pages){
            actualPageProduct=actualPageProduct%pages;
        }
        if(actualPageProduct<0){
            actualPageProduct=0;
        }
        String msg = "Producto: " + name + "\n Estas leyendo la página: " + actualPageProduct;
        return msg;
    }

    public void addReadedPages(int readedpages){
        this.readedPages+=readedPages;
    }

}