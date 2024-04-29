package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleMinDTO(obj.id, obj.amount, obj.date, sel.name) "
            + "FROM Sale obj "
            + "JOIN obj.seller sel "
            + "WHERE UPPER(sel.name) LIKE UPPER(CONCAT('%', :name, '%')) "
            + "AND obj.date BETWEEN :minDate AND :maxDate"

    )
    Page<SaleMinDTO> reportSales(Pageable pageable, @Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate, String name);

    @Query("SELECT new com.devsuperior.dsmeta.dto.SummaryDTO(sel.name, SUM(obj.amount)) "
            + "FROM Sale obj "
            + "JOIN obj.seller sel "
            + "WHERE obj.date BETWEEN :minDate AND :maxDate "
            + "GROUP BY sel.name "
    )
    Page<SummaryDTO> summarySales(Pageable pageable, LocalDate minDate, LocalDate maxDate);

}
