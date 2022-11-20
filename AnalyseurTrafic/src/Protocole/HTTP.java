package Protocole;

import java.util.ArrayList;
import java.util.List;

public class HTTP extends Protocole{

    private String version="";
    private String codeStatut="";
    private String message="";
    private String requete;
    private List<String[]> entete= new ArrayList<>();
    private int index=0;
    
    public HTTP(String o) {
        super(o,"HTTP");
        version = separateChampEspace();
        index++;
        codeStatut = separateChampEspace();
        index++;
        message = separateChampLigne();
        index+=2;
        requete = version + " " + codeStatut + " " + message;
        while(!get(index, index+2).equalsIgnoreCase("0d0a")){
            String champEntete = separateChampEspace();
            index++;
            String valChamp = separateChampLigne();
            index+=2;
            String [] ajt = {champEntete,valChamp};
            entete.add(ajt);
        }
    }

    /*
     * Correspond au nom du champs d'entete dans les ligne d'entete
     */
    private String separateChampEspace(){
        String res="";
        while(!get(index).equals("20")){
            res+=hexToasciiz(get(index++));
        }
        return res;
    }

    /*
     * Correspond au valeur du champ dans les ligne d'entete
     */
    private String separateChampLigne(){
        String res="";
        while(!get(index,index+2).equals("0d0a")){
            res+=hexToasciiz(get(index++));
        }
        return res;
    }

    /*
     * Converti un hexa en un caractere ascii 
     */
    public String hexToasciiz(String hexStr){
        return ""+(char)Integer.parseInt(hexStr, 16);
    }

    public String info(){
        return requete;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("\tRequest: "+requete+"\n");
        for(String[] m: entete){
            sb.append("\t"+m[0]+" "+m[1]+"\n");
        }
        return sb.toString();
    }
}