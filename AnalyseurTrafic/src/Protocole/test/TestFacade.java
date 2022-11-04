package Protocole.test;

import java.io.IOException;

import Protocole.FacadeTrame;
import Protocole.OctetInvalidException;
import Protocole.ProtocoleInvalidException;
import TraitementFichier.FileReader;
import TraitementFichier.FormatInvalidException;

public class TestFacade {
    public static void main(String[] args) throws ProtocoleInvalidException, OctetInvalidException, IOException, FormatInvalidException {
        FileReader fr = new FileReader("data/trame2.txt");
        FacadeTrame ft = new FacadeTrame(fr.getOctet());
        System.out.println(ft.toString());
    }
}
