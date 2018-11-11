package functions;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public class menu {
    public static void showMenu(MessageReceivedEvent event) {

        EmbedBuilder eb = new EmbedBuilder();

        eb.setColor(Color.blue);
        eb.setTitle("Korusuke-Bot", "https://github.com/korusuke/Discord-Bot/");
        eb.addField("!meme", "r/ProgrammersHumor meme at your finger tips", false);
        eb.addField("!rank", "Don't even dare...", false);
        eb.addField("!movie", "Do not search.\nFormat: ```!movie <query>```", false);
        eb.addField("!tvshow", "Do not try.\nFormat: ```!tvshow <query>```", false);
        eb.addField("!news", "Fake News alert.\nCategories: ```general, politics, gaming, music, business, entertainment, health-and-medical, science-and-nature, technology, sport.``` \nFormat: ```!news <category> <keyword or query>``` or ```!news latest``` to get latest updates.", false);
        eb.addField("!ping", "Feeling lonely...\nNo?Try it\nYes?Definitely Try it", false);

        event.getChannel().sendMessage(eb.build()).queue();
    }
}
