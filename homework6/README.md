# Домашнее задание №6

## Вопросы для самоконтроля:
  1. Что означает аннотация Before? - помеченный таким образом метод выполняется перед запуском тестов
  2. Как в тесте проверить, что метод должен бросить исключение? - Assertions.assertThrows или .assertThrowsExactly(когда проверяем конкретное исключение без его потомков)  
  3. Что такое mock? - метод для создания заглушек, изменяющих поведение целевого класса/интерфейса  
     Spy? - по аналогии с mock, только изменяет поведение конкретного объекта
  4. Для чего применяется статический метод Mockito.verify? - позволяет проверить, сколько раз и в каком порядке вызвались те или иные методы с определенными параметрами на мок-объекте.  

Реализуйте свой итератор массива объектов. Напишите тесты для проверки его работоспособности. Оформите сборку кода через maven.  

Спроектировать дизайн соц. сети. В данном задании интересует разбитие приложения на модули, взаимодействие интерфейсов, а не реализация конкретных классов.  
Соцсети обычно предлагают большой набор сервисов: поиск/добавление друзей, просмотр профилей, загрузка и просмотр фото, общение через чат или стены, рекомендации, подарки и куча других сервисов. Все эти сервисы должны находится в отдельных модулях и иметь связи между собой.  
Ваша задача создать maven проект, создать модули для каждого сервиса, прописать зависимости одних модулей от других. В каждом модуле должны быть интерфейсы и доменная модель данного сервиса + в некоторых модулях нужна примерная реализация интерфейсов, где показано как используются интерфейсы других модулей.  
Написать юнит тесты к классам из данного задания (с помощью junit5 + mockito)  