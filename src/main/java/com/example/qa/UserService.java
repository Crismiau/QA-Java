package com.example.qa;

public class UserService {
    private final UserRepository repo;
    // aqui yo hago referencia al interfaz y lo guardo en una variable

    public UserService(UserRepository repo) {
        this.repo = repo;
    } // aqui creo el constructor para copiarlo o simularlo en pro de mockito

    public boolean register(String email) {
        if (repo.existsByEmail(email))
            return false;

            /*
                La lógica del metodo register:
                llamo el interfaz y a su vez con el . me traigo el metodo para verificar la existencia del correo
                Si ese metodo devuelve true → significa que el correo YA EXISTE → entonces register devuelve false (no se puede registrar).
                Si ese metodo devuelve false → significa que el correo NO EXISTE → entonces register devuelve true (se puede registrar).

             */
        return true;
    }
}
