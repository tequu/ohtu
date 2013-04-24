package olutopas.toiminnot;

import IO.IO;

public class Unknow implements Toiminto {

    private IO io;

    public Unknow(IO io) {
        this.io = io;
    }

    @Override
    public void suorita() {
        io.tulosta("unknown command");
    }
}
