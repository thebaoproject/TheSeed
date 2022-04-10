package me.spike.blockartonline.abc;

import java.util.Arrays;
import java.util.List;

public class Ability {
    public ItemAbilityUseAction usage;
    public String name;
    public List<String> description;
    public int cost;

    public Ability setName(String name) {
        this.name = name;
        return this;
    }

    public Ability setDescription(String description) {
        this.description = Arrays.stream(description.split("\n")).toList();
    /*
        List<String> newDescription = new ArrayList<String>();
        int requiredLength = this.name.length() + 10;
        for (String i : this.description) {
            if (i.length() > requiredLength) {
                newDescription.add(i.substring(0, requiredLength));
                newDescription.add(i.substring(requiredLength + 1));
            } else {
                newDescription.add(i);
            }
        }
        this.description = newDescription;
    */
        return this;
    }

    public Ability setUsage(ItemAbilityUseAction usage) {
        this.usage = usage;
        return this;
    }

    public Ability setCost(int cost) {
        this.cost = cost;
        return this;
    }
}
