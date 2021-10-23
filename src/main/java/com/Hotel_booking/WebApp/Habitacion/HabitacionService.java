package com.Hotel_booking.WebApp.Habitacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class HabitacionService {

    private final HabitacionRepository habitacionRepository;

    @Autowired
    public HabitacionService(HabitacionRepository habitacionRepository) {
        this.habitacionRepository = habitacionRepository;
    }

    public List<Habitacion> getHabitacion() {
        return habitacionRepository.findAll();
    }

    public String agregarNuevaHabitacion(Habitacion hab) {

    if(validarTipoHabitacion(hab.getTipoHabitacion(), hab.getCantidadPersonas())) {
        habitacionRepository.save(hab);
        return "Habitación correctamente ingresada";
    }
    else
        return "Debe ingresar los datos correctos";
    }

    @Transactional
    public String modificarHabitacion(Long ID,
                                    Habitacion habNewInfo) {

        Habitacion hab = habitacionRepository.findById(ID).orElseThrow(() -> new IllegalStateException (mensaje()));

        if (habNewInfo.getNombre().trim().equals(""))
            return "Ingresar un nombre válido";

        if(!validarTipoHabitacion(habNewInfo.getTipoHabitacion(), habNewInfo.getCantidadPersonas()))
            return "El tipo de habitación y la cantidad de personas no concuerdan";


        if (habNewInfo.getTVHabitacion() == null)
            return "Debes seleccionar un tipo de TV";


        if (habNewInfo.getCocinaSN() == null)
            return "Debes seleccionar un valor para la cocina";

        if (habNewInfo.getCamaExtraSN() == null)
            return "Debes seleccionar un el valor de cama extra";

        if (habNewInfo.getPrecioAdulto() < 0)
            return "Debes ingresar un precio para adultos válido";

        if (habNewInfo.getPrecioNinho() < 0)
            return "Debes ingresar un precio para niños válido";

        if (habNewInfo.getPrecioMinimo() <= 0)
            return "Debes ingresar un precio mínimo mayor a cero";


        hab.setNombre(habNewInfo.getNombre());
        hab.setCantidadPersonas(habNewInfo.getCantidadPersonas());
        hab.setTipoHabitacion(habNewInfo.getTipoHabitacion());
        hab.setTVHabitacion(habNewInfo.getTVHabitacion());
        hab.setCocinaSN(habNewInfo.getCocinaSN());
        hab.setCamaExtraSN(habNewInfo.getCamaExtraSN());
        hab.setPrecioAdulto(habNewInfo.getPrecioAdulto());
        hab.setPrecioNinho(habNewInfo.getPrecioNinho());
        hab.setPrecioMinimo(habNewInfo.getPrecioMinimo());
        habitacionRepository.save(hab);
        return "Habitación correctamente modificada";
    }

    public boolean validarTipoHabitacion (TipoHabitacion tipo, Integer cantidad) {
        switch (cantidad) {
            case 1 :
                if (tipo == TipoHabitacion.SINGLE)
                    return true;
                break;
            case 2 :
                if (tipo == TipoHabitacion.MATRIMONIAL)
                    return true;
                break;
            case 3 :
                if (tipo == TipoHabitacion.TRIPLE)
                    return true;
                break;
            case 4 :
                if (tipo == TipoHabitacion.CUADRUPLE)
                    return true;
                break;
            case 5 :
                if (tipo == TipoHabitacion.SUITE)
                    return true;
                break;
        }

        return false;
    }

    private String mensaje() {
        return "Número de habitación inexistente";
    }




}
