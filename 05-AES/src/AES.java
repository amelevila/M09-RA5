public class AES {
    public static final String ALGORISME_XIFRAt = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAt_AES = "AES/CBC/PKCS5Padding";

    private static final int MIDA_IV = 16;
    private static byte[] iv = new byte[MIDA_IV];
    private static final String CLAU = "clau1112";

    public static byte[] xifraAES(String msg, String clau) throws Exception {
        //Obtenir els bytes de l'String

        //Genera IvParameterSpec

        //Genera hash

        //Encrypt

        //Combiar IV i part xifrada

        //return iv+msgxifrat
        return null;
    }

    public static String desxifraAES(byte[] bIvIMsgXifrat, String clau) throws Exception {
        //Extreure l'IV

        //Extreure la part xifrada

        //Fer hash de la clau

        //Desxifrat

        //return String desxifrat
        return "";
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
