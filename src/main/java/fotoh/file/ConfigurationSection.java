package fotoh.file;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    protected Map<String, Object> getSectionKeys() {
        return keys;
    }

    public Set<String> getKeys() {
        return keys.keySet();
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

    public String getString(String path) {
        return (String) keys.get(path);
    }

    public boolean getBoolean(String path) {
        return (boolean) keys.get(path);
    }

    public int getInteger(String path) {
        return (int) keys.get(path);
    }

    public double getDouble(String path) {
        return (double) keys.get(path);
    }

    public long getLong(String path) {
        return (long) keys.get(path);
    }

    public Object getObject(String path) {
        return keys.get(path);
    }

    public List<?> getList(String path) {
        return (List<?>) keys.get(path);
    }

    public float getFloat(String path) {
        return (float) keys.get(path);
    }

    public ConfigurationSection getSection(String sectionName) {
        Map<String, Object> values;
        if(!keys.containsKey(sectionName)) return null;
        if(!(keys.get(sectionName) instanceof Map<?,?>)) return null;
        try {
            values = (Map<String, Object>) keys.get(sectionName);
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
