package com.example.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class ClentDemo {
    public static void main(String[] args) {
        // Initialize Hibernate SessionFactory
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        // Insert records into the database
        Customer customer1 = new Customer();
        customer1.setName("Alice");
        customer1.setEmail("alice@example.com");
        customer1.setAge(25);
        customer1.setLocation("New York");

        Customer customer2 = new Customer();
        customer2.setName("Bob");
        customer2.setEmail("bob@example.com");
        customer2.setAge(30);
        customer2.setLocation("California");

        session.save(customer1);
        session.save(customer2);
        transaction.commit();

        // JPA Criteria Queries
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Equal restriction
        System.out.println("Customers with name 'Alice':");
        CriteriaQuery<Customer> query1 = builder.createQuery(Customer.class);
        Root<Customer> root1 = query1.from(Customer.class);
        query1.select(root1).where(builder.equal(root1.get("name"), "Alice"));
        List<Customer> result1 = session.createQuery(query1).getResultList();
        result1.forEach(customer -> System.out.println(customer.getName()));

        // Between restriction
        System.out.println("\nCustomers aged between 20 and 35:");
        CriteriaQuery<Customer> query2 = builder.createQuery(Customer.class);
        Root<Customer> root2 = query2.from(Customer.class);
        query2.select(root2).where(builder.between(root2.get("age"), 20, 35));
        List<Customer> result2 = session.createQuery(query2).getResultList();
        result2.forEach(customer -> System.out.println(customer.getName()));

        // Like restriction
        System.out.println("\nCustomers located in areas containing 'York':");
        CriteriaQuery<Customer> query3 = builder.createQuery(Customer.class);
        Root<Customer> root3 = query3.from(Customer.class);
        query3.select(root3).where(builder.like(root3.get("location"), "%York%"));
        List<Customer> result3 = session.createQuery(query3).getResultList();
        result3.forEach(customer -> System.out.println(customer.getName()));

        session.close();
        sessionFactory.close();
    }
}
