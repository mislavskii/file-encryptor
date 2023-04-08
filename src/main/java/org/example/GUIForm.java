package org.example;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

public class GUIForm {
    private JPanel rootPanel;
    private JTextField filePath;
    private JButton selectButton;
    private JButton actionButton;
    private File selectedFile;
    private boolean encryptedFileSelected;
    private final String decryptAction = "Decrypt";
    private final String encryptAction = "Encrypt";
    private final ZipParameters parameters;

    public GUIForm() {
        parameters = new ZipParameters();
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA);
        parameters.setEncryptFiles(true);
        parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
//        parameters.setPassword("sobaka");

        selectButton.addActionListener(
                new Action() {
                @Override
                public Object getValue(String key) {return null;}
                @Override
                public void putValue(String key, Object value) {}
                @Override
                public void setEnabled(boolean b) {}
                @Override
                public boolean isEnabled() {return false;}
                @Override
                public void addPropertyChangeListener(PropertyChangeListener listener) {}
                @Override
                public void removePropertyChangeListener(PropertyChangeListener listener) {}
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                    chooser.showOpenDialog(rootPanel);
                    selectedFile = chooser.getSelectedFile();
                    if (selectedFile == null) {
                        filePath.setText("");
                        actionButton.setVisible(false);
                    } else {
                        filePath.setText(selectedFile.getAbsolutePath());
                        try {
                            ZipFile zipFile = new ZipFile(selectedFile);
                            encryptedFileSelected = zipFile.isValidZipFile() && zipFile.isEncrypted();
                            actionButton.setText(
                                    encryptedFileSelected ? decryptAction : encryptAction
                            );
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        actionButton.setVisible(true);
                    }
                }
            }
        );

        actionButton.addActionListener(
                new Action() {
                @Override
                public Object getValue(String key) {return null;}
                @Override
                public void putValue(String key, Object value) {}
                @Override
                public void setEnabled(boolean b) {}
                @Override
                public boolean isEnabled() {return false;}
                @Override
                public void addPropertyChangeListener(PropertyChangeListener listener) {}
                @Override
                public void removePropertyChangeListener(PropertyChangeListener listener) {}
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (selectedFile == null) {
                        return;
                    }
                    // Encrypt or decrypt accordingly;
                    setButtonsEnabled(false);
                    String password = JOptionPane.showInputDialog("Enter password:");
                    if (encryptedFileSelected) {
                        decryptFile(password);
                    } else {
                        encryptFile(password);
                    }
                    setButtonsEnabled(true);
                }
            }
        );
    }

    private void setButtonsEnabled(boolean enabled) {
        selectButton.setEnabled(enabled);
        actionButton.setEnabled(enabled);
    }

    private String getArchiveName() {
        for (int i = 0; ; i++) {
            String number = i > 0 ? Integer.toString(i) : "";
            String archiveName = selectedFile.getAbsolutePath() + number + ".cry";
            if (!new File(archiveName).exists()) {
                return archiveName;
            }
        }
    }

    private void encryptFile(String password) {
        String archiveName = getArchiveName();
        parameters.setPassword(password);
        try {
            ZipFile zipFile = new ZipFile(archiveName);
            if (selectedFile.isDirectory()) {
                zipFile.addFolder(selectedFile, parameters);
            } else {
                zipFile.addFile(selectedFile, parameters);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getOutPath() {
        String path = selectedFile.getAbsolutePath().replace(".cry", "");
        for (int i = 0; ; i++) {
            String number = i > 0 ? Integer.toString(i) : "";
            String outPath = path + number;
            if (!new File(outPath).exists()) {
                return outPath;
            }
        }
    }

    private void decryptFile(String password) {
        String outPath = getOutPath();
        try {
            ZipFile zipFile = new ZipFile(selectedFile);
            zipFile.setPassword(password);
            zipFile.extractAll(outPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

}
