import io.github.ccincharge.newsapi.NewsApi;
import io.github.ccincharge.newsapi.datamodels.Article;
import io.github.ccincharge.newsapi.requests.RequestBuilder;
import io.github.ccincharge.newsapi.responses.ApiArticlesResponse;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.awt.Color;
import java.util.ArrayList;

public class Main extends ListenerAdapter {

    NewsApi newsApi;
    ApiArticlesResponse apiArt;
    ArrayList <Article> newsArticle;

    public static void main(String[] args) throws LoginException {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = "NTA4MDAxMzM3MzcyNDQyNjQ0.Dr44-A.9qunUXKsAxEnnRLmT_V2TnqOGeA"; // Hide it later
        builder.setToken(token);
        builder.addEventListener(new Main());
        builder.buildAsync();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        newsApi = new NewsApi("12ecefd220214b38902f8a9d7dc0beff"); // Hide it later

        String param[] = event.getMessage().getContentRaw().split(" ", 2);
        if(param[0].equals("!news")){

            System.out.println("We received a message from " + event.getAuthor().getName() + ": " + event.getMessage().getContentDisplay());
            System.out.println("Parameter passed: " + param[1]);

            RequestBuilder query_request = new RequestBuilder().setQ(param[1]).setLanguage("en");
            try{
                apiArt = newsApi.sendTopRequest(query_request);
            }
            catch (Exception e){
                System.out.println("Error :" + e.getMessage());
            }
            System.out.println("API Response: " +  apiArt.articles());

            newsArticle = apiArt.articles();
            for(Article x: newsArticle) {
                EmbedBuilder eb = new EmbedBuilder();

                try{
                    eb.setTitle(x.title(), x.url());
                    eb.setColor(Color.white);
                    eb.setDescription(x.description());
                    eb.setAuthor(x.author(), null, x.urlToImage());
                    eb.setFooter("Published at " + x.publishedAt(), null);
                    eb.setThumbnail(x.urlToImage());
                    event.getChannel().sendMessage(eb.build()).queue();
                }
                catch(Exception e){
                    System.out.println("Error :" + e.getMessage());
                }
            }
            System.out.println("Done with given query.");
        }
    }
}

