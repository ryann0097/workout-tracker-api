package com.workoutrack.WorkoutTracker.sql;

import com.workoutrack.WorkoutTracker.domain.treino.Exercicio;
import com.workoutrack.WorkoutTracker.repository.ExercicioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class SeederTest {
    @Autowired
    private ExercicioRepository exercicioRepository;

    @Test
    void testExerciciosSeeder() {
        List<Exercicio> lista = exercicioRepository.findAll();
        assertFalse(lista.isEmpty());
        lista.forEach(e -> System.out.println(e.getNome() + " - " + e.getCategoriaExercicio()));
    }
}
