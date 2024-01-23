package uk.co.hoovy.mpd_controller.client;

import de.dixieflatline.mpcw.client.CommunicationException;
import de.dixieflatline.mpcw.client.ProtocolException;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static uk.co.hoovy.mpd_controller.Mpd_controller.LOGGER;
import static uk.co.hoovy.mpd_controller.Mpd_controllerClient.mpdClient;
import static uk.co.hoovy.mpd_controller.Mpd_controllerClient.mpdPlaying;

@Mixin(MinecraftClient.class)
public class GoodbyeMixin {

    @Inject(method = "stop", at = @At("HEAD"))
    private void onClientStop(CallbackInfo ci) {
        try {
            mpdClient.getPlayer().play();
            mpdPlaying = true;
        } catch (CommunicationException | ProtocolException e) {
            LOGGER.error(String.valueOf(e));
        }
    }

}
