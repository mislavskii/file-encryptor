package org.example;

import net.lingala.zip4j.core.ZipFile;

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

    public GUIForm() {

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
                    onFileSelect();
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
                    setButtonsEnabled(false);
                    String password = JOptionPane.showInputDialog("Enter password:");
                    if (password == null || password.isEmpty()) {
                        showWarning("Password is empty!");
                        setButtonsEnabled(true);
                        return;
                    }
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

    void setButtonsEnabled(boolean enabled) {
        selectButton.setEnabled(enabled);
        actionButton.setEnabled(enabled);
    }

    private void onFileSelect() {
        if (selectedFile == null) {
            filePath.setText("");
            actionButton.setVisible(false);
            return;
        }
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

    private void encryptFile(String password) {
        EncryptorThread thread = new EncryptorThread(this);
        thread.setFile(selectedFile);
        thread.setPassword(password);
        thread.start();
    }

    private void decryptFile(String password) {
        DecryptorThread thread = new DecryptorThread(this);
        thread.setFile(selectedFile);
        thread.setPassword(password);
        thread.start();
    }

    public void showWarning(String message) {
        JOptionPane.showMessageDialog(
            rootPanel,
            message,
            "Error",
            JOptionPane.WARNING_MESSAGE
        );
    }

    public void showFinished() {
        JOptionPane.showMessageDialog(
            rootPanel,
            encryptedFileSelected ? "Decrypted" : "Encrypted",
            "Success",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

}
