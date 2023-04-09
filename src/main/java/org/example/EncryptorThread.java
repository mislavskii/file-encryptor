package org.example;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;

import java.io.File;

public class EncryptorThread extends Thread {
    private final GUIForm form;
    private File file;
    private ZipParameters parameters;

    public EncryptorThread(GUIForm form) {
        this.form = form;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setPassword(String password) {
        parameters = ParametersContainer.getParameters();
        parameters.setPassword(password);
    }

    private String getArchiveName() {
        for (int i = 0; ; i++) {
            String number = i > 0 ? Integer.toString(i) : "";
            String archiveName = file.getAbsolutePath() + number + ".cry";
            if (!new File(archiveName).exists()) {
                return archiveName;
            }
        }
    }

    @Override
    public void run() {
        onStart();
        try {
            String archiveName = getArchiveName();
            ZipFile zipFile = new ZipFile(archiveName);
            if (file.isDirectory()) {
                zipFile.addFolder(file, parameters);
            } else {
                zipFile.addFile(file, parameters);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        onFinish();
    }

    private void onStart () {
        form.setButtonsEnabled(false);
    }
    private void onFinish() {
        parameters.setPassword("");
        form.showFinished();
        form.setButtonsEnabled(true);
    }
}
