/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law.You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
 */

package ga.baoproject.theseed.abc;

import ga.baoproject.theseed.i18n.Localized;

/**
 * Represents an effect applied on a player.
 */
public class CustomEffect {
    private String id;
    private Localized name;
    private int duration;
    private Localized description;

    public Localized getName() {
        return name;
    }

    public void setName(Localized name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Localized getDescription() {
        return description;
    }

    public void setDescription(Localized description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method is called every second for an item.
     * Child class must implement this method.
     *
     * @param p the player who have the effect on.
     */
    public void applyEffect(CustomPlayer p) {
    }
}
