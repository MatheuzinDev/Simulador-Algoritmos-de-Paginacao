import java.util.*;

public class LRU {
    public static int simular(int[] pages, int frames) {
        LinkedHashSet<Integer> memoria = new LinkedHashSet<>();
        int faltas = 0;

        for (int p : pages) {
            if (!memoria.contains(p)) {
                if (memoria.size() == frames)
                    memoria.remove(memoria.iterator().next());
                memoria.add(p);
                faltas++;
            } else {
                memoria.remove(p);
                memoria.add(p);
            }
        }

        return faltas;
    }
}
