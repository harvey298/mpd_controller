package uk.co.hoovy.mpd_controller;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mpd_controller implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("MPD Controller");

    @Override
    public void onInitialize() {

        LOGGER.info("Hello, I am going to automaticly control your MPD instance!");
    }
}
