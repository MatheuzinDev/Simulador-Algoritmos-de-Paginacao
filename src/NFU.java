import java.util.*;

public class NFU {
    public static int simular(int[] pages, int frames) {
        Map<Integer, Integer> contador = new HashMap<>();
        List<Integer> memoria = new ArrayList<>();
        int faltas = 0;

        for (int p : pages) {
            if (!memoria.contains(p)) {
                if (memoria.size() == frames) {
                    int menorFreq = Integer.MAX_VALUE, substituir = -1;
                    for (int m : memoria) {
                        int freq = contador.getOrDefault(m, 0);
                        if (freq < menorFreq) {
                            menorFreq = freq;
                            substituir = m;
                        }
                    }
                    memoria.remove((Integer) substituir);
                    contador.remove(substituir);
                }
                memoria.add(p);
                faltas++;
            }
            contador.put(p, contador.getOrDefault(p, 0) + 1);
        }
        return faltas;
    }
}
