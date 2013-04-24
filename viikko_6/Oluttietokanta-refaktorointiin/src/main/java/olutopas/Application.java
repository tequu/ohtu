package olutopas;

import IO.IO;
import olutopas.toiminnot.ToimintoTehdas;
import java.util.Scanner;
import olutopas.tietokanta.Datamapper;

public class Application {

    private IO io;
    private Datamapper dm;
    private ToimintoTehdas toiminnot;

    public Application(Datamapper dm, IO io) {
        this.dm = dm;
        this.io = io;
        toiminnot = new ToimintoTehdas(io, dm);
    }

    public void run() {

        toiminnot.hae("login").suorita();

        io.tulosta("\nWelcome to Ratebeer " + dm.getKayttaja().getName());

        while (true) {
            menu();
            io.tulosta("> ");
            String command = io.lueRivi();
            toiminnot.hae(command).suorita();
            io.tulosta("\npress enter to continue");
            io.lueRivi();
        }

    }

    private void menu() {
        io.tulosta("");
        io.tulosta("1   find brewery");
        io.tulosta("2   find/rate beer");
        io.tulosta("3   add beer");
        io.tulosta("4   list breweries");
        //
        io.tulosta("5   list beers");
        io.tulosta("6   add brewery");
        //

        //
        io.tulosta("7   show my ratings");
        io.tulosta("8   list users");
        //
        io.tulosta("q   quit");
        io.tulosta("");
    }
}
