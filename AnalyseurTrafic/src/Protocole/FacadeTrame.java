package protocole;

import java.util.ArrayList;

public class FacadeTrame {
    
    private ArrayList<String> octets;
    private int id;
    private Ethernet eth;
    private IPv4 ipv4;
    private TCP tcp;
    private HTTP http;
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
        e=subList(index+1, octets.size()-1);
        tcp=new TCP(e);
        index+= tcp.getLength();
        if(tcp.getHasNext()){
            http =new HTTP(subList(index, octets.size()-1));
        }
        
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
     * Pour creer une table dans l'interface
     */
    public String[] getData(int i){
        String protocol = "";
        String info = "";
        if(http==null){
            protocol="TCP";
            info+=tcp.essential();
        }
        else{
            protocol="HTTP";
            info += http.info();
        }
        String[] res = {""+i,ipv4.getSrc(),ipv4.getDest(),protocol,""+octets.size(),info};
        return res; 
    }

    public String getEssential(int i){
        StringBuilder sb = new StringBuilder();
        sb.append(""+i+" "+ipv4.getSrc()+" "+ipv4.getDest()+" ");
        if(http==null){
            sb.append("TCP ");
            sb.append(octets.size()+" ");
            sb.append(tcp.essential()+"\n");
        }
        else{
            sb.append("HTTP ");
            sb.append(octets.size()+" ");
            sb.append(http.info()+"\n");
        }
        return sb.toString();
    }

    /*
     * affiche tout les protocoles
     */
    public String toString(){
        StringBuilder bf = new StringBuilder();
        bf.append("TRAME "+id+"\n\n"+eth.toString()+"\n"+ipv4.toString()+"\n"+tcp.toString()+"\n");
        if(http!=null){
            bf.append(http.toString());
        }
        bf.append("\n");
        bf.append("-----------------------------------------------------------------------------------------------------------");
        bf.append("\n\n");
        return bf.toString();
    }

}
