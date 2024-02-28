package io.github.davileite.rest.controller;

import io.github.davileite.domain.entity.Produto;
import io.github.davileite.domain.repository.Produtos;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
@RestController
@RequestMapping("/api/produtos")
public class PodutoController {

    private Produtos reporisotry;

    public PodutoController(Produtos reporisotry) {
        this.reporisotry = reporisotry;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Produto save (@RequestBody @Valid Produto produto){
        return reporisotry.save(produto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody @Valid Produto produto){
        reporisotry.findById(id)
                .map(p -> {
                    produto.setId(p.getId());
                    reporisotry.save(produto);
                    return produto;
                }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontado"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Integer id){
        reporisotry.findById(id)
                .map(p -> {
                    reporisotry.delete(p);
                    return Void.TYPE;
                }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontado"));
    }

    @GetMapping("{id}")
    public Produto getByid(@PathVariable Integer id){
         return reporisotry.findById(id)
               .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontado"));
    }

    @GetMapping
    public List<Produto> find(Produto filtro){
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        return reporisotry.findAll(example);

    }

}
