package pl.edu.agh.ki.bd2;

public class Solution {

    private final GraphDatabase graphDatabase = GraphDatabase.createDatabase();

    public void databaseStatistics() {
        System.out.println(graphDatabase.runCypher("CALL db.labels()"));
        System.out.println(graphDatabase.runCypher("CALL db.relationshipTypes()"));
    }

    public void runAllTests() {
        System.out.println(findActorByName("Emma Watson"));
        System.out.println(findMovieByTitleLike("Star Wars"));
        System.out.println(findRatedMoviesForUser("maheshksp"));
        System.out.println(findCommonMoviesForActors("Emma Watson", "Daniel Radcliffe"));
        System.out.println(findMovieRecommendationForUser("emileifrem"));
    }

    private String findActorByName(final String actorName) {
        return graphDatabase.runCypher(String.format(
                "MATCH (person:Person {name: \"%s\"}) RETURN person", actorName));
    }

    private String findMovieByTitleLike(final String movieName) {
        return graphDatabase.runCypher(String.format("MATCH (movie:Movie) WHERE movie.title CONTAINS \"%s\" RETURN " +
                        "movie",
                movieName));
    }

    private String findRatedMoviesForUser(final String userLogin) {
        graphDatabase.runCypher(String.format("MATCH (user:User{login:\"%s\"})-[RATED]->(movie:Movie) RETURN movie"
                , userLogin));
        return null;
    }

    private String findCommonMoviesForActors(String actorOne, String actrorTwo) {
        return graphDatabase.runCypher(String.format("MATCH (a:Actor{name: \"%s\"})-[:ACTS_IN]->(m:Movie)" +
                "<-[:ACTS_IN]-(b:Actor{name: \"%s\"}) RETURN m", actorOne, actrorTwo));
    }

    private String findMovieRecommendationForUser(final String userLogin) {
        return graphDatabase.runCypher(String.format("MATCH (user:User{login:\"%s\"})<-[FRIEND]-(a:User)-[RATED]->" +
                "(movie:Movie) RETURN movie", userLogin));
    }
}
