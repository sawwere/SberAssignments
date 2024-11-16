public class Main {
    public static void main(String[] args) {
        Child parameterlessChild = new Child();

        Child parameterizedChild = new Child("Ivan");
    }
}
/*
Статические блоки выполняются один раз при первой инициализации объекта класса,
нестатические - при каждом новом создании объекта.
Причем вначале выполняются блоки родительского класса, его конструктор и только потом соответственно класса-наследника.
Parent:static 1
Parent:static 2
Child:static 1
Child:static 2
Parent:instance 1
Parent:instance 2
Parent:constructor
Child:instance 1
Child:instance 2
Child:constructor
Parent:instance 1
Parent:instance 2
Parent:name-constructor
Child:instance 1
Child:instance 2
Child:name-constructor

Process finished with exit code 0
 */
