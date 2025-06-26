# VZ_CarRepairService

Backend Setup
Use an IDE of your choice (like Eclipse or IntelliJ IDEA) and simply launch the application.
Alternatively, if you do not have an IDE, you can run it through terminal being inside the folder that contains pom.xml file:

# Compile the application

mvn clean install

# Run the application

mvn spring-boot:run
Databases Setup - Desktop Docker
Once desktop docker is active and running, paste the two following commands in the terminal (Do not forget to change password and username or just use defaults (username root, password YOUR-PASSWORD-GOES-HERE, http://localhost:8081))
docker run --name some-mysql -e MYSQL_ROOT_PASSWORD=YOUR-PASSWORD-GOES-HERE -d -p 3306:3306 mysql:8.0
docker run --name phpmyadmin -d --link some-mysql:db -p 8081:80 phpMyAdmin
