# Friend Management
API server that does simple "Friend Management"

## Background
For any application with a need to build its own social network, "Friends Management" is a common requirement which ussually starts off simple but can grow in complexity depending on the application's use case.
Usually, applications would start with features like "Friend", "Unfriend", "Block", "Receive Updates" etc.

## Instruction to run via docker
`docker run -p 8080:8080 raisrahim/friend-management`

## Instruction to build and run the project
1. `mvn clean install`
2. `java -jar target/friend-management-1.0.jar`

Prerequisite to build this project:
1. Java JDK 8 (http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
2. Maven  (https://maven.apache.org/install.html)

## API Endpoints
- `POST: /person/register`
Register an email address.
Request: `{"email":"andy@example.com"}`
Response: `{"success":true}`

- `POST: /friend/connect`
Create a friend connection between two email addresses.
Request: `{"friends":["andy@example.com","john@example.com"]}`
Response: `{"success":true}`

- `POST: /friend/list`
Retrieve the friends list for an email address.
Request: `{"email":"andy@example.com"}`
Response: `{"success":true, "friends":["john@example.com"], "count":1}`

- `POST: /friend/common`
Retrieve the common friends list between two email addresses.
Request: `{"friends":["andy@example.com","john@example.com"]}`
Response: `{"success":true, "friends":["common@example.com"], "count":1}`

- `POST: /friend/subscribe`
Subscribe to updates from an email address.
Request: `{"requestor":"andy@example.com", target:"john@example.com"}`
Response: `{"success":true}`

- `POST: /friend/block`
Block updates from an email address.
Request: `{"requestor":"andy@example.com", target:"john@example.com"}`
Response: `{"success":true}`

- `POST: /updates`
Retrieve all email addresses that can receive updates from an email address.
Request: `{"sender":"john@example.com", text:"Hello World! kate@example.com"}`
Response: `{"success":true, "recipients":["lisa@example.com","kate@example.com"]}`

## Other Endpoint
- `GET: /h2`
The application's database web console.
JDBC URL: `jdbc:h2:mem:fm-db`
User Name: `sa`
Password:
## Error Handling
#### Features:
- ##### Internalization of Error Messages 
    Currently support 2 languages: English & Bahasa Indonesia.
    Add Http Header Request "Accept-Language: in", then the response message will be in Bahasa if error happen.
- ##### `"errorInfo"` for more information
    Error response has a field `"errorInfo"` contains a web page link for more information.
    Also for Unexpected error, the webpage display the saved stack trace.

#### There are 3 types of error:
1. Validation error (400 Bad Request)
2. Business error (409 Conflict)
3. Unexpected error (500 Internal Server Error)

##### Validation error
###### 1. Invalid JSON
When the request body is not valid JSON. 
`POST: /person/register`
`Body: qwerty123`
`Response:`
```json                
                {
                    "timestamp": 1507581821032,
                    "success": false,
                    "status": 400,
                    "error": "901",
                    "message": "Message not readable",
                    "lang": "en",
                    "errorInfo": "/errors/901?lang=en"
                }
```
If the body is valid JSON, then the error response contains an array field "validationErrors"
###### 2. Not Null
when required field is null. 
`POST: /person/register`
`Body: {"email":null}`
`Response:`
```json
            {
                "timestamp": 1507580354791,
                "success": false,
                "status": 400,
                "error": "902",
                "message": "Message not valid",
                "lang": "en",
                "errorInfo": "/errors/902?lang=en",
                "validationErrors": [
                    {
                        "object": "singleEmailRequestDto",
                        "field": "email",
                        "message": "May not be null",
                        "lang": "en"
                    }
                ]
            }
```
###### 3. Email Validation
when email is not valid.
`POST: /person/register`
`Body: {"email":"asdf!"}`
`Response:`
```json
            {
                "timestamp": 1507580576754,
                "success": false,
                "status": 400,
                "error": "902",
                "message": "Message not valid",
                "lang": "en",
                "errorInfo": "/errors/902?lang=en",
                "validationErrors": [
                    {
                        "object": "singleEmailRequestDto",
                        "field": "email",
                        "rejectedValue": "asdf!",
                        "message": "Not a well-formed email address",
                        "lang": "en"
                    }
                ]
            }
```
###### 4. Array Size Validation
when array size constraint not match. 
For example:
`POST: /friend/connect`
`Body: {"friends":[]}`
`Response:`
```json
            {
                "timestamp": 1507581448284,
                "success": false,
                "status": 400,
                "error": "902",
                "message": "Message not valid",
                "lang": "en",
                "errorInfo": "/errors/902?lang=en",
                "validationErrors": [
                    {
                        "object": "listOfTwoEmailsRequestDto",
                        "field": "friends",
                        "rejectedValue": [],
                        "message": "Size must be between 2 and 2",
                        "lang": "en"
                    }
                ]
            }
```
##### Business Error
###### 1. EmailRegisterRepeatedApiException:
when registering (`/person/register`) an email which is already registered
`Response:`
```json 
            {
                "timestamp": 1507579748856,
                "success": false,
                "status": 409,
                "error": "903",
                "message": "No need to register again, email already registered",
                "lang": "en",
                "errorInfo": "/errors/903?lang=en"
            }
````
###### 2. FriendConnectionRequestRepeatedApiException
when the requested friend connection (`/friend/connect`) has already friend
`Response:`
```json
                {
                    "timestamp": 1507582637950,
                    "success": false,
                    "status": 409,
                    "error": "905",
                    "message": "No need to request friend connection again, they already friend",
                    "lang": "en",
                    "errorInfo": "/errors/905?lang=en"
                }
```
###### 3. BlockingRequestRepeatedApiException
when request blocking has already blocked
`Response:`
```json
                {
                    "timestamp": 1507582637950,
                    "success": false,
                    "status": 409,
                    "error": "907",
                    "message": "No need to request blocking again, email already blocked",
                    "lang": "en",
                    "errorInfo": "/errors/907?lang=en"
                }
```
###### 4. SubscriptionRequestRepeatedApiException
when request subscription has already subscribed
`Response:`
```json
                {
                    "timestamp": 1507582637950,
                    "success": false,
                    "status": 409,
                    "error": "906",
                    "message": "No need to request subscription again, email already subscribed",
                    "lang": "en",
                    "errorInfo": "/errors/906?lang=en"
                }
```
###### 5. BlockedFriendApiException
when requesting friend connection (`/friend/connect`) which are blocked
`Response:`
```json
                {
                    "timestamp": 1507582637950,
                    "success": false,
                    "status": 409,
                    "error": "908",
                    "message": "Blocked friend",
                    "lang": "en",
                    "errorInfo": "/errors/908?lang=en"
                }
```
##### Unexpected Error
This error's stack trace is saved and shown in errorInfo page
`Repsonse:`
```json
                {
                    "timestamp": 1507581821032,
                    "success": false,
                    "status": 500,
                    "error": "900",
                    "message": "NullPointerException",
                    "lang": "en",
                    "errorInfo": "/errors/900?lang=en&logId=1"
                }
```