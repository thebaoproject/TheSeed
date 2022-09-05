/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law.You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
 */

package ga.baoproject.theseed.utils;

import com.google.gson.Gson;
import ga.baoproject.theseed.abc.CustomEffect;
import ga.baoproject.theseed.abc.DebugLogger;
import ga.baoproject.theseed.effects.Paralyze;
import ga.baoproject.theseed.effects.VanillaEffect;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

public class EffectUtils {
    /**
     * Gets the {@link ga.baoproject.theseed.abc.CustomEffect} object from its ID.
     *
     * @param effectID the effect id.
     * @return the {@link ga.baoproject.theseed.abc.CustomEffect} found.
     */
    @NotNull
    public static CustomEffect get(@NotNull String effectID) {
        return switch (effectID) {
            case "sao:paralyze" -> new Paralyze();
            default -> new VanillaEffect(effectID);
        };
    }

    @NotNull
    public static CustomEffect getChild(@NotNull CustomEffect e) {
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
