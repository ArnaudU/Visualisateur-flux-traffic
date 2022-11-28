package protocole;

public class TCP extends Protocole{

    private int port_src;
    private int port_dest;
    private String sequence;
    private String ack;
    private int thl;
    private String reserved;//En binaire
    private String fiability;//En binaire contient urg ack psh rst syn et fin
    private String fiabilityContainer=""; // Tout les flags de fiability sont ici par exemple contient SYN,ACK
    private String flag;// contient les set et not set des flags
    private String flags;
    private int window;
    private String checkSum;
    private String urgentPointer;
    private boolean hasNext;
    private int len;
    public TCP(String o) {
        super(o,"TCP");
        port_src = this.hexToDec(get(0).concat(get(1)));
        port_dest = this.hexToDec(get(2).concat(get(3)));

        sequence = get(4,8);
        ack = get(8,12);

        String s1[] = getBytes(get(12));
        thl = hexToDec(s1[0])*4;
        String s2[] = getBytes(get(13));
        flags=s1[1]+s2[0]+s2[1];
        reserved = hexToBinary(s1[1])+hexToBinary(s2[0]).substring(0, 2);
        fiability=hexToBinary(s2[0]).substring(2,4)+hexToBinary(s2[1]);
        
        window = hexToDec(get(14,16));
        checkSum = get(16,18);
        urgentPointer = get(18);
        flag = traitementFiability();
        hasNext();
        len();
    }
    
    //Traite les flags dans reserved et la suite(A FINIR)
    public String traitementFiability(){
        String s = "";
        
        //Flag reserved
        s+="\t\t"+reserved.substring(0, 3)+". .... .... = Reserved: ";
        if(reserved.substring(0, 3).contains("1")){
            fiabilityContainer+="Reserved, ";
            s+="Set\n";
        }
        else{
            s+="Not Set\n";
        }
        
        //Nonce
        s+="\t\t..."+reserved.charAt(3)+" .... .... = Nonce: ";
        if(reserved.charAt(3)=='1'){
            fiabilityContainer+="Nonce, ";
            s+="Set\n";
        }
        else{
            s+="Not Set\n";
        }

        //CWR
        s+="\t\t.... "+reserved.charAt(4)+"... .... = Congestion Window Reduced (CWR): ";
        if(reserved.charAt(4)=='1'){
            fiabilityContainer+="CWR, ";
            s+="Set\n";
        }
        else{
            s+="Not Set\n";
        }

        //ECN-Echo
        s+="\t\t.... ."+reserved.charAt(5)+".. .... = ECN-Echo: ";
        if(reserved.charAt(5)=='1'){
            fiabilityContainer+="ECN, ";
            s+="Set\n";
        }
        else{
            s+="Not Set\n";
        }

        //Urgent
        s+="\t\t.... .."+fiability.charAt(0)+". .... = Urgent: ";
        if(fiability.charAt(0)=='1'){
            fiabilityContainer+="URG, ";
            s+="Set\n";
        }
        else{
            s+="Not Set\n";
        }

        //ACK
        s+="\t\t.... ..."+fiability.charAt(1)+" .... = Acknowlegdment: ";
        if(fiability.charAt(1)=='1'){
            fiabilityContainer+="ACK, ";
            s+="Set\n";
        }
        else{
            s+="Not Set\n";
        }

        //PSH
        s+="\t\t.... .... "+fiability.charAt(2)+"... = Push: ";
        if(fiability.charAt(2)=='1'){
            fiabilityContainer+="PSH, ";
            s+="Set\n";
        }
        else{
            s+="Not Set\n";
        }

        //RST
        s+="\t\t.... .... ."+fiability.charAt(3)+".. = Reset: ";
        if(fiability.charAt(3)=='1'){
            fiabilityContainer+="RST, ";
            s+="Set\n";
        }
        else{
            s+="Not Set\n";
        }
        
        //SYN
        s+="\t\t.... .... .."+fiability.charAt(4)+". = Syn: ";
        if(fiability.charAt(4)=='1'){
            fiabilityContainer+="SYN, ";
            s+="Set\n";
        }
        else{
            s+="Not Set\n";
        }
        
        //FIN
        s+="\t\t.... .... ..."+fiability.charAt(5)+" = Fin: ";
        if(fiability.charAt(5)=='1'){
            fiabilityContainer+="FIN, ";
            s+="Set\n";
        }
        else{
            s+="Not Set\n";
        }
        if(fiabilityContainer.length()>2) fiabilityContainer=fiabilityContainer.substring(0,fiabilityContainer.length()-2);
        return s;
    }

    /*
     * Retourne la taille du protocol
     */
    public int getLength(){
        return thl;
    }

    public String getFiabilityContainer(){
        return fiabilityContainer;
    }

    /*
     * Pour savoir s'il y a le protocole http  est a la suite
     */
    public void hasNext(){
        if(HTTP.isNext(hexToasciizs(get(0,size())))){
            if(get(size()-2,size()).equalsIgnoreCase("0d0a")){
                hasNext=true;
                return;
            }
        }
        hasNext= false;
    }

    public boolean getHasNext(){
        return hasNext;
    }

    private void len(){
        len=size()-20-thl;
    }

    public String essential(){
        StringBuilder sb = new StringBuilder();
        sb.append(port_src + " -> " + port_dest+" " );
        sb.append("["+fiabilityContainer+"]");
        sb.append(" Seq="+sequence);
        sb.append(" Ack="+ack);
        sb.append(" Win="+window);
        sb.append(" Len="+len);
        return sb.toString();
    }

    public String toString(){
      StringBuilder sb = new StringBuilder();
      sb.append(super.toString());
      sb.append("\tPort source :"+port_src+"\n");
      sb.append("\tPort destination :"+port_dest+"\n");
      sb.append("\tSequence number :"+sequence+"\n");
      sb.append("\tAcknowledgment number :"+ack+"\n");
      sb.append("\tHeader Length : " + thl+"\n");
      sb.append("\tFlags : 0x"+flags+" ("+ fiabilityContainer +")\n");
      sb.append(flag);
      sb.append("\tWindow : "+window+"\n");
      sb.append("\tChecksum : 0x"+checkSum+"\n");
      sb.append("\tUrgent Pointer : "+urgentPointer+"\n");
      return sb.toString();
    }
}

