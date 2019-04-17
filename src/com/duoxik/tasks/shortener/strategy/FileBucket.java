package com.duoxik.tasks.shortener.strategy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBucket {

    private Path path;

    public FileBucket() {
        try {
            this.path = Files.createTempFile("", "");
            Files.deleteIfExists(path);
            Files.createFile(path);
            path.toFile().deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long getFileSize() {
        try {
            return Files.size(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public void putEntry(Entry entry) {
        try (
                ObjectOutputStream outputStream
                        = new ObjectOutputStream(Files.newOutputStream(path))
        ) {
            for (Entry e = entry; e != null; e = e.next)
                outputStream.writeObject(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Entry getEntry() {

        if (getFileSize() == 0)
            return null;

        try (
                ObjectInputStream inputStream
                        = new ObjectInputStream(Files.newInputStream(path))
        ) {
            Entry entry = (Entry) inputStream.readObject();

//            Entry currentEntry = entry;
//            Entry nextEntry = (Entry) inputStream.readObject();
//            while (nextEntry != null) {
//                currentEntry.next = nextEntry;
//                currentEntry = nextEntry;
//                nextEntry = (Entry) inputStream.readObject();
//            }

            return entry;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void remove() {
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
