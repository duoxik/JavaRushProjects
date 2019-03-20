package com.duoxik.tasks.htmleditor;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class HTMLFileFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().matches(".*[.]htm[l]?$");
    }

    @Override
    public String getDescription() {
        return "HTML и HTM файлы";
    }
}
