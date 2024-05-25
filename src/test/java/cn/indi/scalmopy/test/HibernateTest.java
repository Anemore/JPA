package cn.indi.scalmopy.test;

import cn.indi.scalmopy.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class HibernateTest {

    private static SessionFactory factory;

    @BeforeAll
    public static void initialize() {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        StandardServiceRegistry registry = builder.configure().build();
        MetadataSources sources = new MetadataSources(registry);
        factory = sources.buildMetadata().buildSessionFactory();
    }

    @Test
    public void save() {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        Customer customer = new Customer(null, "lisi", "shanghai");
        session.persist(customer);
        transaction.commit();
    }

    @Test
    public void update() {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        Customer customer = new Customer(1, "wangwu", "guangzhou");
        session.merge(customer);
        transaction.commit();
    }

    @Test
    public void delete() {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        Customer customer = new Customer(1, null, null);
        session.remove(customer);
        transaction.commit();
    }

    @Test
    public void select() {
        Session session = factory.openSession();
        // 非懒加载, 执行到了就会查询.
        Customer customer = session.find(Customer.class, 2);
        System.out.println(customer);
    }

    @Test
    public void selectLazy() {
        Session session = factory.openSession();
        // 懒加载, 用到的时候才去查询.
        Customer customer = session.getReference(Customer.class, 2);
        System.out.println(customer);
    }

    @Test
    public void selectByHql() {
        String hql = "FROM Customer WHERE id = :id";
        Session session = factory.openSession();
        List<Customer> customers = session.createQuery(hql, Customer.class).setParameter("id", 2).getResultList();
        customers.forEach(System.out::println);
    }

}
