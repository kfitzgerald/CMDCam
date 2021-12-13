package team.creative.cmdcam.client;

//import ca.weblite.objc.Client;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.fml.loading.FMLPaths;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import team.creative.cmdcam.common.util.AnnotationExclusionStrategy;
import team.creative.cmdcam.common.util.CamPath;

import java.io.*;
//import java.nio.file.Files;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class ClientConfiguration {

    public HashMap<String, CamPath> paths;

    public ClientConfiguration() {
        paths = new HashMap<>();
    }

    public ClientConfiguration(HashMap<String, CamPath> paths) {
        this.paths = paths;
    }

    protected Path getConfigPath() {
//        return FMLPaths.CONFIGDIR.get().resolve("cmdcam.yml");
        return FMLPaths.CONFIGDIR.get().resolve("cmdcam.json");
    }

    public void save() {
        Path path = this.getConfigPath();
        this.saveFile(path);
    }

    protected void saveFile(Path path) {
        try {
            System.out.println("CMDCam config saving...");
//            FileWriter writer = new FileWriter(path.toFile());
//            Yaml yaml = new Yaml();
//            yaml.dump(this, writer);
//            writer.close();

            FileWriter writer = new FileWriter(path.toFile());
            Gson gson = new GsonBuilder()
                    .setExclusionStrategies(new AnnotationExclusionStrategy())
                    .setPrettyPrinting()
                    .create();
            gson.toJson(this, writer);
            writer.close();

            System.out.println("CMDCam config saved!");

        } catch (Exception e) {
            System.out.println("CMDCam failed to save config! " + e.toString());
        }
    }

    public void load() {
        Path path = this.getConfigPath();
        this.loadFile(path);
    }

    protected void loadFile(Path path) {
        try {
            if (!path.toFile().exists()) {
                System.out.println("CMDCam config does not exist. Creating: " + path.toString());
                paths = new HashMap<>();
                this.save();
                return;
            }

//            System.out.println("CMDCam config loading...");
//            FileInputStream input = new FileInputStream(path.toFile());
//            Constructor constructor = new Constructor(ClientConfiguration.class);
//            TypeDescription pathDescription = new TypeDescription((ClientConfiguration.class));
//            pathDescription.putMapPropertyType("paths", ClientConfiguration.class, String.class);
//            constructor.addTypeDescription(pathDescription);
//            Yaml yaml = new Yaml(constructor);
//
//            ClientConfiguration config = yaml.load(input);

            Reader reader = Files.newBufferedReader(path);
            Gson gson = new Gson();
            ClientConfiguration config = gson.fromJson(reader, ClientConfiguration.class);

            paths = config.paths;
//            paths = yaml.load(input);
            System.out.println("CMDCam config loaded! "+paths.size()+" paths loaded");

        } catch (FileNotFoundException e) {
            // Config file does not exist -- create!
            paths = new HashMap<>();
            this.save();
            System.out.println("CMDCam config does not exist. Creating: " + path.toString());

        } catch (Exception e) {
            System.out.println("FAILED TO LOAD CMDCam CONFIG: " + e.toString());
        }
    }

}
