import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * A class that will write the catalogues from the memory to the filesystem
 */
public class Writer {
    /**
    * A method that will write the catalogues from the memory to the filesystem
    */
     public static void writeCatalogues(List<Catalogue> catalogues) {
        FileWriter fw;
        try {
            for(Catalogue catalogue : catalogues) {
                System.out.println("Writing to file: " + catalogue.getClass().getName() + ".txt");
                fw = new FileWriter(catalogue.getClass().getName() + ".txt");
                fw.write(catalogue.serialized());
                fw.flush();
                fw.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to file!");
        }
    }
}
