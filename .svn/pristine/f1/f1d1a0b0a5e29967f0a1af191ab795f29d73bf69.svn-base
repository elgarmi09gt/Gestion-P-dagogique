package view.beans.aes;

import java.security.Key;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import oracle.dss.util.BASE64Decoder;
import oracle.dss.util.BASE64Encoder;


public class AESBean {
    
    private static final String ALGO = "AES";
    private byte[] keyvalue;
    
    public AESBean(String key) {
        super();
        keyvalue = key.getBytes();
    }
    
    public String encrypt(String data) throws Exception{
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(data.getBytes());
        @SuppressWarnings("oracle.jdeveloper.java.semantic-warning")
        String encryptedValue = new BASE64Encoder().encode(encVal);
        return encryptedValue;
    }
    
    public String decrypt(String encdata) throws Exception{
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        @SuppressWarnings("oracle.jdeveloper.java.semantic-warning")
        byte[] decodedValue = new BASE64Decoder().decode(encdata);
        byte[] decVal = c.doFinal(decodedValue);
        String decryptValue = new String(decVal);
        return decryptValue;
    }
    
    private Key generateKey() throws Exception{
        Key key = new SecretKeySpec(keyvalue, ALGO);
        return key;
    }
    
    public static void main(String[] args) {
        //clé : pssapssapsaspsaspasspass
        try {
            AESBean aESBean = new AESBean("2122221221221222");
            //String encData = aESBean.encrypt("Garmi");
            String crype ="LoIdJUz4+eNnxZmoKdJBEQ==";
            //System.out.println("Data crypted : "+encData);
            String decData = aESBean.decrypt(crype);
            System.out.println("Data decrypted : "+decData);
            
        } catch (Exception e) {
            Logger.getLogger(AESBean.class.getName()).log(Level.SEVERE, null, e);
        }

    }
    
}
