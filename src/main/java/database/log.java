package database;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import io.github.ccincharge.newsapi.datamodels.Article;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.*;

public class log {
    public static void logMsg(Firestore db, MessageReceivedEvent event){
        System.out.println("Logging...");

        DocumentReference docRef = db.collection("Korusuke-Bot").document(event.getGuild().getName());
        docRef = docRef.collection("AllMsg").document(event.getMessageId());

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

        DocumentReference docRef = db.collection("Korusuke-Bot").document(event.getGuild().getName());
        docRef = docRef.collection("UserPts").document(event.getAuthor().getName());

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

        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    public static void update_ranks(Firestore db, MessageReceivedEvent event){
        //asynchronously retrieve all documents

        DocumentReference docRef = db.collection("Korusuke-Bot").document(event.getGuild().getName());
        ApiFuture<QuerySnapshot> future = docRef.collection("UserPts").get();

        // future.get() blocks on response
        List<users> guys = new ArrayList<>();
        try{
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            for (QueryDocumentSnapshot document : documents) {
                Map<String, Object> data = document.getData();
                users guy = new users();
                String name;
                int points;
                for (Map.Entry<String, Object> entry : data.entrySet()){
                    if(entry.getKey().equals("Author")){
                        name = String.valueOf(entry.getValue());
                        guy.setName(name);
                    }
                    else if(entry.getKey().equals("Points")){
                        points = Integer.parseInt(String.valueOf(entry.getValue()));
                        guy.setRank(points);
                    }
                    System.out.println("Key: "+ entry.getKey() + "\nValue: " + entry.getValue());
                }
                guys.add(guy);
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        int rank;
        Collections.sort(guys);
        for(int i=0; i<guys.size(); i++){
            if(event.getAuthor().getName().equals(guys.get(i).getName())){

                docRef = db.collection("Korusuke-Bot").document(event.getGuild().getName());
                docRef = docRef.collection("UserPts").document(event.getAuthor().getName());

                Map<String, Object> data = new HashMap<>();
                data.put("Rank", guys.size() - i);
                docRef.update(data);
            }
            System.out.println("Name: "+guys.get(i).getName()+" Points: "+guys.get(i).getRank());
        }


    }
}

class users implements Comparable{
    String name;
    int rank;
    public String getName() {
        return name;
    }

    public int getRank() {
        return rank;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", rank=" + rank +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        if (this.getRank() < ((users) o).getRank()) {
            return -1;
        }

        if (this.getRank() == ((users) o).getRank()) {
            return 0;
        }
        return 1;
    }
}