package com.kroot.spring.datajpa.repository;

import com.kroot.spring.datajpa.model.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Specifies methods used to obtain and modify box related information
 * which is stored in the database.
 */
public interface AttributeRepository extends JpaRepository<Attribute, Long> {
}

