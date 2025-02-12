package com.inventorySystem.service.impl.StockIssuance;

import com.inventorySystem.Model.StockIssuance;
import com.inventorySystem.repository.StockIssuanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@Service
public class StockIssuanceServiceImpl implements StockIssuanceService {

    private final StockIssuanceRepository stockIssuanceRepository;

    public StockIssuanceServiceImpl(StockIssuanceRepository stockIssuanceRepository) {
        this.stockIssuanceRepository = stockIssuanceRepository;
    }

    @Override
    public Page<StockIssuance> findAllItem(int page, int size) {
        return stockIssuanceRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public void deleteById(Long id) {
        stockIssuanceRepository.deleteById(id);  // Delete StockIssuance by ID
    }

    @Override
    public Optional<StockIssuance> findById(Long id) {
        return stockIssuanceRepository.findById(id);  // Return StockIssuance by ID wrapped in Optional
    }
}
