1. Ваша задача написать загрузчик плагинов в ваше приложение. Допустим вы пишите свою игру  и хотите чтобы люди имели возможность писать плагины для неё. Соответственно, разные разработчики могут назвать свои классы одинаковым именем, ваш загрузчик должен корректно это обрабатывать.
Усложненная версия задания. Система должна вести себя корректно если в плагине, есть скомпилированные классы с именем, которые есть в вашем приложении(не в плагинах), должны использоваться классы плагина, а не вашего приложения. Для этого придется поменять модель делегирования класслоадера в методе loadClass.
2. (Обязательно)
   Написать EncryptedClassloader
   Данный класслоадер умеет загружать классы из файлов дешифруя их, если они были зашифрованы. Ваша задача переопределить метод findClass(). В нем лоадер считывает зашифрованный массив байт, дешифрует его и превращает в класс (с помощью метода defineClass).
   На вход класслоадер принимает ключ шифрования, рутовую папку, в которой будет искать классы, родительский класслодер. Логика шифрования/дешифрования с использованием ключа может быть любой на ваш вкус (например, каждый считанный байт класса увеличить на определение число).
3. (Необязательно, очень сложное задание)**
   Написать игру камень-ножницы-бумага, где в роли игроков будут выступать плагины (файлы с расширением jar).
   Будет папка с плагинами, куда будет смотреть приложение.
   Каждый плагин должен иметь своё название (по названию файла или по внутреннему состоянию кода).
   Плагины будут загружаться по очереди и три раза играть в камень-ножницы-бумагa.
   Плагин-победитель остаётся в памяти, а проигравший выгружается.
   На место проигравшего загружается следующий плагин из папки и снова происходит игра.
   Данные действия повторяются, пока мы не определим плагин-победитель (его название «имя» будет написано в консоли). Также по сообщениям из консоли мы должны осознавать, что происходит в процессе отбора победителя.
   Предположительно плагин-игрок должен содержать в себе реализацию интерфейса, пример:
   ```
   Interface PlayableRockPaperScissors {  
      RockPaperScissorsEnum play();  
   }
   ```
   В пакете com.sawwere.sber.homework7.rps.player находятся классы игроков, использующих разные стратегии игры. В целях демонстрации в папке ресурсов соответствующего модуля лежат несколько jar файлов, в каждом из которых скомпилирован один из этих классов.
