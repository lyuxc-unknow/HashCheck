package mod.lyuxc.hash;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;

import static mod.lyuxc.hash.HashVerification.hash;

public class HashCalculationUtils {
    public static String hash256;
    public static void traverseDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        traverseDirectory(file); // 递归遍历子文件夹
                    } else {
                        if (isSupportedFileType(file.getName())) {
                            validateFile(file);
                        }
                    }
                }
            }
        }
    }
    private static boolean isSupportedFileType(String fileName) {
        return fileName.endsWith(".jar") || fileName.endsWith(".js") || fileName.endsWith(".zs") || fileName.endsWith(".zc");
    }

    private static void validateFile(File file) {
        System.out.println(file);
        hash256 = Base64.getEncoder().encodeToString(HashCalculationUtils.getSHA256(file).getBytes());
        if(!hash.contains(hash256)) {
            HashVerification.LOGGER.error("{} is a file that fails validation", file.getName());
            System.exit(0);
        }
    }

    private static String getSHA256(File file) {
        try (FileInputStream inputStream = new FileInputStream(file)){
            return DigestUtils.sha256Hex(inputStream);
        } catch (Exception e) {
            HashVerification.LOGGER.error("Hash Calculation Failed!");
        }
        return "00000000000000000000000000000000";
    }
}
