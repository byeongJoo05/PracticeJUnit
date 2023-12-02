package sample.cafekiosk.unit;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import sample.cafekiosk.unit.beverage.Beverage;
import sample.cafekiosk.unit.order.Order;

@Getter
public class CafeKiosk {

	public static final LocalTime SHOP_OPEN_TIME = LocalTime.of(10, 0);
	public static final LocalTime SHOP_CLOSE_TIME = LocalTime.of(22, 0);

	private final List<Beverage> beverages = new ArrayList<>();

	public void add(Beverage beverage) {
		beverages.add(beverage);
	}

	public void add(Beverage beverage, int count) {
		if (count <= 0) {
			throw new IllegalArgumentException("음료는 1잔 이상 주문하셔야 합니다.");
		}

		for (int i = 0; i < count; i++) {
			beverages.add(beverage);
		}
	}

	public void remove(Beverage beverage) {
		beverages.remove(beverage);
	}

	public void clear() {
		beverages.clear();
	}


	// 내부로직 내에 시간에 대한 변수가 있기에
	// 특정 시간대가 아니면 예외처리 유닛테스트를 특정짓지 못한다.
	public Order createOrder() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		LocalTime currentTime = currentDateTime.toLocalTime();
		if (currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)) {
			throw new IllegalArgumentException("주문 시간이 아닙니다. 관리자에게 문의하세요.");
		}

		return new Order(currentDateTime, beverages);
	}

	// 변화가 생길 수 있는 변수를 외부에서 받아왔기에
	// 시간에 관련한 변수 핸들링이 가능해져 유닛테스트가 가능해졌다.
	public Order createOrder(LocalDateTime currentDateTime) {
		LocalTime currentTime = currentDateTime.toLocalTime();
		if (currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)) {
			throw new IllegalArgumentException("주문 시간이 아닙니다. 관리자에게 문의하세요.");
		}

		return new Order(currentDateTime, beverages);
	}

	public int calculateTotalPrice() {
		return beverages.stream()
				.mapToInt(Beverage::getPrice)
				.sum();
	}
}
