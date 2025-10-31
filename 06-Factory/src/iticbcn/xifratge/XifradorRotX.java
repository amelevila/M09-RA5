package iticbcn.xifratge;

public class XifradorRotX implements Xifrador {
    static final char[] MINUS = "aàáäbcçdèéëfghiìíïjklmnñoòóöpqrstuùúüvwxyz".toCharArray();
    static final char[] MAYUS = "AÀÁÄBCÇDÈÉËFGHIÌÍÏJKLMNÑOÒÓÖPQRSTUÙÚÜVWXYZ".toCharArray();
    static final int LEN = MINUS.length;

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        return new TextXifrat(xifraRotN(msg, clau, true).getBytes());
    }
    
    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        String msg = (xifrat == null) ? null:xifrat.toString();
        return xifraRotN(msg, clau, false);
    }

    public String xifraRotN(String msg, String clau, boolean xifra) throws ClauNoSuportada {
        int n;
        try {
            n = Integer.parseInt(clau);
            if (n<0 || n>40) throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }

        if (!xifra) n = -n;

        StringBuilder result = new StringBuilder();
        for (char c : msg.toCharArray()) {
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

    public int buscaLletra(char c, char[] array) {
        for (int i = 0; i<LEN; i++) {
            char ar = array[i];
            if (c == ar) return i;
        }
        return -1;
    }
}
