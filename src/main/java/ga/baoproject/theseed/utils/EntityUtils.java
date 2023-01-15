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

import ga.baoproject.theseed.TheSeed;
import ga.baoproject.theseed.abc.SeedEntity;
import ga.baoproject.theseed.exceptions.InvalidEntityID;
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
     * Gets the {@link SeedEntity} object from the {@link Entity}.
     *
     * @param entityName the ID of the entity.
     * @return the {@link SeedEntity} found.
     */
    @NotNull
    @SuppressWarnings("deprecation")
    public static SeedEntity get(@NotNull String entityName) throws InvalidEntityID {
        if (entityName.contains("minecraft:")) {
            return new SeedEntity(EntityType.fromName(entityName));
        }
        return switch (entityName) {
            case "sao:demonic_servant":
                yield new DemonicServant();
            case "sao:illfang_boss":
                yield new IllfangBoss();
            default:
                throw new InvalidEntityID();
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
            // Entities which haven't had its NBT set up yet (only when it has been spawned
            // could its name be modified).
            return false;
        } else if (e.getType() == EntityType.PLAYER) {
            Integer data = readFrom(e, "health", PersistentDataType.INTEGER);
            return data == null;
        }
        return true;
    }

    @Contract(pure = true)
    public static @NotNull @Unmodifiable List<String> getEntityList() {
        return List.of("sao:demonic_servant", "sao:illfang_boss");
    }

    /**
     * Gets the ID from an already registered entity.
     *
     * @param e the entity to get the ID.
     * @return the ID.
     */
    @NotNull
    @SuppressWarnings("unused")
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
    public static void writeTo(@NotNull Entity e, String path, Object value) {
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
     * @param <T>          the type this class should return. This is the same as
     *                     {@code expectedType}
     * @return the value read from the tag.
     */
    @Nullable
    public static <T> T readFrom(@NotNull Entity e, String path, PersistentDataType<T, T> expectedType) {
        Plugin pl = TheSeed.getInstance();
        PersistentDataContainer container = e.getPersistentDataContainer();
        return container.get(new NamespacedKey(pl, path), expectedType);
    }
}
