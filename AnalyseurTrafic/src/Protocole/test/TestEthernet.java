public class TestEthernet{
    public static void main(String[] args){

        FileReader fr = new FileReader("data/trame.txt");
        System.out.println(fr);

        Ethernet eth = new Ethernet(fr,"Ethernet");

        System.out.println(eth.toString());

    }
}