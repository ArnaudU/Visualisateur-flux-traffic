package Protocole.test;

import Protocole.IPv4;
import Protocole.OctetInvalidException;

public class TestIPv4 {
    public static void main(String[] args) {
        String s = "4f 00 00 7c 3f 86 00 00 fb 01 49 af c0 21 9f 06 84 e3 3d 05 07 27 28 84 e3 3c 20 c0 2c 41 12 c0 46 47 05 c0 21 9f 02 c0 21 9f 06 c0 46 47 06 c0 2c 41 1a 84 e3 3c 1e 84 e3 3d 87 00 00 00 aa 56 2f 00 00 00 29 36 8c 41 00 03 86 2b 08 09 0a 0b 0c 0d 0e 0f 10 11 12 13 14 15 16 17 18 19 1a 1b 1c 1d 1e 1f 20 21 22 23 24 25 26 27 28 29 2a 2b 2c 2d 2e 2f 30 31 32 33 34 35 36 37";
        try {
            System.out.println(new IPv4(s));
        } catch (OctetInvalidException e) {
            e.printStackTrace();
        }
    }
}
