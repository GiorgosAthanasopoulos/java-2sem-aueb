import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Writer {
    public static void writeCatalogues(List<Catalogue> catalogues) {
        FileWriter fw = null;
        try {
            for(Catalogue catalogue : catalogues) {
                fw = new FileWriter(new File(catalogue.getClass().getName() + ".json"));
                fw.write(catalogue.serialized());
                fw.flush();
                fw.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to file!");
        }
    }
}
