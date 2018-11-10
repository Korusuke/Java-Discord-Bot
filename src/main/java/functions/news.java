package functions;

import io.github.ccincharge.newsapi.datamodels.Article;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class news {

    public static void show_news(MessageReceivedEvent event, ArrayList <Article> newsArticle, Boolean flag){
        EmbedBuilder eb = new EmbedBuilder();
        if (!flag){
            if(newsArticle.size() == 0){
                event.getChannel().sendMessage("Sorry, currently no news-feed available for given query.").queue();
            }
            else{
                for (int i=0; i<10; i++) {
                    Article x = newsArticle.get(i);
                    eb = new EmbedBuilder();
                    try {
                        eb.setTitle(x.title(), x.url());
                        eb.setColor(Color.white);
                        eb.setDescription(x.description());
                        eb.setAuthor(x.author(), null, x.urlToImage());
                        eb.addBlankField(false);

                        // @kiteretsu plz figure out a way to parse given date format, i was unable to do so (ISO 8601 Date format, btw)

                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        DateFormat outputformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        Date date = null;
                        String output = null;
                        date= df.parse(x.publishedAt());
                        output = outputformat.format(date);


                        eb.setFooter("Published at " + output + ", by " + x.source().name(), null);
                        eb.setThumbnail(x.urlToImage());
                        event.getChannel().sendMessage(eb.build()).queue();
                    } catch (Exception e) {
                        System.out.println("Error :" + e.getMessage());
                    }
                }
                System.out.println("Done with given query.");
            }
        }
        else{
            if(newsArticle.size() == 0){
                event.getChannel().sendMessage("Sorry, currently no news-feed available for given query.").queue();
            }
            else{
                for (int i=0; i<5; i++) {
                    eb = new EmbedBuilder();
                    Article x = newsArticle.get(i);
                    try {
                        eb.setTitle(x.title(), x.url());
                        eb.setColor(Color.white);
                        eb.setDescription(x.description());
                        eb.setAuthor(x.author(), null, x.urlToImage());
                        eb.addBlankField(false);

                        // @kiteretsu plz figure out a way to parse given date format, i was unable to do so (ISO 8601 Date format, btw)
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        DateFormat outputformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        Date date = null;
                        String output = null;
                        date= df.parse(x.publishedAt());
                        output = outputformat.format(date);

                        eb.setFooter("Published at " + output + ", by " + x.source().name(), null);
                        eb.setThumbnail(x.urlToImage());
                        event.getChannel().sendMessage(eb.build()).queue();
                    } catch (Exception e) {
                        System.out.println("Error :" + e.getMessage());
                    }
                }
                System.out.println("Done with given query.");
            }
        }
    }
}
