# StrategesPatterns

Java2s - пример с матемическими операциями;
JavaCodeGeeks - пример с разными типами покупок;
SourceMaking - пример с решением проблемы;
HeadFirst - пример с симулятором уточек.

Задача:
По типу клиента (или по типу обрабатываемых данных) выбрать подходящий алгоритм, который следует применить. 
Если используется правило, которое не подвержено изменениям, нет необходимости обращаться к шаблону «стратегия».

Участники:
- Класс Strategy определяет, как будут использоваться различные алгоритмы.
- Конкретные классы ConcreteStrategy реализуют эти различные алгоритмы.
- Класс Context использует конкретные классы ConcreteStrategy посредством ссылки на конкретный тип абстрактного 
класса Strategy. Классы Strategy и Context взаимодействуют с целью реализации выбранного алгоритма 
(в некоторых случаях классу Strategy требуется посылать запросы классу Context). 
Класс Context пересылает классу Strategy запрос, поступивший от его класса-клиента.

Этот паттерн проектирования известен также под названием Policy. 
Суть его состоит в том, чтобы создать несколько моделей поведения (стратегий) 
для одного объекта и вынести их в отдельные классы. 

Приимушества:
- позволяет выбирать модель поведения объекта динамически;
- упрощает процесс добавления новых стратегий;
- является альтернативой наследованию;
- избавляет от множества условий (if, case);

Инкапсулирование стратегии в отдельном классе позволяет менять поведение объекта динамически. 
Очевидно, что для добавления новой стратегии нужно создать независимый класс и реализовать необходимые функции. 
Аналогично, для изменения или удаления стратегии опять же будет затронут только один класс.
Для выбора модели поведения часто используют наследование. Имея некоторый объект, мы создаем новый экземпляр 
подкласса этого объекта и присваиваем его самому объекту. 
При использовании паттерна Startegy, достаточно переключится на интересующую нас стратегию.
