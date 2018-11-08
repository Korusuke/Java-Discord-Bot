package functions;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public class reddit {
    public static void ph(MessageReceivedEvent event) {
        event.getChannel().sendMessage("Meme Incoming!").queue();
    }
}
