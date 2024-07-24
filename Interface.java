import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Interface {
    public static void interface1(Units.Bavli bavli) {
        String masechetName="", daf, chapter;
        int choice;
        Units.Page page;
        Units.Masechet masechet=new Units.Masechet();
        ArrayList<Units.Masechet> listOfMasechet = new ArrayList<>();


        while (true) {
            System.out.println("1: for page.\n2: for all chapter.\n3: for mishna of chapter.\n4: to find an expression\n5: to end");
            choice = getNumFromUser();

            if (choice == 5)
                return;
            if (choice!=4) {
                bavli.printMasechet();
                System.out.println("enter masechet");
                masechetName = "מסכת " + getLineFromUser();
                if ((masechet = Funcs.binaryMasechetSearch(masechetName, listOfMasechet)) == null) {
                    if ((masechet = bavli.getMasechet(masechetName)) != null)
                        listOfMasechet.add(Funcs.calculateSpot(listOfMasechet, masechet), masechet);
                    else
                        choice = -1;
                }
            }
            switch (choice) {
                case 1: {
                    System.out.printf("pages from %s to %s \n",masechet.getListOfPages().getFirst().getName(),masechet.getListOfPages().getLast().getName());
                    System.out.println("enter daf");
                    daf = getWordFromUser();
                    System.out.println("enter amud");
                    daf += " - " + getWordFromUser();
                    if ((page = masechet.searchAtCache(masechet.getPath() + "\\" + daf)) == null)
                        page = Searches.printPageFromListFromMasechet(masechetName, daf, bavli);
                    else {
                        System.out.println("from cache");
                        System.out.println(FileActions.readPage(page.getPath()));
                    }
                    masechet.addToCache(page);
                    masechet.printCache();
                    break;
                }
                case 2, 3: {
                    masechet.printChapters();
                    System.out.println("enter perek");
                    chapter = getLineFromUser();
                    Searches.searchByChapter(masechetName, chapter, bavli, choice);
                    break;
                }
                case 4:{
                    System.out.println("enter an expression");
                    Searches.findWordFromFile(getLineFromUser());
                    break;
                }
                case -1: {
                    System.out.println("error masechet didnt found");
                }
            }
        }
    }

    static Units.Bavli start() {

        System.out.println("first time? press 1");
        if (getNumFromUser() == 1) {
            Main.originFileName = "C:\\Users\\amile\\IdeaProjects\\tirgulim\\aa\\bavliProject\\src\\bavli.txt";
            Main.dirName = "C:\\Users\\amile\\IdeaProjects\\tirgulim\\aa\\bavliProject\\bavli";
            try {
                Split.splitToFile();
            } catch (IOException e) {
                System.out.println("file didnt found");
            }
        }
        return Split.createDB(Main.dirName);
    }

    public static String getWordFromUser() {
        String word;
        Scanner scanner = new Scanner(System.in);
        try {
            word = scanner.next();
        } catch (IllegalArgumentException e) {
            System.out.println("please enter word");
            return getWordFromUser();
        }
        return word;
    }

    public static String getLineFromUser() {
        String word;
        Scanner scanner = new Scanner(System.in);
        try {
            word = scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println("please enter line");
            return getWordFromUser();
        }
        return word;
    }

    public static int getNumFromUser() {
        int n;
        Scanner scanner = new Scanner(System.in);
        try {
            n = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("please enter number");
            return getNumFromUser();
        }
        return n;
    }
}
