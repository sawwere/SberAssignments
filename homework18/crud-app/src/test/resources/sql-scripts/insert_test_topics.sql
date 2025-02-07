INSERT INTO topics
    (id, title, text, author)
VALUES
    (1001, '1001 пост', 'Здесь должен быть текст', 'Админ'),
    (1002, '1002 пост', 'И здесь тоже должен быть текст', 'Анонимный пользователь')
ON CONFLICT DO NOTHING;