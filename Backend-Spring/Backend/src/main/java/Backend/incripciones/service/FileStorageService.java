package Backend.incripciones.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {
    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    public SavedFile saveComprobante(MultipartFile file, Long inscripcionId) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Archivo vacío");
        }

        String contentType = file.getContentType() == null ? "" : file.getContentType().toLowerCase();
        // Acepta imágenes y PDF
        boolean okType = contentType.contains("pdf") || contentType.contains("image");
        if (!okType) {
            throw new RuntimeException("Tipo de archivo no permitido. Sube PDF o imagen.");
        }

        String original = file.getOriginalFilename() == null ? "comprobante" : file.getOriginalFilename();
        String ext = getExt(original);

        String safeName = "insc_" + inscripcionId + "_" + UUID.randomUUID().toString().replace("-", "") + ext;

        try {
            Path dir = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(dir);

            Path target = dir.resolve(safeName);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            return new SavedFile(target.toString(), original, safeName);

        } catch (IOException e) {
            throw new RuntimeException("Error guardando archivo: " + e.getMessage());
        }
    }

    private String getExt(String name) {
        int i = name.lastIndexOf('.');
        if (i < 0) return "";
        String ext = name.substring(i).toLowerCase();
        // lista básica segura
        if (ext.equals(".pdf") || ext.equals(".png") || ext.equals(".jpg") || ext.equals(".jpeg")) return ext;
        return "";
    }

    public record SavedFile(String absolutePath, String originalName, String storedName) {}
}
