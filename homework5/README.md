# Домашнее задание №5
1.  Ответить на вопросы: 
    1) Почему на любом объекте можем вызвать метод getClass()? - все объекты наследуются от класса Object в котором определен этот метод   
    2) Почему на любом классе можем вызвать .class? - каждый тип данных может быть связан с соответствующим объектом типа Class, содержащим информацию о данном классе, т.е. класс сам является объектом
    3) В чём отличие динамического прокси от статического? Приведите преимущества и недостатки.  
    Статический прокси создается путем написания отдельной реализации проксируемого интерфейса с добавлением в методы нужной логики. Статические прокси работают значительно быстрее, однако для их создания требуется написать большое количество классов и кода.  
    Динамические прокси могут быть созданы во время выполнения программы, получается более гибкий инструмент. Таким образом мы можем написать всего один класс с необходимой логикой(например логирование) и использовать этот код в разных местах и для разных проксируемых объектов без необходимости писать количество повторяющегося кода(что также облегчает изменение кода - не придется переписывать кучу классов). Из минусов - на их использовании мы теряем в производительности.
    4) Есть ли разница в инициализации класса через new и статический метод newInstance()? - второй способ позволяет создавать объекты классов, имя типа которых можно определить только в рантайме.
    5) Можно ли с помощью рефлексии изменить значения полей аннотации? - да, можно(по крайней мере по такой схеме в Spring рабоает @Value, в которую можно поставить плейсхолдер ${}, который будет заменен на некоторое значение уже после запуска приложения)
