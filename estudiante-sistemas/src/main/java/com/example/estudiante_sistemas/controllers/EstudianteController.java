package com.example.estudiante_sistemas.controllers;

import com.example.estudiante_sistemas.domain.Estudiante;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {
    private List<Estudiante> estudiantes = new ArrayList<>(Arrays.asList(
            new Estudiante(1,"Juan stelo", "juan@gmail.com", 25, "Ing. fnanciera"),
            new Estudiante(2,"rodrigo pedriel", "rp@gmail.com", 18, "Ing. Industrial"),
            new Estudiante(3,"ricardo parra", "ricardop@gmail.com", 20, "Turismo"),
            new Estudiante(4, "richard parker", "rp2@gmail.com", 30, "Developer")
    ));

    @GetMapping
    public List<Estudiante> getEstudiantes(){
        return estudiantes;
    }


    @GetMapping("/{correo}")
    public Estudiante getEstudiante(@PathVariable String correo){
        for (Estudiante e: estudiantes){
            if (e.getCorreo().equalsIgnoreCase(correo)){
                return e;
            }
        }
        return null;
    }

    @PostMapping
    public Estudiante postEstudiante(@RequestBody Estudiante estudiante){
        estudiantes.add(estudiante);
        return estudiante;
    }

    @PutMapping
    public Estudiante putEstudiante(@RequestBody Estudiante estudiante){
        for (Estudiante e: estudiantes){
            if (e.getId() == estudiante.getId()){
                e.setNombre(estudiante.getNombre());
                e.setCorreo(estudiante.getCorreo());
                e.setEdad(estudiante.getEdad());
                e.setCurso(estudiante.getCurso());
                return e;
            }
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public Estudiante deleteEstudiante(@PathVariable int id){
        for (Estudiante e: estudiantes){
            if (e.getId() == id){
                estudiantes.remove(e);
                return e;
            }

        }
        return null;
    }

    @PatchMapping
    public Estudiante patchEstudiante(@RequestBody Estudiante estudiante){
        for (Estudiante e: estudiantes){
            if (e.getId() == estudiante.getId()){
                if (estudiante.getNombre() != null){
                    e.setNombre(estudiante.getNombre());
                }
                if (estudiante.getCorreo() != null){
                    e.setCorreo(estudiante.getCorreo());
                }
                if (estudiante.getEdad() != 0){
                    e.setEdad(estudiante.getEdad());
                }
                if (estudiante.getCurso() != null){
                    e.setCurso(estudiante.getCurso());
                }
                return e;
            }
        }
        return null;
    }
}
