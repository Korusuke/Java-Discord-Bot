package functions;

import io.github.ccincharge.newsapi.NewsApi;
import io.github.ccincharge.newsapi.datamodels.Article;
import io.github.ccincharge.newsapi.requests.RequestBuilder;
import io.github.ccincharge.newsapi.responses.ApiArticlesResponse;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class news {

    private static NewsApi newsApi = new NewsApi(""); // TODO: (newsApi)
    private static RequestBuilder query_request;
    private static ApiArticlesResponse apiArt;

    private static void embed_sender(MessageReceivedEvent event, ArrayList<Article> newsArticle, int count){
        EmbedBuilder eb;

        if(newsArticle.size() == 0){
            event.getChannel().sendMessage("Sorry, currently no news-feed available for given query.").queue();
        }
        else{
            for (int i=0; i<count; i++) {
                Article x = newsArticle.get(i);
                eb = new EmbedBuilder();

                eb.setTitle(x.title(), x.url());
                eb.setColor(Color.white);
                eb.setDescription(x.description());
                eb.setAuthor(x.author(), null, x.urlToImage());
                eb.addBlankField(false);

                Date date = null;
                String output = null;
                try {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    DateFormat outputformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    date = df.parse(x.publishedAt());
                    output = outputformat.format(date);
                }catch(Exception e){
                    output = "In Mysterious Times";
                }
                eb.setFooter("Published at " + output + ", by " + x.source().name(), null);
                eb.setThumbnail(x.urlToImage());
                event.getChannel().sendMessage(eb.build()).queue();
            }
            System.out.println("Done with given query.");
        }
    }

    public static void show_latest_news(MessageReceivedEvent event){
        query_request = new RequestBuilder().setCountry("in").setLanguage("en").setSortBy("popularity"); // searching for latest indian news

        try {
            apiArt = newsApi.sendTopRequest(query_request);
            System.out.println("API Response: " + apiArt.articles()); // Logging into console
            ArrayList <Article> newsArticle = apiArt.articles();
            embed_sender(event, newsArticle, 5);
        }
        catch(Exception e) {
            System.out.println("Error :" + e.getMessage());
        }
    }

    public static void show_news(MessageReceivedEvent event){
        String parameters[] = event.getMessage().getContentRaw().split(" ", 3);

        try{
            query_request = new RequestBuilder().setCategory(parameters[1].toLowerCase()).setQ(parameters[2].toLowerCase()).setLanguage("en").setSortBy("relevancy"); // Search by category and keyword
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            event.getChannel().sendMessage("Please, make the following changes.\n" + e.getMessage()).queue();
        }
        try{
            apiArt = newsApi.sendEverythingRequest(query_request);
            System.out.println("API Response(News): " + apiArt.articles()); // Logging into console
            ArrayList <Article> newsArticle = apiArt.articles();
            embed_sender(event, newsArticle, 10);
        }
        catch (Exception e) {
            System.out.println("Error :" + e.getMessage());
        }
    }
}
