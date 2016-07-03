# UserAccountManager's setup steps:

What you will need:
- tomcat 8
- mysql db
- java 8
- maven

Installation:

1) Build and deploy the project on a tomcat server;
2) Copy the /src/resources/static folder to the tomcat's \webapps folder;
2) Update the application.properties, so that the "db.url" matches your connection string;
3) run the "create_table.sql" script on your db;
4) Delete the default <context> tags and add the following ones in the tomcat's server.xml:
<Context docBase="UserAccountManager" path="/api" reloadable="true" source="org.eclipse.jst.jee.server:UserAccountManager"/>
<Context docBase="{{tomcatDir}}\webapps\static" path="/"/>
      