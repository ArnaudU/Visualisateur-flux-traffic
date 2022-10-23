package Protocole;

public abstract class Protocole {

    public int HexToDec(String hex){
        int dec=-1;
        dec = Integer.parseInt(hex,16);
        return dec;
    }

    public String DecToHex(int dec){
        return Integer.toHexString(dec);
    }
    
}
