package functions;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public class menu {
    public static void showMenu(MessageReceivedEvent event) {

        EmbedBuilder eb = new EmbedBuilder();

        eb.setColor(Color.white);
        eb.addField("!meme", "r/programmershumor meme at your finger tips", false);
        eb.addField("!music", "Its not music", false);
        eb.addField("!news", "Fake News alert", false);
        eb.addField("!ping", "try it out", false);
        event.getChannel().sendMessage(eb.build()).queue();
    }
}
