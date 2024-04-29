package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

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
		LocalDate convertMinDate = minDate.isEmpty() ? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()).minusYears(1) : convertDate(minDate);
		LocalDate convertMaxDate = maxDate.isEmpty() ? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()) : convertDate(maxDate);

		return repository.reportSales(pageable, convertMinDate, convertMaxDate, name);
	}

	@Transactional(readOnly = true)
	public Page<SummaryDTO> summarySales(
			Pageable pageable,
			String minDate,
			String maxDate
	) {
		LocalDate convertMinDate = minDate.isEmpty() ? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()).minusYears(1) : convertDate(minDate);
		LocalDate convertMaxDate = maxDate.isEmpty() ? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()) : convertDate(maxDate);

		return repository.summarySales(pageable, convertMinDate, convertMaxDate);
	}


	private LocalDate convertDate(String date) {
		return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}



}
