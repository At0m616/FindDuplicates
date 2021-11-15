import java.io.IOException;
import java.util.List;

public interface Identity {

    List<OurFile> findFilesInFolder(String folder) throws IOException;

    List<OurFile> findDuplicates(List<OurFile> folderFiles);

    void printUniqueFiles(List<OurFile> folderFiles);

    void printSumOfDuplicates(List<OurFile> duplicateFiles);

    int removeDuplicates(List<OurFile> duplicateFiles);

}
