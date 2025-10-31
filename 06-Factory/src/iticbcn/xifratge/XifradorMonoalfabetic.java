package iticbcn.xifratge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class XifradorMonoalfabetic implements Xifrador {
    static final char[] ABECEDARI = "AÀÁÄBCÇDÈÉËFGHIÌÍÏJKLMNÑOÒÓÖPQRSTUÙÚÜVWXYZ".toCharArray();
    static char[] PERMUTAT;

    public XifradorMonoalfabetic() {
        PERMUTAT = permutaAlfabet(ABECEDARI);
    }
    
    private char[] permutaAlfabet(char[] alfabet) {
        List<Character> llista = new ArrayList<>();
        for (char c : alfabet) llista.add(c);
        Collections.shuffle(llista);

        char[] permutat = new char[llista.size()];
        for (int i = 0; i<llista.size(); i++) {
            permutat[i] = llista.get(i);
        }
        
        return permutat;
    }

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        return new TextXifrat(xifraDesxifraMonoAlfa(msg, clau, true).getBytes());
    }
    
    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        String msg = (xifrat == null) ? null:xifrat.toString();
        return xifraDesxifraMonoAlfa(msg, clau, false);
    }

    public String xifraDesxifraMonoAlfa(String msg, String clau, boolean xifra) throws ClauNoSuportada {
        if (clau != null) throw new ClauNoSuportada("Xifratxe monoalfabètic no suporta clau != null");

        char[] abecedari = (xifra) ? ABECEDARI:PERMUTAT;
        char[] permutat = (xifra) ? PERMUTAT:ABECEDARI;

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
