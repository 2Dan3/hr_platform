How to open:
-------
*Edit: MySQL database is automatically created, as well as schema and test data, when the Springboot app is ran. 
--
Springboot Java app:                                                 from an IDE (e.g. IntellijIDEA) open project from its root [hr_platform] and run the candidates.src.main.java.com.hr.management.candidates.CandidatesApplication.java
--
SwaggerUI:                                                           http://localhost:8080/swagger-ui/index.html
--
Thymeleaf (leftover SSR page, before switching frontends to React):  http://localhost:8080/api/candidates/index
--
React:                                                               http://localhost:5173 (navigate to [hr_platform] -> frontend, execute [npm run dev] there)

Personal Note:
-------
Switching frontends from Thymeleaf to React proved to be the most challenging thing in this project, having only learned *in theory about React's inner structure and principles of working a couple of years ago.
Although it proved to be an easy-to-implement lightweight library, it was interesting to get more familiar with it in practice and to compare the time required for a frontend implementation, between React, Thymeleaf and Angular.
If I had more time, I would fix the frontend file structure in React and decouple the components for a more maintainable code.

Edit: From a domain standpoint, everything is fairly standard. The most complex thing in the project is probably just making sure that the Many-to-many entity relationship is handled properly within CRUD operations (as using Spring JPA Cascades did not make sense in this case, I only made sure to find subjects in the database and to then update both of the entities' relationships before saving, therefore avoiding detached state, e.g. when establishing references between already existing entities).
