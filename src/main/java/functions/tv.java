package functions;

import info.movito.themoviedbapi.model.tv.TvSeries;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class tv {
    public static void show_tv(MessageReceivedEvent event, List<TvSeries> res){
        EmbedBuilder eb = new EmbedBuilder();
        for(int i=0; i<3; i++){
            eb = new EmbedBuilder();
            TvSeries x = res.get(i);
            eb.setTitle(x.getName());
            eb.setImage("https://image.tmdb.org/t/p/w185" + x.getPosterPath());
            eb.setColor(Color.white);
            eb.setDescription(x.getOverview());
            eb.addField("Popularity: ", String.valueOf(x.getPopularity()), false);
            eb.addField("User ratings: ", Float.toString(x.getVoteAverage()), false);
            eb.setFooter("First air date: " + x.getFirstAirDate(), null);
            event.getChannel().sendMessage(eb.build()).queue();
        }
    }
}
