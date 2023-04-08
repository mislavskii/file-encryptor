package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello zip world!");

        JFrame frame = new JFrame("File Encryptor");
        frame.setSize(600, 420);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        GUIForm form = new GUIForm();
        frame.add(form.getRootPanel());

        frame.setVisible(true);

//        String path = "D:\\WinDirs\\Desktop\\GlobicaDocs/";
//        ZipParameters parameters = new ZipParameters();
//
//        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
//        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA);
//        parameters.setEncryptFiles(true);
//        parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
////        parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
//        parameters.setPassword("sobaka");
//
//        UnzipParameters unzipParameters = new UnzipParameters();
//
//        try {
//            ZipFile zipFile = new ZipFile(path + "archive.zip");
//            zipFile.addFile(
//                    new File(path + "договор_01_2101_ММ.pdf"),
//                    parameters
//                    );
//            zipFile.addFolder(new File(path + "PRINT_ONE_COPY"), parameters);
//
////            zipFile.extractAll(path + "unzipped");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}