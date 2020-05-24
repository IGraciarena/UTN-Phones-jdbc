package utn.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utn.controller.InvoiceController;
import utn.controller.PhoneCallController;
import utn.controller.UserController;
import utn.session.SessionManager;

@RestController
@RequestMapping("/api/client")
public class ClientWebController {
    UserController userController;
    SessionManager sessionManager;
    PhoneCallController phoneCallController;// todo consulta de llamadas y consulta de destinos mas llamados
    InvoiceController invoiceController;//todo consulta de facturas de un rango a otro

    @Autowired
    public ClientWebController(UserController userController, SessionManager sessionManager, PhoneCallController phoneCallController, InvoiceController invoiceController) {
        this.invoiceController = invoiceController;
        this.phoneCallController = phoneCallController;
        this.sessionManager = sessionManager;
        this.userController = userController;
    }


}
