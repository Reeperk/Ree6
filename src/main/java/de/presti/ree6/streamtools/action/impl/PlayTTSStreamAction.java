package de.presti.ree6.streamtools.action.impl;

import com.github.twitch4j.common.events.TwitchEvent;
import de.presti.ree6.main.Main;
import de.presti.ree6.streamtools.action.StreamActionInfo;
import de.presti.ree6.streamtools.action.IStreamAction;
import de.presti.ree6.utils.external.RequestUtility;
import de.presti.ree6.utils.others.RandomUtils;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.entities.Guild;
import org.jetbrains.annotations.NotNull;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * StreamAction used to play a text-to-speech messages.
 */
@NoArgsConstructor
@StreamActionInfo(name = "PlayTTS", command = "play-tts", description = "Plays a text-to-speech message.", introduced = "2.2.0")
public class PlayTTSStreamAction implements IStreamAction {

    /**
     * @inheritDoc
     */
    @Override
    public boolean runAction(@NotNull Guild guild, TwitchEvent twitchEvent, String[] arguments) {
        if (arguments == null || arguments.length == 0) {
            return false;
        }

        boolean useTTSMonster = Arrays.stream(arguments).anyMatch(s -> s.startsWith("--ttsmonster:"));

        if (!Main.getInstance().getMusicWorker().isConnectedMember(guild.getSelfMember())) return false;

        String ttsMessage = String.join(" ", arguments).split("--ttsmonster:")[0];

        String baseUrl = useTTSMonster ? "NOT YET IMPLEMENTED" : "https://api.streamelements.com/kappa/v2/speech?voice=Brian&text=";

        Main.getInstance().getMusicWorker().loadAndPlay(guild, null, null,
                baseUrl + URLEncoder.encode(ttsMessage, StandardCharsets.UTF_8),
                null, true, false);

        return true;
    }
}
