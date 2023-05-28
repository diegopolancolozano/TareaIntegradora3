package model;

import java.util.Calendar;
import java.util.ArrayList;
import java.util.Random;

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

        Calendar date2 = Calendar.getInstance();
        date2.set(Calendar.YEAR, 1985);
        date2.set(Calendar.MONTH, Calendar.OCTOBER);
        date2.set(Calendar.DAY_OF_MONTH, 20);

        texts.add(new Book("metamorfosis kafka", "000" , date , "metamorfosisDeKafka.com", 53, "Un hombre se vuelve bicho", 3, 12.5));
        texts.add(new Book("StarWars episodio 1", "001" , date2 , "StarWars.com", 288, "Hombres lunares pelean", 1, 30));
        texts.add(new Book("Harry Potter La reliquia o caliz de fuego", "002" , date2, "HarryPotter.com", 220, "Un chico miope aprende magía", 2, 25));
        texts.add(new Magazine("Maestro Legal", "003", date, "SecretariaDeEducacion.com", 40, 3, "semanal", 4));
        texts.add(new Magazine("El Q'hubo", "004", date, "Qhubo.com", 8, 1, "semanal", 0));
        texts.add(new Magazine("WebDesigner", "005", date, "WebDesigner.com", 100, 2, "mensual", 10));
    }
    
    public String addText(String name, Calendar publicationDate, String url, int pages, int category, String periodicity, double priceSuscriptions){
        texts.add(new Magazine(name, generateHexadecimalCode(), publicationDate, url, pages, category, periodicity, priceSuscriptions));
        return texts.get(texts.size()-1).getAllInfo();
    }

    public String addText(String name, Calendar publicationDate, String url, int pages, String review, int gender, double price){
        texts.add(new Book(name, generateAlphanumericCode(), publicationDate, url, pages, review, gender, price));
        return texts.get(texts.size()-1).getAllInfo();
    }

    public int getIndexUserByName(String nickname){
        int index = -1;
        for(int i=0; i<users.size(); i++){
            if(users.get(i).getName().equalsIgnoreCase(nickname)){
                index = i;
            }
        }
        return index;
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

    public boolean isItMagazine(int index){
        if(texts.get(index) instanceof Magazine){
            return true;
        }
        return false;
    }

    public String modifyProductBook(int index, String name, Calendar publicationDate, String url, int pages, String review, int gender, double price){
        
        for(int i=0; i<users.size(); i++){
            users.get(i).setBill(texts.get(index).getId(), price, 1, publicationDate);
        }

        texts.get(index).setCommon(name, publicationDate, url, pages);
        ((Book)(texts.get(index))).setEverything(review, gender, price);
        return "Producto modificado";
    }
    public String modifyProductMagazine(int index, String name, Calendar publicationDate, String url, int pages, int category, String periodicity, double priceSuscription){
        
        for(int i=0; i<users.size(); i++){
            users.get(i).setBill(texts.get(index).getId(), priceSuscription, 2, publicationDate);
        }

        texts.get(index).setCommon(name, publicationDate, url, pages);
        ((Magazine)(texts.get(index))).setDetails(category, periodicity, priceSuscription);
        return "Producto modificado";
    }

    public String eraseProduct(int index){
        String id = texts.get(index).getId();
        texts.remove(index);
        for(int i=0; i<users.size(); i++){
            users.get(i).eraseProductOfLibrary(id);
        }
        return "eliminado con exito";
    }

    public String addTextToUser(int indexText, int indexUser){
        String msg="";
        String idText=texts.get(indexText).getId();
        double price=0;
        int typeProduct=0;
        boolean hasSpace=true;

        if(users.get(indexUser) instanceof RegularUser){
            if(texts.get(indexText) instanceof Book) hasSpace = ((RegularUser)users.get(indexUser)).hasSpaceForBook();
            if(texts.get(indexText) instanceof Magazine) hasSpace = ((RegularUser)users.get(indexUser)).hasSpaceForMagazine();
        }

        if(hasSpace){

            if(texts.get(indexText) instanceof Book){
                price = ((Book)(texts.get(indexText))).getPrice();
                ((Book)texts.get(indexText)).addSold();
                typeProduct=1;
            }
            
            if(texts.get(indexText) instanceof Magazine){
                price = ((Magazine)(texts.get(indexText))).getPrice();
                ((Magazine)texts.get(indexText)).addSuscription();
                typeProduct=2;
            } 

            msg=users.get(indexUser).addBill(idText, price, typeProduct, texts.get(indexText).getPublicationDate());
        }else{
            msg="No hay espacio";
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
        String msg = "El id no existe o el usuario no lo tiene";
        if(doesUserHaveCertainProduct(indexUser, id)){
            int leftPage = users.get(indexUser).getLeftPage(id);
            msg = texts.get(getIndexProductById(id)).getProductPage(actualPageProduct + leftPage);
            if(actualPageProduct==0 || isItBook(getIndexProductById(id)) && actualPageProduct%20==0 || isItMagazine(getIndexProductById(id)) && actualPageProduct%20==0){
                msg += "\n" + addAddvertise();
            }
        }
        return msg;
    }

    public String getProductPageByCoordenates(int indexUser, int actualPageLibrary, int actualPageProduct, int x, int y){
        String msg = "El id no existe o el usuario no lo tiene";
        String id = users.get(indexUser).getIdByCoordenates(actualPageLibrary, x, y);
        int index = getIndexProductById(id);
        int leftPage = users.get(indexUser).getLeftPage(id);
        if(doesUserHaveCertainProduct(indexUser, id)){
            msg = texts.get(index).getProductPage(actualPageProduct + leftPage);
            if(actualPageProduct==0 || isItBook(getIndexProductById(id)) && actualPageProduct%20==0 || isItMagazine(getIndexProductById(id)) && actualPageProduct%5==0){
                msg += "\n" + addAddvertise();
            }
        }
        return msg;
    }

    public void addPages(int indexUser, String id, int readedPages){
        texts.get(getIndexProductById(id)).addReadedPages(readedPages);
        users.get(indexUser).addReadedPages(id, readedPages);
    }

    public void addPagesByCoordenates(int indexUser, int actualPageLibrary, int actualPageProduct, int x, int y, int readedPages){
        String id = users.get(indexUser).getIdByCoordenates(actualPageLibrary, x, y);
        texts.get(getIndexProductById(id)).addReadedPages(readedPages);
        users.get(indexUser).addReadedPages(id, readedPages);
    }

    public String eraseProductOfUserById(int index, String id){
        String msg = "";
        if(users.get(index).eraseProductOfLibrary(id)){
            msg = "Borrado";
        }else{
            msg = "No se encontró";
        }
        return msg;
    }

    public String eraseProductOfUserByName(int index, String name){
        String id = getIdOfTextByName(name);
        String msg = eraseProductOfUserById(index, id);
        return msg;
    }

    public String getIdOfTextByName(String name){
        String msg = "";
        for(int i=0; i<texts.size(); i++){
            if(texts.get(i).getName().equalsIgnoreCase(name)){
                msg = texts.get(i).getId();
            }
        }
        return msg;
    }
    
    public int getIndexUserById(String idUser){
        int index = -1;
        for(int i=0; i<users.size(); i++){
            if(users.get(i).getCC().equalsIgnoreCase(idUser)){
                index = i;
            }
        }
        return index;
    }

    public int getIndexProductByName(String name){
        int index = -1;
        for(int i=0; i<texts.size() && index == -1; i++){
            if(texts.get(i).getName().equalsIgnoreCase(name)){
                index = i;
            }
        }
        return index;
    }

    public String addAddvertise(){
        String[] ads = {
            "¡Suscríbete al Combo Plus y llévate Disney+ y Star+ a un precio increíble!",
            "Ahora tus mascotas tienen una app favorita: Laika. Los mejores productos para tu peludito.",
            "¡Estamos de aniversario! Visita tu Éxito más cercano y sorpréndete con las mejores ofertas."};

            Random random = new Random();
            int index = random.nextInt(ads.length);
            return ads[index];

    }

    public String generateAlphanumericCode() {
        String alphanumericCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        String code = "";

        do{
            code = "";
            for (int i = 0; i < 3; i++) {
                int index = random.nextInt(alphanumericCharacters.length());
                char character = alphanumericCharacters.charAt(index);
                code += character;
            }
        }while(usedId(code));

        return code.toString();
    }

    public String generateHexadecimalCode() {
        String hexaCharacters = "0123456789abcdefABCDEF";
        Random random = new Random();
        String code = "";

        do{
            code = "";
            for (int i = 0; i < 3; i++) {
                int index = random.nextInt(hexaCharacters.length());
                char character = hexaCharacters.charAt(index);
                code += character;
            }
        }while(usedId(code));

        return code.toString();
    }

    public String showAmountOfPagesPerProduct(){
        String msg = "";
        int readPagesBooks = 0;
        int readPagesMagazine = 0;
        for(int i=0; i<texts.size(); i++){
            if(texts.get(i) instanceof Book){
                readPagesBooks += texts.get(i).getReadedPages();
            }
            if(texts.get(i) instanceof Magazine){
                readPagesMagazine += texts.get(i).getReadedPages();
            }
        }

        msg = "Cantidad de paginas leidas de:\nLibros: "+readPagesBooks+"\nRevistas: "+readPagesMagazine;
        return msg; 
    }

    public String showMostPopularGender(){
        String msg = "";
        int readPagesScienceFiction = 0;
        int readPagesFantasy = 0;
        int readPagesNovel = 0;
        int readPagesVarieties = 0;
        int readPagesDesign = 0;
        int readPagesScientific = 0;
        int type;

        for(int i=0; i<texts.size(); i++){
            type = 0;
            if(texts.get(i) instanceof Book){
                type = ((Book)(texts.get(i))).getGenre();
                switch(type){
                    case 1 -> readPagesScienceFiction += texts.get(i).getReadedPages();
                    case 2 -> readPagesFantasy += texts.get(i).getReadedPages();
                    case 3 -> readPagesNovel += texts.get(i).getReadedPages();
                }
            }else if(texts.get(i) instanceof Magazine){
                type = ((Magazine)(texts.get(i))).getCategory();
                switch(type){
                    case 1 -> readPagesVarieties += texts.get(i).getReadedPages();
                    case 2 -> readPagesDesign += texts.get(i).getReadedPages();
                    case 3 -> readPagesScientific += texts.get(i).getReadedPages();
                }
            }
        }

        msg += "El genero más popular es: \n";
        if(readPagesScienceFiction>=readPagesFantasy && readPagesScienceFiction>=readPagesNovel){
            msg += "ciencia ficcion con " + readPagesScienceFiction + " paginas leidas\n";
        }
        if(readPagesFantasy>=readPagesScienceFiction && readPagesFantasy>=readPagesNovel){
            msg += "fantasia con " + readPagesFantasy + " paginas leidas\n";
        }
        if(readPagesNovel>=readPagesScienceFiction && readPagesNovel>=readPagesFantasy){
            msg += "novela con " + readPagesNovel + " paginas leidas\n";
        }


        msg += "\nLa categoria más popular es: \n";
        if(readPagesScientific>=readPagesDesign && readPagesScientific>=readPagesVarieties){
            msg += "cientifica con " + readPagesScientific + " paginas leidas\n";
        }
        if(readPagesVarieties>=readPagesScientific && readPagesVarieties>=readPagesDesign){
            msg += "variedades con " + readPagesVarieties + " paginas leidas\n";
        }
        if(readPagesDesign>=readPagesScientific && readPagesDesign>=readPagesVarieties){
            msg += "diseño con " + readPagesDesign + " paginas leidas\n";
        }
        
        return msg; 
    }

    public String top5(){
        String msg = "";

        ArrayList<Bibliographic> topBooks = new ArrayList<Bibliographic>();
        ArrayList<Bibliographic> topMagazines = new ArrayList<Bibliographic>();

        for(int i=0; i<texts.size(); i++){
            if(texts.get(i) instanceof Book){
                topBooks.add(texts.get(i));
            }else if(texts.get(i) instanceof Magazine){
                topMagazines.add(texts.get(i));
            }
        }

        topBooks = sortTop5Pages(getTop5Pages(topBooks));
        topMagazines = sortTop5Pages(getTop5Pages(topMagazines));

        msg+="Top Libros:\n";
        for(int i=0; i<topBooks.size(); i++){
            msg += "\n\ntop "+(i+1)+": "+topBooks.get(i).getAllInfo();
        }
        msg+="\nTop Revistas:\n";
        for(int i=0; i<topMagazines.size(); i++){
            msg += "\n\ntop "+(i+1)+": "+topMagazines.get(i).getAllInfo();
        }


        return msg;
    }

    public ArrayList<Bibliographic> getTop5Pages(ArrayList<Bibliographic> list){
        int lowest = list.get(0).getReadedPages();
        int indexLowest = 0;
        while(list.size()>5){
            for(int i=0; i<list.size(); i++){
                if(lowest<list.get(i).getReadedPages()){
                    lowest=list.get(i).getReadedPages();
                    indexLowest=i;
                }
            }
            list.remove(indexLowest);
        }
        return list;
    }

    public ArrayList<Bibliographic> sortTop5Pages(ArrayList<Bibliographic> list){
        Bibliographic tempProduct = list.get(0);
        boolean sorted = false;
        while(!sorted){
            sorted = true;
            for(int i=0; i<list.size()-1; i++){
                if(list.get(i)!=null && list.get(i+1)!=null && list.get(i).getReadedPages()<list.get(i+1).getReadedPages()){
                    tempProduct = list.get(i);
                    list.set(i, list.get(i+1));
                    list.set(i+1, tempProduct);
                    sorted = false;
                }
            }
        }
        return list;
    }

    public String ammountSoldBooks(){
        String msg = "";
        double sumPricesScienceFiction = 0;
        int sumPagesScienceFiction = 0;
        double sumPricesFantasy = 0;
        int sumPagesFantasy = 0;
        double sumPricesNovel = 0;
        int sumPagesNovel = 0;

        int type = 0;
        for(int i=0; i<texts.size(); i++){
            if(texts.get(i) instanceof Book){
                type = ((Book)(texts.get(i))).getGenre();
                switch(type){
                    case 1:
                    sumPricesScienceFiction += ((Book)(texts.get(i))).getPrice() * ((Book)(texts.get(i))).getSold();
                    sumPagesScienceFiction += texts.get(i).getReadedPages();
                    break;
                    case 2:
                    sumPricesFantasy += ((Book)(texts.get(i))).getPrice() * ((Book)(texts.get(i))).getSold();
                    sumPagesFantasy += texts.get(i).getReadedPages();
                    break;
                    case 3:
                    sumPricesNovel += ((Book)(texts.get(i))).getPrice() * ((Book)(texts.get(i))).getSold();
                    sumPagesNovel += texts.get(i).getReadedPages();
                    break;
                }
            }
        }

        msg += "Novela: \nCantidad generada: "+sumPricesNovel+"\nPaginas leidas: "+sumPagesNovel;
        msg += "\nCiencia Ficcion: \nCantidad generada: "+sumPricesScienceFiction+"\nPaginas leidas: "+sumPagesScienceFiction;
        msg += "\nFantasia: \nCantidad generada: "+sumPricesFantasy+"\nPaginas leidas :"+sumPagesFantasy;

        return msg;
    }

    public String ammountSoldMagazines(){
        String msg = "";

        double sumPricesVarieties = 0;
        int sumPagesVarieties = 0;
        double sumPricesScientific = 0;
        int sumPagesScientific = 0;
        double sumPricesDesign = 0;
        int sumPagesDesign = 0;

        int type = 0;
        for(int i=0; i<texts.size(); i++){
            if(texts.get(i) instanceof Magazine){
                type = ((Magazine)(texts.get(i))).getCategory();
                switch(type){
                    case 1:
                    sumPricesVarieties += ((Magazine)(texts.get(i))).getPrice() * ((Magazine)(texts.get(i))).getSold();
                    sumPagesVarieties += texts.get(i).getReadedPages();
                    break;
                    case 2:
                    sumPricesDesign += ((Magazine)(texts.get(i))).getPrice() * ((Magazine)(texts.get(i))).getSold();
                    sumPagesDesign += texts.get(i).getReadedPages();
                    break;
                    case 3:
                    sumPricesDesign += ((Magazine)(texts.get(i))).getPrice() * ((Magazine)(texts.get(i))).getSold();
                    sumPagesDesign += texts.get(i).getReadedPages();
                    break;
                }
            }
        }

        msg += "Variedades: \nCantidad generada: "+sumPricesVarieties+"\nPaginas leidas: "+sumPagesVarieties;
        msg += "\nDiseño: \nCantidad generada: "+sumPricesDesign+"\nPaginas leidas: "+sumPagesDesign;
        msg += "\nCientifica: \nCantidad generada: "+sumPricesScientific+"\nPaginas leidas :"+sumPagesScientific;

        return msg;
    }
}