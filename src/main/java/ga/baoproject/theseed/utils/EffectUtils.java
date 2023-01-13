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

package ga.baoproject.theseed.utils;

import com.google.gson.Gson;
import ga.baoproject.theseed.abc.DebugLogger;
import ga.baoproject.theseed.abc.SeedEffect;
import ga.baoproject.theseed.effects.Paralyze;
import ga.baoproject.theseed.effects.VanillaEffect;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

public class EffectUtils {
    /**
     * Gets the {@link SeedEffect} object from its ID.
     *
     * @param effectID the effect id.
     * @return the {@link SeedEffect} found.
     */
    @NotNull
    public static SeedEffect get(@NotNull String effectID) {
        return switch (effectID) {
            case "sao:paralyze" -> new Paralyze();
            default -> new VanillaEffect(effectID);
        };
    }

    @NotNull
    public static SeedEffect getChild(@NotNull SeedEffect e) {
        DebugLogger.debug(new Gson().toJson(e));
        return switch (e.getId()) {
            case "sao:paralyze":
                Paralyze ef = new Paralyze();
                ef.setDuration(e.getDuration());
                yield ef;
            default:
                yield new VanillaEffect(e.getId());
        };
    }

    @Contract(pure = true)
    public static @NotNull @Unmodifiable List<String> getEffectList() {
        return List.of("sao:paralyze");
    }
}
