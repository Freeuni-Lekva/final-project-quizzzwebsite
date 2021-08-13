import  javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.swing.text.AbstractDocument;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import user.User;

@WebServlet(name = "QuizCreationServlet", value = "/Quiz-CreationServlet")
public class QuizCreationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean checkValidate=true;
        Timestamp tmst=Timestamp.from(Instant.now());
        List<questionParam> lst=new ArrayList<questionParam>();
        String name=request.getParameter("quizName");
        String description=request.getParameter("QuizDescription");
        String correction=request.getParameter("correction");
        String practice=request.getParameter("practice");
        String random=request.getParameter("random");
        String pages=request.getParameter("pages");
         User creatorUser=(User)request.getSession().getAttribute(User.ATTRIBUTE_NAME);
        int creatorID=creatorUser.getId();
        boolean tr=false;
        Enumeration en=request.getParameterNames();
        if(name.equals("")) checkValidate=false;
        if(checkValidate) {
            while (en.hasMoreElements()) {
                String param = (String) en.nextElement();
                if (param.charAt(0) == 't') {
                    tr=true;
                    int type = Character.getNumericValue(param.charAt(param.length() - 1));
                    String txt = request.getParameter(param);
                    String correctAnswer = request.getParameter((String) en.nextElement());

                    if (type == 1 || type == 3) {
                        lst.add(new questionParam(type, "", correctAnswer, txt));
                    } else {
                        String probableAnswers = request.getParameter((String) en.nextElement());
                        lst.add(new questionParam(type, probableAnswers, correctAnswer, txt));
                    }
                }

            }
            for(int i=0; i<lst.size(); i++){
                if(!lst.get(i).checkValidate()){
                    checkValidate=false;
                    break;
                }
            }
        }


        RequestDispatcher reqDisp;
        if(!checkValidate || !tr) {
            reqDisp=request.getRequestDispatcher("FailedCreation.jsp");
            reqDisp.forward(request, response);

        }else {
            quiz addableQuiz =new addQuiz(name,1,tmst,description, pages.equals("yes"),  practice.equals("yes"),  correction.equals("yes"),  random.equals("yes"));
            try {
              quizDao.removeRecordsByUserID(11);
                quizDao.addQUIZ(addableQuiz,lst);
                reqDisp=request.getRequestDispatcher("SuccessfulCreation.jsp");
                reqDisp.forward(request, response);
            } catch (SQLException | ClassNotFoundException throwables) {
                response.sendRedirect("Error.jsp?id=QuizCreationServlet");
                return;
            }

        }



    }
}
