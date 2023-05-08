package ui;

import java.util.Scanner;
import model.Controller;

public class Main{

    private Controller controller;

    public Main(){
        this.controller = new Controller();
    }

    public static void main(String[] args){
        Main m = new Main();
        m.controller.initBooks();
        m.controller.getBooks();
    }
}