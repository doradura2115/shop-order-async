package com.example.shop_order_async;

import com.example.shop_order_async.bonus.BonusAccountRepository;
import com.example.shop_order_async.order.api.CreateOrderRequest;
import com.example.shop_order_async.order.persistence.OrderRepository;
import com.example.shop_order_async.order.service.OrderService;
import com.example.shop_order_async.receipt.ReceiptRepository;
import com.example.shop_order_async.warehouse.WarehouseCheckRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class ShopOrderAsyncApplicationTests {

	@Autowired private OrderService orderService;
	@Autowired private OrderRepository orderRepository;
	@Autowired private BonusAccountRepository bonusRepository;
	@Autowired private WarehouseCheckRepository warehouseRepository;
	@Autowired private ReceiptRepository receiptRepository;
	@Autowired private TransactionTemplate transactionTemplate;

	@BeforeEach
	void setUp() {
		// Очищаем БД перед каждым тестом
		receiptRepository.deleteAll();
		warehouseRepository.deleteAll();
		bonusRepository.deleteAll();
		orderRepository.deleteAll();
	}

	@AfterEach
	void tearDown() {
		// Даем асинхронным пулам завершить работу перед следующим тестом
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	@Test
	void testOrderLifecycleOnCommit() {
		// Явно выполняем создание заказа в транзакции с коммитом
		CreateOrderRequest request = new CreateOrderRequest("Laptop", 1, "user-123", 50000, 100);

		// Запускаем бизнес-метод (он сам откроет транзакцию и закоммитит её)
		var order = orderService.createOrder(request);
		assertThat(order.getId()).isNotNull();

		// Ждем, пока асинхронные задачи завершатся
		await().atMost(Duration.ofSeconds(5)).untilAsserted(() -> {
			assertThat(bonusRepository.count()).isEqualTo(1);
			assertThat(warehouseRepository.count()).isEqualTo(1);
			assertThat(receiptRepository.count()).isEqualTo(1);
		});
	}

	@Test
	void testOrderRollback() {
		CreateOrderRequest request = new CreateOrderRequest("[ROLLBACK] Error", 1, "user-123", 50000, 100);

		assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(request));

		try { Thread.sleep(1500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

		assertThat(orderRepository.count()).isZero();
		assertThat(bonusRepository.count()).isZero();
		assertThat(warehouseRepository.count()).isZero();
		assertThat(receiptRepository.count()).isZero();
	}
}
