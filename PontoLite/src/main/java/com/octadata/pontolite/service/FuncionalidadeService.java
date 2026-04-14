package com.octadata.pontolite.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.octadata.pontolite.model.Funcionalidade;

@Service
public interface FuncionalidadeService {
    List<Funcionalidade> findAll();
}
