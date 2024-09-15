package com.Atividade.InovaEmpresa.Controllers;

import com.Atividade.InovaEmpresa.Services.UsuarioService;
import com.Atividade.InovaEmpresa.entities.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/save")
    public ResponseEntity<UsuarioEntity> save(@RequestBody UsuarioEntity usuarioEntity){
        try{
            return ResponseEntity.ok(usuarioService.save(usuarioEntity));
        } catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        try{
            return ResponseEntity.ok(usuarioService.delete(id));
        } catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{id}/{id}")
    public ResponseEntity<UsuarioEntity> update(@RequestBody UsuarioEntity usuarioEntity, @PathVariable Long logadoId, @PathVariable Long id ){
        try {
            return ResponseEntity.ok(usuarioService.update(usuarioEntity, logadoId, id));
        } catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<UsuarioEntity>> findAll(){
        try{
            return ResponseEntity.ok(usuarioService.findAll());
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<UsuarioEntity> findById(@PathVariable Long id){
        try{
            return ResponseEntity.ok(usuarioService.findById(id));
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping("/addJurados/{id}")
    public ResponseEntity<List<UsuarioEntity>> addJurados(@RequestBody List<Long> usuariosId,@PathVariable Long logadoId){
        try{
            return ResponseEntity.ok(usuarioService.addJurados(usuariosId, logadoId));
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
