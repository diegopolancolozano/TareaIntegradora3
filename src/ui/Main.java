package ui;

import java.util.Scanner;
import model.Controller;
import java.util.Calendar;

public class Main{

    private Controller controller;
    private Scanner reader;

    public Main(){
        this.controller = new Controller();
        this.reader = new Scanner(System.in);
    }

    public static void main(String[] args){
        Main m = new Main();
        
        System.out.println("Bienvenido al gestor de ReadX");
        m.menu();
    }

    public void menu(){
        int option;
        do{
            System.out.println("1. Registar usuario");
            System.out.println("2. Registar producto");
            System.out.println("3. Modificar Producto");
            System.out.println("4. Eliminar Producto");
            System.out.println("5. añadir producto a usuario");
            System.out.println("6. Leer producto de cliente");

            System.out.println("0. Salir");
            option = reader.nextInt();
            switch(option){
                case 1 -> System.out.println(addUser());
                case 2 -> System.out.println(addText());
                case 3 -> System.out.println(modifyProduct());
                case 4 -> System.out.println(eraseProduct());
                case 5 -> System.out.println(addTextToUser());
                case 6 -> System.out.println(readLibraryOfUser());
            }
        }while(option!=0);
    }

    public String addUser(){
        reader.nextLine();
        System.out.println("Inserte el nombre");
        String name=reader.nextLine();
        System.out.println("Inserte la cedula");
        String cc=reader.next();
        System.out.println("Inserte si es premium o si es usuario regular");
        System.out.println("1-> Si, 2-> no");
        int premiumOrNot = reader.nextInt();
        String msg=controller.addUser(name, cc, premiumOrNot);
        return msg;
    }

    public String addText(){
        reader.nextLine();
        System.out.println("Inserte el nombre del producto");
        String name=reader.nextLine();
        System.out.println("Inserte la fecha de publicación");
        System.out.println("año");
        int year = reader.nextInt();
        System.out.println("mes");
        int month = reader.nextInt();
        System.out.println("día");
        int day = reader.nextInt();
        Calendar publicationDate = Calendar.getInstance();
        publicationDate.set(Calendar.YEAR, year);
        publicationDate.set(Calendar.MONTH, month);
        publicationDate.set(Calendar.DAY_OF_MONTH, day);

        System.out.println("Inserte el link de la portada");
        String url=reader.next();
        System.out.println("Inserte páginas");
        int pages=reader.nextInt();


        System.out.println("Inserte si es una revista o si es un libro");
        System.out.println("1-> Libro, 2-> Revista");
        int decision = reader.nextInt();

        String msg;
        switch(decision){
            case 1:
            System.out.println("Inserte la reseña");
            reader.nextLine();
            String review=reader.nextLine();
            System.out.println("Inserte el precio");
            double price = reader.nextDouble();
            System.out.println("Inserte cantidad vendida");
            int sold = reader.nextInt();
            System.out.println("Inserte genero");
            System.out.println("1 -> ciencia ficción, 2 -> fantasia, 3 -> novela");
            int gender = reader.nextInt();

            System.out.println("Inserte el id:");
            String id;
            do{
                id=reader.next();
            }while(!controller.isHexadecimal(id) && controller.usedId(id));
            msg=controller.addText(name, id, publicationDate, url, pages, review, gender, price, sold);
            break;

            case 2:
            System.out.println("Inserte la periodicidad (mensual, semanal, etc)");
            reader.nextLine();
            String periodicity = reader.nextLine();
            System.out.println("Inserte valor de suscripción");
            double priceSuscriptions = reader.nextDouble();
            System.out.println("Inserte cantidad de suscripciones");
            int numberOfSuscriptions = reader.nextInt();
            System.out.println("Inserte categoria");
            System.out.println("1 -> variedades, 2 -> disenio, 3 -> cientifica");
            int category = reader.nextInt();

            System.out.println("Inserte el id:");
            String id2;
            do{
                id2=reader.next();
            }while(!controller.isAlphaNumeric(id2) && controller.usedId(id2));
            msg=controller.addText(name, id2, publicationDate, url, pages, category, periodicity, priceSuscriptions, numberOfSuscriptions);
            break;
            default:
                msg = "no valido";
            break;
        }
        return msg;
    }

    public String modifyProduct(){
        String msg="";
        int option=0;
        int index=0;
        int madeProducts = controller.getMadeProducts();
        System.out.println("Escoga un producto");

        if(madeProducts==0) {
            return "No hay productos";
        }
        while(option!=3 && option!=1 && index<madeProducts){
            if(index==madeProducts){
                return "No hay más";
            }else{
                System.out.println(controller.getProductInfo(index));
                System.out.println("1: Seleccionar, 2: Siguiente, 3: salir");
                option=reader.nextInt();
                if(option==2) index+=1;
            }
        }

        if(option==3){
            msg="saliendo...";
        }else if(index==madeProducts){
            System.out.println("No hay más");
        }else{
            System.out.println("Inserte nueva informacion");
            reader.nextLine();
            System.out.println("Inserte el nombre del producto");
            String name=reader.nextLine();
            System.out.println("Inserte la fecha de publicación");
            System.out.println("año");
            int year = reader.nextInt();
            System.out.println("mes");
            int month = reader.nextInt();
            System.out.println("día");
            int day = reader.nextInt();
            Calendar publicationDate = Calendar.getInstance();
            publicationDate.set(Calendar.YEAR, year);
            publicationDate.set(Calendar.MONTH, month);
            publicationDate.set(Calendar.DAY_OF_MONTH, day);

            System.out.println("Inserte el link de la portada");
            String url=reader.next();
            System.out.println("Inserte páginas");
            int pages=reader.nextInt();
        
            if(controller.isItBook(index)){
                System.out.println("Inserte la reseña");
                reader.nextLine();
                String review=reader.nextLine();
                System.out.println("Inserte el precio");
                double price = reader.nextDouble();
                System.out.println("Inserte cantidad vendida");
                int sold = reader.nextInt();
                System.out.println("Inserte genero");
                System.out.println("1 -> ciencia ficción, 2 -> fantasia, 3 -> novela");
                int gender = reader.nextInt();

                System.out.println("Inserte el id:");
                String id;
                do{
                    id=reader.next();
                }while(!controller.isHexadecimal(id) && controller.usedId(id));
                msg = controller.modifyProductBook(index, name, id, publicationDate, url, pages, review, gender, price, sold);
            }else{
                System.out.println("Inserte la periodicidad (mensual, semanal, etc)");
                reader.nextLine();
                String periodicity = reader.nextLine();
                System.out.println("Inserte valor de suscripción");
                double priceSuscription = reader.nextDouble();
                System.out.println("Inserte cantidad de suscripciones");
                int numberOfSuscriptions = reader.nextInt();
                System.out.println("Inserte categoria");
                System.out.println("1 -> variedades, 2 -> disenio, 3 -> cientifica");
                int category = reader.nextInt();

                System.out.println("Inserte el id:");
                String id;
                do{
                    id=reader.next();
                }while(!controller.isAlphaNumeric(id) && controller.usedId(id));
                msg = controller.modifyProductMagazine(index, name, id, publicationDate, url, pages, category, periodicity, priceSuscription, numberOfSuscriptions);
            }
        }
        return msg;
    }

    public String eraseProduct(){
        String msg="";
        int option=0;
        int index=0;
        int madeProducts = controller.getMadeProducts();
        System.out.println("Escoga un producto");

        if(madeProducts==0) {
            return "No hay productos";
        }
        while(option!=3 && option!=1 && index<madeProducts){
            if(index==madeProducts){
                return "No hay más";
            }else{
                System.out.println(controller.getProductInfo(index));
                System.out.println("1: Seleccionar, 2: Siguiente, 3: salir");
                option=reader.nextInt();
                if(option==2) index+=1;
            }
        }
        if(option==3){
            msg="saliendo...";
        }else if(index==madeProducts){
            System.out.println("No hay más");
        }else{
            msg = controller.eraseProduct(index);
        }
        return msg;
    }

    public String addTextToUser(){
        String msg="";
        int indexUser = choseUser();
        int indexProduct = choseProduct();
        if(indexUser<0){
            msg+="No mas usuarios";
        }else if(indexProduct<0){
            msg="No hay mas libros";
        }else{
            msg=controller.addTextToUser(indexProduct, indexUser);
        }
        return msg;
    }

    public int choseUser(){
        int option=0;
        int indexUser=0;
        int madeUsers = controller.getMadeUsers();
        if(madeUsers==0) {
            return -1;
        }
        System.out.println("Escoje un usuario");
        while(option!=1){
            if(indexUser==madeUsers){
                System.out.println("No hay más usuarios");
                return -2;
            }else{
                System.out.println(controller.getUserInfo(indexUser));
                System.out.println("1: Seleccionar, 2: Siguiente");
                option=reader.nextInt();
                if(option==2) indexUser+=1;
            }
        }
        return indexUser;
    }

    public int choseProduct(){
        int option=0;
        int indexProduct=0;
        int madeProducts = controller.getMadeProducts();
        if(madeProducts==0) {
            return -1;
        }
        System.out.println("Escoje un producto");
        while(option!=1){
            if(indexProduct==madeProducts){
                System.out.println("No hay más usuarios");
                return -2;
            }else{
                System.out.println(controller.getProductInfo(indexProduct));
                System.out.println("1: Seleccionar, 2: Siguiente");
                option=reader.nextInt();
                if(option==2) indexProduct+=1;
            }
        }
        return indexProduct;
    }

    public String readLibraryOfUser(){
        int indexUser = choseUser();
        int actualPageLibrary = 0;
        int option=10;
        if(indexUser<0){
            return "No hay usuario";
        }else if(!controller.userHasProducts(indexUser)){
            return "No tiene productos";
        }else{
            while(option!=0){
                System.out.println(controller.getLibraryPage(indexUser, actualPageLibrary));
                System.out.println("Digite 1 para ver la siguiente pagina");
                System.out.println("Digite 2 para ver la pagina anterior");
                System.out.println("Digite 3 para leer");
                System.out.println("Digite 0 para salir");
                option=reader.nextInt();
                switch(option){
                    case 1:
                        actualPageLibrary+=1;
                    break;

                    case 2:
                    if(actualPageLibrary==0){
                        option=0;
                    }else{
                        actualPageLibrary-=1;
                    }break;
                    case 3:
                        read(indexUser, actualPageLibrary);
                        break;
                    default:
                    System.out.println("Opcion no valida");
                    break;
                }
            }
        }
        return "volviendo al menu...";
    }

    public void read(int indexUser, int actualPageLibrary){
        System.out.println("Seleccionar mediante \n1. id \n2. Cordenada");
        int method=reader.nextInt();
        int actualPageProduct = 0;

        int option=10;
        switch(method){
            case 1:
            System.out.println("Inserte id");
            String id = reader.next();
            while(option!=0){
                System.out.println(controller.getProductPage(indexUser, id, actualPageProduct));
                System.out.println("1. siguiente página");
                System.out.println("2. anterior página");
                System.out.println("0. salir");
                option=reader.nextInt();
                switch(option){
                    case 1:
                    actualPageProduct+=1;
                    break;
                    case 2:
                    actualPageProduct-=1;
                    break;
                    case 3:
                    System.out.println("volviendo");
                    controller.addPages(id, actualPageProduct);
                    break;
                }
            }

            break;
            case 2:
            System.out.println("Inserte coordenada x");
            int x = reader.nextInt();
            System.out.println("Inserte coordenada y");
            int y = reader.nextInt();
            while(option!=0){
                System.out.println(controller.getProductPageByCoordenates(indexUser, actualPageLibrary, actualPageProduct, x, y));
                System.out.println("1. siguiente página");
                System.out.println("2. anterior página");
                System.out.println("0. salir");
                option=reader.nextInt();
                switch(option){
                    case 1:
                    actualPageProduct+=1;
                    break;
                    case 2:
                    actualPageProduct-=1;
                    break;
                    case 3:
                    System.out.println("volviendo");
                    controller. addPagesByCoordenates(indexUser, actualPageLibrary, actualPageProduct, x, y, actualPageProduct);
                    break;
                }
            }
            
            break;
            default:
            System.out.println("Saliendo");
        }
    }
}