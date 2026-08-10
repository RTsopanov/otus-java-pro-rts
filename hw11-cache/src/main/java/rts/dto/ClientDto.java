package rts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rts.model.Client;
import rts.model.Phone;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    private Long id;
    private String name;
    private String addressStreet;
    private List<String> phoneNumbers;

    public static ClientDto from(Client client) {
        return new ClientDto(
                client.getId(),
                client.getName(),
                client.getAddress() != null ? client.getAddress().getStreet() : null,
                client.getPhones().stream()
                        .map(Phone::getNumber)
                        .collect(Collectors.toList())
        );
    }
}