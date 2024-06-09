package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.Document;

import model.Informe;
import repositories.Paciente.PacienteRepositoryImpl;
import repository.informe.InformeRepositoryImpl;
import repository.medico.MedicoRepositoryImpl;

public class MedicoController {

	private final MedicoRepositoryImpl medicoRepositoryImpl = new MedicoRepositoryImpl();
	private final PacienteRepositoryImpl pacienteRepositoryImpl = new PacienteRepositoryImpl();
	private final InformeRepositoryImpl informeRespositoryImpl = new InformeRepositoryImpl();

	public Optional<Document> findByDni(String dni) {
		Optional<Document> medico = medicoRepositoryImpl.findById(dni);

		return medico;

	}

	public Document anadirDniPaciente(String dni) {
		Document informe;
		informe = new Informe().append("Dni_Paciente", dni);

		return informe;
	}

	public Boolean eliminarCita(Optional<Document> medicos, String valor) {
		Boolean actualizado = medicoRepositoryImpl.eliminarCita(medicos, "Citas_Abiertas", valor);
		return actualizado;
	}

	public Optional<Document> comprobarDniPaciente(String dni) {

		Optional<Document> informe = informeRespositoryImpl.findById(dni);
		return informe;
	}

	public Boolean salvarDniMedico(Document paciente) {
		return informeRespositoryImpl.save(paciente);
	}

	public String[] findAlergenosPaciente(String dni) {
		String[] medico = pacienteRepositoryImpl.findAlergenos(dni);
		return medico;
	}

	public List<Document> findbyEspecialidad(String nombre) {
		List<Document> medico = medicoRepositoryImpl.findByEspecialidad(nombre);
		return medico;
	}

	public Boolean crearPacientesCargo(String dniMedico, String[] dni_Paciente) {

		List<String> pacientes_List = Arrays.asList(dni_Paciente);

		Boolean actualizado = medicoRepositoryImpl.updatePacientesCargo(dniMedico, "Pacientes_Cargo", pacientes_List);
		return actualizado;
	}

	public ArrayList<byte[]> findInformeJaspersoft(String nombre) {
		ArrayList<byte[]> medico = informeRespositoryImpl.verInformes(nombre);
		return medico;
	}

	public ArrayList<String> findHoraCreacion(String nombre) {
		ArrayList<String> medico = informeRespositoryImpl.findFechaCreacion(nombre);
		return medico;
	}
}
