import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Polialfabetic {
    static final char[] ABECEDARI = "AÀÁÄBCÇDÈÉËFGHIÌÍÏJKLMNÑOÒÓÖPQRSTUÙÚÜVWXYZ".toCharArray();
    static final long clauSecreta = 1112L;
    static Random rnd = null;

    public static void initRandom(long seed) {
        rnd = new Random(seed);
    }

    public static char[] permutaAlfabet(char[] alfabet) {
        List<Character> llista = new ArrayList<>();
        for (char c : alfabet) llista.add(c);
        Collections.shuffle(llista, rnd);

        char[] permutat = new char[llista.size()];
        for (int i = 0; i<llista.size(); i++) {
            permutat[i] = llista.get(i);
        }

        return permutat;
    }

    public static String xifraPoliAlfa(String str) {
        return xifraDesxifraPoliAlfa(str, true);
    }
    
    public static String desxifraPoliAlfa(String str) {
        return xifraDesxifraPoliAlfa(str, false);
    }

    public static String xifraDesxifraPoliAlfa(String str, boolean xifra) {
        char[] abecedari = (xifra) ? ABECEDARI:permutaAlfabet(ABECEDARI);
        char[] permutat = (xifra) ? permutaAlfabet(ABECEDARI):ABECEDARI;

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
        String msgs[] = {"Test 01 àrbritre, coixí, Perímetre", 
                        "Test 02 Taüll, DÍA, año",
                        "Test 03 Peça, Òrrius, Bòvila"};
        String msgsXifrats[] = new String[msgs.length];

        System.out.println("Xifratge:\n---------");
        for (int i = 0; i < msgs.length; i++) {
            initRandom(clauSecreta);
            msgsXifrats[i] = xifraPoliAlfa(msgs[i]);
            System.out.printf("%-34s -> %s%n", msgs[i], msgsXifrats[i]);
        }

        System.out.println("Desxifratge:\n---------");
        for (int i = 0; i < msgs.length; i++) {
            initRandom(clauSecreta);
            String msg = desxifraPoliAlfa(msgsXifrats[i]);
            System.out.printf("%-34s -> %s%n", msgsXifrats[i], msg);
        }
    }

}