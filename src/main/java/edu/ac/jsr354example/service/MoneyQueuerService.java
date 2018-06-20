package edu.ac.jsr354example.service;

import edu.ac.jsr354example.domain.Value;
import edu.ac.jsr354example.repository.ValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.money.MonetaryAmount;

@Service
public class MoneyQueuerService {
    @Autowired
    protected ValueRepository repository;

    public MonetaryAmount push(MonetaryAmount money){
        Value value = new Value();
        value.setMoney(money);

        return repository.save(value).getMoney();
    }

    public MonetaryAmount pull() {
        return repository.pull().getMoney();
    }
}
