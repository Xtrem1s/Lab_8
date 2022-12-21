package com.example.servingwebcontent;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
public interface TableGameRepo extends CrudRepository<TableGame, Integer> {
    Iterable<TableGame> findAllByPriceIsLessThanEqual(int value);
}
