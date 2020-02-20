package com.ipartek.formacion.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.ipartek.formacion.model.pojo.Habilidad;

public class HabilidadDAO implements IDAO<Habilidad> {

	private final String SQL_GET_ALL="SELECT h.id 'id', h.nombre 'nombre' FROM habilidad AS h ORDER BY h.id LIMIT 500;";

	private final static Logger LOG = LogManager.getLogger(HabilidadDAO.class);

	private static HabilidadDAO INSTANCE = null;

	private HabilidadDAO() {
		super();
	}

	public static synchronized HabilidadDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new HabilidadDAO();
		}
		return INSTANCE;
	}


	@Override
	public List<Habilidad> getAll() {
		List<Habilidad> habilidades = new ArrayList<Habilidad>();
		try(Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL)) {
			LOG.debug(pst);

			try(ResultSet rs = pst.executeQuery()) {
				while(rs.next()) {
					habilidades.add(mapper(rs));
				}
			}
		} catch(Exception e) {
			LOG.error(e);
		}
		return habilidades;
	}

	private Habilidad mapper(ResultSet rs) throws SQLException {
		Habilidad resul = new Habilidad();

		resul.setId(rs.getInt("id" ));
		resul.setNombre(rs.getString("nombre"));



		return resul;
	}

	@Override
	public Habilidad getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Habilidad delete(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Habilidad update(int id, Habilidad pojo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Habilidad create(Habilidad pojo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
