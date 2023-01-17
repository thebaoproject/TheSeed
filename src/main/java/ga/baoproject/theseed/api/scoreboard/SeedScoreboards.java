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

package ga.baoproject.theseed.api.scoreboard;

import ga.baoproject.theseed.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

/**
 * Scoreboards for each player in the game.
 */
public class SeedScoreboards {
    private static final HashMap<String, SeedScoreboard> data = new HashMap<>();

    /**
     * Gets the scoreboard for a specific player.
     * If it doesn't exist; automatically creates one and returns it.
     *
     * @param i the player to get the scoreboard from.
     * @return the player's scoreboard.
     */
    @NotNull
    public static SeedScoreboard get(Player i) {
        SeedScoreboard output = data.get(i.getName());
        if (output == null) {
            try {
                Objects.requireNonNull(i.getScoreboard().getObjective("sao")).unregister();
            } catch (IllegalArgumentException | NullPointerException ignored) {
                ignored.printStackTrace();
            }
            output = new SeedScoreboard(i.getScoreboard(), i.getScoreboard().registerNewObjective("sao", Criteria.DUMMY, Component.text(Utils.color("&e&lSWORD ART ONLINE"))));

            data.put(i.getName(), output);
        }
        return output;
    }
}
