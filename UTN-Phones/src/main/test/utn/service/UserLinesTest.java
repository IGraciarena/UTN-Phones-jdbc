package utn.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import utn.dao.UserLineDao;
import utn.exceptions.AlreadyExistsException;

import static org.mockito.MockitoAnnotations.initMocks;

public class UserLinesTest {

    UserLineService userLineService;
    @Mock
    UserLineDao userLineDao;


    @Before
    public void setUp() {
        initMocks(this);
        userLineService = new UserLineService(userLineDao);
    }

    @Test
    public void testAddOk() throws AlreadyExistsException {

    }
}
