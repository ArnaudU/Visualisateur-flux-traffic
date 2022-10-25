/**
 * Classe de representation d'une entete Ethernet
 */
public class Ethernet extends Protocole {

    
    private String macAddressDest;  //0 a 5e octets
    
    private String macAddressSrc;   //6e a 11e octets   //en Tout 14 octets de l'entete Ethernet a traiter

    private String typeProtocolCode;    //12e a 13e octets
    Private String typeProtocol;


    /**
     * Constructeur recupere les octets de la trame, analyse les 14 premiers octets
     */
    public Ethernet(String o){

        super(o,"Ethernet");
        
        
        //0 a 5e octets

        int i = 0;

        for(i; i<5; i++){
            macAddressDest = octets.get(i)+":";
        }
        i++;
        macAddressDest = octets.get(i); //ajout du 5e sans les ":" a la fin

        //6e a 11e octets

        for(i; i<11; i++){
            macAddressSrc = octets.get(i)+":";
        }
        i++;
        macAddressSrc = octets.get(i); //ajout du 11e sans les ":" a la fin

        //12e a 13e octets

        typeProtocolCode = octets.get(12)+octets.get(13);

        if(typeProtocolCode == "0800") typeProtocol = "IPv4";
        if(typeProtocolCode == "86DD") typeProtocol = "IPv6";
        if(typeProtocolCode == "0806") typeProtocol = "ARP";
        if(typeProtocolCode == "8035") typeProtocol = "RARP";
        if(typeProtocolCode == "809B") typeProtocol = "AppleTalk";
        if(typeProtocolCode == "88CD") typeProtocol = "SRECOS III";
        if(typeProtocolCode == "0600") typeProtocol = "XNS";
        if(typeProtocolCode == "8100") typeProtocol = "VLAN";

        //en Tout 14 octets de l'entete Ethernet traiter

    }

    public String toString(){

        StringBuilder sb = new StringBuilder();

        sb.append("\tMAC Address Destination : "+macAddressDest+"\n");
        sb.append("\tMAC Address Source : "+macAddressSrc+"\n");
        sb.append("\tType Protocol : "+typeProtocol+"\n");
        
        return sb.toString();
    }


}