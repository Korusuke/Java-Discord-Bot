package functions;

import io.github.ccincharge.newsapi.datamodels.Article;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;

public class news {

    public static void show_news(MessageReceivedEvent event, ArrayList <Article> newsArticle, Boolean flag){
        EmbedBuilder eb = new EmbedBuilder();
        if (!flag){
            if(newsArticle.size() == 0){
                event.getChannel().sendMessage("Sorry, currently no news-feed available for given query.").queue();
            }
            else{
                for (Article x : newsArticle) {
                    eb = new EmbedBuilder();
                    try {
                        eb.setTitle(x.title(), x.url());
                        eb.setColor(Color.white);
                        eb.setDescription(x.description());
                        eb.setAuthor(x.author(), null, x.urlToImage());

                        // @korusuke plz figure out a way to parse given date format, i was unable to do so (ISO 8601 Date format, btw)
                        eb.setFooter("Published at " + x.publishedAt() + ", by " + x.source().name(), null);
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

                        // @korusuke plz figure out a way to parse given date format, i was unable to do so (ISO 8601 Date format, btw)
                        eb.setFooter("Published at " + x.publishedAt() + ", by " + x.source().name(), null);
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
