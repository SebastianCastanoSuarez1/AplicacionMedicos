package controller;

import java.util.ArrayList;
import java.util.Optional;

import org.bson.Document;

import com.mongodb.client.result.DeleteResult;

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

	public String[] medicamentos(String dni) {
		String[] dniPacientes = pacienteRepositoryImpl.guardarMedicamentos(dni);
		return dniPacientes;
	}

	public ArrayList<String> findEnfermedad(String nombre) {
		ArrayList<String> medico = pacienteRepositoryImpl.findEnfermedad(nombre);
		return medico;
	}

	public String[] findAlergenos(String dni) {
		String[] medico = pacienteRepositoryImpl.findAlergenos(dni);
		return medico;
	}

	public ArrayList<String> findInforme(String nombre) {
		ArrayList<String> medico = pacienteRepositoryImpl.findInforme(nombre);
		return medico;
	}

	public ArrayList<String> findFecha(String nombre) {
		ArrayList<String> medico = pacienteRepositoryImpl.findFecha(nombre);
		return medico;
	}

	public ArrayList<String> findTratamiento(String nombre) {
		ArrayList<String> medico = pacienteRepositoryImpl.findTratamiento(nombre);
		return medico;
	}

	public ArrayList<ArrayList<String>> findMedicamentosTratamiento(String dni) {
		return pacienteRepositoryImpl.findMedicamentosTratamiento(dni);
	}

	public Boolean actualizarContraseña(Optional<Document> medicos, String atributo, String contraseña) {
		return pacienteRepositoryImpl.update(medicos, atributo, contraseña);
	}

	public DeleteResult eliminarPaciente(String dni) {
		return pacienteRepositoryImpl.delete(dni);
	}

}