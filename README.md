# spring-mvc-demo

To build and run the application without the need to deploy to Tomcat run mvn spring-boot:run this will build and launch the application using an embedded tomcat server. The application will then be accessible at http://localhost:8080/person

Deploying to a Tomcat server run mvn install and then copy the spring-mvc-demo-0.0.1-SNAPSHOT.war to your Tomcat webapps folder. The application will then be accessible at http://localhost:8080/spring-mvc-demo-0.0.1-SNAPSHOT/person

