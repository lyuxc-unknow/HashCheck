package mod.lyuxc.hash;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

public class HashCalculation {
    public static String getSHA256(File file) {
        String hash256;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            hash256 = DigestUtils.sha256Hex(inputStream);
            return hash256;
        } catch (Exception e) {
            TheMod.LOGGER.error("Hash Calculation Failed!");
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.fillInStackTrace();
                }
            }
        }
        return null;
    }
}
