# Домашнее задание №10

## Основное Задание
Дан файл содержащий несколько случайных натуральных чисел от 1 до 50. Необходимо написать многопоточное приложение,  которое параллельно рассчитает и выведет в консоль факториал для каждого числа из файла.

## Бонусные задания без баллов
1. Выведите на экран следующую строку через 2 потока без использования встроенной синхронизации:
        0 a 1 b 2 c 3 d 4 e 5 f 6 g 7 h 8 i 9 j,
где первый поток может выводить только цифры, а второй — буквы (буквы должны чередоваться друг с другом).
2. Приложение не должно тратить на корректный вывод секунду или более.


### Демонстрация вывода
```
...
35! = 10333147966386144929666651337523200000000 ---- Thread-6
39! = 20397882081197443358640281739902897356800000000 ---- Thread-3
8! = 40320 ---- Thread-9
13! = 6227020800 ---- Thread-5
29! = 8841761993739701954543616000000 ---- Thread-1
36! = 371993326789901217467999448150835200000000 ---- Thread-11
44! = 2658271574788448768043625811014615890319638528000000000 ---- Thread-2
48! = 12413915592536072670862289047373375038521486354677760000000000 ---- Thread-7
================================
|            Bonus             |
================================
0 a 1 b 2 c 3 d 4 e 5 f 6 g 7 h 8 i 9 j 
Process finished with exit code 0
```
