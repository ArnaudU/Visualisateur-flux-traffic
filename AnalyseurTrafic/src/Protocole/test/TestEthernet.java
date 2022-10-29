package Protocole.test;
import Protocole.Ethernet;
import Protocole.OctetInvalidException;

public class TestEthernet{
    public static void main(String[] args){
        Ethernet eth;
        try {
            eth = new Ethernet("5c ba ef ed e2 bf dc 00 b0 48 7b 3c 08 00");
            System.out.println(eth.toString());
        } catch (OctetInvalidException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}