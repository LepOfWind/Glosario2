package com.glind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataProvider {

    public static HashMap<String, List<String>> getInfo()
    {
        HashMap<String, List<String>> Details = new HashMap<String, List<String>>();

        List<String> Dess = new ArrayList<String>();
        Dess.add("Luis Trigás");
        Dess.add("Héctor Morales");
        Dess.add("José Domínguez");
        Dess.add("Marcelo Maestre");

        List<String> Coo = new ArrayList<String>();
        Coo.add("Msc. Inmaculada R. de Castillo");

        List<String> Org = new ArrayList<String>();
        Org.add("Fondo de Poblacion de las Naciones Unidas");
        Org.add("Universidad Tecnológica de Panamá");
        Org.add("Ministerio de Salud Panamá");
        Org.add("ACUN");
        Org.add("ASMUNG");
        Org.add("PRODESO");

        List<String> Pers = new ArrayList<String>();
        Pers.add("Silvano Molina (Voz)");
        Pers.add("Ana Tejada (Traducción Guna)");
        Pers.add("Marisol Curundama (Traducción Emberá)");
        Pers.add("Criseria Bacorizo (Voz y Traducción Emberá)");
        Pers.add("Jorge Sarsanedas");
        Pers.add("Martha Mesia");
        Pers.add("Anel Rodríguez");
        Pers.add("Bryan Beniche");
        Pers.add("Carlos Ovaldia");
        Pers.add("Carlos Pineda");
        Pers.add("Eilyn Lee");
        Pers.add("Eric Martínez");
        Pers.add("Fernando Urriola");
        Pers.add("Fernando Castillo");
        Pers.add("Germain Rivera");
        Pers.add("Isaac Tapia");
        Pers.add("Jenny Zhang");
        Pers.add("Karen Matínez");
        Pers.add("Maicol Gómes");
        Pers.add("Marta Magallon");
        Pers.add("Mercedes Martínez");
        Pers.add("Moisés Terreros");
        Pers.add("Oscar Molinar");
        Pers.add("Roberto Cordoba");
        Pers.add("Yirelki Jiménez");
        Pers.add("Jorge Girón");
        Pers.add("Isaac Creighton");
        Pers.add("Ericmanuel Hernandez (Consulta Programación)");
        Pers.add("Jose Mario Insturaín");
        Pers.add("Juan Diego Damian");
        Pers.add("Rogger Arauz");



        Details.put("Desarrolladores de la App", Dess);
        Details.put("Coordinadora del Proyecto", Coo);
        Details.put("Instituciones y Organizaciones", Org);
        Details.put("Personas que Contribuyeron", Pers);

        return Details;

    }

}
