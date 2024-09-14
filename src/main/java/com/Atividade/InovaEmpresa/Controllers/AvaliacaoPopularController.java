package com.Atividade.InovaEmpresa.Controllers;

import com.Atividade.InovaEmpresa.Services.AvaliacaoPopularService;
import com.Atividade.InovaEmpresa.entities.AvaliacaoPopularEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacaoPopular")
public class AvaliacaoPopularController {
    @Autowired
    private AvaliacaoPopularService avaliacaoPopularService;

    @PostMapping("/save")
    public ResponseEntity<AvaliacaoPopularEntity> save(@RequestBody AvaliacaoPopularEntity avaliacaoPopularEntity){
        try{
            return ResponseEntity.ok(avaliacaoPopularService.save(avaliacaoPopularEntity));
        } catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        try{
            return ResponseEntity.ok(avaliacaoPopularService.delete(id));
        } catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AvaliacaoPopularEntity> update(@RequestBody AvaliacaoPopularEntity avaliacaoPopularEntity, @PathVariable Long id){
        try {
            return ResponseEntity.ok(avaliacaoPopularService.update(avaliacaoPopularEntity, id));
        } catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<AvaliacaoPopularEntity>> findAll(){
        try{
            return ResponseEntity.ok(avaliacaoPopularService.findAll());
        } catch (Exception e){
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
