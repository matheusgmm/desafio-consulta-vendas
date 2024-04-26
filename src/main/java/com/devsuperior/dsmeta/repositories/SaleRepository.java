package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    //    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleMinDTO(obj.id, obj.amount, obj.date, obj.seller.name) FROM Sale obj JOIN Fetch obj.seller WHERE obj.date BETWEEN :minDate and :maxDate AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))")

//    SELECT  tb_sales.id, tb_sales.date, tb_sales.amount, tb_seller.name
//    FROM tb_sales
//    INNER JOIN tb_seller
//    WHERE UPPER(tb_seller.name) LIKE UPPER(CONCAT('%', 'odinson', '%'))
//    AND tb_sales.date BETWEEN '2022-05-01' AND '2022-05-31'
    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleMinDTO(obj.id, obj.amount, obj.date, sel.name) "
            + "FROM Sale obj "
            + "JOIN obj.seller sel "
            + "WHERE UPPER(sel.name) LIKE UPPER(CONCAT('%', :name, '%')) "
            + "AND obj.date BETWEEN :minDate AND :maxDate"

    )
    Page<SaleMinDTO> reportSales(Pageable pageable, @Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate, String name);

}
