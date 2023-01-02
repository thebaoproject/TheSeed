/*
 * Copyright 2022-2023 SpikeBonjour
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ga.baoproject.theseed.abc;

import ga.baoproject.theseed.i18n.Localized;

/**
 * Represents an effect applied on a player.
 */
public class SeedEffect {
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
    public void applyEffect(SeedPlayer p) {
    }
}
