package com.Atividade.InovaEmpresa.Controllers;

import com.Atividade.InovaEmpresa.Services.EventoService;
import com.Atividade.InovaEmpresa.entities.EventoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evento")
public class EventoController {
    @Autowired
    private EventoService eventoService;

    @PutMapping("/distribuirIdeias")
    public ResponseEntity<EventoEntity> distribuicaoIdeiasParaJurados(){
        try{
            return ResponseEntity.ok(eventoService.distribuicaoIdeiasParaJurados());
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/save/{id}")
    public ResponseEntity<EventoEntity> save(@RequestBody EventoEntity eventoEntity, @PathVariable Long id){
        try{
            return ResponseEntity.ok(eventoService.save(eventoEntity, id));
        } catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    //adicionado
    @PutMapping("/addUsuarioEvento/{usuarioId}")
    public ResponseEntity<EventoEntity> save(@PathVariable Long usuarioId){
        try{
            return ResponseEntity.ok(eventoService.addUsuarioEvento(usuarioId));
        } catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        try{
            return ResponseEntity.ok(eventoService.delete(id));
        } catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<EventoEntity>> findAll(){
        try{
            return ResponseEntity.ok(eventoService.findAll());
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<EventoEntity> findById(@PathVariable Long id){
        try{
            return ResponseEntity.ok(eventoService.findById(id));
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
