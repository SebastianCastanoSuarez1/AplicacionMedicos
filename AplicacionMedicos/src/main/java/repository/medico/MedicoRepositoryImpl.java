package repository.medico;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import static com.mongodb.client.model.Updates.pull;
import db.MongoDB;

public class MedicoRepositoryImpl implements MedicoRepository {

	MongoClient mongoClient = MongoDB.getClient();
	MongoDatabase database = mongoClient.getDatabase("TrabajoMongo");
	MongoCollection<Document> collection = database.getCollection("Medicos");
	String dni = "Dni", nombre = "Nombre", apellidos = "Apellidos", especialidad = "Especialidad",
			fecha_incorporacion = "Fecha_Incorporacion";

	@Override
	public List<Document> findAll() {
		Bson projectionFields = Projections.excludeId();

		MongoCursor<Document> cursor = collection.find().projection(projectionFields).iterator();

		List<Document> documentList = new ArrayList<>();
		try {
			while (cursor.hasNext()) {
				Document document = cursor.next();
				documentList.add(document);
			}
		} finally {
			cursor.close();
		}

		return documentList;
	}

	public String findDniPorDni(String paciente) {
		Bson filter = eq(dni, paciente);
		Document result = collection.find(filter).first();
		Object dniList = result.get(dni);
		return (String) dniList;

	}

	public String findNombrePordni(String paciente) {
		Bson filter = eq(dni, paciente);
		Document result = collection.find(filter).first();
		Object dniList = result.get(nombre);
		return (String) dniList;

	}

	public String findApellidosPordni(String paciente) {
		Bson filter = eq(dni, paciente);
		Document result = collection.find(filter).first();
		Object dniList = result.get(apellidos);
		return (String) dniList;

	}

	public Boolean updatePacientesCargo(String dniMedico, String atributo, List<String> valor) {
		try {
			Bson filter = eq("Dni", dniMedico);
			collection.updateOne(filter, Updates.addEachToSet(atributo, valor));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public String findEspecialidadPordni(String paciente) {
		Bson filter = eq(dni, paciente);
		Document result = collection.find(filter).first();
		Object dniList = result.get(especialidad);
		return (String) dniList;
	}

	public String findFechaIncorporacionPordni(String paciente) {
		Bson filter = eq(dni, paciente);
		Document result = collection.find(filter).first();
		Object dniList = result.get(fecha_incorporacion);
		return (String) dniList;

	}

	public Boolean abrirCitasMedicas(Optional<Document> paciente, String atributo, List<String> citas) {
		try {
			if (paciente.isPresent()) {
				Document filter = paciente.get();
				collection.updateOne(eq("Dni", filter.getString("Dni")), Updates.pushEach(atributo, citas));
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public String[] guardarDniPacientes(String medico) {
		Bson filter = eq(dni, medico);
		Document document = collection.find(filter).first();
		List<String> dniList = (List<String>) document.get("Pacientes_Cargo");
		return dniList.toArray(new String[0]);
	}

	public String pretty(String json) {
		JsonElement je = JsonParser.parseString(json);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(je);
	}

	public String mostrarMedicos(List<Document> medicos) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String pretty = "";
		if (medicos.isEmpty()) {

		} else {
			for (Document doc : medicos) {
				String json = gson.toJson(doc);

				pretty += pretty(json) + "\n";
			}
		}
		return pretty;
	}

	public String mostrar(Optional<Document> medicos) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String pretty = "";
		if (medicos.isEmpty()) {

		} else {
			Document doc1 = medicos.get();
			String json = gson.toJson(doc1);
			pretty += pretty(json) + "\n";
		}
		return pretty;
	}

	@Override
	public Boolean save(Document entity) {
		try {
			collection.insertOne(entity);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Optional<Document> findById(String id) {
		Bson filter = eq(dni, id);
		Bson projectionFields = Projections.excludeId();
		Document result = collection.find(filter).projection(projectionFields).first();
		return Optional.ofNullable(result);
	}

	public Optional<Document> findByContraseña(String contraseña) {

		Bson filter = eq("Contraseña", contraseña);
		Bson projectionFields = Projections.excludeId();
		Document result = collection.find(filter).projection(projectionFields).first();
		return Optional.ofNullable(result);
	}

	public List<Document> findByNombre(String nombre) {

		Bson filter = eq("Nombre", nombre);
		Bson projectionFields = Projections.excludeId();

		List<Document> results = collection.find(filter).projection(projectionFields).into(new ArrayList<>());
		return results;
	}

	@Override
	public DeleteResult delete(String dni) {
		DeleteResult resultado = null;
		try {
			Bson filter = eq("Dni", dni);
			resultado = collection.deleteOne(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public Boolean update(Optional<Document> medico, String atributo, List<String> valores) {
		try {
			if (medico.isPresent()) {
				Document filter = medico.get();
				Document update = new Document("$set", new Document(atributo, valores));
				collection.updateOne(filter, update);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Boolean update(Optional<Document> medico, String atributo, String valor) {
		try {

			if (medico.isPresent()) {
				Document filter = medico.get(); // filtro para seleccionar el documento a actualizar
				Document update = new Document("$set", new Document(atributo, valor));
				collection.updateOne(filter, update);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Boolean update(Optional<Document> medico, String atributo, Document valores) {
		try {
			if (medico.isPresent()) {
				Document filter = medico.get();
				Document update = new Document("$set", new Document(atributo, valores));
				collection.updateOne(filter, update);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Boolean eliminarCita(Optional<Document> paciente, String atributo, String valor) {
		try {
			if (paciente.isPresent()) {
				Document filter = paciente.get();
				collection.updateOne(eq("Dni", filter.getString("Dni")), Updates.pull(atributo, valor));
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

//	public Boolean updatePacientesCargo(Optional<Document> medico, Document historial) {
//		try {
//
//			if (medico.isPresent()) {
//				Document filter = medico.get(); // filtro para seleccionar el documento a actualizar
//				Document update = new Document("$set", new Document(historial));
//				collection.updateOne(filter, update);
//				return true;
//			} else {
//				return false;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}

	public List<Document> findByEspecialidad(String especialidad) {
		Bson filter = eq("Especialidad", especialidad);
		Bson projectionFields = Projections.excludeId();
		List<Document> results = collection.find(filter).projection(projectionFields).into(new ArrayList<>());
		return results;
	}

	public List<Document> findByMedicodeFamilia(String especialidad) {
		Bson filter = eq("Especialidad", especialidad);
		Bson projectionFields = Projections.excludeId();
		List<Document> results = collection.find(filter).projection(projectionFields).into(new ArrayList<>());
		return results;
	}



}
