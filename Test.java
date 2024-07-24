import java.io.FileNotFoundException;
import java.io.IOException;

public class Test {

    public static void main(String[] args){
        try {
            Split.splitToFile();
        } catch (IOException e) {
            System.out.println("ERROR at splitToFile");
        }
        assert Split.createDB(Main.originFileName).getMasechet("שבת")!=null;
        assert Split.createDB(Main.originFileName).getMasechet("עירובין")!=null;
        assert Split.createDB(Main.originFileName).getMasechet("שבת").getListOfPages()!=null;
        assert Split.createDB(Main.originFileName).getMasechet("עירובין").getListOfPages()!=null;
        System.out.println();



    }


}
