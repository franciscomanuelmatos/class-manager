# class-manager

Spring Boot project.

To run the application, you need Docker installed and run the following command (inside the class-manager folder):

`docker-compose up`

This will start a Postgres container and another container where the API runs. The API will be accessible
from `localhost:8080`

To run JUnit tests, you'll need Maven. Run the following command:

`mvn test`

Some notes on my approach:
- Used docker-compose as deployment mechanism
- The bookings and classes data is stored in Postgres. The tests use an in-memory db solution (H2).
- Used Flyway as a database migration tool.

- The API allows scheduling/booking classes in the past.
- The class endpoint requires a name, capacity, startDate and endDate (if not present, returns a 400 BAD REQUEST). 
- The endDate is an inclusive parameter, so if you want to create just one class the startDate and endDate will be the same. If the endDate is the day after the startDate, then 2 different classes will be created.
- The class endpoint ensures that the endDate is after or the same as the startDate (if not, returns a 400 BAD REQUEST).

- The booking endpoint only saves the booking if a class exists for the given day (if not, returns a 404 NOT FOUND).
- When booking for a class, the date and member name is mandatory (if not, returns a 400 BAD REQUEST).
- Currently, we can have multiple bookings with the same person name for the same date, as there is no way to know if it's the same person or different people with the same name. In an ideal solution, we'd have a `users` table with unique usernames or ids so we could identify cases where a user would try to schedule a repeated booking.


Sample requests for each endpoint:

- Create classes:

`curl --request POST \
  --url http://localhost:8080/classes \
  --header 'Content-Type: application/json' \
  --data '{
	"name": "yoga",
	"capacity": 10,
	"startDate": "2022-10-10",
	"endDate": "2022-10-11"
}'`

Under normal circumstances, this will respond with a 200 and the following response body:

`[
	{
		"id": 1,
		"name": "yoga",
		"date": "2022-10-10",
		"capacity": 10
	},
	{
		"id": 2,
		"name": "yoga",
		"date": "2022-10-11",
		"capacity": 10
	}
]`

- Scheduling a booking

`curl --request POST \
  --url http://localhost:8080/bookings \
  --header 'Content-Type: application/json' \
  --data '{
	"name": "Francisco",
	"date": "2022-10-11"
}'`

Assuming a class exists for that day, it will respond with a 200 and the following response body:

`{
	"id": 1,
	"name": "Francisco",
	"date": "2022-10-11"
}`

If no class exists for the given date, the API will return a 404 with the following response:

`{
	"error": "No class found at date: 2022-10-12"
}`