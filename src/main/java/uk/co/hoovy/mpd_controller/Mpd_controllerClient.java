package uk.co.hoovy.mpd_controller;

import de.dixieflatline.mpcw.client.*;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static uk.co.hoovy.mpd_controller.Mpd_controller.LOGGER;

public class Mpd_controllerClient implements ClientModInitializer {

//    public static final Logger LOGGER = LoggerFactory.getLogger("MPD Controller");

    public static String mpdURL = "mpd://localhost:6600";

    public static IConnection mpdConnection = null;

    public static IClient mpdClient = null;

    public static boolean mpdPlaying = false;

    @Override
    public void onInitializeClient() {
        RestartMPDConnection();
    }

    public static void RestartMPDConnection() {
        try {
            mpdConnection = Connection.create(mpdURL);
            mpdConnection.connect();

            mpdClient = mpdConnection.getClient();

            mpdPlaying = mpdClient.getPlayer().getStatus().getState() == EState.Play;

        } catch (Exception e) {
            LOGGER.info(e.toString());
        }
    }
}
