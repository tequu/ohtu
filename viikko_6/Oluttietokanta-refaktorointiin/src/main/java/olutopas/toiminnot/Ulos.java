package olutopas.toiminnot;

import IO.IO;
import olutopas.toiminnot.Toiminto;

public class Ulos implements Toiminto {

    private IO io;

    public Ulos(IO io) {
        this.io = io;
    }

    @Override
    public void suorita() {
        io.tulosta("bye");
        System.exit(0);
    }
}
