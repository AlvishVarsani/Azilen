package com.azilen.training.repository;

import com.azilen.training.entity.Expenses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses,Integer> {
    Page<Expenses> findByUserDetailsId(int userDetailsId, Pageable pageable);

    @Query("SELECT e FROM Expenses e WHERE e.userDetails.id = :userDetailsId " +
            "AND (:minAmount IS NULL OR e.amount >= :minAmount) " +
            "AND (:maxAmount IS NULL OR e.amount <= :maxAmount) " +
            "AND (:startDate IS NULL OR e.date >= :startDate) " +
            "AND (:endDate IS NULL OR e.date <= :endDate) " +
            "AND (:categoryId IS NULL OR e.category.id = :categoryId)"+
            "AND (:searchTitle IS NULL OR e.title LIKE CONCAT('%', :searchTitle, '%'))")
    Page<Expenses> findFilteredExpenses(
            @Param("userDetailsId") int userDetailsId,
            @Param("minAmount") Double minAmount,
            @Param("maxAmount") Double maxAmount,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("categoryId") Integer categoryId,
            @Param("searchTitle") String searchTitle,
            Pageable pageable);
}
