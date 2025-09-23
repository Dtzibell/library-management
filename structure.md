# Library management structure
@startuml
class Book {
    +int ISBN
    +int publicationYear
    +String title
    +String author
    +boolean availability
    +getters()
    +setters(newVar)
}

class Member {
    +Email userEmail
    +String userName
    +String userSurname
    +String userID
    +String userPhoneNumber
    +getters()
    +setters()
    userEmail setter uses Email.setEmail(String input)
    +Member(userName, userSurname, userID, userEmail, userPhoneNumber, conn)
}

class Email {
    +String email
    +String verification
    +of()
    +setEmail(email)
}

class EmailVerificationException <<exception>>

interface PostgreSQL {
    +connect()
    +iterateOverRows()
}

interface ConnData {
    +String uri
    +String user
    +String passw
}

Member *-- Email
Email *-- EmailVerificationException
PostgreSQL *-- ConnData
note top of Member : Clean.
@enduml
