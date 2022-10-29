package Protocole.test;

import Protocole.FacadeTrame;
import TraitementFichier.FileReader;

public class TestFacade {
    public static void main(String[] args) {
        FileReader fr = new FileReader("data/trame2.txt");
        FacadeTrame ft = new FacadeTrame(fr.getOctet());
        System.out.println(ft.toString());
    }
}
