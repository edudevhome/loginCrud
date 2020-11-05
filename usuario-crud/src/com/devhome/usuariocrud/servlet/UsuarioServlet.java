package com.devhome.usuariocrud.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.devhome.usuariocrud.dao.UsuarioDao;
import com.devhome.usuariocrud.model.Usuario;

/**
 * Servlet implementation class UsuarioServlet
 */
@WebServlet("/")
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsuarioDao usuarioDao;

	public void init() {
		usuarioDao = new UsuarioDao();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertUsuario(request, response);
				break;
			case "/delete":
				deleteUsuario(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateUsuario(request, response);
				break;
			default:
				listUsuario(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void listUsuario(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Usuario> listUsuario = usuarioDao.selectTodosUsuarios();
		request.setAttribute("listUsuario", listUsuario);
		RequestDispatcher dispatcher = request.getRequestDispatcher("usuario-list.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("usuario-form.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Usuario existingUsuario = usuarioDao.selectUsuario(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("usuario-form.jsp");
		request.setAttribute("usuario", existingUsuario);
		dispatcher.forward(request, response);

	}

	private void insertUsuario(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		Integer senha = Integer.parseInt(request.getParameter("senha"));
		Usuario novoUsuario = new Usuario(nome, email, senha);
		usuarioDao.inserirUsuario(novoUsuario);
		response.sendRedirect("list");
	}

	private void updateUsuario(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		Integer senha = Integer.parseInt(request.getParameter("senha"));

		Usuario novoUsuario = new Usuario(id, nome, email, senha);
		usuarioDao.updateUsuario(novoUsuario);
		response.sendRedirect("list");
	}

	private void deleteUsuario(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		usuarioDao.deleteUsuario(id);
		response.sendRedirect("list");

	}

}