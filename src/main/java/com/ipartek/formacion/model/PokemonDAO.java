package com.ipartek.formacion.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.ipartek.formacion.model.pojo.Habilidad;
import com.ipartek.formacion.model.pojo.Pokemon;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.log.Log;

public class PokemonDAO implements IDAO<Pokemon> {

	private static PokemonDAO INSTANCE;

	private final static Logger LOG = LogManager.getLogger(PokemonDAO.class);

	private final String SLQ_GET_ALL = "SELECT p.id 'pokemonId', p.nombre 'pokemonNombre' , h.id 'habilidadId', h.nombre 'habilidadNombre' FROM ( pokemon p LEFT JOIN pokemon_has_habilidad phh ON p.id = phh.pokemonId ) LEFT JOIN habilidad h ON h.id = phh.habilidadId ORDER BY p.id ASC LIMIT 500;";
	private final String SQL_GET_BYID = "SELECT p.id 'pokemonId', p.nombre 'pokemonNombre' , h.id 'habilidadId', h.nombre 'habilidadNombre' FROM ( pokemon p LEFT JOIN pokemon_has_habilidad phh ON p.id = phh.pokemonId ) LEFT JOIN habilidad h ON h.id = phh.habilidadId WHERE p.id = ? ORDER BY p.id ASC LIMIT 500;";
	private final String SQL_GET_BYNAME = "SELECT p.id 'pokemonId', p.nombre 'pokemonNombre' , h.id 'habilidadId', h.nombre 'habilidadNombre' FROM habilidad h, pokemon p, pokemon_has_habilidad phh WHERE phh.pokemonId = p.id AND phh.habilidadId = h.id AND p.nombre LIKE ? ORDER BY p.id ASC LIMIT 500;";

	private final String SQL_INSERT = "INSERT INTO pokemon(nombre) VALUES (?);";
	private final String SQL_UPDATE = "UPDATE pokemon SET nombre = ? WHERE id = ?";

	private final String SQL_DELETE= "DELETE FROM pokemon WHERE id = ?;";

	private PokemonDAO() {
		super();
	}

	public static synchronized PokemonDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PokemonDAO();
		}
		return INSTANCE;
	}

	@Override
	public List<Pokemon> getAll() {

		List<Pokemon> resul = null;

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SLQ_GET_ALL);
				ResultSet rs = pst.executeQuery() ) {
			resul = mapper(rs);
		} catch (Exception e) {
			LOG.error(e);
		}

		return resul;
	}

	@Override
	public Pokemon getById(int id) {
		Pokemon resul = null;
		try(Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_BYID);
				) {
			pst.setInt(1, id);
			try(ResultSet rs = pst.executeQuery()) {
				if(rs.next()) {
					resul = new Pokemon();
					resul.setId(rs.getInt("pokemonId"));
					resul.setNombre(rs.getString("pokemonNombre"));

					List<Habilidad> habilidades = resul.getHabilidades();
					Habilidad habilidad;
					do {
						habilidad = new Habilidad();
						habilidad.setId(rs.getInt("habilidadId"));
						habilidad.setNombre(rs.getString("habilidadNombre"));
						habilidades.add(habilidad);
					} while (rs.next());
				}
			}

		} catch (Exception e) {
			LOG.error(e);
		}

		// TODO Auto-generated method stub
		return resul;
	}

	@Override
	public Pokemon delete(int id) throws Exception {
		Pokemon resul = getById(id);

		try(Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_DELETE)) {
			pst.setInt(1, id);

			int affectedRows = pst.executeUpdate();

			if(affectedRows == 1) {
				LOG.info("Pokemon borrado correctamente de la BD");
			} else {
				LOG.info("Parece que se ha borrado " + affectedRows + " pokemons");
				resul = null;
			}

		} catch (Exception e) {
			LOG.error(e);
		}
		return resul;
	}

	@Override
	public Pokemon update(int id, Pokemon pojo) throws Exception {
		Connection con = ConnectionManager.getConnection();
		con.setAutoCommit(false);
		try(PreparedStatement pst = con.prepareStatement(SQL_UPDATE)) {
			pst.setString(1, pojo.getNombre());
			pst.setInt(2, id);
			int affectedRows = pst.executeUpdate();
			if(affectedRows == 1) {
				LOG.trace("Actualizado correctamente");

				final String SQL_DELETE = "DELETE FROM pokemon_has_habilidad  WHERE pokemonId = ?;";
				try(PreparedStatement pstDelete = con.prepareStatement(SQL_DELETE)) {
					pstDelete.setInt(1, id);
					LOG.debug(pstDelete);
					int affectedRowsDelete =   pstDelete.executeUpdate();
					if(affectedRowsDelete >= 0) {
						String SQL = "INSERT INTO pokemon_has_habilidad(pokemonId, habilidadId) VALUES (?, ?);";
						for (Habilidad habilidad: pojo.getHabilidades()) {
							LOG.trace(id + "--" +  habilidad.getId());

							try(PreparedStatement pstHabilidad = con.prepareStatement(SQL)) {
								pstHabilidad.setInt(1, id);
								pstHabilidad.setInt(2, habilidad.getId());
								LOG.trace(pstHabilidad);
								int affectedRows2 = pstHabilidad.executeUpdate();
								if(affectedRows2 == 1) {

								}

							}
						}
					}
				}




				con.commit();
			} else {
				LOG.trace("La actualizacion ha sido incorrecta, se han actualizado " + affectedRows + " filas");
			}
		} catch (Exception e) {
			con.rollback();
			LOG.error(e);
			throw e;
		}
		pojo.setId(id);
		return pojo;
	}

	@Override
	public Pokemon create(Pokemon pojo) throws Exception {
		Pokemon resul = null;
		Connection con = ConnectionManager.getConnection();
		con.setAutoCommit(false);
		try(PreparedStatement pst = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS )
				){

			// insert en tabla pokemon
			pst.setString(1, pojo.getNombre());
			int affectedRows = pst.executeUpdate();
			if(affectedRows == 1) {
				// obtener el id generado
				try(ResultSet rs = pst.getGeneratedKeys()){
					resul = pojo;
					rs.next();
					resul.setId(rs.getInt(1));
					// por cada habilidad insertar en pokemon_has_habilidades
					String SQL = "INSERT INTO pokemon_has_habilidad(pokemonId, habilidadId) VALUES (?, ?);";
					for (Habilidad habilidad: pojo.getHabilidades()) {
						LOG.trace(pojo.getId() + "--" +  habilidad.getId());

						try(PreparedStatement pstHabilidad = con.prepareStatement(SQL)) {
							pstHabilidad.setInt(1, pojo.getId());
							pstHabilidad.setInt(2, habilidad.getId());
							LOG.trace(SQL);
							int affectedRows2 = pstHabilidad.executeUpdate();
							if(affectedRows2 == 1) {

							}

						}
					}
				}
			}

			//si todo funciona bien


			con.commit();
		} catch (Exception e) {
			con.rollback();
			throw e;
		}
		 finally {
			if(con != null) {
				con.close();
			}
		}
		return resul;
	}

	public List<Pokemon> getByName(String nombreP) {

		List<Pokemon> resul = null;
		try( Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_BYNAME);) {

			pst.setString(1, "%" + nombreP + "%");

			try( ResultSet rs = pst.executeQuery();) {
				resul = mapper(rs);
			}

		} catch (Exception e) {
			LOG.error(e);
		}

		return resul;
	}

	private List<Pokemon> mapper( ResultSet rs) throws SQLException {

		Map<Integer, Pokemon> mapPokemons = new HashMap<Integer, Pokemon>();

		while( rs.next() ) {
			// TODO mapper
			int id = rs.getInt("pokemonId");
			String nombre = rs.getString("pokemonNombre");
			LOG.trace(id + " " + nombre);

			Pokemon p;
			if(mapPokemons.containsKey(id)) {
				// Como el pokemon esta, lo obtenemos y le añadimos la habilidad
				p = mapPokemons.get(id);
			} else {
				// Si el pokemon no esta, lo crea
				p = new Pokemon();
				p.setId(id);
				p.setNombre(nombre);

				mapPokemons.put(id, p);
			}

			Habilidad habilidad = new Habilidad();
			habilidad.setId(rs.getInt("habilidadId"));
			habilidad.setNombre(rs.getString("habilidadNombre"));

			if(habilidad.getNombre() != null) {
				p.getHabilidades().add(habilidad);
			}


		}

		return new ArrayList<Pokemon>(mapPokemons.values());

	}

}
