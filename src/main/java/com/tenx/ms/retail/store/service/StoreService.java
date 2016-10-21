package com.tenx.ms.retail.store.service;
import com.tenx.ms.commons.util.converter.EntityConverter;
import com.tenx.ms.retail.store.domain.StoreEntity;
import com.tenx.ms.retail.store.repository.StoreRepository;
import com.tenx.ms.retail.store.rest.dto.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;

    private final static EntityConverter<Store, StoreEntity> STORECONVERTER = new EntityConverter<>(Store.class, StoreEntity.class);

    @Transactional
    public Long createStore(Store store) {
        StoreEntity storeEntity = STORECONVERTER.toT2(store);
        storeRepository.save(storeEntity);
        return storeEntity.getStoreId();
    }

    public Optional<Store> findStoreById(Long storeId) {
        return storeRepository.findOneByStoreId(storeId).map(STORECONVERTER::toT1);
    }

    public List<Store> findAllStores(Optional<String> name) {
        List<StoreEntity> storeEntities;

        if (name.isPresent()) {
            storeEntities = storeRepository.findByName(name.get());
        } else {
            storeEntities = storeRepository.findAll();
        }

        return storeEntities.stream().map(STORECONVERTER::toT1).collect(Collectors.toList());
    }
}
