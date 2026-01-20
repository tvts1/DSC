
package com.ifpe.clinica;

import com.ifpe.clinica.domain.Paciente;
public class Clinica {

    public static void main(String[] args) {
        Paciente p = new Paciente();
        p.setNome("Tassio");
        
        String b = p.getNome();
        System.out.println(b);
    }
}
