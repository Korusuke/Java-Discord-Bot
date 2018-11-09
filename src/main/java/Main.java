import functions.news;
import io.github.ccincharge.newsapi.NewsApi;
import io.github.ccincharge.newsapi.datamodels.Article;
import io.github.ccincharge.newsapi.requests.RequestBuilder;
import io.github.ccincharge.newsapi.responses.ApiArticlesResponse;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.EmbedBuilder;
import javax.security.auth.login.LoginException;
import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import functions.reddit;
import functions.menu;

public class Main extends ListenerAdapter {

    NewsApi newsApi;
    ApiArticlesResponse apiArt;
    ArrayList<Article> newsArticle;
    EmbedBuilder eb;
    RequestBuilder query_request;

    public static void main(String[] args) throws LoginException {
        JDABuilder builder = new JDABuilder(AccountType.BOT);

        String token = "NTAyMTY1Mzc0MDMzMzMwMTc2.Dqj91Q.gOJ79EubCj8UI2StJFzvJEH1m8w"; // Hide it later, Discord Bot token
        builder.setToken(token);
        builder.addEventListener(new Main());
        builder.buildAsync();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        newsApi = new NewsApi("12ecefd220214b38902f8a9d7dc0beff"); // Hide it later, newsApi api key

        String param[] = event.getMessage().getContentRaw().split(" ", 3);
        if(param[0].toLowerCase().equals("!news") && param.length == 1){
            event.getChannel().sendMessage("Please, type command as follows: ```!news <category> <keyword>``` or ```!news latest```\nFor more info, type ```!menu```").queue();
        }
        else if(param[0].equals("!news") && param[1].toLowerCase().equals("latest")){
            System.out.println("Parameter passed: " + param[1].toLowerCase());
            RequestBuilder query_request = new RequestBuilder().setCountry("in").setLanguage("en");

            try {
                apiArt = newsApi.sendTopRequest(query_request);
            } catch (Exception e) {
                System.out.println("Error :" + e.getMessage());
            }

            System.out.println("API Response: " + apiArt.articles());
            newsArticle = apiArt.articles();
            System.out.println("No. of articles: " + newsArticle.size());
            news.show_news(event, newsArticle, false); // false flag implies latest news will return 20 results
        }
        else if (param[0].equals("!news") && param.length == 3) {

            System.out.println("Category passed: " + param[1].toLowerCase() + "\nKeyword passed: " + param[2].toLowerCase());
            try{
                query_request = new RequestBuilder().setCategory(param[1].toLowerCase()).setQ(param[2].toLowerCase()).setLanguage("en");
            } catch (Exception e) {
                System.out.println("Error :" + e.getMessage());
                event.getChannel().sendMessage("Please, make the following changes.\n" + e.getMessage()).queue();
            }
            try {
                apiArt = newsApi.sendEverythingRequest(query_request);
            } catch (Exception e) {
                System.out.println("Error :" + e.getMessage());
            }
            System.out.println("API Response: " + apiArt.articles());
            newsArticle = apiArt.articles();
            System.out.println("No. of articles: " + newsArticle.size());
            news.show_news(event, newsArticle, true); // true flag implies it will return as of now only 5 results
        }
        else if (param[0].equals("!news") && param.length == 2){
            event.getChannel().sendMessage("Please, type command as follows: ```!news <category> <keyword>``` or ```!news latest```\nFor more info, type ```!menu```").queue();
        }

        System.out.println("We received a message from " + event.getAuthor().getName() + ": " + event.getMessage().getContentDisplay());

        String rmsg = event.getMessage().getContentRaw();
        rmsg = rmsg.toLowerCase();
        System.out.println(rmsg);

        if (rmsg.equals("!menu")) {
            menu.showMenu(event);
        }

        if (rmsg.equals("!ping")) {
            event.getChannel().sendMessage("Pong!").queue();
        }

        if (rmsg.equals("!meme")) {
            reddit.ph(event);
        }
    }
}
