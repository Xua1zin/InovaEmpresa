package com.Atividade.InovaEmpresa.Controllers;

import com.Atividade.InovaEmpresa.Services.IdeiaService;
import com.Atividade.InovaEmpresa.entities.IdeiaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ideia")
public class IdeiaController {
    @Autowired
    private IdeiaService ideiaService;

    @PostMapping("/save/{id}")
    public ResponseEntity<IdeiaEntity> save(@RequestBody IdeiaEntity ideiaEntity, @PathVariable Long id){
        try{
            return ResponseEntity.ok(ideiaService.save(ideiaEntity, id));
        } catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        try{
            return ResponseEntity.ok(ideiaService.delete(id));
        } catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<IdeiaEntity> update(@RequestBody IdeiaEntity ideiaEntity, @PathVariable Long id){
        try {
            return ResponseEntity.ok(ideiaService.update(ideiaEntity, id));
        } catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<IdeiaEntity>> findAll(){
        try{
            return ResponseEntity.ok(ideiaService.findAll());
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<IdeiaEntity> findById(@PathVariable Long id){
        try{
            return ResponseEntity.ok(ideiaService.findById(id));
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/addColaboradores/{id}")
    public ResponseEntity<IdeiaEntity> addColaboradores(@PathVariable Long ideiaId, @RequestBody List<Long> UsuariosId) {
        try {
            return ResponseEntity.ok(ideiaService.addColaboradores(ideiaId, UsuariosId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
