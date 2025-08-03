package cursoSpringBoot.service;

import cursoSpringBoot.domain.Producto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("listasRecursoServicio")
public class ProductosServicioImpl implements ProductoServicio {
    List<Producto> productos = new ArrayList<>(Arrays.asList(
            new Producto(1, "Laptop asus gamer rog", 799.99, 5),
            new Producto(2, "Samsung Galaxy 25", 600.66,10),
            new Producto(3, "Iphone 16 pro Max", 780.00, 2),
            new Producto(4, "Nike Air For One", 500.50,25)
    ));


    @Override
    public List<Producto> getProductos() {
        return productos;
    }
}
