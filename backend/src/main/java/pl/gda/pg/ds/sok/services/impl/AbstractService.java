package pl.gda.pg.ds.sok.services.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import pl.gda.pg.ds.sok.entities.Candidate;
import pl.gda.pg.ds.sok.utils.DbUtil;
import pl.gda.pg.ds.sok.utils.PropertiesUtil;

import java.util.List;

public abstract class AbstractService {

    Session session = DbUtil.getSession();

    protected boolean canAdmin(String token) {
        Query query = session.createQuery("from Candidate where token = :token");
        query.setString("token", token);
        List<Candidate> candidateList = query.list();
        if (candidateList.isEmpty()) {
            return false;
        }
        Candidate candidate = candidateList.get(0);
        return PropertiesUtil.canAdmin(candidate.getEmail());
    }
}
