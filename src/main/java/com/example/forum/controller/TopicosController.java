package com.example.forum.controller;

import com.example.forum.model.Topico;
import com.example.forum.model.TopicoForm;
import com.example.forum.repository.CursoRepository;
import com.example.forum.repository.TopicoRepository;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public List<TopicoDto> lista() {
        List<Topico> topicos = topicoRepository.findAll();
        System.out.println(topicos);
        return TopicoDto.converter(topicos);
    }

    //    Temos que avisar o Spring que os dados vao vir pelo corpo da requisicao
    @PostMapping
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form,
        UriComponentsBuilder uriBuilder) {
        Topico topico = form.converter(cursoRepository);
        topicoRepository.save(topico);
        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

//    public TopicoDto detalhar(@PathVariable Long id) {
    @GetMapping("/{id}")
        public TopicoDto detalhar(@PathVariable @NotNull @DecimalMin("0") Long id) {
        System.out.println("asdasdasdasdas");
        System.out.println(id);
        Topico byId = topicoRepository.getById(id);
        System.out.println(byId);
        return new TopicoDto(byId);
    }

}
