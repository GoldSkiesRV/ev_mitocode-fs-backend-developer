package com.mitocode.service.impl;

import com.mitocode.model.MediaFile;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IMediaFileRepo;
import com.mitocode.service.IMediaFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MediaFileServiceImpl extends CRUDImpl<MediaFile, Integer> implements IMediaFileService {

    private final IMediaFileRepo repo;

    @Override
    protected IGenericRepo<MediaFile, Integer> getRepo() {
        return repo;
    }
}
