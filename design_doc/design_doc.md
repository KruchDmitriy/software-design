# Дизайн-документ


Игра представляет собой классическую RPG с видом сверху вниз и управлением одним персонажем.

## Общие сведения о системе

Все действия происходят на ограниченной карте прямоугольного размера,
считывающейся из файла. Игрок управляет одним персонажем, стрелочными
клавишами, может собирать различные предметы и сражаться с мобами.

**Условие выигрыша:** игрок убил всех мобов.

**Условие проигрыша:** игрок убит.

### Основные понятия:

## Карта

Карта -- это прямоугольная область, состоящая из тайлов.

**Тайл** -- квадратное изображение, размеры которого
подбираются динамически в зависимости от размера карты и общего количества тайлов.

На карте могут находится объекты 4 типов:
игрок, мобы, вещи, ландшафт.

**Моб** -- управляемый программно объект, моб атакует игрока в случае, если они пытаются занять одно и то же место на карте.

**Вещь** -- неподвижный объект на карте. При перемещении игрока в тайл
с вещью, вещь подбирается и в зависимости от её типа игрок получает те или иные бонусы.

Инвентарь у персонажа неограничен.

**Типы вещей**: меч тысячелетия, шлем, морковка и мусор.
Меч увеличивают атаку игрока, шлем увеличивает защиту, морковка увеличивает здоровье. Мусор ничего не добавляет.

**Ландшафт** -- неподвижный объект. Игрок не может занять ячейку, в которой находится объект типа ландшафт.


### Architectural drivers:

В первую очередь большое влияние на архитектуру оказало техническое задание:

* Расширяемая и сопровождаемая архитектура
* Возможность далее сделать графический тайловый интерфейс (поэтому сделал сразу графический
интерфейс

* Персонаж игрока, способный перемещаться по карте, управляемый с клавиатуры. Непосредственно стрелками (или дополнительной цифровой клавиатурой), не вводом команды
* Инвентарь персонажа, включающий элементы, влияющие на его характеристики, которые можно надеть и снять
* Боевая система — движущиеся объекты, пытающиеся занять одну клетку карты, атакуют друг друга

* Карта (считывается из файла)
* Мобы, способные перемещаться по карте


**Главные классы и интерфейсы:**
Entity, Message, World, GameObject.

Каждый класс, объекты которого тем или иным образом хотят общаться с другими
объектами, должны наследовать абстрактный класс Entity.

**Entity** -- содержит список подписчиков -- Entity. Может посылать сообщение
подписчикам, и обрабатывать сообщение (Message). Сообщение при обработке может
быть отфильтровано.

**GameObject** -- базовый класс, объект на карте. Ему также соответствуют классы
**GameObjectView** и **GameObjectController**.

**GameObjectView** -- класс, в котором содержится логика относительно того, как отображать
объект игры. **GameObjectController** -- класс, который обрабатывает события из "внешнего мира",
например, события приходящие от пользователя, и отправляющий соответствующие сообщения модели
-- GameObect'у.


### Роли и случаи использования

**Пользователь 1**

Имя: Косякова Мария

Возраст: 21 год

Образование: Высшее

Описание: любит простые и красивые игры, в которых нужно подумать,
либо, наоборот, полностью отключить мозг после сложного дня.
Занимается спортом, вернее спортивным программированием.

Особенности использования игры:
Поиграла, поскольку интересуется рогаликами.


**Пользователь 2**

Имя: Умхаев Руслан

Возраст: 13 лет

Образование: Без образования

Описание: Типичный школьник, лентяй, играет во все компьютерные игры, которые найдёт.

Особенности использования игры:
Открыл ради интереса, посоветовали друзья.


**Пользователь 2**

Имя: Сидорова Валентина Петровна

Возраст: 56 лет

Образование: Среднее

Описание: Бабушка. Бывает играет  и смотртит то, что забыл закрыть внук.

Особенности использования игры:
Заинтересовала разноцветная игра. Подошла, потыкала на все кнопки и забыла про
кастрюлю с супом на кухне.

