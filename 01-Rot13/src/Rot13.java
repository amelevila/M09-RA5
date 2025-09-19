public class Rot13 {
    static char[] minus = "aàáäbcçdèéëfghiìíïjklmnñoòóöpqrstuùúüvwxyz".toCharArray();
    static char[] mayus = "AÀÁÄBCÇDÈÉËFGHIÌÍÏJKLMNÑOÒÓÖPQRSTUÙÚÜVWXYZ".toCharArray();
    static int len = minus.length;

    public static String xifraRot13(String str) {
        return xifraRotN(str, 13);
    }
    
    public static String desxifraRot13(String str) {
        return xifraRotN(str, -13);
    }

    public static String xifraRotN(String str, int n) {
        String result = "";
        for (char c : str.toCharArray()) {
            if (buscaLletra(c, mayus) != -1) {
                int posicio = (buscaLletra(c, mayus) + n);
                char xifrada = (posicio < 0) ? mayus[posicio+len]:mayus[posicio % len];
                result += xifrada;
            }
            else if (buscaLletra(c, minus) != -1) {
                int posicio = (buscaLletra(c, minus) + n);
                char xifrada = (posicio < 0) ? minus[posicio+len]:minus[posicio % len];
                result += xifrada;
            }
            else result += c;
        }
        return result;
    }

    public static int buscaLletra(char c, char[] array) {
        for (int i = 0; i<len; i++) {
            char ar = array[i];
            if (c == ar) return i;
        }
        return -1;
    }

    public static void main(String[] args) {
        String[] texts = {"ABC", "XYZ", "Hola, Mr. calçot", "Perdó, per tu què és?"};
        //sortida
        System.out.println("\nXifrat\n---------");
        for (String text : texts){
            System.out.printf("%-25s => %s\n", text, xifraRot13(text));
        }

        System.out.println("\nDesxifrat\n---------");
        for (String text : texts){
            System.out.printf("%-25s => %s\n", xifraRot13(text), desxifraRot13(xifraRot13(text)));
        }

    }
}