# GameStore
First API i've created.  
R/W data from mySQL tables.  
Nothing too complicated. Games have names, genre, releaseDate, stock.  
Contains simple JUnit tests.  
Also there are records for the sales of the games. Records have saleDate and salePrice.  

### Commands
`/games` to get all the games  
`/games?id=` to get a game by id mentioned  
`/games/sale?id=&price=` to sale a game -> reduces the stock, updates the records(transaction).
DELETE and UPDATE included  

`/records` to get all the records for the games  
`/records?id=` to get a record by id mentioned  
`/records/sales?from=&to=` to get all the sales for the games and their revenue made in a specific year range  
DELETE and UPDATE included  

### Implemetations
* **Jetty** for HTTP
* **Jersey** for Rest
* **Jackson** for JSON
* **DropWizard** framework
* **Injection Dependency** with Google GUICE
* **JDBI** for MySQL convinience
* Uses two **MySQL** tables: games and records for data.
* Uses **Apache spark** for data manipulation(MapReduce, Datasets and RDD's)
