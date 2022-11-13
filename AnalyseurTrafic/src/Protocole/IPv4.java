package Protocole;

public class IPv4 extends Protocole{

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
   private String flagHex;
   private String flag;
   private String  protocole;
   private String protocoleNom;

   public IPv4(String o) throws OctetInvalidException {
      super(o,"IPv4");
      String p1[]= getBytes(get(0));
      //4 premier octets
      version =  hexToDec(p1[0]);
      headerLength = hexToDec(p1[1])*4;

      typeService = get(1);
      lengthTotal = hexToDec(get(2)+get(3));
      //4 a 8eme octet
      id = get(4)+get(5);
      offset = get(6)+get(7);
      flagHex = get(6);
      //8 a 12 octet
      ttl = hexToDec(get(8));
      protocole = get(9);
      setProtocoleNom();
      checksum = get(10)+get(11);      
      src = hexToDec(get(12))+"."+hexToDec(get(13))+"."+hexToDec(get(14))+"."+hexToDec(get(15));
      des = hexToDec(get(16))+"."+hexToDec(get(17))+"."+hexToDec(get(18))+"."+hexToDec(get(19));
      flag=flag();
   }

   private void setProtocoleNom() throws OctetInvalidException{
      if(protocole.equalsIgnoreCase("01"))protocoleNom="ICMP";
      if(protocole.equalsIgnoreCase("02"))protocoleNom="IGMP";
      if(protocole.equalsIgnoreCase("06"))protocoleNom="TCP";
      if(protocole.equalsIgnoreCase("08"))protocoleNom="EGP";
      if(protocole.equalsIgnoreCase("09"))protocoleNom="IGP";
      if(protocole.equalsIgnoreCase("17"))protocoleNom="UDP";
      if(protocole.equalsIgnoreCase("36"))protocoleNom="XTP";
      if(protocole.equalsIgnoreCase("46"))protocoleNom="RSVP";
      if(protocoleNom==null){
         throw new OctetInvalidException("Le type du protocol apres la couche 3(ipv4) n'existe pas!");
      }
   }

   /*
   * Verifie si la prochaine couche est TCP
   */
   public boolean nextIsTCP(){
      return protocoleNom.equalsIgnoreCase("TCP");
   }
   
   public String getNextProtocol(){
      return protocoleNom;
   }

   public int getLength(){
      return headerLength*4;
   }

   public String flag(){
      String s = "";
      String binary = hexToBinary(flagHex);
      //System.out.println(flagHex);
      System.out.println(binary);
      //Reserved bit
      s+="\t\t"+binary.charAt(0)+"... .... = Reserved bit: ";
      if(binary.charAt(0)=='1'){
          flag+=" Reserved bit";
          s+="Set\n";
      }
      else{
          s+="Not Set\n";
      }

      //Don't fragment
      s+="\t\t."+binary.charAt(1)+".. .... = Don't Fragment: ";
      if(binary.charAt(1)=='1'){
          flag+=" Reserved bit";
          s+="Set\n";
      }
      else{
          s+="Not Set\n";
      }

      //More fragment
      s+="\t\t.."+binary.charAt(2)+". .... = More Fragment: ";
      if(binary.charAt(2)=='1'){
          flag+=" More Fragment";
          s+="Set\n";
      }
      else{
          s+="Not Set\n";
      }

      //Reserved bit
      s+="\t..."+binary.charAt(3)+" "+binary.substring(4)+" = Fragment Offset: ";
      
      return s;
   }

   public String toString(){
      StringBuilder sb = new StringBuilder();
      sb.append(super.toString());
      sb.append("\tVersion "+version+"\n");
      sb.append("\tLength Header : "+headerLength+" octets\n");
      sb.append("\tType Service : "+typeService+"\n");
      sb.append("\tTotal length : "+lengthTotal+"\n");
      sb.append("\tIdentification : 0x"+id+" ("+hexToDec(id)+")\n");
      sb.append("\tFlags : 0x"+flagHex+"\n");
      sb.append(flag+"\n");
      sb.append("\tTime To Live : "+ttl+"\n");
      sb.append("\tProtocole : "+protocole+ " ("+ protocoleNom+")\n");
      sb.append("\tChecksum : 0x"+checksum+"\n");
      sb.append("\tIP source : "+src+"\n");
      sb.append("\tIP destination : "+des+"\n");
      return sb.toString();
   }
}
