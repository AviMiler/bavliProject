import java.util.ArrayList;
import java.util.Scanner;

public class Units {

    public static class Bavli {
        private final String path;
        private final ArrayList<Masechet> listOfMasechet;


        public Bavli(String path) {
            this.path = path;
            listOfMasechet = new ArrayList<>();
            System.out.println("build bavli");
        }

        public Units.Masechet addMasechet(String DName) {
            Masechet masechet = new Masechet(DName, path);
            listOfMasechet.add(Funcs.calculateSpot(listOfMasechet, masechet), masechet);
            return masechet;
        }

        public String getPath() {
            return this.path;
        }

        public Masechet getMasechet(String DName) {
            return Funcs.binaryMasechetSearch(DName, listOfMasechet);
        }

        public void printMasechet() {
            for (Masechet masechet : listOfMasechet) {
                System.out.println(masechet.getName());
            }
        }
    }

    public static class Masechet {
        private final String name;
        private final String path;
        private final ArrayList<Chapter> listOfChapters;
        private final ArrayList<Page> listOfPages;
        private final Cache.Cache1 cache;

        public Masechet(String name, String path) {
            this.name = name;
            this.path = path + "\\" + name;
            listOfChapters = new ArrayList<>();
            listOfPages = new ArrayList<>();
            System.out.println("build DB for masechet " + name);
            cache = new Cache.Cache1();
        }
        public Masechet(){
            this.name = null;
            this.path = null;
            listOfChapters = null;
            listOfPages = null;
            cache = null;
        }

        public String getName() {
            return name;
        }

        public String getPath() {
            return path;
        }

        public Chapter getChapterByName(String name) {
            for (Chapter chapter : listOfChapters)
                if (name.equals(chapter.getName().split("-")[1].trim()))
                    return chapter;
            return null;
        }

        public Chapter addChapter(String name) {
            Chapter chapter = new Chapter(name);
            listOfChapters.add(chapter);
            return chapter;
        }

        public Page addPage(String name, String path){
            Page page = new Page(name, this.getPath() + "\\" + path);
            listOfPages.add(Funcs.calculateSpotByGimetry(listOfPages, page), page);
            listOfChapters.getLast().addPage(page);
            return page;
        }

        public void addToCache(Page page) {
            cache.addPage(page);
        }

        public Page searchAtCache(String key) {
            return cache.getPage(key);
        }

        public void printCache() {
            cache.printCache();
        }

        public int getNumOfGages() {
            return listOfPages.size();
        }

        public Page getPage(String PName) {
            return Funcs.binaryPageSearch(PName, listOfPages);
        }

        public ArrayList<Page> getListOfPages(){
            return listOfPages;
        }

        public void printChapters() {
            for (Chapter listOfChapter : listOfChapters) {
                System.out.println(listOfChapter.getName());
            }
        }
    }

    public static class Chapter {
        private final String name;
        private final ArrayList<Page> listOfPages;
        private final ArrayList<Mishna> listOfMishna;

        public Chapter(String name) {
            this.name = name;
            listOfPages = new ArrayList<>();
            listOfMishna = new ArrayList<>();
        }

        public Chapter() {
            this.name = null;
            listOfPages = null;
            listOfMishna = null;
        }


        public void addPage(Page page) {
            assert listOfPages != null;
            listOfPages.add(Funcs.calculateSpotByGimetry(listOfPages, page), page);
        }


        public ArrayList<Page> getListOfPages() {
            return listOfPages;
        }

        public ArrayList<Mishna> getListOfMishna() {
            return listOfMishna;
        }

        public String getName() {
            return this.name;
        }

        public void addMishna(String content, String path, int index) {
            ArrayList<Mishna> l = setMishnaIndexes(content, path, index);
            assert listOfMishna != null;
            listOfMishna.addAll(l);
        }

    }

    public static class Page {
        private final String name;
        private final String path;

        public Page(String name, String path){
            this.name = name;
            this.path = path;
            System.out.println("build page " + name);
        }

        public String getName() {
            return name;
        }

        public String getPath() {
            return path;
        }
    }

    public static class Mishna {

        private int start;
        private int end;
        private final String path;

        public Mishna(String path) {
            this.start = -1;
            this.end = -1;
            this.path = path;
        }

        public String getPath() {
            return path;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public void setEnd(int end) {
            this.end = end;
        }
    }

    private static ArrayList<Mishna> setMishnaIndexes(String content, String path, int index) {
        ArrayList<Mishna> list = new ArrayList<>();
        int Index = index, size;
        Mishna mishna = new Mishna(path);
        String token;
        String[] sToken;
        Scanner scanner = new Scanner(content);
        scanner.useDelimiter("'");
        while (scanner.hasNext()) {
            token = scanner.next();
            sToken = token.split(" ");
            size = sToken.length;
            Index += token.length();
            if (sToken[size - 1].endsWith("מתני")) {
                mishna.setStart(Index - 4);
                System.out.println("mishna start at " + Index);
                list.add(mishna);
            } else if (sToken[size - 1].endsWith("גמ")) {
                mishna.setEnd(Index - 2);
                if (mishna.getStart() == -1)
                    list.add(mishna);
                mishna = new Mishna(path);
                System.out.println("mishna end at " + Index);
            }
        }
        return list;
    }
}
