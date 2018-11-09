package database;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import io.github.ccincharge.newsapi.datamodels.Article;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.Map;

public class log {
    public static void logMsg(Firestore db, MessageReceivedEvent event){
        System.out.println("Logging...");
        DocumentReference docRef = db.collection("AllMsg").document(event.getMessageId());
        // Add document data  with id "alovelace" using a hashmap
        Map<String, Object> data = new HashMap<>();
        data.put("Author", event.getAuthor().getName());
        data.put("Message", event.getMessage().getContentRaw());
        data.put("Time", "" + event.getMessage().getCreationTime());
        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);
        // ...
        // result.get() blocks on response
    }

    public static void updatepts(Firestore db, MessageReceivedEvent event){

        System.out.println("Calculating pts based on K1506-formula created by my master...");
        DocumentReference docRef = db.collection("UserPts").document(event.getAuthor().getName());

        ApiFuture<DocumentSnapshot> future = docRef.get();

        try {
            DocumentSnapshot document = future.get();


            int points = event.getMessage().getContentRaw().length();

            if(document.exists()){
                points = points + Math.toIntExact(document.getLong("Points"));
                docRef.update("Points", points);

            }else{
                Map<String, Object> data = new HashMap<>();
                data.put("Author", event.getAuthor().getName());
                data.put("Points", points);
                //asynchronously write data
                ApiFuture<WriteResult> result = docRef.set(data);
            }

        }catch (Exception e){}
    }
}
