package kw.mulitplay.game.auto;

import javax.print.attribute.standard.Fidelity;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class App {
    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream stream = new FileInputStream("book.dat");
        Position.loadBook(stream);
    }
}
