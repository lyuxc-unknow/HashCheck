package mod.lyuxc.hash;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Mod("hash_check")
public class HashVerification {
    public static final Logger LOGGER = LogManager.getLogger("HashVerification");
    public static final Set<String> hash = new HashSet<>();
    public HashVerification(IEventBus modEventBus) {
        modEventBus.addListener(this::onSetupEvent);
    }
    public static final String[] dirList = new String[] {
            "mods",
            "scripts",
            "kubejs/client_scripts",
            "kubejs/server_scripts",
            "kubejs/startup_scripts"
    };
    public void onSetupEvent(FMLCommonSetupEvent event) {
        try {
            hash.addAll(
                    List.of(
                            FileUtils.readFromFile("hash.txt", true).split(System.lineSeparator())
                    )
            );

            for(String dirPath : dirList) {
                File directory = new File(System.getProperty("user.dir"), dirPath);
                HashCalculationUtils.traverseDirectory(directory);
            }
        } catch (FileNotFoundException e) {
            FileUtils.writeToNewFile("hash.txt","",false);
        }
    }
}
