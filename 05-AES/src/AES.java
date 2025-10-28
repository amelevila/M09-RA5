//javax.crypto.*;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.*;
import java.util.Arrays;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class AES {
    public static final String ALGORISME_XIFRAt = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";
    public static final Charset CHARSET = StandardCharsets.UTF_8;

    private static final int MIDA_IV = 16;
    private static byte[] iv = new byte[MIDA_IV];
    private static final String CLAU = "clau1112";

    public static byte[] xifraAES(String msg, String clau) throws Exception {
        //Obtenir els bytes de l'String
        byte[] msgBytes = msg.getBytes();

        //Genera IvParameterSpec
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        //Genera hash
        MessageDigest sha = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] keyBytes = sha.digest(CLAU.getBytes(CHARSET));
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, ALGORISME_XIFRAt);

        //Encrypt
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        byte[] msgXifrat = cipher.doFinal(msgBytes);
        
        //Combiar IV i part xifrada
        byte[] bIvIMsgXifrat = new byte[MIDA_IV + msgXifrat.length];
        System.arraycopy(iv, 0, bIvIMsgXifrat, 0, MIDA_IV);
        System.arraycopy(msgXifrat, 0, bIvIMsgXifrat, MIDA_IV, msgXifrat.length);

        //return iv+msgxifrat
        return bIvIMsgXifrat;
    }

    public static String desxifraAES(byte[] bIvIMsgXifrat, String clau) throws Exception {
        //Extreure l'IV
        byte[] iv = Arrays.copyOfRange(bIvIMsgXifrat, 0, 16);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        //Extreure la part xifrada
        byte[] msgXifrat = Arrays.copyOfRange(bIvIMsgXifrat, MIDA_IV, bIvIMsgXifrat.length);
        
        //Fer hash de la clau
        MessageDigest sha = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] keyBytes = sha.digest(CLAU.getBytes(CHARSET));
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, ALGORISME_XIFRAt);

        //Desxifrat
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        byte[] msgDesxifrat = cipher.doFinal(msgXifrat);

        //return String desxifrat
        return new String(msgDesxifrat, CHARSET);
    }

    public static void main(String[] args) {
        String msgs[] = {"Lorem ipsum dicet",
                    "Hola Andrés cómo está tu cuñado",
                    "Àgora ïlla Ôtto"};

        for (int i = 0; i < msgs.length; i++) {
            String msg = msgs[i];

            byte[] bXifrats = null;
            String desxifrat = "";
            try {
                bXifrats = xifraAES(msg, CLAU);
                desxifrat = desxifraAES(bXifrats, CLAU);
            } catch (Exception e) {
                System.err.println("Error de xifrat: " 
                + e.getLocalizedMessage());
            }
            System.out.println("---------------------");
            System.out.println("Msg: " + msg);
            System.out.println("Enc: " + new String(bXifrats));
            System.out.println("DEC: " + desxifrat);
        }
    }
}
