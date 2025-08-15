package com.projetolivraria.livraria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.projetolivraria.livraria.model.Errand;
import com.projetolivraria.livraria.repository.ErrandRepository;

@ExtendWith(MockitoExtension.class)
public class Test_ErrandService {

    @Mock
    private ErrandRepository errandRepository;

    @InjectMocks
    private ErrandService errandService;

    Errand errand1;
    Errand errand2;

    @BeforeEach
    void setup(){
        errand1 = new Errand();
        errand1.setCode(1l);
        errand1.setName("John Green");
        errand1.setEmail("john@gmail.com");
        errand1.setPhone("99 9 9999-9999");
        errand1.setMessage("Hello World");
        errand2 = new Errand();
        errand2.setCode(2l);
        errand2.setName("Matheus");
        errand2.setEmail("matheus@gmail.com");
        errand2.setPhone("12 3 4567-8912");
        errand2.setMessage("Olá mundo");
    }

    @Test
    @DisplayName("Should save Errand")
    void testSaveErrandMethod(){

        when(errandRepository.save(any(Errand.class))).thenReturn(errand1);

        Errand saved = errandService.save(errand1);

        assertEquals("John Green", saved.getName());
        assertEquals(1l, saved.getCode());
    }

    @Test
    @DisplayName("Should return Errand by id")
    void testFindErrandById(){
        when(errandRepository.findById(1l)).thenReturn(Optional.of(errand1));

        Errand result = errandService.findById(1l);

        assertEquals("John Green", result.getName());
    }

    @Test
    @DisplayName("Should return all errands")
    void testFindAllErrands(){
        List<Errand> list = Arrays.asList(errand1,errand2);
        when(errandRepository.findAll()).thenReturn(list);

        List<Errand> result = errandService.getAll();

        assertEquals(list.size(), result.size());
        assertEquals("Matheus", result.get(1).getName());
    }

    @Test
    @DisplayName("Should delete errand by id")
    void testDeleteErrand(){

        Errand mockErrand = new Errand();
        mockErrand.setCode(4L);
        mockErrand.setName("Ana");
        mockErrand.setEmail("Ana@gmail.com");
        mockErrand.setPhone("(47) 9 1265-8753");

        when(errandRepository.findById(4L)).thenReturn(Optional.of(mockErrand));
        doNothing().when(errandRepository).deleteById(4L);

        errandService.deleteErrandById(4L);

        verify(errandRepository, times(1)).deleteById(4L);

    }
}
