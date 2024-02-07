package mod.lyuxc.hash;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;

public class HashCalculation {
    public static String getSHA256(File file) {
        try (FileInputStream inputStream = new FileInputStream(file)){
            return DigestUtils.sha256Hex(inputStream);
        } catch (Exception e) {
            TheMod.LOGGER.error("Hash Calculation Failed!");
        }
        return "00000000000000000000000000000000";
    }
}
