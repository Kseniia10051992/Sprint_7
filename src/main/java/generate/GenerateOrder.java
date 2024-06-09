package generate;

import orderCreating.OrderCreate;

public class GenerateOrder {
    public OrderCreate getOrder(String firstName, String lastName, String address, String metroStation, String phone,
                                Integer rentTime, String deliveryDate, String comment, String[] color) {
        return OrderCreate.builder()
                .firstName(firstName)
                .lastName(lastName)
                .address(address)
                .metroStation(metroStation)
                .phone(phone)
                .rentTime(rentTime)
                .deliveryDate(deliveryDate)
                .comment(comment)
                .color(color)
                .build();
    }
}

