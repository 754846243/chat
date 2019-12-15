package iscyf.chatroom.service.impl;

import iscyf.chatroom.entity.Impression;
import iscyf.chatroom.repository.ImpressionRepository;
import iscyf.chatroom.service.ImpressionService;
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
