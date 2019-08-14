package payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import payroll.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
