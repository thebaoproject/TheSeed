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
