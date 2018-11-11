import com.google.firebase.cloud.FirestoreClient;

import functions.*;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.FileInputStream;
import java.io.InputStream;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import database.log;
import webpage.index;

public class Main extends ListenerAdapter {

    private static Firestore db;



    public static void main(String[] args) throws Exception {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = ""; // TODO: (Discord Bot Token)
        builder.setToken(token);
        builder.addEventListener(new Main());
        builder.buildAsync();
        new index();
        try{ // Firebase connectivity
            InputStream serviceAccount = new FileInputStream(""); //TODO: (Firebase Auth Filepath)
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);
            db = FirestoreClient.getFirestore();
        } catch (Exception e){
            System.out.println("Error connecting to Firebase: " + e.getMessage());
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Boolean flag = false; // For removing that annoying sorry bug

        // Data logging
        log.logMsg(db, event);
        //End of data collection


        System.out.println("We received a message from " + event.getAuthor().getName() + ": " + event.getMessage().getContentDisplay());


        // Beginning of news stuffs
        String parameters[] = event.getMessage().getContentRaw().split(" ", 3);

        if(parameters[0].toLowerCase().equals("!news") && parameters.length == 1){ //News Stuff - Checking for valid query
            flag = true;
            event.getChannel().sendMessage("Please, type command as follows: ```!news <category> <keyword>``` or ```!news latest```\nFor more info, type ```!menu```").queue();
        }

        else if(parameters[0].equals("!news") && parameters[1].toLowerCase().equals("latest")){
            flag = true;
            System.out.println("Parameter passed: " + parameters[1].toLowerCase());
            news.show_latest_news(event); // false flag implies latest news will return 10 results
        }

        else if (parameters[0].equals("!news") && parameters.length == 3) {
            flag = true;
            System.out.println("Category passed: " + parameters[1].toLowerCase() + "\nKeyword passed: " + parameters[2].toLowerCase());
            news.show_news(event);
        }

        else if (parameters[0].equals("!news") && parameters.length == 2){
            flag = true;
            event.getChannel().sendMessage("Please, type command as follows: ```!news <category> <keyword>``` or ```!news latest```\nFor more info, type ```!menu```").queue();
        }
        //End of News-Stuff


        // Other commands
        String rmsg = event.getMessage().getContentRaw().toLowerCase();
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
            }
            catch(Exception e){
                System.out.println("Error: " + e.getMessage());
            }
        }


        // Beginning of movie stuffs

        String query_set[] = rmsg.split(" ", 2);
        if (query_set[0].equals("!movie") && query_set.length == 1){
            flag = true;
            event.getChannel().sendMessage("Please, type command as follows: ```!movie <query>```").queue();
        }

        else if(query_set[0].equals("!movie") && query_set.length == 2){
            flag = true;
            movies.show_movies(event);
        }
        // End of movie stuffs


        // Beginning of Tv shows
        else if(query_set[0].equals("!tvshow") && query_set.length == 1){ // Beginning of TV shows
            flag = true;
            event.getChannel().sendMessage("Please, type command as follows: ```!tvshow <query>```").queue();
        }

        else if(query_set[0].equals("!tvshow") && query_set.length == 2){
            flag = true;
            try{
                tv.show_tv(event);
            } catch(Exception e){
                System.out.println("Error: " + e.getMessage());
            }
        }
        // End of Tv shows


        else if(rmsg.equals("!rank")){ // For displaying server ranks
            flag = true;
            log.update_ranks(db, event);
            server.server_rank(db, event);
        }

        else if (!event.getAuthor().isBot() && event.getMessage().getContentRaw().charAt(0) == '!' && !flag){
            event.getChannel().sendMessage("Sorry, what did you mean?\nFor more info, type: ```!menu```").queue();
        }

        if(!event.getAuthor().isBot()){ // Points calculation late here, coz it adds to bot reply lag, ik it should be above rank updation but same reason or it will update only when !rank is typed, which is not what we want
            log.updatepts(db,event);
        }
    }
}
