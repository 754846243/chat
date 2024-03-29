package com.group.chat.service.impl;

import com.group.chat.entity.Impression;
import com.group.chat.repository.ImpressionRepository;
import com.group.chat.service.ImpressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class ImpressionServiceImpl implements ImpressionService {

    @Autowired
    private ImpressionRepository impressionRepository;

    @Override
    public List<Impression> findAllByUid(Integer uid) {
        return impressionRepository.findAllByUid(uid);
    }

    @Override
    public Impression findOneById(Integer id) {
        return impressionRepository.findById(id);
    }

    @Override
    public Impression save(Impression impression) {
        return impressionRepository.save(impression);
    }

    @Override
    @Transactional
    public boolean delete(Integer id) {
        impressionRepository.deleteById(id);
        return true;
    }
}
