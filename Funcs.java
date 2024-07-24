import java.util.ArrayList;

public class Funcs {
    static Units.Page binaryPageSearch(String FName, ArrayList<Units.Page> list) {
        int low = 0, high = list.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = list.get(mid).getName().compareTo(FName);
            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return list.get(mid);
            }
        }
        return null;
    }

    static Units.Masechet binaryMasechetSearch(String FName, ArrayList<Units.Masechet> list) {
        if (!list.isEmpty()) {
            int low = 0, high = list.size();
            while (low <= high) {
                int mid = (low + high) / 2;
                if (mid >= list.size())
                    return null;
                int cmp = list.get(mid).getName().compareTo(FName);
                if (cmp < 0) {
                    low = mid + 1;
                } else if (cmp > 0) {
                    high = mid - 1;
                } else {
                    return list.get(mid);
                }
            }
        }
        return null;
    }

    static int calculateSpot2(ArrayList<Units.Masechet> list, Units.Masechet masechet) {
        int i = 0;
        for (; i < list.size(); i++) {
            if (masechet.getName().compareTo(list.get(i).getName()) <= 0)
                return i;
        }
        return i;
    }

    static int calculateSpot(ArrayList<Units.Masechet> list, Units.Masechet masechet) {
        if (!list.isEmpty()) {
            int low = 0, high = list.size();
            while (low <= high) {
                int mid = (low + high) / 2;
                if (mid >= list.size())
                    return mid;
                int cmp = list.get(mid).getName().compareTo(masechet.getName());
                if (cmp < 0) {
                    low = mid + 1;
                } else if (cmp > 0) {
                    high = mid - 1;
                } else {
                    return mid;
                }
            }
        }
        return 0;
    }

    static int calculateSpotByGimetry(ArrayList<Units.Page> list, Units.Page page) {
        int i = 0;
        for (; i < list.size(); i++) {
            if (calculateGimetry(page.getName()) < calculateGimetry(list.get(i).getName()))
                return i;
        }
        return i;
    }
    static int calculateSpotByGimetry2(ArrayList<Units.Page> list, Units.Page page) {
        if (!list.isEmpty()) {
            int low = 0, high = list.size();
            while (low <= high) {
                int mid = (low + high) / 2;
                if (mid >= list.size())
                    return mid;

                int cmp = calculateGimetry(list.get(mid).getName())-calculateGimetry(page.getName());
                if (cmp < 0) {
                    low = mid + 1;
                } else if (cmp > 0) {
                    high = mid - 1;
                } else {
                    return mid;
                }
            }
        }
        return 0;
    }

    public static void printLinePieces(String line, int piece) {
        String[] splitLine = line.split(" ");
        for (int i = 0; i < splitLine.length; i++) {
            System.out.print(splitLine[i] + " ");
            if (i % piece == 0 && i != 0)
                System.out.println();
        }
    }

    static int calculateGimetry(String s) {
        char[] l = {'א', 'ב', 'ג', 'ד', 'ה', 'ו', 'ז', 'ח', 'ט', 'י', 'כ', 'ל', 'מ', 'נ', 'ס', 'ע', 'פ', 'צ', 'ק', 'ר', 'ש', 'ת'};
        int[] n = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 200, 300, 400};
        int sum = 0, lSize = l.length, sSize = s.length();
        for (int i = 0; i < sSize; i++) {
            for (int j = 0; j < lSize; j++) {
                if (l[j] == s.charAt(i)) {
                    sum += n[j];
                    break;
                }
            }
        }
        return sum;
    }
    static boolean startPage(String[] sToken) {
        if (sToken.length > 4)
            return false;
        return sToken[0].contains("דף") && sToken[2].contains("-");
    }

    static boolean startPerek(String[] sToken) {
        if (sToken.length < 4)
            return false;
        return sToken[0].contains("פרק") && (sToken[2].contains("-") || sToken[3].equals("-"));
    }
}
