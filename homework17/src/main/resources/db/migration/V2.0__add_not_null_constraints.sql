ALTER TABLE recipes_ingredients ALTER COLUMN  measure_unit SET NOT NULL;
ALTER TABLE recipes_ingredients ALTER COLUMN  quantity SET NOT NULL;

ALTER TABLE ingredients ALTER COLUMN  name SET NOT NULL;

ALTER TABLE recipes ALTER COLUMN  name SET NOT NULL;

