# Кредитный конвейер

В этом проекте демонстрируется цепочка микросервисов, которые, работая вместе, изображают
кредитный конвейер. Этот конвейер принимает заявки, анализирует их и отвергает либо одобряет
их по нескольким критериям.

## Составляющие части
### 1. Бэкенд для фронтенда
#### Имя сервиса: credit-backend-for-frontend
Этот компонент представляет собой серверную часть для фронтенда веб-приложения, посредством которого
клиенты оставляют заявки на получение кредита.

Предоставляет следующие эндпойнты:

| Метод | URL | Описание | 
|---|---|---|
| POST | `/requests` | Добавляет новую заявку на получение кредита. Заявка попадает в очередь RabbitMQ. |


### 2. Фильтрующий сервис
#### Имя сервиса: request-filter-service
Этот компонент представляет собой фильтрующий сервис, который вычитывает заявки,
попадающие в RabbitMQ и применяет к ним правила фильтрации.
* Заявки, которые не удовлетворяют правилам фильтрации, попадают в Redis со статусом
`REJECTED`
* Заявки, которые удовлетворяют правилам фильтрации, отправляются в следующую
часть конвейера, где по ним происходит формирование первичной документации.

Сервис имеет компонент-`@RabbitListerer`, который слушает очередь сообщений с 
заявками на получение кредита, а также связан с БД Redis, в которой сохраняются
результаты фильтрации по заявкам.