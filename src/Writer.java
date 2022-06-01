import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Writer {
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
