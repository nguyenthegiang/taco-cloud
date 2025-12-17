package tacos.data;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tacos.model.TacoOrder;

import java.util.Date;
import java.util.List;

//public interface OrderRepository {
//    TacoOrder save(TacoOrder order);
//}

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
    List<TacoOrder> findByDeliveryZip(String deliveryZip);

    List<TacoOrder> readOrdersByDeliveryZipAndPlacedAtBetween(String deliveryZip, Date placedAtAfter, Date placedAtBefore);

    @Query("select o from TacoOrder o where o.deliveryCity = 'Seattle'")
    List<TacoOrder> readOrdersDeliveredInSeattle();
}