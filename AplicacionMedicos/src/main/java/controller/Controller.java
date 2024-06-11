package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;

import com.mongodb.client.result.DeleteResult;

import repositories.Paciente.PacienteRepositoryImpl;
import repository.medico.MedicoRepositoryImpl;

public class Controller {
	private final PacienteRepositoryImpl pacienteRepositoryImpl = new PacienteRepositoryImpl();
	private final MedicoRepositoryImpl medicoRepositoryImpl = new MedicoRepositoryImpl();

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

	public Boolean anadirContraseña(String dni, String atributo, String valor) {
		Optional<Document> medicos;

		medicos = pacienteRepositoryImpl.findById(dni);
		Boolean actualizado = pacienteRepositoryImpl.update(medicos, atributo, valor);

		return actualizado;
	}

	public Boolean authenticateUser(String username, String password) {
		Optional<Document> user = pacienteRepositoryImpl.findByUsernameAndPassword(username, password);
		return user.isPresent();
	}

	public String[][] findbyCitasPaciente(String dni) {
		return pacienteRepositoryImpl.findCitasPacientes(dni);
	}

	public boolean eliminarCita(String dni, String dniMedico, String fecha) {
		return pacienteRepositoryImpl.eliminarCita(dni, dniMedico, fecha);
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

	public ArrayList<String> findbyCitas(String dni) {
		ArrayList<String> medico = pacienteRepositoryImpl.findCitas(dni);
		return medico;
	}

	public ArrayList<String> findDniMedicobyCitas(String dni) {
		ArrayList<String> medico = pacienteRepositoryImpl.findDniMedicoDeCitas(dni);
		return medico;
	}

	public String findNombreMedicoPorDni(String nombre) {
		String medico = medicoRepositoryImpl.findNombrePordni(nombre);
		return medico;
	}

	public String findApellidoMedicoPorDni(String apellido) {
		String medico = medicoRepositoryImpl.findApellidosPordni(apellido);
		return medico;
	}

	public String findEspecialidadMedicoPorDni(String especialidad) {
		String medico = medicoRepositoryImpl.findEspecialidadPordni(especialidad);
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

	public Boolean addCitasPaciente(Optional<Document> dni, Document cita) {
		Boolean actualizado = pacienteRepositoryImpl.updateCitas(dni, "Citas_Paciente", cita);
		return actualizado;
	}

	public Boolean modificarCita1(String dni, String dniMedico, String fechaOriginal, String fechaNueva) {
	    Boolean actualizado = pacienteRepositoryImpl.modificarCita1(dni, dniMedico, fechaOriginal, fechaNueva);
	    return actualizado;
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