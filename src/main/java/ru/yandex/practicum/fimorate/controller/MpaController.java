package ru.yandex.practicum.fimorate.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.fimorate.model.Mpa;
import ru.yandex.practicum.fimorate.service.MpaService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mpa")
public class MpaController {

    private final MpaService mpaService;

    @GetMapping
    public ResponseEntity<Collection<Mpa>> findAll() {
        Collection<Mpa> ratingMpas = mpaService.findAll();
        return ResponseEntity.ok().body(ratingMpas );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mpa> getById(@PathVariable Long id) {
        Mpa ratingMpa = mpaService.getById(id);
        return ResponseEntity.ok().body(ratingMpa);
    }

}
