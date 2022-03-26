package com.example.webflux.dao;

import com.example.webflux.dto.Customer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class CustomerDao {


    private static void sleepExecution(int i){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> getCustomers()  {
        return IntStream.rangeClosed(1, 10)
                .peek(CustomerDao::sleepExecution) //to sleep the thread of 1 sec
                .peek(i -> System.out.println("processing count : " + i))
                .mapToObj(i -> new Customer(i, "customer" + i)) // mapping to customer object
                .collect(Collectors.toList());
    }


    public Flux<Customer> getCustomersStream()  {
        return Flux.range(1,10)
                .delayElements(Duration.ofSeconds(1)) // this will add delay of 1 sec
                .doOnNext(i -> System.out.println("processing count in stream flow : " + i)) //doOnNext event based response back
                .map(i -> new Customer(i, "customer" + i));
    }


    public Flux<Customer> getCustomerList()  {
        return Flux.range(1,50)
                .doOnNext(i -> System.out.println("processing count in stream flow : " + i))
                .map(i -> new Customer(i, "customer" + i));
    }
}
