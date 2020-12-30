package com.os.mywebservice.controller;

import com.os.mywebservice.dao.CustomerDao;
import com.os.mywebservice.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

//@CrossOrigin(maxAge = 3600)
//@RestController
//@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerDao customerDao;

    @CrossOrigin(allowedHeaders = {"http://localhost:4200/list"})
    @GetMapping("/")
    public List<Customer> getAll(){
        return customerDao.findAll();
    }

    @GetMapping("/{customerId}")
    public Customer getById(@PathVariable int customerId){
        Optional<Customer> optionalCustomer = customerDao.findById(customerId);
        //check if customer isn't found
        Customer customer = null;
        if(optionalCustomer.isPresent()){
            customer = optionalCustomer.get();
        }
        return customer;
    }

    @PostMapping("/add")
    public Customer save(@RequestBody Customer customer){
        customer.setId(0);
        return customerDao.save(customer);
    }

    @PutMapping("/update")
    public Customer update(@RequestBody Customer customer){
        return customerDao.save(customer);
    }

    @DeleteMapping("/delete/{customerId}")
    public String delete(@PathVariable int customerId){
        customerDao.deleteById(customerId);
        return "customer deleted - "+customerId;
    }
}
