package airfoilmanager.AirfoilManagerService;

import airfoilmanager.dao.AirfoilDao;
import base.domain.airfoil.Airfoil;
import base.domain.airfoil.Prefix;
import base.dto.AirfoilDto;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ServiceAirfoil {
    private final AirfoilDao airfoilDao;
    private final Converter converter;

    @Autowired(required = false)
    public ServiceAirfoil(AirfoilDao airfoilDao,  Converter converter) {
        this.airfoilDao = airfoilDao;
        this.converter = converter;
    }

    public Airfoil getAirfoilById(@NonNull String id) {
        return airfoilDao.findOne(id);
    }

    public List<AirfoilDto> getAirfoilsByPrefix(@NonNull Prefix prefix) {
        List<Airfoil> airfoils = airfoilDao.findAllByPrefix(prefix);
        return airfoils.stream().map(converter::toDtoAirfoil).collect(Collectors.toList());
    }

    public String addAirfoil(@NonNull Airfoil airfoil) {
        if (airfoilDao.exists(airfoil.getShortName())) {
            log.info("airfoil {} уже существует", airfoil);
            return "airfoil уже существует";
        }
        return saveAirfoil(airfoil);
    }

    private String saveAirfoil(@NonNull Airfoil airfoil) {
        try {
            airfoilDao.save(airfoil);
        } catch (Exception e) {
            log.warn("Ошибка добавление Airfoil {}", airfoil, e);
            return "NOT_OK";
        }
        return "OK";
    }

    public String updateAirfoil(@NonNull Airfoil airfoil) {
        if (!airfoilDao.exists(airfoil.getShortName())) {
            return saveAirfoil(airfoil);
        }
        return "Airfoil еще не существует. для добавления используйте api <</addAirfoil>>";
    }

    public String deleteAirfoil(@NonNull String airfoilId) {
        if (airfoilDao.exists(airfoilId)) {
            try {
                airfoilDao.delete(airfoilId);
                return "OK";
            } catch (Exception e) {
                log.warn("Ошибка добавление AirfoilId {}", airfoilId, e);
                return "NOT_OK";
            }
        }
        return "NOT_OK";
    }
}
