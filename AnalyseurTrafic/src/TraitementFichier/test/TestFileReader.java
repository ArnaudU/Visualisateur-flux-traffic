package TraitementFichier.test;

import TraitementFichier.FileReader;

public class TestFileReader {
    public static void main(String[] args){
        FileReader fr = new FileReader("data/trame.txt");
        System.out.println(fr);
    }
}
