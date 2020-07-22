package com.tzwjkl.bowl.autoindex.service;

import com.tzwjkl.bowl.autoindex.entity.FileEntry;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

@Component
public class LocalService {

    private final Path workingPath = FileSystems.getDefault().getPath("").toAbsolutePath();
    private final Path webBindRoot = Path.of("/");

    public List<FileEntry> access(String requestUri) throws IOException {
        Path relativeCurrentPath = webBindRoot.relativize(Path.of(requestUri));
        Path currentPath = workingPath.resolve(relativeCurrentPath);
        if (Files.isDirectory(currentPath)) {
            return listDirectory(currentPath);
        }
        return null;
    }

    private List<FileEntry> listDirectory(Path currentPath) throws IOException {
        List<FileEntry> list = new LinkedList<>(){{
            add(new FileEntry("..", "..", null, null));
        }};
        Files
                .list(currentPath)
                .parallel()
                .map(this::parseEntry)
                .forEach(list::add);
        return list;
    }

    private FileEntry parseEntry(Path entryPath) {
        try {
            return new FileEntry(
                    webBindRoot.resolve(workingPath.relativize(entryPath)).normalize().toString(),
                    entryPath.getFileName().toString(),
                    Files.getLastModifiedTime(entryPath).toMillis(),
                    Files.size(entryPath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
