package fotoh.file;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class YML {

    public YML(String folder, String filename) {
        this(new File(folder, filename));
    }

    private final Yaml yaml = new Yaml();
    private final File file;
    private final Map<String, Object> keys;

    public YML(File file) {
        File parent = file.getParentFile();
        if(!parent.exists()) parent.mkdirs();
        if(!file.exists()) try {
            file.createNewFile();
        }catch (IOException e) {
            throw new RuntimeException("Unable to create file", e);
        }
        this.file = file;

        try {
            this.keys = yaml.load(new FileReader(file));
        }catch (IOException e) {
            throw new RuntimeException("Unable to load Yaml file", e);
        }
    }

    public Set<String> getKeys() {
        return keys.keySet();
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

    public YML(File parent, String filename) {
        this(new File(parent, filename));
    }

    public ConfigurationSection createSection(String sectionName) {
        return new ConfigurationSection(sectionName, keys, this);
    }

    public ConfigurationSection createSection(String sectionName, ConfigurationSection parentSection) {
        return new ConfigurationSection(sectionName, parentSection.getParentKeys(), this);
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
        return new ConfigurationSection(sectionName, values, keys, this);
    }

    public ConfigurationSection getOrCreateSection(String sectionName) {
        if(getSection(sectionName) != null) return getSection(sectionName);
        else{
            return createSection(sectionName);
        }
    }

}
