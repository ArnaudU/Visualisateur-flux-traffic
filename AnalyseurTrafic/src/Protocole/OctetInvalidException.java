package protocole;

@SuppressWarnings("serial")
public class OctetInvalidException extends Exception{
    public OctetInvalidException(String msg){
        super(msg);
    }
}