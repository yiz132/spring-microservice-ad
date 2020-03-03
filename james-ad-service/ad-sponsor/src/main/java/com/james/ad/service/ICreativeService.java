package com.james.ad.service;

import com.james.ad.vo.CreativeRequest;
import com.james.ad.vo.CreativeResponse;

public interface ICreativeService {
    CreativeResponse createCreative(CreativeRequest request);
}
