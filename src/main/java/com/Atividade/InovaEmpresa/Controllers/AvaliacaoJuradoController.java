package com.Atividade.InovaEmpresa.Controllers;

import com.Atividade.InovaEmpresa.Services.AvaliacaoJuradoService;
import com.Atividade.InovaEmpresa.entities.AvaliacaoJuradoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacaoJurado")
public class AvaliacaoJuradoController {
    @Autowired
    private AvaliacaoJuradoService avaliacaoJuradoService;

    @GetMapping("/nota/{id}")
    public ResponseEntity<Double> verNota(@PathVariable Long id){
        try{
            return ResponseEntity.ok(avaliacaoJuradoService.verNota(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/save/{ideiaId}/{usuarioId}")
    public ResponseEntity<AvaliacaoJuradoEntity> save(@PathVariable Long ideiaId, @PathVariable Long usuarioId, @RequestParam Double nota) {
        try {
            return ResponseEntity.ok(avaliacaoJuradoService.save(ideiaId, usuarioId, nota));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<AvaliacaoJuradoEntity>> findAll(){
        try{
            return ResponseEntity.ok(avaliacaoJuradoService.findAll());
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<AvaliacaoJuradoEntity> findById(@PathVariable Long id){
        try{
            return ResponseEntity.ok(avaliacaoJuradoService.findById(id));
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
