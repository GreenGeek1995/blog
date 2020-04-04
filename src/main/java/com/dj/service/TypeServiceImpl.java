package com.dj.service;

import com.dj.NotFoundException;
import com.dj.dao.TypeRespository;
import com.dj.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeRespository typeRespository;

    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRespository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRespository.getOne(id);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRespository.findAll(pageable);
    }

    @Override
    public List<Type> listType() {
        return typeRespository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Pageable pageable=PageRequest.of(0,size,Sort.by(Sort.Direction.DESC,"blogs.size"));
        return typeRespository.findTop(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typeRespository.getOne(id);
        if(t==null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type,t);
        return typeRespository.save(t);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRespository.deleteById(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRespository.findByName(name);
    }
}
