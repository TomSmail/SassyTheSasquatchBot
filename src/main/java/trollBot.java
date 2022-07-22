import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import javax.security.auth.login.LoginException;

public class trollBot extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {

        // Creates a bot with default intent
        JDA bot = null;

        try {
            bot = JDABuilder.createDefault("OTk4OTY4MTY2MDQ4ODc0NjE3.Gk9OmI.O2P1FnEpn1eLgTFmT0_QZbkhtaRPvk55Oev88U")
                    .addEventListeners(new botListeners())
                    .build().awaitReady();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Guild guild = bot.getGuildById("991039500081836094");
        if (guild != null){
            guild.upsertCommand("raid", "Lil Trolla")
                    .addOptions(new OptionData(OptionType.CHANNEL,  "raid" , "What channel do you want to raid?", true),
                            new OptionData(OptionType.MENTIONABLE, "user", "Pick the user you want to troll!", true),
                            new OptionData(OptionType.INTEGER, "menuchoice", "pick the number you want from the menu", true).setRequiredRange(1, 4))
                                    .queue();
            guild.upsertCommand("menu", "Pick your poisson!").queue();
            guild.upsertCommand("check", "Check ya points").queue();
        }


    }


}