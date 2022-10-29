package Protocole.test;

import Protocole.IPv4;
import Protocole.OctetInvalidException;

public class TestIPv4 {
    public static void main(String[] args) {
        String s = "45 00 00 96 2e 33 40 00 40 06 87 dd c0 a8 01 ae c0 a8 01 53";
        try {
            System.out.println(new IPv4(s));
        } catch (OctetInvalidException e) {
            e.printStackTrace();
        }
    }
}
