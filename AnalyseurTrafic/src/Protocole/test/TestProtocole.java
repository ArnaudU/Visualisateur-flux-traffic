package Protocole.test;

import Protocole.IPv4;
import Protocole.Protocole;

public class TestProtocole {
    
    public static void main(String[] args) {
        Protocole p = new IPv4("45 00 00 48 49 ba 00 00 1e 06 69 8d c1 37 33 f6 c1 37 33 04");
        System.out.println("Decimal de 0xfffff: "+p.hexToDec("FFFFF"));
        System.out.println("Hexadecimal de 255: "+p.decToHex(255));

    }
}
