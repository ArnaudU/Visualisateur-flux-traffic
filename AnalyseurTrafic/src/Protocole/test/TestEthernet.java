package Protocole.test;
import Protocole.Ethernet;

public class TestEthernet{
    public static void main(String[] args){
        Ethernet eth = new Ethernet("5c ba ef ed e2 bf dc 00 b0 48 7b 3c 08 00");

        System.out.println(eth.toString());

    }
}