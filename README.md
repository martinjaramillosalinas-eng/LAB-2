# SaborExpress

Laboratorio 2 - POO 2026-2

Integrante: Martin

## Como correrlo

Abrir la carpeta en IntelliJ y correr Main. Usé Java 17.

## Decisiones

- Hice una sola clase para guardar los datos y la use para clientes, productos, repartidores y pedidos, asi no repito el mismo codigo. Por dentro usa un mapa para que buscar por id sea rapido.
- Los clientes se identifican por el documento, entonces si alguien se registra por otro canal no se crea otra vez.
- Cuando una busqueda no encuentra nada no se devuelve null.
- Los pedidos de prueba se cargan al inicio del Main.

## Donde use cada tema

- **Colecciones:** listas, mapas y conjuntos para guardar los datos y armar los reportes.
- **Ordenamiento:** el menu y los repartidores se pueden ordenar por varios criterios combinados.
- **Genericos con wildcards:** en la clase que guarda los datos y en los calculos que sirven para cualquier tipo de producto.
- **Programacion funcional:** streams y lambdas en los reportes y consultas.
- **SOLID:** cada clase tiene su responsabilidad y se pueden agregar cosas nuevas sin cambiar el pedido.
- **Strategy:** para las promociones.
- **Observer:** para avisar a la cocina, al cliente y al repartidor cuando cambia el estado del pedido.
- **Factory:** para crear los pedidos segun el canal.
