import java.util.*;

public class Clock {
    public static int simular(int[] pages, int frames) {
        int faltas = 0;
        int ponteiro = 0;
        int[] memoria = new int[frames];
        boolean[] uso = new boolean[frames];
        Arrays.fill(memoria, -1);

        for (int p : pages) {
            boolean hit = false;
            for (int i = 0; i < frames; i++) {
                if (memoria[i] == p) {
                    uso[i] = true;
                    hit = true;
                    break;
                }
            }

            if (!hit) {
                while (uso[ponteiro]) {
                    uso[ponteiro] = false;
                    ponteiro = (ponteiro + 1) % frames;
                }
                memoria[ponteiro] = p;
                uso[ponteiro] = true;
                ponteiro = (ponteiro + 1) % frames;
                faltas++;
            }
        }
        return faltas;
    }
}
