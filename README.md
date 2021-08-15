## Description
This java API leverages the spring framework to provide an endpoint for browsing and filtering city names.
* The list of cites returned are paged and has a default size of 10 items per page.
* A city object has 3 fields and looks like this:
```json
{
  "id": 1,
  "name": "CITY_NAME",
  "photo": "URL_FOR_CITY_IMAGE"
}
```
* This project has a csv file that is used to initialize the application.

### Implementation

* The docker setup still needs some work
* No authentication for the update endpoint

## Running the application

* The first thing to do is to ensure there is a postgres db with configurations defined here 👉 `/src/main/java/com/kn/citylist/resources/application.properties`.
* Secondly, defining an environment variable for the database host eg. `export DB_HOST=localhost`.
* Ensuring a database with the name `cities` exists or creating a new one using `createdb cities`.
* Then the Application can be packaged and started.

### To explore beautiful cities:

* You can get a list of Paginates cities and their image with this request:
```
[GET] /cities

OPTIONAL Parameters
name : To filter for a location by name
pageNumber : The number of the page to return
pageSize : The size of records on each page
```

* A sample filtered response looks like this

```json
{
  "content": [
    {
      "id": 18,
      "name": "Lagos",
      "photo": "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5e/2014_Tinubu_Square_Lagos_Nigeria_14640600637.jpg/500px-2014_Tinubu_Square_Lagos_Nigeria_14640600637.jpg"
    }
  ],
  "pageable": {
    "sort": {
      "sorted": true,
      "unsorted": false,
      "empty": false
    },
    "pageNumber": 0,
    "pageSize": 10,
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "last": true,
  "totalPages": 1,
  "totalElements": 1,
  "numberOfElements": 1,
  "number": 0,
  "first": true,
  "size": 10,
  "sort": {
    "sorted": true,
    "unsorted": false,
    "empty": false
  },
  "empty": false
}
```

### Updating an incorrect information

* It is also possible to make a patch request to update a city information.

```
[PATCH] /cities/:city_id
```

* Sample body to patch a city

```json
{
	"name": "Abeokuta",
	"photo": "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a7/A_view_of_Gbagura_mosque_in_Abeokuta%2C_Ogun_State-Nigeria.jpg/500px-A_view_of_Gbagura_mosque_in_Abeokuta%2C_Ogun_State-Nigeria.jpg"
}
```