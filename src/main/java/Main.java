import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.awt.Color;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = ""; // Hide it later
        builder.setToken(token);
        builder.addEventListener(new Main());
        builder.buildAsync();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Reply", "https://www.google.com");
        eb.setColor(Color.white);
        eb.setDescription("Lorem Ipsum");
        eb.addField("My Field", "Lorem Ipsum", false);
        eb.addBlankField(false);
        eb.setAuthor("John Doe", "https://www.github.com/", "http://chittagongit.com//images/icon-urls/icon-urls-8.jpg");
        eb.setFooter("Hala Madrid!", null);
        eb.setImage("http://chittagongit.com//images/icon-urls/icon-urls-8.jpg");

        System.out.println("We received a message from " + event.getAuthor() .getName() + ": " + event.getMessage().getContentDisplay());
        if(event.getMessage().getContentRaw().equals("!ping")){
            event.getChannel().sendMessage(eb.build()).queue();
        }
    }
}

