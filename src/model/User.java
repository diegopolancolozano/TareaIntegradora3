package model;

import java.util.Calendar;
import java.util.ArrayList;
public abstract class User{
    
    public static final int SIZElIBRARY = 5;
    private String name;
    private String cc;
    private Calendar joiningDate;
    
    protected ArrayList<Bill> library;

    public User(String name, String cc){
        this.name=name;
        this.cc=cc;
        this.joiningDate= Calendar.getInstance();
        this.library = new ArrayList<Bill>();
    }

    @Override
    public String toString(){
        String stringDate = "";
        stringDate += joiningDate.get(Calendar.DAY_OF_MONTH) + "/";
        stringDate += joiningDate.get(Calendar.MONTH) + "/";
        stringDate += joiningDate.get(Calendar.YEAR);
        return "Usuario: " + name + ", de cedula:" + cc + "\nfecha de union:" + stringDate + "\n";
    }

    public String addBill(String id, double moneyPaid, int typeProduct, Calendar publicationDate){
        library.add(new Bill(id, moneyPaid, typeProduct, publicationDate));
        String msg = library.get(library.size()-1).toString();
        organizeLibrary();
        return msg;
    }

    public ArrayList<String> getIds(){
        ArrayList<String> arrayIds = new ArrayList<String>();
        for(int i=0; i<library.size(); i++){
            arrayIds.add(library.get(i).getId());
        }
        return arrayIds;
    }

    public void setBill(String originalId, double moneyPaid, int typeProduct, Calendar publicationDate){
        for(int i=0; i<library.size(); i++){
            if(library.get(i).getId().equals(originalId)){
                library.get(i).setBill(moneyPaid, typeProduct, publicationDate);
            }
        }
        organizeLibrary();
    }

    public int getSizeLibrary(){
        return library.size();
    }

    public String showPageLibrary(int page){
        String msg = "Librería pagina:" + page + "\n";
        msg +="   |  0  |  1  |  2  |  3  |  4  |\n";
        int realIndex = SIZElIBRARY*SIZElIBRARY*page;
        for(int i=0; i<SIZElIBRARY; i++){
            msg+=" " +i+ " |";
            for(int j=0; j<SIZElIBRARY; j++){
                if(realIndex+i*SIZElIBRARY+j>=library.size()){
                    msg+=" --- |";
                }else{
                    msg+=" " + library.get(realIndex + j + i*SIZElIBRARY).getId() + " |";
                }
            }
            msg+="\n";
        }
        return msg;
    }

    public boolean doesUserHaveCertainProduct(String id){
        boolean hasProduct = false;
        for(int i=0; i<library.size(); i++){
            if(library.get(i).getId().equals(id)){
                hasProduct = true;
            }
        }
        return hasProduct;
    }

    public String getIdByCoordenates(int actualPageLibrary, int x, int y){
        int actualIndex = SIZElIBRARY*SIZElIBRARY*actualPageLibrary+((x)+y*SIZElIBRARY);
        if(actualIndex>=library.size()){
            return "No Existe";
        }
        String id = library.get(actualIndex).getId();
        return id;
    }

    public boolean eraseProductOfLibrary(String id){
        boolean erased = false;
        for(int i=0; i<library.size() && !erased; i++){
            if(library.get(i).getId().equals(id)){
                library.remove(i);
                erased = true;
            }
        }
        organizeLibrary();
        return erased;
    }

    public String getName(){
        return this.name;
    }

    public String getCC(){
        return cc;
    }

public void organizeLibrary() {
    boolean isOrganized = false;
    Bill tempBill = new Bill();
    if (library != null && library.size() > 1){
        do {
            isOrganized = false;
            for (int i = 0; i < library.size() - 1; i++) {
                Calendar publicationDate1 = library.get(i).getPublicationDate();
                Calendar publicationDate2 = library.get(i + 1).getPublicationDate();
                if (publicationDate1 != null && publicationDate2 != null &&
                    publicationDate1.compareTo(publicationDate2)<0) {
                    tempBill = library.get(i);
                    library.set(i, library.get(i + 1));
                    library.set(i + 1, tempBill);
                    isOrganized = true;
                }
            }
        } while (isOrganized);
    }
}

public int getLeftPage(String idProduct){
    return library.get(getIndexProductById(idProduct)).getLeftPage();
}

public int getIndexProductById(String id){
    int index = -1;
    for(int i=0; i<library.size(); i++){
        if(library.get(i).getId().equals(id)){
            index = i;
        }
    }
    return index;
}

public void addReadedPages(String id, int readedPages){
    int index = getIndexProductById(id);
    library.get(index).addReadedPages(readedPages);
}

}