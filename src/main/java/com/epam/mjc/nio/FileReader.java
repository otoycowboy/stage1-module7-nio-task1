package com.epam.mjc.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileReader {

    public Profile getDataFromFile(File file) {
        Profile profile = new Profile();

        try (RandomAccessFile aFile = new RandomAccessFile(file, "r");
             FileChannel readChannel = aFile.getChannel()) {

            ByteBuffer buffer = ByteBuffer.allocate(1024);

            while (readChannel.read(buffer) > 0) {
                buffer.flip();
                profile.setName(getClearInfo(buffer));
                profile.setAge(Integer.valueOf(getClearInfo(buffer)));
                profile.setEmail(getClearInfo(buffer));
                profile.setPhone(Long.valueOf(getClearInfo(buffer)));
                buffer.clear();
            }

        } catch (IOException e) {
            return null;
        }

        return profile;
    }

    private String getClearInfo(ByteBuffer readBuffer) {
        StringBuilder info = new StringBuilder();
        int c;
        while ((c = readBuffer.get()) != '\n') {
            info.append((char) c);
        }

        info = new StringBuilder(info.substring(info.toString().lastIndexOf(':') + 1).trim());
        return info.toString();
    }
}
