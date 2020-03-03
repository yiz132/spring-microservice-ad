package com.james.ad.service.impl;

import com.james.ad.dao.CreativeRepository;
import com.james.ad.entity.Creative;
import com.james.ad.service.ICreativeService;
import com.james.ad.vo.CreativeRequest;
import com.james.ad.vo.CreativeResponse;
import org.springframework.stereotype.Service;

@Service
public class CreativeServiceImpl implements ICreativeService {

    private final CreativeRepository creativeRepository;

    public CreativeServiceImpl(CreativeRepository creativeRepository) {
        this.creativeRepository = creativeRepository;
    }

    @Override
    public CreativeResponse createCreative(CreativeRequest request) {
        Creative creative = creativeRepository.save(request.convertToEntity());
        return new CreativeResponse(creative.getId(),creative.getName());
    }
}
