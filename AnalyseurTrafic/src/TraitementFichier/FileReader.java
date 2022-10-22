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

    public FileReader(String file){
        readLine(file);
    }

    private void readLine(String file){
        int ligne=1; // iterateur pour savoir la ligne(utile si une erreur est detecte
        int sizeMaxOctet =16*3+7-1; // La taille a lire 
        int sizeMinOctet=7;
        BufferedReader br;
        try {
            br = new BufferedReader(
                    new InputStreamReader(
                        new BufferedInputStream(
                            new FileInputStream(new File(file)))));
            
            String line;
			while((line = br.readLine())!= null){
                if(line.length()<sizeMaxOctet){
                    //Le cas ou il manque des octets et non la position des octets 
                    if(line.length()>=sizeMinOctet ){
                        if(br.readLine()==null){
                            String getOctet=line.substring(sizeMinOctet, line.length());
                            octet.add(getOctet);
                            break;
                        }
                        else{
                            int lineOctet=line.length()-sizeMinOctet;
                            int nbOctet =(lineOctet)/3; 
                            int nbOctetManquant = 16-nbOctet;
                            throw new FormatInvalidException("Erreur a la ligne "+ ligne+ ", il manque entre "+ (nbOctetManquant-1)  +" et " + nbOctetManquant +" octet");
                        }
                    }
                    else{
                        throw new FormatInvalidException("Il manque les 4 hexa qui marque la ligne et les 16 octets a la ligne "+ligne);
                    }
                }
                String getOctet=line.substring(sizeMinOctet, sizeMaxOctet);
                octet.add(getOctet);
                ligne++;
            }
            //Ici on enleve les espaces engendre par le dernier octet
            String lastListOctet=octet.get(octet.size()-1);
            while(lastListOctet.charAt(lastListOctet.length()-1)==' '){
                lastListOctet=lastListOctet.substring(0, lastListOctet.length()-1);
                //System.out.println(lastListOctet);
            }
            octet.remove(octet.size()-1);
            octet.add(lastListOctet);
            System.out.println(octet);
		}
		catch(FileNotFoundException e) {
			System.out.println("Le fichier n'existe pas");
            System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		} 
        //System.out.println(octet);
        catch (FormatInvalidException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        
    }
}
