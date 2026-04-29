import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.UUID;

public class Shredder {

    private static final int PASSES = 3;
    private static final int BUFFER_SIZE = 65536;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static void shred(Path path) throws IOException {
        overwrite(path);
        deleteWithRename(path);
    }

    private static void overwrite(Path path) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];

        try (RandomAccessFile raf = new RandomAccessFile(path.toFile(), "rw")) {
            long length = raf.length();

            for (int i = 1; i <= PASSES; i++) {
                System.out.printf("Проход %d/%d..%n", i, PASSES);
                raf.seek(0);
                long remaining = length;

                while (remaining > 0) {
                    RANDOM.nextBytes(buffer);
                    int chunk = (int) Math.min(BUFFER_SIZE, remaining);
                    raf.write(buffer, 0, chunk);
                    remaining -= chunk;
                }

                raf.getFD().sync();
            }
        }
    }

    private static void deleteWithRename(Path path) throws IOException {
        Path renamed = path.resolveSibling(UUID.randomUUID() + ".tmp");
        java.nio.file.Files.move(path, renamed);

        if (!renamed.toFile().delete()) {
            throw new IOException("Не удалось удалить файл - " + renamed);
        }
    }
}