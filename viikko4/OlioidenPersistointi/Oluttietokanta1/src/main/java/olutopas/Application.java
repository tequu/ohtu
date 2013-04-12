package olutopas;

import com.avaje.ebean.EbeanServer;
import java.util.List;
import java.util.Scanner;
import javax.persistence.OptimisticLockException;
import olutopas.model.Beer;
import olutopas.model.Brewery;
import olutopas.model.Pub;
import olutopas.model.Rating;
import olutopas.model.User;

public class Application {

    private EbeanServer server;
    private Scanner scanner = new Scanner(System.in);
    private User user = null;

    public Application(EbeanServer server) {
        this.server = server;
    }

    public void run(boolean newDatabase) {
        if (newDatabase) {
            seedDatabase();
        }
        logIn();
        System.out.println("Welcome to Ratebeer "+user.getName());

        while (true) {
            menu();
            System.out.print("> ");
            String command = scanner.nextLine();

            if (command.equals("0")) {
                break;
            } else if (command.equals("1")) {
                findBrewery();
            } else if (command.equals("2")) {
                findBeer();
            } else if (command.equals("3")) {
                addBeer();
            } else if (command.equals("4")) {
                listBreweries();
            } else if (command.equals("5")) {
                deleteBeer();
            } else if (command.equals("6")) {
                listBeers();
            } else if (command.equals("7")) {
                addBrewery();
            } else if (command.equals("8")) {
                deleteBrewery();
            } else if (command.equals("t")) {
                myRatings();
            } else if (command.equals("a")) {
                addBeerToPub();
            } else if (command.equals("s")) {
                showBeersInPub();
            } else if (command.equals("l")) {
                listPubs();
            } else if (command.equals("r")) {
                removeBeerFromPub();
            } else if (command.equals("e")) {
                addPub();
            } else {
                System.out.println("unknown command");
            }

            System.out.print("\npress enter to continue");
            scanner.nextLine();
        }

        System.out.println("bye");
    }

    private void menu() {
        System.out.println("");
        System.out.println("1   find brewery");
        System.out.println("2   find/rate beer");
        System.out.println("3   add beer");
        System.out.println("4   list breweries");
        System.out.println("5   delete beer");
        System.out.println("6   list beers");
        System.out.println("7   add brewery");
        System.out.println("8   delete brewery");
        System.out.println("t   show my ratings");
        System.out.println("a   add beer to pub");
        System.out.println("s   show beer in pub");
        System.out.println("l   list pubs");
        System.out.println("r   remove beer from pub");
        System.out.println("e   add pub");
        System.out.println("0   quit");
        System.out.println("");
    }

    // jos kanta on luotu uudelleen, suoritetaan tämä ja laitetaan kantaan hiukan dataa
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
    }

    private void findBeer() {
        System.out.print("beer to find: ");
        String n = scanner.nextLine();
        Beer foundBeer = server.find(Beer.class).where().like("name", n).findUnique();

        if (foundBeer == null) {
            System.out.println(n + " not found");
            return;
        }

        System.out.println(foundBeer);
        System.out.println("number of ratings: "+foundBeer.getRatings().size()+" "+(foundBeer.getAverage() != null?"average "+foundBeer.getAverage():" no average"));
        addRating(foundBeer);
    }

    private void findBrewery() {
        System.out.print("brewery to find: ");
        String n = scanner.nextLine();
        Brewery foundBrewery = server.find(Brewery.class).where().like("name", n).findUnique();

        if (foundBrewery == null) {
            System.out.println(n + " not found");
            return;
        }

        System.out.println(foundBrewery);
        for (Beer bier : foundBrewery.getBeers()) {
            System.out.println("   " + bier.getName());
        }
    }

    private void listBreweries() {
        List<Brewery> breweries = server.find(Brewery.class).findList();
        for (Brewery brewery : breweries) {
            System.out.println(brewery);
        }
    }

    private void addBeer() {
        System.out.print("to which brewery: ");
        String name = scanner.nextLine();
        Brewery brewery = server.find(Brewery.class).where().like("name", name).findUnique();

        if (brewery == null) {
            System.out.println(name + " does not exist");
            return;
        }

        System.out.print("beer to add: ");

        name = scanner.nextLine();

        Beer exists = server.find(Beer.class).where().like("name", name).findUnique();
        if (exists != null) {
            System.out.println(name + " exists already");
            return;
        }

        brewery.addBeer(new Beer(name));
        server.save(brewery);
        System.out.println(name + " added to " + brewery.getName());
    }

    private void deleteBeer() {
        System.out.print("beer to delete: ");
        String n = scanner.nextLine();
        Beer beerToDelete = server.find(Beer.class).where().like("name", n).findUnique();

        if (beerToDelete == null) {
            System.out.println(n + " not found");
            return;
        }

        server.delete(beerToDelete);
        System.out.println("deleted: " + beerToDelete);

    }

    private void listBeers() {
        System.out.println("Beers:");
        for (Beer beer : server.find(Beer.class).orderBy("brewery").findList()) {
            System.out.println(beer);
            System.out.println("number of ratings: "+beer.getRatings().size()+" "+(beer.getAverage() != null?"average "+beer.getAverage():" no average"));
        }
    }

    private void addBrewery() {
        System.out.print("Brewery to add: ");

        String name = scanner.nextLine();

        Brewery exists = server.find(Brewery.class).where().like("name", name).findUnique();
        if (exists != null) {
            System.out.println(name + " exists already");
            return;
        }
        Brewery uusi = new Brewery();
        uusi.setName(name);
        server.save(uusi);
        System.out.println("Added " + uusi.getName());
    }

    private void deleteBrewery() {
        System.out.print("Brewery to delete: ");
        String n = scanner.nextLine();
        Brewery breweryToDelete = server.find(Brewery.class).where().like("name", n).findUnique();

        if (breweryToDelete == null) {
            System.out.println(n + " not found");
            return;
        }

        server.delete(breweryToDelete);
        System.out.println("deleted: " + breweryToDelete);
    }

    private void newUser() {
        while (true) {
            System.out.println("Register a new user");
            System.out.print("give username: ");
            String n = scanner.nextLine();
            User isUsed = server.find(User.class).where().like("name", n).findUnique();
            if (isUsed != null) {
                System.out.println("Username is used\n");
                continue;
            }
            User user = new User();
            user.setName(n);
            server.save(user);
            System.out.println("user created!\n");
            break;
        }
    }

    private void logIn() {
        while (user == null) {
            System.out.println("Login(give ? to register a new user)");
            System.out.print("Username: ");
            String n = scanner.nextLine();
            if (n.equals("?")) {
                System.out.println("");
                newUser();
                continue;
            }

            User user = server.find(User.class).where().like("name", n).findUnique();
            if (user != null) {
                this.user = user;
            } else {
                System.out.println("Not found");
            }
            System.out.println("");
        }
    }
    
    private void addRating(Beer beer){
        System.out.print("give rating (leave empty if not): ");
        String n = scanner.nextLine();
        if(n.trim().isEmpty()){
            return;
        }
        Rating rating = new Rating();
        rating.setBeer(beer);
        rating.setUser(user);
        rating.setValue(Integer.parseInt(n));
        server.save(rating);
        updateBeerRatingAverage(beer);
        user = server.find(User.class).where().like("name", user.getName()).findUnique();
    }
    
    private void updateBeerRatingAverage(Beer beer){
        beer = server.find(Beer.class).where().like("name", beer.getName()).findUnique();
        double average = 0;
        for (Rating rating : beer.getRatings()) {
            average += rating.getValue();
        }
        average /= beer.getRatings().size();
        beer.setAverage(average);
        server.save(beer);
    }
    
    private void myRatings(){
        System.out.println("Ratings by "+user.getName());
        for (Rating rating : user.getRatings()) {
            System.out.println(rating);
        }
        if(user.getRatings().isEmpty()){
            System.out.println("No ratings");
        }
        System.out.println("");
    }
    private void addBeerToPub() {
        System.out.print("beer: ");
        String name = scanner.nextLine();
        Beer beer = server.find(Beer.class).where().like("name", name).findUnique();

        if (beer == null) {
            System.out.println("does not exist");
            return;
        }

        System.out.print("pub: ");
        name = scanner.nextLine();
        Pub pub = server.find(Pub.class).where().like("name", name).findUnique();

        if (pub == null) {
            System.out.println("does not exist");
            return;
        }

        pub.addBeer(beer);
        server.save(pub);
    }

    private void showBeersInPub() {
        System.out.print("Pub to list: ");
        String n = scanner.nextLine();
        Pub pub = server.find(Pub.class).where().like("name", n).findUnique();

        if (pub == null) {
            System.out.println(n + " not found");
            return;
        }

        System.out.println(n + " pub's beers:");
        for (Beer beer : pub.getBeers()) {
            System.out.println("\t" + beer);
        }
    }

    private void listPubs() {
        List<Pub> pubs = server.find(Pub.class).findList();
        System.out.println("Pubs: ");
        for (Pub pub : pubs) {
            System.out.println(pub);
        }
    }
    
    private void removeBeerFromPub(){
        System.out.print("Pub from remove: ");
        String n = scanner.nextLine();
        Pub pub = server.find(Pub.class).where().like("name", n).findUnique();

        if (pub == null) {
            System.out.println(n + " not found");
            return;
        }
        
        System.out.print("Beer to remove: ");
        n = scanner.nextLine();
        Beer beer = server.find(Beer.class).where().like("name", n).findUnique();

        if (beer == null) {
            System.out.println(n + " not found");
            return;
        }
        
        pub.removeBeer(beer);
        server.save(pub);
    }
    private void addPub() {
        System.out.print("pub to add: ");

        String name = scanner.nextLine();

        Pub exists = server.find(Pub.class).where().like("name", name).findUnique();
        if (exists != null) {
            System.out.println(name + " exists already");
            return;
        }

        server.save(new Pub(name));
    }
}
