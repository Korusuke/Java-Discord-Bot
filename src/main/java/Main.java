import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.EmbedBuilder;
import javax.security.auth.login.LoginException;
import java.awt.Color;

import functions.reddit;
import functions.menu;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = "NTAyMTY1Mzc0MDMzMzMwMTc2.Dqj91Q.gOJ79EubCj8UI2StJFzvJEH1m8w"; // Hide it later
        builder.setToken(token);
        builder.addEventListener(new Main());
        builder.buildAsync();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){

        String rmsg = event.getMessage().getContentRaw();
        rmsg = rmsg.toLowerCase();
        System.out.println(rmsg);
        System.out.println("We received a message from " + event.getAuthor() .getName() + ": " + event.getMessage().getContentDisplay());

        if(rmsg.equals("!menu")){
            menu.showMenu(event);

        }

        if(rmsg.equals("!ping")) {
            event.getChannel().sendMessage("Pong!").queue();
        }




        if(rmsg.equals("!meme")){
            reddit.ph(event);

        }

    }
}

