package TraitementFichier.test;

import java.io.IOException;

import TraitementFichier.FileReader;
import TraitementFichier.FormatInvalidException;

public class TestFileReader {
    public static void main(String[] args) throws IOException, FormatInvalidException{
        FileReader fr = new FileReader("data/trame.txt");
        System.out.println(fr);
    }
}
