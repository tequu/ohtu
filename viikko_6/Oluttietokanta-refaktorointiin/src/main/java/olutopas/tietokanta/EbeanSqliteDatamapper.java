package olutopas.tietokanta;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.dbplatform.SQLitePlatform;
import java.util.List;
import javax.persistence.OptimisticLockException;
import olutopas.model.Beer;
import olutopas.model.Brewery;
import olutopas.model.Rating;
import olutopas.model.User;

public class EbeanSqliteDatamapper implements Datamapper {

    private Class[] luokat;
    private EbeanServer server;
    private String tietokantaUrl;
    private boolean dropAndCreateDatabase;
    private User kayttaja;

    @Override
    public User getKayttaja() {
        return kayttaja;
    }

    public void setKayttaja(User kayttaja) {
        this.kayttaja = kayttaja;
    }

    public EbeanSqliteDatamapper(String tietokantaUrl, boolean dropAndCreateDatabase, Class... luokat) {
        this.luokat = luokat;
        this.tietokantaUrl = tietokantaUrl;
        this.dropAndCreateDatabase = dropAndCreateDatabase;
        init();
        if(dropAndCreateDatabase){
            seedDatabase();
        }
    }

    public void init() {
        ServerConfig config = new ServerConfig();
        config.setName("beerDb");
        DataSourceConfig sqLite = new DataSourceConfig();
        // loput konfiguraatiosta
        sqLite.setDriver("org.sqlite.JDBC");
        sqLite.setUsername("mluukkai");
        sqLite.setPassword("mluukkai");
        sqLite.setUrl(tietokantaUrl);
        config.setDataSourceConfig(sqLite);
        config.setDatabasePlatform(new SQLitePlatform());
        config.getDataSourceConfig().setIsolationLevel(Transaction.READ_UNCOMMITTED);
        config.setDefaultServer(false);
        config.setRegister(false);
        // konstruktorin parametrina annettavat hallinnoitavat luokat lisätään seuraavasti
        for (Class luokka : luokat) {
            config.addClass(luokka);
        }

        if (dropAndCreateDatabase) {
            config.setDdlGenerate(true);
            config.setDdlRun(true);
        }
        server = EbeanServerFactory.create(config);
    }

    private void seedDatabase() throws OptimisticLockException {
        Brewery brewery = new Brewery("Schlenkerla");
        brewery.addBeer(new Beer("Urbock"));
        brewery.addBeer(new Beer("Lager"));
        // tallettaa myös luodut oluet, sillä Brewery:n OneToMany-mappingiin on määritelty
        // CascadeType.all
        server.save(brewery);

        // luodaan olut ilman panimon asettamista
        Beer b = new Beer("Märzen");
        server.save(b);

        // jotta saamme panimon asetettua, tulee olot lukea uudelleen kannasta
        b = server.find(Beer.class, b.getId());
        brewery = server.find(Brewery.class, brewery.getId());
        brewery.addBeer(b);
        server.save(brewery);

        server.save(new Brewery("Paulaner"));

        server.save(new User("mluukkai"));
    }

    @Override
    public Brewery haeBreweryNimella(String n) {
        return server.find(Brewery.class).where().like("name", n).findUnique();
    }

    public EbeanServer getServer() {
        return server;
    }

    @Override
    public void tallennBrewery(Brewery b) {
        server.save(b);
    }

    @Override
    public void tallennaBeer(Beer b) {
        server.save(b);
    }

    @Override
    public Beer haeBeer(Integer id) {
        return server.find(Beer.class, id);
    }

    @Override
    public void tallennaUser(User u) {
        server.save(u);
    }

    @Override
    public Brewery haeBrewery(Integer id) {
        return server.find(Brewery.class, id);
    }

    @Override
    public Beer haeBeerNimella(String n) {
        return server.find(Beer.class).where().like("name", n).findUnique();
    }

    @Override
    public List<Brewery> listaaBreweryt() {
        return server.find(Brewery.class).findList();
    }

    @Override
    public List<Beer> listaaBeerit() {
        return server.find(Beer.class).orderBy("brewery.name").findList();
    }

    @Override
    public User haeKayttajaNimella(String n) {
        return server.find(User.class).where().like("name", n).findUnique();
    }

    @Override
    public void tallennaRating(Rating r) {
        server.save(r);
    }

    @Override
    public List<User> listaaKayttajat() {
        return server.find(User.class).findList();
    }
}
