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
import jdk.jshell.execution.Util;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

/**
 * Non-flickering scoreboard (a single {@link Objective}) implementation.
 */
public class SeedScoreboard {
    private final Scoreboard scoreboard;
    private final Objective obj;
    private final List<String> content;


    public SeedScoreboard(Scoreboard scoreboard, Objective obj) {
        this.scoreboard = scoreboard;
        this.obj = obj;
        this.content = new ArrayList<>();
        this.obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        for (int i = 0; i <= 15; i++) {
            String name = Utils.nothing(i);
            this.content.add(name);
            obj.getScore(name).setScore(i);
        }
    }

    public void setLine(int line, String value) {
        scoreboard.resetScores(content.get(15-line));
        obj.getScore(value).setScore(15-line);
        content.set(15-line, value);
    }
}
