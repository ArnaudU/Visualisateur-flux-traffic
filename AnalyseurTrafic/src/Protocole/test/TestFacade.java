package Protocole.test;

import java.io.IOException;
import java.util.ArrayList;

import Protocole.FacadeTrame;
import Protocole.OctetInvalidException;
import Protocole.ProtocoleInvalidException;
import TraitementFichier.FileReader;
import TraitementFichier.FormatInvalidException;

public class TestFacade {
    public static void main(String[] args) throws ProtocoleInvalidException, OctetInvalidException, IOException, FormatInvalidException {
        FileReader fr = new FileReader("AnalyseurTrafic/data/trame_test_OpenFile.txt");
        int i=1;
        for(ArrayList<String> octet :fr.getOctet()){
            FacadeTrame ft = new FacadeTrame(i,octet);
            System.out.println(ft.toString());
            i++;
        }
    }
}
