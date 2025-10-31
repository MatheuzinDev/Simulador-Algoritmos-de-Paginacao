import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite a sequência de páginas (ex: 1 2 3 4 1 2 5):");
        int[] pages = Arrays.stream(scanner.nextLine().trim().split("\\s+"))
                .mapToInt(Integer::parseInt).toArray();

        System.out.println("Digite o número de quadros de memória:");
        int frames = scanner.nextInt();

        System.out.println("\n--- Resultados ---");
        System.out.println("FIFO - " + FIFO.simular(pages, frames) + " faltas de página");

    }
}
