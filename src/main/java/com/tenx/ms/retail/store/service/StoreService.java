package com.tenx.ms.retail.store.service;

import com.tenx.ms.commons.util.converter.EntityConverter;
import com.tenx.ms.retail.store.domain.StoreEntity;
import com.tenx.ms.retail.store.repository.StoreRepository;
import com.tenx.ms.retail.store.rest.dto.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;

    private final static EntityConverter<Store, StoreEntity> storeConverter = new EntityConverter<>(Store.class, StoreEntity.class);

    public Long createStore(Store store) {
        StoreEntity storeEntity = storeConverter.toT2(store);
        storeRepository.save(storeEntity);
        return storeEntity.getStoreId();
    }

    public Optional<Store> findStoreById(Long storeId) {
        Optional<Store> store = storeRepository.findOneByStoreId(storeId).map(storeConverter::toT1);
        return store;
    }

    public List<Store> findAllStores() {
        List<StoreEntity> storeEntities = storeRepository.findAll();
        return storeEntities.stream().map(storeConverter::toT1).collect(Collectors.toList());
    }
}
