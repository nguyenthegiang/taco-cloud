package tacos.data;

import org.springframework.data.repository.CrudRepository;
import tacos.model.Ingredient;

//public interface IngredientRepository extends Repository<Ingredient, String> {
//    Iterable<Ingredient> findAll();
//
//    Optional<Ingredient> findById(String id);
//
//    Ingredient save(Ingredient ingredient);
//}

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}