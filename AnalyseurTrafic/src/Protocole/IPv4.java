package Protocole;

import java.util.ArrayList;
import java.util.List;

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
   private List<String> options = new ArrayList<>();

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
      src = getIP(12);
      des = getIP(16);
      flag=flag();
      if(lengthTotal>20 && size()>20){
         setOption();
      }
   }

   /*
    * Permet de savoir quel est le nom du prochain protocole
    */
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
         throw new OctetInvalidException("Le type du protocol apres la couche 3(ipv4) n'existe probablement pas!");
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
      return headerLength;
   }

   /*
    * Retourne sous forme de wireshark le binaire des flag dans la couche ipv4
    */
   public String flag(){
      String s = "";
      String binary = hexToBinary(flagHex);
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
      int i = binaryToDec(binary.charAt(3)+hexToBinary(offset).substring(4));
      s+="\t..."+binary.charAt(3)+" "+hexToBinary(offset).substring(4)+" = Fragment Offset: "+i;
      
      return s;
   }

   private String getIP(int begin){
      return  hexToDec(get(begin++))+"."+hexToDec(get(begin++))+"."+hexToDec(get(begin++))+"."+hexToDec(get(begin++));
   }

   public void setOption(){
      int lengthOption=lengthTotal-20;
      int i=0;
      while(i<lengthOption){
         System.out.println(i +" "+ lengthOption);
         // 1001
         String write="\t\t";
         String type = get(20+i);
         if(type.equalsIgnoreCase("01"))write+="NOP : ";
         if(type.equalsIgnoreCase("44"))write+="TS : ";
         if(type.equalsIgnoreCase("83"))write+="LSR : ";
         if(type.equalsIgnoreCase("89"))write+="SSR : ";
         i++;
         int length = hexToDec(get(20+i));
         i++;
         if(type.equalsIgnoreCase("07")){
            write+="RR : \n";
            int j=1;
            while(4*j<length){
               write+= "\t\t\t"+j+" : "+getIP(i+(j*4)+2);
               if(4*(j+1)<length){
                  write+="\n";
               }
               j++;
            }
         }
         else{
            if(type.equalsIgnoreCase("00")){
               write+="EOOL\n" ;
               options.add(write);
               break;
            }
            write+="0x"+get(20+i,20+length-2);
         }
         i+=length-2;
         write+="\n";
         options.add(write);
      }

   }

   public String getSrc(){
      return ""+src;
   }

   public String getDest(){
      return ""+des;
   }

   public String toString(){
      StringBuilder sb = new StringBuilder();
      sb.append(super.toString());
      sb.append("\tVersion "+version+"\n");
      sb.append("\tLength Header : "+headerLength+" octets\n");
      sb.append("\tType Service : "+typeService+"\n");
      sb.append("\tTotal length : "+lengthTotal+"\n");
      sb.append("\tIdentification : 0x"+hexToDec(id)+" ("+id+")\n");
      sb.append("\tFlags : 0x"+flagHex+"\n");
      sb.append(flag+"\n");
      sb.append("\tTime To Live : "+ttl+"\n");
      sb.append("\tProtocole : "+protocole+ " ("+ protocoleNom+")\n");
      sb.append("\tChecksum : 0x"+checksum+"\n");
      sb.append("\tIP source : "+src+"\n");
      sb.append("\tIP destination : "+des+"\n");
      if(options.size()>0){
         sb.append("\tOption(s):\n");
         for(String s : options){
            sb.append(s);
         }
      }
      return sb.toString();
   }
}
