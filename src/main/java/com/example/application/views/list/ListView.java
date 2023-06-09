package com.example.application.views.list;

import com.example.application.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.example.application.Cita;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@PageTitle("CITAS MEDICAS") // Título de la página
@Route(value = "", layout = MainLayout.class) // Ruta de la página y diseño principal utilizado
public class ListView extends VerticalLayout {
    private List<Cita> citas; // Lista para almacenar las citas
    private Grid<Cita> grid; // Cuadrícula para mostrar las citas en forma de tabla
    private TextField nombreField; // Campo de texto para el nombre
    private TextField apellidoField; // Campo de texto para el apellido
    private TextField cedulaField; // Campo de texto para la cédula
    private DatePicker fechaPicker; // Selector de fecha
    private TextField horaField; // Campo de texto para la hora
    private TextField historiaClinicaField; // Campo de texto para la historia clínica

    public ListView() {
        citas = new ArrayList<>(); // Inicializa la lista de citas
        grid = new Grid<>(Cita.class); // Crea una cuadrícula con el tipo de clase Cita
        nombreField = new TextField("Nombre"); // Crea un campo de texto con la etiqueta "Nombre"
        apellidoField = new TextField("Apellido"); // Crea un campo de texto con la etiqueta "Apellido"
        cedulaField = new TextField("Cédula"); // Crea un campo de texto con la etiqueta "Cédula"
        fechaPicker = new DatePicker("Fecha"); // Crea un selector de fecha con la etiqueta "Fecha"
        horaField = new TextField("Hora"); // Crea un campo de texto con la etiqueta "Hora"
        historiaClinicaField = new TextField("Historia Clínica"); // Crea un campo de texto con la etiqueta "Historia Clínica"

        fechaPicker.setLocale(Locale.ENGLISH); // Establece el idioma a inglés para evitar problemas con el formato de fecha

        Button agendarButton = new Button("Agendar cita", e -> agendarCita()); // Crea un botón "Agendar cita" y define su acción al hacer clic
        Button buscarButton = new Button("Buscar cita", e -> buscarCita()); // Crea un botón "Buscar cita" y define su acción al hacer clic
        Button guardarButton = new Button("Guardar citas", e -> guardarCitasEnArchivo()); // Crea un botón "Guardar citas" y define su acción al hacer clic

        FormLayout formLayout = new FormLayout(); // Crea un diseño de formulario
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1), // En pantallas pequeñas, mostrar 1 columna
                new FormLayout.ResponsiveStep("600px", 2) // En pantallas más grandes, mostrar 2 columnas
        );
        formLayout.add(nombreField, apellidoField, cedulaField); // Agrega los campos de texto al formulario
        formLayout.add(fechaPicker, horaField); // Agrega el selector de fecha y el campo de texto de hora al formulario
        formLayout.add(historiaClinicaField); // Agrega el campo de texto de historia clínica al formulario

        VerticalLayout titleWrapper = new VerticalLayout(); // Crea un diseño vertical para el título
        titleWrapper.setAlignItems(Alignment.CENTER); // Centra los elementos verticalmente en el diseño
        titleWrapper.getStyle().set("background-color", "blue"); // Establece el color de fondo del diseño en azul
        titleWrapper.getStyle().set("color", "white"); // Establece el color del texto en blanco
        titleWrapper.getStyle().set("padding", "10px"); // Agrega un espacio de relleno de 10 píxeles alrededor del diseño
        titleWrapper.getStyle().set("font-size", "24px"); // Establece el tamaño de fuente en 24 píxeles
        titleWrapper.add("CITAS MEDICAS"); // Agrega el título "CITAS MEDICAS" al diseño

        add(titleWrapper, formLayout, agendarButton, buscarButton, guardarButton, grid); // Agrega todos los elementos al diseño vertical principal
    }

    private void agendarCita() {
        String nombre = nombreField.getValue(); // Obtiene el valor del campo de texto del nombre
        String apellido = apellidoField.getValue(); // Obtiene el valor del campo de texto del apellido
        String cedula = cedulaField.getValue(); // Obtiene el valor del campo de texto de la cédula
        LocalDate fecha = fechaPicker.getValue(); // Obtiene el valor seleccionado del selector de fecha
        String hora = horaField.getValue(); // Obtiene el valor del campo de texto de la hora
        String historiaClinica = historiaClinicaField.getValue(); // Obtiene el valor del campo de texto de la historia clínica

        if (!nombre.isEmpty() && !apellido.isEmpty() && !cedula.isEmpty() && fecha != null && !hora.isEmpty() && !historiaClinica.isEmpty()) {
            // Verifica que todos los campos estén completos
            Cita cita = new Cita(nombre, apellido, cedula, fecha, hora, historiaClinica); // Crea una nueva instancia de Cita con los datos ingresados
            citas.add(cita); // Agrega la cita a la lista
            grid.setItems(citas); // Actualiza la cuadrícula con las citas

            Notification.show("Cita agendada con éxito"); // Muestra una notificación de éxito
            limpiarCampos(); // Limpia los campos del formulario
        } else {
            Notification.show("Por favor, complete todos los campos"); // Muestra una notificación de error si algún campo está vacío
        }
    }

    private void buscarCita() {
        String cedula = cedulaField.getValue(); // Obtiene el valor del campo de texto de la cédula

        if (!cedula.isEmpty()) {
            // Verifica que la cédula no esté vacía
            for (Cita cita : citas) {
                // Itera sobre cada cita en la lista
                if (cita.getCedula().equals(cedula)) {
                    // Si la cédula de la cita coincide con la cédula buscada
                    Notification.show("Nombre: " + cita.getNombre() + " " + cita.getApellido() +
                            ", Cédula: " + cita.getCedula() +
                            ", Fecha: " + cita.getFecha() +
                            ", Hora: " + cita.getHora() +
                            ", Historia Clínica: " + cita.getHistoriaClinica()); // Muestra los detalles de la cita encontrada en una notificación
                    return; // Finaliza la búsqueda
                }
            }
            Notification.show("Cita no encontrada"); // Muestra una notificación si no se encuentra ninguna cita con la cédula buscada
        } else {
            Notification.show("Por favor, ingrese el número de cédula"); // Muestra una notificación de error si el campo de cédula está vacío
        }
    }

    private void guardarCitasEnArchivo() {
        try (FileWriter fileWriter = new FileWriter("citas.txt")) {
            // Crea un escritor de archivos llamado "fileWriter" para escribir en el archivo "citas.txt"
            for (Cita cita : citas) {
                // Itera sobre cada cita en la lista
                fileWriter.write("Nombre del paciente: " + cita.getNombre() + " " + cita.getApellido() + "\n");
                fileWriter.write("Número de cédula: " + cita.getCedula() + "\n");
                fileWriter.write("Fecha de la cita: " + cita.getFecha() + "\n");
                fileWriter.write("Hora de la cita: " + cita.getHora() + "\n");
                fileWriter.write("Historia clínica: " + cita.getHistoriaClinica() + "\n");
                fileWriter.write("\n");
            }
            Notification.show("Citas guardadas en el archivo citas.txt"); // Muestra una notificación de éxito
        } catch (IOException e) {
            Notification.show("Error al guardar las citas en el archivo"); // Muestra una notificación de error si ocurre una excepción de E/S
        }
    }

    private void limpiarCampos() {
        nombreField.clear(); // Limpia el campo de texto del nombre
        apellidoField.clear(); // Limpia el campo de texto del apellido
        cedulaField.clear(); // Limpia el campo de texto de la cédula
        fechaPicker.clear(); // Limpia el selector de fecha
        horaField.clear(); // Limpia el campo de texto de la hora
        historiaClinicaField.clear(); // Limpia el campo de texto de la historia clínica
    }
}
