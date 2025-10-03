import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Monoalfabetic {
    static final char[] ABECEDARI = "AÀÁÄBCÇDÈÉËFGHIÌÍÏJKLMNÑOÒÓÖPQRSTUÙÚÜVWXYZ".toCharArray();
    static final char[] PERMUTAT = permutaAlfabet(ABECEDARI);

    public static char[] permutaAlfabet(char[] alfabet) {
        List<Character> llista = new ArrayList<>();
        for (char c : alfabet) llista.add(c);
        Collections.shuffle(llista);

        char[] permutat = new char[llista.size()];
        for (int i = 0; i<llista.size(); i++) {
            permutat[i] = llista.get(i);
        }
        
        return permutat;
    }

    public static String xifraMonoAlfa(String str) {
        return xifraDesxifraMonoAlfa(str, true);
    }
    
    public static String desxifraMonoAlfa(String str) {
        return xifraDesxifraMonoAlfa(str, false);
    }

    public static String xifraDesxifraMonoAlfa(String str, boolean xifra) {
        char[] abecedari = (xifra) ? ABECEDARI:PERMUTAT;
        char[] permutat = (xifra) ? PERMUTAT:ABECEDARI;

        StringBuilder result = new StringBuilder();
        for (char c : str.toCharArray()) {
            boolean lowercase = false;
            if (Character.isLowerCase(c)) {
                lowercase = true;
                c = Character.toUpperCase(c);
            }
            if (buscaLletra(c, abecedari) != -1) {
                c = permutat[buscaLletra(c, abecedari)];
            }

            if (lowercase) c = Character.toLowerCase(c);
            result.append(c);
        }
        return result.toString();
    }

    public static int buscaLletra(char c, char[] array) {
        for (int i = 0; i<array.length; i++) {
            char ar = array[i];
            if (c == ar) return i;
        }
        return -1;
    }

    public static void main(String[] args) {
        String[] texts = {"ABC", "XYZ", "Hola, Mr. calçot", "Perdó, per tu què és?"};
        
        System.out.println("\nXifrat\n------");
        for (String text : texts) {
            System.out.printf("%-25s => %s\n", text, xifraMonoAlfa(text));
        }

        System.out.println("\nDesxifrat\n---------");
        for (String text : texts) {
            String xifrat = xifraMonoAlfa(text);
            System.out.printf("%-25s => %s\n", xifrat, desxifraMonoAlfa(xifrat));
        }
    }
}
