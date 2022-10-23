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
      src = hexToDec(octets.get(12))+"."+hexToDec(octets.get(13))+"."+hexToDec(octets.get(14))+"."+hexToDec(octets.get(15));
      des = hexToDec(octets.get(16))+"."+hexToDec(octets.get(17))+"."+hexToDec(octets.get(18))+"."+hexToDec(octets.get(19));
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
      sb.append("\tIP source : "+src+"\n");
      sb.append("\tIP destination : "+des+"\n");
      return sb.toString();
   }
}
