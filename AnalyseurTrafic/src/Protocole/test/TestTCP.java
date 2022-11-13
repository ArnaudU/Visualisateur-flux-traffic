package Protocole.test;
import Protocole.TCP;

public class TestTCP {
    public static void main(String[] args) {
        String s = "d5 2c 01 bb a4 51 d6 4f f1 56 90 60 50 10 03 ff b9 56 00 00";
        System.out.println(new TCP(s));
        
    }
}
