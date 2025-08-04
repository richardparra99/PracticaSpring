package cursoSpringBoot.controllers;

import cursoSpringBoot.configurations.ExternalizedConfigurations;
import cursoSpringBoot.domain.Producto;
import cursoSpringBoot.service.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    @Lazy
    //@Qualifier("jsonRecursoServicio")
    private ProductoServicio productosServicio;

    @Autowired
    private ExternalizedConfigurations externalizedConfigurations;
    @GetMapping
    public ResponseEntity<?> getProducto(){
        System.out.println(externalizedConfigurations.toString());
        List<Producto> productos = productosServicio.getProductos();
        return ResponseEntity.ok(productos);
    }
}
