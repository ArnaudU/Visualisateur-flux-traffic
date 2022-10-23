package Protocole.test;

import Protocole.IP;

public class TestHexa {
    
    public static void main(String[] args) {
        String s = "45 00 00 48 49 ba 00 00 1e 06 69 8d c1 37 33 f6 c1 37 33 04";
        System.out.println(new IP(s));
    }
}
