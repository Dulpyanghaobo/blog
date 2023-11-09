package com.hab.blog.utility;
import java.io.File;
import java.io.IOException;

public class FileConvertProvider {

    public static void main(String[] args) {
        convertToPdf("/Users/i564407/blog/ChatGPT.pptx", "~/blog/demo");
    }

    public static void convertToPdf(String sourceFilePath, String outputDirectory) {
        // 替换路径中的~为用户目录路径
        if (outputDirectory.startsWith("~" + File.separator)) {
            outputDirectory = System.getProperty("user.home") + outputDirectory.substring(1);
        }

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(
                "soffice",
                "--headless",
                "--convert-to", "pdf:writer_pdf_Export",
                sourceFilePath,
                "--outdir", outputDirectory
        );

        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Conversion successful.");
            } else {
                System.err.println("Conversion failed with exit code " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}