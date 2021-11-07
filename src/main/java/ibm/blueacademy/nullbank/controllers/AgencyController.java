package ibm.blueacademy.nullbank.controllers;

import ibm.blueacademy.nullbank.models.Agency;
import ibm.blueacademy.nullbank.requests.CreateAgencyRequest;
import ibm.blueacademy.nullbank.responses.AgencyResponse;
import ibm.blueacademy.nullbank.services.AgencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/agencies")
public class AgencyController {
    private AgencyService agencyService;

    public AgencyController(AgencyService agencyService) {
        this.agencyService = agencyService;
    }

    @PostMapping
    public ResponseEntity<AgencyResponse> createAgency(
        @Valid @RequestBody CreateAgencyRequest request
    ) {
        Agency agency = agencyService.createAgency(request);
        AgencyResponse response = new AgencyResponse(agency);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AgencyResponse>> listAgencies() {
        List<Agency> agencies = agencyService.listAgencies();
        List<AgencyResponse> response = agencies.stream().map(AgencyResponse::new).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
