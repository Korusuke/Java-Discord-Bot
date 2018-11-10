import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.cloud.FirestoreClient;
import database.log;
import functions.menu;
import functions.movies;
import functions.news;
import functions.reddit;
import functions.tv;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbReviews;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.TvResultsPage;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.tv.TvSeries;
import io.github.ccincharge.newsapi.NewsApi;
import io.github.ccincharge.newsapi.datamodels.Article;
import io.github.ccincharge.newsapi.requests.RequestBuilder;
import io.github.ccincharge.newsapi.responses.ApiArticlesResponse;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;


public class Main extends ListenerAdapter {

    NewsApi newsApi;
    ApiArticlesResponse apiArt;
    ArrayList<Article> newsArticle;
    EmbedBuilder eb;
    RequestBuilder query_request;
    List<MovieDb> res;
    List<TvSeries> ser;
    Boolean flag = false;
    static
    Firestore db;


    public static void main(String[] args) throws Exception {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = "NTA4MDAxMzM3MzcyNDQyNjQ0.DseBjQ.bKNcS4BX53JF66iu3Mw995sUaZg"; // TODO: Hide it later (Discord Bot Token)
        builder.setToken(token);
        builder.addEventListener(new Main());
        builder.buildAsync();

        try{
            InputStream serviceAccount = new FileInputStream("oopm-ea9b5-firebase-adminsdk-xtizv-0d013dadab.json");// TODO: Firebase Token Path
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);
            db = FirestoreClient.getFirestore();

        }catch(Exception e){
            System.out.println("Error connecting to Firebase:" + e);
        }

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        //Important things first :p Log Everything (LOL)
        flag = false;
        System.out.println("We received a message from " + event.getAuthor().getName() + ": " + event.getMessage().getContentDisplay());

        log.logMsg(db,event);


        //End of data collection


        newsApi = new NewsApi("d0c33432dc1a4d798b5ec9764f04ef29"); // TODO: Hide it later (newsApi)

        String param[] = event.getMessage().getContentRaw().split(" ", 3);

        //News Stuff - Checking for valid query
        if(param[0].toLowerCase().equals("!news") && param.length == 1){
            flag = true;
            event.getChannel().sendMessage("Please, type command as follows: ```!news <category> <keyword>``` or ```!news latest```\nFor more info, type ```!menu```").queue();
        }
        else if(param[0].equals("!news") && param[1].toLowerCase().equals("latest")){
            flag = true;
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
            flag = true;
            System.out.println("Category passed: " + param[1].toLowerCase() + "\nKeyword passed: " + param[2].toLowerCase());
            try{
                query_request = new RequestBuilder().setCategory(param[1].toLowerCase()).setQ(param[2].toLowerCase()).setLanguage("en").setSortBy("relevancy");
            } catch (Exception e) {
                System.out.println("Error :" + e.getMessage());
                event.getChannel().sendMessage("Please, make the following changes.\n" + e.getMessage()).queue();
            }
            try {
                apiArt = newsApi.sendEverythingRequest(query_request);
                System.out.println("API Response: " + apiArt.articles());
                newsArticle = apiArt.articles();
                System.out.println("No. of articles: " + newsArticle.size());
                news.show_news(event, newsArticle, true); // true flag implies it will return as of now only 5 results
            } catch (Exception e) {
                System.out.println("Error :" + e.getMessage());
            }
        }
        else if (param[0].equals("!news") && param.length == 2){
            flag = true;
            event.getChannel().sendMessage("Please, type command as follows: ```!news <category> <keyword>``` or ```!news latest```\nFor more info, type ```!menu```").queue();
        }

        //End of News-Stuff


        String rmsg = event.getMessage().getContentRaw();
        rmsg = rmsg.toLowerCase();
        System.out.println(rmsg);

        if (rmsg.equals("!menu")) {
            flag = true;
            menu.showMenu(event);
        }

        if (rmsg.equals("!ping")) {
            flag = true;
            event.getChannel().sendMessage("Pong!").queue();
        }

        if (rmsg.equals("!meme")) {
            flag = true;
            try {
                reddit.ph(event);
            }catch(Exception e){
                System.out.println("Nope, " + e.getMessage());
            }

        }

        // Beginning of movie stuff
        final String TMDb_API = "925b0bf55e6719518fb07ebed6932308"; // TODO: Hide it later (TMDb api key)
        TmdbSearch tmdbSearch = new TmdbApi(TMDb_API).getSearch();  // TmDB API key

        String query_set[] = rmsg.split(" ", 2);
        if (query_set[0].equals("!movie") && query_set.length == 1){
            flag = true;
            event.getChannel().sendMessage("Please, type command as follows: ```!movie <query>```").queue();
        }
        else if(query_set[0].equals("!movie") && query_set.length == 2){
            flag = true;
            try {
                MovieResultsPage ans = tmdbSearch.searchMovie(query_set[1], 0, "en-US", false, 1);
                res = ans.getResults();
                System.out.println("API Response: " + res);
                if(res.size() == 0){
                    event.getChannel().sendMessage("Sorry, currently no movie is available for given query.").queue();
                }
                else {
                    movies.show_movies(event, res);
                }
            }
            catch(Exception e){
                System.out.println("Error: " + e.getMessage());
            }
        }
        else if(query_set[0].equals("!tvshow") && query_set.length == 1){ // Beginning of TV shows
            flag = true;
            event.getChannel().sendMessage("Please, type command as follows: ```!tvshow <query>```").queue();
        }
        else if(query_set[0].equals("!tvshow") && query_set.length == 2){
            flag = true;
            try{
                TvResultsPage tv_result = tmdbSearch.searchTv(query_set[1], "en-US", 1);
                ser = tv_result.getResults();
                System.out.println("API Response(TV): " + res);
                if(ser.size() == 0){
                    event.getChannel().sendMessage("Sorry, currently no tv show is available for given query.").queue();
                }
                else {
                    tv.show_tv(event, ser);
                }

            } catch(Exception e){
                System.out.println("Error: " + e.getMessage());
            }
        }


        else if(rmsg.equals("!rank")){ // For displaying server ranks
            flag = true;
            User user = event.getAuthor();
            eb = new EmbedBuilder();
            eb.setTitle("Guild: LosBlancos");
            eb.setAuthor(user.getName());

            DocumentReference docRef = db.collection("Korusuke-Bot").document(event.getGuild().getName());
            docRef = docRef.collection("UserPts").document(event.getAuthor().getName());
            // asynchronously retrieve the document
            ApiFuture<DocumentSnapshot> future = docRef.get();
            // ...
            // future.get() blocks on response
            log.update_ranks(db, event);
            try{
                DocumentSnapshot document = future.get();
                if (document.exists()) {
                    System.out.println("Document data: " + document.getData());
                } else {
                    System.out.println("No such document!");
                }
                int points = Math.toIntExact(document.getLong("Points"));
                eb.addField("User Points: ", String.valueOf(points), false);
                int ranks = Math.toIntExact(document.getLong("Rank"));
                eb.addField("Server Rank: ", String.valueOf(ranks), false);
            }
            catch(Exception e){
                System.out.println("Error: " + e.getMessage());
            }
            eb.setImage(user.getAvatarUrl());
            System.out.println("Avatar URL: "+user.getAvatarUrl());
            eb.setFooter("User ID: " + user.getId(), null);
            event.getChannel().sendMessage(eb.build()).queue();
        }
        else if (!event.getAuthor().isBot() && event.getMessage().getContentRaw().charAt(0) == '!' && !flag){
            event.getChannel().sendMessage("Sorry, what did you mean?\nFor more info, type: ```!menu```").queue();
        }

        if(!event.getAuthor().isBot()){ // Points calculation late here, coz it adds to bot reply lag, ik it should be above rank updation but same reason or it will update only when !rank is typed, which is not what we want
            log.updatepts(db,event);
        }
    }
}
