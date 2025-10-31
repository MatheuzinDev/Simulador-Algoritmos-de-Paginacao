import java.util.*;

public class FIFO {
    public static int simular(int[] pages, int frames) {
        Queue<Integer> memoria = new LinkedList<>();
        int faltas = 0;

        for (int p : pages) {
            if (!memoria.contains(p)) {
                if (memoria.size() == frames)
                    memoria.poll();
                memoria.add(p);
                faltas++;
            }
        }

        return faltas;
    }
}
