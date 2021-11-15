import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class MainApp {
    private static String INPUT;

    private static void runSc(Scanner sc) {

        System.out.println("Введиде дерикторию для поиска дубликатов файлов ");
        System.out.println("Для выхода введите q ");
        INPUT = sc.nextLine();
        if (INPUT.equals("q")) {
            System.exit(0);
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);


        Identity checkFiles = new IdentityImpl();
        List<OurFile> filesInFolder = new ArrayList<>();
        for (int i = 5; i >= 0; ) {
            runSc(sc);
            try {
                filesInFolder = checkFiles.findFilesInFolder(INPUT);
                break;
            } catch (Exception e) {
                i--;
                System.err.println("Директория не найдена");
            }
        }

        long start = System.nanoTime();

        checkFiles.printUniqueFiles(filesInFolder);
        List<OurFile> duplicates = checkFiles.findDuplicates(filesInFolder);
        System.out.println("Дубликаты: " + duplicates);
        checkFiles.printSumOfDuplicates(duplicates);



        long fin = System.nanoTime();
        System.out.printf("Время работы заняло: %.4f сек \n", ((fin - start) * 1e-9));

        System.out.println("Нажмите Y чтобы удалить дубликаты, для выхода нажмите любую клавишу");
        String input = sc.nextLine();
        if(input.equals("Y")){
            checkFiles.removeDuplicates(duplicates);
        }
    }
}
