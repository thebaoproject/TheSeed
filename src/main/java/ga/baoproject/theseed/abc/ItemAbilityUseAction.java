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
import org.jetbrains.annotations.NotNull;

/**
 * Describes the way a skill can be activated.
 */
public enum ItemAbilityUseAction {
    /**
     * Right click when holding the item.
     */
    RIGHT_CLICK,
    /**
     * Hold shift when holding.
     */
    SNEAK,
    /**
     * Jump two times when holding the item.
     */
    DOUBLE_JUMP,
    /**
     * The base property of the item. It always works.
     */
    NONE;

    /**
     * Gets the localized action string for an item.
     *
     * @return the action name.
     */
    @NotNull
    public Localized toLocalizedString() {
        return switch (this) {
            case RIGHT_CLICK -> new Localized("CHUỘT PHẢI", "plugin.item.useAction.rightClick");
            case SNEAK -> new Localized("SHIFT", "plugin.item.useAction.shift");
            case DOUBLE_JUMP -> new Localized("NHẢY HAI LẦN", "plugin.item.useAction.doubleJump");
            case NONE -> new Localized("");
        };
    }
}
