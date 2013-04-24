package olutopas;

import IO.KomentoriviIO;
import olutopas.model.Beer;
import olutopas.model.Brewery;

import olutopas.model.Rating;
import olutopas.model.User;
import olutopas.tietokanta.EbeanSqliteDatamapper;

public class Main {

    public static void main(String[] args) {
        new Application(new EbeanSqliteDatamapper("jdbc:sqlite:beer.db", true, Beer.class, Brewery.class, User.class, Rating.class), new KomentoriviIO()).run();
    }
}
