package iticbcn.xifratge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class XifradorPolialfabetic implements Xifrador {
    static final char[] ABECEDARI = "AÀÁÄBCÇDÈÉËFGHIÌÍÏJKLMNÑOÒÓÖPQRSTUÙÚÜVWXYZ".toCharArray();
    Random rnd = null;

    public void initRandom(long seed) {
        rnd = new Random(seed);
    }

    public char[] permutaAlfabet(char[] alfabet) {
        List<Character> llista = new ArrayList<>();
        for (char c : alfabet) llista.add(c);
        Collections.shuffle(llista, rnd);

        char[] permutat = new char[llista.size()];
        for (int i = 0; i<llista.size(); i++) {
            permutat[i] = llista.get(i);
        }

        return permutat;
    }
    
    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        return new TextXifrat(xifraDesxifraPoliAlfa(msg, clau, true).getBytes());
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        String msg = (xifrat == null) ? null:xifrat.toString();
        return xifraDesxifraPoliAlfa(msg, clau, false);
    }

    public String xifraDesxifraPoliAlfa(String msg, String clau, boolean xifra) throws ClauNoSuportada{
        long clauL;
        try {
            clauL = Long.parseLong(clau);
        }
        catch (NumberFormatException e){
            throw new ClauNoSuportada("La clau de Polialfabètic ha de ser un String convertible a long");
        }
        initRandom(clauL);

        char[] abecedari = (xifra) ? ABECEDARI:permutaAlfabet(ABECEDARI);
        char[] permutat = (xifra) ? permutaAlfabet(ABECEDARI):ABECEDARI;

        StringBuilder result = new StringBuilder();
        for (char c : msg.toCharArray()) {
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

    public int buscaLletra(char c, char[] array) {
        for (int i = 0; i<array.length; i++) {
            char ar = array[i];
            if (c == ar) return i;
        }
        return -1;
    }
}