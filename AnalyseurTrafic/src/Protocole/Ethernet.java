package Protocole;

/**
 * Classe de representation d'une entete Ethernet
 */
public class Ethernet extends Protocole {

    
    private String macAddressDest="";  //0 a 5e octets
    
    private String macAddressSrc="";   //6e a 11e octets   //en Tout 14 octets de l'entete Ethernet a traiter

    private String typeProtocolCode;    //12e a 13e octets
    private String typeProtocol;
    private String crc;

    /**
     * Constructeur recupere les octets de la trame, analyse les 14 premiers octets
     */

    public Ethernet(String entete){

        super(entete,"Ethernet");
    
        //0 a 5e octets

        for(int i=0; i<4; i++){
            macAddressDest += octets.get(i)+":";
        }
        macAddressDest += octets.get(5); //ajout du 5e sans les ":" a la fin

        //6e a 11e octets

        for(int i=5; i<10; i++){
            macAddressSrc += octets.get(i)+":";
        }
        macAddressSrc += octets.get(11); //ajout du 11e sans les ":" a la fin

        //12e a 13e octets
        typeProtocolCode = octets.get(12)+octets.get(13);
        if(typeProtocolCode.equalsIgnoreCase("0800")) typeProtocol = "IPv4";
        if(typeProtocolCode.equalsIgnoreCase("86DD")) typeProtocol = "IPv6";
        if(typeProtocolCode.equalsIgnoreCase("0806")) typeProtocol = "ARP";
        if(typeProtocolCode.equalsIgnoreCase("8035")) typeProtocol = "RARP";
        if(typeProtocolCode.equalsIgnoreCase("809B")) typeProtocol = "AppleTalk";
        if(typeProtocolCode.equalsIgnoreCase("88CD")) typeProtocol = "SRECOS III";
        if(typeProtocolCode.equalsIgnoreCase("0600")) typeProtocol = "XNS";
        if(typeProtocolCode.equalsIgnoreCase("8100")) typeProtocol = "VLAN";

        //en Tout 14 octets de l'entete Ethernet traiter

    }
    /**
     * Dans le cas ou on a une avec un crc
     * @param entete
     * @param enqueue
     */
    public Ethernet(String entete, String enqueue){
        this(entete);
        crc=enqueue;
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

        sb.append("\tMAC Address Destination : "+macAddressDest+"\n");
        sb.append("\tMAC Address Source : "+macAddressSrc+"\n");
        sb.append("\tType Protocol : "+typeProtocol+"\n");
        if(crc!=null){
            sb.append("\tType crc : "+crc+"\n");
        }
        return sb.toString();
    }


}