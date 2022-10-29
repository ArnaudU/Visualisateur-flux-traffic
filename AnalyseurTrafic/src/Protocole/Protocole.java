package Protocole;

import java.util.ArrayList;

public abstract class Protocole {


    protected ArrayList<String> octets = new ArrayList<>();
    private String name;

    public Protocole(String o, String name){
        //On split en 1 octet la liste d'octet
        for(String s : o.split(" ")){
            octets.add(s);
        }      
        this.name=name;
    }

    /*
     * Converti les octets en hexa en decimale
     */
    public int hexToDec(String hex){
        int dec=-1;
        dec = Integer.parseInt(hex,16);
        return dec;
    }

    /*
     * Converti les octets en hexa
     * @param indice de l'octets dans la trame
     * 
     */
    public int hexToDec(int index){
        String hex=get(index);
        int dec=-1;
        dec = Integer.parseInt(hex,16);
        return dec;
    }
    /*
     * Convertie les octets en decimale en hexa
     */
    public String decToHex(int dec){
        return Integer.toHexString(dec);
    }
    
    /*
     * Donne seulement les 4 bytes(en hexa) d'un octet c'est a dire un hexa
     * @return un tableau 
     * Exemple : 
     *          getFourBytes("AF")->[A,F]
     * Pas besoin d'exception car elles sont deja traites auparavant, dans la classe FileReader
     */
    public String[] getFourBytes(String octet){
        String s[] = new String[octet.length()];
        int i=0;
        for(char c : octet.toCharArray()){
            s[i] = String.valueOf(c);
            i++;
        }
        return s;
    }

    public String get(int index){
        return octets.get(index);
    }

    public String toString(){
        return "Protocole : "+name + "\n";
    }
}
