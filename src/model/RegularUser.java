package model;

public class RegularUser extends User{

    public static final int MAXBOOKS = 5;
    public static final int MAXMAGAZINES = 2;

    public RegularUser(String name, String cc){
        super(name, cc);
    }

    public boolean hasSpaceForBook(){
        int counterBooks=0;
        int smallerIndex=library.size();
        if(!(smallerIndex<MAXBOOKS+MAXMAGAZINES)){
            smallerIndex=MAXBOOKS+MAXMAGAZINES;
        }
        for(int i=0; i<smallerIndex; i++){
            if(library.get(i)!=null && library.get(i).isBook()){
                counterBooks+=1;
            }
        }
        if(counterBooks>=MAXBOOKS){
            return false;
        }else{
            return true;
        }
    }

    public boolean hasSpaceForMagazine(){
        int counterMagazine=0;
        int smallerIndex=library.size();
        if(!(smallerIndex<MAXBOOKS+MAXMAGAZINES)){
            smallerIndex=MAXBOOKS+MAXMAGAZINES;
        }
        for(int i=0; i<smallerIndex; i++){
            if(library.get(i)!=null && library.get(i).isMagazine()){
                counterMagazine+=1;
            }
        }
        if(counterMagazine>=MAXMAGAZINES){
            return false;
        }else{
            return true;
        }
    }

}