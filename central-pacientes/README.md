# Central de Pacientes

Aplicación en **Java** que administra la información de pacientes utilizando una **lista enlazada simple**.  
Incluye una interfaz gráfica en **Swing** para gestionar los datos.

## Funcionalidad

El sistema permite:

- **Registrar pacientes** con ID único, nombre, edad y clínica.
- **Buscar pacientes** por su ID.
- **Modificar pacientes** existentes (nombre, edad o clínica).
- **Eliminar pacientes** registrados.
- **Listar todos** los pacientes almacenados en memoria.
- **Limpiar** el formulario de entrada.

La información se muestra en una tabla que aparece solo cuando hay resultados (al registrar, buscar, modificar o listar).

---

## Operación de la aplicación

### Comportamiento general
- Los datos se almacenan **en memoria** usando una **lista enlazada simple** (nodos y apuntadores).  
- El **ID debe ser único**. Si intentas registrar un ID existente, el sistema lo rechaza con un mensaje.  
- La **tabla**:
  - **No es visible por defecto**.
  - Se **muestra** cuando hay resultados para presentar.
  - Al usar **Listar todos**, muestra **todos** los pacientes.
  - En **Registrar/Buscar/Modificar**, muestra **solo** el paciente afectado.
  - En **Eliminar**:
    - Si la tabla tenía varios, se **refresca** mostrando los restantes.
    - Si solo tenía uno (el eliminado), la tabla se **oculta**.

### Botones y acciones

- **Registrar**
  - Valida: ID no vacío/único, nombre no vacío, edad numérica ≥ 0, clínica no vacía.
  - Si es válido: agrega el paciente a la lista, muestra **solo el nuevo** en la tabla y limpia el formulario.
  - Si hay error: muestra mensaje (ID duplicado o datos inválidos).

- **Buscar**
  - Requiere ID.
  - Si existe: llena el formulario con los datos y muestra **solo ese** paciente en la tabla.
  - Si no existe: muestra aviso y **oculta** la tabla.

- **Modificar**
  - Requiere ID existente.
  - Permite cambiar **nombre**, **edad** (entero) y **clínica**; los campos en blanco **no se modifican**.
  - Si se actualiza: muestra **solo el actualizado** en la tabla.
  - Si el ID no existe: avisa que no se pudo actualizar.

- **Eliminar**
  - Requiere ID.
  - Si elimina:
    - Si la tabla mostraba **varios**, se recarga con **todos menos el eliminado**.
    - Si la tabla mostraba **uno**, se limpia y **se oculta**.
  - Si el ID no existe: avisa “No encontrado”.

- **Listar todos**
  - Carga **toda** la lista en la tabla y la **muestra**.

- **Limpiar**
  - Vacía los campos del formulario y limpia la selección de la tabla (no modifica la lista).

### Notas
- No hay persistencia a disco: al cerrar la app, los datos se pierden (enfoque académico de estructuras de datos).
- Las validaciones básicas se manejan con mensajes emergentes (**JOptionPane**).


