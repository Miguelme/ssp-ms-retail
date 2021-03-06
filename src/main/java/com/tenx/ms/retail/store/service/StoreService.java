package com.tenx.ms.retail.store.service;
import com.tenx.ms.commons.util.converter.EntityConverter;
import com.tenx.ms.retail.store.domain.StoreEntity;
import com.tenx.ms.retail.store.repository.StoreRepository;
import com.tenx.ms.retail.store.rest.dto.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
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

    public List<Store> findAllStores(Pageable pageable, Optional<String> name) {
        List<StoreEntity> storeEntities;

        if (name.isPresent()) {
            storeEntities = storeRepository.findByName(pageable, name.get()).getContent();
        } else {
            storeEntities = storeRepository.findAll(pageable).getContent();
        }

        return storeEntities.stream().map(STORECONVERTER::toT1).collect(Collectors.toList());
    }

    public void deleteStoreById(Long storeId) throws NoSuchElementException {
        Optional<StoreEntity> storeEntity = storeRepository.findOneByStoreId(storeId);
        storeEntity.orElseThrow( () -> new NoSuchElementException("The store was not found"));
        storeRepository.delete(storeId);
    }
}
