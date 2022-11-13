package Protocole;

public class TCP extends Protocole{

    private int port_src;
    private int port_dest;
    private int sequence;
    private int ack;
    private int thl;
    private String reserved;//En binaire
    private String fiability;//En binaire contient urg ack psh rst syn et fin
    private String fiabilityContainer=""; // Tout les flags de fiability sont ici par exemple contient SYN,ACK
    private String flags;

    public TCP(String o, String name) {
        super(o,"TCP");
        port_src = this.hexToDec(get(0).concat(get(1)));
        port_dest = this.hexToDec(get(2).concat(get(3)));
        sequence = hexToDec(get(3,6));
        ack = hexToDec(get(7,10));
        String s1[] = getBytes(get(11));
        thl = hexToDec(s1[0])*4;
        String s2[] = getBytes(get(12));
        flags=s1[1]+s2[0]+s2[1];
        reserved = hexToBinary(s1[1])+hexToBinary(s2[0]).substring(0, 2);
        fiability=hexToBinary(s2[0]).substring(2,4)+hexToBinary(s2[1]);
        
    }
    
    //Traite les flags dans reserved et la suite(A FINIR)
    public String traitementFiability(){
        String s = "";
        //Flag reserved
        if(reserved.contains("1")){
            fiabilityContainer+="Reserved, ";
            s+=reserved+" = Reserved: Set\n";
        }
        else{
            s+=reserved+" = Reserved: Not Set\n";
        }
        
        //Nonce
        if(fiability.contains("1")){
            fiabilityContainer+=", ";
            s+=fiability.charAt(0)+" = : Set\n";
        }
        else{
            s+=fiability.charAt(0)+" = : Not Set\n";
        }

        //CWR
        if(fiability.contains("1")){
            fiabilityContainer+=", ";
            s+=fiability.charAt(0)+" = : Set\n";
        }
        else{
            s+=fiability.charAt(0)+" = : Not Set\n";
        }
        
        
        return s;
    }

    public String toString(){
      StringBuilder sb = new StringBuilder();
      sb.append(super.toString());
      sb.append("\tPort source :"+port_src+"\n");
      sb.append("\tPort destination :"+port_dest+"\n");
      sb.append("\tSequence number :"+sequence+"\n");
      sb.append("\tAcknowledgment number :"+ack+"\n");
      sb.append("\tHeader Length : " + thl+"\n");
      sb.append("Flags : "+flags+" ("+fiabilityContainer +")\n");
      return sb.toString();
    }
}

