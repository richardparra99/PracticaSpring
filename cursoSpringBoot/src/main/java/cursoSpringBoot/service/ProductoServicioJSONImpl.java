package cursoSpringBoot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cursoSpringBoot.domain.Producto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service("jsonRecursoServicio")
public class ProductoServicioJSONImpl implements ProductoServicio{

    @Override
    public List<Producto> getProductos() {
        List<Producto> productos;
        try {
            productos = new ObjectMapper()
                    .readValue(this.getClass().getResourceAsStream("/productos.json"),
                            new TypeReference<List<Producto>>() {});
            return productos;
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
