package com.niliusjulius.lolinfo.riot.lol.util;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import com.niliusjulius.lolinfo.component.Messages;
import no.stelar7.api.r4j.basic.constants.api.URLEndpoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CustomFileSystemCacheProviderTest {

    @InjectMocks
    private CustomFileSystemCacheProvider cacheProvider;

    @Nested
    @DisplayName("CustomFileSystemCacheProvider")
    public class FileSystemOperationTests {
        private FileSystem fs;

        private final String homeDir = "home";
        private final String testDir1 = "TestDir1";
        private final String testDir2 = "TestDir2";
        private final String testValue = "TestValue";

        @BeforeEach
        public void init() {
            fs = Jimfs.newFileSystem(Configuration.unix());
            cacheProvider.setHome(fs.getPath(homeDir));
        }

        @Nested
        @DisplayName("store should")
        public class storeTests {

            @Test
            @DisplayName("successfully create a new file with the given data.")
            public void storeSuccessTest() throws IOException, ClassNotFoundException {
                Map<String, Object> testMap = new LinkedHashMap<>();
                testMap.put("doesNotMatter", testDir1);
                testMap.put("canBeAnything", testDir2);
                testMap.put("value", testValue);

                cacheProvider.store(URLEndpoint.V4_LEAGUE, testMap);

                Path testFilePath = fs.getPath(homeDir)
                        .resolve(URLEndpoint.V4_LEAGUE.toString())
                        .resolve(testDir1)
                        .resolve(testDir2)
                        .resolve(cacheProvider.FILENAME);

                assertTrue(Files.exists(testFilePath), "Cannot find file which should have been created.");
                assertEquals(testValue, retrieveFileObject(testFilePath).toString(), "File has incorrect data inside.");
            }

            @Test
            @DisplayName("not create a new file when an invalid value is given.")
            public void storeInvalidValueTest() {
                Map<String, Object> testMap = new LinkedHashMap<>();
                testMap.put("doesNotMatter", testDir1);
                testMap.put("canBeAnything", testDir2);
                testMap.put("value", null);

                cacheProvider.store(URLEndpoint.V4_LEAGUE, testMap);

                Path testFilePath = fs.getPath(homeDir)
                        .resolve(URLEndpoint.V4_LEAGUE.toString())
                        .resolve(testDir1)
                        .resolve(testDir2)
                        .resolve(cacheProvider.FILENAME);

                assertFalse(Files.exists(testFilePath), "No file should have been created with an invalid/empty value.");
            }

            @Test
            @DisplayName("ignore an existing file with the same name and continue to overwrite it.")
            public void storeFileAlreadyExistsTest() throws IOException, ClassNotFoundException {
                Map<String, Object> testMap = new LinkedHashMap<>();
                testMap.put("doesNotMatter", testDir1);
                testMap.put("canBeAnything", testDir2);
                testMap.put("value", testValue);

                Path testFilePath = fs.getPath(homeDir)
                        .resolve(URLEndpoint.V4_LEAGUE.toString())
                        .resolve(testDir1)
                        .resolve(testDir2)
                        .resolve(cacheProvider.FILENAME);

                Files.createDirectories(testFilePath.getParent());
                Files.createFile(testFilePath);

                cacheProvider.store(URLEndpoint.V4_LEAGUE, testMap);

                assertTrue(Files.exists(testFilePath), "Cannot find file which should have been created.");
                assertEquals(testValue, retrieveFileObject(testFilePath).toString(),
                        "File has incorrect data inside and should have been overwritten with the correct data.");
            }

            @Test
            @DisplayName("return a RunTimeException when value is missing.")
            public void storeMissingValueTest() {
                Map<String, Object> testMap = new LinkedHashMap<>();
                testMap.put("doesn'tMatter", testDir1);
                testMap.put("canBeAnything", testDir2);

                RuntimeException re = assertThrows(RuntimeException.class,
                        () -> cacheProvider.store(URLEndpoint.V4_LEAGUE, testMap),
                        "Expected a RunTimeException when the 'value' key is missing.");
                assertEquals(Messages.getMessage("cache.insert.invalid"), re.getMessage(),
                        "Exception message is not as expected.");
            }
        }

        @Nested
        @DisplayName("get should")
        public class getTests {

            @Test
            @DisplayName("successfully return the object from a file.")
            public void getSuccessTest() throws IOException {
                Map<String, Object> testMap = new LinkedHashMap<>();
                testMap.put("doesNotMatter", testDir1);
                testMap.put("canBeAnything", testDir2);

                Path testFilePath = fs.getPath(homeDir)
                        .resolve(URLEndpoint.V4_LEAGUE.toString())
                        .resolve(testDir1)
                        .resolve(testDir2)
                        .resolve(cacheProvider.FILENAME);

                Files.createDirectories(testFilePath.getParent());
                Files.createFile(testFilePath);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutput out = new ObjectOutputStream(bos);
                out.writeObject(testValue);
                out.flush();
                Files.write(testFilePath, bos.toByteArray());
                bos.close();

                Optional<?> resultValue = cacheProvider.get(URLEndpoint.V4_LEAGUE, testMap);

                assertTrue(Files.exists(testFilePath), "Cannot find file which should have only been read.");
                assertTrue(resultValue.isPresent(), "The result should contain a present optional.");
                assertEquals(testValue, resultValue.get(), "File has incorrect data inside.");
            }

            @Test
            @DisplayName("successfully delete the cache file if the data is old.")
            public void getDeleteOldCacheTest() throws IOException {
                Map<String, Object> testMap = new LinkedHashMap<>();
                testMap.put("doesNotMatter", testDir1);
                testMap.put("canBeAnything", testDir2);

                Path testFilePath = fs.getPath(homeDir)
                        .resolve(URLEndpoint.V4_LEAGUE.toString())
                        .resolve(testDir1)
                        .resolve(testDir2)
                        .resolve(cacheProvider.FILENAME);

                Files.createDirectories(testFilePath.getParent());
                Files.createFile(testFilePath);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutput out = new ObjectOutputStream(bos);
                out.writeObject(testValue);
                out.flush();
                Files.write(testFilePath, bos.toByteArray());
                bos.close();

                Instant fileTimeInstant = Files.getLastModifiedTime(testFilePath).toInstant().minusMillis(TimeUnit.DAYS.toMillis(1));
                Files.setAttribute(testFilePath, "lastModifiedTime", FileTime.from(fileTimeInstant));

                cacheProvider.get(URLEndpoint.V4_LEAGUE, testMap);

                assertFalse(Files.exists(testFilePath), "File should have been removed since it was outdated.");
            }

            @Test
            @DisplayName("return an empty optional when the file does not exist.")
            public void getFileDoesNotExistTest() {
                Map<String, Object> testMap = new LinkedHashMap<>();
                testMap.put("doesNotMatter", testDir1);
                testMap.put("canBeAnything", testDir2);

                assertFalse(cacheProvider.get(URLEndpoint.V4_LEAGUE, testMap).isPresent(),
                        "No file exists so the returned optional should be empty.");
            }

            @Test
            @DisplayName("return an empty optional when the file does not contain a valid object.")
            public void getInvalidObjectTest() throws IOException {
                Map<String, Object> testMap = new LinkedHashMap<>();
                testMap.put("doesNotMatter", testDir1);
                testMap.put("canBeAnything", testDir2);

                Path testFilePath = fs.getPath(homeDir)
                        .resolve(URLEndpoint.V4_LEAGUE.toString())
                        .resolve(testDir1)
                        .resolve(testDir2)
                        .resolve(cacheProvider.FILENAME);

                Files.createDirectories(testFilePath.getParent());
                Files.createFile(testFilePath);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutput out = new ObjectOutputStream(bos);
                out.writeObject(null);
                out.flush();
                Files.write(testFilePath, bos.toByteArray());
                bos.close();

                assertFalse(cacheProvider.get(URLEndpoint.V4_LEAGUE, testMap).isPresent(),
                        "The file had an invalid object so the returned optional should be empty.");

                assertFalse(Files.exists(testFilePath), "The file had an invalid object so should have been deleted.");
            }

            @Test
            @DisplayName("return an empty optional when a file exception occurs.")
            public void getFileExceptionTest() throws IOException {
                Map<String, Object> testMap = new LinkedHashMap<>();
                testMap.put("doesNotMatter", testDir1);
                testMap.put("canBeAnything", testDir2);

                Path testFilePath = fs.getPath(homeDir)
                        .resolve(URLEndpoint.V4_LEAGUE.toString())
                        .resolve(testDir1)
                        .resolve(testDir2)
                        .resolve(cacheProvider.FILENAME);

                Files.createDirectories(testFilePath.getParent());
                Files.createFile(testFilePath);
                Files.writeString(testFilePath, "test");

                assertFalse(cacheProvider.get(URLEndpoint.V4_LEAGUE, testMap).isPresent(),
                        "Reading the file led to an exception so the returned optional should be empty.");

                assertTrue(Files.exists(testFilePath),
                        "Reading the file led to an exception so the file should still be there.");
            }
        }

        private Object retrieveFileObject(Path filePath) throws IOException, ClassNotFoundException {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Files.readAllBytes(filePath));
            ObjectInput objectInput = new ObjectInputStream(byteArrayInputStream);
            return objectInput.readObject();
        }
    }

    @Test
    @DisplayName("should return an UnsupportedOperationException")
    public void timeToLiveGlobalTest() {
        assertThrows(UnsupportedOperationException.class, () -> cacheProvider.setTimeToLiveGlobal(1),
                "Method is not implemented and should return UnsupportedOperationException");
    }

    @Test
    @DisplayName("should return an UnsupportedOperationException")
    public void getSizeTest() {
        assertThrows(UnsupportedOperationException.class, () -> cacheProvider.getSize(null, null),
                "Method is not implemented and should return UnsupportedOperationException");
    }

    @Test
    @DisplayName("should return an UnsupportedOperationException")
    public void ClearOldCacheTest() {
        assertThrows(UnsupportedOperationException.class, () -> cacheProvider.clearOldCache(),
                "Method is not implemented and should return UnsupportedOperationException");
    }

    @Test
    @DisplayName("should return an UnsupportedOperationException")
    public void clearTest() {
        assertThrows(UnsupportedOperationException.class, () -> cacheProvider.clear(null, null),
                "Method is not implemented and should return UnsupportedOperationException");
    }

    @Test
    @DisplayName("should return an UnsupportedOperationException")
    public void updateTest() {
        assertThrows(UnsupportedOperationException.class, () -> cacheProvider.update(null, null),
                "Method is not implemented and should return UnsupportedOperationException");
    }
}
