# Домашнее задание №11
## Вопросы
1. Как получить ссылку на текущий поток? - Thread.currentThread()
2. Зачем нужно ключевое слово synchronized? - служит для синхронизации состояния критической секции между потоками  
На что его можно вещать? - на метод или блок кода
3. Захват какого монитора происходит при входе в synchronized метод/статик метод/блок?
synchronized метод - по объекту, чей метод вызван.  
synchronized static метод - по классу, метод которого был вызван.  
блок - по объекту, указанному в круглых скобках в начале блока.  
4. Зачем нужно ключевое слово volatile? - операции чтения и записи для помеченного объекта выполняются атомарно, также этот объект не кешируется(что обеспечивает видимость изменений во всех потоках)   
На что его можно вещать(поле, метод, класс, конструктор..)? - на поля
5. Что делает метод  
Object#wait - приостанавливает текущий поток, освобождая монитор  
Object#notify/Object#notifyAll - возобновляет работу 1 случайного потока(всех потоков во 2 случае), ранее вызвавшего wait() для данного объекта
6. Что за исключение IllegalMonitorStateException? - возникает при попытке вызова предыдущих методов вне synchronized блока
7. Что делает метод Thread#join? - вызвавший этот метод поток останавливается до момента завершения другого указанного потока
8. Что делает метод Thread#interrupt? - устанавливает значение флага interrupted для заданного потока равным true


## Задание. Реализовать ThreadPool

```
public interface ThreadPool {
void start(); // запускает потоки. Потоки бездействуют, до тех пор пока не появится новое задание в очереди (см. execute)

    void execute(Runnable runnable); // складывает это задание в очередь. Освободившийся поток должен выполнить это задание. Каждое задание должны быть выполнено ровно 1 раз
}
```

Сделать 2 реализации ThreadPool
1) FixedThreadPool - Количество потоков задается в конструкторе и не меняется.
2) ScalableThreadPool в конструкторе задается минимальное и максимальное(int min, int max) число потоков,
   количество запущенных потоков может быть увеличено от минимального к максимальному, если при добавлении нового задания в очередь нет свободного потока для исполнения этого задания. При отсутствии задания в очереди, количество потоков опять должно быть уменьшено до значения min
