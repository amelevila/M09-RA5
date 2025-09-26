public class RotX {
    static final char[] MINUS = "aàáäbcçdèéëfghiìíïjklmnñoòóöpqrstuùúüvwxyz".toCharArray();
    static final char[] MAYUS = "AÀÁÄBCÇDÈÉËFGHIÌÍÏJKLMNÑOÒÓÖPQRSTUÙÚÜVWXYZ".toCharArray();
    static final int LEN = MINUS.length;

    public static String xifraRotX(String str, int n) {
        return xifraRotN(str, n);
    }
    
    public static String desxifraRotX(String str, int n) {
        return xifraRotN(str, -n);
    }

    public static String xifraRotN(String str, int n) {
        StringBuilder result = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (buscaLletra(c, MAYUS) == -1 && buscaLletra(c, MINUS) == -1) {
                result.append(c);
                continue;
            }

            char[] abecedari = (buscaLletra(c, MINUS) != -1) ? MINUS:MAYUS;
            int posicio = (buscaLletra(c, abecedari) + n);
            char xifrada = (posicio < 0) ? abecedari[posicio+LEN]:abecedari[posicio % LEN];
            result.append(xifrada);
        }
        return result.toString();
    }

    public static int buscaLletra(char c, char[] array) {
        for (int i = 0; i<LEN; i++) {
            char ar = array[i];
            if (c == ar) return i;
        }
        return -1;
    }

    public static void forcaBrutaRotX(String str) {
        System.out.printf("\nMissatge xifrat: %s\n----------------\n", str);
        for (int i = 0; i<LEN; i++) {
            System.out.printf("(%d)->%s\n", i, desxifraRotX(str, i));
        }
    }

    public static void main(String[] args) {
        String[] texts = {"ABC", "XYZ", "Hola, Mr. calçot", "Perdó, per tu què és?"};
        int[] desplaçaments = {0,2,4,6};
        //sortida
        System.out.println("\nXifrat\n------");
        for (int i = 0; i<texts.length; i++){
            String text = texts[i];
            int desplaçament = desplaçaments[i];
            System.out.printf("(%d)%-25s => %s\n", desplaçament, text, xifraRotX(text, desplaçament));
        }

        System.out.println("\nDesxifrat\n---------");
        for (int i = 0; i<texts.length; i++){
            String text = texts[i];
            int desplaçament = desplaçaments[i];
            System.out.printf("(%d)%-25s => %s\n", desplaçament, xifraRotX(text, desplaçament), desxifraRotX(xifraRotX(text, desplaçament), desplaçament));
        }

        forcaBrutaRotX(xifraRotX(texts[3], 6));
    }
}
