package mod.lyuxc.hash;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Mod("hash_verification")
public class TheMod {
    public static final Logger LOGGER = LogManager.getLogger("HashVerification");
    public static final Set<String> hash = new HashSet<>();
    public static final Set<String> calculationHashList = new HashSet<>();
    public static String hash256 = "0";
    public TheMod(IEventBus modEventBus) {
        modEventBus.addListener(this::onSetupEvent);
    }
    public void onSetupEvent(FMLCommonSetupEvent event) {
        try {
            String v = FileUtils.readFromFile("hash.txt",true);
            hash.addAll(Arrays.asList(v.split(System.lineSeparator())));
            File files = new File(System.getProperty("user.dir") + "/mods");
            for(File file: Objects.requireNonNull(files.listFiles())) {
                if(file.getName().endsWith(".jar")) {
                    hash256 = HashCalculation.getSHA256(file);
                    calculationHashList.add(hash256);
                    if(!hash.contains(hash256)) {
                        System.exit(0);
                    }
                }
            }
            System.out.println(calculationHashList.stream().toList());
        } catch (FileNotFoundException e) {
            FileUtils.writeToNewFile("hash.txt","",false);
        }
    }
}
