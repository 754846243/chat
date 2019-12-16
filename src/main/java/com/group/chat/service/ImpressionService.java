package com.group.chat.service;

import com.group.chat.entity.Impression;

import java.util.List;

public interface ImpressionService {

    public List<Impression> findAllByUid (Integer uid);

    public Impression findOneById (Integer id);

    public Impression save (Impression impression);

    public boolean delete (Integer id);

}
