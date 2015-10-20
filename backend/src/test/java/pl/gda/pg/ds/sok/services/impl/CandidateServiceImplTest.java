package pl.gda.pg.ds.sok.services.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import pl.gda.pg.ds.sok.entities.Candidate;
import pl.gda.pg.ds.sok.utils.DbUtil;

import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CandidateServiceImplTest {

    private CandidateServiceImpl candidateService;
    //private static final String token = "a-b-c";

    @Before
    public void init() {
        candidateService = new CandidateServiceImpl();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetCandidateByToken() throws Exception {
        Response response = candidateService.getCandidateByToken("a");
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());

        Session session = DbUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Candidate");
        List<Candidate> resultList = query.list();
        if (resultList.size() > 0) {
            response = candidateService.getCandidateByToken(resultList.get(0).getToken());
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        }
    }

    /*
    need to add PowerMockito to omit sending an email
    @Test
    @SuppressWarnings("unchecked")
    public void testCreateCandidate() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        CandidateBean candidate = Mockito.mock(CandidateBean.class);

        Mockito.when(request.getRemoteAddr()).thenReturn("127.0.0.1");
        Mockito.when(candidate.getName()).thenReturn("Test user");
        Mockito.when(candidate.getEmail()).thenReturn("soktest@ds.pg.gda.pl");
        Mockito.when(candidate.getToken()).thenReturn(token);

        Response response = candidateService.createCandidate(request, candidate);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

        response = candidateService.createCandidate(request, candidate);
        assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());

        Session session = DbUtil.getSessionFactory().openSession();
        Query query = session.createQuery("delete from Candidate where token = :token");
        query.setString("token", token);
        System.err.println(query.executeUpdate());
    }*/
}