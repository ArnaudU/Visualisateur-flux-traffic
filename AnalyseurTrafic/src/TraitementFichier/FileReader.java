package TraitementFichier;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileReader {
    private ArrayList<String> octet= new ArrayList<>() ; // Correspond au octet dans le fichier
    private ArrayList<String> message=new ArrayList<>() ; // Correspond au message ASCII dans le fichier

    public FileReader(String file){
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                        new BufferedInputStream(
                            new FileInputStream(new File(file)))));
            
            String line;
			while((line = br.readLine())!= null){
                String []word = line.split("   ");
                //Cas ou le fichier ne contient que les octets
                //word[0] contient la position des octets
                if(word.length==2){
                    octet.add(word[1]);
                    message.add(word[2]);
                }
                else{
                    if(word.length==3){
                        octet.add(word[1]);
                        message.add(word[2]);
                    }
                    else{
                        throw new FormatInvalidException("Format invalide:\n<ligne octet> <suite de 10 octet> <message>(le format message est facultatif) ");
                    }
                }
            }
            
		}
		catch(FileNotFoundException e) {
			System.out.println("Le fichier n'existe pas");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FormatInvalidException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        }
        finally{
            System.exit(1);
        }
        System.out.println(message);
    }
}
