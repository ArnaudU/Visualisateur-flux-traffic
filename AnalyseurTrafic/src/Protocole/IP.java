package Protocole;

public class IP extends Protocole{

   private String src;
   private String des;
   private int version;
   private int headerLength;
   private int lengthTotal;
   private int ttl;
   private String checksum;
   private String typeService;
   private String id;
   private String offset;
   private String  protocole;

   public IP(String o) {
      super(o,"IP");
      String p1[]= getFourBytes(octets.get(0));
      //4 premier octets
      version =  hexToDec(p1[0]);
      headerLength = hexToDec(p1[1]);
      typeService = octets.get(1);
      lengthTotal = hexToDec(octets.get(2)+octets.get(3));
      //4 a 8eme octet
      id = octets.get(4)+octets.get(5);
      offset = octets.get(6)+octets.get(7);
      //8 a 12 octet
      ttl = hexToDec(octets.get(8));
      protocole = octets.get(9);
      checksum = octets.get(10)+octets.get(11);      
   }
   
   public String toString(){
      StringBuilder sb = new StringBuilder();
      sb.append(super.toString());
      sb.append("\tVersion "+version+"\n");
      sb.append("\tLength Header : "+headerLength+"\n");
      sb.append("\tType Service : "+typeService+"\n");
      sb.append("\tTotal length : "+lengthTotal+"\n");
      sb.append("\tIdentifier : "+id+"\n");
      sb.append("\tOffset : "+offset+"\n");
      sb.append("\tTime To Live : "+ttl+"\n");
      sb.append("\tProtocole : "+protocole+"\n");
      sb.append("\tChecksum : "+checksum+"\n");
      return sb.toString();
   }
}
