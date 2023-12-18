package ru.job4j.cars.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.PhotoDto;
import ru.job4j.cars.model.Photo;
import ru.job4j.cars.repository.PhotoRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Service
public class SimplePhotoService implements PhotoService {

    private final PhotoRepository photoRepository;

    private final String storageDirectory;

    public SimplePhotoService(PhotoRepository photoRepository,
                              @Value("${file.directory}") String storageDirectory) {
        this.photoRepository = photoRepository;
        this.storageDirectory = storageDirectory;
        createStorageDirectory(storageDirectory);
    }

    private void createStorageDirectory(String path) {
        try {
            Files.createDirectories(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Photo save(PhotoDto photoDto) {
        var path = getNewFilePath(photoDto.getPath());
        writeFileBytes(path, photoDto.getContent());
        Photo photo = new Photo();
        photo.setPath(path);
        return photoRepository.save(photo);
    }

    private String getNewFilePath(String sourceName) {
        return storageDirectory + File.separator + UUID.randomUUID() + sourceName;
    }

    private void writeFileBytes(String path, byte[] content) {
        try {
            Files.write(Path.of(path), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<PhotoDto> findById(int id) {
        var optionalPhoto = photoRepository.findById(id);
        if (optionalPhoto.isEmpty()) {
            return Optional.empty();
        }
        var content = readFileAsBytes(optionalPhoto.get().getPath());
        return Optional.of(new PhotoDto(optionalPhoto.get().getPath(), content));
    }

    private byte[] readFileAsBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        var optionalPhoto = photoRepository.findById(id);
        if (optionalPhoto.isPresent()) {
            deleteFile(optionalPhoto.get().getPath());
            photoRepository.delete(id);
        }
    }

    private void deleteFile(String path) {
        try {
            Files.deleteIfExists(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
