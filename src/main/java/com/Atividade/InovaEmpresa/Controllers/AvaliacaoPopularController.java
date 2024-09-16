package com.Atividade.InovaEmpresa.Controllers;

import com.Atividade.InovaEmpresa.Services.AvaliacaoPopularService;
import com.Atividade.InovaEmpresa.entities.AvaliacaoPopularEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/voto")
public class AvaliacaoPopularController {
    @Autowired
    private AvaliacaoPopularService avaliacaoPopularService;

    @PostMapping("/votar")
    public ResponseEntity<AvaliacaoPopularEntity> votar(@RequestParam Long ideiaId, @RequestParam Long usuarioId) {
        try{
            return ResponseEntity.ok(avaliacaoPopularService.votar(ideiaId, usuarioId));
        } catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<AvaliacaoPopularEntity> findById(@PathVariable Long id){
        try{
            return ResponseEntity.ok(avaliacaoPopularService.findById(id));
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
