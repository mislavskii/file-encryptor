package org.example;

import net.lingala.zip4j.core.ZipFile;

import java.io.File;

public class DecryptorThread extends Thread{
    private final GUIForm form;
    private File file;
    private String password;

    public DecryptorThread(GUIForm form) {
        this.form = form;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFile(File file) {
        this.file = file;
    }

    private String getOutPath() {
        String path = file.getAbsolutePath().replace(".cry", "");
        for (int i = 0; ; i++) {
            String number = i > 0 ? Integer.toString(i) : "";
            String outPath = path + number;
            if (!new File(outPath).exists()) {
                return outPath;
            }
        }
    }

    @Override
    public void run() {
        onStart();
        String outPath = getOutPath();
        try {
            ZipFile zipFile = new ZipFile(file);
            zipFile.setPassword(password);
            zipFile.extractAll(outPath);
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("Wrong Password")) {
                form.showWarning("Wrong Password!");
                Utils.wipeDir(outPath);
                form.setButtonsEnabled(true);
                return;
            }
            e.printStackTrace();
        }
        onFinish();
    }

    private void onStart() {
        form.setButtonsEnabled(false);
    }
    private void onFinish() {
        form.showFinished();
        form.setButtonsEnabled(true);
    }

}
