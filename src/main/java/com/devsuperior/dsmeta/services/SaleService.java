package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	@Transactional(readOnly = true)
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	@Transactional(readOnly = true)
	public Page<SaleMinDTO> reportSales(
		Pageable pageable,
		String minDate,
		String maxDate,
		String name
	) {
		if (minDate.isEmpty() && maxDate.isEmpty()) {
			LocalDate convertMaxDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
			LocalDate convertMinDate = convertMaxDate.minusYears(1L);
			return repository.reportSales(pageable, convertMinDate, convertMaxDate, name);
		}

		if (minDate.isEmpty() && !maxDate.isEmpty()) {
			LocalDate convertMaxDate = convertDate(maxDate);
			LocalDate convertMinDate = convertDate(maxDate).minusYears(1L);
			return repository.reportSales(pageable, convertMinDate, convertMaxDate, name);
		}

		if (!minDate.isEmpty() && maxDate.isEmpty()) {
			LocalDate convertMaxDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
			LocalDate convertMinDate = convertDate(minDate);
			return repository.reportSales(pageable, convertMinDate, convertMaxDate, name);
		}

		return repository.reportSales(pageable, convertDate(minDate), convertDate(maxDate), name);
	}

	private LocalDate convertDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date,formatter);
	}
}
