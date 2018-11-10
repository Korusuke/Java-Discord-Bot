package functions;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbReviews;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.Reviews;

import info.movito.themoviedbapi.model.core.MovieResultsPage;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class movies {
    private static TmdbApi tmdbApi = new TmdbApi("925b0bf55e6719518fb07ebed6932308"); // TODO: Hide it later (TMDb api key)

    public static void show_movies(MessageReceivedEvent event){

        EmbedBuilder eb;
        TmdbSearch tmdbSearch = tmdbApi.getSearch();

        String query_set[] = event.getMessage().getContentRaw().split(" ", 2);
        MovieResultsPage movieResultsPage = tmdbSearch.searchMovie(query_set[1], 0, "en-US", false, 1);
        List<MovieDb> movieResults = movieResultsPage.getResults();
        System.out.println("API Response(Movies): " + movieResults);

        if(movieResults.size() == 0){
            event.getChannel().sendMessage("Sorry, currently no movie is available for given query.").queue();
        }

        else {

            TmdbReviews tmdbReviews = tmdbApi.getReviews();
            eb = new EmbedBuilder();

            for(int i=0; i<3; i++){ // 3 movie results
                MovieDb x = movieResults.get(i);

                eb.setImage("https://image.tmdb.org/t/p/w185" + x.getPosterPath());
                eb.setTitle(x.getTitle());
                eb.setColor(Color.white);
                eb.addField("IMDb rating: ", Float.toString(x.getVoteAverage()), false);
                eb.setDescription(x.getOverview());

                try{
                    TmdbReviews.ReviewResultsPage movieReviews = tmdbReviews.getReviews(x.getId(), "en-US", 1);
                    List <Reviews> reviews = movieReviews.getResults();

                    System.out.println("Reviews: " + reviews);

                    if(reviews.size()!=0) {
                        eb.addField("Top Reviews:", "Down here.", false);
                    }
                    else{
                        eb.addField("Top Reviews:", "Currently, not available.", false);
                    }

                    for(int j=0; j<3; j++){ // 3 movie reviews
                        Reviews temp = reviews.get(j);

                        if(temp.getContent().length() > 249) {
                            eb.addField(temp.getAuthor(), temp.getContent().substring(0, 245) + "...", false);
                        }
                        else{
                            eb.addField(temp.getAuthor(), temp.getContent() + "...", false);
                        }

                        eb.addField("Read the full review here: ", temp.getUrl(), false);
                        eb.setFooter("Released on " + x.getReleaseDate(), null);
                    }

                }
                catch (Exception e){
                    System.out.println("Error: " + e.getMessage());
                }

                event.getChannel().sendMessage(eb.build()).queue();
            }
        }
    }
}
