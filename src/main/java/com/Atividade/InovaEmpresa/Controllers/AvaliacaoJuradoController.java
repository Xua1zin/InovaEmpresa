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

    @PostMapping("/save")
    public ResponseEntity<AvaliacaoJuradoEntity> save(@RequestBody AvaliacaoJuradoEntity avaliacaoJuradoEntity){
        try{
            return ResponseEntity.ok(avaliacaoJuradoService.save(avaliacaoJuradoEntity));
        } catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        try{
            return ResponseEntity.ok(avaliacaoJuradoService.delete(id));
        } catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AvaliacaoJuradoEntity> update(@RequestBody AvaliacaoJuradoEntity avaliacaoJuradoEntity, @PathVariable Long id){
        try {
            return ResponseEntity.ok(avaliacaoJuradoService.update(avaliacaoJuradoEntity, id));
        } catch(Exception e){
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
