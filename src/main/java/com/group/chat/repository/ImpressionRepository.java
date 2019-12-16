package com.group.chat.repository;

import com.group.chat.entity.Impression;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImpressionRepository extends CrudRepository <Impression, Object> {
    public List<Impression> findAllByUid (Integer uid);

    public void deleteById (Integer id);

    public Impression findById (Integer id);
}
