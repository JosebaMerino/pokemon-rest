Hacer un ?nombre=pika

Buscar un pokemon por id (Done)

//////////////////////////////
mirar el video de "video teorico de rest" en moodle
//////////////////////////////
-------------------------------------------
GET 	pokemon/{id}		200	404
	response: {pokemon}
-------------------------------------------
GET	pokemon/?nombre=value	200	204
	response: [{pokemon}]
-------------------------------------------
DELETE	pokemon/{id}		200	404
	response: {pokemon}
-------------------------------------------
POST 	pokemon/			201	409 - conflicto
	body: {nombre: "pika"}		400 - peticion incorrecta
-------------------------------------------
PUT	pokemon/{id}			200	404
	body: {nombre: "pika"}		409 - conflicto
	response: {pokemon}
-------------------------------------------


