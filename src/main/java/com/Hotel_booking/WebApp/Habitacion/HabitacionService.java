package com.Hotel_booking.WebApp.Habitacion;

import com.Hotel_booking.WebApp.exceptions.CustomErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    public List<Habitacion> getHabitaciones() {
        return habitacionRepository.findAll();
    }

    public String agregarNuevaHabitacion(Habitacion hab) {

    if(validarTipoHabitacion(hab.getTipoHabitacion(), hab.getCantidadPersonas())) {
        habitacionRepository.save(hab);
        throw new CustomErrorException(HttpStatus.OK, "Habitación correctamente ingresada");
    }
    else
        throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Datos ingresados incorrectos");
    }

    @Transactional
    public String modificarHabitacion(Long ID,
                                    Habitacion habNewInfo) {

        Habitacion hab = habitacionRepository.findById(ID).orElseThrow(() -> new IllegalStateException (mensaje()));

        if (habNewInfo.getNombre().trim().equals(""))
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Ingresar un nombre válido");

        if(!validarTipoHabitacion(habNewInfo.getTipoHabitacion(), habNewInfo.getCantidadPersonas()))
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "El tipo de habitación y la cantidad de personas no concuerdan");

        if (habNewInfo.getTVHabitacion() == null)
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Debes seleccionar un tipo de TV");


        if (habNewInfo.getCocinaSN() == null)
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Debes seleccionar un valor para la cocina");

        if (habNewInfo.getCamaExtraSN() == null)
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Debes seleccionar un el valor de cama extra");

        if (habNewInfo.getPrecioAdulto() < 0)
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Debes ingresar un precio para adultos válido");

        if (habNewInfo.getPrecioNinho() < 0)
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Debes ingresar un precio para niños válido");

        if (habNewInfo.getPrecioMinimo() <= 0)
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Debes ingresar un precio mínimo mayor a cero");


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

        throw new CustomErrorException(HttpStatus.OK, "Habitación correctamente modificada");

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
        throw new CustomErrorException(HttpStatus.NOT_FOUND, "Número de habitación inexistente");
    }




}
