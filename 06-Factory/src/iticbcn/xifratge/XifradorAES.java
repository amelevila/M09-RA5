package iticbcn.xifratge;

//javax.crypto.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class XifradorAES implements Xifrador {

    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";
    public static final Charset CHARSET = StandardCharsets.UTF_8;

    private static final int MIDA_IV = 16;

    private byte[] iv = new byte[MIDA_IV];

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        try {
            //Obtenir els bytes de l'String
            byte[] msgBytes = msg.getBytes();
    
            //Genera IvParameterSpec
            SecureRandom random = new SecureRandom();
            random.nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            //Genera hash
            MessageDigest sha = MessageDigest.getInstance(ALGORISME_HASH);
            byte[] keyBytes = sha.digest(clau.getBytes(CHARSET));
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, ALGORISME_XIFRAT);

            //Encrypt
            Cipher cipher = Cipher.getInstance(FORMAT_AES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            byte[] msgXifrat = cipher.doFinal(msgBytes);

            //Combiar IV i part xifrada
            byte[] bIvIMsgXifrat = new byte[MIDA_IV + msgXifrat.length];
            System.arraycopy(iv, 0, bIvIMsgXifrat, 0, MIDA_IV);
            System.arraycopy(msgXifrat, 0, bIvIMsgXifrat, MIDA_IV, msgXifrat.length);

            //return iv+msgxifrat
            return new TextXifrat(bIvIMsgXifrat);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        try {
            byte[] bIvIMsgXifrat = xifrat.getBytes();
    
            //Extreure l'IV
            iv = Arrays.copyOfRange(bIvIMsgXifrat, 0, 16);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
    
            //Extreure la part xifrada
            byte[] msgXifrat = Arrays.copyOfRange(bIvIMsgXifrat, MIDA_IV, bIvIMsgXifrat.length);
    
            //Fer hash de la clau
            MessageDigest sha = MessageDigest.getInstance(ALGORISME_HASH);
            byte[] keyBytes = sha.digest(clau.getBytes(CHARSET));
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, ALGORISME_XIFRAT);
    
            //Desxifrat
            Cipher cipher = Cipher.getInstance(FORMAT_AES);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
            byte[] msgDesxifrat = cipher.doFinal(msgXifrat);
    
            //return String desxifrat
            return new String(msgDesxifrat, CHARSET);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            System.out.println(e);
            return null;
        }
        
    }
}
