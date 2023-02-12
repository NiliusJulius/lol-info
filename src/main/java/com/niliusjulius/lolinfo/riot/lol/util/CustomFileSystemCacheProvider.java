package com.niliusjulius.lolinfo.riot.lol.util;

import com.niliusjulius.lolinfo.component.Messages;
import lombok.extern.slf4j.Slf4j;
import no.stelar7.api.r4j.basic.cache.CacheLifetimeHint;
import no.stelar7.api.r4j.basic.cache.CacheProvider;
import no.stelar7.api.r4j.basic.constants.api.URLEndpoint;
import no.stelar7.api.r4j.basic.utils.Utils;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class CustomFileSystemCacheProvider implements CacheProvider {

    private Path home = Paths.get(".", "l4j8cache").normalize();
    private CacheLifetimeHint hints = CacheLifetimeHint.DEFAULTS;
    final String FILENAME = "Data";

    public CustomFileSystemCacheProvider() {
    }

    @Override
    public void store(URLEndpoint type, Map<String, Object> obj) {
        try {
            if (!obj.containsKey("value")) {
                throw new RuntimeException(Messages.getMessage("cache.insert.invalid"));
            }

            List<Object> values = new ArrayList<>(obj.values());
            Object storeItem = obj.get("value");
            values.remove(storeItem);

            // if the object we are trying to store is not valid, don't store it.
            if (storeItem == null) {
                return;
            }

            values.add(FILENAME);

            Path storePath = resolvePath(type, values);
            Files.createDirectories(storePath.getParent());
            try {
                Files.createFile(storePath);
            } catch (FileAlreadyExistsException e) {
                // ignore it
            }
            writeObject(storePath, storeItem);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeObject(Path path, Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(obj);
        out.flush();

        Files.write(path, bos.toByteArray());

        bos.close();
    }


    private Path resolvePath(URLEndpoint type, List<Object> obj) {
        Path storePath = home.resolve(type.toString());

        List<Object> pathData = new ArrayList<>(obj);

        for (Object datum : pathData) {
            storePath = storePath.resolve(datum != null ? Utils.normalizeString(datum.toString()) : "null");
        }

        return storePath;
    }

    @Override
    public Optional<?> get(URLEndpoint type, Map<String, Object> obj) {
        List<Object> values = new ArrayList<>(obj.values());

        values.add(FILENAME);
        Path filepath = resolvePath(type, values);

        try {
            clearPath(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!Files.exists(filepath)) {
            return Optional.empty();
        }

        try (ByteArrayInputStream bis = new ByteArrayInputStream(Files.readAllBytes(filepath)); ObjectInputStream in = new ObjectInputStream(bis)) {
            log.debug(Messages.getMessage("cache.data.loaded"), this.getClass().getName(), type, values);
            Object o = in.readObject();

            // if the object we are trying to load is not valid, remove it
            if (o == null) {
                Files.deleteIfExists(filepath);
                return Optional.empty();
            }

            return Optional.of(o);

        } catch (IOException | ClassNotFoundException e) {
            log.error(Messages.getMessage("cache.data.reading.error"));
            return Optional.empty();
        }
    }

    private void clearPath(Path p) throws IOException {
        if (!Files.exists(p) || p.equals(home)) {
            return;
        }

        LocalDateTime lastModifiedTime = Files.getLastModifiedTime(p).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime nowTime = LocalDateTime.now();
        long life = Duration.between(lastModifiedTime, nowTime).getSeconds() * 1000;
        Path folder = p;

        while (!folder.getParent().equals(home)) {
            folder = folder.getParent();
        }

        URLEndpoint endpoint = URLEndpoint.valueOf(folder.getFileName().toString());
        long expectedLife = hints.get(endpoint);

        if (expectedLife < life) {
            // no point in deleting the folders..
            if (Files.isDirectory(p)) {
                return;
            }

            log.debug(Messages.getMessage("cache.data.outdated"));
            Files.deleteIfExists(p);
        }
    }

    @Override
    public long getTimeToLive(URLEndpoint type) {
        return hints.get(type);
    }

    @Override
    public void setTimeToLive(CacheLifetimeHint hints) {
        this.hints = hints;
    }

    public void setHome(Path home) {
        this.home = home;
    }

    @Override
    public void setTimeToLiveGlobal(long timeToLive) {
        // Not implemented
        throw new UnsupportedOperationException();
    }

    @Override
    public long getSize(URLEndpoint type, Map<String, Object> filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clearOldCache() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear(URLEndpoint type, Map<String, Object> filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(URLEndpoint type, Map<String, Object> obj) {
        throw new UnsupportedOperationException();
    }
}
