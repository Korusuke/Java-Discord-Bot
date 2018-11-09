import functions.news;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
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
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import functions.reddit;
import functions.menu;
import functions.movies;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;



public class Main extends ListenerAdapter {

    NewsApi newsApi;
    ApiArticlesResponse apiArt;
    ArrayList<Article> newsArticle;
    EmbedBuilder eb;
    RequestBuilder query_request;
    List<MovieDb> res;

    public static void main(String[] args) throws Exception {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = "NTAyMTY1Mzc0MDMzMzMwMTc2.Dqj91Q.gOJ79EubCj8UI2StJFzvJEH1m8w"; // Hide it later, Discord Bot token
        builder.setToken(token);
        builder.addEventListener(new Main());
        builder.buildAsync();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        System.out.println("We received a message from " + event.getAuthor().getName() + ": " + event.getMessage().getContentDisplay());

        newsApi = new NewsApi("12ecefd220214b38902f8a9d7dc0beff"); // Hide it later, newsApi api key

        String param[] = event.getMessage().getContentRaw().split(" ", 3);

        //News Stuff - Checking for valid query
        if(param[0].toLowerCase().equals("!news") && param.length == 1){
            event.getChannel().sendMessage("Please, type command as follows: ```!news <category> <keyword>``` or ```!news latest```\nFor more info, type ```!menu```").queue();
        }
        else if(param[0].equals("!news") && param[1].toLowerCase().equals("latest")){
            System.out.println("Parameter passed: " + param[1].toLowerCase());
            RequestBuilder query_request = new RequestBuilder().setCountry("in").setLanguage("en").setSortBy("popularity");

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
                query_request = new RequestBuilder().setCategory(param[1].toLowerCase()).setQ(param[2].toLowerCase()).setLanguage("en").setSortBy("relevancy");
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

        //End of News-Stuff


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
            try {
                reddit.ph(event);
            }catch(Exception e){
                System.out.println("Nope"+e);
            }

        }

        TmdbSearch tmdbSearch = new TmdbApi("50bf7dc3c21dd3c8f31cf6ba460b5bcb").getSearch();  // TmDB API key

        String query_set[] = rmsg.split(" ", 2);
        if (query_set[0].equals("!movie") && query_set.length == 1){
            event.getChannel().sendMessage("Please, type command as follows: ```!movie <query>```").queue();
        }
        else if(query_set[0].equals("!movie") && query_set.length == 2){
            try {
                MovieResultsPage ans = tmdbSearch.searchMovie(query_set[1], 0, "en-US", false, 1);
                res = ans.getResults();
                System.out.println("API Response: " + res);
                if(res.size() == 0){
                    event.getChannel().sendMessage("Sorry, currently no movie available for given query.").queue();
                }
                else {
                    movies.show_movies(event, res);
                }
            }
            catch(Exception e){
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
