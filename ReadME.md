# SPRING BOOT | JPA | HIBERNATE
## APPLICATION: BOOK MANAGEMENT
### Part 1 - Setup:
* Initialize a new Spring Boot project with following required dependencies:
* Spring Web
* Spring Data JPA
* MySQL Driver
* H2 Database (for testing)
* Make sure you have created a database in MySQL.
* Configure your production MySQL DataSource with application.properties file.
* Change pom.xml H2 dependency to be active in test by changing: <scope>runtime</scope> → <scope>test</scope>
* Create resources directory in test directory.(Should come up as a suggestion once you right-click test folder)
* Configure your H2 database with an application.properties file added in the resource folder under the test folder. (will override the other application.properties file for your tests.)
* Verify that you application starts without errors.

### Part 2 – Adding AppUser and Details entities
1. Create a package for your entity models
2. Create classes AppUser and Details according to Table 1
3. Turn Details into an entity
4. Turn AppUser into an entity
5. Set up the OneToOne relationship
6. Create a package (repository) for the Interfaces.
7. Create interfaces and follow the repository requirments.
8. Test implementations of Repositories.

### Part 3 - Adding Book and BookLoan
1. Create Book according to Table 1.
2. Make Book into an entity. Pick an appropriate strategy for id.
3. Create BookLoan according to Table 1.
4. Make BookLoan into an entity.
a. Pick an appropriate strategy for id.
b. Map relationship to AppUser according to Table 1.
c. Map relationship to Book according to Table 1.
5. Create interfaces and follow the repository requirements.
6. (If you have time) Test repository interfaces.
7. Always commit changes.

### Part 4 - Adding Author
1. Create Author according to Table 1
2. Make Author into an entity. Pick an appropriate strategy for authorId
3. In Book and Author set up the relationship according to Table 1.
4. Create interfaces and follow the repository requirements.
5. (If you have time) Test implemented class of AuthorDao.
6. Always commit changes.

### Part 5 - Setting up OneToMany and finishing up.
1. Add a List of entity “BookLoan” in AppUser
2. Set up OneToMany relationship in AppUser according to Table 1
3. Add method in AppUser to add BookLoan in a bidirectional way.
4. Add method(s) in Book and / or Author to add and remove Books/Authors.

### Optional
1. Calculate the field “dueDate” by using “maxLoanDays” from Book.
2. Add a new field boolean available in Book (Default value should be true). Make sure you set available to false when a BookLoan is added to AppUser. Also make sure you can NOT lend a book that has available set to false.

#### AppUser requirements:
* id generated with the identity strategy
* username need to be unique

#### Details requirements:
* id generated with identity strategy
* email need to be unique

#### AppUserRepository requirements:
* Basic CRUD Operations.
* Find a user by Username.
* Find users by registration date between two specific dates.
* Find users by details id.
* Find user by email ignore case.
* (Optional)Add more as needed.

#### DetailsRepository requirements:
* Basic CRUD Operations.
* Find a details by email.
* Find details by name contains.
* Find details by name ignore-case.
* (Optional)Add more as needed.

#### BookRepository requirements:
* Basic CRUD Operations.
* Find book by isbn ignore case.
* Find book by title contains.
* Find books with maximum loan days less than a specified number.

#### BookLoanRepository requirements:
* Basic CRUD Operations.
* Find book loans by borrower's ID.
* Find book loans by book ID.
* Find book loans that are not yet returned.
* Find overdue book loans (due date is past and not returned).
* Find book loans between specified dates.
* Mark a book loan as returned by its loan ID.

#### AuthorRepository requirements:
* Find authors by their first name.
* Find authors by their last name
* Find authors by their first name or last name containing a keyword.
* Find authors by a book's ID.
* Update an author's name by their ID.
* Delete an author by their ID.