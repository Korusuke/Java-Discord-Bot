package functions;

import net.dean.jraw.RedditClient;
import net.dean.jraw.http.NetworkAdapter;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.models.Subreddit;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONObject;


public class reddit {
    public static void ph(MessageReceivedEvent event) throws Exception{


        String url = "https://www.reddit.com/r/ProgrammerHumor/top.json?limit=50&sort=top";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);


        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //print in String
        System.out.println(response.toString());

        //Read JSON response and print
        JSONObject myResponse = new JSONObject(response.toString());
        System.out.println("result after Reading JSON Response");
        System.out.println("statusCode- "+myResponse.getString("kind"));
        if(!myResponse.getString("kind").equals("Listing")){
            System.out.println("Error generating Dank memes at the moment"); // to replace with send to discord once testing is done
        }else{
            JSONObject data = (JSONObject) myResponse.get("data");
            int random = (int )(Math.random() * data.getInt("dist") );
            System.out.println(random);
            JSONObject Post = (JSONObject) (data.getJSONArray("children").getJSONObject(random)).get("data");
            System.out.println(Post);

            //Now get data for post
            String postUrl = "https://www.reddit.com" + Post.getString("permalink");
            String postPic = Post.getString("url");
            String postTitle = Post.getString("title");

            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(postTitle, postUrl);
            eb.setColor(Color.white);
            eb.setImage(postPic);
            event.getChannel().sendMessage(eb.build()).queue();
        }

    }

}

