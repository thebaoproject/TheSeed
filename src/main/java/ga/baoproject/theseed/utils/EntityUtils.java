/*
 * Copyright (c) 2022 the Block Art Online Project contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package ga.baoproject.theseed.utils;

import ga.baoproject.theseed.TheSeed;
import ga.baoproject.theseed.abc.CustomEntity;
import ga.baoproject.theseed.exceptions.UnknownEntity;
import ga.baoproject.theseed.monsters.DemonicServant;
import ga.baoproject.theseed.monsters.IllfangBoss;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.Objects;

public class EntityUtils {
    /**
     * Gets the {@link CustomEntity} object from the {@link Entity}.
     *
     * @param entityName the ID of the entity.
     * @return the {@link CustomEntity} found.
     */
    @NotNull
    public static CustomEntity get(String entityName) throws UnknownEntity {
        return switch (entityName) {
            case "demonic_servant":
                yield new DemonicServant();
            case "boss_illfang":
                yield new IllfangBoss();
            default:
                throw new UnknownEntity();
        };
    }

    /**
     * Checks if the player is sus (have the player data set up).
     *
     * @return whether the player is sus or not.
     */
    public static boolean impostor(@NotNull Damageable e) {
        // Well shit, but there is no other way to do this.
        if (e.isCustomNameVisible() && e.getName().contains("❤")) {
            // Entities which haven't had its NBT set up yet (only when it has been spawned could its name be modified).
            return false;
        } else if (e.getType() == EntityType.PLAYER) {
            Integer data = readFrom(e, "health", PersistentDataType.INTEGER);
            return data == null;
        }
        return true;
    }

    @Contract(pure = true)
    @NotNull
    @Unmodifiable
    public static List<String> getEntityList() {
        return List.of("demonic_servant", "boss_illfang");
    }

    /**
     * Gets the ID from an already registered entity.
     *
     * @param e the entity to get the ID.
     * @return the ID.
     */
    @NotNull
    public static String getIDFrom(@NotNull Entity e) {
        Plugin pl = TheSeed.getInstance();
        PersistentDataContainer container = e.getPersistentDataContainer();
        return Objects.requireNonNullElse(container.get(new NamespacedKey(pl, "id"), PersistentDataType.STRING), "");
    }

    /**
     * Writes a NBT Tag to an entity.
     *
     * @param e     the entity to write to.
     * @param path  the path of the tag.
     * @param value the value of the tag.
     */
    public static void writeTo(@NotNull Damageable e, String path, Object value) {
        Plugin pl = TheSeed.getInstance();
        PersistentDataContainer container = e.getPersistentDataContainer();
        if (value instanceof Integer) {
            container.set(new NamespacedKey(pl, path), PersistentDataType.INTEGER, (Integer) value);
        } else if (value instanceof String) {
            container.set(new NamespacedKey(pl, path), PersistentDataType.STRING, (String) value);
        } else if (value instanceof Long) {
            container.set(new NamespacedKey(pl, path), PersistentDataType.LONG, (Long) value);
        } else if (value instanceof Double) {
            container.set(new NamespacedKey(pl, path), PersistentDataType.DOUBLE, (Double) value);
        }
    }

    /**
     * Reads a NBT Tag from an entity.
     *
     * @param e            the entity to read.
     * @param path         the path of the tag.
     * @param expectedType the expected value type.
     * @param <T>          the type this class should return. This is the same as {@code expectedType}
     * @return the value read from the tag.
     */
    @Nullable
    public static <T> T readFrom(@NotNull Damageable e, String path, PersistentDataType<T, T> expectedType) {
        Plugin pl = TheSeed.getInstance();
        PersistentDataContainer container = e.getPersistentDataContainer();
        return container.get(new NamespacedKey(pl, path), expectedType);
    }
}
