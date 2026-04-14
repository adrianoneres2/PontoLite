package com.octadata.pontolite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.octadata.pontolite.model.Funcionalidade;
import com.octadata.pontolite.repository.FuncionalidadeRepository;

@Service
public class FuncionalidadeServiceImpl implements FuncionalidadeService {

    @Autowired
    private FuncionalidadeRepository funcionalidadeRepository;

    @Override
    public List<Funcionalidade> findAll() {
        return funcionalidadeRepository.findAll();
    }
}
