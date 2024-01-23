package uk.co.hoovy.mpd_controller.client;

import de.dixieflatline.mpcw.client.CommunicationException;
import de.dixieflatline.mpcw.client.ProtocolException;
import net.minecraft.client.sound.*;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.sound.SoundCategory;

import java.util.List;
import java.util.Map;

import static uk.co.hoovy.mpd_controller.Mpd_controller.LOGGER;
import static uk.co.hoovy.mpd_controller.Mpd_controllerClient.*;

@Mixin(SoundSystem.class)
public class MusicController {
    // SoundSystem.class

    @Shadow @Final private List<TickableSoundInstance> soundsToPlayNextTick;
    @Shadow @Final private Map<SoundInstance, Integer> soundEndTicks;

    @Inject(method = "play(Lnet/minecraft/client/sound/SoundInstance;)V", at = @At("RETURN"))
    private void onPlay(SoundInstance sound, CallbackInfo info) {
        if (sound.getCategory() == SoundCategory.MUSIC ) {
            LOGGER.info("Music Playing! Pausing MPD Music");

            try {
                mpdClient.getPlayer().pause();
                mpdPlaying = false;
            } catch (CommunicationException | ProtocolException e) {
                LOGGER.error(String.valueOf(e));
            } catch (NullPointerException e) {
                LOGGER.warn(String.valueOf(e));
                LOGGER.warn("Restarting MPD Connection!");
                RestartMPDConnection();
            }
        }
    }

    @Inject(method = "tick()V", at = @At("RETURN"))
    private void onSoundsTick(CallbackInfo info) {
        if (this.soundsToPlayNextTick.isEmpty() && this.soundEndTicks.isEmpty() && !mpdPlaying ) {
            try {
                mpdClient.getPlayer().play();
                mpdPlaying = true;
            } catch (CommunicationException | ProtocolException e) {
                LOGGER.error(String.valueOf(e));
            } catch (NullPointerException e) {
                LOGGER.warn(String.valueOf(e));
                LOGGER.warn("Restarting MPD Connection!");
                RestartMPDConnection();
            }
        }

    }


}
