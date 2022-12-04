package protocole;

/**
 * Classe de representation d'une entete Ethernet
 */
public class Ethernet extends Protocole {
    
    private String macAddressDest;  //0 a 5e octets
    private String macAddressSrc;   //6e a 11e octets   //en Tout 14 octets de l'entete Ethernet a traiter
    private String typeProtocolCode;    //12e a 13e octets
    private String typeProtocol;
    private String crc;

    /**
     * Constructeur recupere les octets de la trame, analyse les 14 premiers octets
     * @throws OctetInvalidException
     */

    public Ethernet(String entete) throws OctetInvalidException{

         //en Tout 14 octets de l'entete Ethernet traiter
        super(entete,"Ethernet");
    
        //0 a 5e octets
        macAddressDest=setMacAdd(0, 5);
        //6e a 11e octets
        macAddressSrc=setMacAdd(6, 11);

        //12e a 13e octets
        typeProtocolCode = get(12)+get(13);
        setTypeProtocoleCode();
    }

    /**
     * Dans le cas ou on a une avec un crc
     * @param entete
     * @param enqueue
     * @throws OctetInvalidException
     */
    public Ethernet(String entete, String enqueue) throws OctetInvalidException{
        this(entete);
        crc=enqueue;
    }

    private void setTypeProtocoleCode() throws OctetInvalidException{
        if(typeProtocolCode.equalsIgnoreCase("0800")) typeProtocol = "IPv4";
        if(typeProtocolCode.equalsIgnoreCase("86DD")) typeProtocol = "IPv6";
        if(typeProtocolCode.equalsIgnoreCase("0806")) typeProtocol = "ARP";
        if(typeProtocolCode.equalsIgnoreCase("8035")) typeProtocol = "RARP";
        if(typeProtocolCode.equalsIgnoreCase("809B")) typeProtocol = "AppleTalk";
        if(typeProtocolCode.equalsIgnoreCase("88CD")) typeProtocol = "SRECOS III";
        if(typeProtocolCode.equalsIgnoreCase("0600")) typeProtocol = "XNS";
        if(typeProtocolCode.equalsIgnoreCase("8100")) typeProtocol = "VLAN";
        if(typeProtocol==null){
            typeProtocol="???";
            throw new OctetInvalidException("Le type du protocol apres la couche 2(ethernet) n'existe pas!");
        }
    }

    /*
     * Verifie si la prochaine couche est IPv4
     */
    public boolean nextIsIPv4(){
        return typeProtocol.equalsIgnoreCase("IPv4");
    }
     
    /*
     * Methode privee pour attribuer l'adresse mac sous la forme XX:XX:XX:XX:XX:XX
     * @param indexDebut: l'indice de debut de l'octect 
     * @param indexFin: l'indice de fin de l'octect 
     */
    private String setMacAdd(int indexDebut, int indexFin){
        String s="";
        for(int i =indexDebut; i<indexFin-1;i++){
            s+=get(i)+":";
        }
        //Pour ne pas mettre le ":" a la fin
        s+=get(indexFin);
        return s;
    }

    public String getNextProtocol(){
        return typeProtocol;
    }

    /**
     * Rend la chaine de caractere de la forme:
     * 
     *  MAC Address Destination : 08:00:XX:XX:XX:XX
     *  MAC Address Source : 08:00:XX:XX:XX:XX
     *  Type Protocol : IPv4
     * 
     */
    public String toString(){

        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("\tMAC Address Destination : "+macAddressDest+"\n");
        sb.append("\tMAC Address Source : "+macAddressSrc+"\n");
        sb.append("\tType Protocol : "+typeProtocol+"\n");
        if(crc!=null){
            sb.append("\tType crc : "+crc+"\n");
        }
        return sb.toString();
    }
}