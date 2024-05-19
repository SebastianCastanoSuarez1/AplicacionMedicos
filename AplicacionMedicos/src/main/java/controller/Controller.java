package controller;

import java.util.Optional;

import org.bson.Document;

import repositories.Paciente.PacienteRepositoryImpl;

public class Controller {
	private final PacienteRepositoryImpl pacienteRepositoryImpl = new PacienteRepositoryImpl();

	public Optional<Document> findByDni(String dni) {
		Optional<Document> paciente = pacienteRepositoryImpl.findById(dni);
		return paciente;
	}

	public Boolean existdni(String dni) {
		Optional<Document> paciente = pacienteRepositoryImpl.findById(dni);
		return paciente.isPresent();
	}

	public Boolean savePaciente(Document paciente) {
		return pacienteRepositoryImpl.save(paciente);
	}
	
	public Boolean authenticateUser(String username, String password) {
	    Optional<Document> user = pacienteRepositoryImpl.findByUsernameAndPassword(username, password);
	    return user.isPresent();
	}
	public Boolean updateData(String dni, Document newData) {
	    Optional<Document> paciente = pacienteRepositoryImpl.findById(dni);
	    return paciente.map(p -> pacienteRepositoryImpl.replaceDocument(paciente, newData)).orElse(false);
	}
	

}