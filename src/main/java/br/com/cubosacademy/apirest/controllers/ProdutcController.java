package br.com.cubosacademy.apirest.controllers;

import br.com.cubosacademy.apirest.models.Product;
import br.com.cubosacademy.apirest.repositories.ProductRepository;
import br.com.cubosacademy.apirest.utils.ResponseHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProdutcController {
    @Autowired
    private ProductRepository productRepository;
//    exibir produtos;
    @GetMapping()
    public List<Product> list(){
    return productRepository.findAll();
    }

//    consultar apenas um produto;
    @GetMapping("/{id}")
    public ResponseEntity<Object> listById(@PathVariable Integer id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(value -> new ResponseEntity<Object>(value, HttpStatus.OK)).orElseGet(() ->
                ResponseHandle.generate("Produto não encotrado", HttpStatus.NOT_FOUND));
    }
//    cadastrar produto;
    @PostMapping()
    public ResponseEntity<Object> registerProduct(@RequestBody Product product){
        if(product.getName() == null){
            return ResponseHandle.generate("Nome do produto é obrigatório", HttpStatus.BAD_REQUEST);
    }
        if(product.getPrice() == null){
            return ResponseHandle.generate("Preço do produto é obrigatório", HttpStatus.BAD_REQUEST);
        }
            System.out.println(product.getClass().getName());
        Product newProduct = productRepository.save(product);
        return  new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }
//    editar um produto;
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@RequestBody Product product, @PathVariable Integer id){
        Optional<Product> oldProduct = productRepository.findById(id);
        if (oldProduct.isEmpty()){
            return ResponseHandle.generate("Produto não encontrado", HttpStatus.BAD_REQUEST);
        }
        Product productUpdated = oldProduct.get();

        if(product.getName() == null){
            productUpdated.setName(oldProduct.get().getName());
        }
        if(product.getPrice() == null){
            productUpdated.setName(String.valueOf(oldProduct.get().getPrice()));
        }
        if(product.getDescription() == null){
            productUpdated.setName(oldProduct.get().getDescription());
        }
        productUpdated.setName(product.getName());
        productUpdated.setPrice(product.getPrice());
        productUpdated.setDescription(product.getDescription());

        productRepository.save(productUpdated);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteProduct(@PathVariable Integer id) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isEmpty()) {
            return ResponseHandle.generate("Produto não encontrado", HttpStatus.BAD_REQUEST);
        }
        Product delteProduct = existingProduct.get();
        productRepository.delete(delteProduct);
        return ResponseEntity.noContent().build();
    }
//    excluir um produto;
}
