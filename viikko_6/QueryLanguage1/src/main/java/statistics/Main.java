package statistics;

import statistics.matcher.*;

public class Main {

//    public static void main(String[] args) {
//        Statistics stats = new Statistics(new PlayerReaderImpl("http://nhlstatistics.herokuapp.com/players.txt"));
//
////        Matcher m = new And( new HasAtLeast(10, "goals"),
////                             new HasAtLeast(10, "assists"),
////                             new PlaysIn("PHI")
////        );
//
////        Matcher m = new Or(new HasFewerThan(10, "goals"),
////                new PlaysIn("PHI"));
//        
//        Matcher m = new Not(new Or(new PlaysIn("PHI"), new PlaysIn("PIT")));
//
//        for (Player player : stats.matches(m)) {
//            System.out.println(player);
//        }
//    }
    public static void main(String[] args) {
        Statistics stats = new Statistics(new PlayerReaderImpl("http://nhlstatistics.herokuapp.com/players.txt"));

        QueryBuilder query = new QueryBuilder();

//        Matcher m = query.playsIn("NYR")
//                .hasAtLeast(10, "goals")
//                .hasFewerThan(25, "assists").build();
        Matcher m = query.oneOf(
                        query.playsIn("PHI").not()
                             .hasAtLeast(10, "goals")
                             .hasFewerThan(15, "assists").build(),
 
                        query.playsIn("EDM")
                             .hasAtLeast(50, "points").build()
                       ).build();

        for (Player player : stats.matches(m)) {
            System.out.println(player);
        }
    }
}
