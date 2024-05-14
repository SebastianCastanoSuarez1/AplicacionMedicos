package repositories.Paciente;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.result.DeleteResult;

import db.MongoDB;

public class PacienteRepositoryImpl implements PacienteRepository {

	MongoClient mongoClient = MongoDB.getClient();
	MongoDatabase database = mongoClient.getDatabase("TrabajoMongo");
	MongoCollection<Document> collection = database.getCollection("Pacientes");
	String dni = "Dni", nombre = "Nombre", apellidos = "Apellido", fechaNacimiento = "Fecha_Nacimiento", sexo = "Sexo",
			lugarNacimiento = "Lugar_Nacimiento", altura = "Altura", peso = "Peso", grupo_Sanguineo = "Grupo_Sanguineo",
			enfermedad = "Enfermedad", tipo = "Tipo";

	@Override
	public List<Document> findAll() {
		Bson projectionFields = Projections.fields(Projections.include(dni, nombre, enfermedad, tipo),
				Projections.excludeId());

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

	public Boolean update(Optional<Document> paciente, String atributo, List<String> valores) {
		try {
			if (paciente.isPresent()) {
				Document filter = paciente.get();
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

	public Boolean update(Optional<Document> paciente, String atributo, String valor) {
		try {

			if (paciente.isPresent()) {
				Document filter = paciente.get(); // filtro para seleccionar el documento a actualizar
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

	public Boolean update(Optional<Document> paciente, String atributo, Document valores) {
		try {
			if (paciente.isPresent()) {
				Document filter = paciente.get();
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

	public boolean updateAllData(Optional<Document> paciente, Document newData) {
		try {
			if (paciente.isPresent()) {
				Document filter = paciente.get();
				collection.replaceOne(filter, newData);
				return true;
			} else {
				return false; // No se encontró el documento
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false; // Error al actualizar
		}
	}

	public List<Document> findByAttribute(String atributo, String valor) {
		Bson filter = eq(atributo, valor);
		Bson projectionFields = Projections.excludeId();
		List<Document> results = collection.find(filter).projection(projectionFields).into(new ArrayList<>());
		return results;
	}

	public Optional<Document> findByUsernameAndPassword(String username, String password) {
		Bson filter = Filters.and(Filters.eq("Dni", username), Filters.eq("Contraseña", password));
		Bson projectionFields = Projections.excludeId();
		Document result = collection.find(filter).projection(projectionFields).first();
		return Optional.ofNullable(result);
	}

}
