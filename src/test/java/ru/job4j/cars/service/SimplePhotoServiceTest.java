package ru.job4j.cars.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;
import ru.job4j.cars.dto.PhotoDto;
import ru.job4j.cars.model.Photo;
import ru.job4j.cars.repository.PhotoRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimplePhotoServiceTest {

    private PhotoService photoService;

    private PhotoRepository photoRepository;

    @TempDir
    private Path storageDirectory;

    private File testFile;

    @BeforeEach
    public void init() throws IOException {
        photoRepository = mock(PhotoRepository.class);
        photoService = new SimplePhotoService(photoRepository, storageDirectory.toString());
        testFile = Files.createTempFile(storageDirectory, "testFile", ".test").toFile();
    }

    @Test
    public void testSaveMethod() {
        byte[] content = "content".getBytes();
        String path = "testPath.test";
        PhotoDto photoDto = new PhotoDto(path, content);

        Photo photo = new Photo();
        photo.setPath(storageDirectory + "testPath.test");

        var photoCaptor = ArgumentCaptor.forClass(Photo.class);

        when(photoRepository.save(photoCaptor.capture())).thenReturn(photo);

        var rsl = photoService.save(photoDto);
        var actualPhoto = photoCaptor.getValue();
        var actualPath = actualPhoto.getPath();

        assertThat(actualPath).startsWith(storageDirectory.toString());
        assertThat(actualPath).endsWith(path);
        assertThat(rsl).isEqualTo(photo);
    }

    @Test
    public void testFindByIdMethod() {
        String path = testFile.getPath();

        Photo photo = new Photo();
        photo.setId(1);
        photo.setPath(path);

        byte[] content = new byte[0];
        PhotoDto expectedPhotoDto = new PhotoDto(path, content);

        when(photoRepository.findById(anyInt())).thenReturn(Optional.of(photo));

        var rsl = photoService.findById(1).get();

        assertThat(rsl.getContent()).isEqualTo(content);
        assertThat(rsl.getPath()).isEqualTo(path);
    }

    @Test
    public void testDeleteMethod() {
        String path = testFile.getPath();

        Photo photo = new Photo();
        photo.setId(1);
        photo.setPath(path);

        when(photoRepository.findById(anyInt())).thenReturn(Optional.of(photo));

        var beforeDelete = Files.exists(Path.of(path));
        photoService.delete(1);
        var afterDelete = Files.exists(Path.of(path));

        assertThat(beforeDelete).isTrue();
        assertThat(afterDelete).isFalse();
    }

}