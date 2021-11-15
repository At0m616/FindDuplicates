import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IdentityImpl implements Identity {


    private static List<OurFile> distinctList(List<OurFile> folderFiles) {
        return folderFiles.parallelStream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<OurFile> findFilesInFolder(String folder) throws IOException {

        return Files.walk(Paths.get(folder))
                .parallel()
                .filter(Files::isRegularFile)
                .map(Path::toString)
                .map(OurFile::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<OurFile> findDuplicates(List<OurFile> folderFiles) {
        List<OurFile> uniqueFiles = distinctList(folderFiles);

        List<OurFile> duplicates = new ArrayList<>();
        int count = 0;
        for (OurFile uniq : uniqueFiles) {
            for (OurFile all : folderFiles) {
                if (all.hashCode() == uniq.hashCode()) {
                    count++;
                }
                if (count > 1 & all.hashCode() == uniq.hashCode()) {
                    duplicates.add(all);
                }
            }
            count = 0;
        }

        return duplicates;
    }

    @Override
    public void printUniqueFiles(List<OurFile> folderFiles) {
        System.out.println("Уникальные файлы: " + distinctList(folderFiles));
    }

    @Override
    public void printSumOfDuplicates(List<OurFile> duplicateFiles) {

        double allSize = 0;
        for (OurFile f : duplicateFiles) {
            allSize += f.getFileSizeMB();
        }
        System.out.printf("При удалении дубликатов освободится %.3f MB \n", allSize);
    }

    @Override
    public int removeDuplicates(List<OurFile> duplicateFiles) {
        int deleteCount = 0;
        for (OurFile f : duplicateFiles) {
            if (f.delete()) {
                deleteCount++;
                System.out.println(f.getName() + " был удален");
            } else {
                System.out.println(f.getName() + " удалить не удалось");
            }
        }
        return deleteCount;
    }
}
