package cursoSpringBoot.controllers;

import cursoSpringBoot.domain.Producto;
import cursoSpringBoot.service.ProductoServicio;
import cursoSpringBoot.service.ProductosServicioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    //ProductoServicio productosServicio = new ProductosServicioImpl();

    @Autowired
    @Qualifier("jsonRecursoServicio")
    private ProductoServicio productosServicio;

    @GetMapping
    public ResponseEntity<?> getProducto(){
        List<Producto> productos = productosServicio.getProductos();
        return ResponseEntity.ok(productos);
    }
}
