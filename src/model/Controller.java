package model;

import java.util.Calendar;
import java.util.ArrayList;

public class Controller{

    private ArrayList<User> users;
    private ArrayList<Bibliographic> texts;

    public Controller(){
        this.users = new ArrayList<User>();
        this.texts = new ArrayList<Bibliographic>();
        initModel();
    }

    public String addUser(String name, String cc, int premium){
        String msg = "añadido";
       
        switch(premium){
            case 1 -> users.add(new Premium(name, cc));
            case 2 -> users.add(new RegularUser(name, cc));
            default -> msg = "No valida";
        }
        return msg;
    }

    private void initModel(){
        Calendar date = Calendar.getInstance();
        users.add(new RegularUser("Jose maria lozano garcia", "666"));
        users.add(new Premium("maria jose garcia Lozano", "a00399926"));
        date.set(Calendar.YEAR, 1915);
        date.set(Calendar.MONTH, Calendar.OCTOBER);
        date.set(Calendar.DAY_OF_MONTH, 20);
        texts.add(new Book("metamorfosis kafka", "000" , date , "metamorfosisDeKafka.com", 53, "Un hombre se vuelve bicho", 3, 12.5, 0));
        texts.add(new Book("StarWars episodio 1", "001" , date , "StarWars.com", 288, "Hombres lunares pelean", 1, 30, 0));
        texts.add(new Book("Harry Potter La reliquia o caliz de fuego", "003" , date , "HarryPotter.com", 220, "Un chico miope aprende magía", 2, 25, 0));
        texts.add(new Magazine("Maestro Legal", "004", date, "SecretariaDeEducacion.com", 40, 3, "semanal", 4, 0));
        texts.add(new Magazine("El Q'hubo", "005", date, "Qhubo.com", 8, 1, "semanal", 0, 0));
        texts.add(new Magazine("WebDesigner", "005", date, "WebDesigner.com", 100, 2, "mensual", 10, 0));

    }

    public boolean isHexadecimal(String data){
        String validString = "0123456789abcdefABCDEF";
        int validCharts = 0;
        if(data.length() == 3){
            for(int i=0; i<3; i++){
                if(validString.indexOf(data.charAt(i))!=-1){
                    validCharts+=1;
                }
            }
        }
        if(validCharts==3){
            return true;
        }else{
            return false;
        }
    }
    
    public String addText(String name, String id, Calendar publicationDate, String url, int pages, int category, String periodicity, double priceSuscriptions, int numberOfSuscriptions){
        String msg = "añadido";
        texts.add(new Magazine(name, id, publicationDate, url, pages, category, periodicity, priceSuscriptions, numberOfSuscriptions));
        return msg;
    }

    public String addText(String name, String id, Calendar publicationDate, String url, int pages, String review, int gender, double price, int sold){
        String msg = "añadido";
        texts.add(new Book(name, id, publicationDate, url, pages, review, gender, price, sold));
        return msg;
    }

    public boolean usedId(String id){
        boolean repeated = false;
        for(int i=0; i<texts.size(); i++){
            if(texts.get(i) != null && texts.get(i).getId().equals(id)){
                repeated = true;
            }
        }
        return repeated;
    }
    
    public boolean isAlphaNumeric(String data){
        String validString = "0123456789abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int validCharts = 0;
        if(data.length() == 3){
            for(int i=0; i<3; i++){
                if(validString.indexOf(data.charAt(i))!=-1){
                    validCharts+=1;
                }
            }
        }
        if(validCharts==3){
            return true;
        }else{
            return false;
        }
    }

    public int getMadeProducts(){
        return texts.size();
    }

    public int getMadeUsers(){
        return users.size();
    }

    public String getProductInfo(int index){
        String msg = texts.get(index).getAllInfo();
        return msg;
    }
    
    public boolean isItBook(int index){
        if(texts.get(index) instanceof Book){
            return true;
        }
        return false;
    }

    public String modifyProductBook(int index, String name, String id, Calendar publicationDate, String url, int pages, String review, int gender, double price, int sold){
        for(int i=0; i<users.size(); i++){
            users.get(i).setBill(texts.get(index).getId(), id, price, 1);
        }
        texts.get(index).setCommon(name, id, publicationDate, url, pages);
        ((Book)(texts.get(index))).setEverything(review, gender, price, sold);
        return "Producto modificado";
    }
    public String modifyProductMagazine(int index, String name, String id, Calendar publicationDate, String url, int pages, int category, String periodicity, double priceSuscription, int numberOfSuscriptions){
        for(int i=0; i<users.size(); i++){
            users.get(i).setBill(texts.get(index).getId(), id, priceSuscription, 2);
        }
        texts.get(index).setCommon(name, id, publicationDate, url, pages);
        ((Magazine)(texts.get(index))).setDetails(category, periodicity, priceSuscription, numberOfSuscriptions);
        return "Producto modificado";
    }

    public String eraseProduct(int index){
        texts.remove(index);
        return "eliminado con exito";
    }

    public String addTextToUser(int indexText, int indexUser){
        String msg="";
        String idText=texts.get(indexText).getId();
        double price=0;
        int typeProduct=0;
        boolean hasSpace=true;

        if(hasSpace){
            msg=users.get(indexUser).addBill(idText, price, typeProduct);
        }else{
            msg="No hay espacio";
        }

        if(texts.get(indexText) instanceof Book){
            price = ((Book)(texts.get(indexText))).getPrice();
            ((Book)texts.get(indexText)).addSold();
            typeProduct=1;
        }
        if(texts.get(indexText) instanceof Magazine){
            price = ((Magazine)(texts.get(indexText))).getPriceSuscriptions();
            ((Magazine)texts.get(indexText)).addSuscription();
            typeProduct=2;
        }   

        if(users.get(indexUser) instanceof RegularUser){
            if(texts.get(indexText) instanceof Book) hasSpace = ((RegularUser)users.get(indexUser)).hasSpaceForBook();
            if(texts.get(indexText) instanceof Magazine) hasSpace = ((RegularUser)users.get(indexUser)).hasSpaceForMagazine();
        }

        ArrayList<String> arrayIds = new ArrayList<String>();
        arrayIds=users.get(indexUser).getIds();
        for(int i=0; i<arrayIds.size(); i++){
            if(texts.get(indexText).getId().equals(arrayIds.get(i))){   //Si se repite el id
                return "Producto repetido";
            }
        }

        return msg;
    }

    public String getUserInfo(int index){
        return users.get(index).toString();
    }

    public boolean userHasProducts(int index){
        if(users.get(index).getSizeLibrary()>0){
            return true;
        }else{
            return false;
        }
    }

    public String getLibraryPage(int indexUser, int page){
        return users.get(indexUser).showPageLibrary(page);
    }

    public boolean doesUserHaveCertainProduct(int indexUser, String id){
        return users.get(indexUser).doesUserHaveCertainProduct(id);
    }

    public int getIndexProductById(String id){
        for(int i=0; i<texts.size(); i++){
            if(texts.get(i).getId().equals(id)) return i;
        }
        return -1;
    }

    public String getProductPage(int indexUser, String id, int actualPageProduct){
        if(doesUserHaveCertainProduct(indexUser, id)){
            return texts.get(getIndexProductById(id)).getProductPage(actualPageProduct);
        }else{
            return "El id no existe o el usuario no lo tiene";
        }
    }

    public String getProductPageByCoordenates(int indexUser, int actualPageLibrary, int actualPageProduct, int x, int y){
        String id = users.get(indexUser).getIdByCoordenates(actualPageLibrary, x, y);
        int index = getIndexProductById(id);
        if(doesUserHaveCertainProduct(indexUser, id)){
            return texts.get(index).getProductPage(actualPageProduct);
        }else{
            return "El id no existe o el usuario no lo tiene";
        }
    }

    public void addPages(String id, int readedPages){
        texts.get(getIndexProductById(id)).addReadedPages(readedPages);
    }

    public void addPagesByCoordenates(int indexUser, int actualPageLibrary, int actualPageProduct, int x, int y, int readedPages){
        String id = users.get(indexUser).getIdByCoordenates(actualPageProduct, x, y);
        texts.get(getIndexProductById(id)).addReadedPages(readedPages);
    }


}