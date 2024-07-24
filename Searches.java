import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class Searches {

    static Units.Page printPageFromListFromMasechet(String masechet, String daf, Units.Bavli bavli) {

        Units.Page page;
        Units.Masechet masechet1 = bavli.getMasechet(masechet);
        System.out.println(masechet1.getName() + " " + masechet1.getNumOfGages() + " pages");
        if ((page = masechet1.getPage(daf)) == null) {
            System.out.println("daf didn't found");
            return null;
        }
        System.out.println(daf);
        Funcs.printLinePieces(Objects.requireNonNull(FileActions.readPage(page.getPath())), 15);
        return page;
    }

    static void searchByChapter(String masechet, String chapter, Units.Bavli bavli, int choice) {

        Units.Masechet masechet1 = bavli.getMasechet(masechet);
        Units.Page page;
        Units.Mishna mishna;

        Units.Chapter chapter1 = masechet1.getChapterByName(chapter);
        if (chapter1 == null) {
            System.out.println("chapter didn't found");
            return;
        }

        try {
            if (choice == 2) {
                for (int i = 0; i < chapter1.getListOfPages().size(); i++) {
                    page = chapter1.getListOfPages().get(i);
                    System.out.println(page.getName());
                    Funcs.printLinePieces(Objects.requireNonNull(FileActions.readPage(page.getPath())), 15);
                }
            } else {
                for (int i = 0; i < chapter1.getListOfMishna().size(); i++) {
                    mishna = chapter1.getListOfMishna().get(i);
                    Funcs.printLinePieces(FileActions.readMishna(mishna), 15);
                }

            }
        } catch (Exception e) {
            System.out.println("ERROR file can't open");
        }
    }

    public static void findPageFromFile(String masehet, String page) throws IOException {
        long index;
        String s;
        if ((index = FileActions.searchLine(0, "מסכת " + masehet, null)) > -1) {
            s = FileActions.readLines(index, 1);
            index += s.length() - 1;
            if ((index = FileActions.searchLine(index, "דף " + page, "מסכת")) > -1) {
                System.out.println(s + FileActions.readLines(index, 4));
            } else {
                System.out.println("daf doesn't exist");
            }
        } else {
            System.out.println("masechet doesn't exist");
        }

    }

    public static void findWordFromFile(String word){

        try {
            Scanner scanner = new Scanner(new File("C:\\Users\\amile\\IdeaProjects\\tirgulim\\aa\\bavliProject\\src\\bavli.txt"));
            String token, page = "", masechet = "";
            int cnt = 0;
            while (scanner.hasNext()) {
                token = scanner.nextLine();
                if (token.startsWith("מסכת")) {
                    masechet = token;
                }
                if (Funcs.startPage(token.split(" "))) {
                    if (scanner.hasNext())
                        page = token + scanner.nextLine();
                }
                if (token.contains(word)) {
                    System.out.println(masechet + " " + page);
                    cnt++;
                }
            }
            System.out.println(cnt + "times");
        }
        catch (FileNotFoundException e){
            System.out.println("File didnt found");
        }

    }

    static void searchPageFromDisk(String masechet, String daf) {
        String path = Main.dirName + "\\" + masechet;
        System.out.println("print " + path);
        try {
            BufferedReader reader = new BufferedReader(new FileReader((path + "\\" + daf + ".txt")));
            System.out.println(masechet + " " + daf);
            Funcs.printLinePieces(reader.readLine(), 15);
        } catch (IOException e) {
            System.out.println("daf didn't found");
        }
    }
}