package fotoh.file;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationSection {

    @Getter
    private final String name;
    private final Map<String, Object> keys;
    private final Map<String, Object> parent;
    private final YML yml;

    public ConfigurationSection(String name, Map<String, Object> parent, YML yml) {
        this.yml = yml;
        this.keys = new HashMap<>();
        this.parent = parent;
        parent.putIfAbsent(name, keys);
        this.name = name;
    }

    protected Map<String, Object> getKeys() {
        return keys;
    }

    protected Map<String, Object> getParentKeys() {
        return parent;
    }

    public ConfigurationSection(String name, Map<String, Object> keys, Map<String, Object> parent, YML yml) {
        this.yml = yml;
        this.name = name;
        this.parent = parent;
        this.keys = keys;
    }

    public ConfigurationSection getParent() {
        return new ConfigurationSection(name, parent, yml);
    }

    public ConfigurationSection createSection(String sectionName) {
        return new ConfigurationSection(sectionName, keys, yml);
    }

    public ConfigurationSection createSection(String sectionName, ConfigurationSection parentSection) {
        return new ConfigurationSection(sectionName, parentSection.keys, yml);
    }

    public ConfigurationSection getSection(String sectionName) {
        Map<String, Object> values;
        if(!parent.containsKey(sectionName)) return null;
        if(!(parent.get(sectionName) instanceof Map<?,?>)) return null;
        try {
            values = (Map<String, Object>) parent.get(sectionName);
        }catch(Exception e) {
            return null;
        }
        return new ConfigurationSection(sectionName, values, keys, yml);
    }

    public ConfigurationSection getOrCreateSection(String sectionName) {
        if(getSection(sectionName) != null) return getSection(sectionName);
        else{
            return createSection(sectionName);
        }
    }

}
