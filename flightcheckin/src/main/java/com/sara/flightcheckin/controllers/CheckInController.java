package com.sara.flightcheckin.controllers;

import com.sara.flightcheckin.dto.ReservationUpdateRequest;
import com.sara.flightcheckin.entities.Reservation;
import com.sara.flightcheckin.integration.ReservationRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CheckInController {

    @Autowired
    ReservationRestClient restClient;

    @RequestMapping("/showStartCheckIn")
    public String showStartCheckIn() {
        return "startCheckIn";
    }

    @RequestMapping("/startCheckIn")
    public String startCheckIn(
            @RequestParam("reservationId") Long reservationId,
            ModelMap modelMap) {
        Reservation reservation = restClient.findReservation(reservationId);
        modelMap.addAttribute("reservation", reservation);
        return "displayReservationDetails";
    }

    @RequestMapping("/completeCheckIn")
    public String completeCheckIn(
            @RequestParam("reservationId") Long reservationId,
            @RequestParam("numberOfBags") int numberOfBags) {
        ReservationUpdateRequest reservationUpdateRequest = new ReservationUpdateRequest();
        reservationUpdateRequest.setId(reservationId);
        reservationUpdateRequest.setNumberOfBags(numberOfBags);
        reservationUpdateRequest.setCheckedIn(true);
        restClient.updateReservation(reservationUpdateRequest);
        return "checkInConfirmation";
    }
}
