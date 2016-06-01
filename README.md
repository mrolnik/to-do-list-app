# To Do List

> HTTP server for a to-do list.
> Scala app made with Play 2 framework.

## Pre-requisites
- Java 1.8
- Play Framework [(DOC)](https://www.playframework.com/documentation/2.5.x/Installing)

## Add item
### Request
- __URL__ : /todos
- __TYPE__: POST
- __BODY__: {"description": "Buy some milk"}
- __HEADERS__: Content-Type: application/json

### Response

__Added ->__ 201 CREATED
```javascript
{
	"id": "1"
}
```

## Update item
### Request
- __URL__ : /todos/{id}
- __TYPE__: PUT
- __BODY__: {"description": "Buy some more milk"}
- __HEADERS__: Content-Type: application/json

### Response

__Updated ->__ 200 OK

## Delete item
### Request
- __URL__ : /todos/{id}
- __TYPE__: DELETE

### Response

__Removed ->__ 200 OK


## Get all items
### Request
- __URL__ : /todos
- __TYPE__: GET

### Response

__Got all ->__ 200 OK
```javascript
[{
	"id": "2",
	"description": "Watch Sherlock.",
	"Date": "2016/06/01 07:11:29"
},
{
	"id": "1"
	"description": "Buy some milk!",
	"Date": "2016/06/01 07:10:29"
}]
```
## Get history
### Request
- __URL__ : /history/todos
- __TYPE__: GET

### Response

__Got all ->__ 200 OK
```javascript
[{
	"id": "3"
	"description": "DELETE",
	"itemId": "2"
	"Date": "2016/06/01 07:20:29"
},
{
	"id": "3"
	"description": "UPDATE",
	"itemDescription": "Buy some more milk!",
	"itemId": "1"
	"Date": "2016/06/01 07:13:29"
},
{
	"id": "2",
	"description": "ADD",
	"itemDescription": "Watch Sherlock.",
	"itemId": "2",
	"Date": "2016/06/01 07:11:29"
},
{
	"id": "1"
	"description": "ADD",
	"itemDescription": "Buy some milk!",
	"itemId": "1"
	"Date": "2016/06/01 07:10:29"
}]
```