/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law. You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
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
