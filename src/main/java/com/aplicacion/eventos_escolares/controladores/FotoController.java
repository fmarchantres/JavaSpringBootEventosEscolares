package com.aplicacion.eventos_escolares.controladores;

import com.aplicacion.eventos_escolares.converter.FotoMapper;
import com.aplicacion.eventos_escolares.dto.FotoDTO;
import com.aplicacion.eventos_escolares.modelos.Foto;
import com.aplicacion.eventos_escolares.servicios.FotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/fotos")
@CrossOrigin(origins = "*")
public class FotoController {

    @Autowired
    private FotoService fotoService;

    @Autowired
    private FotoMapper fotoMapper;

    /**
     * MÉTODO PARA LA CÁMARA: Guarda la foto en la carpeta física 'uploads'
     */
    @PostMapping("/eventos/{id}/upload")
    public FotoDTO subirFotoFisica(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        try {
            String nombreCarpeta = "uploads";
            Path directorioImagenes = Paths.get(nombreCarpeta);

            if (!Files.exists(directorioImagenes)) {
                Files.createDirectories(directorioImagenes);
            }

            String nombreFinal = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path rutaCompleta = directorioImagenes.resolve(nombreFinal);
            Files.copy(file.getInputStream(), rutaCompleta);

            // CONFIGURACIÓN DEL DTO
            FotoDTO dto = new FotoDTO();
            dto.setUrl("/uploads/" + nombreFinal);
            dto.setDescripcion("Foto desde el móvil");
            dto.setEventoId(id);

            // ¡IMPORTANTE!: Asignamos un ID de usuario que exista en tu BD
            // Si no tienes un usuario con ID 1, cámbialo por uno que sí exista.
            dto.setUsuarioId(1);

            return fotoService.subirFotoAGaleria(id, dto);

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo: " + e.getMessage());
        }
    }

    /**
     * MÉTODO ORIGINAL: Para subir fotos mediante JSON
     */
    @PostMapping("/eventos/{id}/galeria")
    public FotoDTO subirFotoAGaleria(@PathVariable Integer id, @Valid @RequestBody FotoDTO dto) {
        return fotoService.subirFotoAGaleria(id, dto);
    }


    // --- CRUD ESTÁNDAR ---

    @GetMapping
    public List<Foto> listar() {
        return fotoService.listarTodas();
    }

    @GetMapping("/{id}")
    public Optional<Foto> obtenerPorId(@PathVariable Integer id) {
        return fotoService.buscarPorId(id);
    }

    @PostMapping
    public Foto crear(@RequestBody Foto foto) {
        return fotoService.guardar(foto);
    }

    @PutMapping("/{id}")
    public Foto actualizar(@PathVariable Integer id, @RequestBody Foto foto) {
        foto.setId(id);
        return fotoService.guardar(foto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        fotoService.eliminar(id);
    }
}