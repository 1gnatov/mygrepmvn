import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    // program arguments
    //"qwerty" file.txt testdir\\secondfile.txt file3.txt filenottext.bin test.html test2.html test3.html test4.html test5.html

    public static void main(String[] args) {

        //time it!
        long startTime = System.nanoTime();

        System.out.println(Arrays.toString(args));
        System.out.println(args[0]);

        String que = args[0];

        for (int i=1; i<args.length; i++) {
            String filename = args[i];
            //System.out.println("Searching in: " + filename);
            try {
                Stream<String> streamFromFiles = Files.lines(Paths.get(filename));
                //Stream<String> streamFromFiles = Files.lines(Paths.get("testdir\\secondfile.txt"));
                Collection<String> result = streamFromFiles
                        .parallel()
                        .filter((s) -> s.contains(que))
                        .collect(Collectors.toList());
                // result can be 0, 1, or >
                if (!result.isEmpty()) {
                    for (String r: result) {
                        System.out.println("File: " + filename + ", string: " + r);
                    }

                }
            } catch (IOException e) {
                System.out.println("No such file: " + e.toString());
            } catch (UncheckedIOException e) {
                System.out.println("Cannot read file " + filename + ": check type of file and charset");
            }
        }

//        long endTime = System.nanoTime();
//        long duration = (endTime - startTime);
//        System.out.println("It takes " + duration/1000000 + " ms");

    }
}
