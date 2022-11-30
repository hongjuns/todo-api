# Todo List Application
Spring Boot 이용한 Todo List Backend

## 프로젝트 개발환경

- Language
  - jdk 11
- Framework
  - SpringBoot 2.7.3
- ORM
  - JPA
- DB
  - h2

## 코드 실행

```
$ cd <프로젝트 디렉터리>/demo
$ ./gradlew bootRun
```

## REST API

### Todo

| Method     | URI                               | Action                                                  |
|------------|-----------------------------------|---------------------------------------------------------|
| `GET`      | `select`                          | `App\Http\Controllers\TodoController@selectTodo`    |
| `POST`     | `create`                          | `App\Http\Controllers\TodoController@createTodo`    |
| `PUT`      | `update`                          | `App\Http\Controllers\TodoController@updateTodo`    |
| `DELETE`   | `delete`                          | `App\Http\Controllers\TodoController@deleteTodo`    |
| `PUT`      | `position`                        | `App\Http\Controllers\TodoController@updateChangeTodo` |

### User

| Method     | URI                               | Action                                                  |
|------------|-----------------------------------|---------------------------------------------------------|
| `POST`     | `signup`                          | `App\Http\Controllers\UserController@registerUser`    |
| `POST`     | `signin`                          | `App\Http\Controllers\UserController@authenticate`    |
