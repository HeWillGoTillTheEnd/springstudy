package ch06;

import ch05.ProductService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/studentControl")
public class StudentController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    StudentDAO dao;
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dao = new StudentDAO();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String view = "";

        if(action == null){
            getServletContext().getRequestDispatcher("/studentControl?action=list").forward(req,resp);
        } else {
            switch (action){
                case "list" : view = list(req,resp); break;
                case "insert" : view = insert(req,resp); break;
            }
            getServletContext().getRequestDispatcher("/ch06/"+view).forward(req,resp);
        }
    }


    private String list(HttpServletRequest request, HttpServletResponse response){
        request.setAttribute("students",dao.getAll());
        return "studentInfo.jsp";
    }

    public String insert(HttpServletRequest request,HttpServletResponse response){
        Student s = new Student();
        try{
            BeanUtils.populate(s, request.getParameterMap());
        } catch (Exception e) {
            e.printStackTrace();
        }
        dao.insert(s);
        return list(request, response);
    }
}
