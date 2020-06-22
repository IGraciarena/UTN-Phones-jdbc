package utn.controller.web;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class RestUtils {
    public static URI getLocationPhoneCall(Integer idPhoneCall) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(idPhoneCall)
                .toUri();
    }
}
