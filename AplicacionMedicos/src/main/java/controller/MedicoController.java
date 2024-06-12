package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.Document;

import repository.informe.InformeRepositoryImpl;
import repository.medico.MedicoRepositoryImpl;

public class MedicoController {

	private final MedicoRepositoryImpl medicoRepositoryImpl = new MedicoRepositoryImpl();
	private final InformeRepositoryImpl informeRespositoryImpl = new InformeRepositoryImpl();

	public Optional<Document> findByDni(String dni) {
		Optional<Document> medico = medicoRepositoryImpl.findById(dni);
		return medico;
	}

	public Boolean eliminarCita(Optional<Document> medicos, String valor) {
		Boolean actualizado = medicoRepositoryImpl.eliminarCita(medicos, "Citas_Abiertas", valor);
		return actualizado;
	}

	public Boolean abrirCitasPaciente(Optional<Document> dni, List<String> citas) {
		Boolean actualizado = medicoRepositoryImpl.abrirCitasMedicas(dni, "Citas_Abiertas", citas);

		return actualizado;
	}

	public String[] citasAbiertas(String dni) {
		String[] dniPacientes = medicoRepositoryImpl.guardarCitasAbiertas(dni);
		return dniPacientes;
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
