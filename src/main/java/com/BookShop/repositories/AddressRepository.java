package com.BookShop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BookShop.entities.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

}
