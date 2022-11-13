package Protocole;

import java.util.ArrayList;

public class FacadeTrame {
    
    private ArrayList<String> octets;
    private int id;
    private Ethernet eth;
    private IPv4 ipv4;
    private TCP tcp;
    //private HTTP http;

    /*
     * c'est une facade de la trame qui permet de regrouper tout les protocoles;
     */
    public FacadeTrame(int id,ArrayList<String> octets) throws ProtocoleInvalidException, OctetInvalidException{
        this.octets=octets;
        // On recupere l'entete de eth dans la trame puis on l'instancie
        this.id=id;
        int index=0;
        String e = subList(index, index+13);
        index+=13;
        eth = new Ethernet(e);
        //On verifie si la couche suivante est bien ipv4
        if(!eth.nextIsIPv4()){
            throw new ProtocoleInvalidException("Protocole IPv4 et non un protocole "+eth.getNextProtocol()+" pour la couche 3!");
        }
        // On recupere l'entete de ipv4 dans la trame puis on l'instancie(sans l'option)
        String ip = subList(index+1, index+20);
        ipv4 = new IPv4(ip);
        if(!ipv4.nextIsTCP()){
            throw new ProtocoleInvalidException("Protocole TCP et non un protocole "+ipv4.getNextProtocol()+" pour la couche 4!");
        }
        //On verfie s'il y a une option en regardant la longueur de l"entete ip
        int lengthIPv4 = ipv4.getLength();
        //Si la longueur est supérieur a 20 alors on reinstancie ipv4 avec la nouvelle taille 
        if(lengthIPv4!=20){
            ip= subList(index+1,index+lengthIPv4);
            ipv4 = new IPv4(ip);
        }
        index+=lengthIPv4;
        //Faire pour TCP et HTTP
        e=subList(index, index+20);
        tcp=new TCP(ip);
        index+= tcp.getLentgh();
    }

    /*
     * Met sous forme de sous forme de String d'octets, decoupe pour un espace, la liste d'octet commencant par @indexDebut et finissant par @indexFin
     */
    private String subList(int indexDebut,int indexFin){
        String res = "";
        for(int i=indexDebut;i<=indexFin-1;i++){
            res+=octets.get(i)+ " ";
        }
        res+=octets.get(indexFin);
        return res;
    }

    /*
     * affiche tout les protocoles
     */
    public String toString(){
        return "TRAME "+id+"\n\n"+eth.toString()+"\n"+ipv4.toString()+"\n"+tcp.toString()+"\n"/*+http.toString()*/+"----------------------------------------------------------------------------------------------------------------------------------------------------\n\n";
    }
}
