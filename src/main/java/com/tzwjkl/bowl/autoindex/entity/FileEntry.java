package com.tzwjkl.bowl.autoindex.entity;

public class FileEntry {
    private final String link;
    private final String name;
    private final Long lastModified;
    private final Long size;

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }

    public Long getLastModified() {
        return lastModified;
    }

    public Long getSize() {
        return size;
    }

    public FileEntry(String link, String name, Long lastModified, Long size) {
        this.link = link;
        this.name = name;
        this.lastModified = lastModified;
        this.size = size;
    }

    @Override
    public String toString() {
        return "FileEntry{" +
                "link='" + link +
                "', name='" + name +
                "', lastModified=" + lastModified +
                "', size=" + size +
                '}';
    }
}
