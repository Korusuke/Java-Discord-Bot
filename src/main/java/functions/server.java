package functions;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class server {
    public static void server_rank(Firestore db, MessageReceivedEvent event){
        EmbedBuilder eb = new EmbedBuilder();
        User user = event.getAuthor();

        eb.setTitle("Server:"+event.getGuild().getName());
        eb.setAuthor(user.getName());

        DocumentReference docRef = db.collection("Korusuke-Bot").document(event.getGuild().getName());
        docRef = docRef.collection("UserPts").document(event.getAuthor().getName());
        ApiFuture<DocumentSnapshot> future = docRef.get();
        try{
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                System.out.println("Document data: " + document.getData());
            }
            else {
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
}
