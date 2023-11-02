import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PublisherCount {

    public static final int COL_TITLE = 1;
    public static final int COL_PUBLISHED = 11;

    public static void main(String[] args) throws FileNotFoundException, IOException {

        if (args.length <=0){
            System.err.println("missing book csv");
            System.exit(1);
        }

        System.out.printf("Processing %s\n", args[0]);

        try (FileReader fr = new FileReader(args[0])) {
            BufferedReader br = new BufferedReader(fr);
            //br.readLine()

            Map<String, List<Book>> classified = br.lines() //// get.publisher returns string. map: key is string, list of books 
                .skip(1) // skips first column 
                // String -> String[] - stream goes through the row one row at a time 
                .map(row ->row.trim().split(",")) //do not use trim on password data becuse there can be spaces. csv file so split using comma 
                // String[] -> Book 
                .map(fields -> new Book(fields[COL_TITLE],fields[COL_PUBLISHED])) // map fields to the created Book object
                // groupingBy -> returns a value that classifies the input (get a book and assign the book to a publisher)
                .collect(Collectors.groupingBy(book -> book.getPublisher())); // functions that only require parameters to function are called pure parameters (no reference to external data)

            // to access map, need keyset (read maps documentation )
            for (String publisher: classified.keySet()) {
                List<Book> books = classified.get(publisher);
                System.out.printf("%s (%d)\n", publisher, books.size());

                for (Book book: books){
                    System.out.printf("\t%s\n", book.getTitle());
                }
        }
                
    }
}
}
