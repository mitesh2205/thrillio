package thrillio.util;

import java.security.MessageDigest;


//A class that encrypts user passwords
public class StringUtil {
	private final static String ENC_ALGO = "SHA";
	
	/**
     * A method that encodes and encrypts a password
     * 
     * If there is an exception, then the plain-text string is returned
     * 
     * @param password --> The password to encrypt
     * @return The encrypted password
     */
    public static String encodePassword(String password) {
    	
    	//Encodes the password into a sequence of bytes
        byte[] unencodedPassword = password.getBytes();
        
        MessageDigest md = null;
        
        try {
            
        	//Creates the message digest
            md = MessageDigest.getInstance(ENC_ALGO);
        } catch (Exception e) {
            //log.error("Exception: " + e);
            return password;
        }
        
        md.reset();
        
        //Updates the message digest
        md.update(unencodedPassword);
        
        //Calculates the hash value
        byte[] encodedPassword = md.digest();
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < encodedPassword.length; i++) {
            if ((encodedPassword[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
        }
        return buf.toString();
    }
}


