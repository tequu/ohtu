package IO;

import java.util.Scanner;

public class KomentoriviIO implements IO{

    private Scanner lukija = new Scanner(System.in);
    @Override
    public String lueRivi() {
        return lukija.nextLine();
    }

    @Override
    public void tulosta(String m) {
        System.out.println(m);
    }
    
}
