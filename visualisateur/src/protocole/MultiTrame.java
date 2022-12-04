package protocole;
import java.util.ArrayList;
import traitement.FileReader;

public class MultiTrame {

    private ArrayList<Trame> trames;
    public MultiTrame(FileReader fr) throws ProtocoleInvalidException, OctetInvalidException{
        trames=new ArrayList<>();
        int i=1;
        for(ArrayList<String> str : fr.getOctet()){
            trames.add(new Trame(i, str));
            i++;
        }
        try{
            traitement();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public MultiTrame(ArrayList<Trame> trames){
        this.trames=trames;
    }

    public ArrayList<Trame> getTrames() {
        return trames;
    }
    
    public int size(){
        return trames.size();
    }
    

    private void traitement(){
        ArrayList<Trame> container = new ArrayList<>();
        try{
            for(Trame trame :trames){
                if(!container.contains(trame)){
                    if(trame.getTCP()!=null){
                        if(trame.getTCP().isSYN()){
                            modifier(trame,container);
                        }
                    }
                }
            } 
        }
        catch(NullPointerException e){}
    }
    
    private void modifier(Trame trame,ArrayList<Trame> container ){
        TCP tcp = trame.getTCP();
        long ack = tcp.getAcknowledgment();
        long seq = tcp.getSequence();
        boolean acknull=ack==0;
        tcp.setACK(0);
        tcp.setSEQ(0);
        container.add(trame);
        for(Trame trame2 : trames){
            if(!container.contains(trame2)){
                TCP tcp2 = trame2.getTCP();
                if(tcp2.isSYN()){
                
                    break;
                }
                if(trame.equalsTransaction(trame2)){
                    tcp2.setACK(tcp2.getAcknowledgment()-ack);
                    tcp2.setSEQ(tcp2.getSequence()-seq);
                    container.add(trame2);
                }
                else{
                    if(trame.equalsContraryTransaction(trame2)){
                        if(acknull){
                            ack=tcp2.getSequence();
                            tcp2.setACK(1);
                            acknull=false;
                        }
                        else{
                            tcp2.setACK(tcp2.getAcknowledgment()-seq);
                        }
                        tcp2.setSEQ(tcp2.getSequence()-ack);
                        container.add(trame2);
                    }
                }
            }
        }
    }

    public ArrayList<Trame> cloneTrames(){
        ArrayList<Trame> res = new ArrayList<>();
        for(Trame trame :trames){
            res.add(trame);
        }
        return res;
    }

    public ArrayList<String> getAllIp(){
        ArrayList<String> list = new ArrayList<>();;
        for(Trame trame: trames){
            IPv4 ip = trame.getIPv4();
            if(ip==null){
                continue;
            }
            if(!list.contains(ip.getSrc())){
                list.add(ip.getSrc());
            }
            if(!list.contains(ip.getDest())){
                list.add(ip.getDest());
            }
        }
        return list;
    }


    public String toString(){
        StringBuilder sb = new StringBuilder();
        ArrayList<String> list = getAllIp();
        sb.append("Trame");
        for(String ip: list){
            sb.append("   ");
            int len = 20-ip.length();
            for(int i=0;i<len/2;i++){
                sb.append(" ");
            }
            sb.append(ip);
            for(int i=0;i<len/2;i++){
                sb.append(" ");
            }
            sb.append(" ");
        }
        sb.append("\n\n");
        for(Trame trame : trames){
            IPv4 ip = trame.getIPv4();
            if(ip==null){
                continue;
            }
            int src = list.indexOf(ip.getSrc());
            int dest = list.indexOf(ip.getDest());
            int max = Math.max(src, dest);
            int min = Math.min(src,dest);
            TCP tcp= trame.getTCP();
            sb.append(" "+trame.getId()+"          ");
            for(int i=0;i<max*32;i++){
                if(i<(7*(min+1))){
                    sb.append(" ");
                }
                else{
                    if(i==(7*(min+1))){
                        if(src>dest){
                            if(tcp!=null){
                                sb.append(""+tcp.getDest());
                                i+= (""+tcp.getDest()).length();
                            }
                            else{
                                sb.append("?");
                            }
                            sb.append("<");
                        }
                        else{
                            if(tcp!=null){
                                sb.append(""+tcp.getSrc());
                                i+= (""+tcp.getSrc()).length();
                            }
                            else{
                                sb.append("?");
                            }
                        }
                        
                    }
                    else{
                        sb.append("-");
                    } 
                }   
                
            }
            if(src<dest){
                sb.append(">");
            
                if(tcp!=null){
                    sb.append(""+tcp.getDest());
                }
                else{
                    sb.append("?");
                }
            }
            else{
                if(tcp!=null){
                    sb.append(""+tcp.getSrc());
                }
                else{
                    sb.append("?");
                }
            }
            
            sb.append("\n");
        }
        return sb.toString();
    }
}
