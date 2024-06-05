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

	public Boolean addCitasPaciente(Optional<Document> paciente, List<String> citas) {
		return medicoRepositoryImpl.abrirCitasMedicas(paciente, "Citas_Paciente", citas);
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
//	public String[] findMedicamentosPaciente(String dni) {
//		String[] medico = pacienteRepositoryImpl.findMedicamentos(dni);
//		return medico;
//	}

	public ArrayList<ArrayList<String>> findMedicamentosTratamiento(String dni) {
		return pacienteRepositoryImpl.findMedicamentosTratamiento(dni);
	}

	public ArrayList<String> findEnfermedad(String nombre) {
		ArrayList<String> medico = pacienteRepositoryImpl.findEnfermedad(nombre);
		return medico;
	}

	public ArrayList<byte[]> findInformeJaspersoft(String nombre) {
		ArrayList<byte[]> medico = informeRespositoryImpl.verInformes(nombre);
		return medico;
	}

//	public ArrayList<String> findInforme(String nombre) {
//		ArrayList<String> medico = pacienteRepositoryImpl.findInformeHistorialMedico(nombre);
//		return medico;
//	}
	public ArrayList<String> findHoraCreacion(String nombre) {
		ArrayList<String> medico = informeRespositoryImpl.findFechaCreacion(nombre);
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

	public List<Document> findEspecialdidad(String Especialidad) {
		List<Document> medico = medicoRepositoryImpl.findByEspecialidad(Especialidad);
		return medico;
	}

	public String findDniMedicoPorDni(String nombre) {
		String medico = medicoRepositoryImpl.findDniPorDni(nombre);
		return medico;
	}

	public String findNombreMedicoPorDni(String nombre) {
		String medico = medicoRepositoryImpl.findNombrePordni(nombre);
		return medico;
	}

	public String findApellidosMedicoPorDni(String nombre) {
		String medico = medicoRepositoryImpl.findApellidosPordni(nombre);
		return medico;
	}

	public String findEspecialidadPorDni(String nombre) {
		String medico = medicoRepositoryImpl.findEspecialidadPordni(nombre);
		return medico;
	}

	public String findFechaIncoporacionPorDni(String nombre) {
		String medico = medicoRepositoryImpl.findFechaIncorporacionPordni(nombre);
		return medico;
	}

//	public String findDniPacientePorDni(String nombre) {
//		String medico = pacienteRepositoryImpl.findDniPorDni(nombre);
//		return medico;
//	}
//
//	public String findNombrePacientePorDni(String nombre) {
//		String medico = pacienteRepositoryImpl.findNombrePordni(nombre);
//		return medico;
//	}
//
//	public String findApellidosPacientePorDni(String nombre) {
//		String medico = pacienteRepositoryImpl.findApellidosPordni(nombre);
//		return medico;
//	}
//
	// public String findFechaNacimientoPorDni(String nombre) {
//		String medico = pacienteRepositoryImpl.findFechaNacimientoPordni(nombre);
//		return medico;
//	}
//
//	public String findSexoPorDni(String nombre) {
//		String medico = pacienteRepositoryImpl.findSexoPordni(nombre);
//		return medico;
//	}
//
//	public String findLugarNacimientoPorDni(String nombre) {
//		String medico = pacienteRepositoryImpl.findLugarNacimientoPordni(nombre);
//		return medico;
//	}
//
//	public Integer findAlturaPorDni(String nombre) {
//		Integer medico = pacienteRepositoryImpl.findAlturaPordni(nombre);
//		return medico;
//	}
//
//	public Integer findPesoPorDni(String nombre) {
//		Integer medico = pacienteRepositoryImpl.findPesoPordni(nombre);
//		return medico;
//	}
//
//	public String findGrupoSanguineoPorDni(String nombre) {
//		String medico = pacienteRepositoryImpl.findGrupoSanguineoPordni(nombre);
//		return medico;
//	}
//
//	public String findEnfermedadIngreso(String nombre) {
//		String medico = pacienteRepositoryImpl.findEnfermedadIngreso(nombre);
//		return medico;
//	}
//
//	public String findTipo(String nombre) {
//		String medico = pacienteRepositoryImpl.findTipoEnfermedad(nombre);
//		return medico;
//	}
//
//	public String findFechaIngreso(String nombre) {
//		String medico = pacienteRepositoryImpl.findFechaIngreso(nombre);
//		return medico;
//	}
//
//	public String findDniMedico(String nombre) {
//		String medico = pacienteRepositoryImpl.findDniMedico(nombre);
//		return medico;
//	}
//
//	public ArrayList<String> findHoraCreacion(String nombre) {
//		ArrayList<String> medico = informeRespositoryImpl.findFechaCreacion(nombre);
//		return medico;
//	}
//
//	public Boolean addCitasPaciente(Optional<Document> dni, List<String> citas) {
//		Boolean actualizado = pacienteRepositoryImpl.updateCitasMedicos(dni, "Citas_Paciente", citas);
//
//		return actualizado;
//	}
//
//	public Boolean anadirInforme(Optional<Document> dni, byte[] pdfBytes, String hora) {
//		Boolean actualizado = informeRespositoryImpl.guardarInforme(dni, pdfBytes, hora);
//
//		return actualizado;
//	}
//
//	public Boolean eliminarDiagnostico(Optional<Document> dni, String enfermedad, String tipo, String fecha) {
//		Boolean actualizado = pacienteRepositoryImpl.eliminarEnfermedadIngreso(dni, enfermedad, tipo, fecha);
//
//		return actualizado;
//	}
//
//	public Boolean actualizarEnfermedadYTipo(Optional<Document> paciente, String enfermedad, String tipo,
//			String fecha) {
//		return pacienteRepositoryImpl.updateEnfermedadYTipo(paciente, enfermedad, tipo, fecha);
//	}

	public Boolean abrirCitasPaciente(Optional<Document> dni, List<String> citas) {
		Boolean actualizado = medicoRepositoryImpl.abrirCitasMedicas(dni, "Citas_Abiertas", citas);
		return actualizado;
	}

	public Optional<Document> findByDniPaciente(String dni) {
		Optional<Document> paciente = pacienteRepositoryImpl.findById(dni);

		return paciente;
	}

//	public String[] findbyCitasPaciente(String dni) {
//		String[] medico = pacienteRepositoryImpl.findCitasPacientes(dni);
//		return medico;
//	}

	public String[] dniPacientes(String dni) {
		String[] dniPacientes = medicoRepositoryImpl.guardarDniPacientes(dni);
		return dniPacientes;
	}

	public String[] medicamentos(String dni) {
		String[] dniPacientes = pacienteRepositoryImpl.guardarMedicamentos(dni);
		return dniPacientes;
	}

	public String[] informes(String dni) {
		String[] dniPacientes = informeRespositoryImpl.guardaInformes(dni);
		return dniPacientes;
	}

	public Optional<Document> comprobarDni(String dni) {
		Optional<Document> medicos = medicoRepositoryImpl.findById(dni);
		return medicos;

	}

	public String mostrar(List<Document> medicos) {
		String ensenar = medicoRepositoryImpl.mostrarMedicos(medicos);
		return ensenar;
	}

	public String mostrar(Optional<Document> medicos) {
		String ensenar = medicoRepositoryImpl.mostrar(medicos);
		return ensenar;
	}

	public String mostrar(String mensaje) {
		return mensaje;
	}

}
