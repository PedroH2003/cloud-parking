package candido.pedro.parking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import candido.pedro.parking.controller.dto.ParkingCreateDTO;
import candido.pedro.parking.controller.dto.ParkingDTO;
import candido.pedro.parking.controller.mapper.ParkingMapper;
import candido.pedro.parking.model.Parking;
import candido.pedro.parking.service.ParkingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/parking")
@Tag(name = "Parking Controller", description = "Gerencia operações de estacionamento")
public class ParkingController {

    private final ParkingService parkingService;
    private final ParkingMapper parkingMapper;
  
    public ParkingController(ParkingService parkingService, ParkingMapper parkingMapper){
        this.parkingService = parkingService;
        this.parkingMapper = parkingMapper;
    }

    @GetMapping
    @Operation(
        summary = "Find All",
        description = "Retorna uma lista de todos os veículos disponíveis no sistema"
    )
    public ResponseEntity<List<ParkingDTO>> findAll(){
        List<Parking> parkingList = parkingService.findAll(); 
        List<ParkingDTO> result = parkingMapper.toParkingDTOList(parkingList);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Find By Id",
        description = "Retorna um veículo disponível no sistema pelo id"
    )
    public ResponseEntity<ParkingDTO> findById(@PathVariable String id){
        Parking parking = parkingService.findById(id); 
        ParkingDTO result = parkingMapper.toParkingDTO(parking);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    @Operation(
        summary = "Create",
        description = "Salva um veículo no sistema de estacionamento"
    )
    public ResponseEntity<ParkingDTO> create(@RequestBody ParkingCreateDTO parkingCreateDTO){
        Parking parkingCreate = parkingMapper.toParkingCreate(parkingCreateDTO);
        Parking parking = parkingService.create(parkingCreate); 
        ParkingDTO result = parkingMapper.toParkingDTO(parking);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }    

}
