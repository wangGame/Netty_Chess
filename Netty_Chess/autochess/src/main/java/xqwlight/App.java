package xqwlight;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class App {
    public static void main(String[] args) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File("book.dat"));
            Position.loadBook(fileInputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
