package Main;

import View.View;
import Controller.UIController;

public class Main {
    public static void main(String[] args) {
        View view = new View();
        new UIController(view);
    }
}