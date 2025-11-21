import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HexFormat;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashes {
    public int npass = 0;

    public String getSHA512AmbSalt(String pw, String salt) {
        try {
            //generar hash (en bytes)
            String input = salt + pw;
            MessageDigest md = MessageDigest.getInstance("SHA512");
            byte[] hashBytes = md.digest(input.getBytes());

            //Convertir bytes del hash a String
            HexFormat hex = HexFormat.of();
            return hex.formatHex(hashBytes);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error a SHA-512", e);
        }
    }

    public String getPBKDF2AmbSalt(String pw, String salt) {
        try {
            //Parametres de PBKDF2
            byte[] saltBytes = salt.getBytes();
            int iterations = 65536;
            int keyLength = 512;

            //Crear PBEKeySpec
            PBEKeySpec spec = new PBEKeySpec(pw.toCharArray(), saltBytes, iterations, keyLength);

            //Crear SecretKeyFactory per generar el hash a partir de spec
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            
            //Generar el hash amb sfk
            byte[] hashBytes = skf.generateSecret(spec).getEncoded();

            //Convertir bytes del hash a String
            HexFormat hex = HexFormat.of();
            return hex.formatHex(hashBytes);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error a PBKDF2", e);
        }
    }

    public String forceBruta(String alg, String hash, String salt) {
        char[] cs = "abcdefABCDEF1234567890!".toCharArray();
        char[] pw = new char[6];
        npass = 0;
        for (char c0: cs) {
            pw[0] = c0;
            for (char c1: cs) {
                pw[1] = c1;
                for (char c2: cs) {
                    pw[2] = c2;
                    for (char c3: cs) {
                        pw[3] = c3;
                        for (char c4: cs) {
                            pw[4] = c4;
                            for (char c5: cs) {
                                pw[5] = c5;

                                //Contrasenya a String
                                String pwString = new String(pw);

                                //Generar hash a partir de la contrasenya
                                String hashTest;
                                switch (alg) {
                                    case "SHA-512" -> hashTest = getSHA512AmbSalt(pwString, salt);
                                    case "PBKDF2" -> hashTest = getPBKDF2AmbSalt(pwString, salt);
                                    default -> {return null;}
                                }

                                //
                                npass++;                            
                                if (hashTest.equals(hash)) return pwString;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public String getInterval(long t1, long t2) {
        long t = (t2-t1);
        long s = t/1000;
        long m = s/60;
        long h = m/60;
        long d = h/24;

        return String.format("%d dies / %d hores / %d minuts / %d segons / %d millis",
        d%24,h%60,m%60,s%60,t%1000);
    }
    
    public static void main(String[] args) {
        String salt = "qpoweiruañslkdfjz";
        String pw = "aaabF!";
        Hashes h = new Hashes();
        String[] aHashes = { h.getSHA512AmbSalt(pw, salt),
            h.getPBKDF2AmbSalt(pw, salt)};
        String pwTrobat;
        String[] algorismes = {"SHA-512", "PBKDF2"};
        for (int i = 0; i<aHashes.length; i++) {
            System.out.printf("===========================\n");
            System.out.printf("Algorisme: %s\n", algorismes[i]);
            System.out.printf("Hash: %s\n", aHashes[i]);
            System.out.printf("----------------------------\n");
            System.out.printf("-- Inici de força bruta ---\n");

            long t1 = System.currentTimeMillis();
            pwTrobat = h.forceBruta(algorismes[i], aHashes[i], salt);
            long t2 = System.currentTimeMillis();

            System.out.printf("Pass   : %s\n", pwTrobat);
            System.out.printf("Provats: %d\n", h.npass);
            System.out.printf("Temps  : %s\n", h.getInterval(t1, t2));
            System.out.printf("----------------------------\n\n");
        }

    }
}