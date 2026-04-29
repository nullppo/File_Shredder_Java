import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    private static void printBanner() {
        System.out.println("     _                  _     _           ");
        System.out.println("    | |                | |   | |          ");
        System.out.println(" ___| |__  _ __ ___  __| | __| | ___ _ __ ");
        System.out.println("/ __| '_ \\| '__/ _ \\/ _` |/ _` |/ _ \\ '__|");
        System.out.println("\\__ \\ | | | | |  __/ (_| | (_| |  __/ |   ");
        System.out.println("|___/_| |_|_|  \\___|\\_,_|\\__,_|\\___|_|   ");
        System.out.println();
    }
    public static void main(String[] args) {

        try (Scanner in = new Scanner(System.in)) {
            printBanner();
            System.out.print("Введите путь: ");
            Path path = Paths.get(in.nextLine());

            if (!Files.exists(path) || !Files.isRegularFile(path)) {
                System.err.println("Файл не найден или указана директория.");
                return;
            }

            System.out.println("Шреддер начинает работу..");
            Shredder.shred(path);
            System.out.println("Успешно");

        } catch (IOException e) {
            System.err.println("Ошибка -> " + e.getMessage());
            System.exit(1);
        }
    }
}