package com.sawwere.sber.homework17;

import com.sawwere.sber.homework17.domain.Ingredient;
import com.sawwere.sber.homework17.domain.Recipe;
import com.sawwere.sber.homework17.domain.RecipeIngredient;
import com.sawwere.sber.homework17.domain.RecipeIngredientId;
import com.sawwere.sber.homework17.service.IngredientService;
import com.sawwere.sber.homework17.service.RecipeService;
import com.sawwere.sber.homework17.util.ConsoleUtils;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class RecipeApplicationRunner implements ApplicationRunner {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("Добро пожаловать в приложение для хранения рецептов, выберите и введите одну из команд");
        String message = """
            
            Меню операций
            1 - поиск рецепта по id
            2 - поиск рецепта по названию
            3 - создание рецепта
            4 - удаление рецепта
            5 - список всех ингредиентов
            6 - создание ингредиента
            0 - выход
            """;

        while (true) {
            System.out.println(message);
            String input = ConsoleUtils.readString("Введите код операции: ", scanner);
            switch (input) {
                case "0": return;
                case "1": {
                    resolveFindRecipe();
                    break;
                }
                case "2": {
                    resolveSearchByLike();
                    break;
                }
                case "3": {
                    resolveCreateRecipe();
                    break;
                }
                case "4": {
                    resolveDeleteRecipe();
                    break;
                }
                case "5": {
                    resolveFindAllIngredients();
                    break;
                }
                case "6": {
                    resolveCreateIngredient();
                    break;
                }
                default: {
                    System.out.println("Некорректный код операции, повторите попытку.");
                    break;
                }
            }
        }
    }

    private boolean readAndGetBooleanAnswer() {
        String eagerAnswer = "";
        while (true) {
            System.out.print("Вывести полную информацию (жадная инициализация) ([д]/н): ");
            eagerAnswer = scanner.nextLine();
            switch (eagerAnswer.toLowerCase()) {
                case "y", "д", "":
                    return  true;
                case "n", "н":
                    return false;
                default:
                    System.out.println("Некорректный ввод. Повторите попытку.");
            }
        }
    }

    private void resolveFindRecipe() {
        Long id = ConsoleUtils.readLong("Введите id рецепта: ", scanner);
        boolean eagerFetch = readAndGetBooleanAnswer();

        var optionalRecipe = recipeService.findById(id, eagerFetch);
        if (optionalRecipe.isPresent()) {
            System.out.println("Найден рецепт: ");
            Recipe recipe = optionalRecipe.get();
            printRecipe(recipe);
            if (eagerFetch) {
                System.out.println("Всего ингредиентов: " + recipe.getIngredients().size());
                for (int i = 0; i < recipe.getIngredients().size(); i++) {
                    System.out.println("Ингредиент %d: { id: %d, название: %s, количество: %d %s }".formatted(
                            i + 1,
                            recipe.getIngredients().get(i).getIngredient().getId(),
                            recipe.getIngredients().get(i).getIngredient().getName(),
                            recipe.getIngredients().get(i).getQuantity(),
                            recipe.getIngredients().get(i).getMeasureUnit()
                    ));
                }
            }
        } else {
            System.out.println("Рецепт с заданным id не найден.");
        }
    }

    private void resolveSearchByLike() {
        String query = ConsoleUtils.readString("Введите название рецепта (целиком или полностью): ", scanner);
        var recipes = recipeService.search(query);
        System.out.println("Найдено рецептов: " + recipes.size());
        for (Recipe recipe : recipes) {
            printRecipe(recipe);
        }
    }

    private void printRecipe(Recipe recipe) {
        System.out.println("id: %d, название: %s".formatted(recipe.getId(), recipe.getName()));
    }

    private void printIngredient(Ingredient ingredient) {
        System.out.println("id: %d, название: %s".formatted(ingredient.getId(), ingredient.getName()));
    }

    private void resolveCreateRecipe() {
        String name = ConsoleUtils.readString("Введите название рецепта: ", scanner);
        Recipe recipe = Recipe.builder().name(name).build();
        List<RecipeIngredient> ingredientList = new ArrayList<>();

        while (true) {
            Long ingredientId = ConsoleUtils.readLong("Введите id ингредиента или -1 для прекращения ввода: ", scanner);
            if (ingredientId == -1L) {
                break;
            }
            Optional<Ingredient> optionalIngredient = ingredientService.findById(ingredientId);
            if (optionalIngredient.isEmpty()) {
                System.out.println("Данный ингредиент отсутствует. Повторите ввод.");
                continue;
            }
            int quantity = ConsoleUtils.readInt("Введите количество ингредиента: ", scanner);
            String measureUnit = ConsoleUtils.readString("Введите единицу измерения: ", scanner);
            ingredientList.add(RecipeIngredient.builder()
                    .recipe(recipe)
                    .ingredient(optionalIngredient.get()) // detached
                    .quantity(quantity)
                    .measureUnit(measureUnit)
                    .build()
            );
        }
        recipe.setIngredients(ingredientList);
        recipe = recipeService.create(recipe);
        System.out.println("Создан рецепт:");
        printRecipe(recipe);

    }

    private void resolveDeleteRecipe() {
        Long id = ConsoleUtils.readLong("Введите id рецепта: ", scanner);
        boolean deleteResult = recipeService.delete(id);
        if (deleteResult) {
            System.out.println("Удален рецепт с id = " + id);
        } else {
            System.out.println("Удаление не выполнено, возможно этого рецепта нет в базе?");
        }
    }

    private void resolveFindAllIngredients() {
        System.out.println("На данный момент в базе есть записи о следующих ингредиентах: ");
        var ingredients = ingredientService.findAll();
        for (Ingredient ingredient : ingredients) {
            printIngredient(ingredient);
        }
    }

    private void resolveCreateIngredient() {
        String name = ConsoleUtils.readString("Введите название ингредиента: ", scanner);
        var ingredient = ingredientService.create(Ingredient.builder().name(name).build());
        printIngredient(ingredient);
    }
}
