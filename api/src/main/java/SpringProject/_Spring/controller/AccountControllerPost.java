package SpringProject._Spring.controller;

import SpringProject._Spring.dto.client.ClientMapping;
import SpringProject._Spring.dto.client.ClientRequestDTO;
import SpringProject._Spring.model.Account;
import SpringProject._Spring.model.Client;
import SpringProject._Spring.model.Role;
import SpringProject._Spring.service.AccountService;
import SpringProject._Spring.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountControllerPost {

  private final AccountService accountService;
  private final PasswordEncoder passwordEncoder;
  private final ClientService clientService;

  @Autowired
  public AccountControllerPost(AccountService accountService, PasswordEncoder passwordEncoder, ClientService clientService) {
    this.accountService = accountService;
    this.passwordEncoder = passwordEncoder;
    this.clientService = clientService;
  }

  @PostMapping("/register")
  @PreAuthorize("isAnonymous()")
  public ResponseEntity<?> addClient(@Valid @RequestBody ClientRequestDTO clientRequestDTO) {
    if (accountService.existsAccountByEmail(clientRequestDTO.email())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body("This email is already registered. Please try logging in.");
    }

    Client client = ClientMapping.toClient(clientRequestDTO);

    Client savedClient = clientService.saveClient(new Account(clientRequestDTO.email(),
            passwordEncoder.encode(clientRequestDTO.password()),
            List.of(new Role("CLIENT", 3))), client);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedClient.getAccountId())
                            .toUri())
            .body(ClientMapping.toClientResponseDTO(savedClient));
  }
}
