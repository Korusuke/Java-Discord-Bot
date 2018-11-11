package functions;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.TvResultsPage;
import info.movito.themoviedbapi.model.tv.TvSeries;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class tv {
    private static TmdbApi tmdbApi = new TmdbApi(""); // TODO: (TMDb api key)

    public static void show_tv(MessageReceivedEvent event){
        EmbedBuilder eb;
        TmdbSearch tmdbSearch = tmdbApi.getSearch();

        String query_set[] = event.getMessage().getContentRaw().split(" ", 2);
        try{
            TvResultsPage tv_result = tmdbSearch.searchTv(query_set[1], "en-US", 1);
            List<TvSeries> tvSeries = tv_result.getResults();

            System.out.println("API Response(TV): " + tvSeries);
            if(tvSeries.size() == 0){
                event.getChannel().sendMessage("Sorry, currently no tv show is available for given query.").queue();
            }
            else {
                for(int i=0; i<3; i++){
                    eb = new EmbedBuilder();
                    TvSeries x = tvSeries.get(i);

                    eb.setTitle(x.getName());
                    eb.setImage("https://image.tmdb.org/t/p/w185" + x.getPosterPath());
                    eb.setColor(Color.white);
                    eb.setDescription(x.getOverview());
                    eb.addField("Popularity: ", String.valueOf(x.getPopularity()), false);

                    if(x.getVoteAverage() > 0){
                        eb.addField("User ratings: ", Float.toString(x.getVoteAverage()), false);
                    }

                    eb.setFooter("First air date: " + x.getFirstAirDate(), null);
                    event.getChannel().sendMessage(eb.build()).queue();
                }
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
