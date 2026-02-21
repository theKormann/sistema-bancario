package app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SistemaBancarioController {

    public SistemaBancarioController(){
        @GetMapping
        public void EncontrarConta(String id){

        }
    }
}
