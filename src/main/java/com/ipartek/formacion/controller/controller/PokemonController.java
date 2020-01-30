package com.ipartek.formacion.controller.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.Utilities;

import com.google.gson.Gson;
import com.ipartek.formacion.controller.utils.Utilidades;
import com.ipartek.formacion.model.PokemonDAO;
import com.ipartek.formacion.model.pojo.Pokemon;

/**
 * Servlet implementation class PokemonController
 */
@WebServlet("/api/pokemon/*")
public class PokemonController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static PokemonDAO dao;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dao = PokemonDAO.getInstance();
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		dao = null;
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");

		super.service(request, response);

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int id = -1;
		String nombre = request.getParameter("nombre");
		int status = 200;

		Object objetoRespuesta = null;
		try {
			id = Utilidades.obtenerId(request.getPathInfo());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Pokemon> pokemons;
		if (nombre != null && nombre.length() != 0) {
			pokemons = dao.getByName(nombre);
			if (pokemons.isEmpty()) {
				status = HttpServletResponse.SC_NO_CONTENT;
			}
			objetoRespuesta = pokemons;
		} else if (id == -1) {
			pokemons = dao.getAll();
			if (pokemons.isEmpty()) {
				status = HttpServletResponse.SC_NO_CONTENT;
			}
			objetoRespuesta = pokemons;
		} else {
			objetoRespuesta = dao.getById(id);
			if (objetoRespuesta == null) {
				status = HttpServletResponse.SC_NOT_FOUND;
			}

		}

		response.setStatus(status);
		if (objetoRespuesta != null) {
			try (PrintWriter out = response.getWriter()) {

				Gson json = new Gson();
				out.print(json.toJson(objetoRespuesta));
				out.flush();

			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
	}

}
