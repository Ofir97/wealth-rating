package com.ofir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ofir.beans.Person;

@Repository
public interface RichPeopleRepo extends JpaRepository<Person, Long>{

}
