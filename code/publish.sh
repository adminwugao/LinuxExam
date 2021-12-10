#!/bin/bash
javac -classpath /root/tomcat8/webapps/ROOT/WEB-INF/lib/servlet-api.jar:/root/tomcat8/webapps/ROOT/WEB-INF/lib/gson-2.8.9.jar:/root/tomcat8/webapps/ROOT/WEB-INF/lib/jedis-2.9.0.jar /root/CreateStudent.java /root/DeleteStudent.java /root/GetStudentById.java Student.java UpdateStudent.java  GetStudentList.java && mv /root/*.class /root/tomcat8/webapps/ROOT/WEB-INF/classes

bash /root/tomcat8/bin/shutdown.sh
bash /root/tomcat8/bin/startup.sh
