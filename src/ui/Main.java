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
            System.out.println("6. Eliminar producto de un usuario");
            System.out.println("7. Leer producto de cliente");
            System.out.println("8. generar reporte");
            System.out.println("0. Salir");
            option = validateInteger();
            switch(option){
                case 0 -> System.out.println(exit());
                case 1 -> System.out.println(addUser());
                case 2 -> System.out.println(addText());
                case 3 -> System.out.println(modifyProduct());
                case 4 -> System.out.println(eraseProduct());
                case 5 -> System.out.println(addTextToUser());
                case 6 -> System.out.println(eraseAccountProduct());
                case 7 -> System.out.println(readLibraryOfUser());
                case 8 -> System.out.println(reportMenu());
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
        int premiumOrNot = validateInteger();
        String msg=controller.addUser(name, cc, premiumOrNot);
        return msg;
    }

    public String addText(){
        reader.nextLine();
        System.out.println("Inserte el nombre del producto");
        String name=reader.nextLine();
        System.out.println("Inserte la fecha de publicación");
        System.out.println("año");
        int year = validateInteger();
        System.out.println("mes");
        int month = validateInteger();
        System.out.println("día");
        int day = validateInteger();
        Calendar publicationDate = Calendar.getInstance();
        publicationDate.set(Calendar.YEAR, year);
        publicationDate.set(Calendar.MONTH, month);
        publicationDate.set(Calendar.DAY_OF_MONTH, day);

        System.out.println("Inserte el link de la portada");
        String url=reader.next();
        System.out.println("Inserte páginas");
        int pages = validateInteger();


        System.out.println("Inserte si es una revista o si es un libro");
        System.out.println("1-> Libro, 2-> Revista");
        int decision = validateInteger();

        String msg;
        switch(decision){
            case 1:
            System.out.println("Inserte la reseña");
            reader.nextLine();
            String review=reader.nextLine();
            System.out.println("Inserte el precio");
            double price = validateDouble();
            System.out.println("Inserte genero");
            System.out.println("1 -> ciencia ficción, 2 -> fantasia, 3 -> novela");
            int gender = validateInteger();

            msg=controller.addText(name, publicationDate, url, pages, review, gender, price);
            break;

            case 2:
            System.out.println("Inserte la periodicidad (mensual, semanal, etc)");
            reader.nextLine();
            String periodicity = reader.nextLine();
            System.out.println("Inserte valor de suscripción");
            double priceSuscriptions = validateDouble();
            System.out.println("Inserte categoria");
            System.out.println("1 -> variedades, 2 -> disenio, 3 -> cientifica");
            int category = validateInteger();

            msg = controller.addText(name, publicationDate, url, pages, category, periodicity, priceSuscriptions);
            break;
            default:
                msg = "no valido";
            break;
        }
        return msg;
    }

    public String modifyProduct(){
        String msg="";
        int index=0;
        index = selectProduct();

        if(index==-1){
            msg="no hay";
        }else{
            System.out.println("Inserte nueva informacion");
            System.out.println("Inserte el nombre del producto");
            String name=reader.nextLine();
            System.out.println("Inserte la fecha de publicación");
            System.out.println("año");
            int year = validateInteger();
            System.out.println("mes");
            int month = validateInteger();
            System.out.println("día");
            int day = validateInteger();
            Calendar publicationDate = Calendar.getInstance();
            publicationDate.set(Calendar.YEAR, year);
            publicationDate.set(Calendar.MONTH, month);
            publicationDate.set(Calendar.DAY_OF_MONTH, day);

            System.out.println("Inserte el link de la portada");
            String url=reader.next();
            System.out.println("Inserte páginas");
            int pages = validateInteger();
        
            if(controller.isItBook(index)){
                System.out.println("Inserte la reseña");
                reader.nextLine();
                String review=reader.nextLine();    
                System.out.println("Inserte el precio");
                double price = validateDouble();
                System.out.println("Inserte genero");
                System.out.println("1 -> ciencia ficción, 2 -> fantasia, 3 -> novela");
                int gender = validateInteger();

                System.out.println("Inserte el id:");
                msg = controller.modifyProductBook(index, name, publicationDate, url, pages, review, gender, price);
            }else{
                System.out.println("Inserte la periodicidad (mensual, semanal, etc)");
                reader.nextLine();
                String periodicity = reader.nextLine();
                System.out.println("Inserte valor de suscripción");
                double priceSuscription = validateDouble();
                System.out.println("Inserte categoria");
                System.out.println("1 -> variedades, 2 -> disenio, 3 -> cientifica");
                int category = validateInteger();

                msg = controller.modifyProductMagazine(index, name, publicationDate, url, pages, category, periodicity, priceSuscription);
            }
        }
        return msg;
    }

    public String eraseProduct(){
        String msg="";
        int index=-1;
        index = selectProduct();

        if(index==-1){
            msg = "No se pudo, no hay tal producto";
        }else{
            msg = controller.eraseProduct(index);
        }
        return msg;
    }

    public String addTextToUser(){
        String msg="";
        int indexUser = selectUser();
        if(indexUser<0){
            msg+="No existe el usuario";
        }else {
            int indexProduct = selectProduct();
            if(indexProduct<0){
                msg="No existe el producto";
            }else{
            msg=controller.addTextToUser(indexProduct, indexUser);
        }
    }
        return msg;
    }

    public String readLibraryOfUser(){
        int indexUser = selectUser();
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
                option = validateInteger();
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
                    case 0:
                        System.out.println("volviendo");
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
        
        
        int method = validateInteger();
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
                option = validateInteger();
                switch(option){
                    case 1:
                    actualPageProduct+=1;
                    break;
                    case 2:
                    actualPageProduct-=1;
                    break;
                    case 0:
                    System.out.println("volviendo");
                    controller.addPages(indexUser, id, actualPageProduct);
                    break;
                }
            }

            break;
            case 2:
            System.out.println("Inserte coordenada x");
            int x = validateInteger();
            System.out.println("Inserte coordenada y");
            int y = validateInteger();
            while(option!=0){
                System.out.println(controller.getProductPageByCoordenates(indexUser, actualPageLibrary, actualPageProduct, x, y));
                System.out.println("1. siguiente página");
                System.out.println("2. anterior página");
                System.out.println("0. salir");
                option = validateInteger();
                switch(option){
                    case 1:
                    actualPageProduct+=1;
                    break;
                    case 2:
                    actualPageProduct-=1;
                    break;
                    case 0:
                    System.out.println("volviendo");
                    controller.addPagesByCoordenates(indexUser, actualPageLibrary, actualPageProduct, x, y, actualPageProduct);
                    break;
                    default:
                    System.out.println("No valido");
                    break;
                }
            }
            
            break;
            default:
            System.out.println("Saliendo");
        }
    }

    public int selectUser(){
        System.out.println("Inserte el CC del usuario");
        reader.nextLine();
        String idUser = reader.nextLine();
        int index = controller.getIndexUserById(idUser);
        return index;
    }

    public int selectProduct(){
        System.out.println("Seleccionar producto mediante\n1. id\n2. nombre");
        int option = -1;
        int index = -1;
        do{
            option = validateInteger();
        }while(option != 1 && option != 2);

        reader.nextLine();
        String idProduct;
        String nameProduct;
        switch (option){
            case 1:
            System.out.println("inserte el id");
            idProduct = reader.nextLine();
            index = controller.getIndexProductById(idProduct);
            break;
            case 2:
            System.out.println("inserte el nombre");
            nameProduct = reader.nextLine();
            index = controller.getIndexProductByName(nameProduct);
            break;
        }
        return index;
    }

    public String eraseAccountProduct(){
        String msg = "";
        int index = selectUser();
        if(index == -1){
            msg = "No existe ese usuario";
        }else{
            System.out.println("Quiere borrar el producto por: \n1. id \n2. nombre");
            int option;
            String information;
            do{
                option = validateInteger();
            }while(option != 1 && option !=2);
            System.out.println("Inserte informacion");
            reader.nextLine();
            switch(option){
                case 1:
                    information = reader.nextLine();
                    msg = controller.eraseProductOfUserById(index, information);
                    break;
                case 2:
                    information = reader.nextLine();
                    msg = controller.eraseProductOfUserByName(index, information);
            }
        }
        return msg;
    }

    public int validateInteger() {
        int number = -1;
        boolean isValid = false;
    
        do {
            if (reader.hasNextInt()) {
                number = reader.nextInt();
                isValid = true;
            } else {
                System.out.println("Inserte un entero válido");
                reader.nextLine();
            }
        } while (!isValid);
    
        return number;
    }

    public double validateDouble() {
        double number = -1;
        boolean isValid = false;
    
        do {
            if (reader.hasNextDouble()) {
                number = reader.nextDouble();
                isValid = true;
            } else {
                System.out.println("Inserte un número decimal válido");
                reader.nextLine();
            }
        } while (!isValid);
    
        return number;
    }

    public String exit(){
        reader.close();
        return "saliendo...";
    }

    public String reportMenu(){
        System.out.println("Escoja un reporte a generar\n1. acumulado de paginas leidas por tipo de producto \n2. genero o categoria mas popular");
        System.out.println("3. Top 5 de revistas y libros \n4. cantidad vendida de cada genero de libro \n5. cantidad vendida de cada categoria de revista\n");
        int option = validateInteger();
        String msg = "";
        switch(option){
            case 1 -> msg = controller.showAmountOfPagesPerProduct();
            case 2 -> msg = controller.showMostPopularGender();
            case 3 -> msg = controller.top5();
            case 4 -> msg = controller.ammountSoldBooks();
            case 5 -> msg = controller.ammountSoldMagazines();
            
        }
        return msg;
    }
}