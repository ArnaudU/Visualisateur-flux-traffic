package Protocole.test;

import Protocole.HTTP;

public class TestHTTP {
    public static void main(String[] args) {
        String s="";
        String h = "474554202f522f4133734b494464694d54646b4f47526c5a54637a4d4451794e7a68684e6a67355a4751315a6d51354e544d304d54646a4567514349424569474d4d42496745434b67674942424330324f716a41536f4943414d517a38767a6f51457943776741454c375a36714d42474941434f4a6176714c41425169447344436f6a5a46423865465f654673444c554a34416875466d42582d354e63485461625341564c6d6d775569416779673d20485454502f312e310d0a486f73743a2073752e66662e61766173742e636f6d0d0a4163636570743a202a2f2a0d0a436f6e74656e742d547970653a206170706c69636174696f6e2f6f637465742d73747265616d0d0a507261676d613a206e6f2d63616368650d0a436f6e6e656374696f6e3a206b6565702d616c6976650d0a0d0a";
        for(int i=0;i<h.length();i+=2){
            s+= h.substring(i, i+2)+" ";
        }
        s.subSequence(0, s.length()-1);
        HTTP http = new HTTP(s);
        System.out.println(http);
    }
}