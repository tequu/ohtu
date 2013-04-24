package statistics.matcher;

public class QueryBuilder {

    private Matcher matcher;

    public QueryBuilder() {
    }

    private QueryBuilder(Matcher matcher) {
        this.matcher = matcher;
    }

    public QueryBuilder playsIn(String j) {
        if (matcher == null) {
            return new QueryBuilder(new PlaysIn(j));
        }
        return new QueryBuilder(new And(matcher, new PlaysIn(j)));
    }

    public QueryBuilder hasAtLeast(int arvo, String m) {
        if (matcher == null) {
            return new QueryBuilder(new HasAtLeast(arvo, m));
        }
        return new QueryBuilder(new And(matcher, new HasAtLeast(arvo, m)));
    }

    public QueryBuilder hasFewerThan(int arvo, String m) {
        if (matcher == null) {
            return new QueryBuilder(new HasFewerThan(arvo, m));
        }
        return new QueryBuilder(new And(matcher, new HasFewerThan(arvo, m)));
    }

    public QueryBuilder not() {
        return new QueryBuilder(new Not(matcher));
    }

    public QueryBuilder oneOf(Matcher m1, Matcher m2) {
        return new QueryBuilder(new Or(m1, m2));
    }

    public Matcher build() {
        return matcher;
    }
}
