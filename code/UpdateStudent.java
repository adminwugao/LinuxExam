import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.gson.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import com.google.gson.reflect.TypeToken;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/UpdateStudent")
public class UpdateStudent extends HttpServlet {
   final static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
   final static String URL = "jdbc:mysql://180.76.168.134/linux_final";
   static final String USER = "root";
   static final String PASS = "Gw20001007.";
   final static String SQL_UPDATE_Student = "UPDATE t_student SET age=? WHERE name=?";

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");

      Student req = getRequestBody(request);
      getServletContext().log(req.toString());
      PrintWriter out = response.getWriter();

      out.println(updateStu(req));
      out.flush();
      out.close();
   }

   private Student getRequestBody(HttpServletRequest request) throws IOException {
      Student stu = new Student();
      StringBuffer bodyJ = new StringBuffer();
      String line = null;
      BufferedReader reader = request.getReader();
      while ((line = reader.readLine()) != null)
         bodyJ.append(line);
      Gson gson = new Gson();
      stu = gson.fromJson(bodyJ.toString(), new TypeToken<Student>() {
      }.getType());
      return stu;
   }

   private int updateStu(Student req) {
      Connection conn = null;
      PreparedStatement stmt = null;
      int retcode = -1;
      try {
         Class.forName(JDBC_DRIVER);
         conn = DriverManager.getConnection(URL, USER, PASS);
         stmt = conn.prepareStatement(SQL_UPDATE_Student);
	 
	 stmt.setInt(1, req.age);
         stmt.setString(2, req.name);
         int row = stmt.executeUpdate();
         if (row > 0)
            retcode = 1;

         stmt.close();
         conn.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (stmt != null)
               stmt.close();
            if (conn != null)
               conn.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
      return retcode;
   }
}

