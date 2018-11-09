package functions;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public class menu {
    public static void showMenu(MessageReceivedEvent event) {

        EmbedBuilder eb = new EmbedBuilder();

        eb.setColor(Color.blue);
        eb.setTitle("Behold the Korusuke Bot!", "https://github.com/2knal/Probably-OOPM/");
        eb.addField("!meme", "r/programmershumor meme at your finger tips", false);
        eb.addField("!music", "Its not music!", false);
        eb.addField("!news", "Fake News alert.\nCategories: ```general, politics, gaming, music, business, entertainment, health-and-medical, science-and-nature, technology, sport.``` \nFormat: ```!news <category> <keyword or query>``` or ```!news latest``` to get latest updates.", false);
        eb.addField("!ping", "Try it out...", false);
        event.getChannel().sendMessage(eb.build()).queue();
    }
}
