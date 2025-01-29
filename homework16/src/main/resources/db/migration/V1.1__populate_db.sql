INSERT INTO recipes
    (id, name)
VALUES
    (1, 'картофельное пюре'),
    (2, 'куриная котлета'),
    (3, 'отварная гречка')
ON CONFLICT DO NOTHING;
SELECT pg_catalog.setval('public.recipes_id_seq', 3, true);

INSERT INTO ingredients
    (id, name)
VALUES
    (1, 'картофель'),
    (2, 'мясо курицы'),
    (3, 'крупа гречневая'),
    (4, 'масло сливочное'),
    (5, 'соль'),
    (6, 'лук репчатый')
ON CONFLICT DO NOTHING;
SELECT pg_catalog.setval('public.ingredients_id_seq', 6, true);


INSERT INTO recipes_ingredients
    (recipe_id, ingredient_id, quantity, measure_unit)
VALUES
    (1, 1, 1000, 'кг'),
    (2, 2, 1000, 'кг'),
    (2, 4, 10, 'грамм'),
    (2, 5, 25, 'грамм'),
    (2, 6, 2, 'штука'),
    (3, 3, 500, 'грамм'),
    (3, 4, 50, 'грамм'),
    (3, 5, 10, 'грамм')
ON CONFLICT DO NOTHING;