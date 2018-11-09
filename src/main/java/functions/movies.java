package functions;

import info.movito.themoviedbapi.model.MovieDb;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class movies {
    public static void show_movies(MessageReceivedEvent event, List<MovieDb> res){
        EmbedBuilder eb = new EmbedBuilder();

        for(int i=0; i<5; i++){
            MovieDb x = res.get(i);
            eb = new EmbedBuilder();
            eb.setImage("https://image.tmdb.org/t/p/w185" + x.getPosterPath());
            eb.setTitle(x.getTitle());
            eb.setColor(Color.white);
            System.out.println("IMDb rating: " + x.getVoteAverage());
            try{
                eb.addField("IMDb rating: ", Float.toString(x.getVoteAverage()), false);
            }catch (Exception e){
                System.out.println("Error: " + e.getMessage());
            }
            eb.setDescription(x.getOverview());
            eb.setFooter("Released on " + x.getReleaseDate(), null);
            event.getChannel().sendMessage(eb.build()).queue();
        }
    }
}
