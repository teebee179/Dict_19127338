import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

/**
 * PACKAGE_NAME
 * Created by 19127338_Nguyễn Huỳnh Thế Bảo
 * Date 11/12/2021 - 7:35 CH
 * Description: ...
 */

public class Main {
    public static void main(String[] args){
        HashMap<String,String> dictionary = new HashMap<String, String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("slang.txt"));
            String line = br.readLine();
            while (line != null) {
                String[] word = line.split("`");
                System.out.println(word[0] + "," + word[1]);
                dictionary.put(word[0],word[1]);
                line = br.readLine();
            }
            br.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(dictionary.size());
    }
}

