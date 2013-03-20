A sample web application using Spring 3.x MVC and jQuery

Steps to use the application:

1. git clone git@github.com:yashmalik/weather_app.git
2. Ensure that you have JDK 1.7 and Maven 3 installed and in classpath
3. Ensure that you have Apache Tomcat 7.x installed
4. cd weather_app
5. mvn clean test (To ensure that tests run fine)
6. To deploy the app to tomcat:
    1. Run mvn clean package
    2. cp target/weather_app-1.0-SNAPSHOT.war ${tomcat_dir}/webapps/weather.war
7. To run the app
    1. cd ${tomcat_dir}/bin
    2. sh catalina.sh run
8. Open http://localhost:8080/weather/weather.html to access the app