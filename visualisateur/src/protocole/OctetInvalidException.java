package protocole;

@SuppressWarnings("serial")
/*
 * Exception si un octet n'est pas codee en hexa c'est a dire entre 1 et f
 */
public class OctetInvalidException extends Exception{
    public OctetInvalidException(String msg){
        super(msg);
    }
}