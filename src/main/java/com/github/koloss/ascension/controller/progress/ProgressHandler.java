package com.github.koloss.ascension.controller.progress;

import org.bukkit.event.Event;

public interface ProgressHandler {
    long getProgress(Event event);
}
