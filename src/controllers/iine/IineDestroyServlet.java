package controllers.iine;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Iine;
import utils.DBUtil;

/**
 * Servlet implementation class DestroyServlet
 */
@WebServlet("/iine/destroy")
public class IineDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public IineDestroyServlet() {
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

            Iine i = em.find(Iine.class, (Integer)(request.getSession().getAttribute("iines_count")));

            em.getTransaction().begin();
            em.remove(i);
            em.getTransaction().commit();
            em.close();
            request.getSession().setAttribute("flush", "いいね!を取り消しました。");

            request.setAttribute("_token", request.getSession().getId());

            request.getSession().removeAttribute("iines_count");
            request.getSession().removeAttribute("get_iine");


            response.sendRedirect(request.getContextPath() + "/reports/show?id="+ request.getParameter("id"));

        }
    }
}
