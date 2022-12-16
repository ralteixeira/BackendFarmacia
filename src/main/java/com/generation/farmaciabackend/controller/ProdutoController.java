package com.generation.farmaciabackend.controller;

import com.generation.farmaciabackend.model.Produto;
import com.generation.farmaciabackend.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/produto")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {


    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping                             //getAll
    public ResponseEntity<List<Produto>> ListarTodosOsProdutos() {
        return ResponseEntity.ok(produtoRepository.findAll());
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Produto>> BuscarPorNome(@PathVariable String nome) {
        if (nome.equals(nome)) {
            return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCase(nome));
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("maior/{preco}")
    public ResponseEntity<List<Produto>> listarPorPrecoMaiorQue(@PathVariable double preco){
        return ResponseEntity.ok(produtoRepository.findByPrecoGreaterThanOrderByPreco(preco));
    }

    @GetMapping("menor/{preco}")
    public ResponseEntity<List<Produto>> ListarPorMenorQue (@PathVariable double preco){
        return ResponseEntity.ok(produtoRepository.findByPrecoLessThanOrderByPrecoDesc(preco));
    }

    @PostMapping
    public ResponseEntity<Produto> CriarProduto(@Valid @RequestBody Produto produto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));
    }

    @PutMapping
    public ResponseEntity<Produto> AtualizarProduto(@Valid @RequestBody Produto produto) {
        if(produto.getId() == null){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(produtoRepository.save(produto));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> DeletarProduto(@PathVariable Long id) {
        try {
            produtoRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
