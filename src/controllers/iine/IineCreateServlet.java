package controllers.iine;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Iine;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class EmployeesCreateServlet
 */
@WebServlet("/iine/create")
public class IineCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public IineCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Iine i = new Iine();

            i.setEmployee((Employee)request.getSession().getAttribute("login_employee"));

            Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

            i.setReport((Report)r);



             em.getTransaction().begin();
             em.persist(i);
             em.getTransaction().commit();
             em.close();
             request.getSession().setAttribute("flush", "いいねしました。");

             request.setAttribute("report", r);
             request.setAttribute("_token", request.getSession().getId());
             request.setAttribute("iine.report", i);

             if(request.getSession().getAttribute("flush") != null) {
                 request.setAttribute("flush", request.getSession().getAttribute("flush"));
                 request.getSession().removeAttribute("flush");
             }


             //response.sendRedirect(request.getContextPath() + "/reports/show?id = report.id"+ request.getParameter("id"));

             RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
             rd.forward(request, response);
          }
    }

}
