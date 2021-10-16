package csd.cs203project.service.measures;

import csd.cs203project.model.Measures;
import csd.cs203project.model.User;
import csd.cs203project.repository.measures.MeasuresRepository;
import csd.cs203project.service.SES.SESService;
import csd.cs203project.service.telegrambot.TelegramBotService;
import csd.cs203project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeasuresServiceImpl implements MeasuresService {

    private MeasuresRepository measuresRepository;

    private UserService userService;

    private TelegramBotService telegramBotService;

    private SESService sesService;

    @Autowired
    public MeasuresServiceImpl(MeasuresRepository measuresRepository, UserService userService, TelegramBotService telegramBotService, SESService sesService) {
        this.measuresRepository = measuresRepository;
        this.userService = userService;
        this.telegramBotService = telegramBotService;
        this.sesService = sesService;
    }

    @Override
    public Measures addMeasures(Measures measures) {
     
        Measures oldMeasures = findByTypeOfShop(measures.getTypeOfShop());
        if (measures.getClosingTime().length() == 5) measures.setClosingTime(measures.getClosingTime()+":00");
        if (oldMeasures != null){
            List<String> changes = getChangeInMeasures(oldMeasures, measures);
            List<User> affectedUsers = userService.findByShopShopType(measures.getTypeOfShop());
            List<String> affectedUsersEmails = affectedUsers.stream()
                    .map(affectedUser -> affectedUser.getEmail())
                    .collect(Collectors.toList());
            if (changes.size() > 0) {
                telegramBotService.sendUpdate(changes, affectedUsers);
                //sesService.sendMessageEmailRequest("fake", "faker", affectedUserEmails);
            }
        }
        measuresRepository.deleteByTypeOfShop(measures.getTypeOfShop());
        return measuresRepository.save(measures);

    }

    @Override
    public Measures findByTypeOfShop(String typeOfShop) {
        List<Measures> measures = measuresRepository.findByTypeOfShop(typeOfShop);
        if (measures.size() > 0) return measures.get(0);
        else return null;
    }

    @Override
    public List<String> getChangeInMeasures(Measures oldMeasures, Measures newMeasures) {
        ArrayList<String> changes = new ArrayList<>();
        if (oldMeasures.getDineInSize() != newMeasures.getDineInSize()) {
            changes.add(
                    "Dine In Size changed from "
                    + oldMeasures.getDineInSize()
                    + " to " + newMeasures.getDineInSize()
            );
        }
        if (oldMeasures.getMaxGrpSizeVacc() != newMeasures.getMaxGrpSizeVacc()) {
            changes.add(
                    "Max Group Size for Vaccinated customers changed from "
                            + oldMeasures.getMaxGrpSizeVacc()
                            + " to " + newMeasures.getMaxGrpSizeVacc()
            );
        }
        if (oldMeasures.getMaxGrpSizeNonVacc() != newMeasures.getMaxGrpSizeNonVacc()) {
            changes.add(
                    "Max Group Size for Non Vaccinated customers changed from "
                            + oldMeasures.getMaxGrpSizeNonVacc()
                            + " to " + newMeasures.getMaxGrpSizeNonVacc()
            );
        }
        if (oldMeasures.getSocialDistance() != newMeasures.getSocialDistance()) {
            changes.add(
                    "The Social Distance changed from "
                            + oldMeasures.getSocialDistance()
                            + " to " + newMeasures.getSocialDistance()
            );
        }
        if (!oldMeasures.getClosingTime().equals(newMeasures.getClosingTime())) {
            changes.add(
                    "The Closing Time changed from "
                            + oldMeasures.getClosingTime()
                            + " to " + newMeasures.getClosingTime()
            );
        }
        if (!oldMeasures.getPhase().equals(newMeasures.getPhase())) {
            changes.add(
                    "The Phase changed from "
                            + oldMeasures.getPhase()
                            + " to " + newMeasures.getPhase()
            );
        }
        return changes;
    }

    @Override
    public List<Measures> findAllMeasures() {
        return measuresRepository.findAll();
    }

}