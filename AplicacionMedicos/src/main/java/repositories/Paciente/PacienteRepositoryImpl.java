package repositories.Paciente;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.pull;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

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

	public boolean eliminarCita(String dni, String dniMedico, String fecha) {
		boolean exito = false;
		try {
			Bson filter = eq("Dni", dni);
			Bson update = pull("Citas_Paciente", new Document("DniMedico", dniMedico).append("Fecha", fecha));
			UpdateResult resultado = collection.updateOne(filter, update);
			// Verifica si algún documento fue modificado
			if (resultado.getModifiedCount() > 0) {
				exito = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exito;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> findFechaAlta(String paciente) {
		Bson filter = eq(dni, paciente);
		Document document = collection.find(filter).first();

		ArrayList<Document> enfermedades = (ArrayList<Document>) document.get("Enfermedades");
		ArrayList<String> fecha = new ArrayList<>();

		for (Document obj : enfermedades) {
			if (obj.containsKey("Fecha_Alta")) {
				fecha.add(obj.getString("Fecha_Alta"));
			}
		}

		return fecha;
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

	@SuppressWarnings("unchecked")
	public String[] guardarMedicamentos(String paciente) {
		Bson filter = eq(dni, paciente);
		Document document = collection.find(filter).first();
		List<String> medicamentos = (List<String>) document.get("Medicamentos");
		return medicamentos.toArray(new String[0]);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> findFecha(String medico) {
		Bson filter = eq(dni, medico);
		Document document = collection.find(filter).first();

		ArrayList<Document> enfermedades = (ArrayList<Document>) document.get("Enfermedades");
		ArrayList<String> fecha = new ArrayList<>();

		for (Document obj : enfermedades) {
			if (obj.containsKey("Fecha_Baja")) {
				fecha.add(obj.getString("Fecha_Baja"));
			}
		}

		return fecha;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> findTratamiento(String medico) {
		Bson filter = eq(dni, medico);
		Document document = collection.find(filter).first();

		ArrayList<Document> enfermedades = (ArrayList<Document>) document.get("Enfermedades");
		ArrayList<String> tratamiento = new ArrayList<>();

		for (Document obj1 : enfermedades) {
			if (obj1.containsKey("Detalles")) {
				Document obj2 = (Document) obj1.get("Detalles");
				if (obj2.containsKey("Tratamiento")) {
					tratamiento.add(obj2.getString("Tratamiento"));
				}
			}
		}

		return tratamiento;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> findInforme(String medico) {
		Bson filter = eq(dni, medico);
		Document document = collection.find(filter).first();

		ArrayList<Document> enfermedades = (ArrayList<Document>) document.get("Enfermedades");
		ArrayList<String> informe = new ArrayList<>();

		for (Document obj1 : enfermedades) {
			if (obj1.containsKey("Detalles")) {
				Document obj2 = (Document) obj1.get("Detalles");
				if (obj2.containsKey("Informe")) {
					informe.add(obj2.getString("Informe"));
				}
			}
		}

		return informe;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<ArrayList<String>> findMedicamentosTratamiento(String medico) {
		Bson filter = eq(dni, medico);
		Document document = collection.find(filter).first();

		if (document == null) {
			return new ArrayList<>();
		}

		ArrayList<Document> enfermedades = (ArrayList<Document>) document.get("Enfermedades");
		ArrayList<ArrayList<String>> medicamentos = new ArrayList<>();

		for (Document obj1 : enfermedades) {
			if (obj1.containsKey("Detalles")) {
				Document obj2 = (Document) obj1.get("Detalles");
				if (obj2.containsKey("Medicamentos")) {
					ArrayList<String> medicamento = (ArrayList<String>) obj2.get("Medicamentos");
					medicamentos.add(new ArrayList<>(medicamento));
				} else {
					medicamentos.add(new ArrayList<>());
				}
			} else {
				medicamentos.add(new ArrayList<>());
			}
		}

		return medicamentos;
	}

	@SuppressWarnings("unchecked")
	public String[] findAlergenos(String medico) {
		Bson filter = eq(dni, medico);
		Document result = collection.find(filter).first();
		List<String> alergenos = (List<String>) result.get("Alergenos");
		return alergenos.toArray(new String[0]);

	}

	@SuppressWarnings("unchecked")
	public String[][] findCitasPacientes(String dniPaciente) {
		Bson filter = Filters.eq("dni", dniPaciente);
		Document result = collection.find(filter).first();

		if (result != null) {
			List<Document> citasDocumento = (List<Document>) result.get("Citas_Paciente");
			String[][] citas = new String[citasDocumento.size()][2];

			for (int i = 0; i < citasDocumento.size(); i++) {
				Document cita = citasDocumento.get(i);
				String dniMedico = cita.getString("DniMedico");
				String fecha = cita.getString("Fecha");

				citas[i][0] = dniMedico != null ? dniMedico : "Sin médico asignado";
				citas[i][1] = fecha != null ? fecha : "Fecha no especificada";
			}

			return citas;
		} else {
			return new String[0][0]; // Retornar una matriz vacía si no se encuentra el documento
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> findCitas(String medico) {
		Bson filter = eq(dni, medico);
		Document document = collection.find(filter).first();

		ArrayList<Document> enfermedades = (ArrayList<Document>) document.get("Citas_Paciente");
		ArrayList<String> fecha = new ArrayList<>();

		for (Document obj : enfermedades) {
			if (obj.containsKey("Fecha")) {
				fecha.add(obj.getString("Fecha"));
			}
		}

		return fecha;

	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> findDniMedicoDeCitas(String medico) {
		Bson filter = eq(dni, medico);
		Document document = collection.find(filter).first();

		ArrayList<Document> enfermedades = (ArrayList<Document>) document.get("Citas_Paciente");
		ArrayList<String> fecha = new ArrayList<>();

		for (Document obj : enfermedades) {
			if (obj.containsKey("DniMedico")) {
				fecha.add(obj.getString("DniMedico"));
			}
		}

		return fecha;

	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> findEnfermedad(String medico) {
		Bson filter = eq(dni, medico);
		Document document = collection.find(filter).first();

		ArrayList<Document> enfermedadesLista = (ArrayList<Document>) document.get("Enfermedades");
		ArrayList<String> enfermedades = new ArrayList<>();

		for (Document obj : enfermedadesLista) {
			if (obj.containsKey("Enfermedad")) {
				enfermedades.add(obj.getString("Enfermedad"));
			}
		}

		return enfermedades;
	}

	public Boolean updatePartialData(Optional<Document> paciente, Document newData) {
		try {
			if (paciente.isPresent()) {
				Document filter = paciente.get();
				Document update = new Document("$set", newData);
				collection.updateOne(filter, update);
				return true;
			} else {
				return false; // No se encontró el documento
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false; // Error al actualizar
		}
	}

	public Boolean updateCitas(Optional<Document> paciente, String atributo, Document cita) {
		try {
			if (paciente.isPresent()) {
				Document filter = paciente.get();
				collection.updateOne(eq("Dni", filter.getString("Dni")), Updates.push(atributo, cita));
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Boolean replaceDocument(Optional<Document> optionalOldDocument, Document newDocument) {
		try {
			if (optionalOldDocument.isPresent()) {
				Document oldDocument = optionalOldDocument.get();

				// Iterar sobre las claves del nuevo documento
				for (String key : newDocument.keySet()) {
					// Obtener el valor correspondiente en el nuevo documento
					Object newValue = newDocument.get(key);

					// Verificar si el campo existe en el documento anterior
					if (oldDocument.containsKey(key)) {
						// Si existe, actualizar su valor en el documento anterior
						oldDocument.put(key, newValue);
					}
				}

				// Reemplazar el documento anterior con el documento actualizado
				collection.replaceOne(eq("Dni", oldDocument.getString("Dni")), oldDocument);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Boolean updatePassword(Optional<Document> medico, String atributo, String valor) {
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

	public Boolean modificarCita(String dni, String dniMedico, String fechaOriginal, String fechaNueva) {
		try {
			Document filter = new Document("Dni", dni).append("Citas_Paciente",
					new Document("$elemMatch", new Document("DniMedico", dniMedico).append("Fecha", fechaOriginal)));
			Document update = new Document("$set", new Document("Citas_Paciente.$[e].Fecha", fechaNueva));
			Document arrayFilter = new Document("e.DniMedico", dniMedico).append("e.Fecha", fechaOriginal);
			UpdateOptions options = new UpdateOptions().arrayFilters(Arrays.asList(arrayFilter));
			UpdateResult result = collection.updateOne(filter, update, options);
			return result.getModifiedCount() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
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
