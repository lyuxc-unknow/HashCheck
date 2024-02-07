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
public class TheMod {
    public static final Logger LOGGER = LogManager.getLogger("HashVerification");
    public static final Set<String> hash = new HashSet<>();
    public static String hash256;
    public TheMod(IEventBus modEventBus) {
        modEventBus.addListener(this::onSetupEvent);
    }
    public void onSetupEvent(FMLCommonSetupEvent event) {
        try {
            String v = FileUtils.readFromFile("hash.txt",true);
            hash.addAll(Arrays.asList(v.split(System.lineSeparator())));
            for(File file: Objects.requireNonNull(new File(System.getProperty("user.dir") + "/mods").listFiles())) {
                if(file.getName().endsWith(".jar")) {
                    hash256 = Base64.getEncoder().encodeToString(HashCalculation.getSHA256(file).getBytes());
                    if(!hash.contains(hash256)) {
                        System.exit(0);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            FileUtils.writeToNewFile("hash.txt","",false);
        }
    }
}
